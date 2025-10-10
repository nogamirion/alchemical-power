package jp.nogami_rion.alchemical_power.block.custom;

import net.minecraftforge.energy.EnergyStorage;

public class ModGenEnergyStorage extends EnergyStorage {
    public ModGenEnergyStorage(int capacity, int maxTransfer) {
        super(capacity,maxTransfer,maxTransfer);
    }

    public void setEnergy(int energy){
        this.energy = energy;
    }

    public int getMaxTransfer(){
        return this.maxReceive;
    }

}
