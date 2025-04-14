package jp.nogami_rion.alchemical_power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class X225_Cobblestone extends Block {
    public X225_Cobblestone(){
        super (Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops());
    }
    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

}
