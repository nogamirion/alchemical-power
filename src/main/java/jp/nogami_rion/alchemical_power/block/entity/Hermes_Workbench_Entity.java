package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.recipe.Hermes_Workbench_Recipe;
import jp.nogami_rion.alchemical_power.screen.Hermes_Workbench_Menu;
import jp.nogami_rion.alchemical_power.util.ConfigurableItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hermes_Workbench_Entity extends BlockEntity implements MenuProvider {
    private final ConfigurableItemHandler itemHandler = new ConfigurableItemHandler(27,this,
            IntStream.rangeClosed(0,25).boxed().collect(Collectors.toSet()), Set.of(26));

    private static final List<Integer> INPUT_SLOT  = new ArrayList<>();;
    private static int OUTPUT_SLOT;


    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;

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
    public void setRemoved(){
        super.setRemoved();
        LazyItemHandler.invalidate();
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

    @Override
    public CompoundTag getUpdateTag(){
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag){
        load(tag);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (!itemHandler.getStackInSlot(INPUT_SLOT.get(25)).isEmpty()) { // ツールスロットが空でない場合
            if (hasRecipe()) {
                setChanged(pLevel, pPos, pState);
                increaseCraftingProgress();
                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            } else {
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
        Optional<Hermes_Workbench_Recipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        extractItem();
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
//        this.itemHandler.setStackInSlot(INPUT_SLOT26,_s
        this.itemHandler.setStackInSlot(OUTPUT_SLOT,new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));

    }

    private void extractItem(){
        for (int i =0; i < this.itemHandler.getSlots()-1;i++){
            if(i != this.itemHandler.getSlots()-2) {
                this.itemHandler.extractItem(INPUT_SLOT.get(i), 1, false);
            } else if (i == this.itemHandler.getSlots()-2) {
                ItemStack _stk = this.itemHandler.getStackInSlot(INPUT_SLOT.get(i)).copy();
                if(_stk.is(itemlist.PHILOSOPHERS_STONE.get())){
                    return;
                }
                if(_stk.isDamageableItem()) {
                    if (_stk.hurt(1, RandomSource.create(), null)) {
                        _stk.shrink(1);
                        _stk.setDamageValue(0);
                    }
                }else {
                    _stk.shrink(1);
                }
                this.itemHandler.setStackInSlot(INPUT_SLOT.get(i),_stk);
            }
        }
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
        for (int i = 0; i < this.itemHandler.getSlots();i++){
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
