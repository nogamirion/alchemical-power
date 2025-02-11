package jp.nogami_rion.alchemical_power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class Unite_Alloy_Block extends Block {
    public Unite_Alloy_Block(){
        super (Properties.of().sound(SoundType.METAL).strength(100f,2400f).requiresCorrectToolForDrops());
    }
    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

}
