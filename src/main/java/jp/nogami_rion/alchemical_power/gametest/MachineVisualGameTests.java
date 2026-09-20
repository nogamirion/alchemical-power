package jp.nogami_rion.alchemical_power.gametest;

import jp.nogami_rion.alchemical_power.block.PanakeiaExtractorBlock;
import jp.nogami_rion.alchemical_power.block.AlchemicalReactorBlock;
import jp.nogami_rion.alchemical_power.block.entity.PanakeiaExtractorBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.util.AlchemicalMachineShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("alchemical_power")
@PrefixGameTestTemplate(false)
public class MachineVisualGameTests {
    @GameTest(template="reactor_test", batch="machine_visuals")
    public static void machineShapesFollowAllFacings(GameTestHelper helper) {
        Direction[] directions={Direction.NORTH,Direction.EAST,Direction.SOUTH,Direction.WEST};
        for (int turn=0; turn<4; turn++) {
            var extractor=blocklist.PANAKEIA_EXTRACTOR.get().defaultBlockState()
                    .setValue(PanakeiaExtractorBlock.FACING,directions[turn]);
            var shape=extractor.getShape(helper.getLevel(),BlockPos.ZERO);
            helper.assertTrue(shape == extractor.getCollisionShape(helper.getLevel(),BlockPos.ZERO,CollisionContext.empty()),
                    "Selection and collision use the same cached extractor shape");
            helper.assertTrue(contains(shape,4,6,8,turn),"Tall vessel is solid in every facing");
            helper.assertTrue(contains(shape,12,5,8,turn),"Small vessel is solid in every facing");
            helper.assertTrue(!contains(shape,12,12,8,turn),"Air above small vessel remains empty");
            helper.assertTrue(!contains(shape,8.2,6,8,turn),"Gap between vessels remains empty");
            helper.assertTrue(contains(shape,8.2,10.5,7.5,turn),"Connecting pipe has collision");
            var reactor=blocklist.ALCHEMICAL_REACTOR.get().defaultBlockState()
                    .setValue(AlchemicalReactorBlock.FACING,directions[turn]);
            var furnace=reactor.getShape(helper.getLevel(),BlockPos.ZERO);
            helper.assertTrue(furnace == reactor.getCollisionShape(helper.getLevel(),BlockPos.ZERO,CollisionContext.empty()),
                    "Selection and collision use the same cached reactor shape");
            helper.assertTrue(contains(furnace,8,6,8,turn),"Reactor vessel is solid");
            helper.assertTrue(!contains(furnace,8,6,1.8,turn),"Reactor front recess is empty");
            helper.assertTrue(contains(furnace,15.8,8,8,turn),"Reactor inlet rotates with the model");
        }
        helper.succeed();
    }

    private static boolean contains(VoxelShape shape,double x,double y,double z,int turns) {
        for(int i=0;i<turns;i++) { double old=x; x=16-z; z=old; }
        final double px=x/16, py=y/16, pz=z/16;
        return shape.toAabbs().stream().anyMatch(box -> box.contains(px,py,pz));
    }

