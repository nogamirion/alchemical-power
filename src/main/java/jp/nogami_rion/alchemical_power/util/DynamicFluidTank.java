package jp.nogami_rion.alchemical_power.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.function.IntSupplier;
import java.util.function.Predicate;

public class DynamicFluidTank extends FluidTank {
    private final IntSupplier capacitySupplier;
    private final Predicate<FluidStack> validator;

    public DynamicFluidTank(IntSupplier capacitySupplier, Predicate<FluidStack> validator) {
        super(0, validator);
        this.capacitySupplier = capacitySupplier;
        this.validator = validator;
    }

    @Override
    public int getCapacity() {
        return capacitySupplier.getAsInt();
    }

    @Override
    public int getSpace() {
        return Math.max(0, getCapacity() - fluid.getAmount());
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return validator.test(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) return 0;
        if (!fluid.isEmpty() && fluid.getFluid() != resource.getFluid()) return 0;

        int filled = Math.min(getSpace(), resource.getAmount());
        if (filled <= 0) return 0;

        if (action.execute()) {
            if (fluid.isEmpty()) {
                fluid = resource.copy();
                fluid.setAmount(filled);
            } else {
                fluid.grow(filled);
            }
            onContentsChanged();
        }
        return filled;
    }

    @Override
    public FluidTank readFromNBT(CompoundTag nbt) {
        super.readFromNBT(nbt);
        clampToCapacity();
        return this;
    }

    public void clampToCapacity() {
        if (fluid.getAmount() > getCapacity()) {
            fluid.setAmount(getCapacity());
            onContentsChanged();
        }
    }
}
