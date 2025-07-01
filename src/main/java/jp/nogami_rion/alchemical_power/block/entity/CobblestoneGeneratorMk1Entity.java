package jp.nogami_rion.alchemical_power.block.entity;


import jp.nogami_rion.alchemical_power.block.CobblestoneGeneratorMk1;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import java.util.Set;

public class CobblestoneGeneratorMk1Entity extends BlockEntity{
    private final ItemStackHandler itemHandler = new LargeStackItemHandler(1, this);
    private Item product;
    private static final int SLOT = 0;
    private long itemCount = 0;
    private final long genStackCount = 1; //1処理ごとの生産数


    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 20;

    public CobblestoneGeneratorMk1Entity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.COBBLESTONE_GENERATOR_MK1_BE.get(), pPos, pBlockState);
        if(pBlockState.getBlock() instanceof CobblestoneGeneratorMk1 block){
        this.product = block.getProduct();}
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> CobblestoneGeneratorMk1Entity.this.progress;
                    case 1 -> CobblestoneGeneratorMk1Entity.this.maxProgress;
                    default -> 0;
                };

            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> CobblestoneGeneratorMk1Entity.this.progress = pValue;
                    case 1 -> CobblestoneGeneratorMk1Entity.this.maxProgress = pValue;
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
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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
//        SimpleContainer inventory = new SimpleContainer((itemHandler.getSlots()));
//        for (int i = 0; i < itemHandler.getSlots(); i++) {
//            inventory.setItem(i, itemHandler.getStackInSlot(i));
//        }
//        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    // NBT保存
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putLong("itemCount", itemCount);
        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemCount = pTag.getLong("itemCount");
        updateStack();
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
                increaseCraftingProgress();
                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
    }

    public void use(Player player){
        ItemStack stack = itemHandler.getStackInSlot(SLOT);
        Component itemName = itemHandler.getStackInSlot(SLOT).getDisplayName();
        int count = stack.getCount();
        if (itemCount > 0) {
            int dropAmount = (int)Math.min(itemCount, 64);
            ItemStack toGive = new ItemStack(product, dropAmount);
            if(!player.isShiftKeyDown()) {
                boolean added = player.addItem(toGive.copy());
                if (!added) {
                    // 入りきらない場合は足元にドロップ
                    net.minecraft.world.Containers.dropItemStack(
                            level, player.getX(), player.getY(), player.getZ(), toGive
                    );
                }
//                stack.shrink(dropAmount);
//                itemHandler.setStackInSlot(SLOT, stack);
                itemCount -= dropAmount;
                updateStack();

                // 翻訳対応のメッセージ
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.translatable(
                                "message.alchemical_power.cobblegen.remaining",itemName,itemCount),
                        true);
            } else {
                player.displayClientMessage(
                        net.minecraft.network.chat.Component.translatable(
                                "message.alchemical_power.cobblegen.remaining",itemName,itemCount),
                        true);
            }
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {

//        itemHandler.setStackInSlot(SLOT, new ItemStack(product,
//                itemHandler.getStackInSlot(SLOT).getCount() + 1));
        if (itemCount + genStackCount > Long.MAX_VALUE) {
            itemCount = Long.MAX_VALUE;
            updateStack();
        }else{
            itemCount += genStackCount;
            updateStack();
        }
    }
    // スタックの見かけ上の個数を更新
    private void updateStack() {
        int displayCount = (int)Math.min(itemCount, Integer.MAX_VALUE);
        itemHandler.setStackInSlot(SLOT, itemCount > 0 ? new ItemStack(product, displayCount) : ItemStack.EMPTY);
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    public static class LargeStackItemHandler extends ItemStackHandler {
        private final CobblestoneGeneratorMk1Entity parent;

        public LargeStackItemHandler(int slots, CobblestoneGeneratorMk1Entity parent) {
            super(slots);
            this.parent = parent;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (parent.itemCount <= 0) return ItemStack.EMPTY;
            int extractAmount = (int)Math.min(amount, Math.min(parent.itemCount, Integer.MAX_VALUE));
            ItemStack extracted = new ItemStack(parent.product, extractAmount);
            if (!simulate) {
                parent.itemCount -= extractAmount;
                parent.updateStack();
            }
            return extracted;
        }

        @Override
        public int getStackLimit(int slot, @NotNull ItemStack stack) {
            return Integer.MAX_VALUE;
        }
    }

    // NBTへ保存
    public void saveToItem(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        tag.put("inventory", itemHandler.serializeNBT());
        stack.addTagElement("BlockEntityTag", tag);
    }

    // NBTから復元
    public void loadFromItem(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("BlockEntityTag")) {
            CompoundTag tag = stack.getTag().getCompound("BlockEntityTag");
            itemHandler.deserializeNBT(tag.getCompound("inventory"));
        }
    }



}
