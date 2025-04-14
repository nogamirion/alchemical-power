package jp.nogami_rion.alchemical_power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

public class Panakeia_Reed_T5 extends SugarCaneBlock {
    private int maxGrowth = 3;

    public Panakeia_Reed_T5() {
        super(Properties.of().mapColor(MapColor.PLANT).randomTicks().sound(SoundType.GRASS).instabreak().noCollission().offsetType(OffsetType.NONE).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face){
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.BEACH;
    }

    @Override
    public void randomTick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.isEmptyBlock(pos.above())) {
            int i = 1;
            for (; world.getBlockState(pos.below(i)).is(this); ++i);
            if (i < maxGrowth) {
                int j = blockstate.getValue(AGE);
                if (ForgeHooks.onCropsGrowPre(world, pos, blockstate, true)) {
                    if (j == 15) {
                        world.setBlockAndUpdate(pos.above(), defaultBlockState());
                        world.setBlock(pos, blockstate.setValue(AGE, 0), 4);
                    } else {
                        world.setBlock(pos, blockstate.setValue(AGE, j + 1), 4);
                    }
                }
            }
        }
    }


}
