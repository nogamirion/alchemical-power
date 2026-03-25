package jp.nogami_rion.alchemical_power.block.craft;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalTablesTier1BlockEntity;
import jp.nogami_rion.alchemical_power.util.InventoryDropUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class AlchemyTableBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public AlchemyTableBlock() {
        super(BlockBehaviour.Properties.of().strength(3.0f).noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AlchemicalTablesTier1BlockEntity(blockPos,blockState);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if(level.isClientSide){
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if(!(be instanceof AlchemicalTablesTier1BlockEntity)){
            return InteractionResult.PASS;
        }

        NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) be,pos);

        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state,Level level,BlockPos pos,BlockState newState,boolean isMoving){
        if(state.getBlock() != newState.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof AbstractAlchemicalTableBlockEntity be){
               be.dropGridContents();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
