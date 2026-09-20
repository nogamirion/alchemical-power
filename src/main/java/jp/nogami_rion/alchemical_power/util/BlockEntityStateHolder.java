package jp.nogami_rion.alchemical_power.util;

import net.minecraft.nbt.CompoundTag;

public interface BlockEntityStateHolder {
    void saveToItemTag(CompoundTag tag);

    void loadFromItemTag(CompoundTag tag);
}
