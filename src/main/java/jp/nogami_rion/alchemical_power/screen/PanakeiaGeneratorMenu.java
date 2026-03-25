package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.PanakeiaGeneratorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class PanakeiaGeneratorMenu extends AbstractContainerMenu {
    private final PanakeiaGeneratorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public PanakeiaGeneratorMenu(int id, Inventory inventory, FriendlyByteBuf buf){
        this(id,inventory,
                (PanakeiaGeneratorBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(5));
    }

    public PanakeiaGeneratorMenu(int id, Inventory inventory, PanakeiaGeneratorBlockEntity blockEntity,ContainerData data){
        super(ModMenuTypes.PANAKEIA_GENERATOR_MENU.get(),id);
        checkContainerSize(inventory,6);
        this.blockEntity = blockEntity;
        this.level = inventory.player.level();
        this.data = data;

        addDataSlots(data);

        //inventory

        //燃料
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 23, 32) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isFuel(stack);
            }
        });
        //触媒
        this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 1, 77, 32){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isCatalyst(stack);
            }
        });
        //アプグレ
        for(int i = 2; i <= 5;i++) {
            int slotPosition = i - 2;
            this.addSlot(new SlotItemHandler(blockEntity.getItemHandler(), i, 149, 5 + (slotPosition * 18)){
                @Override
                public boolean mayPlace(ItemStack stack){
                    return isUpgrade(stack);
                }
                @Override
                public int getMaxStackSize(){
                    return 1;
                }

            });
        }

        //playerInventory
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {

            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            if (index < 6) {
                if (!this.moveItemStackTo(stack, 6, 42, true))
                    return ItemStack.EMPTY;
            }
            else {
                if(isFuel(stack)){
                    if(!this.moveItemStackTo(stack,0,1,false));
                    return ItemStack.EMPTY;
                }
                else if (isCatalyst(stack)){
                    if(!this.moveItemStackTo(stack,1,2,false));
                    return ItemStack.EMPTY;
                }
                else if (isUpgrade(stack)){
                    if(!this.moveItemStackTo(stack,2,6,false));
                    return ItemStack.EMPTY;
                }
                else{
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty())
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player){
        return stillValid(ContainerLevelAccess.create(level,blockEntity.getBlockPos()),player, blocklist.PANAKEIA_GENERATOR.get());
    }

    private void addPlayerInventory(Inventory inventory) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {

                this.addSlot(new Slot(inventory,
                        col + row * 9 + 9,
                        5 + col * 18,
                        90 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inventory) {

        for (int col = 0; col < 9; col++) {

            this.addSlot(new Slot(inventory,
                    col,
                    5 + col * 18,
                    148));
        }
    }

    private boolean isFuel(ItemStack stack){
        return PanakeiaGeneratorBlockEntity.PANAKEIA_TIERS.containsKey(stack.getItem());
    }

    private boolean isCatalyst(ItemStack stack){
        return PanakeiaGeneratorBlockEntity.CATALYST_BASE.containsKey(stack.getItem());
    }

    private boolean isUpgrade(ItemStack stack){
        if(!(stack.getItem() instanceof UpgradeItem upgrade))
            return false;

        return upgrade.getType() == UpgradeType.ENERGY;
    }

    //GUI用getter
    public int getBurnTime() {
        return data.get(0);
    }

    public int getEnergy() {
        return data.get(1);
    }

    public int getMaxEnergy(){
        return data.get(2);
    }

    public ItemStack getFuelStack() {
        return blockEntity.getFuelStack();
    }

    public ItemStack getCatalystStack() {
        return blockEntity.getCatalystStack();
    }

    public ItemStack getActiveFuel(){
        return new ItemStack(Item.byId(data.get(3)));
    }

    public int getOutput(){
        return data.get(4);
    }

}
