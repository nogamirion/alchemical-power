package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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

public class T1_Panakeia extends Item {
    public T1_Panakeia(){
        super (new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        Item heldItem = context.getPlayer() instanceof LivingEntity ? ((LivingEntity) context.getPlayer()).getMainHandItem().getItem() : Items.AIR;

        if (state.getBlock() == Blocks.LAVA_CAULDRON && heldItem == itemlist.T1_PANAKEIA.get()) {
            if (!context.getPlayer().isCreative()) {
                (context.getPlayer() instanceof LivingEntity ? ((LivingEntity) context.getPlayer()).getMainHandItem() : ItemStack.EMPTY).shrink(1);
                level.playSound(null, clickedPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, clickedPos);
            }

            // 溶岩入り大釜を空の大釜に置き換える
            level.setBlock(clickedPos, Blocks.CAULDRON.defaultBlockState(), 3);
            level.playSound(null, clickedPos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F); // 溶岩を空にする音

            if (level instanceof ServerLevel _level) {
                ItemEntity entityToSpawn = new ItemEntity(_level, (clickedPos.getX() + 0.5), (clickedPos.getY() + 1), (clickedPos.getZ() + 0.5), new ItemStack(itemlist.T2_PANAKEIA.get()));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
