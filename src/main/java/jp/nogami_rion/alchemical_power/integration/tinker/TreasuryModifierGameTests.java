package jp.nogami_rion.alchemical_power.integration.tinker;

import jp.nogami_rion.alchemical_power.integration.tinker.modifier.ConstellationTreasuryModifier;
import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.screen.ConstellationTreasuryMenu;
import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolBuildHandler;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.tools.TinkerTools;

/** Registered only by the optional TiC bootstrap, never loaded without TiC. */
@PrefixGameTestTemplate(false)
public class TreasuryModifierGameTests {
    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_arsenal_return", timeoutTicks = 100)
    public static void arsenalLaunchReturnsModifiedTreasuryAfterSalvo(GameTestHelper helper) {
        checkArsenalLaunch(helper, 3);
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_arsenal", timeoutTicks = 100)
    public static void arsenalLaunchDropsModifiedTreasuryAfterSalvo(GameTestHelper helper) {
        checkArsenalLaunch(helper, 0);
    }

    private static void checkArsenalLaunch(GameTestHelper helper, int loyalty) {
        Player owner = helper.makeMockSurvivalPlayer();
        var target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        target.setNoAi(true);
        target.setNoGravity(true);
        target.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(1000);
        target.setHealth(1000);
        owner.setPos(target.position().add(0, 0, -3));
        ItemStack bow = new ItemStack(jp.nogami_rion.alchemical_power.init.itemlist.ARSENAL_BOW.get());
        if (loyalty > 0) bow.enchant(Enchantments.LOYALTY, loyalty);
        owner.setItemInHand(InteractionHand.MAIN_HAND, bow);
        ItemStack equipment = treasury(TinkerTools.scythe.get(), helper);
        ConstellationTreasuryItem.inventory(equipment).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        owner.getInventory().setItem(9, equipment);
        bow.getItem().releaseUsing(bow, helper.getLevel(), owner, bow.getUseDuration() - 20);
        var shots = helper.getLevel().getEntitiesOfClass(jp.nogami_rion.alchemical_power.entity.ThrownSwordEntity.class,
                owner.getBoundingBox().inflate(5), e -> e.getOwner() == owner);
        helper.assertTrue(shots.size() == 1 && owner.getInventory().getItem(9).isEmpty(), "Bow transfers equipment into exactly one projectile");
        var shot = shots.get(0);
        shot.setPos(target.position().add(0, 0.5, -1.5));
        shot.setDeltaMovement(0, 0, 2);
        shot.tick();
        helper.assertTrue(ConstellationTreasuryCombat.isActive(shot.getSwordStack()), "Projectile-held modifier starts its salvo while owner holds bow");
        helper.assertTrue(!ConstellationTreasuryCombat.isActive(bow), "Bow does not become the storage source");
        helper.runAfterDelay(10, () -> {
            helper.assertTrue(!shot.isRemoved() && shot.getDeltaMovement().lengthSqr() == 0, "Return/drop waits for salvo");
            // Reload the same entity's saved state to exercise persistent projectile storage.
            CompoundTag saved = new CompoundTag();
            shot.saveWithoutId(saved);
            shot.load(saved);
            // Mock players are not in the level's UUID lookup used by Projectile after loading.
            shot.setOwner(owner);
        });
        helper.runAfterDelay(70, () -> {
            // Complete the return deterministically without depending on mock-player movement.
            if (loyalty > 0 && !shot.isRemoved()) { shot.setPos(owner.getEyePosition()); shot.tick(); }
            helper.assertTrue(shot.isRemoved(), "Projectile returns or drops after salvo");
            ItemStack result = ItemStack.EMPTY;
            if (loyalty > 0) {
                for (int slot = 0; slot < owner.getInventory().getContainerSize(); slot++)
                    if (ConstellationTreasuryItem.isTreasury(owner.getInventory().getItem(slot))) {
                        helper.assertTrue(result.isEmpty(), "Only one equipment stack returns");
                        result = owner.getInventory().getItem(slot);
                    }
            } else {
                var drops = helper.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class,
                        target.getBoundingBox().inflate(8), e -> ConstellationTreasuryItem.isTreasury(e.getItem()));
                helper.assertTrue(drops.size() == 1, "Exactly one treasury drops");
                result = drops.get(0).getItem().copy();
                drops.forEach(net.minecraft.world.entity.Entity::discard);
            }
            helper.assertTrue(!result.isEmpty() && !ConstellationTreasuryCombat.isActive(result), "Returned equipment retains modifier and unlocks storage");
            helper.assertTrue(ConstellationTreasuryItem.inventory(result).getStackInSlot(0).getDamageValue() == 1,
                    "Stored weapon fires once and its updated durability survives reload and return");
            target.discard();
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury")
    public static void sharedStorageKeySelectsHeldTreasuries(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        helper.assertTrue(jp.nogami_rion.alchemical_power.util.ConstellationTreasuryStorage.heldSlot(owner) == -1, "Empty hands do not open storage");
        owner.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(jp.nogami_rion.alchemical_power.init.itemlist.CONSTELLATION_TREASURY.get()));
        helper.assertTrue(jp.nogami_rion.alchemical_power.util.ConstellationTreasuryStorage.heldSlot(owner) == 40, "Original treasury opens from offhand");
        owner.getInventory().selected = 3;
        owner.setItemInHand(InteractionHand.MAIN_HAND, treasury(TinkerTools.longbow.get(), helper));
        helper.assertTrue(jp.nogami_rion.alchemical_power.util.ConstellationTreasuryStorage.heldSlot(owner) == 3, "Modifier bow takes mainhand priority independently of right-click");
        owner.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
        helper.assertTrue(jp.nogami_rion.alchemical_power.util.ConstellationTreasuryStorage.heldSlot(owner) == 40, "Ordinary bow permits offhand treasury selection");
        helper.succeed();
    }

    private static ConstellationTreasuryModifier modifier() {
        return (ConstellationTreasuryModifier) TinkersIntegration.CONSTELLATION_TREASURY_MODIFIER.get();
    }

    private static ModifierEntry entry() { return new ModifierEntry(modifier(), 1); }

    private static ItemStack treasury(IModifiable item, GameTestHelper helper) {
        ItemStack stack = ToolBuildHandler.buildItemRandomMaterials(item, net.minecraft.util.RandomSource.create(1234));
        ToolStack.from(stack).addModifier(modifier().getId(), 1);
        helper.assertTrue(ConstellationTreasuryItem.isTreasury(stack), "Tool has registered treasury modifier");
        return stack;
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_scythe", timeoutTicks = 90)
    public static void singularityScytheWithMixedStorageSummons(GameTestHelper helper) {
        var target = helper.spawn(jp.nogami_rion.alchemical_power.init.entitylist.ALCHETREE_MYSTERIOUS_SCARECROW.get(), new BlockPos(2, 2, 2));
        target.setHealth(2);
        checkMixedScytheSalvo(helper, target, false);
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_scythe", timeoutTicks = 90)
    public static void singularityScytheSummonsOnMmmTargetDummy(GameTestHelper helper) {
        if (!net.minecraftforge.fml.ModList.get().isLoaded("dummmmmmy")) {
            helper.succeed();
            return;
        }
        var type = net.minecraftforge.registries.ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("dummmmmmy", "target_dummy"));
        helper.assertTrue(type != null, "MmmMmmMmm target dummy is registered");
        helper.setBlock(new BlockPos(2, 1, 2), net.minecraft.world.level.block.Blocks.STONE);
        var target = (net.minecraft.world.entity.LivingEntity) helper.spawn(type, new BlockPos(2, 2, 2));
        checkMixedScytheSalvo(helper, target, true);
    }

    private static void checkMixedScytheSalvo(GameTestHelper helper, net.minecraft.world.entity.LivingEntity target, boolean infiniteHealth) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = ToolBuildHandler.createSingleMaterial(TinkerTools.scythe.get(),
                slimeknights.tconstruct.library.materials.definition.MaterialVariant.of(
                        new slimeknights.tconstruct.library.materials.definition.MaterialId("alchemical_power:singularity"), ""));
        ToolStack.from(stack).addModifier(modifier().getId(), 1);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        var inventory = ConstellationTreasuryItem.inventory(stack);
        inventory.insertItem(0, ToolBuildHandler.buildItemRandomMaterials(TinkerTools.sword.get(),
                net.minecraft.util.RandomSource.create(1234)), false);
        int slot = 1;
        for (String id : java.util.List.of("projecte:rm_sword", "projecte:dm_sword", "mysticalagriculture:awakened_supremium_sword")) {
            var key = new ResourceLocation(id);
            if (net.minecraftforge.registries.ForgeRegistries.ITEMS.containsKey(key)) {
                ItemStack weapon = new ItemStack(net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(key));
                helper.assertTrue(inventory.insertItem(slot++, weapon, false).isEmpty(), "Storage accepts " + id);
            }
        }
        int woodenSlot = slot++;
        inventory.insertItem(woodenSlot, new ItemStack(Items.WOODEN_SWORD), false);
        float healthBefore = target.getHealth();
        stack.getItem().onLeftClickEntity(stack, owner, target);
        if (infiniteHealth) helper.assertTrue(target.getHealth() == healthBefore, "MmmMmmMmm records the hit without reducing health");
        helper.assertTrue(ConstellationTreasuryCombat.isActive(stack), "Singularity scythe starts a salvo with mixed modded weapons");
        int count = slot;
        helper.assertTrue(helper.getLevel().getEntitiesOfClass(jp.nogami_rion.alchemical_power.entity.SummonedArsenalWeapon.class,
                target.getBoundingBox().inflate(128), entity -> entity.getTargetId() == target.getId()).size() == count,
                "Every stored modded weapon has a visible projection");
        long startTime = helper.getLevel().getGameTime();
        net.minecraft.world.damagesource.CombatEntry[] finalHit = {null};
        for (int tick = 1; tick <= 58; tick++) helper.runAfterDelay(tick, () -> {
            helper.assertTrue(target.isAlive(), "Mixed salvo target disappeared: " + target.getRemovalReason()
                    + ", position=" + target.position() + ", support=" + helper.getLevel().getBlockState(target.getOnPos()));
            stack.getItem().inventoryTick(stack, helper.getLevel(), owner, 0, true);
            long age = helper.getLevel().getGameTime() - startTime;
            if (infiniteHealth && age == ConstellationTreasuryCombat.impactTick(woodenSlot))
                finalHit[0] = dummyLastEntry(target);
        });
        helper.runAfterDelay(59, () -> {
            helper.assertTrue(!ConstellationTreasuryCombat.isActive(stack), "Mixed-weapon salvo completes");
            helper.assertTrue(!ConstellationTreasuryItem.inventory(stack).getStackInSlot(0).isEmpty(), "TiC weapon remains stored");
            // MmmMmmMmm may suppress weapon wear, so verify its actual combat record instead.
            if (infiniteHealth) {
                helper.assertTrue(finalHit[0] != null && finalHit[0].source() instanceof jp.nogami_rion.alchemical_power.util.ArsenalDamageSource
                        && finalHit[0].damage() > 0, "MmmMmmMmm records damage from the last summoned weapon");
            } else {
                helper.assertTrue(ConstellationTreasuryItem.inventory(stack).getStackInSlot(woodenSlot).getDamageValue() == 1,
                        "Successful follow-up consumes weapon durability exactly once");
            }
            target.discard();
            helper.succeed();
        });
    }

    private static net.minecraft.world.damagesource.CombatEntry dummyLastEntry(net.minecraft.world.entity.LivingEntity target) {
        try {
            return (net.minecraft.world.damagesource.CombatEntry) target.getClass().getMethod("getLastEntry").invoke(target);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Cannot inspect the optional target dummy's damage record", e);
        }
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_real", timeoutTicks = 90)
    public static void realMeleeDispatchAndInventoryTick(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        target.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(1000);
        target.setHealth(1000);
        target.setNoAi(true);
        stack.getItem().onLeftClickEntity(stack, owner, target);
        helper.assertTrue(target.getHealth() < 1000, "Actual TiC melee attack deals damage");
        helper.assertTrue(ConstellationTreasuryCombat.isActive(stack), "Actual TiC attack dispatch starts the salvo");
        for (int tick = 1; tick <= 58; tick++) helper.runAfterDelay(tick,
                () -> stack.getItem().inventoryTick(stack, helper.getLevel(), owner, 0, true));
        helper.runAfterDelay(59, () -> {
            helper.assertTrue(ConstellationTreasuryItem.inventory(stack).getStackInSlot(0).getDamageValue() == 1,
                    "Actual TiC inventory tick drives the follow-up");
            target.discard();
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_real")
    public static void rejectedMeleeHitDoesNotSummon(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        target.setInvulnerable(true);
        stack.getItem().onLeftClickEntity(stack, owner, target);
        helper.assertTrue(!ConstellationTreasuryCombat.isActive(stack), "Rejected hits do not trigger follow-ups");
        target.discard();
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_real")
    public static void absorbedMeleeHitStillSummons(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        target.setAbsorptionAmount(1000);
        stack.getItem().onLeftClickEntity(stack, owner, target);
        helper.assertTrue(target.getAbsorptionAmount() < 1000 && target.getHealth() == target.getMaxHealth(),
                "The real hit was accepted and absorbed without reducing health");
        helper.assertTrue(ConstellationTreasuryCombat.isActive(stack), "An absorbed successful hit still opens the treasury");
        target.discard();
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_real")
    public static void scarecrowHealingDuringHitStillSummons(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        var target = helper.spawn(jp.nogami_rion.alchemical_power.init.entitylist.ALCHETREE_MYSTERIOUS_SCARECROW.get(), new BlockPos(2, 2, 2));
        target.setHealth(2);
        stack.getItem().onLeftClickEntity(stack, owner, target);
        helper.assertTrue(target.getHealth() > 2, "Scarecrow heals during its hurt method");
        helper.assertTrue(ConstellationTreasuryCombat.isActive(stack), "Accepted hit still summons when health increases");
        target.discard();
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury_real")
    public static void realBowLaunchAndArrowImpact(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.longbow.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        owner.getInventory().setItem(9, new ItemStack(Items.ARROW, 16));
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        target.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH).setBaseValue(1000);
        target.setHealth(1000);
        target.setNoAi(true);
        owner.setPos(target.getX(), target.getY(), target.getZ() - 3);
        stack.getItem().use(helper.getLevel(), owner, InteractionHand.MAIN_HAND);
        stack.getItem().releaseUsing(stack, helper.getLevel(), owner, stack.getUseDuration() - 40);
        var arrows = helper.getLevel().getEntitiesOfClass(net.minecraft.world.entity.projectile.AbstractArrow.class,
                target.getBoundingBox().inflate(8), arrow -> arrow.getOwner() == owner);
        helper.assertTrue(arrows.size() == 1, "Actual TiC bow shoots one arrow");
        var arrow = arrows.get(0);
        arrow.setPos(target.getX(), target.getY() + 0.4, target.getZ() - 2);
        arrow.setNoGravity(true);
        arrow.setDeltaMovement(0, 0, 1);
        owner.getInventory().setItem(10, stack);
        owner.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        for (int tick = 0; tick < 3 && arrow.isAlive(); tick++) arrow.tick();
        helper.assertTrue(target.getHealth() < 1000, "Actual arrow collision hits the target");
        helper.assertTrue(ConstellationTreasuryCombat.isActive(stack), "Actual arrow collision finds the original bow after switching");
        arrow.discard();
        target.discard();
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury")
    public static void storageSurvivesRebuildAndRemovalIsSafe(GameTestHelper helper) {
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        ItemStack weapon = new ItemStack(Items.DIAMOND_SWORD);
        weapon.setDamageValue(17);
        weapon.enchant(Enchantments.SHARPNESS, 3);
        ConstellationTreasuryItem.inventory(stack).insertItem(35, weapon, false);
        ToolStack.from(stack).rebuildStats();
        ItemStack restored = ItemStack.of(stack.save(new CompoundTag()));
        helper.assertTrue(ItemStack.matches(ConstellationTreasuryItem.inventory(restored).getStackInSlot(35), weapon),
                "Slot 36, durability and enchantments survive tool rebuild and save/load");
        helper.assertTrue(!ConstellationTreasuryItem.accepts(stack), "Treasuries cannot be nested inside either storage");
        helper.assertTrue(modifier().onRemoved(ToolStack.from(restored), modifier()) != null, "Nonempty storage blocks removal");
        ConstellationTreasuryItem.inventory(restored).extractItem(35, 1, false);
        helper.assertTrue(modifier().onRemoved(ToolStack.from(restored), modifier()) == null, "Empty storage allows removal");
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury", timeoutTicks = 90)
    public static void meleeSalvoUsesRealInventoryOnce(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        target.setNoAi(true);
        var context = new ToolAttackContext(owner, owner, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND,
                target, target, false, 1, false);
        modifier().afterMeleeHit(ToolStack.from(stack), entry(), context, 1);
        helper.assertTrue(ConstellationTreasuryCombat.isActive(stack), "Melee hit starts a salvo");
        ConstellationTreasuryMenu menu = new ConstellationTreasuryMenu(1, owner.getInventory(), owner.getInventory().selected);
        helper.assertTrue(!menu.stillValid(owner), "Salvo locks the shared menu");
        for (int tick = 1; tick <= 58; tick++) helper.runAfterDelay(tick, () -> {
            modifier().onInventoryTick(ToolStack.from(stack), entry(), helper.getLevel(), owner, 0, true, true, stack);
            modifier().onInventoryTick(ToolStack.from(stack), entry(), helper.getLevel(), owner, 0, true, true, stack);
        });
        helper.runAfterDelay(59, () -> {
            helper.assertTrue(target.getHealth() < target.getMaxHealth(), "Follow-up deals damage");
            helper.assertTrue(ConstellationTreasuryItem.inventory(stack).getStackInSlot(0).getDamageValue() == 1,
                    "Repeated ticks consume real weapon durability exactly once");
            helper.assertTrue(!ConstellationTreasuryCombat.isActive(stack) && menu.stillValid(owner), "Salvo releases menu lock");
            target.discard();
            helper.succeed();
        });
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury")
    public static void projectileFindsOriginalBowAfterSwitchAndDeduplicatesHits(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack original = treasury(TinkerTools.longbow.get(), helper);
        ItemStack other = treasury(TinkerTools.longbow.get(), helper);
        owner.getInventory().setItem(10, original);
        owner.setItemInHand(InteractionHand.MAIN_HAND, other);
        ConstellationTreasuryItem.inventory(original).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        ConstellationTreasuryItem.inventory(other).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Arrow arrow = new Arrow(helper.getLevel(), owner);
        ModDataNBT projectileData = new ModDataNBT();
        modifier().onProjectileLaunch(ToolStack.from(original), entry(), owner, arrow, arrow, projectileData, true);
        projectileData = ModDataNBT.readFromNBT(projectileData.getCopy());
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        for (int i = 0; i < 2; i++) modifier().onProjectileHitEntity(ToolStack.from(original).getModifiers(), projectileData,
                entry(), arrow, new EntityHitResult(target), owner, target);
        helper.assertTrue(ConstellationTreasuryCombat.isActive(original) && !ConstellationTreasuryCombat.isActive(other),
                "Only the original bow fires, even after changing slots and saving projectile data");
        helper.assertTrue(ConstellationTreasuryItem.data(original, "ArsenalSalvo").getList("Volleys", Tag.TAG_COMPOUND).size() == 1,
                "Repeated collision callback cannot duplicate a salvo");
        target.discard();
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury")
    public static void missingSourceAndSyntheticHitsDoNotSummon(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        ItemStack stack = treasury(TinkerTools.sword.get(), helper);
        owner.setItemInHand(InteractionHand.MAIN_HAND, stack);
        ConstellationTreasuryItem.inventory(stack).insertItem(0, new ItemStack(Items.WOODEN_SWORD), false);
        Pig target = helper.spawn(EntityType.PIG, new BlockPos(2, 2, 2));
        ArsenalTinkerCombat.afterMeleeHit(stack, owner, target, 1);
        helper.assertTrue(!ConstellationTreasuryCombat.isActive(stack), "Synthetic arsenal hit cannot recursively activate the treasury");
        Arrow arrow = new Arrow(helper.getLevel(), owner);
        ModDataNBT data = new ModDataNBT();
        modifier().onProjectileLaunch(ToolStack.from(stack), entry(), owner, arrow, arrow, data, true);
        owner.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        modifier().onProjectileHitEntity(ToolStack.from(stack).getModifiers(), data, entry(), arrow,
                new EntityHitResult(target), owner, target);
        helper.assertTrue(!ConstellationTreasuryCombat.isActive(stack), "A dropped source tool cannot attack from an unowned copy");
        target.discard();
        helper.succeed();
    }

    @GameTest(templateNamespace = "alchemical_power", template = "reactor_test", batch = "tic_treasury")
    public static void modifierRecipesLoad(GameTestHelper helper) {
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(new ResourceLocation("alchemical_power", "tools/modifiers/constellation_treasury")).isPresent(),
                "Treasury modifier recipe loads with TiC");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(new ResourceLocation("alchemical_power", "tools/modifiers/akashic_records")).isPresent(),
                "Updated Akashic Records recipe loads");
        var recipe = (slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipe) helper.getLevel().getRecipeManager()
                .byKey(new ResourceLocation("alchemical_power", "tools/modifiers/constellation_treasury")).orElseThrow();
        helper.assertTrue(recipe.getLevel().max() == 1 && recipe.getSlots().count() == 1
                && recipe.getSlots().type() == slimeknights.tconstruct.library.tools.SlotType.ABILITY,
                "Treasury caps at level one and costs one ability slot");
        helper.assertTrue(recipe.getDisplayItems(0).size() == 1
                && recipe.getDisplayItems(0).get(0).is(jp.nogami_rion.alchemical_power.init.itemlist.SINGULARITY_INGOT.get())
                && recipe.getDisplayItems(0).get(0).getCount() == 1, "Treasury costs one singularity ingot");
        var akashic = (slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipe) helper.getLevel().getRecipeManager()
                .byKey(new ResourceLocation("alchemical_power", "tools/modifiers/akashic_records")).orElseThrow();
        helper.assertTrue(akashic.getDisplayItems(0).size() == 1
                && akashic.getDisplayItems(0).get(0).is(jp.nogami_rion.alchemical_power.init.itemlist.SINGULARITY.get()),
                "Akashic Records now costs a singularity instead of an ingot");
        helper.succeed();
    }
}
