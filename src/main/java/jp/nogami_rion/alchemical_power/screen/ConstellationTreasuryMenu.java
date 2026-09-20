package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ConstellationTreasuryMenu extends AbstractContainerMenu {
    public static final int ROWS = ConstellationTreasuryItem.SLOTS / 9;
    private final Inventory playerInventory;
    private final int swordSlot;
    private final ItemStack sword;

    public ConstellationTreasuryMenu(int id, Inventory inventory, FriendlyByteBuf data) { this(id, inventory, data.readVarInt()); }

    public ConstellationTreasuryMenu(int id, Inventory inventory, int swordSlot) {
        super(ModMenuTypes.CONSTELLATION_TREASURY_MENU.get(), id);
        this.playerInventory = inventory;
        this.swordSlot = swordSlot;
        this.sword = inventory.getItem(swordSlot);
        var weapons = ConstellationTreasuryItem.inventory(sword);
        for (int slot = 0; slot < ConstellationTreasuryItem.SLOTS; slot++)
            addSlot(new SlotItemHandler(weapons, slot, ConstellationTreasuryLayout.column(slot % 9), ConstellationTreasuryLayout.weaponRow(slot / 9)));
        for (int row = 0; row < 3; row++) for (int col = 0; col < 9; col++)
            addPlayerSlot(inventory, col + row * 9 + 9, ConstellationTreasuryLayout.column(col), ConstellationTreasuryLayout.inventoryRow(row));
        for (int col = 0; col < 9; col++) addPlayerSlot(inventory, col, ConstellationTreasuryLayout.column(col), ConstellationTreasuryLayout.HOTBAR_Y);
    }

    private void addPlayerSlot(Inventory inventory, int inventoryIndex, int x, int y) {
        addSlot(new Slot(inventory, inventoryIndex, x, y) {
            @Override public boolean mayPickup(Player player) { return inventoryIndex != swordSlot; }
            @Override public boolean mayPlace(ItemStack stack) { return inventoryIndex != swordSlot; }
        });
    }

    @Override public boolean stillValid(Player player) {
        if (player.level().isClientSide) return true; // Slot synchronization replaces the client-side sword stack.
        return player.isAlive() && playerInventory.getItem(swordSlot) == sword
                && ConstellationTreasuryItem.isTreasury(sword) && !ConstellationTreasuryCombat.isActive(sword);
    }

    @Override public void clicked(int slot, int button, ClickType type, Player player) {
        if (!stillValid(player)) return;
        // Number-key swaps and the offhand key bypass Slot.mayPickup on their source slot.
        if (type == ClickType.SWAP && button == swordSlot) return;
        super.clicked(slot, button, type, player);
    }

    @Override public ItemStack quickMoveStack(Player player, int index) {
        if (!stillValid(player) || index < 0 || index >= slots.size()) return ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem() || !slot.mayPickup(player)) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack before = stack.copy();
        if (index < ConstellationTreasuryItem.SLOTS ? !moveItemStackTo(stack, ConstellationTreasuryItem.SLOTS, slots.size(), true)
                : !moveItemStackTo(stack, 0, ConstellationTreasuryItem.SLOTS, false)) return ItemStack.EMPTY;
        if (stack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
        else slot.setChanged();
        slot.onTake(player, stack);
        return before;
    }
}
