package jp.nogami_rion.alchemical_power.block.craft;

import jp.nogami_rion.alchemical_power.block.entity.AutoAlchemicalAssemblerBlockEntity;
import jp.nogami_rion.alchemical_power.block.entity.ModBlockEntities;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.screen.AutoAlchemicalAssemblerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

import org.jetbrains.annotations.Nullable;

public class AutoAlchemicalAssemblerBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public AutoAlchemicalAssemblerBlock() {
        super(BlockBehaviour.Properties.of().strength(3.0f).noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(Level level, BlockState state, BlockEntityType<T> type){
        return type == ModBlockEntities.AUTO_ALCHEMICAL_ASSEMBLER.get()
                ? (lvl, pos, st, be) ->
                AutoAlchemicalAssemblerBlockEntity.tick(lvl, pos, st,
                        (AutoAlchemicalAssemblerBlockEntity) be)
                : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AutoAlchemicalAssemblerBlockEntity(blockPos,blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AutoAlchemicalAssemblerBlockEntity be) {
                dropInventory(level, pos, be);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            MenuProvider provider = new SimpleMenuProvider((id, inv, ply) ->
                    new AutoAlchemicalAssemblerMenu(id, inv, level.getBlockEntity(pos)),
                    Component.translatable("block.alchemical_power.auto_alchemical_assembler"));

            NetworkHooks.openScreen((ServerPlayer) player, provider, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private void dropInventory(Level level,
                               BlockPos pos,
                               AutoAlchemicalAssemblerBlockEntity be) {

        if(level.isClientSide) return;

        // 入力inventory
        dropItemHandler(level,pos,be.getInventoryHandler());

        // 出力
        dropItemHandler(level,pos,be.getOutputHandler());

        // upgrade
        dropItemHandler(level,pos,be.getUpgradeHandler());
    }

    private void dropItemHandler(Level level, BlockPos pos, IItemHandler handler){
        for(int i = 0; i < handler.getSlots(); i++){
            ItemStack stack = handler.getStackInSlot(i);
            if(!stack.isEmpty()){
                Containers.dropItemStack(level,pos.getX(),pos.getY(),pos.getZ(),stack);
            }
        }
    }

}
