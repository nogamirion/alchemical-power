package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class The_Destroyer_of_the_Rial extends PickaxeItem {
    public The_Destroyer_of_the_Rial() {
        super(ModMaterialTiers.IMITATED_BEDROCK,  0, 0.0f,
                new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        // 耐久を無限にする
        return false;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos setblockpos = context.getClickedPos().relative(context.getClickedFace());
        BlockPos breakblockpos = context.getClickedPos();
        BlockState clickedBlock = level.getBlockState(breakblockpos);
        boolean isSneaking = context.getPlayer() != null && context.getPlayer().isShiftKeyDown();

        if (!level.isClientSide) {
            if (isSneaking) {
                // Shift+右クリックで岩盤を設置
                level.setBlock(setblockpos, Blocks.BEDROCK.defaultBlockState(), 3);
                level.playSound(null, setblockpos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (clickedBlock.is(Blocks.BEDROCK)) {
                // 右クリックで岩盤を破壊（空気へ入れ替え）
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_DESTROY, breakblockpos);
                level.setBlock(breakblockpos, Blocks.AIR.defaultBlockState(), 3);
                level.playSound(null, breakblockpos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
