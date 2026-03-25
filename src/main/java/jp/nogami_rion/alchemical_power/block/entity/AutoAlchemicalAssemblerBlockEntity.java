package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.container.AlchemicalPowerTablesContainerView;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import jp.nogami_rion.alchemical_power.util.DynamicEnergyStorage;
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
import java.util.HashMap;
import java.util.Map;

public class AutoAlchemicalAssemblerBlockEntity extends BlockEntity {

    private static final int BASE_ENERGY_CAPACITY = 300000;
    private final DynamicEnergyStorage energy = new DynamicEnergyStorage(this::getMaxEnergy,Integer.MAX_VALUE,Integer.MAX_VALUE);
//    private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);
    private AlchemicalPowerTablesRecipe cachedRecipe = null;
    private boolean recipeDirty = true;

    private final ItemStackHandler inventory = new ItemStackHandler(169){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> inventoryCap = LazyOptional.of(() -> inventory);

    private final ItemStackHandler output = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
            recipeDirty = true;
        }
    };
    private final LazyOptional<ItemStackHandler> outputCap = LazyOptional.of(() -> output);

    private final ItemStackHandler upgrades = new ItemStackHandler(4){
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
            setChanged();
        }
    };
    private final LazyOptional<ItemStackHandler> upgradesCap = LazyOptional.of(() -> upgrades);

    private final IItemHandler combinedHandler = new CombinedInvWrapper(inventory,output,upgrades);
    private final LazyOptional<IItemHandler> combinedCap = LazyOptional.of(() -> combinedHandler);
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
    private AlchemicalPowerTablesRecipe lastRecipe = null;

    public AutoAlchemicalAssemblerBlockEntity(BlockPos pos,BlockState state) {
        super(ModBlockEntities.AUTO_ALCHEMICAL_ASSEMBLER.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AutoAlchemicalAssemblerBlockEntity be){
        if(level.isClientSide()) return;

//        System.out.println("Tick start");

        AlchemicalPowerTablesRecipe recipe = be.getCurrentRecipe();
//        System.out.println("Recipe: " + recipe);
//        if(recipe != null) {
//            System.out.println("IngredientCount: " + be.getIngredientCount(recipe));
//        }

        if(recipe == null){
            be.progress = 0;
            be.lastRecipe = null;
            return;
        }

        if(recipe != be.cachedRecipe){
            be.progress = 0;
            be.lastRecipe = recipe;
        }

        int ingredientCount = be.getIngredientCount(recipe);
        if(ingredientCount <= 0){
            be.progress = 0;
            return;
        }

        if(!be.canOutput(recipe)){
            return;
        }

        if(!be.hasRequiredIngredients(recipe)){
            be.progress = 0;
            return;
        }
        int speedTier = be.getSpeedTier();
        int efficiencyTier = be.getEfficiencyTier();

        EnergyStats stats =
                EnergyFormula.calculate(
                        ingredientCount,
                        speedTier,
                        efficiencyTier
                );

        be.maxProgress = stats.finalTime();
        be.currentFEPerTick = stats.fePerTick();

        if(be.energy.getEnergyStored() < stats.fePerTick()) {return;}

        be.energy.extractEnergy(stats.fePerTick(), false);
        int extracted = be.energy.extractEnergy(stats.fePerTick(),false);
        if(extracted > 0){be.setChanged();}

        be.progress++;

        if(be.progress >= stats.finalTime()){
            be.finishCrafting(recipe);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side){
        if (cap == ForgeCapabilities.ENERGY){
            return LazyOptional.of(() -> new IEnergyStorage() {

                @Override
                public int receiveEnergy(int maxReceive, boolean simulate) {
                    return energy.receiveEnergy(maxReceive, simulate);
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
            }).cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return LazyOptional.of(() -> sidedHandler).cast();
        }

        return super.getCapability(cap,side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    //    energyCap.invalidate();
        inventoryCap.invalidate();
        outputCap.invalidate();
        upgradesCap.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putInt("Progress", progress);
        tag.put("Energy", energy.serializeNBT());
        tag.put("Inventory", inventory.serializeNBT());
        tag.put("Output", output.serializeNBT());
        tag.put("Upgrades", upgrades.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        progress = tag.getInt("Progress");
        energy.deserializeNBT(tag.get("Energy"));
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        output.deserializeNBT(tag.getCompound("Output"));
        upgrades.deserializeNBT(tag.getCompound("Upgrades"));
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

    private AlchemicalPowerTablesRecipe findRecipe(){
        if(level == null) return null;

        BlockEntity be = getTemplateBE();
        if(!(be instanceof AbstractAlchemicalTableBlockEntity tableBE)){
            return null;
        }

        // 元のToolを保存
        ItemStack originalTool = tableBE.getTool();
        // 自動機の仮想Toolを生成
        ItemStack virtualTool = getVirtualToolStack();
        // テーブルBEのToolを仮想Toolに置き換え
        tableBE.setTool(virtualTool);

//        System.out.println("Grid size: " + recipeView.getContainerSize());
//        System.out.println("=== Checking grid contents ===");
//        for(int i = 0; i < recipeView.getContainerSize(); i++) {
//            System.out.println("Slot " + i + ": " + recipeView.getItem(i));
//        }
        var recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ALCHEMICAL_POWER_TABLES_TYPE.get(),
                new AlchemicalPowerTablesContainerView(tableBE), level).orElse(null);
//        System.out.println("Recipe found: " + recipe);

        // テーブルBEのToolを元のToolに戻す
        tableBE.setTool(originalTool);

        return recipe;
    }

    private int getIngredientCount(AlchemicalPowerTablesRecipe recipe){
        if (recipe == null) return 0;
        return countIngredients(recipe).values().stream().mapToInt(Integer::intValue).sum();
    }

    private boolean hasRequiredIngredients(AlchemicalPowerTablesRecipe recipe){
        Map<Ingredient,Integer> required = countIngredients(recipe);
        for(var entry: required.entrySet()){
            Ingredient ingredient = entry.getKey();
            int needed = entry.getValue();

            if(countMatching(ingredient) < needed){
                return false;
            }
        }

        return true;
    }

    private void consumeIngredients(AlchemicalPowerTablesRecipe recipe){
        Map<Ingredient,Integer> required = countIngredients(recipe);

        for(var entry: required.entrySet()){
            Ingredient ingredient = entry.getKey();
            int remaining = entry.getValue();

            for(int i = 0; i < inventory.getSlots() && remaining > 0; i++){
                ItemStack stack = inventory.getStackInSlot(i);
                if(!stack.isEmpty() && ingredient.test(stack)){
                    int shrink = Math.min(stack.getCount(), remaining);
                    stack.shrink(shrink);
                    inventory.setStackInSlot(i,stack);

                    remaining -= shrink;
                }
            }

        }
    }

    private Map<Ingredient,Integer> countIngredients(AlchemicalPowerTablesRecipe recipe){
        Map<Ingredient,Integer> required = new HashMap<>();
        Ingredient tool = recipe.getTool();
        for(Ingredient ingredient : recipe.getIngredients()){
            if(ingredient.isEmpty()) continue;
            if(isToolIngredient(ingredient,recipe)) continue;
            required.merge(ingredient,1,Integer::sum);
        }
        return required;
    }

    private int countMatching(Ingredient ingredient){
        int total = 0;
        for(int i = 0; i < inventory.getSlots(); i++){
            ItemStack stack = inventory.getStackInSlot(i);
            if(!stack.isEmpty() && ingredient.test(stack)){
                total += stack.getCount();
            }
        }
        return total;
    }

    private boolean canOutput(AlchemicalPowerTablesRecipe recipe){
        ItemStack result = recipe.getResultItem(level.registryAccess());
        ItemStack current = output.getStackInSlot(0);
        if(current.isEmpty()){
            return true;
        }
        if(!ItemStack.isSameItemSameTags(current,result)){
            return false;
        }
        return current.getCount() + result.getCount() <= current.getMaxStackSize();
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
        };

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

    private AlchemicalPowerTablesRecipe getCurrentRecipe(){
        if(recipeDirty){
            cachedRecipe = findRecipe();
            recipeDirty = false;
        }
        return cachedRecipe;
    }

    public int getMaxEnergy(){
        double multiplier = 1.0 + 0.8 * getEnergyTier();
        return (int)(BASE_ENERGY_CAPACITY * multiplier);
    }

    private boolean isToolIngredient(Ingredient ingredient,AlchemicalPowerTablesRecipe recipe){
        Ingredient tool = recipe.getTool();
        if(tool == null || tool.isEmpty()) return false;
        for(ItemStack stack : ingredient.getItems()){
            if(tool.test(stack)){
                return true;
            }
        }
        return false;
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
