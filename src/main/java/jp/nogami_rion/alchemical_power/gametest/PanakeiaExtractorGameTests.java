package jp.nogami_rion.alchemical_power.gametest;

import jp.nogami_rion.alchemical_power.block.entity.PanakeiaExtractorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("alchemical_power")
@PrefixGameTestTemplate(false)
public class PanakeiaExtractorGameTests {
    @GameTest(template = "reactor_test")
    public static void cubeWaitsForTankUpgrade(GameTestHelper helper) {
        checkUpgrade(helper, itemlist.T7_PANAKEIA_CUBE.get(), itemlist.TANK_CAPACITY_UPGRADE_T1.get());
    }

    @GameTest(template = "reactor_test")
    public static void infinityWaitsForTankUpgrade(GameTestHelper helper) {
        checkUpgrade(helper, itemlist.INFINITY_PANAKEIA.get(), itemlist.TANK_CAPACITY_UPGRADE_T5.get());
    }

    private static void checkUpgrade(GameTestHelper helper, Item input, Item upgrade) {
        var be = machine(helper);
        var fluids = be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new);
        var energy = be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new);
        be.getItemHandler().insertItem(0, new ItemStack(input), false);
        fluids.fill(new FluidStack(Fluids.WATER, Integer.MAX_VALUE), IFluidHandler.FluidAction.EXECUTE);
        energy.receiveEnergy(Integer.MAX_VALUE, false);
        int waterBefore = fluids.getFluidInTank(0).getAmount();
        int energyBefore = energy.getEnergyStored();
        tick(be);
        helper.assertTrue(be.getItemHandler().getStackInSlot(0).getCount() == 1
                && fluids.getFluidInTank(0).getAmount() == waterBefore
                && energy.getEnergyStored() == energyBefore, "Insufficient capacity consumes no material, water or FE");

        be.getItemHandler().insertItem(4, new ItemStack(upgrade), false);
        be.getItemHandler().insertItem(3, new ItemStack(itemlist.ENERGY_CAPACITY_UPGRADE_T1.get()), false);
        fluids.fill(new FluidStack(Fluids.WATER, Integer.MAX_VALUE), IFluidHandler.FluidAction.EXECUTE);
        for (int i = 0; i < PanakeiaExtractorBlockEntity.PROCESS_TIME; i++) {
            energy.receiveEnergy(Integer.MAX_VALUE, false);
            tick(be);
        }
        helper.assertTrue(be.getItemHandler().getStackInSlot(0).isEmpty()
                && fluids.getFluidInTank(1).getAmount() == PanakeiaExtractorBlockEntity.PANAKEIA_OUTPUTS.get(input),
                "Tank upgrade permits processing with the full output retained");
        helper.succeed();
    }

    @GameTest(template = "reactor_test")
    public static void outputSpaceMustFitEntireBatch(GameTestHelper helper) {
        var be = machine(helper);
        var fluids = be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new);
        var energy = be.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalStateException::new);
        int capacity = fluids.getTankCapacity(1);
        var saved = new CompoundTag();
        saved.put("OutputTank", new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(), capacity - 9)
                .writeToNBT(new CompoundTag()));
        be.loadFromItemTag(saved);
        be.getItemHandler().insertItem(0, new ItemStack(itemlist.T0_PANAKEIA.get()), false);
        fluids.fill(new FluidStack(Fluids.WATER, 10), IFluidHandler.FluidAction.EXECUTE);
        energy.receiveEnergy(10, false);
        tick(be);
        helper.assertTrue(!be.getItemHandler().getStackInSlot(0).isEmpty()
                && fluids.getFluidInTank(0).getAmount() == 10 && energy.getEnergyStored() == 10,
                "One mB short of output space must block without consuming inputs");
        fluids.drain(1, IFluidHandler.FluidAction.EXECUTE);
        for (int i = 0; i < PanakeiaExtractorBlockEntity.PROCESS_TIME; i++) tick(be);
        helper.assertTrue(be.getItemHandler().getStackInSlot(0).isEmpty()
                && fluids.getFluidInTank(1).getAmount() == capacity && energy.getEnergyStored() == 0,
                "Exact available space accepts the entire output");
        helper.succeed();
    }

    private static PanakeiaExtractorBlockEntity machine(GameTestHelper helper) {
        var pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, blocklist.PANAKEIA_EXTRACTOR.get());
        return (PanakeiaExtractorBlockEntity) helper.getBlockEntity(pos);
    }

    private static void tick(PanakeiaExtractorBlockEntity be) {
        PanakeiaExtractorBlockEntity.tick(be.getLevel(), be.getBlockPos(), be.getBlockState(), be);
    }
}
