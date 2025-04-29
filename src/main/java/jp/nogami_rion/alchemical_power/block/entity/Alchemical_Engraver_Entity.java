package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.recipe.Alchemical_Engraver_Recipe;
import jp.nogami_rion.alchemical_power.screen.Alchemical_Engraver_Menu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Alchemical_Engraver_Entity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4);

    private static final int INPUT_SLOT = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int INPUT_SLOT3 = 2;
    private static final int OUTPUT_SLOT = 3;

    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;

    public Alchemical_Engraver_Entity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALCHEMICAL_ENGRAVER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> Alchemical_Engraver_Entity.this.progress;
                    case 1 -> Alchemical_Engraver_Entity.this.maxProgress;
                    default -> 0;
                };

            }

             @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> Alchemical_Engraver_Entity.this.progress = pValue;
                    case 1 -> Alchemical_Engraver_Entity.this.maxProgress = pValue;
                }

             }

             @Override
            public int getCount() {
                return 2;
            }
        };

    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return LazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer((itemHandler.getSlots()));
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.alchemical_power.alchemical_engraver");
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new Alchemical_Engraver_Menu(i,inventory,this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.putInt("alchemical_engraver_progress",progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("alchemical_engraver_progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()){
            increaseCraftingProgress();
            setChanged(pLevel,pPos,pState);
            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress(){
        progress = 0;
    }

    private void craftItem() {
        Optional<Alchemical_Engraver_Recipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        extractItem();

        itemHandler.setStackInSlot(OUTPUT_SLOT,new ItemStack(result.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

    }
    public void extractItem(){

        for (int i : new int[]{INPUT_SLOT, INPUT_SLOT2,}) {
            this.itemHandler.extractItem(i, 1, false);
        }
        ItemStack _stk = this.itemHandler.getStackInSlot(INPUT_SLOT3).copy();
        if(_stk.hurt(1, RandomSource.create(),null)){
            _stk.shrink(1);
            _stk.setDamageValue(0);
        }
        this.itemHandler.setStackInSlot(INPUT_SLOT3,_stk);
    }

    private boolean hasRecipe() {
        Optional<Alchemical_Engraver_Recipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return false;
        }
        ItemStack result = recipe.get().getResultItem(null);

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<Alchemical_Engraver_Recipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots();i++){
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(Alchemical_Engraver_Recipe.Type.INSTANCE,inventory,level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished(){
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress(){
        progress++;
    }


}
