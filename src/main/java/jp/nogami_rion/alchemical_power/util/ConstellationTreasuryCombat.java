package jp.nogami_rion.alchemical_power.util;

import jp.nogami_rion.alchemical_power.entity.SummonedArsenalWeapon;
import jp.nogami_rion.alchemical_power.event.ArsenalDamageSourceEvent;
import jp.nogami_rion.alchemical_power.integration.tinker.TinkerArsenalBridge;
import jp.nogami_rion.alchemical_power.item.ArsenalBowItem;
import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import javax.annotation.Nullable;

public final class ConstellationTreasuryCombat {
    private static final String SALVO = "ArsenalSalvo";
    private static final String LAST_ATTACK = "AlchemicalPowerArsenalLastAttack";
    public static final int FIRST_IMPACT = 20;
    public static final int INTERVAL = 2;
    public static final int WEAPONS_PER_SHOT = 2;
    public static final double BASE_SUMMON_RADIUS = 5;
    public static final double RETARGET_RADIUS = 32;

    private ConstellationTreasuryCombat() {}

    public static int impactTick(int slot) { return FIRST_IMPACT + slot / WEAPONS_PER_SHOT * INTERVAL; }

    public static DamageSource source(Level level, Entity direct, LivingEntity owner) {
        return ArsenalDamageSource.penetrating(baseSource(level, direct, owner));
    }

    private static DamageSource baseSource(Level level, Entity direct, LivingEntity owner) {
        if (direct != owner) return level.damageSources().trident(direct, owner);
        return owner instanceof Player player ? level.damageSources().playerAttack(player) : level.damageSources().mobAttack(owner);
    }

    public static boolean canTarget(LivingEntity owner, LivingEntity target) {
        if (owner == target || !target.isAlive() || target.isSpectator() || owner.isAlliedTo(target)) return false;
        if (target instanceof Player player) {
            if (player.getAbilities().instabuild) return false;
            if (owner instanceof Player attacker && !attacker.canHarmPlayer(player)) return false;
        }
        return true;
    }

