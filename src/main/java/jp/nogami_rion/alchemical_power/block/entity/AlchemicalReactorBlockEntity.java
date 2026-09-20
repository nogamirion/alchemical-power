package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.block.AlchemicalReactorBlock;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalReactorRecipe;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.screen.AlchemicalReactorMenu;
import jp.nogami_rion.alchemical_power.util.BlockEntityStateHolder;
import jp.nogami_rion.alchemical_power.util.DynamicFluidTank;
import jp.nogami_rion.alchemical_power.util.ReactorProgressCost;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.Comparator;

public class AlchemicalReactorBlockEntity extends BlockEntity implements MenuProvider, BlockEntityStateHolder {
    public static final int INPUTS = 12, SAMPLE = 12, OUTPUT = 13, UPGRADES = 14, SLOTS = 19;
    public static final int BASE_TANK_CAPACITY = 2_000_000;
    // Provisional balance: keep independent of the recipe's total FE cost.
    public static final int BASE_ENERGY_CAPACITY = 100_000;
    public static final UpgradeType[] UPGRADE_TYPES = {
            UpgradeType.TOOL, UpgradeType.SPEED, UpgradeType.EFFICIENCY, UpgradeType.ENERGY, UpgradeType.TANK
    };
    private static final String[] UPGRADE_NAMES = {"ToolUpgrade", "SpeedUpgrade", "EfficiencyUpgrade", "EnergyUpgrade", "TankUpgrade"};
    public static final int IDLE = 0, RUNNING = 1, NO_ENERGY = 2, OUTPUT_FULL = 3, NO_FLUID = 4, LOW_TIER = 5;
    public static final int DATA_VALUES = 16;
    private boolean fluidVisualDirty = true;
    private int progress, duration, status;
    private long totalFluid, fluidSpent;
    private long totalEnergy, energySpent;
    private FluidStack pendingFluid = FluidStack.EMPTY;
    private ItemStack pendingResult = ItemStack.EMPTY;
    private final ItemStack[] animationInputs = new ItemStack[INPUTS];

