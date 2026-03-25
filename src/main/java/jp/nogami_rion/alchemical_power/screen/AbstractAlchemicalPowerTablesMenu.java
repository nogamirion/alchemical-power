package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.container.AlchemicalPowerTablesContainerView;
import jp.nogami_rion.alchemical_power.grid.AlchemicalTableGrid;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Optional;

public abstract class AbstractAlchemicalPowerTablesMenu extends AbstractContainerMenu {

    public final AbstractAlchemicalTableBlockEntity blockEntity;
    protected final int size;
    public final int gridSlotCount;
    public final int toolSlotIndex;
    public final int resultSlotIndex;
    public final int playerInvStart;
    private final AlchemicalPowerTablesContainerView view;
    private final ResultContainer resultContainer = new ResultContainer();
    private final Player player;
    protected final AlchemicalTableGrid grid;
    private boolean isCustomRecipe = false;
    private boolean playAnimation = false;
    protected final AlchemicalPowerTablesLayout layout;

    protected AbstractAlchemicalPowerTablesMenu(
            MenuType<?> type,
            int id,
            Inventory playerInv,
            AbstractAlchemicalTableBlockEntity blockEntity,
            AlchemicalPowerTablesLayout layout
    ) {
        super(type, id);

        this.blockEntity = blockEntity;
        this.size = blockEntity.getGrid().getGridSize();
        this.grid = blockEntity.getGrid();
        this.layout = layout;
        this.gridSlotCount = size * size;
        this.toolSlotIndex = gridSlotCount;
        this.resultSlotIndex = gridSlotCount + 1;
        this.playerInvStart = gridSlotCount + 2;
        this.view = new AlchemicalPowerTablesContainerView(blockEntity);
        this.player = playerInv.player;

        addGridSlots();
        addToolSlot();
        addResultSlot();
        addPlayerInventory(playerInv);
        grid.setOnChanged(this::setupResultSlot);
        setupResultSlot();
    }

    private void addGridSlots() {
        int slotSize = 18;

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                int index = y * size + x;

                addSlot(new Slot(grid,index,
                        layout.gridStartX() + x * slotSize,
                        layout.gridStartY() + y * slotSize){
                    @Override
                    public void setChanged(){
                        super.setChanged();
                        blockEntity.setChanged();
                    }
                });
            }
        }
    }

    private void addToolSlot(){

        addSlot(new Slot(grid, toolSlotIndex, layout.toolX() , layout.toolY()){
            @Override
            public void setChanged(){
                super.setChanged();
                blockEntity.setChanged();
            }
        });
    }

    private void addResultSlot(){
        addSlot(new AlchemicalTablesResultSlot(player,view,resultContainer,blockEntity,this,0,layout.resultX(),layout.resultY()));
    }

    private void addPlayerInventory(Inventory inv) {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inv, col + row * 9 + 9,
                        layout.playerInvStartX() + col * 18,
                        layout.playerInvStartY() + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inv, col,
                    layout.playerInvStartX() + col * 18,
                    layout.playerInvStartY() + 58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack original = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if(slot != null && slot.hasItem()){
            ItemStack stack = slot.getItem();
            original = stack.copy();

            int gridStart = 0;
            int gridEnd = gridSlotCount;
            int toolSlot = gridSlotCount;
            int playerInvEnd = this.slots.size();

            // resultスロット
            if(index == resultSlotIndex){
                if(!this.moveItemStackTo(stack,playerInvStart,playerInvEnd,true)){
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack,original);
            }
            // プレイヤーインベントリ
            else if(index >= playerInvStart){
                //toolに入るか？
                if(isTool(stack)) {
                    if (!this.moveItemStackTo(stack, toolSlot, toolSlot + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                //それ以外はグリッドへ
                else {
                    if (!this.moveItemStackTo(stack, gridStart, gridEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                }

            }
            //グリッドからプレイヤーインベントリへ
            else{
                if(!this.moveItemStackTo(stack,playerInvStart,playerInvEnd,false)){
                    return ItemStack.EMPTY;
                }
            }
            if(stack.isEmpty()){
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if(stack.getCount() == original.getCount()){
                return ItemStack.EMPTY;
            }

            slot.onTake(player,stack);
        }
        return original;
    }

    @Override
    public boolean stillValid(Player player){
        return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(),blockEntity.getBlockPos()),player,blockEntity.getBlockState().getBlock());
    }

    @Override
    public void slotsChanged(Container container){
        super.slotsChanged(container);
//        setupResultSlot();

    }

    private void setupResultSlot(){
        if(player.level().isClientSide) return;

        Optional<AlchemicalPowerTablesRecipe> apRecipe = player.level().getRecipeManager().getRecipeFor(ModRecipes.ALCHEMICAL_POWER_TABLES_TYPE.get(),view,player.level());

        if(apRecipe.isPresent()){
            isCustomRecipe = true;
            ItemStack result = apRecipe.get().assemble(view,player.level().registryAccess());
            resultContainer.setItem(0,result);
            resultContainer.setChanged();
            broadcastChanges();
            return;
        } else {
            isCustomRecipe = false;
        }

        Optional<CraftingRecipe> vanilla = player.level().getRecipeManager().getRecipeFor(RecipeType.CRAFTING,view,player.level());

        if(vanilla.isPresent()){
            ItemStack result = vanilla.get().assemble(view,player.level().registryAccess());
            resultContainer.setItem(0,result);
        } else {
            resultContainer.setItem(0,ItemStack.EMPTY);
        }

        resultContainer.setChanged();
        broadcastChanges();
    }

    public boolean isCustomRecipe() {
        return isCustomRecipe;
    }

    public boolean isTool(ItemStack stack){
        return stack.isDamageableItem();
    }

    public void triggerPlayAnimation(){
        this.playAnimation = true;
    }

    public boolean shouldPlayAnimation(){
        return playAnimation;
    }

    public void resetPlayAnimation(){
        this.playAnimation = false;
    }

    public Slot getResultSlot(){
        return this.slots.get(resultSlotIndex);
    }

}