    /** Deliberately does not use isPickable/isAttackable; even a zero-sized box gets a small aim volume. */
    @Nullable public static LivingEntity findTarget(Level level, LivingEntity owner, Vec3 start, Vec3 end, double padding) {
        Vec3 limit = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, owner)).getLocation();
        LivingEntity nearest = null;
        double distance = start.distanceToSqr(limit) + 1.0E-7;
        for (LivingEntity candidate : level.getEntitiesOfClass(LivingEntity.class, new AABB(start, limit).inflate(padding + 1.0), e -> canTarget(owner, e))) {
            AABB bounds = candidate.getBoundingBox().inflate(padding);
            Vec3 hit = bounds.contains(start) ? start : bounds.clip(start, limit).orElse(null);
            if (hit != null && start.distanceToSqr(hit) < distance) {
                distance = start.distanceToSqr(hit);
                nearest = candidate;
            }
        }
        return nearest;
    }

    public static void attack(Player player) {
        if (player.level().isClientSide || !player.isAlive() || player.isSpectator()
                || player.containerMenu != player.inventoryMenu || player.isUsingItem()
                || !(player.getMainHandItem().getItem() instanceof ConstellationTreasuryItem)) return;
        long now = player.level().getGameTime();
        CompoundTag state = player.getPersistentData();
        if (state.contains(LAST_ATTACK) && state.getLong(LAST_ATTACK) == now) return;
        state.putLong(LAST_ATTACK, now);
        // Respect attack speed even though the damage itself bypasses the target's cooldown.
        if (player.getAttackStrengthScale(0.5F) < 0.9F) return;
        Vec3 start = player.getEyePosition();
        LivingEntity target = findTarget(player.level(), player, start, start.add(player.getLookAngle().scale(player.getEntityReach())), 0.25);
        player.resetAttackStrengthTicker();
        if (target == null) return;
        ItemStack sword = player.getMainHandItem();
        float damage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                + EnchantmentHelper.getDamageBonus(sword, target.getMobType());
        if (strike(sword, player, target, player, damage, false)) {
            EnchantmentHelper.doPostHurtEffects(target, player);
            EnchantmentHelper.doPostDamageEffects(player, target);
            player.setLastHurtMob(target);
            player.awardStat(Stats.ITEM_USED.get(sword.getItem()));
            player.causeFoodExhaustion(0.1F);
            startSalvo(sword, player, target);
            player.level().playSound(null, target.blockPosition(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1, 1);
        }
    }

    public static boolean strike(ItemStack weapon, LivingEntity owner, LivingEntity target, Entity direct, float damage, boolean wear) {
        if (!(owner.level() instanceof ServerLevel) || !canTarget(owner, target) || !Float.isFinite(damage) || damage <= 0) return false;
        float baseDamage = damage;
        damage = TinkerArsenalBridge.getMeleeDamage(weapon, owner, target, baseDamage, damage);
        damage = TinkerArsenalBridge.beforeMeleeHit(weapon, owner, target, damage);
        if (!Float.isFinite(damage) || damage <= 0) return false;
        // Items do not expose a universal native DamageSource getter. Integrations can resolve
        // weapon-specific primary sources here, without replacing sources from secondary effects.
        var sourceEvent = new ArsenalDamageSourceEvent(weapon, owner, target, direct, baseSource(owner.level(), direct, owner));
        MinecraftForge.EVENT_BUS.post(sourceEvent);
        if (!target.hurt(ArsenalDamageSource.penetrating(sourceEvent.getSource()), damage)) {
            TinkerArsenalBridge.failedMeleeHit(weapon, owner, target, damage);
            return false;
        }
        int fire = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, weapon);
        if (fire > 0) target.setSecondsOnFire(fire * 4);
        int knockback = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, weapon);
        if (knockback > 0) target.knockback(knockback * 0.5, owner.getX() - target.getX(), owner.getZ() - target.getZ());
        if (wear) {
            boolean handled = weapon.getItem().hurtEnemy(weapon, target, owner);
            if (!handled) weapon.hurtAndBreak(1, owner, e -> {});
        }
        TinkerArsenalBridge.afterMeleeHit(weapon, owner, target, damage);
        return true;
    }

    public static boolean isActive(ItemStack sword) { return ConstellationTreasuryItem.data(sword, SALVO) != null; }
    public static void cancelSalvo(ItemStack sword) { ConstellationTreasuryItem.removeData(sword, SALVO); }

    public static void startSalvo(ItemStack sword, LivingEntity owner, LivingEntity target) {
        if (!(owner.level() instanceof ServerLevel level)) return;
        Vec3 searchCenter = target.position();
        if (!target.isAlive()) target = findNextTarget(level, owner, searchCenter, searchCenter);
        if (target == null || !canTarget(owner, target)) return;
        CompoundTag active = ConstellationTreasuryItem.createData(sword, SALVO);
        ListTag volleys = active.getList("Volleys", Tag.TAG_COMPOUND);
        if (volleys.size() >= 16) return;
        var inventory = ConstellationTreasuryItem.inventory(sword);
        boolean any = false;
        long now = level.getGameTime();
        CompoundTag projections = new CompoundTag();
        Vec3 summonCenter = target.position().add(0, target.getBbHeight() * 0.5, 0);
        var occupied = new java.util.ArrayList<Vec3>();
        for (var projection : level.getEntitiesOfClass(SummonedArsenalWeapon.class, target.getBoundingBox().inflate(128),
                entity -> !entity.isDismissing())) occupied.add(projection.getSpawnAnchor().subtract(summonCenter));
        for (int slot = 0; slot < ConstellationTreasuryItem.SLOTS; slot++) {
            ItemStack weapon = inventory.getStackInSlot(slot);
            if (!ConstellationTreasuryItem.accepts(weapon)) continue;
            any = true;
            Vec3 anchor = ArsenalSummonAnimation.chooseAnchor(level.random,
                    Math.max(BASE_SUMMON_RADIUS, target.getBbWidth() * 0.5 + 4.75), occupied);
            occupied.add(anchor);
            var projection = new SummonedArsenalWeapon(level, target, weapon, anchor, impactTick(slot));
            if (level.addFreshEntity(projection)) projections.putUUID(Integer.toString(slot), projection.getUUID());
        }
        if (any) {
            CompoundTag salvo = new CompoundTag();
            salvo.putUUID("Owner", owner.getUUID());
            salvo.putUUID("Target", target.getUUID());
            salvo.putDouble("SearchX", searchCenter.x);
            salvo.putDouble("SearchY", searchCenter.y);
            salvo.putDouble("SearchZ", searchCenter.z);
            salvo.putDouble("TargetX", target.getX());
            salvo.putDouble("TargetY", target.getY());
            salvo.putDouble("TargetZ", target.getZ());
            salvo.put("Projections", projections);
            salvo.putString("Dimension", level.dimension().location().toString());
            salvo.putLong("Start", now);
            salvo.putLong("LastTick", now);
            volleys.add(salvo);
            active.put("Volleys", volleys);
        } else if (volleys.isEmpty()) {
            cancelSalvo(sword);
        }
    }

    /** Absolute deadlines skip missed shots after unloading; no delayed burst or double hit on reload. */
    public static void tickSalvo(ItemStack sword, LivingEntity owner) {
        CompoundTag active = ConstellationTreasuryItem.data(sword, SALVO);
        if (active == null || !(owner.level() instanceof ServerLevel)) return;
        ListTag volleys = active.getList("Volleys", Tag.TAG_COMPOUND);
        for (int i = volleys.size() - 1; i >= 0; i--) {
            CompoundTag salvo = volleys.getCompound(i);
            if (tickVolley(sword, owner, salvo)) {
                updateProjections((ServerLevel) owner.level(), salvo, null);
                volleys.remove(i);
            }
        }
        if (volleys.isEmpty()) cancelSalvo(sword);
    }

    private static boolean tickVolley(ItemStack sword, LivingEntity owner, CompoundTag salvo) {
        ServerLevel level = (ServerLevel) owner.level();
        long now = level.getGameTime();
        if (!owner.isAlive() || !salvo.hasUUID("Owner") || !salvo.getUUID("Owner").equals(owner.getUUID())
                || !salvo.hasUUID("Target") || !salvo.getString("Dimension").equals(level.dimension().location().toString())) {
            return true;
        }
        if (salvo.getLong("LastTick") == now) return false;
        long age = now - salvo.getLong("Start");
        LivingEntity target = resolveTarget(level, owner, salvo);
        if (target == null || age < 0) {
            return true;
        }
        if (age >= FIRST_IMPACT && (age - FIRST_IMPACT) % INTERVAL == 0) {
            long index = (age - FIRST_IMPACT) / INTERVAL * WEAPONS_PER_SHOT;
            if (index < ConstellationTreasuryItem.SLOTS) {
                salvo.putLong("LastTick", now);
                var inventory = ConstellationTreasuryItem.inventory(sword);
                for (int slot = (int) index; slot < Math.min(index + WEAPONS_PER_SHOT, ConstellationTreasuryItem.SLOTS); slot++) {
                    ItemStack weapon = inventory.getStackInSlot(slot);
                    if (ConstellationTreasuryItem.accepts(weapon)) {
                        target = resolveTarget(level, owner, salvo);
                        if (target == null) return true;
                        float damage = ArsenalBowItem.getProjectileBaseDamage(weapon) + EnchantmentHelper.getDamageBonus(weapon, target.getMobType());
                        if (strike(weapon, owner, target, owner, damage, true)) {
                            inventory.setStackInSlot(slot, weapon.isEmpty() ? ItemStack.EMPTY : weapon);
                            level.playSound(null, target.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 0.65F, 1.2F);
                        }
                    }
                }
            }
        }
        return age >= impactTick(ConstellationTreasuryItem.SLOTS - 1);
    }

    @Nullable private static LivingEntity findNextTarget(ServerLevel level, LivingEntity owner, Vec3 searchCenter, Vec3 origin) {
        LivingEntity nearest = null;
        double distance = Double.POSITIVE_INFINITY;
        for (LivingEntity candidate : level.getEntitiesOfClass(LivingEntity.class, new AABB(searchCenter, searchCenter).inflate(RETARGET_RADIUS),
                e -> canTarget(owner, e) && !e.isAlliedTo(owner) && !(e instanceof Player)
                        && e.position().distanceToSqr(searchCenter) <= RETARGET_RADIUS * RETARGET_RADIUS
                        && (e instanceof Enemy || e instanceof Mob mob && mob.getTarget() == owner))) {
            double nextDistance = candidate.position().distanceToSqr(origin);
            if (nextDistance <= distance) {
                nearest = candidate;
                distance = nextDistance;
            }
        }
        return nearest;
    }

    @Nullable private static LivingEntity resolveTarget(ServerLevel level, LivingEntity owner, CompoundTag salvo) {
        Entity entity = level.getEntity(salvo.getUUID("Target"));
        // Older active volleys have no original center; freeze their last known position once.
        if (!salvo.contains("SearchX")) {
            Vec3 fallback = salvo.contains("TargetX")
                    ? new Vec3(salvo.getDouble("TargetX"), salvo.getDouble("TargetY"), salvo.getDouble("TargetZ"))
                    : entity == null ? null : entity.position();
            if (fallback == null) return null;
            salvo.putDouble("SearchX", fallback.x);
            salvo.putDouble("SearchY", fallback.y);
            salvo.putDouble("SearchZ", fallback.z);
        }
        if (entity instanceof LivingEntity target && canTarget(owner, target)) {
            salvo.putDouble("TargetX", target.getX());
            salvo.putDouble("TargetY", target.getY());
            salvo.putDouble("TargetZ", target.getZ());
            return target;
        }
        // Persist the last known position so removed corpses do not end the remaining volley.
        Vec3 origin = entity != null ? entity.position() : salvo.contains("TargetX")
                ? new Vec3(salvo.getDouble("TargetX"), salvo.getDouble("TargetY"), salvo.getDouble("TargetZ")) : null;
        Vec3 searchCenter = new Vec3(salvo.getDouble("SearchX"), salvo.getDouble("SearchY"), salvo.getDouble("SearchZ"));
        LivingEntity next = origin == null ? null : findNextTarget(level, owner, searchCenter, origin);
        if (next != null) {
            salvo.putUUID("Target", next.getUUID());
            salvo.putDouble("TargetX", next.getX());
            salvo.putDouble("TargetY", next.getY());
            salvo.putDouble("TargetZ", next.getZ());
            updateProjections(level, salvo, next);
        }
        return next;
    }

    private static void updateProjections(ServerLevel level, CompoundTag salvo, @Nullable LivingEntity target) {
        CompoundTag projections = salvo.getCompound("Projections");
        for (String key : projections.getAllKeys()) {
            if (projections.hasUUID(key) && level.getEntity(projections.getUUID(key)) instanceof SummonedArsenalWeapon projection) {
                if (target == null) projection.dismiss();
                else projection.setTarget(target);
            }
        }
    }
}
