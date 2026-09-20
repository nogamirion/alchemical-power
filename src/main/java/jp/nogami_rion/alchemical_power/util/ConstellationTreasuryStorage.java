package jp.nogami_rion.alchemical_power.util;

import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.screen.ConstellationTreasuryMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkHooks;

/** Server-selected held storage; clients cannot nominate arbitrary inventory slots. */
public final class ConstellationTreasuryStorage {
    public static int heldSlot(Player player) {
        if (ConstellationTreasuryItem.isTreasury(player.getMainHandItem())) return player.getInventory().selected;
        return ConstellationTreasuryItem.isTreasury(player.getOffhandItem()) ? 40 : -1;
    }

    public static void openHeld(ServerPlayer player) {
        if (!player.isAlive() || player.isSpectator() || player.containerMenu != player.inventoryMenu) return;
        int slot = heldSlot(player);
        if (slot < 0) return;
        var stack = player.getInventory().getItem(slot);
        if (ConstellationTreasuryCombat.isActive(stack)) {
            player.displayClientMessage(Component.translatable("message.alchemical_power.constellation_treasury.busy"), true);
            return;
        }
        player.stopUsingItem();
        var title = stack.getItem() instanceof ConstellationTreasuryItem ? stack.getHoverName()
                : Component.translatable("modifier.alchemical_power.constellation_treasury");
        NetworkHooks.openScreen(player,
                new SimpleMenuProvider((id, inv, p) -> new ConstellationTreasuryMenu(id, inv, slot), title),
                buffer -> buffer.writeVarInt(slot));
    }
}
