package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.container.AlchemicalPowerTablesContainerView;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import jp.nogami_rion.alchemical_power.util.BlockEntityStateHolder;
import jp.nogami_rion.alchemical_power.util.DynamicEnergyStorage;
import jp.nogami_rion.alchemical_power.util.UpgradeRemovalState;
import jp.nogami_rion.alchemical_power.util.EnergyFormula;
import jp.nogami_rion.alchemical_power.util.EnergyStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import jp.nogami_rion.alchemical_power.util.AssemblerIngredientPlan;
import jp.nogami_rion.alchemical_power.util.AssemblerRecipeReloads;

public class AutoAlchemicalAssemblerBlockEntity extends BlockEntity implements BlockEntityStateHolder {

    private static final int BASE_ENERGY_CAPACITY = 300000;
    private final DynamicEnergyStorage energy = new DynamicEnergyStorage(this::getMaxEnergy,Integer.MAX_VALUE,Integer.MAX_VALUE);
    private LazyOptional<IEnergyStorage> energyCap = createEnergyCap();
    private AlchemicalPowerTablesRecipe cachedRecipe = null;
    private boolean recipeDirty = true;
    private BlockEntity cachedTemplate;
    private long templateRevision = -1;
    private long reloadRevision = -1;
    private boolean inventoryDirty = true;
    private boolean outputDirty = true;
    private boolean statsDirty = true;
    private List<Ingredient> requiredIngredients = List.of();
    private int[] consumptionPlan;
    private ItemStack recipeResult = ItemStack.EMPTY;
    private boolean outputAvailable;
    private String progressRecipeId = "";

    private final ItemStackHandler inventory = new ItemStackHandler(169){
        @Override
        protected void onContentsChanged(int slot){
            inventoryDirty = true;
            setChanged();
        }
    };

