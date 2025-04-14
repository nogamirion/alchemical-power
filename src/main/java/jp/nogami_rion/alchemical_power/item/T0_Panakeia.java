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
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

public class T0_Panakeia extends Item {
    public T0_Panakeia() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        Item heldItem = context.getPlayer() instanceof LivingEntity ? context.getPlayer().getMainHandItem().getItem() : Items.AIR;

        if (state.getBlock() == Blocks.WATER_CAULDRON && heldItem == itemlist.T0_PANAKEIA.get()) {
            IntegerProperty levelProperty = LayeredCauldronBlock.LEVEL; // 水入り大釜の水の量を表すプロパティ
            int currentLevel = state.getValue(levelProperty);

            if (currentLevel > 0) {
                if (!context.getPlayer().isCreative()) {
                    (context.getPlayer() instanceof LivingEntity ? context.getPlayer().getMainHandItem() : ItemStack.EMPTY).shrink(1);
                    BlockState newState;
                    if (currentLevel == 1) {
                        newState = Blocks.CAULDRON.defaultBlockState(); // 水がなくなったら空の大釜に
                        level.playSound(null, clickedPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.gameEvent(context.getPlayer(), GameEvent.FLUID_PLACE, clickedPos);
                    } else {
                        newState = state.setValue(levelProperty, currentLevel - 1);
                        level.playSound(null, clickedPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.gameEvent(context.getPlayer(), GameEvent.FLUID_PLACE, clickedPos);
                    }
                    level.setBlock(clickedPos, newState, 3);
                }

                if (level instanceof ServerLevel _level) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, (clickedPos.getX() + 0.5), (clickedPos.getY() + 1), (clickedPos.getZ() + 0.5), new ItemStack(itemlist.T1_PANAKEIA.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}