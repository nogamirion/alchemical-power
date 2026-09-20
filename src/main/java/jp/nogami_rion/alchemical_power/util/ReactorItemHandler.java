package jp.nogami_rion.alchemical_power.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/** Oversized stacks stay inside the reactor; persisted counts are full integers. */
public class ReactorItemHandler extends ItemStackHandler {
    public static final int INPUT_SLOTS = 12, INPUT_LIMIT = 4096;
    public static final int TOTAL_INPUT_LIMIT = INPUT_SLOTS * INPUT_LIMIT;
    public ReactorItemHandler(int slots) { super(slots); }
    @Override public void setStackInSlot(int slot, ItemStack stack) {
        if (slot >= 0 && slot < INPUT_SLOTS && stack.getCount() > INPUT_LIMIT)
            throw new IllegalArgumentException("Reactor input exceeds 4096 items");
        super.setStackInSlot(slot, stack);
    }
    @Override public int getSlotLimit(int slot) {
        return slot < INPUT_SLOTS ? INPUT_LIMIT : slot == 13 ? 64 : 1;
    }
    @Override protected int getStackLimit(int slot, ItemStack stack) {
        return slot < INPUT_SLOTS ? INPUT_LIMIT : super.getStackLimit(slot, stack);
    }
    @Override public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack stored = getStackInSlot(slot);
        return super.extractItem(slot, Math.min(amount, stored.getMaxStackSize()), simulate);
    }
    /** Called only after the recipe has allocated and reserved a complete operation. */
    public ItemStack consumeInput(int slot, int amount) {
        if (slot < 0 || slot >= INPUT_SLOTS || amount < 0 || amount > getStackInSlot(slot).getCount())
            throw new IllegalArgumentException("Invalid reactor consumption");
        if (amount == 0) return ItemStack.EMPTY;
        ItemStack remainder = getStackInSlot(slot).copy();
        ItemStack visual = remainder.copy(); visual.setCount(1);
        remainder.shrink(amount);
        setStackInSlot(slot, remainder);
        return visual;
    }
    @Override public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        ListTag items = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < items.size(); i++) {
            CompoundTag entry = items.getCompound(i);
            int slot = entry.getInt("Slot");
            if (slot < INPUT_SLOTS) {
                entry.putByte("Count", (byte) 1);
                entry.putInt("ReactorCount", getStackInSlot(slot).getCount());
            }
        }
        return tag;
    }
    @Override public void deserializeNBT(CompoundTag tag) {
        super.deserializeNBT(tag);
        ListTag items = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < items.size(); i++) {
            CompoundTag entry = items.getCompound(i);
            int slot = entry.getInt("Slot");
            if (slot >= 0 && slot < INPUT_SLOTS && entry.contains("ReactorCount", Tag.TAG_INT)) {
                ItemStack stack = getStackInSlot(slot).copy();
                stack.setCount(Math.max(0, Math.min(INPUT_LIMIT, entry.getInt("ReactorCount"))));
                setStackInSlot(slot, stack);
            }
        }
    }
}
