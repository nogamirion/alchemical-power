package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.grid.AlchemicalTableGrid;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;

import java.util.List;


public class AbstractAlchemicalTableBlockEntity extends BlockEntity {

    protected final AlchemicalTableGrid grid;
    protected ItemStack result = ItemStack.EMPTY;
    private final int craftingSize;
    private final int toolIndex;
    private final int size;

    protected AbstractAlchemicalTableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size){
        super(type, pos, state);
        this.grid = new AlchemicalTableGrid(size);
        this.grid.setOnChanged(this::setChanged);
        this.size = size;
        this.craftingSize = size * size;
        this.toolIndex = craftingSize;
    }

    public AlchemicalTableGrid getGrid(){
        return grid;
    }

    public int getSize(){
        return size;
    }

    public int getCraftingSize(){
        return craftingSize;
    }

    public ItemStack getTool(){
        return grid.getItem(toolIndex);
    }

    public void setTool(ItemStack stack){
        grid.setItem(toolIndex,stack);
        setChanged();

        if(level != null && !level.isClientSide){
            level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);
        }

    }

    public void clearTool(){
        grid.setItem(toolIndex,ItemStack.EMPTY);
        setChanged();
    }

    public void consumeTool(Player player){
        ItemStack tool = grid.getItem(toolIndex);

        if(tool.isEmpty()) return;

        if(tool.isDamageableItem()){
            tool.hurt(1,level.getRandom(),null);
            if(tool.getDamageValue() >= tool.getMaxDamage()){
                tool.shrink(1);
            }
        } else {
            return;
        }

        if(tool.getCount() <= 0){
            grid.setItem(toolIndex,ItemStack.EMPTY);
        }

        setChanged();
    }

    public ItemStack getResult(){
        return result;
    }

    public void setResult(ItemStack stack){
        this.result = stack;
        setChanged();
    }

    public void clearResult(){
        this.result = ItemStack.EMPTY;
        setChanged();
    }

    public void consumeInputs() {
        for (int i = 0; i < craftingSize; i++) {
            ItemStack stack = grid.getItem(i);
            if (!stack.isEmpty()) {
                if(stack.isDamageableItem()){
                    stack.hurt(1,level.getRandom(),null);
                    if(stack.getDamageValue() >= stack.getMaxDamage()){
                        stack.shrink(1);
                    }
                    grid.setItem(i, stack);
                } else if(stack.hasCraftingRemainingItem()){
                    ItemStack remain = stack.getCraftingRemainingItem();
                    if(remain == null){
                        remain = ItemStack.EMPTY;
                    }
                    grid.setItem(i, remain);

                } else {
                    stack.shrink(1);
                    grid.setItem(i, stack);
                }
            }
        }
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag,this.grid.getAllItems());
//        System.out.println("Saving BE");
    }

    @Override
    public void load(CompoundTag tag){
        super.load(tag);
        ContainerHelper.loadAllItems(tag,this.grid.getAllItems());
//        System.out.println("Loading BE");
    }

    public void dropGridContents(){
        if (level == null || level.isClientSide) return;

        for(int i = 0; i < grid.getGridSize() * grid.getGridSize() + 1 ; i++){
            ItemStack stack = grid.getItem(i);
            if(!stack.isEmpty()){
                Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), stack);
            }
        }
    }

}