    private final ItemStackHandler output = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
            outputDirty = true;
        }
    };

    private final ItemStackHandler upgrades = new ItemStackHandler(4){
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!getUpgradeRemovalState(slot).allowed()) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
        @Override
        public boolean isItemValid(int slot,ItemStack stack){
            if(!(stack.getItem() instanceof UpgradeItem upgrade)){
                return false;
            }
            return switch (slot){
                case 0 -> upgrade.getType() == UpgradeType.TOOL;
                case 1 -> upgrade.getType() == UpgradeType.SPEED;
                case 2 -> upgrade.getType() == UpgradeType.EFFICIENCY;
                case 3 -> upgrade.getType() == UpgradeType.ENERGY;
                default -> false;
            };
        }

        @Override
        public int getSlotLimit(int slot){
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot){
            super.onContentsChanged(slot);
            clampEnergyToCapacity();
            statsDirty = true;
            if (slot == 0) recipeDirty = true;
            setChanged();
        }
    };

    private final IItemHandler combinedHandler = new CombinedInvWrapper(inventory,output,upgrades);
    private final IItemHandler sidedHandler = new IItemHandler() {
        @Override
        public int getSlots() {
            return combinedHandler.getSlots();
        }

        @Override
        public @NotNull ItemStack getStackInSlot(int slot) {
            return combinedHandler.getStackInSlot(slot);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if(slot < inventory.getSlots()){
                return combinedHandler.insertItem(slot, stack, simulate);
            }
            return stack; // 入力スロット以外には搬入を禁止
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            int outputSlotStart = inventory.getSlots();
            int outputSlotEnd = inventory.getSlots() + output.getSlots();

            if(slot >= outputSlotStart && slot < outputSlotEnd){
                return combinedHandler.extractItem(slot, amount, simulate);
            }
            return ItemStack.EMPTY; // 出力スロット以外からの搬出を禁止
        }

        @Override
        public int getSlotLimit(int slot) {
            return combinedHandler.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if(slot >= inventory.getSlots()){
                return false; // 出力スロットには挿入禁止
            }
            return combinedHandler.isItemValid(slot, stack);
        }
    };

    private int progress = 0;
    private int maxProgress = 0;
    private int currentFEPerTick = 0;


    public AutoAlchemicalAssemblerBlockEntity(BlockPos pos,BlockState state) {
        super(ModBlockEntities.AUTO_ALCHEMICAL_ASSEMBLER.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AutoAlchemicalAssemblerBlockEntity be) {
        if (level.isClientSide()) return;
        AlchemicalPowerTablesRecipe recipe = be.getCurrentRecipe();
        if (recipe == null || be.requiredIngredients.isEmpty()) {
            be.resetProgress();
            be.maxProgress = 0;
            be.currentFEPerTick = 0;
            return;
        }
        if (be.statsDirty) {
            EnergyStats stats = EnergyFormula.calculate(be.requiredIngredients.size(), be.getSpeedTier(), be.getEfficiencyTier());
            be.maxProgress = stats.finalTime();
            be.currentFEPerTick = stats.fePerTick();
            be.statsDirty = false;
        }
        if (!be.canOutput(recipe)) return;
        if (!be.hasRequiredIngredients(recipe)) {
            be.resetProgress();
            return;
        }
        if (be.energy.getEnergyStored() < be.currentFEPerTick) return;
        be.energy.extractEnergy(be.currentFEPerTick, false);
        be.progress++;
        be.setChanged();
        if (be.progress >= be.maxProgress) be.finishCrafting(recipe);
    }

    private void resetProgress() {
        if (progress != 0) {
            progress = 0;
            setChanged();
        }
    }
    private LazyOptional<IItemHandler> sidedCap = LazyOptional.of(() -> sidedHandler);

    private LazyOptional<IEnergyStorage> createEnergyCap() {
        return LazyOptional.of(() -> new IEnergyStorage() {

                @Override
                public int receiveEnergy(int maxReceive, boolean simulate) {
                    int received = energy.receiveEnergy(maxReceive, simulate);
                    if (!simulate && received > 0) setChanged();
                    return received;
                }

                @Override
                public int extractEnergy(int maxExtract, boolean simulate) {
                    return 0; // 外部抽出禁止
                }

                @Override
                public int getEnergyStored() {
                    return energy.getEnergyStored();
                }

                @Override
                public int getMaxEnergyStored() {
                    return energy.getMaxEnergyStored();
                }

                @Override
                public boolean canExtract() {
                    return false;
                }

                @Override
                public boolean canReceive() {
                    return true;
                }
            });
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return energyCap.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER) return sidedCap.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
        sidedCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        energyCap = createEnergyCap();
        sidedCap = LazyOptional.of(() -> sidedHandler);
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Progress", progress);
        tag.putString("ProgressRecipe", progressRecipeId);
        tag.put("Energy", energy.serializeNBT());
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("Output", output.serializeNBT());
        tag.put("Upgrades", upgrades.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        progress = tag.getInt("Progress");
        progressRecipeId = tag.getString("ProgressRecipe");
        observedReload = AssemblerRecipeReloads.revision();
        recipeDirty = inventoryDirty = outputDirty = statsDirty = true;
        energy.deserializeNBT(tag.get("Energy"));
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        output.deserializeNBT(tag.getCompound("Output"));
        upgrades.deserializeNBT(tag.getCompound("Upgrades"));
    }

    @Override
    public void saveToItemTag(CompoundTag tag) {
        tag.put("Energy", energy.serializeNBT());
        tag.putInt("MaxEnergy", getMaxEnergy());

        CompoundTag upgradeTag = new CompoundTag();
        saveUpgrade(upgradeTag, "ToolUpgrade", 0);
        saveUpgrade(upgradeTag, "SpeedUpgrade", 1);
        saveUpgrade(upgradeTag, "EfficiencyUpgrade", 2);
        saveUpgrade(upgradeTag, "EnergyUpgrade", 3);
        if (!upgradeTag.isEmpty()) {
            tag.put("Upgrades", upgradeTag);
        }
    }

    @Override
    public void loadFromItemTag(CompoundTag tag) {
        if (tag.contains("Upgrades")) {
            CompoundTag upgradeTag = tag.getCompound("Upgrades");
            loadUpgrade(upgradeTag, "ToolUpgrade", 0);
            loadUpgrade(upgradeTag, "SpeedUpgrade", 1);
            loadUpgrade(upgradeTag, "EfficiencyUpgrade", 2);
            loadUpgrade(upgradeTag, "EnergyUpgrade", 3);
        }
        if (tag.contains("Energy")) {
            energy.deserializeNBT(tag.get("Energy"));
            energy.setEnergy(Math.min(energy.getEnergyStored(), energy.getMaxEnergyStored()));
        }
        setChanged();
    }

    private void saveUpgrade(CompoundTag tag, String tagName, int slot) {
        ItemStack stack = upgrades.getStackInSlot(slot);
        if (!stack.isEmpty()) {
            tag.put(tagName, stack.save(new CompoundTag()));
        }
    }

    private void loadUpgrade(CompoundTag tag, String tagName, int slot) {
        upgrades.setStackInSlot(slot, ItemStack.of(tag.getCompound(tagName)));
    }

    private boolean hasAnyItem(){
        for(int i = 0; i < inventory.getSlots(); i++){
            if(!inventory.getStackInSlot(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    private void finishCrafting(AlchemicalPowerTablesRecipe recipe){

        if(!hasRequiredIngredients(recipe)) {
            progress = 0;
            return;
        }

        if(!canOutput(recipe)) {
            progress = 0;
            return;
        }

        consumeIngredients(recipe);

        ItemStack result = recipe.getResultItem(level.registryAccess()).copy();
        ItemStack current = output.getStackInSlot(0);
        if(current.isEmpty()){
            output.setStackInSlot(0, result);
        } else if(ItemStack.isSameItemSameTags(current,result)){
            current.grow(result.getCount());
            output.setStackInSlot(0,current);
        }
        progress = 0;
        setChanged();
    }

    private int countTotalItems(){
        int total = 0;
        for(int i = 0; i < inventory.getSlots(); i++){
            total += inventory.getStackInSlot(i).getCount();
        }
        return total;
    }

    private BlockEntity getTemplateBE(){
        if(level == null) return null;
        BlockPos below = worldPosition.below();
        return level.getBlockEntity(below);
    }

    private AlchemicalPowerTablesRecipe findRecipe() {
        if (level == null || !(cachedTemplate instanceof AbstractAlchemicalTableBlockEntity table)) return null;
        return level.getRecipeManager().getRecipeFor(ModRecipes.ALCHEMICAL_POWER_TABLES_TYPE.get(),
                new AlchemicalPowerTablesContainerView(table, getVirtualToolStack()), level).orElse(null);
    }

    private boolean hasRequiredIngredients(AlchemicalPowerTablesRecipe recipe) {
        if (inventoryDirty) {
            consumptionPlan = AssemblerIngredientPlan.create(requiredIngredients, inventory);
            inventoryDirty = false;
        }
        return consumptionPlan != null;
    }

    private void consumeIngredients(AlchemicalPowerTablesRecipe recipe) {
        // The same plan that proved sufficiency is applied on the server thread.
        int[] plan = consumptionPlan;
        for (int i = 0; i < plan.length; i++) {
            if (plan[i] > 0) inventory.extractItem(i, plan[i], false);
        }
    }

    private boolean canOutput(AlchemicalPowerTablesRecipe recipe) {
        if (outputDirty) {
            ItemStack current = output.getStackInSlot(0);
            outputAvailable = !recipeResult.isEmpty()
                    && recipeResult.getCount() <= recipeResult.getMaxStackSize()
                    && (current.isEmpty() || (ItemStack.isSameItemSameTags(current, recipeResult)
                    && current.getCount() + recipeResult.getCount() <= current.getMaxStackSize()));
            outputDirty = false;
        }
        return outputAvailable;
    }
    public IItemHandler getInventoryHandler(){
        return inventory;
    }

    public IItemHandler getOutputHandler(){
        return output;
    }

    public ItemStackHandler getUpgradeHandler(){
        return upgrades;
    }

    public UpgradeRemovalState getUpgradeRemovalState(int slot) {
        int reasons = progress > 0 ? UpgradeRemovalState.PROCESSING : 0;
        if (slot == 3 && energy.getEnergyStored() > BASE_ENERGY_CAPACITY) reasons |= UpgradeRemovalState.ENERGY;
        return new UpgradeRemovalState(reasons, BASE_ENERGY_CAPACITY, 0);
    }

    public boolean shouldDropUpgradeSlot(int slot) {
        return false;
    }
    private boolean isValidUpgrade(int slot,ItemStack stack){
        if(!(stack.getItem() instanceof UpgradeItem upgrade)){
            return false;
        }

        return switch (slot){
            case 0 -> upgrade.getType() == UpgradeType.TOOL;
            case 1 -> upgrade.getType() == UpgradeType.SPEED;
            case 2 -> upgrade.getType() == UpgradeType.EFFICIENCY;
            case 3 -> upgrade.getType() == UpgradeType.ENERGY;
            default -> false;
        };
    }

    private int getUpgradeTier(UpgradeType type){
        for(int i = 0; i < upgrades.getSlots(); i++){
            ItemStack stack = upgrades.getStackInSlot(i);

            if(stack.isEmpty()) continue;

            if(stack.getItem() instanceof UpgradeItem upgrade){
                if(upgrade.getType() == type){
                    return upgrade.getTier();
                }
            }

        }
        return 0;
    }

    public int getSpeedTier(){
        return getUpgradeTier(UpgradeType.SPEED);
    }
    public int getEfficiencyTier(){
        return getUpgradeTier(UpgradeType.EFFICIENCY);
    }
    public int getEnergyTier(){
        return getUpgradeTier(UpgradeType.ENERGY);
    }
    public int getToolTier(){
        return getUpgradeTier(UpgradeType.TOOL);
    }

    public boolean insertUpgrade(ItemStack stack){
        if(!(stack.getItem() instanceof UpgradeItem upgrade)){
            return false;
        }
        UpgradeType type = upgrade.getType();

        int tagetSlot = switch (type){
            case TOOL -> 0;
            case SPEED -> 1;
            case EFFICIENCY -> 2;
            case ENERGY -> 3;
            case TANK -> -1;
        };

        if(tagetSlot < 0){
            return false;
        }

        ItemStack existing = upgrades.getStackInSlot(tagetSlot);
        if(!existing.isEmpty()){
            return false;
        }

        ItemStack single = stack.copy();
        single.setCount(1);

        upgrades.setStackInSlot(tagetSlot,single);
        setChanged();
        recipeDirty = true;

        return true;

    }

    public ItemStack getRecognizedRecipeResult() {
        if (level == null || level.isClientSide) return ItemStack.EMPTY;
        getCurrentRecipe();
        return recipeResult.copy();
    }

    private AlchemicalPowerTablesRecipe getCurrentRecipe() {
        BlockEntity template = getTemplateBE();
        long revision = template instanceof AbstractAlchemicalTableBlockEntity table ? table.getGrid().getRevision() : -1;
        long reload = AssemblerRecipeReloads.revision();
        if (recipeDirty || template != cachedTemplate || revision != templateRevision || reload != reloadRevision) {
            cachedTemplate = template;
            templateRevision = revision;
            reloadRevision = reload;
            cachedRecipe = findRecipe();
            recipeDirty = false;
            String id = cachedRecipe == null ? "" : cachedRecipe.getId().toString();
            if (!id.equals(progressRecipeId) || (observedReload != -1 && reload != observedReload)) resetProgress();
            observedReload = reload;
            progressRecipeId = id;
            requiredIngredients = new ArrayList<>();
            if (cachedRecipe != null) {
                var ingredients = cachedRecipe.getIngredients();
                // Only the final, dedicated tool slot is virtual. Kits in the pattern are materials.
                for (int i = 0; i < ingredients.size() - 1; i++) {
                    if (!ingredients.get(i).isEmpty()) requiredIngredients.add(ingredients.get(i));
                }
                recipeResult = cachedRecipe.getResultItem(level.registryAccess());
            } else recipeResult = ItemStack.EMPTY;
            inventoryDirty = outputDirty = statsDirty = true;
        }
        return cachedRecipe;
    }

    private long observedReload = -1;
    public int getMaxEnergy(){
        double multiplier = 1.0 + 0.8 * getEnergyTier();
        return (int)(BASE_ENERGY_CAPACITY * multiplier);
    }

    public ItemStack getVirtualToolStack(){
        int tier = getToolTier();
        return switch (tier){
            case 0 -> new ItemStack (itemlist.ALCHEMY_BEGINNERS_KIT.get());
            case 1 -> new ItemStack (itemlist.ALCHEMY_INTERMEDIATE_KIT.get());
            case 2 -> new ItemStack (itemlist.ALCHEMY_EXPERTS_KIT.get());
            case 3 -> new ItemStack (itemlist.ULTIMATE_ALCHEMY_KIT.get());
            case 4 -> new ItemStack (itemlist.PHILOSOPHERS_STONE.get());
            default -> ItemStack.EMPTY;
        };
    }

    private void clampEnergyToCapacity(){
        int max = energy.getMaxEnergyStored();
        if(energy.getEnergyStored() > max){
            energy.setEnergy(max);
        }
    }

    public int getProgress(){
        return progress;
    }

    public int getMaxProgress(){
        return maxProgress;
    }

    public int getCurrentFEPerTick(){
        return currentFEPerTick;
    }

    public int getEnergyStored(){
        return energy.getEnergyStored();
    }

    public int getMaxEnergyStored(){
        return energy.getMaxEnergyStored();
    }

}