    private final jp.nogami_rion.alchemical_power.util.ReactorItemHandler inventory = new jp.nogami_rion.alchemical_power.util.ReactorItemHandler(SLOTS) {

        @Override public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == OUTPUT) return false;
            if (slot == SAMPLE) return !isProcessing();
            if (slot >= UPGRADES) return !isProcessing() && isUpgradeFor(slot, stack);
            return true;
        }
        @Override public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!canRemove(slot)) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
        @Override protected void onContentsChanged(int slot) {
            setChanged();
            if (slot == UPGRADES + 4) fluidVisualDirty = true;
        }
    };
    private final ReactorEnergy energy = new ReactorEnergy();
    private final DynamicFluidTank tank = new DynamicFluidTank(this::getTankCapacity,
            fluid -> fluid.getFluid() == ModFluids.LIQUID_PANAKEIA.source.get()) {
        @Override protected void onContentsChanged() {
            setChanged();
            fluidVisualDirty = true;
        }
    };
    // Automation sees twelve inputs and the output, never the sample or upgrades.
    private final IItemHandler externalItems = new IItemHandler() {
        @Override public int getSlots() { return INPUTS + 1; }
        private int map(int slot) {
            if (slot < 0 || slot > INPUTS) throw new IndexOutOfBoundsException(slot);
            return slot == INPUTS ? OUTPUT : slot;
        }
        @Override public ItemStack getStackInSlot(int slot) { return inventory.getStackInSlot(map(slot)); }
        @Override public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            int mapped = map(slot);
            return mapped == OUTPUT ? stack : inventory.insertItem(mapped, stack, simulate);
        }
        @Override public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return map(slot) == OUTPUT ? inventory.extractItem(OUTPUT, amount, simulate) : ItemStack.EMPTY;
        }
        @Override public int getSlotLimit(int slot) { return inventory.getSlotLimit(map(slot)); }
        @Override public boolean isItemValid(int slot, ItemStack stack) { return map(slot) != OUTPUT; }
    };
    private final IFluidHandler externalFluid = new IFluidHandler() {
        @Override public int getTanks() { return 1; }
        @Override public FluidStack getFluidInTank(int index) { return index == 0 ? tank.getFluid().copy() : FluidStack.EMPTY; }
        @Override public int getTankCapacity(int index) { return index == 0 ? tank.getCapacity() : 0; }
        @Override public boolean isFluidValid(int index, FluidStack stack) { return index == 0 && tank.isFluidValid(stack); }
        @Override public int fill(FluidStack resource, FluidAction action) { return tank.fill(resource, action); }
        @Override public FluidStack drain(FluidStack resource, FluidAction action) { return FluidStack.EMPTY; }
        @Override public FluidStack drain(int maxDrain, FluidAction action) { return FluidStack.EMPTY; }
    };
    private LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> externalItems);
    private LazyOptional<IFluidHandler> fluidCap = LazyOptional.of(() -> externalFluid);
    private LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);

    public AlchemicalReactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMICAL_REACTOR_BE.get(), pos, state);
        Arrays.fill(animationInputs, ItemStack.EMPTY);
    }
    public ItemStackHandler getItemHandler() { return inventory; }
    public ItemStack getAnimationInput(int slot) { return animationInputs[slot]; }
    public boolean isProcessing() { return !pendingResult.isEmpty(); }
    public static boolean isUpgradeFor(int slot, ItemStack stack) {
        return slot >= UPGRADES && slot < SLOTS && stack.getItem() instanceof UpgradeItem upgrade
                && upgrade.getType() == UPGRADE_TYPES[slot - UPGRADES];
    }
    public int getTier(UpgradeType type) {
        for (int i = 0; i < UPGRADE_TYPES.length; i++) if (UPGRADE_TYPES[i] == type) {
            ItemStack stack = inventory.getStackInSlot(UPGRADES + i);
            return stack.getItem() instanceof UpgradeItem upgrade && upgrade.getType() == type ? upgrade.getTier() : 0;
        }
        return 0;
    }
    public int getFluidAmount() { return tank.getFluidAmount(); }

    public int getTankCapacity() {
        int tier = getTier(UpgradeType.TANK);
        return BASE_TANK_CAPACITY * (tier == 5 ? 32 : 1 + 3 * tier);
    }
    public int getEnergyCapacity() { return (int) (BASE_ENERGY_CAPACITY * (1.0 + 0.8 * getTier(UpgradeType.ENERGY))); }
    public boolean canRemove(int slot) {
        if (isProcessing() && (slot == SAMPLE || slot >= UPGRADES)) return false;
        // Never discard stored resources when an upgrade is taken out.
        if (slot == UPGRADES + 3 && energy.getEnergyStored() > BASE_ENERGY_CAPACITY) return false;
        return slot != UPGRADES + 4 || tank.getFluidAmount() <= BASE_TANK_CAPACITY;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AlchemicalReactorBlockEntity be) {
        if (level.isClientSide) return;
        if (!be.isProcessing()) be.startRecipe();
        if (be.isProcessing()) {
            if (!be.canOutput(be.pendingResult)) be.status = OUTPUT_FULL;
            else {
                long due = ReactorProgressCost.cumulative(be.totalEnergy, be.progress + 1, be.duration) - be.energySpent;
                long liquidDue = ReactorProgressCost.cumulative(be.totalFluid, be.progress + 1, be.duration) - be.fluidSpent;
                if (liquidDue > 0 && (!be.tank.getFluid().isFluidEqual(be.pendingFluid)
                        || be.tank.getFluidAmount() < liquidDue)) be.status = NO_FLUID;
                else if (be.energy.getEnergyStored() < due) be.status = NO_ENERGY;
                else {
                    // Check both resources before consuming either or advancing progress.
                    be.energy.consume((int) due);
                    if (liquidDue > 0) be.tank.drain((int) liquidDue, IFluidHandler.FluidAction.EXECUTE);
                    be.energySpent += due;
                    be.fluidSpent += liquidDue;
                    be.progress++;
                    be.status = RUNNING;
                    be.setChanged();
                    if (be.progress >= be.duration) be.finishRecipe();
                }
            }
        }
        boolean lit = be.status == RUNNING;
        if (state.getValue(AlchemicalReactorBlock.LIT) != lit)
            level.setBlock(pos, state.setValue(AlchemicalReactorBlock.LIT, lit), 3);
        if (be.fluidVisualDirty) be.sync();
    }

    private void startRecipe() {
        status = IDLE;
        if (level == null) return;
        // A stable ID ordering makes identical datapack recipes deterministic.
        var recipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.ALCHEMICAL_REACTOR_TYPE.get()).stream()
                .sorted(Comparator.comparing(recipe -> recipe.getId().toString())).toList();
        for (AlchemicalReactorRecipe recipe : recipes) {
            if (!recipe.matchesSample(inventory.getStackInSlot(SAMPLE))) continue;
            int[] allocation = recipe.allocate(inventory);
            if (allocation == null) continue;
            if (getTier(UpgradeType.TOOL) < recipe.toolTier()) { status = LOW_TIER; continue; }
            FluidStack required = recipe.fluid();
            int operationDuration = Math.max(2, (int) Math.ceil(recipe.time() / (1.0 + 0.36 * Math.pow(getTier(UpgradeType.SPEED), 2)) / 2.0) * 2);
            long firstFluid = Math.max(1, recipe.fluidAmount() / operationDuration);
            if (!required.isEmpty() && (!tank.getFluid().isFluidEqual(required) || tank.getFluidAmount() < firstFluid)) { status = NO_FLUID; continue; }
            ItemStack result = recipe.getResultItem(level.registryAccess());
            if (!canOutput(result)) { status = OUTPUT_FULL; continue; }
            if (energy.getEnergyStored() <= 0) { status = NO_ENERGY; continue; }
            // Round up to tenths of a second (two ticks), including speed upgrades.
            duration = operationDuration;
            totalEnergy = ReactorProgressCost.efficientEnergy(recipe.energy(), getTier(UpgradeType.EFFICIENCY));
            progress = 0;
            energySpent = 0;
            totalFluid = recipe.fluidAmount();
            fluidSpent = 0;
            pendingFluid = required.copy();
            if (!pendingFluid.isEmpty()) pendingFluid.setAmount(1);
            // Reserve a whole operation atomically. Save the pending output and consumed-item visuals,
            // so interruptions, reloads and breaking/replacing cannot consume or produce twice.
            for (int slot = 0; slot < INPUTS; slot++) {
                animationInputs[slot] = inventory.consumeInput(slot, allocation[slot]);
            }
            pendingResult = result;
            sync();
            return;
        }
    }
    private boolean canOutput(ItemStack result) {
        ItemStack output = inventory.getStackInSlot(OUTPUT);
        return output.isEmpty() || (ItemStack.isSameItemSameTags(output, result)
                && output.getCount() + result.getCount() <= Math.min(64, result.getMaxStackSize()));
    }
    private void finishRecipe() {
        ItemStack output = inventory.getStackInSlot(OUTPUT).copy();
        if (output.isEmpty()) output = pendingResult.copy(); else output.grow(pendingResult.getCount());
        inventory.setStackInSlot(OUTPUT, output);
        pendingResult = ItemStack.EMPTY;
        Arrays.fill(animationInputs, ItemStack.EMPTY);
        progress = duration = 0; totalFluid = fluidSpent = 0;
        totalEnergy = energySpent = 0;
        pendingFluid = FluidStack.EMPTY;
        status = IDLE;
        sync();
    }
    private void sync() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            fluidVisualDirty = false;
        }
    }
    public int dataValue(int index) {
        return switch (index) {
            case 0 -> progress; case 1 -> duration;
            case 2 -> energy.getEnergyStored(); case 3 -> getEnergyCapacity();
            case 4 -> tank.getFluidAmount(); case 5 -> getTankCapacity();
            case 6 -> status; case 7 -> getTier(UpgradeType.TOOL);
            case 8 -> (int) totalEnergy; case 9 -> (int) energySpent;
            case 10 -> (int) (totalEnergy >>> 32); case 11 -> (int) (energySpent >>> 32);
            case 12 -> (int) totalFluid; case 13 -> (int) fluidSpent;
            case 14 -> (int) (totalFluid >>> 32); case 15 -> (int) (fluidSpent >>> 32);
            default -> 0;
        };
    }

    @Override protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        writeState(tag);
    }
    private void writeState(CompoundTag tag) {
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("MaxEnergy", getEnergyCapacity());
        // Reuse the existing machine-item tooltip's liquid-panakeia keys.
        tag.put("OutputTank", tank.writeToNBT(new CompoundTag()));
        tag.putInt("OutputCapacity", getTankCapacity());
        tag.putInt("Progress", progress); tag.putInt("Duration", duration);
        tag.putLong("TotalEnergy", totalEnergy); tag.putLong("EnergySpent", energySpent);
        tag.putLong("TotalFluid", totalFluid); tag.putLong("FluidSpent", fluidSpent);
        tag.put("PendingFluid", pendingFluid.writeToNBT(new CompoundTag()));
        tag.put("PendingResult", pendingResult.save(new CompoundTag()));
        for (int i = 0; i < INPUTS; i++) tag.put("Animation" + i, animationInputs[i].save(new CompoundTag()));
        CompoundTag upgrades = new CompoundTag();
        for (int i = 0; i < UPGRADE_TYPES.length; i++)
            upgrades.put(UPGRADE_NAMES[i], inventory.getStackInSlot(UPGRADES + i).save(new CompoundTag()));
        tag.put("Upgrades", upgrades);
    }
    @Override public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        energy.restore(tag.getInt("Energy"));
        tank.readFromNBT(tag.getCompound("OutputTank"));
        fluidVisualDirty = true;
        pendingResult = ItemStack.of(tag.getCompound("PendingResult"));
        duration = Math.max(1, tag.getInt("Duration"));
        progress = Math.max(0, Math.min(duration - 1, tag.getInt("Progress")));
        totalEnergy = Math.max(1, tag.getLong("TotalEnergy"));
        energySpent = Math.max(0, Math.min(totalEnergy, tag.getLong("EnergySpent")));
        // Old saves reserved all liquid up front and have no TotalFluid field.
        // Treat their outstanding fluid cost as zero so it is never charged twice.
        totalFluid = Math.max(0, tag.getLong("TotalFluid"));
        fluidSpent = Math.max(0, Math.min(totalFluid, tag.getLong("FluidSpent")));
        pendingFluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("PendingFluid"));
        for (int i = 0; i < INPUTS; i++) animationInputs[i] = ItemStack.of(tag.getCompound("Animation" + i));
        if (!isProcessing()) {
            progress = duration = 0; totalFluid = fluidSpent = 0;
            totalEnergy = energySpent = 0;
            pendingFluid = FluidStack.EMPTY;
        }
    }
    @Override public void saveToItemTag(CompoundTag tag) { writeState(tag); }
    @Override public void loadFromItemTag(CompoundTag tag) { load(tag); setChanged(); }
    @Override public CompoundTag getUpdateTag() { CompoundTag tag = new CompoundTag(); writeState(tag); return tag; }
    @Override public ClientboundBlockEntityDataPacket getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }
    @Override public Component getDisplayName() { return Component.translatable("block.alchemical_power.alchemical_reactor"); }
    @Override public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) { return new AlchemicalReactorMenu(id, inv, this); }
    @Override public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) return itemCap.cast();
        if (cap == ForgeCapabilities.FLUID_HANDLER) return fluidCap.cast();
        if (cap == ForgeCapabilities.ENERGY) return energyCap.cast();
        return super.getCapability(cap, side);
    }
    @Override public void invalidateCaps() { super.invalidateCaps(); itemCap.invalidate(); fluidCap.invalidate(); energyCap.invalidate(); }
    @Override public void reviveCaps() {
        super.reviveCaps();
        itemCap = LazyOptional.of(() -> externalItems);
        fluidCap = LazyOptional.of(() -> externalFluid);
        energyCap = LazyOptional.of(() -> energy);
    }
    private class ReactorEnergy extends EnergyStorage {
        ReactorEnergy() { super(0); }
        @Override public int getMaxEnergyStored() { return getEnergyCapacity(); }
        @Override public boolean canReceive() { return true; }
        @Override public boolean canExtract() { return false; }
        @Override public int receiveEnergy(int amount, boolean simulate) {
            int accepted = Math.max(0, Math.min(amount, getMaxEnergyStored() - energy));
            if (!simulate && accepted > 0) { energy += accepted; setChanged(); }
            return accepted;
        }
        @Override public int extractEnergy(int amount, boolean simulate) { return 0; }
        void consume(int amount) { energy -= amount; }
        void restore(int amount) { energy = Math.max(0, Math.min(amount, getMaxEnergyStored())); }
    }
}
