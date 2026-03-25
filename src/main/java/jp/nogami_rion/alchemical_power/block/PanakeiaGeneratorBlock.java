package jp.nogami_rion.alchemical_power.block;

import jp.nogami_rion.alchemical_power.block.entity.ModBlockEntities;
import jp.nogami_rion.alchemical_power.block.entity.PanakeiaGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class PanakeiaGeneratorBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty RAINBOW = IntegerProperty.create("rainbow", 0, 23);

    public PanakeiaGeneratorBlock(){
        super(Properties.of().sound(SoundType.METAL).strength(5f,10f).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(RAINBOW,0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder){
        super.createBlockStateDefinition(builder);
        builder.add(FACING,LIT,RAINBOW);
    }

    @Override
    public RenderShape getRenderShape(BlockState state){
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PanakeiaGeneratorBlockEntity(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity>BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type){
        return level.isClientSide ? null :
                createTickerHelper(type, ModBlockEntities.PANAKEIA_GENERATOR_BE.get(),PanakeiaGeneratorBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if(!level.isClientSide){
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof PanakeiaGeneratorBlockEntity generator){
                NetworkHooks.openScreen((ServerPlayer) player,generator,pos);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void neighborChanged(BlockState state,Level level,BlockPos pos,Block block,BlockPos fromPos,boolean isMoving){
        BlockEntity be = level.getBlockEntity(pos);
        if(be instanceof PanakeiaGeneratorBlockEntity generator){
            generator.updateNeighbors();
        }
        super.neighborChanged(state,level,pos,block,fromPos,isMoving);
    }

    @Override
    public void onRemove(BlockState state,Level level,BlockPos pos,BlockState newState,boolean isMoving){
        if(!level.isClientSide && state.getBlock() != newState.getBlock()){
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof PanakeiaGeneratorBlockEntity generator){
                dropInventory(level,pos,generator.getItemHandler());
            }
        }
        super.onRemove(state,level,pos,newState,isMoving);
    }

    private void dropInventory(Level level, BlockPos pos, IItemHandler handler) {
        for(int i = 0; i < handler.getSlots(); i++){
            ItemStack stack = handler.getStackInSlot(i);
            if(!stack.isEmpty()){
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack.copy());
            }
        }
    }

}
