package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.Hermes_Workbench_Entity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class Hermes_Workbench_Menu extends AbstractContainerMenu {
    public final Hermes_Workbench_Entity blockEntity;
    private final Level level;
    private final ContainerData data;

    public Hermes_Workbench_Menu(int pContainerid, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerid,inv,inv.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(27));
    }

    public Hermes_Workbench_Menu(int pContainerid, Inventory inv, BlockEntity entity, ContainerData data){
        super(ModMenuTypes.HERMES_WORKBENCH_MENU.get(),pContainerid);
        checkContainerSize(inv,27);
        blockEntity = ((Hermes_Workbench_Entity) entity);
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
//            this.addSlot(new SlotItemHandler(iItemHandler, 0,10, 20));
//            this.addSlot(new SlotItemHandler(iItemHandler, 1,28, 20));
//            this.addSlot(new SlotItemHandler(iItemHandler, 2,46, 20));
//            this.addSlot(new SlotItemHandler(iItemHandler, 3,64, 20));
//            this.addSlot(new SlotItemHandler(iItemHandler, 4,82, 20));
//            this.addSlot(new SlotItemHandler(iItemHandler, 5,10, 39));
//            this.addSlot(new SlotItemHandler(iItemHandler, 6,28, 39));
//            this.addSlot(new SlotItemHandler(iItemHandler, 7,46, 39));
//            this.addSlot(new SlotItemHandler(iItemHandler, 8,64, 39));
//            this.addSlot(new SlotItemHandler(iItemHandler, 9,82, 39));
//            this.addSlot(new SlotItemHandler(iItemHandler, 10,10, 58));
//            this.addSlot(new SlotItemHandler(iItemHandler, 11,28, 58));
//            this.addSlot(new SlotItemHandler(iItemHandler, 12,46, 58));
//            this.addSlot(new SlotItemHandler(iItemHandler, 13,64, 58));
//            this.addSlot(new SlotItemHandler(iItemHandler, 14,82, 58));
//            this.addSlot(new SlotItemHandler(iItemHandler, 15,10, 77));
//            this.addSlot(new SlotItemHandler(iItemHandler, 16,28, 77));
//            this.addSlot(new SlotItemHandler(iItemHandler, 17,46, 77));
//            this.addSlot(new SlotItemHandler(iItemHandler, 18,64, 77));
//            this.addSlot(new SlotItemHandler(iItemHandler, 19,82, 77));
//            this.addSlot(new SlotItemHandler(iItemHandler, 20,10, 96));
//            this.addSlot(new SlotItemHandler(iItemHandler, 21,28, 96));
//            this.addSlot(new SlotItemHandler(iItemHandler, 22,46, 96));
//            this.addSlot(new SlotItemHandler(iItemHandler, 23,64, 96));
//            this.addSlot(new SlotItemHandler(iItemHandler, 24,82, 96));

            for (int i = 0; i < 5; ++i) {
                for (int l = 0; l < 5; ++l) {
                    this.addSlot(new SlotItemHandler(iItemHandler, l + i * 5, 10 + l * 18, 20 + i * 18){
                        @Override
                        public boolean mayPickup(Player player) {
                            return true;
                        }
                    });
                }
            }
            this.addSlot(new SlotItemHandler(iItemHandler, 25,109, 56){
                @Override
                public boolean mayPickup(Player player) {
                    return true;
                }
            });
            this.addSlot(new SlotItemHandler(iItemHandler, 26,163, 56));

        });

        addDataSlots(data);


    }

    public boolean isCrafting(){
        return data.get(0) > 0;
    }

    public int getScaledProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 16;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress:0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 27;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level,blockEntity.getBlockPos()),
                player, blocklist.HERMES_WORKBENCH.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 20 + l * 18, 114 + i * 19));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 20 + i * 18, 172));
        }
    }
}
