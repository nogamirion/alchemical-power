package jp.nogami_rion.alchemical_power.block;

import jp.nogami_rion.alchemical_power.block.entity.CobblestoneGeneratorMk6Entity;
import jp.nogami_rion.alchemical_power.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class CobblestoneGeneratorMk6 extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final Supplier<Item> product;


    public CobblestoneGeneratorMk6(Supplier<Item> product) {
        super(Properties.of().sound(SoundType.STONE).strength(2.5f, 2.5f).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.product = product;

    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof CobblestoneGeneratorMk6Entity){
                ((CobblestoneGeneratorMk6Entity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level plevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!plevel.isClientSide()){
            BlockEntity entity = plevel.getBlockEntity(pPos);
            if(entity instanceof CobblestoneGeneratorMk6Entity gen){
                gen.use(pPlayer);
            } else{
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(plevel.isClientSide());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState){
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CobblestoneGeneratorMk6Entity(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.COBBLESTONE_GENERATOR_MK6_BE.get(),
                (pLevel1,pPos,pState1,pBlockEntity) -> pBlockEntity.tick(pLevel1,pPos,pState1));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof CobblestoneGeneratorMk6Entity cobbleGen) {
            cobbleGen.loadFromItem(stack);
        }
    }

    public Item getProduct(){
        return product.get();
    }
//NBTアイテムへの内部保存　よくわからないのでコメントアウト
//    @Override
//    public List<ItemStack> getDrops(BlockState state, LootContext context) {
//        BlockEntity blockEntity = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
//        ItemStack drop = new ItemStack(this);
//
//        if (blockEntity instanceof CobblestoneGeneratorMk6Entity cobbleGen) {
//            cobbleGen.saveToItem(drop);
//        }
//
//        java.util.List<ItemStack> drops = new java.util.ArrayList<>();
//        drops.add(drop);
//        return drops;
//    }
}
