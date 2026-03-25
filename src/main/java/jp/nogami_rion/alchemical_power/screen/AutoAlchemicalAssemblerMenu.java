package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AutoAlchemicalAssemblerBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class AutoAlchemicalAssemblerMenu extends AbstractContainerMenu {

    private final AutoAlchemicalAssemblerBlockEntity blockEntity;
    private final Level level;

    private static final int COLUMNS = 13;
    private static final int TOTAL_ROWS = 13;
    private static final int VISIBLE_ROWS = 3;
    private static final int VISIBLE_COUNT = COLUMNS * VISIBLE_ROWS;
    private int scrollOffset;

    private int firstRow = 0;

    private final List<ScrollableSlot> inputSlots = new ArrayList<>();

    private final ContainerData data;
    private final int[] dataValues = new int[5];
    private final ContainerLevelAccess access;

    public AutoAlchemicalAssemblerMenu(int id, Inventory playerInv, FriendlyByteBuf extraData){
        this(id,playerInv,playerInv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public AutoAlchemicalAssemblerMenu(int id,Inventory playerInv, BlockEntity entity){
        super (ModMenuTypes.AUTO_ALCHEMICAL_ASSEMBLER_MENU.get(),id);

        this.blockEntity = (AutoAlchemicalAssemblerBlockEntity) entity;
        this.level = playerInv.player.level();
        IItemHandler input = blockEntity.getInventoryHandler();
        IItemHandler outPut = blockEntity.getOutputHandler();
        IItemHandler upgrade = blockEntity.getUpgradeHandler();

        // データ同期用
        this.data = new ContainerData() {
            @Override
            public int get(int index){
                if(!level.isClientSide) {
                    return switch (index) {
                        case 0 -> blockEntity.getProgress();
                        case 1 -> blockEntity.getMaxProgress();
                        case 2 -> blockEntity.getEnergyStored();
                        case 3 -> blockEntity.getMaxEnergyStored();
                        case 4 -> blockEntity.getCurrentFEPerTick();
                        default -> 0;
                    };
                }
                return dataValues[index];
            }
            @Override
            public void set(int index,int value){
                dataValues[index] = value;
            }

            @Override
            public int getCount(){
                return 5;
            }
        };
        addDataSlots(this.data);

        // 入力スロット
        for(int row = 0; row < VISIBLE_ROWS; row++){
            for(int col = 0; col < COLUMNS; col++) {
                int actualIndex = row * COLUMNS + col;
                ScrollableSlot slot = new ScrollableSlot(input, actualIndex, 10 + col * 18, 20 + row * 18,() -> this.firstRow,COLUMNS);

                inputSlots.add(slot);
                addSlot(slot);

            }
        }

        // 出力スロット
        addSlot(new SlotItemHandler(outPut,0,118,84){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // アップグレードスロット
        for(int i = 0; i < 4; i++) {
            addSlot(new SlotItemHandler(upgrade,i, 172 + i * 18, 84){
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof UpgradeItem;
                }
            });
        }

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        this.access = ContainerLevelAccess.create(level, blockEntity.getBlockPos());
    }

    private void scrollTo(int newFirstRow){
        this.firstRow = Mth.clamp(newFirstRow,0,TOTAL_ROWS - VISIBLE_ROWS);

//        for(int row = 0; row < VISIBLE_ROWS; row++){
//            for(int col = 0; col < COLUMNS; col++){
//                int visibleIndex = row * COLUMNS + col;
//                int actualIndex = (firstRow + row) * COLUMNS + col;
//                inputSlots.get(visibleIndex).setIndex(actualIndex);
//            }
//        }
    }

    private void addPlayerInventory(Inventory playerInv){
        for(int row = 0; row < 3 ; ++row){
            for(int col = 0; col < 9 ; ++col){
                addSlot(new Slot(playerInv,col + row * 9 + 9,10 + col * 18,114 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInv){
        for (int col = 0; col < 9 ; ++col){
            addSlot(new Slot(playerInv,col,10 + col * 18,172));
        }
    }

    @Override
    public boolean stillValid(Player player){
        return stillValid(access,player, blocklist.AUTO_ALCHEMICAL_ASSEMBLER.get());
    }


    // ===== shiftクリック =====
    @Override
    public ItemStack quickMoveStack(Player player, int index){
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem()){
            ItemStack item = slot.getItem();
            stack = item.copy();

            int machineSlots = VISIBLE_COUNT + 1 + 4;

            if(index < machineSlots){
                if(!moveItemStackTo(item,
                        machineSlots,
                        this.slots.size(),
                        true)){
                    return ItemStack.EMPTY;
                }
            } else {
                if(!moveItemStackTo(item,
                        0,
                        machineSlots,
                        false)){
                    return ItemStack.EMPTY;
                }
            }

            if(item.isEmpty()){
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return stack;
    }

    @Override
    public boolean clickMenuButton(Player player,int id){
        this.firstRow = Mth.clamp(id,0,TOTAL_ROWS - VISIBLE_ROWS);
        return true;
    }

    public int getFirstRow(){
        return firstRow;
    }

    // ===== GUI用 getter =====
    public int getProgress() { return data.get(0); }
    public int getMaxProgress() { return data.get(1); }
    public int getEnergyStored() { return data.get(2); }
    public int getMaxEnergyStored() { return data.get(3); }
    public int getEnergyPerTick() { return data.get(4); }

}
