package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.PanakeiaExtractorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.SlotItemHandler;

public class PanakeiaExtractorMenu extends AbstractContainerMenu {
    private final PanakeiaExtractorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public PanakeiaExtractorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory,
                (PanakeiaExtractorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(9));
    }

    public PanakeiaExtractorMenu(int id, Inventory inventory, PanakeiaExtractorBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.PANAKEIA_EXTRACTOR_MENU.get(), id);
        checkContainerSize(inventory, 5);
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        // Menu packets carry signed shorts; preserve both halves of fluid and energy values.
        this.data = level.isClientSide ? new SimpleContainerData(data.getCount() * 2) : new ContainerData() {
            @Override public int get(int index) { return (data.get(index / 2) >>> (16 * (index % 2))) & 0xffff; }
            @Override public void set(int index, int value) {}
            @Override public int getCount() { return data.getCount() * 2; }
        };

        addDataSlots(this.data);

        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, PanakeiaExtractorLayout.MATERIAL_X, PanakeiaExtractorLayout.MATERIAL_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return PanakeiaExtractorBlockEntity.isPanakeia(stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, PanakeiaExtractorLayout.WATER_X, PanakeiaExtractorLayout.CONTAINER_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return FluidUtil.getFluidHandler(stack).isPresent();
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 2, PanakeiaExtractorLayout.OUTPUT_X, PanakeiaExtractorLayout.CONTAINER_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return FluidUtil.getFluidHandler(stack).isPresent();
            }
        });
        for (int i = 3; i <= 4; i++) {
            int upgradeIndex = i;
            LockedUpgradeSlot upgradeSlot = new LockedUpgradeSlot(blockEntity.getItemHandler(), i,
                    i == 3 ? PanakeiaExtractorLayout.ENERGY_UPGRADE_X : PanakeiaExtractorLayout.TANK_UPGRADE_X,
                    PanakeiaExtractorLayout.CONTAINER_Y,
                    level.isClientSide, () -> blockEntity.getUpgradeRemovalState(upgradeIndex));
            addSlot(upgradeSlot);
            addDataSlots(upgradeSlot.syncData());
        }

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem() && slot.mayPickup(player)) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            if (index < 5) {
                if (!this.moveItemStackTo(stack, 5, 41, true)) return ItemStack.EMPTY;
            } else if (PanakeiaExtractorBlockEntity.isPanakeia(stack)) {
                if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (PanakeiaExtractorBlockEntity.isEnergyUpgrade(stack)) {
                if (!this.moveItemStackTo(stack, 3, 4, false)) return ItemStack.EMPTY;
            } else if (PanakeiaExtractorBlockEntity.isTankUpgrade(stack)) {
                if (!this.moveItemStackTo(stack, 4, 5, false)) return ItemStack.EMPTY;
            } else if (FluidUtil.getFluidHandler(stack).isPresent()) {
                if (!this.moveItemStackTo(stack, 1, 3, false)) return ItemStack.EMPTY;
            } else {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, blocklist.PANAKEIA_EXTRACTOR.get());
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, PanakeiaExtractorLayout.INVENTORY_X + col * 18, PanakeiaExtractorLayout.INVENTORY_Y + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, PanakeiaExtractorLayout.INVENTORY_X + col * 18, PanakeiaExtractorLayout.HOTBAR_Y));
        }
    }

    private int value(int index) {
        return (data.get(index * 2) & 0xffff) | ((data.get(index * 2 + 1) & 0xffff) << 16);
    }

    public int getProgress() { return value(0); }
    public int getMaxProgress() { return value(1); }
    public int getEnergy() { return value(2); }
    public int getMaxEnergy() { return value(3); }
    public int getWater() { return value(4); }
    public int getMaxWater() { return value(5); }
    public int getOutputFluid() { return value(6); }
    public int getMaxOutputFluid() { return value(7); }
    public int getActiveOutput() { return value(8); }
}
