package jp.nogami_rion.alchemical_power.block;

import jp.nogami_rion.alchemical_power.block.entity.InfiniteLavaBarrelEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfiniteLavaBarrel extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public InfiniteLavaBarrel() {
        super(Properties.of().sound(SoundType.WOOD).strength(3f,6f).requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new InfiniteLavaBarrelEntity(blockPos,blockState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state){
        return RenderShape.MODEL;
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
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        ItemStack held = player.getItemInHand(hand);

        if(world.isClientSide){
            return InteractionResult.SUCCESS;
        }

        if(held.getItem() == Items.BUCKET){
            if(!player.getAbilities().instabuild) held.shrink(1);
            ItemStack give = new ItemStack(Items.LAVA_BUCKET);
            if(!player.addItem(give)) player.drop(give,false);
            world.playSound(null,pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS,1.0f,1.0f);
            return InteractionResult.SUCCESS;
        }

        if (held.getItem() == Items.LAVA_BUCKET) {
            if (!player.getAbilities().instabuild) held.shrink(1);
            ItemStack give = new ItemStack(Items.BUCKET);
            if (!player.addItem(give)) player.drop(give, false);
            world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.CONSUME;
        }

        return FluidUtil.getFluidHandler(held).map(handler -> {
            int bucketAmount = 1000;
            FluidStack lava = new FluidStack(Fluids.LAVA, bucketAmount);
            int filled = handler.fill(lava, IFluidHandler.FluidAction.EXECUTE);

            if (filled > 0) {
                if (handler instanceof IFluidHandlerItem) {
                    ItemStack newStack = ((IFluidHandlerItem) handler).getContainer();
                    if (!ItemStack.matches(held, newStack)) {
                        player.setItemInHand(hand, newStack);
                    }
                }
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }).orElse(InteractionResult.PASS);
    }
}
