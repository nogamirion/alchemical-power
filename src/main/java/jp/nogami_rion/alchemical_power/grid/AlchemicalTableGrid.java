package jp.nogami_rion.alchemical_power.grid;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class AlchemicalTableGrid implements Container {

    private final int size;
    private final NonNullList<ItemStack> items;

    private Runnable onChanged = () -> {
    };

    public AlchemicalTableGrid(int size) {
        this.size = size;
        this.items = NonNullList.withSize(size * size + 1, ItemStack.EMPTY);
    }

    public int getGridSize() {
        return size;
    }

    public void setOnChanged(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    // ===== Container 実装 =====

    @Override
    public void setChanged() {
        if(onChanged != null)
            onChanged.run();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        items.set(index, stack);
        setChanged();
    }

    public void setItem(int x , int y ,ItemStack stack){
        setItem(y * size + x,stack);
    }


    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack result = ContainerHelper.removeItem(items, index, count);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    public ItemStack removeItem(int x, int y, int count){
        return removeItem(y * size + x,count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(items, index);
    }

    public ItemStack removeItemNoUpdate(int x,int y){
        return removeItemNoUpdate(y * size + x);
    }

    @Override
    public ItemStack getItem(int index) {
        return items.get(index);
    }

    public ItemStack getItem(int x,int y){
        return getItem(y * size + x);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }

    public Container getRecipeContainerView(){
        AlchemicalTableGrid parent = this;
        return new Container() {
            @Override
            public int getContainerSize() {
                return parent.getGridSize() * parent.getGridSize();
            }

            @Override
            public boolean isEmpty() {
                for (int i = 0; i < getContainerSize(); i++) {
                    if (!getItem(i).isEmpty()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public ItemStack getItem(int index) {
                return parent.getItem(index);
            }

            @Override
            public ItemStack removeItem(int index, int count) {
                return parent.removeItem(index, count);
            }

            @Override
            public ItemStack removeItemNoUpdate(int index) {
                return parent.removeItemNoUpdate(index);
            }

            @Override
            public void setItem(int index, ItemStack stack) {
                parent.setItem(index, stack);
            }

            @Override
            public void setChanged() {
                parent.setChanged();
            }

            @Override
            public boolean stillValid(Player player) {
                return true;
            }

            @Override
            public void clearContent() {
                for(int i = 0; i < getContainerSize(); i++){
                    parent.setItem(i, ItemStack.EMPTY);
                }
            }
        };
    }

    // ===== 便利メソッド =====

    public NonNullList<ItemStack> getAllItems() {
        return items;
    }
}