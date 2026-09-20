package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.util.UpgradeRemovalState;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

final class UpgradeLockTooltip {
    private UpgradeLockTooltip() {}

    static List<Component> append(List<Component> original, Slot hovered) {
        if (!(hovered instanceof LockedUpgradeSlot slot) || slot.removalState().allowed()) return original;
        List<Component> result = new ArrayList<>(original);
        UpgradeRemovalState state = slot.removalState();
        result.add(Component.empty());
        add(result, "title", ChatFormatting.RED);
        if ((state.reasons() & UpgradeRemovalState.PROCESSING) != 0) add(result, "processing", ChatFormatting.RED);
        if ((state.reasons() & UpgradeRemovalState.GENERATING) != 0) add(result, "generating", ChatFormatting.RED);
        if ((state.reasons() & UpgradeRemovalState.ENERGY) != 0) {
            add(result, "energy", ChatFormatting.RED);
            add(result, "energy_limit", ChatFormatting.GRAY, String.format(Locale.ROOT, "%,d", state.energyLimit()));
        }
        if ((state.reasons() & UpgradeRemovalState.WATER) != 0) add(result, "water", ChatFormatting.RED);
        if ((state.reasons() & UpgradeRemovalState.OUTPUT) != 0) add(result, "output", ChatFormatting.RED);
        if ((state.reasons() & (UpgradeRemovalState.WATER | UpgradeRemovalState.OUTPUT)) != 0)
            add(result, "tank_limit", ChatFormatting.GRAY, String.format(Locale.ROOT, "%,d", state.tankLimit()));
        return result;
    }

    private static void add(List<Component> lines, String key, ChatFormatting color, Object... args) {
        lines.add(Component.translatable("gui.alchemical_power.upgrade_lock." + key, args).withStyle(color));
    }
}
