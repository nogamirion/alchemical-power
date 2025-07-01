package jp.nogami_rion.alchemical_power.util;

import net.minecraftforge.items.ItemStackHandler;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ConfigurableItemHandler extends ItemStackHandler {
    private final Set<Integer> inputSlots;
    private final Set<Integer> outputSlots;

    public ConfigurableItemHandler(int size, Set<Integer> inputSlots, Set<Integer> outputSlots) {
        super(size);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
    }

    @Override
    public int getSlots() {
        return inputSlots.size() + outputSlots.size();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        // 入力スロットのみアイテムを受け入れる
        return inputSlots.contains(slot);
    }


//    @Override
//    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
//        // 出力スロット以外からの外部搬出を禁止…のはずだが手動操作もNGになっている
//        if (!outputSlots.contains(slot) && simulate) {
//            return ItemStack.EMPTY;
//        }
//        // 手動操作や内部処理の場合は制限を適用しない
//        return super.extractItem(slot, amount, simulate);
//    }

}