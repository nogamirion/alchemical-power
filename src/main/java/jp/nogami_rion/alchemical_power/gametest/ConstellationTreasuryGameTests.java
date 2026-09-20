package jp.nogami_rion.alchemical_power.gametest;

import jp.nogami_rion.alchemical_power.entity.ThrownSwordEntity;
import jp.nogami_rion.alchemical_power.entity.SummonedArsenalWeapon;
import jp.nogami_rion.alchemical_power.util.ArsenalSummonAnimation;
import jp.nogami_rion.alchemical_power.event.ArsenalDamageSourceEvent;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.screen.ConstellationTreasuryMenu;
import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import jp.nogami_rion.alchemical_power.util.ArsenalDamageSource;
import jp.nogami_rion.alchemical_power.util.ModDamageTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.MinecraftForge;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("alchemical_power")
@PrefixGameTestTemplate(false)
public class ConstellationTreasuryGameTests {
    @GameTest(template = "reactor_test", batch = "constellation_fixed_search")
    public static void retargetRangeStaysAtInitialVictimAfterSave(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss first = wither(helper);
        Vec3 origin = first.position();
        double radius = ConstellationTreasuryCombat.RETARGET_RADIUS;
        WitherBoss next = wither(helper);
        next.setPos(origin.add(radius - 2, 0, 0));
        first.setHealth(0);
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        for (int slot = 0; slot < 4; slot++) ConstellationTreasuryItem.inventory(sword).insertItem(slot, new ItemStack(Items.WOODEN_SWORD), false);
        ConstellationTreasuryCombat.startSalvo(sword, owner, first);
        ItemStack restored = ItemStack.of(sword.save(new CompoundTag()));
        var salvo = ConstellationTreasuryItem.data(restored, "ArsenalSalvo").getList("Volleys", net.minecraft.nbt.Tag.TAG_COMPOUND).getCompound(0);
        helper.assertTrue(salvo.getUUID("Target").equals(next.getUUID()) && salvo.getDouble("SearchX") == origin.x,
                "A lethal opening strike retains the original victim's search center across save/load");
        WitherBoss outside = wither(helper);
        outside.setPos(origin.add(radius + 1, 0, 0));
        WitherBoss inside = wither(helper);
        inside.setPos(origin.add(radius - 7, 0, 0));
        WitherBoss central = wither(helper);
        central.setPos(origin.add(1, 0, 0));
        next.setHealth(0);
        long now = helper.getLevel().getGameTime();
        salvo.putLong("Start", now - ConstellationTreasuryCombat.FIRST_IMPACT);
        salvo.putLong("LastTick", now - 1);
        ConstellationTreasuryCombat.tickSalvo(restored, owner);
        helper.assertTrue(salvo.getUUID("Target").equals(inside.getUUID()),
                "Choose the nearest enemy to the previous victim only within the original sphere, excluding a closer outsider");
        helper.assertTrue(outside.getHealth() == outside.getMaxHealth(), "An enemy beyond the initial radius takes no follow-up damage");
        inside.setHealth(0);
        central.setHealth(0);
        salvo.putLong("LastTick", now - 1);
        ConstellationTreasuryCombat.tickSalvo(restored, owner);
        helper.assertTrue(!ConstellationTreasuryCombat.isActive(restored), "The volley ends when only enemies outside the initial sphere remain");
        for (var entity : java.util.List.of(first, next, inside, outside, central)) entity.discard();
        helper.succeed();
    }
    @GameTest(template = "reactor_test", batch = "constellation_tooltip")
    public static void tooltipSummarizesStoredWeaponsWithoutChangingContents(GameTestHelper helper) {
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        var item = (ConstellationTreasuryItem) sword.getItem();
        var lines = new java.util.ArrayList<net.minecraft.network.chat.Component>();
        item.appendHoverText(sword, helper.getLevel(), lines, net.minecraft.world.item.TooltipFlag.Default.NORMAL);
        helper.assertTrue(lines.size() == 2 && sword.getTag() == null, "Empty treasury shows only two summary lines without mutating the stack");
        var empty = (net.minecraft.network.chat.contents.TranslatableContents) lines.get(0).getContents();
        helper.assertTrue(empty.getArgs()[0].equals(0) && empty.getArgs()[1].equals(36), "Empty inventory reports zero of thirty-six slots");
        var inventory = ConstellationTreasuryItem.inventory(sword);
        for (int slot = 0; slot < 36; slot++) inventory.insertItem(slot, new ItemStack(Items.WOODEN_SWORD), false);
        CompoundTag before = sword.save(new CompoundTag());
        lines.clear();
        item.appendHoverText(sword, helper.getLevel(), lines, net.minecraft.world.item.TooltipFlag.Default.NORMAL);
        var count = (net.minecraft.network.chat.contents.TranslatableContents) lines.get(0).getContents();
        var damage = (net.minecraft.network.chat.contents.TranslatableContents) lines.get(1).getContents();
        helper.assertTrue(count.getArgs()[0].equals(36) && damage.getArgs()[0].equals("108"),
                "Thirty-six wooden swords total 108 base damage, excluding the owner's unarmed damage and the treasury itself");
        helper.assertTrue(sword.save(new CompoundTag()).equals(before), "Tooltip generation leaves real inventory and durability unchanged");
        inventory.extractItem(0, 1, false);
        lines.clear();
        item.appendHoverText(sword, helper.getLevel(), lines, net.minecraft.world.item.TooltipFlag.Default.NORMAL);
        damage = (net.minecraft.network.chat.contents.TranslatableContents) lines.get(1).getContents();
        helper.assertTrue(damage.getArgs()[0].equals("105"), "Summary reflects removed weapons immediately");
        helper.succeed();
    }
    @GameTest(template = "reactor_test", batch = "constellation_migration")
    public static void legacyRegistryIdRemapsWithoutChangingStorage(GameTestHelper helper) {
        var registry = net.minecraftforge.registries.ForgeRegistries.ITEMS;
        class TrackedMapping extends net.minecraftforge.registries.MissingMappingsEvent.Mapping<net.minecraft.world.item.Item> {
            net.minecraft.world.item.Item remapped;
            TrackedMapping(String path) {
                super(registry, registry, new net.minecraft.resources.ResourceLocation("alchemical_power", path), -1);
            }
            @Override public void remap(net.minecraft.world.item.Item item) { super.remap(item); remapped = item; }
        }
        var legacy = new TrackedMapping("arsenal_sword");
        var other = new TrackedMapping("unrelated_missing_item");
        jp.nogami_rion.alchemical_power.event.ConstellationTreasuryMigration.remap(
                new net.minecraftforge.registries.MissingMappingsEvent(net.minecraftforge.registries.ForgeRegistries.Keys.ITEMS,
                        registry, java.util.List.of(legacy, other)));
        helper.assertTrue(legacy.remapped == itemlist.CONSTELLATION_TREASURY.get() && other.remapped == null,
                "Only the legacy sword ID remaps to the renamed treasury");
        ItemStack sword = new ItemStack(legacy.remapped);
        ItemStack stored = new ItemStack(Items.DIAMOND_SWORD);
        stored.setDamageValue(17);
        stored.enchant(Enchantments.SHARPNESS, 3);
        ConstellationTreasuryItem.inventory(sword).insertItem(35, stored, false);
        CompoundTag saved = sword.save(new CompoundTag());
        helper.assertTrue(saved.getString("id").equals("alchemical_power:constellation_treasury"), "Future saves use the new registry ID");
        helper.assertTrue(saved.getCompound("tag").contains("ArsenalInventory")
                && ItemStack.matches(ConstellationTreasuryItem.inventory(ItemStack.of(saved)).getStackInSlot(35), stored),
                "Legacy storage payload retains occupied slots, durability, and enchantments");
        helper.succeed();
    }
    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void internalUpdatesDoNotReequipHeldSword(GameTestHelper helper) {
        ItemStack before = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        ConstellationTreasuryItem.inventory(before).insertItem(0, new ItemStack(Items.IRON_SWORD), false);
        ItemStack updated = before.copy();
        var inventory = ConstellationTreasuryItem.inventory(updated);
        ItemStack worn = inventory.getStackInSlot(0);
        worn.setDamageValue(1);
        inventory.setStackInSlot(0, worn);
        updated.getOrCreateTagElement("ArsenalSalvo").putLong("Start", 123);
        var item = before.getItem();
        helper.assertTrue(!item.shouldCauseReequipAnimation(before, updated, false), "Durability and salvo updates must not lower the held sword");
        helper.assertTrue(item.shouldCauseReequipAnimation(before, updated, true), "Actual slot changes still animate");
        ItemStack enchanted = updated.copy();
        enchanted.enchant(Enchantments.SHARPNESS, 1);
        helper.assertTrue(item.shouldCauseReequipAnimation(updated, enchanted, false), "Changes to the sword itself still animate");
        helper.assertTrue(item.shouldCauseReequipAnimation(updated, new ItemStack(Items.IRON_SWORD), false), "Switching items still animates");
        helper.assertTrue(inventory.getStackInSlot(0).getDamageValue() == 1 && ConstellationTreasuryCombat.isActive(updated), "Animation comparison must not mutate gameplay data");
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void hemisphereSpacingAndAnimationPhases(GameTestHelper helper) {
        for (int seed = 0; seed < 24; seed++) {
            var random = net.minecraft.util.RandomSource.create(seed);
            var anchors = new java.util.ArrayList<Vec3>();
            // Separation applies only to weapons in the same volley.
            for (int i = 0; i < ConstellationTreasuryItem.SLOTS; i++) {
                Vec3 anchor = ArsenalSummonAnimation.chooseAnchor(random, ConstellationTreasuryCombat.BASE_SUMMON_RADIUS, anchors);
                helper.assertTrue(anchor.length() >= ConstellationTreasuryCombat.BASE_SUMMON_RADIUS, "Expanded hemisphere respects its base radius");
                helper.assertTrue(anchor.y > 0, "Spawn anchor stays in the upper hemisphere");
                for (Vec3 prior : anchors) helper.assertTrue(anchor.distanceTo(prior) >= ArsenalSummonAnimation.MIN_SEPARATION,
                        "Random anchors retain minimum separation within one volley");
                anchors.add(anchor);
            }
        }
        Vec3 anchor = new Vec3(3, 4, 0);
        int impact = ConstellationTreasuryCombat.FIRST_IMPACT;
        helper.assertTrue(ArsenalSummonAnimation.opacity(0) == 0 && ArsenalSummonAnimation.opacity(6) == 1,
                "Weapons materialize from invisible to opaque");
        helper.assertTrue(ArsenalSummonAnimation.offset(anchor, 0, impact, 0).length() < anchor.length(), "Appearance starts slightly closer to the target");
        helper.assertTrue(ArsenalSummonAnimation.offset(anchor, 8, impact, 0).y != ArsenalSummonAnimation.offset(anchor, 10, impact, 0).y,
                "Waiting weapons bob vertically");
        double pulled = ArsenalSummonAnimation.offset(anchor, impact - 4, impact, 0).length();
        helper.assertTrue(pulled > anchor.length(), "Weapon pulls away from target before launch");
        double firstStep = pulled - ArsenalSummonAnimation.offset(anchor, impact - 3, impact, 0).length();
        double lastStep = ArsenalSummonAnimation.offset(anchor, impact - 1, impact, 0).length();
        helper.assertTrue(lastStep > firstStep && ArsenalSummonAnimation.offset(anchor, impact, impact, 0).lengthSqr() == 0,
                "Flight accelerates and reaches the target at the damage deadline");
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void penetratingSourceRetainsTypeAndPropertiesWithoutGlobalChanges(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        DamageSource ordinary = helper.getLevel().damageSources().playerAttack(owner);
        DamageSource original = new DamageSource(ordinary.typeHolder(), owner, owner, new Vec3(1, 2, 3)) {
            @Override public float getFoodExhaustion() { return 0.75F; }
            @Override public String getMsgId() { return "test_original_source"; }
        };
        DamageSource wrapped = ArsenalDamageSource.penetrating(original);
        helper.assertTrue(wrapped.typeHolder() == original.typeHolder() && wrapped.is(DamageTypes.PLAYER_ATTACK),
                "The registered damage type is preserved");
        helper.assertTrue(wrapped.getEntity() == owner && wrapped.getDirectEntity() == owner
                && wrapped.sourcePositionRaw().equals(new Vec3(1, 2, 3)), "Attacker and source position are preserved");
        helper.assertTrue(wrapped.getFoodExhaustion() == 0.75F && wrapped.getMsgId().equals("test_original_source"),
                "Custom source method behavior is delegated");
        helper.assertTrue(wrapped.is(DamageTypeTags.BYPASSES_COOLDOWN) && wrapped.is(DamageTypeTags.BYPASSES_INVULNERABILITY),
                "Bypass flags apply to this source instance");
        helper.assertTrue(!ordinary.is(DamageTypeTags.BYPASSES_COOLDOWN) && !ordinary.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                && !original.is(DamageTypeTags.BYPASSES_COOLDOWN), "Ordinary attacks and the supplied source are not modified");
        helper.assertTrue(wrapped.is(DamageTypeTags.BYPASSES_ARMOR) == original.is(DamageTypeTags.BYPASSES_ARMOR),
                "Armor handling is not changed");
        DamageSource magic = helper.getLevel().damageSources().magic();
        DamageSource wrappedMagic = ArsenalDamageSource.penetrating(magic);
        helper.assertTrue(wrappedMagic.is(DamageTypes.MAGIC) && wrappedMagic.is(DamageTypeTags.BYPASSES_ARMOR) == magic.is(DamageTypeTags.BYPASSES_ARMOR),
                "A special source retains its type and armor behavior");
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void weaponSourceIntegrationPreservesSpecialTypeOnActualHit(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss target = wither(helper);
        ItemStack weapon = new ItemStack(Items.IRON_SWORD);
        DamageSource original = ModDamageTypes.singularityTrue(helper.getLevel(), owner);
        Consumer<ArsenalDamageSourceEvent> listener = event -> {
            if (event.getWeapon() == weapon) event.setSource(original);
        };
        MinecraftForge.EVENT_BUS.addListener(listener);
        try {
            helper.assertTrue(ConstellationTreasuryCombat.strike(weapon, owner, target, owner, 8, true),
                    "Weapon-specific source still bypasses the wither's summoning immunity");
            DamageSource hit = target.getLastDamageSource();
            helper.assertTrue(hit != null && hit.is(ModDamageTypes.SINGULARITY_TRUE) && hit.typeHolder() == original.typeHolder(),
                    "The actual hit retains the integration's original special damage type");
            helper.assertTrue(weapon.getDamageValue() == 1 && target.getInvulnerableTicks() == 200,
                    "Normal durability handling and summoning state remain intact");
        } finally {
            MinecraftForge.EVENT_BUS.unregister(listener);
            target.discard();
        }
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void directAttackUsesServerAimAndRejectsDuplicatePackets(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        owner.getInventory().setItem(0, sword);
        ConstellationTreasuryItem.inventory(sword).insertItem(0, new ItemStack(Items.IRON_SWORD), false);
        for (int tick = 0; tick < 30; tick++) owner.tick();
        WitherBoss target = wither(helper);
        owner.setPos(target.position().add(0, 0, -2));
        owner.setYRot(0);
        owner.setXRot(0);
        float before = target.getHealth();
        ConstellationTreasuryCombat.attack(owner);
        float after = target.getHealth();
        ConstellationTreasuryCombat.attack(owner);
        helper.assertTrue(after < before && target.getHealth() == after, "Main attack hits the summoning wither exactly once despite duplicate requests");
        helper.assertTrue(ConstellationTreasuryCombat.isActive(sword), "Successful direct hit starts follow-up attacks");
        target.discard();
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void storageRoundTripAndMenuSafety(GameTestHelper helper) {
        Player player = helper.makeMockSurvivalPlayer();
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        player.getInventory().setItem(0, sword);
        var weapons = ConstellationTreasuryItem.inventory(sword);
        ItemStack weapon = new ItemStack(Items.IRON_SWORD);
        weapon.setDamageValue(12);
        weapon.enchant(Enchantments.SHARPNESS, 2);
        weapons.insertItem(0, weapon, false);
        helper.assertTrue(!weapons.insertItem(1, sword.copy(), false).isEmpty(), "Nested arsenal swords must be rejected");
        helper.assertTrue(!weapons.insertItem(1, new ItemStack(Items.DIRT), false).isEmpty(), "Non-weapons must be rejected");
        sword.getTagElement("ArsenalInventory").putInt("Size", 9); // Old nine-slot save.
        ItemStack loaded = ItemStack.of(sword.save(new CompoundTag()));
        helper.assertTrue(ConstellationTreasuryItem.inventory(loaded).getSlots() == 36, "Legacy saves expand to thirty-six slots");
        ItemStack legacy18 = loaded.copy();
        ConstellationTreasuryItem.inventory(legacy18).insertItem(17, new ItemStack(Items.DIAMOND_SWORD), false);
        legacy18.getTagElement("ArsenalInventory").putInt("Size", 18);
        var migrated18 = ConstellationTreasuryItem.inventory(ItemStack.of(legacy18.save(new CompoundTag())));
        helper.assertTrue(migrated18.getSlots() == 36 && migrated18.getStackInSlot(17).is(Items.DIAMOND_SWORD),
                "The last occupied slot of an eighteen-slot save is preserved");
        helper.assertTrue(ItemStack.matches(ConstellationTreasuryItem.inventory(loaded).getStackInSlot(0), weapon), "Contents retain damage and enchantments across save/load");
        ConstellationTreasuryMenu menu = new ConstellationTreasuryMenu(1, player.getInventory(), 0);
        menu.clicked(ConstellationTreasuryItem.SLOTS + 27, 0, ClickType.THROW, player);
        menu.clicked(0, 0, ClickType.SWAP, player);
        helper.assertTrue(player.getInventory().getItem(0) == sword && menu.getCarried().isEmpty(), "Open sword cannot be thrown or swapped into its own storage");
        menu.quickMoveStack(player, 0);
        helper.assertTrue(ConstellationTreasuryItem.inventory(sword).getStackInSlot(0).isEmpty(), "Shift extraction saves immediately");
        long count = player.getInventory().items.stream().filter(s -> s.is(Items.IRON_SWORD)).count();
        helper.assertTrue(count == 1, "Shift extraction produces exactly one real weapon");
        player.getInventory().setItem(0, loaded);
        helper.assertTrue(!menu.stillValid(player), "Replacing the owning sword invalidates the old menu");
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void witherSpawnAndDamageCooldownAreBypassed(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss target = wither(helper);
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        helper.assertTrue(!target.hurt(helper.getLevel().damageSources().playerAttack(owner), 8), "Ordinary attack must fail during summoning");
        float before = target.getHealth();
        helper.assertTrue(ConstellationTreasuryCombat.strike(sword, owner, target, owner, 8, false), "Arsenal hit bypasses summoning immunity");
        float first = target.getHealth();
        helper.assertTrue(ConstellationTreasuryCombat.strike(sword, owner, target, owner, 8, false), "Second hit in the same tick bypasses damage cooldown");
        helper.assertTrue(first < before && target.getHealth() < first && target.getInvulnerableTicks() == 200, "Both hits deal damage without ending the summoning phase");
        target.discard();
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury")
    public static void nonPickableZeroSizeTargetAndWall(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        Vec3 start = Vec3.atCenterOf(helper.absolutePos(new BlockPos(1, 3, 1)));
        Pig target = new Pig(EntityType.PIG, helper.getLevel()) {
            @Override public boolean isPickable() { return false; }
            @Override public boolean isAttackable() { return false; }
        };
        target.setNoAi(true);
        target.setPos(start.add(0, 0, 2));
        target.setBoundingBox(new AABB(target.position(), target.position()));
        helper.getLevel().addFreshEntity(target);
        helper.assertTrue(ConstellationTreasuryCombat.findTarget(helper.getLevel(), owner, start, start.add(0, 0, 3), 0.25) == target,
                "Custom targeting finds a non-pickable, non-attackable, zero-size living entity");
        helper.getLevel().setBlockAndUpdate(BlockPos.containing(start.add(0, 0, 1)), Blocks.STONE.defaultBlockState());
        helper.assertTrue(ConstellationTreasuryCombat.findTarget(helper.getLevel(), owner, start, start.add(0, 0, 3), 0.25) == null,
                "Direct attacks still respect walls");
        target.discard();
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury", timeoutTicks = 120)
    public static void salvoWearsRealWeaponsOnceAndSurvivesSave(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss target = wither(helper);
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        var inventory = ConstellationTreasuryItem.inventory(sword);
        for (int slot = 0; slot < ConstellationTreasuryItem.SLOTS - 1; slot++) inventory.insertItem(slot, new ItemStack(Items.IRON_SWORD), false);
        ItemStack fragile = new ItemStack(Items.GOLDEN_SWORD);
        fragile.setDamageValue(fragile.getMaxDamage() - 1);
        inventory.insertItem(ConstellationTreasuryItem.SLOTS - 1, fragile, false);
        ConstellationTreasuryCombat.startSalvo(sword, owner, target);
        helper.assertTrue(helper.getLevel().getEntitiesOfClass(SummonedArsenalWeapon.class, target.getBoundingBox().inflate(128),
                entity -> entity.getTargetId() == target.getId()).size() == 36, "All thirty-six occupied slots summon a weapon");
        // Simulate persistence between activation and the first strike.
        ItemStack restored = ItemStack.of(sword.save(new CompoundTag()));
        float before = target.getHealth();
        long salvoStart = helper.getLevel().getGameTime();
        int lastImpact = ConstellationTreasuryCombat.impactTick(ConstellationTreasuryItem.SLOTS - 1);
        for (int tick = 1; tick <= lastImpact + 3; tick++) helper.runAfterDelay(tick, () -> {
            ConstellationTreasuryCombat.tickSalvo(restored, owner);
            ConstellationTreasuryCombat.tickSalvo(restored, owner); // Duplicate tick must never double-charge.
            if (helper.getLevel().getGameTime() - salvoStart == ConstellationTreasuryCombat.FIRST_IMPACT) {
                var pair = ConstellationTreasuryItem.inventory(restored);
                helper.assertTrue(pair.getStackInSlot(0).getDamageValue() == 1 && pair.getStackInSlot(1).getDamageValue() == 1
                        && pair.getStackInSlot(2).getDamageValue() == 0, "Exactly the first pair hits together before the next pair");
            }
        });
        helper.runAfterDelay(lastImpact + 5, () -> {
            var after = ConstellationTreasuryItem.inventory(restored);
            for (int slot = 0; slot < ConstellationTreasuryItem.SLOTS - 1; slot++)
                helper.assertTrue(after.getStackInSlot(slot).getDamageValue() == 1, "Every stored iron sword fires and wears exactly once");
            helper.assertTrue(after.getStackInSlot(ConstellationTreasuryItem.SLOTS - 1).isEmpty(), "The final weapon fires and breaks without affecting other slots");
            helper.assertTrue(target.getHealth() < before && !ConstellationTreasuryCombat.isActive(restored), "Salvo damages target and releases inventory lock");
            target.discard();
            helper.succeed();
        });
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury", timeoutTicks = 120)
    public static void bowShotBypassesWitherAndPreservesContents(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss target = wither(helper);
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        ConstellationTreasuryItem.inventory(sword).insertItem(0, new ItemStack(Items.IRON_SWORD), false);
        ThrownSwordEntity shot = new ThrownSwordEntity(helper.getLevel(), owner, sword, 0, 0, 1);
        Vec3 center = target.position().add(0, target.getBbHeight() * 0.5, 0);
        shot.setPos(center.add(0, 0, -1.5));
        shot.setDeltaMovement(0, 0, 2);
        float before = target.getHealth();
        // Tick manually once for impact, then tick manually at each real world tick.
        shot.tick();
        helper.assertTrue(target.getHealth() < before && ConstellationTreasuryCombat.isActive(shot.getSwordStack()), "Thrown arsenal sword penetrates summoning immunity and starts a salvo");
        helper.assertTrue(target.getLastDamageSource() != null && target.getLastDamageSource().is(DamageTypes.TRIDENT)
                && target.getLastDamageSource().is(DamageTypeTags.IS_PROJECTILE), "Bow hit retains the original projectile damage type and tag");
        int lastImpact = ConstellationTreasuryCombat.impactTick(ConstellationTreasuryItem.SLOTS - 1);
        for (int tick = 1; tick <= lastImpact + 3; tick++) helper.runAfterDelay(tick, () -> { if (!shot.isRemoved()) shot.tick(); });
        helper.runAfterDelay(lastImpact + 5, () -> {
            helper.assertTrue(shot.isRemoved(), "Projectile completes the salvo then drops/returns the real sword");
            var drops = helper.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, target.getBoundingBox().inflate(5),
                    e -> e.getItem().is(itemlist.CONSTELLATION_TREASURY.get()));
            helper.assertTrue(drops.size() == 1, "Exactly one arsenal sword is dropped");
            helper.assertTrue(ConstellationTreasuryItem.inventory(drops.get(0).getItem()).getStackInSlot(0).getDamageValue() == 1,
                    "Dropped sword keeps the actual weapon and its updated durability");
            drops.forEach(net.minecraft.world.entity.Entity::discard);
            target.discard();
            helper.succeed();
        });
    }

    @GameTest(template = "reactor_test", batch = "constellation_treasury", timeoutTicks = 80)
    public static void killingHitStartsSalvoOnNearbyEnemy(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss first = wither(helper);
        WitherBoss next = wither(helper);
        next.setPos(first.position().add(3, 0, 0));
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        owner.getInventory().setItem(0, sword);
        ConstellationTreasuryItem.inventory(sword).insertItem(0, new ItemStack(Items.IRON_SWORD), false);
        for (int tick = 0; tick < 30; tick++) owner.tick();
        owner.setPos(first.position().add(0, 0, -2));
        owner.setYRot(0);
        owner.setXRot(0);
        first.setHealth(1);
        ConstellationTreasuryCombat.attack(owner);
        helper.assertTrue(!first.isAlive() && ConstellationTreasuryCombat.isActive(sword), "Lethal direct attack still starts the salvo");
        var projections = helper.getLevel().getEntitiesOfClass(SummonedArsenalWeapon.class, next.getBoundingBox().inflate(32),
                e -> e.getTargetId() == next.getId());
        helper.assertTrue(projections.size() == 1, "The summon targets the nearby enemy after the initial kill");
        projections.forEach(net.minecraft.world.entity.Entity::discard);
        first.discard();
        next.discard();
        helper.succeed();
    }

    @GameTest(template = "reactor_test", batch = "constellation_retarget", timeoutTicks = 80)
    public static void lethalFirstOfPairRetargetsRemainingWeapons(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        WitherBoss first = wither(helper);
        WitherBoss next = wither(helper);
        next.setPos(first.position().add(3, 0, 0));
        Pig passive = new Pig(EntityType.PIG, helper.getLevel());
        passive.setPos(first.position().add(1, 0, 0));
        passive.setNoAi(true);
        passive.setNoGravity(true);
        helper.getLevel().addFreshEntity(passive);
        ItemStack sword = new ItemStack(itemlist.CONSTELLATION_TREASURY.get());
        for (int slot = 0; slot < 4; slot++) ConstellationTreasuryItem.inventory(sword).insertItem(slot, new ItemStack(Items.IRON_SWORD), false);
        ConstellationTreasuryCombat.startSalvo(sword, owner, first);
        var projections = helper.getLevel().getEntitiesOfClass(SummonedArsenalWeapon.class, first.getBoundingBox().inflate(32),
                e -> e.getTargetId() == first.getId());
        for (int tick = 1; tick <= ConstellationTreasuryCombat.FIRST_IMPACT; tick++) helper.runAfterDelay(tick, () -> {
            first.setHealth(1);
            ConstellationTreasuryCombat.tickSalvo(sword, owner);
            ConstellationTreasuryCombat.tickSalvo(sword, owner);
        });
        helper.runAfterDelay(ConstellationTreasuryCombat.FIRST_IMPACT + 1, () -> {
            var inventory = ConstellationTreasuryItem.inventory(sword);
            helper.assertTrue(!first.isAlive() && next.getHealth() < next.getMaxHealth(), "Second weapon in the lethal pair damages the next enemy");
            helper.assertTrue(inventory.getStackInSlot(0).getDamageValue() == 1 && inventory.getStackInSlot(1).getDamageValue() == 1
                    && inventory.getStackInSlot(2).getDamageValue() == 0, "Retargeting preserves the schedule and charges each hit once");
            helper.assertTrue(projections.stream().filter(e -> !e.isRemoved()).allMatch(e -> e.getTargetId() == next.getId()),
                    "Waiting projections follow the replacement target");
            helper.assertTrue(passive.getHealth() == passive.getMaxHealth(), "A nearer passive animal is not selected");
            next.discard();
            ConstellationTreasuryCombat.tickSalvo(sword, owner);
            helper.assertTrue(!ConstellationTreasuryCombat.isActive(sword) && ConstellationTreasuryItem.inventory(sword).getStackInSlot(2).getDamageValue() == 0,
                    "No remaining enemy ends the salvo without wearing unused weapons");
            var remaining = projections.stream().filter(e -> !e.isRemoved()).toList();
            helper.assertTrue(remaining.size() == 2 && remaining.stream().allMatch(SummonedArsenalWeapon::isDismissing),
                    "Unused weapons begin dismissal instead of disappearing immediately");
            Vec3 exitStart = remaining.get(0).renderPosition(0);
            Vec3 exitDirection = remaining.get(0).facingDirection(0);
            float exitOpacity = remaining.get(0).getOpacity(0);
            first.discard();
            passive.discard();
            helper.runAfterDelay(SummonedArsenalWeapon.DISMISS_TICKS / 2, () -> {
                var projection = remaining.get(0);
                helper.assertTrue(!projection.isRemoved() && projection.getOpacity(0) > 0 && projection.getOpacity(0) < exitOpacity,
                        "Dismissal fades gradually and survives the original impact deadline");
                helper.assertTrue(projection.renderPosition(0).subtract(exitStart).dot(exitDirection) < 0,
                        "The weapon retreats away from the former target even after its removal");
                helper.assertTrue(projection.facingDirection(0).equals(exitDirection), "Dismissal preserves orientation past the former impact deadline");
                helper.assertTrue(ConstellationTreasuryItem.inventory(sword).getStackInSlot(2).getDamageValue() == 0,
                        "The dismissal animation never consumes weapon durability");
            });
            helper.runAfterDelay(SummonedArsenalWeapon.DISMISS_TICKS + 1, () -> {
                helper.assertTrue(remaining.stream().allMatch(net.minecraft.world.entity.Entity::isRemoved), "Dismissal removes projections after fading");
                helper.succeed();
            });
        });
    }

    @GameTest(template = "reactor_test", batch = "arsenal_animation", timeoutTicks = 80)
    public static void retargetKeepsWorldAnchorAndContinuousFlight(GameTestHelper helper) {
        WitherBoss first = wither(helper);
        WitherBoss next = wither(helper);
        next.setPos(first.position().add(4, 0, 0));
        var weapon = new SummonedArsenalWeapon(helper.getLevel(), first, new ItemStack(Items.IRON_SWORD), new Vec3(3, 4, 2), 40);
        Vec3 anchor = weapon.getSpawnAnchor();
        helper.runAfterDelay(8, () -> {
            Vec3 before = weapon.renderPosition(0);
            first.setPos(first.position().add(0, 0, 2));
            helper.assertTrue(weapon.renderPosition(0).distanceToSqr(before) < 1.0E-10, "Target movement must not move waiting weapons");
            weapon.setTarget(next);
            helper.assertTrue(weapon.renderPosition(0).distanceToSqr(before) < 1.0E-10 && weapon.getSpawnAnchor().equals(anchor),
                    "Retargeting preserves the original world-space summon anchor");
            Vec3 towardNext = next.position().add(0, next.getBbHeight() * 0.5, 0).subtract(before).normalize();
            helper.assertTrue(weapon.facingDirection(0).dot(towardNext) > 0.999, "Waiting weapon faces its new destination");
        });
        helper.runAfterDelay(34, () -> {
            Vec3 before = weapon.renderPosition(0);
            weapon.setTarget(first);
            helper.assertTrue(weapon.renderPosition(0).distanceToSqr(before) < 1.0E-10, "Retargeting during pullback does not teleport");
        });
        helper.runAfterDelay(37, () -> {
            Vec3 before = weapon.renderPosition(0);
            weapon.setTarget(next);
            helper.assertTrue(weapon.renderPosition(0).distanceToSqr(before) < 1.0E-10, "In-flight retargeting starts at the current flight position");
            helper.assertTrue(weapon.renderPosition(0.5F).distanceToSqr(before) > 0, "The redirected weapon continues flying");
        });
        helper.runAfterDelay(40, () -> {
            helper.assertTrue(weapon.renderPosition(0).distanceToSqr(next.position().add(0, next.getBbHeight() * 0.5, 0)) < 1.0E-10,
                    "The redirected flight reaches the new target at the original impact deadline");
            first.discard();
            next.discard();
            helper.succeed();
        });
    }

    private static WitherBoss wither(GameTestHelper helper) {
        WitherBoss target = new WitherBoss(EntityType.WITHER, helper.getLevel());
        target.setPos(Vec3.atCenterOf(helper.absolutePos(new BlockPos(2, 3, 2))));
        target.setNoAi(true);
        target.setNoGravity(true);
        target.setInvulnerableTicks(200);
        helper.getLevel().addFreshEntity(target);
        return target;
    }
}
