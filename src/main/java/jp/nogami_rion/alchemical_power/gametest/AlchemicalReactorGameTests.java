package jp.nogami_rion.alchemical_power.gametest;

import io.netty.buffer.Unpooled;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalReactorRecipe;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.util.BlockEntityStateTransfer;
import jp.nogami_rion.alchemical_power.util.ReactorRecipeTransfer;
import jp.nogami_rion.alchemical_power.screen.AlchemicalReactorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.ItemStackHandler;
import java.util.List;
import static jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity.*;

/** Runs only in Forge's GameTest environment; does not add any gameplay recipes. */
@GameTestHolder("alchemical_power")
@PrefixGameTestTemplate(false)
public class AlchemicalReactorGameTests {
    @GameTest(template = "reactor_test")
    public static void longFluidJsonNetworkPersistenceAndResume(GameTestHelper helper) {
        // Use a populated vanilla ingredient so this test also runs without optional T7 material mods.
        var json = com.google.gson.JsonParser.parseString("""
                {"ingredients":[{"ingredient":{"item":"minecraft:netherite_ingot"},"count":36504},
                  {"ingredient":{"item":"minecraft:nether_star"},"count":1}],
                 "sample":{"item":"alchemical_power:faith_crystal"},
                 "result":{"item":"alchemical_power:faith_crystal","count":1},
                 "fluid":{"fluid":"alchemical_power:liquid_panakeia","amount":86611434624},
                 "tool_tier":2,"time":28302,"energy":397109303}
                """).getAsJsonObject();
        var serializer = new AlchemicalReactorRecipe.Serializer();
        var recipe = serializer.fromJson(new ResourceLocation("alchemical_power", "long_fluid_test"), json);
        helper.assertTrue(recipe.fluidAmount() == 86_611_434_624L, "JSON retains fluid above int range");
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            serializer.toNetwork(buffer, recipe);
            helper.assertTrue(serializer.fromNetwork(recipe.getId(), buffer).fluidAmount() == recipe.fluidAmount(),
                    "Network retains the full liquid cost");
        } finally { buffer.release(); }
        var be = machine(helper);
        var base = recipe(helper);
        supply(be, base, base.energy()); tick(be, 1);
        var saved = new net.minecraft.nbt.CompoundTag(); be.saveToItemTag(saved);
        // Restore a valid in-flight operation past the int boundary without depending on optional tags.
        int progress = recipe.time() / 2;
        long spent = jp.nogami_rion.alchemical_power.util.ReactorProgressCost.cumulative(
                recipe.fluidAmount(), progress, recipe.time());
        saved.putInt("Duration", recipe.time()); saved.putInt("Progress", progress);
        saved.putLong("TotalFluid", recipe.fluidAmount()); saved.putLong("FluidSpent", spent);
        saved.putLong("TotalEnergy", recipe.energy()); saved.putLong("EnergySpent", 0);
        saved.put("PendingResult", recipe.getResultItem(null).save(new net.minecraft.nbt.CompoundTag()));
        saved.getCompound("OutputTank").putInt("Amount", 0);
        be.loadFromItemTag(saved);
        long synced = (Integer.toUnsignedLong(be.dataValue(14)) << 32) | Integer.toUnsignedLong(be.dataValue(12));
        long syncedSpent = (Integer.toUnsignedLong(be.dataValue(15)) << 32) | Integer.toUnsignedLong(be.dataValue(13));
        helper.assertTrue(synced == recipe.fluidAmount() && syncedSpent == spent, "Menu halves retain long liquid totals");
        tick(be, 1);
        helper.assertTrue(be.dataValue(0) == progress && be.dataValue(6) == NO_FLUID, "Empty tank pauses long-fluid operation");
        // Test the final tick, including exact remaining liquid and FE, after a save/reload.
        long beforeLast = jp.nogami_rion.alchemical_power.util.ReactorProgressCost.cumulative(
                recipe.fluidAmount(), recipe.time() - 1, recipe.time());
        int lastLiquid = Math.toIntExact(recipe.fluidAmount() - beforeLast);
        saved.putInt("Progress", recipe.time() - 1); saved.putLong("FluidSpent", beforeLast);
        saved.putLong("EnergySpent", recipe.energy() - 1);
        saved.getCompound("OutputTank").putInt("Amount", lastLiquid);
        // Supply a larger tank through saved upgrade inventory, as normal operation requires.
        be.getItemHandler().setStackInSlot(UPGRADES + 4, new ItemStack(itemlist.TANK_CAPACITY_UPGRADE_T1.get()));
        var upgraded = new net.minecraft.nbt.CompoundTag(); be.saveToItemTag(upgraded);
        saved.put("Inventory", upgraded.getCompound("Inventory"));
        be.loadFromItemTag(saved); tick(be, 1);
        helper.assertTrue(be.getItemHandler().getStackInSlot(OUTPUT).is(recipe.getResultItem(null).getItem())
                && be.dataValue(4) == 0 && !be.isProcessing(), "Finishes long-fluid recipe without truncation or leftover liquid");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void fluidPausesAndResumesAfterReplacement(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper);
        be.getItemHandler().setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 2));
        be.getItemHandler().setStackInSlot(SAMPLE, recipe.sample());
        be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new)
                .receiveEnergy(Math.toIntExact(recipe.energy()), false);
        be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new)
                .fill(new FluidStack(recipe.fluid().getFluid(), 1), IFluidHandler.FluidAction.EXECUTE);
        tick(be, 1);
        helper.assertTrue(be.isProcessing() && be.dataValue(0) == 1 && be.dataValue(4) == 0,
                "Start with one tick of fluid, not the full operation cost");
        int energy = be.dataValue(2);
        tick(be, 10);
        helper.assertTrue(be.dataValue(6) == NO_FLUID && be.dataValue(0) == 1 && be.dataValue(2) == energy,
                "Missing liquid must pause without consuming FE or progress");
        ItemStack dropped = new ItemStack(blocklist.ALCHEMICAL_REACTOR.get());
        BlockEntityStateTransfer.copyStateToStack(dropped, be);
        helper.setBlock(POS, Blocks.AIR); be = machine(helper);
        BlockEntityStateTransfer.loadStateFromStack(dropped, be);
        helper.assertTrue(be.dataValue(12) == 216 && be.dataValue(13) == 1, "Persist total and consumed liquid separately");
        be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new)
                .fill(new FluidStack(recipe.fluid().getFluid(), 215), IFluidHandler.FluidAction.EXECUTE);
        tick(be, recipe.time() - 1);
        helper.assertTrue(be.getItemHandler().getStackInSlot(OUTPUT).getCount() == 1
                && be.getItemHandler().getStackInSlot(0).getCount() == 1 && be.dataValue(4) == 0 && be.dataValue(2) == 0,
                "Resuming charges exactly 216 mB and one material across both placements");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void legacyPrepaidFluidIsNotChargedAgain(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper);
        supply(be, recipe, recipe.energy()); tick(be, 1);
        var saved = new net.minecraft.nbt.CompoundTag(); be.saveToItemTag(saved);
        saved.remove("TotalFluid"); saved.remove("FluidSpent"); saved.remove("PendingFluid");
        saved.getCompound("OutputTank").putInt("Amount", 0);
        // Old builds stored FE totals as int NBT values.
        saved.putInt("TotalEnergy", Math.toIntExact(recipe.energy()));
        saved.putInt("EnergySpent", Math.toIntExact(recipe.energy() / recipe.time()));
        be.loadFromItemTag(saved);
        tick(be, recipe.time() - 1);
        helper.assertTrue(be.getItemHandler().getStackInSlot(OUTPUT).getCount() == 1 && be.dataValue(4) == 0,
                "An old operation that prepaid liquid can finish with an empty tank");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void crystalLongEnergyAndStreamingFluid(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper, "temperance_crystal");
        helper.assertTrue(recipe.energy() == 131_773_714L && recipe.time() == 9_392
                && recipe.fluid().getAmount() == 1_050_147_072,
                "Crystal time and FE use quarter-root balance without reducing liquid");
        // Keep coverage above the int limit even though the balanced crystal now costs less.
        long largeEnergy = 24_308_962_704L;
        var longRecipe = new AlchemicalReactorRecipe(recipe.getId(), recipe.inputs(), recipe.sample(),
                recipe.getResultItem(helper.getLevel().registryAccess()), recipe.fluid(),
                recipe.toolTier(), recipe.time(), largeEnergy);
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            var serializer = new AlchemicalReactorRecipe.Serializer();
            serializer.toNetwork(buffer, longRecipe);
            var received = serializer.fromNetwork(recipe.getId(), buffer);
            helper.assertTrue(received.energy() == largeEnergy, "Recipe network sync must preserve long FE");
        } finally { buffer.release(); }
        for (int i = 0; i < 8; i++) be.getItemHandler().setStackInSlot(i, new ItemStack(Items.NETHERITE_INGOT, 4096));
        be.getItemHandler().setStackInSlot(8, new ItemStack(Items.NETHERITE_INGOT, 3736));
        be.getItemHandler().setStackInSlot(9, new ItemStack(Items.NETHER_STAR));
        var pool = new ItemStackHandler(48);
        for (int i = 0; i < INPUTS; i++) pool.setStackInSlot(i, be.getItemHandler().getStackInSlot(i).copy());
        var plan = ReactorRecipeTransfer.plan(recipe, pool, false);
        helper.assertTrue(plan.error() == ReactorRecipeTransfer.Error.NONE
                && plan.inputs()[0].getCount() == 4096 && plan.inputs()[8].getCount() == 3736
                && plan.inputs()[9].is(Items.NETHER_STAR), "JEI packs 36504 tag materials and a star in ten inputs");
        be.getItemHandler().setStackInSlot(SAMPLE, recipe.sample());
        be.getItemHandler().setStackInSlot(UPGRADES, new ItemStack(itemlist.CRAFTING_TOOL_UPGRADE_T3.get()));
        be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new)
                .fill(new FluidStack(recipe.fluid().getFluid(),
                        (int) Math.ceil((double) recipe.fluid().getAmount() / recipe.time())),
                        IFluidHandler.FluidAction.EXECUTE);
        be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new).receiveEnergy(100000, false);
        tick(be, 1);
        helper.assertTrue(be.isProcessing() && be.dataValue(0) == 1 && be.dataValue(13) > 0,
                "A recipe exceeding tank capacity starts from a small supply and consumes per tick");
        for (int i = 0; i < 10; i++) helper.assertTrue(be.getItemHandler().getStackInSlot(i).isEmpty(),
                "Reserve 36504 raw ingots and one star across ten inputs");
        var saved = new net.minecraft.nbt.CompoundTag(); be.saveToItemTag(saved);
        helper.assertTrue(saved.getLong("TotalEnergy") == recipe.energy(), "Persist long FE without truncation");
        saved.putLong("TotalEnergy", largeEnergy);
        be.loadFromItemTag(saved);
        long synced = (Integer.toUnsignedLong(be.dataValue(10)) << 32) | Integer.toUnsignedLong(be.dataValue(8));
        helper.assertTrue(synced == largeEnergy, "Both halves of the menu value retain long FE");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void largeInputsPersistAndConsumeAcrossSlots(GameTestHelper helper) {
        var be = machine(helper);
        var handler = be.getItemHandler();
        for (int i = 0; i < 64; i++)
            helper.assertTrue(handler.insertItem(0, new ItemStack(Items.COPPER_BLOCK, 64), false).isEmpty(), "Accept 4096 items");
        helper.assertTrue(handler.insertItem(0, new ItemStack(Items.COPPER_BLOCK), false).getCount() == 1, "Reject item 4097");
        handler.setStackInSlot(1, new ItemStack(Items.COPPER_BLOCK, 2008));
        handler.setStackInSlot(2, new ItemStack(Items.NETHER_STAR));
        var saved = new net.minecraft.nbt.CompoundTag();
        be.saveToItemTag(saved);
        var restored = new AlchemicalReactorBlockEntity(POS, be.getBlockState());
        restored.loadFromItemTag(saved);
        helper.assertTrue(restored.getItemHandler().getStackInSlot(0).getCount() == 4096
                && restored.getItemHandler().getStackInSlot(1).getCount() == 2008, "Full integer counts survive machine-item NBT");
        helper.assertTrue(handler.extractItem(0, 4096, true).getCount() == 64
                && handler.getStackInSlot(0).getCount() == 4096, "Simulated external extraction stays at a normal stack");
        var large = new AlchemicalReactorRecipe(new ResourceLocation("alchemical_power", "test_large_inputs"),
                List.of(new AlchemicalReactorRecipe.Input(Ingredient.of(Items.COPPER_BLOCK), 6104),
                        new AlchemicalReactorRecipe.Input(Ingredient.of(Items.NETHER_STAR), 1)),
                ItemStack.EMPTY, new ItemStack(Items.DIAMOND), FluidStack.EMPTY, 0, 20, 100);
        int[] allocation = large.allocate(handler);
        helper.assertTrue(allocation != null && allocation[0] == 4096 && allocation[1] == 2008 && allocation[2] == 1,
                "Allocate 6104 blocks across two input slots");
        var pool = new ItemStackHandler(48);
        for (int i = 0; i < INPUTS; i++) pool.setStackInSlot(i, handler.getStackInSlot(i).copy());
        var plan = ReactorRecipeTransfer.plan(large, pool, false);
        helper.assertTrue(plan.error() == ReactorRecipeTransfer.Error.NONE
                && plan.inputs()[0].getCount() == 4096 && plan.inputs()[1].getCount() == 2008,
                "JEI planner packs large inputs without exceeding 4096 per slot");
        for (int i = 0; i < INPUTS; i++)
            ((jp.nogami_rion.alchemical_power.util.ReactorItemHandler) handler).consumeInput(i, allocation[i]);
        helper.assertTrue(handler.getStackInSlot(0).isEmpty() && handler.getStackInSlot(1).isEmpty(),
                "Recipe consumption removes the entire allocation, not just 64");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void largeInputClicksKeepNormalPlayerStacks(GameTestHelper helper) {
        var be = machine(helper);
        var player = net.minecraftforge.common.util.FakePlayerFactory.get(helper.getLevel(),
                new com.mojang.authlib.GameProfile(java.util.UUID.fromString("9ba2a8dc-f218-4997-807d-b36469e53b4c"), "ReactorLargeInput"));
        player.getInventory().clearContent();
        var menu = new AlchemicalReactorMenu(43, player.getInventory(), be);
        be.getItemHandler().setStackInSlot(0, new ItemStack(Items.COPPER_BLOCK, 4048));
        menu.setCarried(new ItemStack(Items.COPPER_BLOCK, 64));
        menu.clicked(0, 0, net.minecraft.world.inventory.ClickType.PICKUP, player);
        helper.assertTrue(menu.inputStack(0).getCount() == 4096 && menu.getCarried().getCount() == 16,
                "Click deposits only the available capacity");
        helper.assertTrue(menu.getSlot(0).getItem().getCount() == 1 && menu.inputCount(0) == 4096,
                "Vanilla slot sync uses an icon and independent full count");
        menu.setCarried(ItemStack.EMPTY);
        menu.clicked(0, 0, net.minecraft.world.inventory.ClickType.PICKUP, player);
        helper.assertTrue(menu.getCarried().getCount() == 64 && menu.inputStack(0).getCount() == 4032,
                "Click withdrawal leaves the rest inside the reactor");
        menu.setCarried(ItemStack.EMPTY);
        menu.quickMoveStack(player, 0);
        helper.assertTrue(menu.inputStack(0).getCount() == 3968 && player.getInventory().countItem(Items.COPPER_BLOCK) == 64,
                "Shift-click withdraws one normal stack");
        player.getInventory().setItem(9, new ItemStack(Items.GOLD_BLOCK, 64));
        menu.quickMoveStack(player, SLOTS);
        helper.assertTrue(menu.inputStack(1).is(Items.GOLD_BLOCK) && menu.inputStack(1).getCount() == 64
                && player.getInventory().getItem(9).isEmpty(), "Shift deposit must not alias and erase the inserted stack");
        menu.setCarried(new ItemStack(Items.COPPER_BLOCK, 64));
        menu.clicked(-999, 0, net.minecraft.world.inventory.ClickType.QUICK_CRAFT, player);
        menu.clicked(0, 1, net.minecraft.world.inventory.ClickType.QUICK_CRAFT, player);
        menu.clicked(2, 1, net.minecraft.world.inventory.ClickType.QUICK_CRAFT, player);
        menu.clicked(-999, 2, net.minecraft.world.inventory.ClickType.QUICK_CRAFT, player);
        helper.assertTrue(menu.inputStack(0).getCount() == 4000 && menu.inputStack(2).getCount() == 32
                && menu.getCarried().isEmpty(), "Drag distributes normal stacks into large inputs without loss");
        helper.succeed();
    }
    private static final BlockPos POS = new BlockPos(1, 1, 1);
    private static AlchemicalReactorBlockEntity machine(GameTestHelper helper) {
        helper.setBlock(POS, blocklist.ALCHEMICAL_REACTOR.get());
        return (AlchemicalReactorBlockEntity) helper.getBlockEntity(POS);
    }
    private static AlchemicalReactorRecipe recipe(GameTestHelper helper) {
        return recipe(helper, "t1_panakeia_ingot");
    }
    private static AlchemicalReactorRecipe recipe(GameTestHelper helper, String name) {
        return helper.getLevel().getRecipeManager().getAllRecipesFor(ModRecipes.ALCHEMICAL_REACTOR_TYPE.get()).stream()
                .filter(r -> r.getId().getPath().equals("alchemical_reactor/" + name)).findFirst().orElseThrow();
    }
    private static void supply(AlchemicalReactorBlockEntity be, AlchemicalReactorRecipe recipe, long energy) {
        be.getItemHandler().setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 64));
        be.getItemHandler().setStackInSlot(SAMPLE, recipe.sample());
        be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new)
                .fill(recipe.fluid(), IFluidHandler.FluidAction.EXECUTE);
        be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new).receiveEnergy(Math.toIntExact(energy), false);
    }
    private static void tick(AlchemicalReactorBlockEntity be, int count) {
        for (int i = 0; i < count; i++) AlchemicalReactorBlockEntity.tick(be.getLevel(), be.getBlockPos(), be.getBlockState(), be);
    }
    @GameTest(template = "reactor_test")
    public static void transferCountedAndMaximumBatches(GameTestHelper helper) {
        var recipe = recipe(helper, "t1_panakeia_ingot_block");
        var pool = new ItemStackHandler(48);
        pool.setStackInSlot(0, new ItemStack(Items.GOLD_INGOT, 5));
        pool.setStackInSlot(12, new ItemStack(Items.COPPER_INGOT, 64));
        var single = ReactorRecipeTransfer.plan(recipe, pool, false);
        helper.assertTrue(single.error() == ReactorRecipeTransfer.Error.NONE && single.inputs()[0].getCount() == 9,
                "Plus must supply nine copper for one block recipe");
        helper.assertTrue(single.inventory()[0].getCount() == 55 && single.inventory()[1].is(Items.GOLD_INGOT)
                && single.inventory()[1].getCount() == 5, "Return excess and unrelated inputs without losing items");
        var max = ReactorRecipeTransfer.plan(recipe, pool, true);
        helper.assertTrue(max.batches() == 7 && max.inputs()[0].getCount() == 63 && max.inventory()[0].getCount() == 1,
                "Shift-plus transfers only complete batches");
        helper.assertTrue(pool.getStackInSlot(12).getCount() == 64 && pool.getStackInSlot(0).getCount() == 5,
                "Preview must not change source stacks");
        pool.setStackInSlot(12, new ItemStack(Items.COPPER_INGOT, 8));
        helper.assertTrue(ReactorRecipeTransfer.plan(recipe, pool, false).error() == ReactorRecipeTransfer.Error.MISSING_ITEMS,
                "Transfer must reject insufficient counts");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void transferFullInventoryFailsAtomically(GameTestHelper helper) {
        var pool = new ItemStackHandler(48);
        pool.setStackInSlot(0, new ItemStack(Items.GOLD_INGOT, 64));
        for (int i = 12; i < 48; i++) pool.setStackInSlot(i, new ItemStack(Items.DIRT, 64));
        pool.setStackInSlot(12, new ItemStack(Items.COPPER_INGOT, 64));
        var result = ReactorRecipeTransfer.plan(recipe(helper, "t1_panakeia_ingot_block"), pool, false);
        helper.assertTrue(result.error() == ReactorRecipeTransfer.Error.NO_SPACE,
                "Full inventory cannot accept an old machine ingredient");
        helper.assertTrue(pool.getStackInSlot(0).getCount() == 64 && pool.getStackInSlot(12).getCount() == 64,
                "Failed planning must not partially consume or clear any slot");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void transferTwelveKindsToMenuPreservesSpecialSlots(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper, "unite_alloy");
        var player = net.minecraftforge.common.util.FakePlayerFactory.get(helper.getLevel(),
                new com.mojang.authlib.GameProfile(java.util.UUID.fromString("98ce33b3-7ae8-4a1c-a1c4-118cd8362075"), "ReactorTransfer"));
        player.getInventory().clearContent();
        player.setPos(be.getBlockPos().getX() + 0.5, be.getBlockPos().getY() + 0.5, be.getBlockPos().getZ() + 0.5);
        var menu = new AlchemicalReactorMenu(42, player.getInventory(), be);
        for (int i = 0; i < 12; i++) {
            ItemStack material = recipe.inputs().get(i).ingredient().getItems()[0].copy(); material.setCount(2);
            menu.getSlot(SLOTS + i).set(material);
        }
        be.getItemHandler().setStackInSlot(SAMPLE, recipe.sample());
        be.getItemHandler().setStackInSlot(OUTPUT, new ItemStack(Items.DIAMOND));
        be.getItemHandler().setStackInSlot(UPGRADES, new ItemStack(itemlist.CRAFTING_TOOL_UPGRADE_T4.get()));
        helper.assertTrue(ReactorRecipeTransfer.transfer(menu, player, recipe, false, false) == ReactorRecipeTransfer.Error.NONE
                && be.getItemHandler().getStackInSlot(0).isEmpty(), "Menu preview must not move items");
        helper.assertTrue(ReactorRecipeTransfer.transfer(menu, player, recipe, false, true) == ReactorRecipeTransfer.Error.NONE,
                "Menu transfer must succeed for twelve kinds");
        for (int i = 0; i < 12; i++) helper.assertTrue(menu.getSlot(i).getItem().getCount() == 1
                && menu.getSlot(SLOTS + i).getItem().getCount() == 1, "Transfer exactly one of each kind");
        helper.assertTrue(ItemStack.isSameItemSameTags(menu.getSlot(SAMPLE).getItem(), recipe.sample())
                && menu.getSlot(OUTPUT).getItem().is(Items.DIAMOND)
                && menu.getSlot(UPGRADES).getItem().is(itemlist.CRAFTING_TOOL_UPGRADE_T4.get()),
                "Do not transfer sample, output, or upgrade items");
        menu.removed(player);
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void uniteAlloyUsesTwelveMaterialsAndRequiredTier(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper, "unite_alloy");
        helper.assertTrue(recipe.inputs().size() == 12 && recipe.toolTier() == 3, "Unite alloy requires twelve material kinds and tier 3");
        for (int i = 0; i < 12; i++) {
            var variants = recipe.inputs().get(i).ingredient().getItems();
            helper.assertTrue(variants.length > 0, "Unite alloy material tag must not be empty");
            ItemStack material = variants[0].copy(); material.setCount(2);
            be.getItemHandler().setStackInSlot(i, material);
        }
        be.getItemHandler().setStackInSlot(SAMPLE, recipe.sample());
        be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new).fill(recipe.fluid(), IFluidHandler.FluidAction.EXECUTE);
        var energy = be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new);
        long remainingEnergy = recipe.energy() - energy.receiveEnergy(Math.toIntExact(recipe.energy()), false);
        be.getItemHandler().setStackInSlot(UPGRADES, new ItemStack(itemlist.CRAFTING_TOOL_UPGRADE_T3.get()));
        tick(be, 1);
        helper.assertTrue(!be.isProcessing() && be.dataValue(4) == 302330, "Tier 2 must not consume this tier 3 recipe");
        be.getItemHandler().setStackInSlot(UPGRADES, new ItemStack(itemlist.CRAFTING_TOOL_UPGRADE_T4.get()));
        tick(be, 1);
        for (int i = 0; i < 12; i++) {
            helper.assertTrue(be.getAnimationInput(i).getCount() == 1 && be.getItemHandler().getStackInSlot(i).getCount() == 1,
                    "Reserve and animate one item from each of the twelve slots");
        }
        // The operation costs more than the internal buffer can hold. Supply only
        // the remaining recipe budget, counting FE actually accepted each tick.
        for (int i = 1; i < recipe.time(); i++) {
            remainingEnergy -= energy.receiveEnergy(Math.toIntExact(remainingEnergy), false);
            tick(be, 1);
        }
        helper.assertTrue(remainingEnergy == 0, "Supply exactly one operation's FE across multiple ticks");
        helper.assertTrue(ItemStack.isSameItemSameTags(be.getItemHandler().getStackInSlot(OUTPUT), recipe.getResultItem(null)), "Produce unite alloy");
        helper.assertTrue(be.getItemHandler().getStackInSlot(SAMPLE).getCount() == 1 && be.dataValue(2) == 0 && be.dataValue(4) == 0,
                "Keep the sample and consume exactly one operation's FE and fluid");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void ingotBlockConsumesNineAndSyncsCount(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper, "t1_panakeia_ingot_block");
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        try {
            var serializer = new AlchemicalReactorRecipe.Serializer();
            serializer.toNetwork(buffer, recipe);
            var received = serializer.fromNetwork(recipe.getId(), buffer);
            helper.assertTrue(received.inputs().get(0).count() == 9 && received.fluid().getAmount() == 1944,
                    "Client/JEI recipe sync must retain material count and liquid amount");
        } finally { buffer.release(); }
        supply(be, recipe, recipe.energy());
        be.getItemHandler().setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 8));
        tick(be, 1);
        helper.assertTrue(!be.isProcessing(), "Eight copper cannot make a block");
        be.getItemHandler().setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 64));
        tick(be, 1);
        helper.assertTrue(be.getAnimationInput(0).getCount() == 1 && be.getItemHandler().getStackInSlot(0).getCount() == 55,
                "Reserve nine copper and show their item icon");
        tick(be, recipe.time() - 1);
        helper.assertTrue(ItemStack.isSameItemSameTags(be.getItemHandler().getStackInSlot(OUTPUT), recipe.getResultItem(null)), "Produce the ingot block");
        helper.assertTrue(be.getItemHandler().getStackInSlot(SAMPLE).getCount() == 1 && be.dataValue(2) == 0 && be.dataValue(4) == 0,
                "Block sample survives and costs are exact");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void consumesExactlyOneOperation(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper);
        supply(be, recipe, recipe.energy());
        tick(be, recipe.time());
        helper.assertTrue(be.getItemHandler().getStackInSlot(OUTPUT).getCount() == 1, "One output must be produced");
        helper.assertTrue(be.getItemHandler().getStackInSlot(0).getCount() == 63, "Only one copper may be consumed");
        helper.assertTrue(be.getItemHandler().getStackInSlot(SAMPLE).getCount() == 1, "Sample must survive");
        helper.assertTrue(be.dataValue(2) == 0 && be.dataValue(4) == 0, "Consume exactly the recipe FE and liquid");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void pausesAndRestoresAfterPlacement(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper);
        int firstTickEnergy = Math.toIntExact(recipe.energy() / recipe.time());
        supply(be, recipe, firstTickEnergy);
        tick(be, 1);
        int progress = be.dataValue(0);
        tick(be, 10);
        helper.assertTrue(be.isProcessing() && be.dataValue(0) == progress && be.dataValue(4) == 215,
                "Power loss must pause both processing and liquid consumption");
        helper.assertTrue(be.getAnimationInput(0).getCount() == 1, "Only the consumed count is animated");
        helper.assertTrue(be.getItemHandler().extractItem(SAMPLE, 1, false).isEmpty(), "Sample is locked during processing");
        ItemStack dropped = new ItemStack(blocklist.ALCHEMICAL_REACTOR.get());
        BlockEntityStateTransfer.copyStateToStack(dropped, be);
        helper.setBlock(POS, Blocks.AIR);
        be = machine(helper);
        BlockEntityStateTransfer.loadStateFromStack(dropped, be);
        be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new).receiveEnergy(Math.toIntExact(recipe.energy() - firstTickEnergy), false);
        tick(be, recipe.time() - progress);
        helper.assertTrue(be.getItemHandler().getStackInSlot(OUTPUT).getCount() == 1, "Saved operation must finish once");
        helper.assertTrue(be.getItemHandler().getStackInSlot(0).getCount() == 63 && be.dataValue(2) == 0, "Restoring must not charge resources twice");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void sampleAndOutputGateConsumption(GameTestHelper helper) {
        var be = machine(helper); var recipe = recipe(helper);
        supply(be, recipe, recipe.energy());
        be.getItemHandler().setStackInSlot(SAMPLE, ItemStack.EMPTY);
        tick(be, 1);
        helper.assertTrue(!be.isProcessing(), "Sample-free mode must not run a sample recipe");
        be.getItemHandler().setStackInSlot(SAMPLE, new ItemStack(Items.IRON_INGOT));
        tick(be, 1);
        helper.assertTrue(!be.isProcessing(), "Wrong sample must not select another recipe");
        be.getItemHandler().setStackInSlot(SAMPLE, recipe.sample());
        be.getItemHandler().setStackInSlot(OUTPUT, new ItemStack(itemlist.T1_PANAKEIA_INGOT.get(), 64));
        tick(be, 1);
        helper.assertTrue(!be.isProcessing() && be.dataValue(4) == 216 && be.getItemHandler().getStackInSlot(0).getCount() == 64,
                "Blocked output must not reserve materials or fluid");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void automationAndLargeCapacities(GameTestHelper helper) {
        var be = machine(helper);
        be.getItemHandler().setStackInSlot(UPGRADES + 4, new ItemStack(itemlist.TANK_CAPACITY_UPGRADE_T5.get()));
        var fluid = be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new);
        helper.assertTrue(fluid.getTankCapacity(0) == 64_000_000
                        && fluid.fill(new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(), Integer.MAX_VALUE), IFluidHandler.FluidAction.EXECUTE) == 64_000_000,
                "T5 must hold exactly 64 million mB");
        helper.assertTrue(!be.canRemove(UPGRADES + 4), "Removing capacity upgrade must not destroy liquid");
        helper.assertTrue(fluid.drain(1000, IFluidHandler.FluidAction.EXECUTE).isEmpty(), "Pipes cannot drain the input tank");
        var items = be.getCapability(ForgeCapabilities.ITEM_HANDLER).orElseThrow(IllegalStateException::new);
        helper.assertTrue(items.getSlots() == 13, "Automation must not expose sample or upgrades");
        helper.assertTrue(!items.insertItem(12, new ItemStack(Items.COPPER_INGOT), false).isEmpty(), "Pipes cannot insert output");
        var energy = be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new);
        int simulated = energy.receiveEnergy(Integer.MAX_VALUE, true);
        helper.assertTrue(energy.getEnergyStored() == 0 && energy.receiveEnergy(Integer.MAX_VALUE, false) == simulated,
                "FE simulation and execution must agree without overflow");
        helper.succeed();
    }
    @GameTest(template = "reactor_test")
    public static void countedAndOverlappingIngredients(GameTestHelper helper) {
        ItemStackHandler inventory = new ItemStackHandler(12);
        inventory.setStackInSlot(0, new ItemStack(Items.COPPER_INGOT, 4));
        inventory.setStackInSlot(1, new ItemStack(Items.COPPER_INGOT, 5));
        inventory.setStackInSlot(2, new ItemStack(Items.IRON_INGOT, 1));
        var recipe = new AlchemicalReactorRecipe(new ResourceLocation("alchemical_power", "test_only"),
                List.of(new AlchemicalReactorRecipe.Input(Ingredient.of(Items.COPPER_INGOT, Items.IRON_INGOT), 1),
                        new AlchemicalReactorRecipe.Input(Ingredient.of(Items.COPPER_INGOT), 9)),
                ItemStack.EMPTY, new ItemStack(Items.GOLD_INGOT), new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(), 216), 4, 200, 40000);
        int[] plan = recipe.allocate(inventory);
        helper.assertTrue(plan != null && plan[0] == 4 && plan[1] == 5 && plan[2] == 1,
                "Overlapping ingredient must use iron so the copper-only requirement can be met");
        inventory.setStackInSlot(1, new ItemStack(Items.COPPER_INGOT, 4));
        helper.assertTrue(recipe.allocate(inventory) == null, "Insufficient counted input must not match");
        helper.assertTrue(recipe.matchesSample(ItemStack.EMPTY) && !recipe.matchesSample(new ItemStack(Items.GOLD_INGOT)),
                "Sample-free recipe must reject an installed sample");
        helper.succeed();
    }
}
