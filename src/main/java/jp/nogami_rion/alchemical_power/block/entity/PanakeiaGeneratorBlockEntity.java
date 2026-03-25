package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.block.PanakeiaGeneratorBlock;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import jp.nogami_rion.alchemical_power.screen.PanakeiaGeneratorMenu;
import jp.nogami_rion.alchemical_power.util.DynamicEnergyStorage;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Dynamic;

import java.util.Map;

public class PanakeiaGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    // インベントリ 0:燃料　1：触媒　2～5；アプグレスロット
    private final ItemStackHandler inventory = new ItemStackHandler(6){
        @Override
        public int getSlotLimit(int slot){
            if(slot >= 2 && slot <= 5){
                return 1;
            }
            return super.getSlotLimit(slot);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot == 1){
                recalcCatalyst();
            }
            if(slot >= 2){
                clampEnergyToCapacity();
            }
            if(slot >= 2 && slot <= 5){
                clampEnergy();
            }
        }
    };
    private final LazyOptional<IItemHandler> inventoryCap = LazyOptional.of(()-> inventory);
    // 燃焼関連
    private int burnTime = 0;
    private static final int MAX_BURN_TIME = 100;
    private ItemStack activeFuel = ItemStack.EMPTY;
    private int activeFuelTier = -1;
    private int cachedOutput = 0;
    private double catalystMultiplier = 1.0;
    //エネルギー管理
    private static final int BASE_CAPACITY = 100000;
    private final DynamicEnergyStorage energy;
    private final LazyOptional<IEnergyStorage> energyCap;
    private int pushIndex = 0;
    private int lastOutput;
    //RS制御
    private int redstoneMode = 0;
    //エネルギー送信管理
    private final BlockEntity[] neighbors = new BlockEntity[6];
    //ContainerData
    private final ContainerData data = new ContainerData(){
        @Override
        public int get(int index){
            return switch(index){
                case 0 -> burnTime;
                case 1 -> energy.getEnergyStored();
                case 2 -> getMaxEnergy();
                case 3 -> Item.getId(activeFuel.getItem());
                case 4 -> lastOutput;
                default -> 0;
            };
        }
        @Override
        public void set(int index,int value){
            switch(index){
                case 0 -> burnTime = value;
            }
        }
        @Override
        public int getCount() {return 5;}
    };

    public PanakeiaGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PANAKEIA_GENERATOR_BE.get(),pos,state);
        this.energy = new DynamicEnergyStorage(this::getMaxEnergy,Integer.MAX_VALUE,Integer.MAX_VALUE);
        this.energyCap = LazyOptional.of(() -> energy);
    }

    public IItemHandler getItemHandler() {
        return inventory;
    }

    //　燃料リスト
    public static final Map<Item,Integer> PANAKEIA_TIERS = Map.of(
            itemlist.T0_PANAKEIA.get(),0,
            itemlist.T1_PANAKEIA.get(),1,
            itemlist.T2_PANAKEIA.get(),2,
            itemlist.T3_PANAKEIA.get(),3,
            itemlist.T4_PANAKEIA.get(),4,
            itemlist.T5_PANAKEIA.get(),5,
            itemlist.T6_PANAKEIA.get(),6,
            itemlist.T7_PANAKEIA.get(),7,
            itemlist.INFINITY_PANAKEIA.get(),8
    );

    //　触媒リスト
    public static final Map<Item,Double> CATALYST_BASE = Map.of(
            Items.COPPER_BLOCK,1.1,
            Items.IRON_BLOCK,1.15,
            Items.REDSTONE_BLOCK,1.3,
            Items.GOLD_BLOCK,1.4,
            Items.QUARTZ_BLOCK,1.5,
            Items.DIAMOND_BLOCK,1.75,
            Items.EMERALD_BLOCK,2.0,
            Items.NETHERITE_BLOCK,2.5,
            itemlist.UNITE_ALLOY_BLOCK.get(),10.0,
            itemlist.SINGULARITY.get(),128.0
    );

    private void recalcCatalyst() {
        ItemStack stack = inventory.getStackInSlot(1);

        if(stack.isEmpty()){
            catalystMultiplier = 1.0;
            return;
        }
        double base = CATALYST_BASE.getOrDefault(stack.getItem(),1.0);
        int count = stack.getCount();

        catalystMultiplier = base * (1 + (3 * Math.sqrt(count)) / 8.0);

    }

    private int getOutputForTier(int tier){
        return switch (tier){
            case 0 -> 10;
            case 1 -> 20;
            case 2 -> 40;
            case 3 -> 100;
            case 4 -> 200;
            case 5 -> 400;
            case 6 -> 800;
            case 7 -> 64000;
            case 8 -> 3200000;
            default ->0;
        };
    }

    private int getUpgradeTier(ItemStack stack){
        if(!(stack.getItem() instanceof UpgradeItem upgrade))
            return 0;
        if(upgrade.getType() != UpgradeType.ENERGY)
            return 0;

        return upgrade.getTier();
    }

    private int getTotalUpgradeTier(){
        int total = 0;
        for(int i = 2; i <=5; i++){
            ItemStack stack = inventory.getStackInSlot(i);
            total += getUpgradeTier(stack);
        }
        return total;
    }

    private int getMaxEnergy(){
        long capacity = BASE_CAPACITY;
        for (int i = 2; i <= 5; i++){
            ItemStack stack = inventory.getStackInSlot(i);
            int tier = getUpgradeTier(stack);
            capacity *= (1 + 3L * tier);
        }

        return (int)Math.min(capacity,Integer.MAX_VALUE);
    }

    private void tryStartBurn(){
        if(burnTime > 0) return;
        if(!activeFuel.isEmpty())return;
        if(!shouldRun()) return;
        if(energy.getEnergyStored() >= energy.getMaxEnergyStored()) return;

        ItemStack fuel = inventory.getStackInSlot(0);
        if(fuel.isEmpty()) return;

        activeFuelTier = PANAKEIA_TIERS.getOrDefault(fuel.getItem(),-1);
        if(activeFuelTier < 0) return;

        cachedOutput = getOutputForTier(activeFuelTier);

        recalcCatalyst();

        burnTime = MAX_BURN_TIME;
        activeFuel = fuel.copy();
        fuel.shrink(1);

        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);

    }

    private boolean shouldRun(){
        boolean powered = level.hasNeighborSignal(worldPosition);

        return switch (redstoneMode){
            case 0 -> true;
            case 1 -> !powered;
            case 2 -> powered;
            default -> true;
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PanakeiaGeneratorBlockEntity be){
        if(level.isClientSide) return;
        boolean changed = false;

        if(be.burnTime <= 0){
            be.tryStartBurn();
        }

        if(be.burnTime > 0){
            if(!be.shouldRun()) return;
            if(be.energy.getEnergyStored() < be.energy.getMaxEnergyStored()) {
                long rawOutput = (long) (be.cachedOutput * be.catalystMultiplier);
                int output = (int) Math.min(rawOutput, Integer.MAX_VALUE);
                int accepted = be.energy.receiveEnergy(output, false);
                int tier = be.activeFuelTier;
                be.lastOutput = accepted;
                be.burnTime--;
                if(tier >= 7){
                    long time = level.getGameTime();
                    int phase;
                    if(tier == 7) {
                        phase = (int) ((time / 4) % 24);
                    }else {
                        phase = (int) (time % 24);
                    }
                    BlockState phaseState = be.getBlockState();
                    if(phaseState.getValue(PanakeiaGeneratorBlock.RAINBOW) != phase){
                        level.setBlock(be.worldPosition,phaseState.setValue(PanakeiaGeneratorBlock.RAINBOW, phase), 3);
                    }
                }

                if (accepted > 0) {
                    changed = true;
                }
            }
        }

        if(be.burnTime == 0){
            be.activeFuel = ItemStack.EMPTY;
        }

        if(changed && be.energy.getEnergyStored() > 0) {
            be.pushEnergy();
        }

        boolean isLit = be.burnTime > 0;
        if(state.getValue(PanakeiaGeneratorBlock.LIT) != isLit){
            level.setBlock(be.worldPosition,state.setValue(PanakeiaGeneratorBlock.LIT,isLit),3);
        }
        if(changed){
            be.clampEnergy();
            be.setChanged();
            level.sendBlockUpdated(pos,state,state,3);
        }

    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        tag.put("Inventory",inventory.serializeNBT());
        tag.put("Energy",energy.serializeNBT());
        tag.putInt("BurnTime",burnTime);
        tag.putInt("RSMode",redstoneMode);
        if(!activeFuel.isEmpty()) {
            tag.put("ActiveFuel", activeFuel.save(new CompoundTag()));
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        energy.deserializeNBT(tag.get("Energy"));
        burnTime = tag.getInt("BurnTime");
        redstoneMode = tag.getInt("RSMode");
        if(tag.contains("ActiveFuel")) {
            activeFuel = ItemStack.of(tag.getCompound("ActiveFuel"));
        } else {
            activeFuel = ItemStack.EMPTY;
        }
    }

    @Override
    public void onLoad(){
        super.onLoad();
        if(!level.isClientSide){
            updateNeighbors();
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

        if (cap == ForgeCapabilities.ENERGY)
            return energyCap.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER)
            return inventoryCap.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
        inventoryCap.invalidate();
    }

    private void clampEnergyToCapacity(){
        int max = energy.getMaxEnergyStored();
        if(energy.getEnergyStored() > max){
            energy.setEnergy(max);
        }
    }

    public void updateNeighbors(){
        if(level == null) return;
        for(Direction dir : Direction.values()){
            BlockPos target = worldPosition.relative(dir);
            neighbors[dir.ordinal()] = level.getBlockEntity(worldPosition.relative(dir));
        }
    }

    private void pushEnergy() {
        int available = energy.getEnergyStored();
        if(available <= 0) return;
        Direction dir = Direction.values()[pushIndex];
        pushIndex = (pushIndex + 1) % 6;

        BlockEntity neighbor = neighbors[dir.ordinal()];
        if (neighbor == null) return;

        LazyOptional<IEnergyStorage> cap = neighbor.getCapability(ForgeCapabilities.ENERGY,dir.getOpposite());

        if(!cap.isPresent()) return;

        IEnergyStorage storage = cap.orElse(null);
        if(storage == null || !storage.canReceive()) return;

        int transfer = Math.min(available,Integer.MAX_VALUE);
        int extractable = energy.extractEnergy(transfer, true);
        if(extractable <= 0) return;
        int received = storage.receiveEnergy(extractable,false);

        if(received > 0){
            energy.extractEnergy(received,false);
        }

    }

    private void clampEnergy(){
        int stored = energy.getEnergyStored();
        int max = energy.getMaxEnergyStored();
        if(stored > max){
            energy.extractEnergy(stored - max,false);
        }
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player){
        return new PanakeiaGeneratorMenu(id,playerInventory,this,data);
    }

    @Override
    public Component getDisplayName(){
        return Component.translatable("block.alchemical_power.panakeia_generator");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
//        return saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag){
        load(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    public ItemStack getFuelStack() {
        return inventory.getStackInSlot(0);
    }

    public ItemStack getCatalystStack() {
        return inventory.getStackInSlot(1);
    }

    public ItemStack getActiveFuel() {
        int id = data.get(3);
        if(id <= 0) return ItemStack.EMPTY;

        return new ItemStack(Item.byId(id));
    }

    public int getCurrentOutput(){
        return lastOutput;
    }

    public int getFuelTint(){
        ItemStack fuel = activeFuel;
        if(fuel.isEmpty()) return 0xefefef;

        int tier = PANAKEIA_TIERS.getOrDefault(fuel.getItem(),-1);

        return switch (tier){
            case 0 -> 0x009999;
            case 1 -> 0xCCFFFF;
            case 2 -> 0xFFFF66;
            case 3 -> 0xFF6666;
            case 4 -> 0xFF33FF;
            case 5 -> 0x9999FF;
            case 6 -> 0xCCCCCC;
            default -> 0xefefef;
        };
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getFuelTier() {
        ItemStack fuel = activeFuel;
        if(fuel.isEmpty()){
            fuel = inventory.getStackInSlot(0);
        };
        return PANAKEIA_TIERS.getOrDefault(fuel.getItem(),-1);
    }
}
