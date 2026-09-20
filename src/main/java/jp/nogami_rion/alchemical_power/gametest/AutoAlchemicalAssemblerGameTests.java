package jp.nogami_rion.alchemical_power.gametest;

import java.util.List;
import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AutoAlchemicalAssemblerBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import jp.nogami_rion.alchemical_power.util.AssemblerIngredientPlan;
import jp.nogami_rion.alchemical_power.util.AssemblerRecipeReloads;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.items.ItemStackHandler;

@GameTestHolder("alchemical_power")
@PrefixGameTestTemplate(false)
public class AutoAlchemicalAssemblerGameTests {
    @GameTest(template = "reactor_test")
    public static void overlappingIngredientsCannotDoubleCount(GameTestHelper helper) {
        var inventory = new ItemStackHandler(2);
        var requirements = List.of(Ingredient.of(Items.OAK_LOG, Items.BIRCH_LOG), Ingredient.of(Items.OAK_LOG));
        inventory.setStackInSlot(0, new ItemStack(Items.OAK_LOG));
        helper.assertTrue(AssemblerIngredientPlan.create(requirements, inventory) == null,
                "One oak log cannot satisfy two ingredients");
        inventory.setStackInSlot(1, new ItemStack(Items.BIRCH_LOG));
        int[] plan = AssemblerIngredientPlan.create(requirements, inventory);
        helper.assertTrue(plan != null && plan[0] == 1 && plan[1] == 1,
                "Broad ingredient must move to birch, leaving oak for the exact ingredient");
        inventory.setStackInSlot(1, ItemStack.EMPTY);
        inventory.setStackInSlot(0, new ItemStack(Items.OAK_LOG, 2));
        plan = AssemblerIngredientPlan.create(requirements, inventory);
        helper.assertTrue(plan != null && plan[0] == 2, "Stack counts must supply multiple requirements");
        helper.succeed();
    }

    @GameTest(template = "reactor_test")
    public static void templateChangesEnergyAndSavedProgress(GameTestHelper helper) {
        BlockPos tablePos = new BlockPos(1, 1, 1);
        BlockPos machinePos = tablePos.above();
        helper.setBlock(tablePos, blocklist.ALCHEMY_TABLE_RE.get());
        helper.setBlock(machinePos, blocklist.AUTO_ALCHEMICAL_ASSEMBLER.get());
        var table = (AbstractAlchemicalTableBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(tablePos));
        var machine = (AutoAlchemicalAssemblerBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(machinePos));
        var energy = machine.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new);
        energy.receiveEnergy(100000, false);
        tick(machine); // Cache an empty template, then change it.
        var recipe = (AlchemicalPowerTablesRecipe) helper.getLevel().getRecipeManager().byKey(
                new ResourceLocation("alchemical_power", "alchemical_power_tables/3x3/alchemy_intermediate_kit")).orElseThrow();
        var ingredients = recipe.getIngredients();
        for (int i = 0; i < 9; i++) {
            ItemStack sample = ingredients.get(i).getItems()[0].copy();
            sample.setCount(1);
            table.getGrid().setItem(i, sample.copy());
            sample.setCount(2);
            machine.getInventoryHandler().insertItem(i, sample, false);
        }
        long revision = table.getGrid().getRevision();
        tick(machine);
        helper.assertTrue(machine.getProgress() == 1 && machine.getEnergyStored() == 99800,
                "Changed template starts and charges exactly 200 FE once");
        helper.assertTrue(table.getGrid().getRevision() == revision && table.getTool().isEmpty(),
                "Virtual tool search must not modify the real table");
        machine.load(machine.saveWithoutMetadata());
        tick(machine);
        helper.assertTrue(machine.getProgress() == 2 && machine.getEnergyStored() == 99600,
                "Saved progress resumes for the same recipe");
        for (int i = 2; i < 90; i++) tick(machine);
        helper.assertTrue(machine.getOutputHandler().getStackInSlot(0).getCount() == 1
                && machine.getEnergyStored() == 82000, "One 90-tick craft charges exactly 18000 FE");
        for (int i = 0; i < 9; i++) helper.assertTrue(machine.getInventoryHandler().getStackInSlot(i).getCount() == 1,
                "Consume each required material exactly once");
        machine.getOutputHandler().extractItem(0, 1, false);
        tick(machine);
        tick(machine);
        AssemblerRecipeReloads.onSync(new OnDatapackSyncEvent(helper.getLevel().getServer().getPlayerList(), null));
        tick(machine);
        helper.assertTrue(machine.getProgress() == 1, "Reload must invalidate in-flight recipe calculations");
        table.getGrid().clearContent();
        tick(machine);
        helper.assertTrue(machine.getProgress() == 0 && machine.getCurrentFEPerTick() == 0,
                "Removing template inputs stops the cached recipe");
        var blankRune = (AlchemicalPowerTablesRecipe) helper.getLevel().getRecipeManager().byKey(
                new ResourceLocation("alchemical_power", "alchemical_power_tables/3x3/blank_rune")).orElseThrow();
        for (int i = 0; i < 9; i++) {
            machine.getInventoryHandler().extractItem(i, 64, false);
            var ingredient = blankRune.getIngredients().get(i);
            if (!ingredient.isEmpty()) {
                ItemStack sample = ingredient.getItems()[0].copy();
                sample.setCount(1);
                table.getGrid().setItem(i, sample.copy());
                machine.getInventoryHandler().insertItem(i, sample, false);
            }
        }
        tick(machine);
        helper.assertTrue(machine.getProgress() == 0, "Tier 2 recipe must reject the default virtual tool");
        machine.getUpgradeHandler().setStackInSlot(0, new ItemStack(itemlist.CRAFTING_TOOL_UPGRADE_T2.get()));
        tick(machine);
        helper.assertTrue(machine.getProgress() == 1, "Tool slot changes must invalidate a negative cache");
        helper.setBlock(tablePos, Blocks.AIR);
        tick(machine);
        helper.assertTrue(machine.getProgress() == 0, "Removing the table must stop crafting");
        helper.succeed();
    }

    private static void tick(AutoAlchemicalAssemblerBlockEntity machine) {
        AutoAlchemicalAssemblerBlockEntity.tick(machine.getLevel(), machine.getBlockPos(), machine.getBlockState(), machine);
    }
}
