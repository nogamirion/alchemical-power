package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.block.PanakeiaExtractorBlock;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.screen.PanakeiaExtractorMenu;
import jp.nogami_rion.alchemical_power.util.BlockEntityStateHolder;
import jp.nogami_rion.alchemical_power.util.DynamicEnergyStorage;
import jp.nogami_rion.alchemical_power.util.UpgradeRemovalState;
import jp.nogami_rion.alchemical_power.util.DynamicFluidTank;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PanakeiaExtractorBlockEntity extends BlockEntity implements MenuProvider, BlockEntityStateHolder {
    private static final int SLOT_PANAKEIA = 0;
    private static final int SLOT_WATER_CONTAINER = 1;
    private static final int SLOT_OUTPUT_CONTAINER = 2;
    private static final int SLOT_ENERGY_UPGRADE = 3;
    private static final int SLOT_TANK_UPGRADE = 4;

    private static final int BASE_ENERGY_CAPACITY = 100000;
    private static final int BASE_WATER_CAPACITY = 800000;
    private static final int BASE_OUTPUT_CAPACITY = 800000;
    public static final int PROCESS_TIME = 100;

    public static final Map<Item, Integer> PANAKEIA_OUTPUTS = Map.ofEntries(
            Map.entry(itemlist.T0_PANAKEIA.get(), 10),
            Map.entry(itemlist.T1_PANAKEIA.get(), 27),
            Map.entry(itemlist.T2_PANAKEIA.get(), 71),
            Map.entry(itemlist.T3_PANAKEIA.get(), 190),
            Map.entry(itemlist.T4_PANAKEIA.get(), 506),
            Map.entry(itemlist.T5_PANAKEIA.get(), 1348),
            Map.entry(itemlist.T6_PANAKEIA.get(), 3596),
            Map.entry(itemlist.T7_PANAKEIA.get(), 296582),
            Map.entry(itemlist.INFINITY_PANAKEIA.get(), 12282790),
            // Cubes process nine items' worth in the same PROCESS_TIME.
            Map.entry(itemlist.T1_PANAKEIA_CUBE.get(), 27 * 9),
            Map.entry(itemlist.T2_PANAKEIA_CUBE.get(), 71 * 9),
            Map.entry(itemlist.T3_PANAKEIA_CUBE.get(), 190 * 9),
            Map.entry(itemlist.T4_PANAKEIA_CUBE.get(), 506 * 9),
            Map.entry(itemlist.T5_PANAKEIA_CUBE.get(), 1348 * 9),
            Map.entry(itemlist.T6_PANAKEIA_CUBE.get(), 3596 * 9),
            Map.entry(itemlist.T7_PANAKEIA_CUBE.get(), 296582 * 9),
            // Output per operation: logs consume a full stack, saplings consume one item.
            Map.entry(blocklist.ALCHETREE_LOG.get().asItem(), 10 * 64),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T0.get().asItem(), 10),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T1.get().asItem(), 27),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T2.get().asItem(), 71),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T3.get().asItem(), 190),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T4.get().asItem(), 506),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T5.get().asItem(), 1348),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T6.get().asItem(), 3596),
            Map.entry(blocklist.ALCHETREE_SAPLINGS_T7.get().asItem(), 296582)
    );

    /** Required input count, shared by processing and JEI. */
    public static int inputCount(Item item) {
        return item == blocklist.ALCHETREE_LOG.get().asItem() ? 64 : 1;
    }

    private final ItemStackHandler inventory = new ItemStackHandler(5) {
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot >= SLOT_ENERGY_UPGRADE && !getUpgradeRemovalState(slot).allowed()) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
        @Override
        public int getSlotLimit(int slot) {
            if (slot == SLOT_ENERGY_UPGRADE || slot == SLOT_TANK_UPGRADE) {
                return 1;
            }
            return super.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case SLOT_PANAKEIA -> isPanakeia(stack);
                case SLOT_WATER_CONTAINER, SLOT_OUTPUT_CONTAINER -> FluidUtil.getFluidHandler(stack).isPresent();
                case SLOT_ENERGY_UPGRADE -> isEnergyUpgrade(stack);
                case SLOT_TANK_UPGRADE -> isTankUpgrade(stack);
                default -> false;
            };
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (slot == SLOT_ENERGY_UPGRADE) {
                energy.setEnergy(Math.min(energy.getEnergyStored(), energy.getMaxEnergyStored()));
            } else if (slot == SLOT_TANK_UPGRADE) {
                fluidVisualDirty = true;
                waterTank.clampToCapacity();
                outputTank.clampToCapacity();
            }
        }
    };

    private final LazyOptional<IItemHandler> inventoryCap = LazyOptional.of(() -> inventory);
    private final LazyOptional<IItemHandler> externalInventoryCap = LazyOptional.of(() -> new ExtractorItemHandler());
    private final DynamicEnergyStorage energy = new DynamicEnergyStorage(this::getMaxEnergy, Integer.MAX_VALUE, Integer.MAX_VALUE);
    private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
    private final DynamicFluidTank waterTank = new DynamicFluidTank(this::getMaxWater, stack -> stack.getFluid() == Fluids.WATER) {
        @Override protected void onContentsChanged() {
            setChanged();
            fluidVisualDirty = true;
        }
    };
    private final DynamicFluidTank outputTank = new DynamicFluidTank(this::getMaxOutput, stack -> stack.getFluid() == ModFluids.LIQUID_PANAKEIA.source.get()) {
        @Override protected void onContentsChanged() {
            setChanged();
            fluidVisualDirty = true;
        }
    };
    private final LazyOptional<IFluidHandler> fluidCap = LazyOptional.of(() -> new ExtractorFluidHandler());

    private boolean fluidVisualDirty = true;

    private int progress = 0;
    private int activeOutput = 0;
    private int energySpent = 0;

    public UpgradeRemovalState getUpgradeRemovalState(int slot) {
        int reasons = activeOutput > 0 ? UpgradeRemovalState.PROCESSING : 0;
        if (slot == SLOT_ENERGY_UPGRADE && energy.getEnergyStored() > BASE_ENERGY_CAPACITY)
            reasons |= UpgradeRemovalState.ENERGY;
        if (slot == SLOT_TANK_UPGRADE) {
            if (waterTank.getFluidAmount() > BASE_WATER_CAPACITY) reasons |= UpgradeRemovalState.WATER;
            if (outputTank.getFluidAmount() > BASE_OUTPUT_CAPACITY) reasons |= UpgradeRemovalState.OUTPUT;
        }
        return new UpgradeRemovalState(reasons, BASE_ENERGY_CAPACITY,
                (reasons & UpgradeRemovalState.WATER) != 0 ? BASE_WATER_CAPACITY : BASE_OUTPUT_CAPACITY);
    }

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> PROCESS_TIME;
                case 2 -> energy.getEnergyStored();
                case 3 -> getMaxEnergy();
                case 4 -> waterTank.getFluidAmount();
                case 5 -> getMaxWater();
                case 6 -> outputTank.getFluidAmount();
                case 7 -> getMaxOutput();
                case 8 -> activeOutput;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                progress = value;
            }
        }

        @Override
        public int getCount() {
            return 9;
        }
    };

    public PanakeiaExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PANAKEIA_EXTRACTOR_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PanakeiaExtractorBlockEntity be) {
        if (level.isClientSide) return;

        boolean changed = false;
        changed |= be.tryDrainWaterContainer();
        changed |= be.tryFillOutputContainer();

        if (be.activeOutput <= 0) {
            changed |= be.tryStartProcess();
        }

        if (be.activeOutput > 0 && be.outputTank.getSpace() >= be.activeOutput) {
            int energyPerTick = be.getEnergyPerTick();
            if (energyPerTick <= 0 || be.energy.extractEnergy(energyPerTick, true) >= energyPerTick) {
                be.energy.extractEnergy(energyPerTick, false);
                be.energySpent += energyPerTick;
                be.progress++;
                changed = true;

                if (be.progress >= PROCESS_TIME && be.outputTank.getSpace() >= be.activeOutput) {
                    be.finishProcess();
                    changed = true;
                }
            }
        }

        boolean isLit = be.activeOutput > 0;
        if (state.getValue(PanakeiaExtractorBlock.LIT) != isLit) {
            level.setBlock(pos, state.setValue(PanakeiaExtractorBlock.LIT, isLit), 3);
        }

        if (changed || be.fluidVisualDirty) {
            be.setChanged();
            BlockState currentState = be.getBlockState();
            level.sendBlockUpdated(pos, currentState, currentState, 3);
            be.fluidVisualDirty = false;
        }
    }

    private boolean tryStartProcess() {
        ItemStack panakeia = inventory.getStackInSlot(SLOT_PANAKEIA);
        int output = PANAKEIA_OUTPUTS.getOrDefault(panakeia.getItem(), 0);
        if (output <= 0) return false;
        int count = inputCount(panakeia.getItem());
        if (panakeia.getCount() < count) return false;
        if (output > waterTank.getCapacity() || output > outputTank.getCapacity()) return false;
        if (waterTank.getFluidAmount() < output) return false;
        if (outputTank.getSpace() < output) return false;

        panakeia.shrink(count);
        waterTank.drain(output, IFluidHandler.FluidAction.EXECUTE);
        activeOutput = output;
        energySpent = 0;
        progress = 0;
        return true;
    }

    private void finishProcess() {
        outputTank.fill(new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(), activeOutput), IFluidHandler.FluidAction.EXECUTE);
        progress = 0;
        activeOutput = 0;
        energySpent = 0;
    }

    private int getEnergyPerTick() {
        int remainingEnergy = Math.max(0, activeOutput - energySpent);
        if (remainingEnergy <= 0) return 0;
        int remainingTicks = Math.max(1, PROCESS_TIME - progress);
        return (int) Math.ceil(remainingEnergy / (double) remainingTicks);
    }

    private boolean tryDrainWaterContainer() {
        ItemStack stack = inventory.getStackInSlot(SLOT_WATER_CONTAINER);
        if (stack.isEmpty() || waterTank.getSpace() <= 0) return false;

        return FluidUtil.getFluidHandler(stack).map(handler -> {
            FluidStack drained = handler.drain(new FluidStack(Fluids.WATER, waterTank.getSpace()), IFluidHandler.FluidAction.SIMULATE);
            if (drained.isEmpty()) return false;

            int accepted = waterTank.fill(drained, IFluidHandler.FluidAction.EXECUTE);
            if (accepted <= 0) return false;

            handler.drain(new FluidStack(Fluids.WATER, accepted), IFluidHandler.FluidAction.EXECUTE);
            inventory.setStackInSlot(SLOT_WATER_CONTAINER, handler.getContainer());
            return true;
        }).orElse(false);
    }

    private boolean tryFillOutputContainer() {
        ItemStack stack = inventory.getStackInSlot(SLOT_OUTPUT_CONTAINER);
        if (stack.isEmpty() || outputTank.getFluidAmount() <= 0) return false;

        return FluidUtil.getFluidHandler(stack).map(handler -> {
            FluidStack available = outputTank.drain(outputTank.getFluidAmount(), IFluidHandler.FluidAction.SIMULATE);
            int filled = handler.fill(available, IFluidHandler.FluidAction.SIMULATE);
            if (filled <= 0) return false;

            FluidStack moved = outputTank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
            handler.fill(moved, IFluidHandler.FluidAction.EXECUTE);
            inventory.setStackInSlot(SLOT_OUTPUT_CONTAINER, handler.getContainer());
            return true;
        }).orElse(false);
    }

    private int getUpgradeTier(int slot, UpgradeType type) {
        ItemStack stack = inventory.getStackInSlot(slot);
        if (!(stack.getItem() instanceof UpgradeItem upgrade)) return 0;
        if (upgrade.getType() != type) return 0;
        return upgrade.getTier();
    }

    private int getCapacityMultiplier(UpgradeType type) {
        int tier = type == UpgradeType.TANK
                ? getUpgradeTier(SLOT_TANK_UPGRADE, UpgradeType.TANK)
                : getUpgradeTier(SLOT_ENERGY_UPGRADE, UpgradeType.ENERGY);
        return 1 + 3 * tier;
    }

    private int getMaxEnergy() {
        long capacity = (long) BASE_ENERGY_CAPACITY * getCapacityMultiplier(UpgradeType.ENERGY);
        return (int) Math.min(capacity, Integer.MAX_VALUE);
    }

    private int getMaxWater() {
        long capacity = (long) BASE_WATER_CAPACITY * getCapacityMultiplier(UpgradeType.TANK);
        return (int) Math.min(capacity, Integer.MAX_VALUE);
    }

    private int getMaxOutput() {
        long capacity = (long) BASE_OUTPUT_CAPACITY * getCapacityMultiplier(UpgradeType.TANK);
        return (int) Math.min(capacity, Integer.MAX_VALUE);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("Energy", energy.serializeNBT());
        tag.put("WaterTank", waterTank.writeToNBT(new CompoundTag()));
        tag.put("OutputTank", outputTank.writeToNBT(new CompoundTag()));
        tag.putInt("Progress", progress);
        tag.putInt("ActiveOutput", activeOutput);
        tag.putInt("EnergySpent", energySpent);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        energy.deserializeNBT(tag.get("Energy"));
        waterTank.readFromNBT(tag.getCompound("WaterTank"));
        outputTank.readFromNBT(tag.getCompound("OutputTank"));
        progress = tag.getInt("Progress");
        activeOutput = tag.getInt("ActiveOutput");
        energySpent = tag.getInt("EnergySpent");
    }

    @Override
    public void saveToItemTag(CompoundTag tag) {
        tag.put("Energy", energy.serializeNBT());
        tag.putInt("MaxEnergy", getMaxEnergy());
        tag.put("WaterTank", waterTank.writeToNBT(new CompoundTag()));
        tag.putInt("WaterCapacity", getMaxWater());
        tag.put("OutputTank", outputTank.writeToNBT(new CompoundTag()));
        tag.putInt("OutputCapacity", getMaxOutput());

        CompoundTag upgrades = new CompoundTag();
        ItemStack energyUpgrade = inventory.getStackInSlot(SLOT_ENERGY_UPGRADE);
        ItemStack tankUpgrade = inventory.getStackInSlot(SLOT_TANK_UPGRADE);
        if (!energyUpgrade.isEmpty()) {
            upgrades.put("EnergyUpgrade", energyUpgrade.save(new CompoundTag()));
        }
        if (!tankUpgrade.isEmpty()) {
            upgrades.put("TankUpgrade", tankUpgrade.save(new CompoundTag()));
        }
        if (!upgrades.isEmpty()) {
            tag.put("Upgrades", upgrades);
        }
    }

    @Override
    public void loadFromItemTag(CompoundTag tag) {
        if (tag.contains("Upgrades")) {
            CompoundTag upgrades = tag.getCompound("Upgrades");
            inventory.setStackInSlot(SLOT_ENERGY_UPGRADE, ItemStack.of(upgrades.getCompound("EnergyUpgrade")));
            inventory.setStackInSlot(SLOT_TANK_UPGRADE, ItemStack.of(upgrades.getCompound("TankUpgrade")));
        }
        if (tag.contains("Energy")) {
            energy.deserializeNBT(tag.get("Energy"));
            energy.setEnergy(Math.min(energy.getEnergyStored(), energy.getMaxEnergyStored()));
        }
        if (tag.contains("WaterTank")) {
            waterTank.readFromNBT(tag.getCompound("WaterTank"));
        }
        if (tag.contains("OutputTank")) {
            outputTank.readFromNBT(tag.getCompound("OutputTank"));
        }
        setChanged();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return energyCap.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER) return side == null ? inventoryCap.cast() : externalInventoryCap.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER) return fluidCap.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
        inventoryCap.invalidate();
        externalInventoryCap.invalidate();
        fluidCap.invalidate();
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new PanakeiaExtractorMenu(id, playerInventory, this, data);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.alchemical_power.panakeia_extractor");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public int getWaterAmount() { return waterTank.getFluidAmount(); }
    public int getWaterCapacity() { return waterTank.getCapacity(); }
    public int getOutputAmount() { return outputTank.getFluidAmount(); }
    public int getOutputCapacity() { return outputTank.getCapacity(); }

    public IItemHandler getItemHandler() {
        return inventory;
    }

    public boolean shouldDropInventorySlot(int slot) {
        return slot != SLOT_ENERGY_UPGRADE && slot != SLOT_TANK_UPGRADE;
    }

    public static boolean isPanakeia(ItemStack stack) {
        return PANAKEIA_OUTPUTS.containsKey(stack.getItem());
    }

    public static boolean isEnergyUpgrade(ItemStack stack) {
        return stack.getItem() instanceof UpgradeItem upgrade && upgrade.getType() == UpgradeType.ENERGY;
    }

    public static boolean isTankUpgrade(ItemStack stack) {
        return stack.getItem() instanceof UpgradeItem upgrade && upgrade.getType() == UpgradeType.TANK;
    }

    private static boolean hasDrainableWater(ItemStack stack) {
        return FluidUtil.getFluidHandler(stack)
                .map(handler -> !handler.drain(new FluidStack(Fluids.WATER, Integer.MAX_VALUE), IFluidHandler.FluidAction.SIMULATE).isEmpty())
                .orElse(false);
    }

    private static boolean hasLiquidPanakeia(ItemStack stack) {
        return FluidUtil.getFluidHandler(stack)
                .map(handler -> !handler.drain(new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(), Integer.MAX_VALUE), IFluidHandler.FluidAction.SIMULATE).isEmpty())
                .orElse(false);
    }

    private class ExtractorItemHandler implements IItemHandler {
        @Override
        public int getSlots() {
            return inventory.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return inventory.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot == SLOT_PANAKEIA && isPanakeia(stack)) {
                return inventory.insertItem(slot, stack, simulate);
            }
            if (slot == SLOT_WATER_CONTAINER && hasDrainableWater(stack)) {
                return inventory.insertItem(slot, stack, simulate);
            }
            return stack;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (slot == SLOT_WATER_CONTAINER && !stack.isEmpty() && !hasDrainableWater(stack)) {
                return inventory.extractItem(slot, amount, simulate);
            }
            if (slot == SLOT_OUTPUT_CONTAINER && hasLiquidPanakeia(stack)) {
                return inventory.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return inventory.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return (slot == SLOT_PANAKEIA && isPanakeia(stack))
                    || (slot == SLOT_WATER_CONTAINER && hasDrainableWater(stack));
        }
    }

    private class ExtractorFluidHandler implements IFluidHandler {
        @Override
        public int getTanks() {
            return 2;
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int tank) {
            return switch (tank) {
                case 0 -> waterTank.getFluid();
                case 1 -> outputTank.getFluid();
                default -> FluidStack.EMPTY;
            };
        }

        @Override
        public int getTankCapacity(int tank) {
            return switch (tank) {
                case 0 -> waterTank.getCapacity();
                case 1 -> outputTank.getCapacity();
                default -> 0;
            };
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return tank == 0 && stack.getFluid() == Fluids.WATER;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.getFluid() != Fluids.WATER) return 0;
            return waterTank.fill(resource, action);
        }

        @Override
        public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.getFluid() != ModFluids.LIQUID_PANAKEIA.source.get()) return FluidStack.EMPTY;
            return outputTank.drain(resource, action);
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
            return outputTank.drain(maxDrain, action);
        }
    }
}
