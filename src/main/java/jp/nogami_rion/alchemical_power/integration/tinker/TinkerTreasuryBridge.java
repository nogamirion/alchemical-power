package jp.nogami_rion.alchemical_power.integration.tinker;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.lang.reflect.Method;

/** Keeps optional TiC classes out of the shared storage and menu classes. */
public final class TinkerTreasuryBridge {
    private static Method hasModifier;
    private static Method storage;

    private TinkerTreasuryBridge() {}

    private static boolean available() {
        if (!ModList.get().isLoaded("tconstruct")) return false;
        if (hasModifier == null) {
            try {
                Class<?> type = Class.forName("jp.nogami_rion.alchemical_power.integration.tinker.modifier.ConstellationTreasuryModifier");
                hasModifier = type.getMethod("hasModifier", ItemStack.class);
                storage = type.getMethod("storage", ItemStack.class, boolean.class);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("Cannot initialize TiC treasury storage", e);
            }
        }
        return true;
    }

    public static boolean hasModifier(ItemStack stack) {
        if (stack.isEmpty() || !available()) return false;
        try {
            return (boolean) hasModifier.invoke(null, stack);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot inspect TiC treasury", e);
        }
    }

    public static CompoundTag storage(ItemStack stack, boolean create) {
        try {
            return (CompoundTag) storage.invoke(null, stack, create);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Cannot access TiC treasury storage", e);
        }
    }
}
