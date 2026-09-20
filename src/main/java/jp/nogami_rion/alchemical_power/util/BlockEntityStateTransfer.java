package jp.nogami_rion.alchemical_power.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockEntityStateTransfer {
    private static final String BLOCK_ENTITY_TAG = "BlockEntityTag";

    private BlockEntityStateTransfer() {
    }

    public static List<ItemStack> copyStateToDrops(List<ItemStack> drops, Block block, @Nullable BlockEntity blockEntity) {
        if (!(blockEntity instanceof BlockEntityStateHolder holder)) {
            return drops;
        }

        for (ItemStack stack : drops) {
            if (stack.getItem() == block.asItem()) {
                copyStateToStack(stack, holder);
                break;
            }
        }
        return drops;
    }

    public static void copyStateToStack(ItemStack stack, BlockEntityStateHolder holder) {
        CompoundTag tag = new CompoundTag();
        holder.saveToItemTag(tag);
        if (!tag.isEmpty()) {
            stack.addTagElement(BLOCK_ENTITY_TAG, tag);
        }
    }

    public static void loadStateFromStack(ItemStack stack, BlockEntityStateHolder holder) {
        if (stack.hasTag() && stack.getTag().contains(BLOCK_ENTITY_TAG)) {
            holder.loadFromItemTag(stack.getTag().getCompound(BLOCK_ENTITY_TAG));
        }
    }
}
