package jp.nogami_rion.alchemical_power.util;

import jp.nogami_rion.alchemical_power.block.entity.AutoAlchemicalAssemblerBlockEntity;
import net.minecraftforge.energy.EnergyStorage;

import java.util.function.IntSupplier;

public class DynamicEnergyStorage extends EnergyStorage {
    private final IntSupplier capacitySupplier;

    public DynamicEnergyStorage(IntSupplier capacitySupplier,int maxReceive, int maxExtract) {
        super(0, maxReceive, maxExtract);
        this.capacitySupplier = capacitySupplier;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacitySupplier.getAsInt();
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate){
        int capacity = getMaxEnergyStored();
        long newEnergy = (long)this.energy + maxReceive;
        int received = Math.min(capacity - this.energy, Math.min(maxReceive, this.maxReceive));
        if (!simulate) {
            this.energy = (int)Math.min(capacity,newEnergy);
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract,boolean simulate) {
        int energyExtracted = Math.min(this.energy, Math.min(maxExtract, this.maxExtract));
        if(!simulate){
            this.energy -= energyExtracted;
        }
        return energyExtracted;
    }

    public void setEnergy(int amount) {
        this.energy = Math.min(amount, getMaxEnergyStored());
    }
}
