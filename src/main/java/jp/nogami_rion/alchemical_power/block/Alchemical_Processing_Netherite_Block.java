package jp.nogami_rion.alchemical_power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class Alchemical_Processing_Netherite_Block extends Block {
    public Alchemical_Processing_Netherite_Block(){
        super (Properties.of().sound(SoundType.METAL).strength(50f,1200f).requiresCorrectToolForDrops());
    }
    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

}
