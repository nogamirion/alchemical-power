package jp.nogami_rion.alchemical_power.entity;

import jp.nogami_rion.alchemical_power.init.entitylist;
import jp.nogami_rion.alchemical_power.item.ArsenalBowItem;
import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class ThrownSwordEntity extends Projectile {
    private static final EntityDataAccessor<ItemStack> SWORD_STACK = SynchedEntityData.defineId(ThrownSwordEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Boolean> WAITING_FOR_SALVO = SynchedEntityData.defineId(ThrownSwordEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> LOYALTY_LEVEL = SynchedEntityData.defineId(ThrownSwordEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RETURNING = SynchedEntityData.defineId(ThrownSwordEntity.class, EntityDataSerializers.BOOLEAN);
    private boolean dealtDamage;
    private byte powerLevel;
    private float drawPower = 1.0F;
    private int returningTicks;

    public ThrownSwordEntity(EntityType<? extends ThrownSwordEntity> type, Level level) {
        super(type, level);
    }

    public ThrownSwordEntity(Level level, LivingEntity owner, ItemStack swordStack, int loyaltyLevel, int powerLevel, float drawPower) {
        this(entitylist.THROWN_SWORD.get(), level);
        this.setOwner(owner);
        this.setPos(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
        this.setSwordStack(swordStack);
        this.entityData.set(LOYALTY_LEVEL, Mth.clamp(loyaltyLevel, 0, 127));
        this.powerLevel = (byte) Mth.clamp(powerLevel, 0, 127);
        this.drawPower = Mth.clamp(drawPower, 0.0F, 1.0F);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SWORD_STACK, new ItemStack(Items.IRON_SWORD));
        this.entityData.define(WAITING_FOR_SALVO, false);
        this.entityData.define(LOYALTY_LEVEL, 0);
        this.entityData.define(RETURNING, false);
    }

    public ItemStack getSwordStack() {
        return this.entityData.get(SWORD_STACK);
    }

    private void setSwordStack(ItemStack stack) {
        if (stack == null) {
            this.entityData.set(SWORD_STACK, ItemStack.EMPTY);
            return;
        }
        ItemStack copy = stack.copy();
        if (!copy.isEmpty()) {
            copy.setCount(1);
        }
        this.entityData.set(SWORD_STACK, copy);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.entityData.get(WAITING_FOR_SALVO)) return;

        Entity owner = this.getOwner();
        if (!this.level().isClientSide && ConstellationTreasuryItem.isTreasury(this.getSwordStack())
                && owner instanceof LivingEntity livingOwner) {
            ConstellationTreasuryCombat.tickSalvo(this.getSwordStack(), livingOwner);
            boolean waiting = this.dealtDamage && ConstellationTreasuryCombat.isActive(this.getSwordStack());
            this.entityData.set(WAITING_FOR_SALVO, waiting);
            if (waiting) return;
        }
        int loyaltyLevel = this.entityData.get(LOYALTY_LEVEL);
        if ((!this.level().isClientSide && this.dealtDamage) || this.entityData.get(RETURNING)) {
            if (loyaltyLevel > 0 && owner != null) {
                if (!this.isAcceptibleReturnOwner()) {
                    if (!this.level().isClientSide) {
                        this.spawnAtLocation(this.getSwordStack());
                        this.discard();
                    }
                    return;
                }

                this.entityData.set(RETURNING, true);
                Vec3 target = new Vec3(owner.getX() - this.getX(), owner.getEyeY() - this.getY(), owner.getZ() - this.getZ());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(target.normalize().scale(0.05D * loyaltyLevel)));
                if (this.returningTicks == 0 && !this.level().isClientSide) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }
                this.returningTicks++;

                if (target.lengthSqr() < 1.0D) {
                    if (!this.level().isClientSide) {
                        this.returnToOwner(owner);
                        this.discard();
                    }
                    return;
                }
            } else if (this.dealtDamage && !this.level().isClientSide) {
                if (!this.getSwordStack().isEmpty()) {
                    this.spawnAtLocation(this.getSwordStack());
                }
                this.discard();
                return;
            }
        }

        if (!this.entityData.get(RETURNING) && !this.dealtDamage) {
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (this.getSwordStack().getItem() instanceof ConstellationTreasuryItem && owner instanceof LivingEntity livingOwner) {
                LivingEntity specialTarget = ConstellationTreasuryCombat.findTarget(this.level(), livingOwner,
                        this.position(), this.position().add(this.getDeltaMovement()), 0.3);
                if (specialTarget != null) {
                    var bounds = specialTarget.getBoundingBox().inflate(0.3);
                    Vec3 point = bounds.contains(this.position()) ? this.position()
                            : bounds.clip(this.position(), this.position().add(this.getDeltaMovement())).orElse(specialTarget.position());
                    if (hitResult.getType() == HitResult.Type.MISS || point.distanceToSqr(this.position()) <= hitResult.getLocation().distanceToSqr(this.position()))
                        hitResult = new EntityHitResult(specialTarget, point);
                }
            }
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.setPos(hitResult.getLocation());
                this.onHit(hitResult);
            }
        }

        if (this.isRemoved() || this.entityData.get(WAITING_FOR_SALVO)) return;

        Vec3 movement = this.getDeltaMovement();
        double nextX = this.getX() + movement.x;
        double nextY = this.getY() + movement.y;
        double nextZ = this.getZ() + movement.z;
        this.updateRotation();

        if (!this.entityData.get(RETURNING) && !this.dealtDamage) {
            float drag = this.isInWater() ? 0.8F : 0.99F;
            this.setDeltaMovement(movement.scale(drag).add(0.0D, -0.05D, 0.0D));
        }

        this.setPos(nextX, nextY, nextZ);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level().isClientSide) {
            this.finishHit();
            return;
        }
        Entity target = result.getEntity();
        Entity owner = this.getOwner();
        float damage = this.getSwordDamage(target);

        if (this.getSwordStack().getItem() instanceof ConstellationTreasuryItem
                && target instanceof LivingEntity livingTarget && owner instanceof LivingEntity livingOwner) {
            if (ConstellationTreasuryCombat.strike(this.getSwordStack(), livingOwner, livingTarget, this, damage, false)) {
                ConstellationTreasuryCombat.startSalvo(this.getSwordStack(), livingOwner, livingTarget);
            }
            this.entityData.set(WAITING_FOR_SALVO, ConstellationTreasuryCombat.isActive(this.getSwordStack()));
            this.finishHit();
            this.level().playSound(null, this.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1, 1);
            return;
        }

        if (target instanceof LivingEntity livingTarget && owner instanceof LivingEntity livingOwner) {
            if (ConstellationTreasuryCombat.strike(this.getSwordStack(), livingOwner, livingTarget, this, damage, true)) {
                EnchantmentHelper.doPostHurtEffects(livingTarget, owner);
                EnchantmentHelper.doPostDamageEffects(livingOwner, livingTarget);
                this.doEnchantDamageEffects(livingOwner, target);
                // Use the equipment held by this projectile, not the owner's bow.
                // Synthetic melee hooks stay excluded to prevent recursive treasury salvos.
                if (ConstellationTreasuryItem.isTreasury(this.getSwordStack())) {
                    ConstellationTreasuryCombat.startSalvo(this.getSwordStack(), livingOwner, livingTarget);
                    this.entityData.set(WAITING_FOR_SALVO, ConstellationTreasuryCombat.isActive(this.getSwordStack()));
                }
                this.refreshSwordStack();
            }
        } else {
            target.hurt(this.damageSources().trident(this, owner == null ? this : owner), damage);
        }

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
        this.finishHit();
    }

    @Override
    protected void onHit(HitResult result) {
        if (this.level().isClientSide) {
            this.finishHit();
            return;
        }
        super.onHit(result);
        if (result.getType() != HitResult.Type.ENTITY) {
            this.hurtSword();
            this.finishHit();
            if (this.entityData.get(LOYALTY_LEVEL) <= 0 && !this.level().isClientSide && !this.getSwordStack().isEmpty()) {
                this.spawnAtLocation(this.getSwordStack());
                this.discard();
            }
        }
    }

    private void finishHit() {
        this.dealtDamage = true;
        this.setDeltaMovement(Vec3.ZERO);
        // Return state is authoritative on the server, including any salvo delay.
        if (!this.level().isClientSide && this.entityData.get(LOYALTY_LEVEL) > 0
                && this.isAcceptibleReturnOwner() && !this.entityData.get(WAITING_FOR_SALVO)) {
            this.entityData.set(RETURNING, true);
            Entity owner = this.getOwner();
            Vec3 target = new Vec3(owner.getX() - this.getX(), owner.getEyeY() - this.getY(), owner.getZ() - this.getZ());
            this.setDeltaMovement(target.normalize().scale(0.05D * this.entityData.get(LOYALTY_LEVEL)));
        }
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        if (this.getSwordStack().getItem() instanceof ConstellationTreasuryItem && entity instanceof LivingEntity target
                && this.getOwner() instanceof LivingEntity owner) return ConstellationTreasuryCombat.canTarget(owner, target);
        return super.canHitEntity(entity) && entity != this.getOwner();
    }

    private float getSwordDamage(Entity target) {
        ItemStack stack = this.getSwordStack();
        float damage = ArsenalBowItem.getProjectileBaseDamage(stack);
        if (target instanceof LivingEntity livingTarget) {
            damage += EnchantmentHelper.getDamageBonus(stack, livingTarget.getMobType());
        }
        if (this.powerLevel > 0) {
            damage += (float) this.powerLevel * 0.5F + 0.5F;
        }
        return damage * (0.15F + 1.10F * this.drawPower);
    }

    private boolean isAcceptibleReturnOwner() {
        Entity owner = this.getOwner();
        if (owner == null || !owner.isAlive()) {
            return false;
        }
        return !(owner instanceof Player player) || !player.isSpectator();
    }

    private void hurtSword() {
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity livingOwner) {
            ItemStack stack = this.getSwordStack();
            stack.hurtAndBreak(1, livingOwner, entity -> {
            });
            this.refreshSwordStack();
        }
    }

    private void refreshSwordStack() {
        ItemStack stack = this.getSwordStack();
        if (stack.isEmpty()) {
            this.setSwordStack(ItemStack.EMPTY);
        } else {
            this.entityData.set(SWORD_STACK, stack);
        }
    }

    private void returnToOwner(Entity owner) {
        ItemStack stack = this.getSwordStack().copy();
        if (stack.isEmpty()) {
            return;
        }
        if (owner instanceof Player player) {
            if (!player.getInventory().add(stack)) {
                player.drop(stack, false);
            }
        } else {
            owner.spawnAtLocation(stack);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setSwordStack(ItemStack.of(tag.getCompound("Sword")));
        this.dealtDamage = tag.getBoolean("DealtDamage");
        this.entityData.set(RETURNING, tag.getBoolean("Returning"));
        this.entityData.set(LOYALTY_LEVEL, Mth.clamp((int) tag.getByte("Loyalty"), 0, 127));
        this.powerLevel = tag.getByte("Power");
        this.drawPower = tag.contains("DrawPower") ? Mth.clamp(tag.getFloat("DrawPower"), 0.0F, 1.0F) : 1.0F;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Sword", this.getSwordStack().save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
        tag.putBoolean("Returning", this.entityData.get(RETURNING));
        tag.putByte("Loyalty", this.entityData.get(LOYALTY_LEVEL).byteValue());
        tag.putByte("Power", this.powerLevel);
        tag.putFloat("DrawPower", this.drawPower);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
