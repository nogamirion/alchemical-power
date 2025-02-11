package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.recipe.Hermes_Workbench_Recipe;
import jp.nogami_rion.alchemical_power.screen.Hermes_Workbench_Menu;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hermes_Workbench_Entity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(27);

    private static final List<Integer> INPUT_SLOT = new ArrayList<Integer>();
    private static int OUTPUT_SLOT;
//    public void listup(){
//        for (int i =0; i < this.itemHandler.getSlots();i++){
//            if(i != this.itemHandler.getSlots()){
//                INPUT_SLOT.add(i);
//            } else if (i == this.itemHandler.getSlots()) {
//                OUTPUT_SLOT = i;
//            }
//
//        }
//    }




//    private static final int INPUT_SLOT = 0;
//    private static final int INPUT_SLOT2 = 1;
//    private static final int INPUT_SLOT3 = 2;
//    private static final int INPUT_SLOT4 = 3;
//    private static final int INPUT_SLOT5 = 4;
//    private static final int INPUT_SLOT6 = 5;
//    private static final int INPUT_SLOT7 = 6;
//    private static final int INPUT_SLOT8 = 7;
//    private static final int INPUT_SLOT9 = 8;
//    private static final int INPUT_SLOT10 = 9;
//    private static final int INPUT_SLOT11 = 10;
//    private static final int INPUT_SLOT12 = 11;
//    private static final int INPUT_SLOT13 = 12;
//    private static final int INPUT_SLOT14 = 13;
//    private static final int INPUT_SLOT15 = 14;
//    private static final int INPUT_SLOT16 = 15;
//    private static final int INPUT_SLOT17 = 16;
//    private static final int INPUT_SLOT18 = 17;
//    private static final int INPUT_SLOT19 = 18;
//    private static final int INPUT_SLOT20 = 19;
//    private static final int INPUT_SLOT21 = 20;
//    private static final int INPUT_SLOT22 = 21;
//    private static final int INPUT_SLOT23 = 22;
//    private static final int INPUT_SLOT24 = 23;
//    private static final int INPUT_SLOT25 = 24;
//    private static final int INPUT_SLOT26 = 25;
//    private static final int OUTPUT_SLOT = 26;

    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 1;

    public Hermes_Workbench_Entity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HERMES_WORKBENCH_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> Hermes_Workbench_Entity.this.progress;
                    case 1 -> Hermes_Workbench_Entity.this.maxProgress;
                    default -> 0;
                };

            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> Hermes_Workbench_Entity.this.progress = pValue;
                    case 1 -> Hermes_Workbench_Entity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            if (i != this.itemHandler.getSlots()-1) {
                INPUT_SLOT.add(i);
            } else if (i == this.itemHandler.getSlots()-1) {
                OUTPUT_SLOT = i;
            }
        }
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
        return Component.translatable("block.alchemical_power.hermes_workbench");
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new Hermes_Workbench_Menu(i,inventory,this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());
        pTag.putInt("hermes_workbench_progress",progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("hermes_workbench_progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasRecipe()){
            increaseCraftingProgress();
            setChanged(pLevel,pPos,pState);
            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        }else{
            resetProgress();
        }
    }

    private void resetProgress(){
        progress = 0;
    }

    private void craftItem() {
        Optional<Hermes_Workbench_Recipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        for (int i =0; i < this.itemHandler.getSlots()-1;i++){
            if(i != this.itemHandler.getSlots()-2) {
                this.itemHandler.extractItem(INPUT_SLOT.get(i), 1, false);
            } else if (i == this.itemHandler.getSlots()-2) {
                ItemStack _stk = this.itemHandler.getStackInSlot(INPUT_SLOT.get(i)).copy();
                if(_stk.hurt(1, RandomSource.create(),null)){
                    _stk.shrink(1);
                    _stk.setDamageValue(0);
                }
                this.itemHandler.setStackInSlot(INPUT_SLOT.get(i),_stk);
            }
        }
//        this.itemHandler.extractItem(INPUT_SLOT,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT2,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT3,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT4,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT5,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT6,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT7,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT8,1,false);
//        this.itemHandler.extractItem(INPUT_SLOT9,1,false);
//        ItemStack _stk = this.itemHandler.getStackInSlot(INPUT_SLOT26).copy();
//        if(_stk.hurt(1, RandomSource.create(),null)){
//            _stk.shrink(1);
//            _stk.setDamageValue(0);
//        }
//        this.itemHandler.setStackInSlot(INPUT_SLOT26,_stk);


        this.itemHandler.setStackInSlot(OUTPUT_SLOT,new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

    }

    private boolean hasRecipe() {
        Optional<Hermes_Workbench_Recipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()){
            return false;
        }
        ItemStack result = recipe.get().getResultItem(null);

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<Hermes_Workbench_Recipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots();i++){
            inventory.setItem(i,this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(Hermes_Workbench_Recipe.Type.INSTANCE,inventory,level);
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
