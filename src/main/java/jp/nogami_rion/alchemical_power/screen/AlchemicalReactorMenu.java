package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import jp.nogami_rion.alchemical_power.util.ReactorItemHandler;
import static jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity.*;

public class AlchemicalReactorMenu extends AbstractContainerMenu {
    // Item origins measured from the supplied 360x360 GUI, inside the corner marks.
    public static final int[][] INPUT_POSITIONS = {
            {105,45}, {87,63}, {105,63}, {123,63}, {69,81}, {87,81},
            {123,81}, {141,81}, {87,99}, {105,99}, {123,99}, {105,117}
    };
    public static final int[][] UPGRADE_POSITIONS = {{271,41}, {280,61}, {289,81}, {280,101}, {271,121}};
    public static final int OUTPUT_X = 214, OUTPUT_Y = 81;
    private final AlchemicalReactorBlockEntity blockEntity;
    private final ContainerData data;
    private final ContainerData inputCounts;
    private final java.util.Set<Integer> dragSlots = new java.util.LinkedHashSet<>();
    private int dragMode = -1;

    public AlchemicalReactorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, (AlchemicalReactorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }
    public AlchemicalReactorMenu(int id, Inventory playerInventory, AlchemicalReactorBlockEntity blockEntity) {
        super(ModMenuTypes.ALCHEMICAL_REACTOR_MENU.get(), id);
        this.blockEntity = blockEntity;
        if (playerInventory.player.level().isClientSide) data = new SimpleContainerData(DATA_VALUES * 2);
        else data = new ContainerData() {
            // Vanilla menu data packets carry signed shorts. Split every int, including tank capacities.
            @Override public int get(int index) { return (blockEntity.dataValue(index / 2) >>> (16 * (index % 2))) & 0xffff; }
            @Override public void set(int index, int value) {}
            @Override public int getCount() { return DATA_VALUES * 2; }
        };
        addDataSlots(data);
        inputCounts = playerInventory.player.level().isClientSide ? new SimpleContainerData(INPUTS) : new ContainerData() {
            @Override public int get(int index) { return blockEntity.getItemHandler().getStackInSlot(index).getCount(); }
            @Override public void set(int index, int value) {}
            @Override public int getCount() { return INPUTS; }
        };
        addDataSlots(inputCounts);
        for (int i = 0; i < INPUTS; i++) addMachineSlot(i, INPUT_POSITIONS[i][0], INPUT_POSITIONS[i][1]);
        addMachineSlot(SAMPLE, 105, 81);
        addMachineSlot(OUTPUT, OUTPUT_X, OUTPUT_Y);
        for (int i = 0; i < 5; i++) addMachineSlot(UPGRADES + i, UPGRADE_POSITIONS[i][0], UPGRADE_POSITIONS[i][1]);
        for (int row = 0; row < 3; row++) for (int col = 0; col < 9; col++)
            addSlot(new Slot(playerInventory, 9 + row * 9 + col, 100 + col * 18, 206 + row * 18));
        for (int col = 0; col < 9; col++) addSlot(new Slot(playerInventory, col, 100 + col * 18, 264));
    }
    private void addMachineSlot(int index, int x, int y) {
        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), index, x, y) {
            // Vanilla item packets carry a byte-sized count. Only send an icon;
            // the full input count travels through the independent data slots.
            @Override public ItemStack getItem() {
                ItemStack stack = super.getItem();
                if (index >= INPUTS || stack.isEmpty()) return stack;
                ItemStack icon = stack.copy(); icon.setCount(1); return icon;
            }
            @Override public int getMaxStackSize() { return index < INPUTS ? 64 : super.getMaxStackSize(); }
            @Override public int getMaxStackSize(ItemStack stack) { return index < INPUTS ? stack.getMaxStackSize() : super.getMaxStackSize(stack); }
            @Override public boolean mayPlace(ItemStack stack) { return blockEntity.getItemHandler().isItemValid(index, stack); }
            @Override public boolean mayPickup(Player player) { return blockEntity.canRemove(index); }
        });
    }
    public int value(int index) { return (data.get(index * 2) & 0xffff) | ((data.get(index * 2 + 1) & 0xffff) << 16); }
    public long totalEnergy() { return (Integer.toUnsignedLong(value(10)) << 32) | Integer.toUnsignedLong(value(8)); }
    public long energySpent() { return (Integer.toUnsignedLong(value(11)) << 32) | Integer.toUnsignedLong(value(9)); }
    public long totalFluid() { return (Integer.toUnsignedLong(value(14)) << 32) | Integer.toUnsignedLong(value(12)); }
    public long fluidSpent() { return (Integer.toUnsignedLong(value(15)) << 32) | Integer.toUnsignedLong(value(13)); }
    public ItemStack animationInput(int slot) { return blockEntity.getAnimationInput(slot); }
    public int inputCount(int slot) { return inputCounts.get(slot); }
    public ItemStack inputStack(int slot) {
        ItemStack stack = blockEntity.getLevel().isClientSide ? getSlot(slot).getItem().copy()
                : blockEntity.getItemHandler().getStackInSlot(slot).copy();
        if (blockEntity.getLevel().isClientSide) stack.setCount(inputCount(slot));
        return stack;
    }
    public void setInputStack(int slot, ItemStack stack) {
        if (slot < 0 || slot >= INPUTS || stack.getCount() > ReactorItemHandler.INPUT_LIMIT)
            throw new IllegalArgumentException("Invalid reactor input stack");
        blockEntity.getItemHandler().setStackInSlot(slot, stack);
    }
    @Override public void clicked(int slotId, int button, ClickType type, Player player) {
        if (type == ClickType.QUICK_CRAFT) {
            if (!player.level().isClientSide) handleDrag(slotId, button, player);
            return;
        }
        dragSlots.clear(); dragMode = -1;
        if (slotId < 0 || slotId >= INPUTS) { super.clicked(slotId, button, type, player); return; }
        // Authoritative input interactions never place an oversized stack on a cursor,
        // in a player's inventory, or in a vanilla slot-sync packet.
        if (player.level().isClientSide) return;
        var handler = blockEntity.getItemHandler();
        ItemStack stored = inputStack(slotId), carried = getCarried();
        if (type == ClickType.QUICK_MOVE) { quickMoveStack(player, slotId); }
        else if (type == ClickType.PICKUP && (button == 0 || button == 1)) {
            if (carried.isEmpty()) {
                if (blockEntity.canRemove(slotId)) {
                    int amount = Math.min(stored.getMaxStackSize(), button == 1 ? (stored.getCount() + 1) / 2 : stored.getCount());
                    setCarried(handler.extractItem(slotId, amount, false));
                }
            } else if (stored.isEmpty() || ItemStack.isSameItemSameTags(stored, carried)) {
                ItemStack offered = carried.copy();
                offered.setCount(button == 1 ? 1 : carried.getCount());
                int inserted = offered.getCount() - handler.insertItem(slotId, offered, false).getCount();
                carried.shrink(inserted); setCarried(carried);
            } else if (blockEntity.canRemove(slotId) && stored.getCount() <= stored.getMaxStackSize()) {
                setInputStack(slotId, carried.copy()); setCarried(stored);
            }
        } else if (type == ClickType.SWAP && (button >= 0 && button < 9 || button == 40)) {
            ItemStack hotbar = player.getInventory().getItem(button);
            if (stored.isEmpty() || !hotbar.isEmpty() && ItemStack.isSameItemSameTags(stored, hotbar)) {
                player.getInventory().setItem(button, handler.insertItem(slotId, hotbar.copy(), false));
            } else if (blockEntity.canRemove(slotId) && hotbar.isEmpty()) {
                player.getInventory().setItem(button, handler.extractItem(slotId, stored.getMaxStackSize(), false));
            } else if (blockEntity.canRemove(slotId) && stored.getCount() <= stored.getMaxStackSize()) {
                setInputStack(slotId, hotbar.copy()); player.getInventory().setItem(button, stored);
            }
        } else if (type == ClickType.THROW && carried.isEmpty() && blockEntity.canRemove(slotId)) {
            player.drop(handler.extractItem(slotId, button == 0 ? 1 : stored.getMaxStackSize(), false), true);
        } else if (type == ClickType.CLONE && player.getAbilities().instabuild && carried.isEmpty() && !stored.isEmpty()) {
            stored.setCount(stored.getMaxStackSize()); setCarried(stored);
        } else if (type == ClickType.PICKUP_ALL) {
            super.clicked(slotId, button, type, player);
        }
        broadcastChanges();
    }
    private void handleDrag(int slotId, int button, Player player) {
        int stage = button & 3, mode = (button >> 2) & 3;
        ItemStack carried = getCarried();
        if (stage == 0) {
            dragSlots.clear();
            dragMode = !carried.isEmpty() && (mode < 2 || mode == 2 && player.getAbilities().instabuild) ? mode : -1;
        } else if (stage == 1 && mode == dragMode && slotId >= 0 && slotId < slots.size()) {
            Slot slot = getSlot(slotId);
            ItemStack current = slotId < INPUTS ? inputStack(slotId) : slot.getItem();
            int limit = slotId < INPUTS ? ReactorItemHandler.INPUT_LIMIT : slot.getMaxStackSize(carried);
            if (!carried.isEmpty() && slot.mayPlace(carried) && (current.isEmpty() || ItemStack.isSameItemSameTags(current, carried))
                    && current.getCount() < limit) dragSlots.add(slotId);
        } else if (stage == 2 && mode == dragMode) {
            int each = dragSlots.isEmpty() ? 0 : dragMode == 0 ? carried.getCount() / dragSlots.size() : 1;
            for (int index : dragSlots) {
                if (carried.isEmpty()) break;
                Slot slot = getSlot(index);
                ItemStack current = index < INPUTS ? inputStack(index) : slot.getItem();
                int limit = index < INPUTS ? ReactorItemHandler.INPUT_LIMIT : slot.getMaxStackSize(carried);
                int amount = Math.min(limit - current.getCount(), dragMode == 2 ? limit : Math.min(each, carried.getCount()));
                if (amount <= 0 || !slot.mayPlace(carried) || !current.isEmpty() && !ItemStack.isSameItemSameTags(current, carried)) continue;
                ItemStack offered = carried.copy(); offered.setCount(amount);
                if (index < INPUTS) amount -= blockEntity.getItemHandler().insertItem(index, offered, false).getCount();
                else amount -= slot.safeInsert(offered).getCount();
                if (dragMode != 2) carried.shrink(amount);
            }
            setCarried(carried); dragSlots.clear(); dragMode = -1; broadcastChanges();
        } else { dragSlots.clear(); dragMode = -1; }
    }
    @Override public ItemStack quickMoveStack(Player player, int index) {
        if (player.level().isClientSide) return ItemStack.EMPTY;
        if (index < 0 || index >= slots.size()) return ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem() || !slot.mayPickup(player)) return ItemStack.EMPTY;
        if (index < INPUTS) {
            ItemStack stored = inputStack(index);
            ItemStack moving = stored.copy(); moving.setCount(Math.min(stored.getCount(), stored.getMaxStackSize()));
            int before = moving.getCount();
            if (!moveItemStackTo(moving, SLOTS, SLOTS + 36, true)) return ItemStack.EMPTY;
            stored.shrink(before - moving.getCount()); setInputStack(index, stored);
            return ItemStack.EMPTY; // One normal stack per shift-click; no oversized vanilla retry loop.
        }
        ItemStack stack = slot.getItem(), original = stack.copy();
        if (index < SLOTS) {
            if (!moveItemStackTo(stack, SLOTS, SLOTS + 36, true)) return ItemStack.EMPTY;
        } else {
            int upgradeSlot = -1;
            for (int i = UPGRADES; i < SLOTS; i++) if (isUpgradeFor(i, stack)) { upgradeSlot = i; break; }
            if (upgradeSlot >= 0) {
                if (!moveItemStackTo(stack, upgradeSlot, upgradeSlot + 1, false)) return ItemStack.EMPTY;
            } else {
                for (int i = 0; i < INPUTS && !stack.isEmpty(); i++) {
                    ItemStack remainder = blockEntity.getItemHandler().insertItem(i, stack.copy(), false);
                    stack.setCount(remainder.getCount());
                }
            }
            // Samples are deliberately placed by hand; shift-click never assigns a recipe.
        }
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }
    @Override public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), blockEntity.getBlockPos()), player, blocklist.ALCHEMICAL_REACTOR.get());
    }
}
