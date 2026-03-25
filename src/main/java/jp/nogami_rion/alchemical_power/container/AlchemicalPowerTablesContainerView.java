package jp.nogami_rion.alchemical_power.container;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.grid.AlchemicalTableGrid;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AlchemicalPowerTablesContainerView implements CraftingContainer {

    private final AlchemicalTableGrid grid;
    private final AbstractAlchemicalTableBlockEntity blockEntity;

    public AlchemicalPowerTablesContainerView(AbstractAlchemicalTableBlockEntity blockEntity){
        this.blockEntity = blockEntity;
        this.grid = blockEntity.getGrid();
    }

    private int size(){
        return grid.getGridSize();
    }

    public AlchemicalTableGrid getGrid(){
        return grid;
    }

    public AbstractAlchemicalTableBlockEntity getBlockEntity(){
        return blockEntity;
    }

    @Override
    public int getWidth(){
        return grid.getGridSize();
    }

    @Override
    public int getHeight(){
        return grid.getGridSize();
    }

    @Override
    public List<ItemStack> getItems() {
        return grid.getAllItems();
    }

    @Override
    public int getContainerSize() {
        return size() * size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0 ; i < getContainerSize() ; i++){
            if(!getItem(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return grid.getItem(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return grid.removeItem(index,amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return grid.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        grid.setItem(index,stack);
    }

    @Override
    public void setChanged() {
        grid.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0 ; i < getContainerSize() ; i++){
            setItem(i,ItemStack.EMPTY);
        }
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for(int i = 0;i < getContainerSize(); i++){
            contents.accountStack(getItem(i));
        }
    }
}
