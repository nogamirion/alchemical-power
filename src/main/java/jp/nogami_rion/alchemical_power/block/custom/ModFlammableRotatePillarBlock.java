package jp.nogami_rion.alchemical_power.block.custom;

import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

import javax.annotation.Nullable;

public class ModFlammableRotatePillarBlock  extends RotatedPillarBlock {
    public ModFlammableRotatePillarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action,boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem){
            if(state.is(blocklist.ALCHETREE_LOG.get())){
                return blocklist.STRIPPED_ALCHETREE_LOG.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
            }
            if(state.is(blocklist.ALCHETREE_WOOD.get())){
                return blocklist.STRIPPED_ALCHETREE_WOOD.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
            }
        }
        return super.getToolModifiedState(state, context, action, simulate);
    }

}

