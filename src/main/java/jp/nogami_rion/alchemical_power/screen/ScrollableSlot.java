package jp.nogami_rion.alchemical_power.screen;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.function.IntSupplier;

public class ScrollableSlot extends SlotItemHandler {

    private int actualIndex;
    private final IItemHandler handler;
    private final IntSupplier firstRowSupplier;
    private final int columns;

    public ScrollableSlot(IItemHandler handler, int index, int x, int y, IntSupplier firstRowSupplier,int columns){
        super(handler,index,x,y);
        this.handler = handler;
        this.actualIndex = index;
        this.firstRowSupplier = firstRowSupplier;
        this.columns = columns;
    }

    public void setIndex(int index){
        this.actualIndex = index;
    }

    private int getActualIndex(){
        int firstRow = firstRowSupplier.getAsInt();
        return (firstRow * columns) + actualIndex;
    }

    @Override
    public int getSlotIndex(){
        return getActualIndex();
    }

    @Override
    public ItemStack getItem(){
        return handler.getStackInSlot(getActualIndex());
    }

    @Override
    public void set(ItemStack stack){
        if(handler instanceof IItemHandlerModifiable modifiable){
            modifiable.setStackInSlot(getActualIndex(),stack);
        }
        setChanged();
    }

    @Override
    public boolean hasItem() {
        return !getItem().isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return handler.extractItem(getActualIndex(), amount, false);
    }

}
