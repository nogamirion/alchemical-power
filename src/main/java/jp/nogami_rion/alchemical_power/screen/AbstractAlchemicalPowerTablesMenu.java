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
import net.minecraft.world.item.crafting.Recipe;
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
    private boolean dirty = false;
    private long observedRevision = -1;
    private Recipe<? super AlchemicalPowerTablesContainerView> currentRecipe;

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

//        long debugStart = System.nanoTime();

        addGridSlots();
        addToolSlot();
        addResultSlot();
        addPlayerInventory(playerInv);
        setupResultSlot();
//
////        //デバッグ用ログ
//        long debugEnd = System.nanoTime();
//        System.out.println("[Alchemical Power] Menu initialization: "
//                + ((debugEnd - debugStart)/1_000_000.0) + "ms");

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
        if (index == resultSlotIndex && !canTakeResult()) return ItemStack.EMPTY;
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
            if (index == resultSlotIndex) player.drop(stack, false);
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
        if(!player.level().isClientSide){
            dirty = true;
//            setupResultSlot();
        }
    }

    @Override
    public void broadcastChanges(){
        if(!player.level().isClientSide && (dirty || observedRevision != grid.getRevision())){
            setupResultSlot();
        }

        super.broadcastChanges();
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (!player.level().isClientSide) {
            ItemStack previousResult = resultContainer.getItem(0).copy();
            if (dirty || observedRevision != grid.getRevision()) setupResultSlot();
            // Do not turn a click on an old preview into a different craft.
            if (slotId == resultSlotIndex
                    && !ItemStack.matches(previousResult, resultContainer.getItem(0))) {
                broadcastChanges();
                return;
            }
        }
        super.clicked(slotId, button, clickType, player);
    }

    public boolean canTakeResult() {
        if (player.level().isClientSide) return !resultContainer.getItem(0).isEmpty();
        // Never replace the result here: callers may already hold its ItemStack.
        return !dirty && observedRevision == grid.getRevision()
                && currentRecipe != null && !resultContainer.getItem(0).isEmpty()
                && currentRecipe.matches(view, player.level())
                && ItemStack.matches(resultContainer.getItem(0),
                        currentRecipe.assemble(view, player.level().registryAccess()));
    }

    public void finishResultTake() {
        resultContainer.setItem(0, ItemStack.EMPTY);
        setupResultSlot();
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    private void setupResultSlot(){
//        long debugStart = System.nanoTime();

        if(player.level().isClientSide) return;
        observedRevision = grid.getRevision();
        dirty = false;
        currentRecipe = null;

//        long debugAPRecipeStart = System.nanoTime();

        Optional<AlchemicalPowerTablesRecipe> apRecipe = player.level().getRecipeManager().getRecipeFor(ModRecipes.ALCHEMICAL_POWER_TABLES_TYPE.get(),view,player.level());

//        //デバッグ用ログ
//        long debugAPRecipeEnd = System.nanoTime();
//
//        System.out.println(
//                "[AlchemicalPower] Alchemical recipe search: "
//                        + ((debugAPRecipeEnd - debugAPRecipeStart) / 1_000_000.0)
//                        + " ms"
//        );

        if(apRecipe.isPresent()){
            currentRecipe = apRecipe.get();
            isCustomRecipe = true;
            ItemStack result = apRecipe.get().assemble(view,player.level().registryAccess());
            if(!ItemStack.matches(resultContainer.getItem(0),result)) {
                resultContainer.setItem(0, result);
                resultContainer.setChanged();
            }
//            broadcastChanges();
            return;
        }
        isCustomRecipe = false;

        //入力が空ならバニラレシピの検索をスキップ
        boolean hasInput = false;
        for (int i = 0; i < view.getContainerSize(); i++){
            if(!view.getItem(i).isEmpty()) {
                hasInput = true;
                break;
            }
        }

        if(!hasInput){
            if(!resultContainer.getItem(0).isEmpty()) {
                resultContainer.setItem(0,ItemStack.EMPTY);
                resultContainer.setChanged();
            }
            return;
        }

//        long debugCraftingStart = System.nanoTime();

        Optional<CraftingRecipe> vanilla =
                player.level().getRecipeManager().getRecipeFor(RecipeType.CRAFTING,view,player.level());
        currentRecipe = vanilla.orElse(null);
//
//        long debugCraftingEnd = System.nanoTime();
//
//        System.out.println(
//                "[AlchemicalPower] Crafting recipe search: "
//                        + ((debugCraftingEnd - debugCraftingStart) / 1_000_000.0)
//                        + " ms"
//        );

        ItemStack result = vanilla.map(r -> r.assemble(view,player.level().registryAccess()))
                .orElse(ItemStack.EMPTY);

        if(!ItemStack.matches(resultContainer.getItem(0),result)) {
            resultContainer.setItem(0, result);
            resultContainer.setChanged();
        }


//        long debugEnd = System.nanoTime();
//
//        System.out.println(
//                "[AlchemicalPower] setupResultSlot: "
//                        + ((debugEnd - debugStart) / 1_000_000.0)
//                        + " ms"
//        );
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
