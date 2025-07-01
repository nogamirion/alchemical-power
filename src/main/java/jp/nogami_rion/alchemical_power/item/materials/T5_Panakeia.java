package jp.nogami_rion.alchemical_power.item.materials;

import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class T5_Panakeia extends Item {
    public T5_Panakeia(){
        super (new Properties().stacksTo(64).rarity(Rarity.RARE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack){
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        Item heldItem = context.getPlayer() instanceof LivingEntity ? context.getPlayer().getMainHandItem().getItem() : Items.AIR;

        if (state.getBlock() == Blocks.BEACON && heldItem == itemlist.T5_PANAKEIA.get()) {
            if (!context.getPlayer().isCreative()) {
                (context.getPlayer() instanceof LivingEntity ? context.getPlayer().getMainHandItem() : ItemStack.EMPTY).shrink(1);
                level.playSound(null, clickedPos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_DESTROY, clickedPos);
            }

            // ビーコンを空気ブロックに置き換える (破壊)
            level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);

            if (level instanceof net.minecraft.server.level.ServerLevel _level) {
                ItemEntity entityToSpawn = new ItemEntity(_level, (clickedPos.getX() + 0.5), (clickedPos.getY() + 1), (clickedPos.getZ() + 0.5), new ItemStack(itemlist.T6_PANAKEIA.get()));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