    @GameTest(template="reactor_test", batch="machine_visuals")
    public static void tankPacketPreservesAmountsAndUpgradedCapacities(GameTestHelper helper) {
        BlockPos pos=new BlockPos(1,1,1);
        helper.setBlock(pos,blocklist.PANAKEIA_EXTRACTOR.get());
        var be=(PanakeiaExtractorBlockEntity)helper.getBlockEntity(pos);
        var fluids=be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new);
        fluids.fill(new FluidStack(Fluids.WATER,400000),IFluidHandler.FluidAction.SIMULATE);
        helper.assertTrue(be.getWaterAmount()==0,"Simulated pipe transfer changes no liquid");
        fluids.fill(new FluidStack(Fluids.WATER,400000),IFluidHandler.FluidAction.EXECUTE);
        be.getItemHandler().insertItem(4,new ItemStack(itemlist.TANK_CAPACITY_UPGRADE_T1.get()),false);
        CompoundTag tag=be.getUpdateTag();
        tag.put("OutputTank",new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(),200000).writeToNBT(new CompoundTag()));
        be.load(tag);
        fluids.drain(50000,IFluidHandler.FluidAction.EXECUTE);
        PanakeiaExtractorBlockEntity.tick(be.getLevel(),be.getBlockPos(),be.getBlockState(),be);
        var clientCopy=new PanakeiaExtractorBlockEntity(be.getBlockPos(),be.getBlockState());
        clientCopy.handleUpdateTag(be.getUpdatePacket().getTag());
        helper.assertTrue(clientCopy.getWaterAmount()==400000 && clientCopy.getOutputAmount()==150000,
                "Update packet carries pipe-filled water and drained output without an open menu");
        helper.assertTrue(clientCopy.getWaterCapacity()==be.getWaterCapacity()
                        && clientCopy.getOutputCapacity()==be.getOutputCapacity()
                        && clientCopy.getWaterCapacity()>800000,
                "Client derives upgraded capacities from synchronized inventory");
        helper.succeed();
    }

    @GameTest(template="reactor_test", batch="machine_visuals")
    public static void fluidHeightStaysInsideBothVessels(GameTestHelper helper) {
        for(AABB tank:new AABB[]{AlchemicalMachineShapes.EXTRACTOR_WATER,AlchemicalMachineShapes.EXTRACTOR_OUTPUT,AlchemicalMachineShapes.REACTOR_FLUID}) {
            helper.assertTrue(AlchemicalMachineShapes.fluidTop(tank,0,800000)==tank.minY,"Empty vessel has no fluid height");
            helper.assertTrue(Math.abs(AlchemicalMachineShapes.fluidTop(tank,400000,800000)
                    -(tank.minY+tank.getYsize()/2))<1e-9,"Half tank produces half height");
            helper.assertTrue(AlchemicalMachineShapes.fluidTop(tank,Integer.MAX_VALUE,800000)==tank.maxY,
                    "Overfull amount clamps to glass interior");
            helper.assertTrue(AlchemicalMachineShapes.fluidTop(tank,400000,3200000)<AlchemicalMachineShapes.fluidTop(tank,400000,800000),
                    "Capacity upgrades lower the visible fill fraction");
        }
        helper.succeed();
    }

    @GameTest(template="reactor_test", batch="machine_visuals")
    public static void reactorLiquidTracksFillConsumptionAndCapacity(GameTestHelper helper) {
        BlockPos pos=new BlockPos(1,1,1);
        helper.setBlock(pos,blocklist.ALCHEMICAL_REACTOR.get());
        var be=(AlchemicalReactorBlockEntity)helper.getBlockEntity(pos);
        var fluids=be.getCapability(ForgeCapabilities.FLUID_HANDLER).orElseThrow(IllegalStateException::new);
        var liquid=new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(),1000000);
        fluids.fill(liquid,IFluidHandler.FluidAction.SIMULATE);
        helper.assertTrue(be.getFluidAmount()==0,"Simulated reactor fill stays empty");
        fluids.fill(liquid,IFluidHandler.FluidAction.EXECUTE);
        AlchemicalReactorBlockEntity.tick(be.getLevel(),be.getBlockPos(),be.getBlockState(),be);
        var clientCopy=new AlchemicalReactorBlockEntity(be.getBlockPos(),be.getBlockState());
        clientCopy.handleUpdateTag(be.getUpdatePacket().getTag());
        helper.assertTrue(clientCopy.getFluidAmount()==1000000 && clientCopy.getTankCapacity()==2000000,
                "Idle fill packet contains half-full base tank");
        be.getItemHandler().insertItem(AlchemicalReactorBlockEntity.UPGRADES+4,
                new ItemStack(itemlist.TANK_CAPACITY_UPGRADE_T1.get()),false);
        AlchemicalReactorBlockEntity.tick(be.getLevel(),be.getBlockPos(),be.getBlockState(),be);
        clientCopy.handleUpdateTag(be.getUpdatePacket().getTag());
        helper.assertTrue(clientCopy.getFluidAmount()==1000000 && clientCopy.getTankCapacity()==8000000,
                "Capacity change synchronizes without changing stored liquid");
        CompoundTag active=be.getUpdateTag();
        active.put("PendingResult",new ItemStack(Items.IRON_INGOT).save(new CompoundTag()));
        active.put("PendingFluid",new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(),1).writeToNBT(new CompoundTag()));
        active.putInt("Duration",20); active.putInt("Progress",0);
        active.putInt("Energy",20); active.putLong("TotalEnergy",20); active.putLong("EnergySpent",0);
        active.putLong("TotalFluid",200000); active.putLong("FluidSpent",0);
        be.load(active);
        AlchemicalReactorBlockEntity.tick(be.getLevel(),be.getBlockPos(),be.getBlockState(),be);
        clientCopy.handleUpdateTag(be.getUpdatePacket().getTag());
        helper.assertTrue(clientCopy.getFluidAmount()==990000,"Processing consumption reaches client tank state");
        helper.succeed();
    }
}
