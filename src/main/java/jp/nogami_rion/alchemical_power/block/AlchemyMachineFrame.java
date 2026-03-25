package jp.nogami_rion.alchemical_power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class AlchemyMachineFrame extends Block {
    public AlchemyMachineFrame(){
        super (Properties.of().sound(SoundType.METAL).strength(5f,6f).noOcclusion());
    }
    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

}
