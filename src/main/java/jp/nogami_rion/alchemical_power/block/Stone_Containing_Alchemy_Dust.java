package jp.nogami_rion.alchemical_power.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class Stone_Containing_Alchemy_Dust extends Block {
    public Stone_Containing_Alchemy_Dust(){
        super (BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3f,6f).requiresCorrectToolForDrops());
    }
    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

}
