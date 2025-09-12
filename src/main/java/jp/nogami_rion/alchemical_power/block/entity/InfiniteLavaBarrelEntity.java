package jp.nogami_rion.alchemical_power.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class InfiniteLavaBarrelEntity extends BlockEntity {

    private final IFluidHandler tank = new InfiniteLavaHandler();
    private final LazyOptional<IFluidHandler> fluidCap;

    public InfiniteLavaBarrelEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INFINITE_LAVA_BARREL_BE.get(),pPos,pBlockState);
        this.fluidCap = LazyOptional.of(InfiniteLavaHandler::new);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidCap.invalidate();
    }

    // Capability 提供
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return LazyOptional.of(() -> this.tank).cast();
        }
        return super.getCapability(cap, side);
    }

    private static class InfiniteLavaHandler implements IFluidHandler{

        private static final int TANK_CAPACITY = Integer.MAX_VALUE;

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int i) {
            if(i != 0) return FluidStack.EMPTY;
            return new FluidStack(Fluids.LAVA,TANK_CAPACITY);
        }

        @Override
        public int getTankCapacity(int i) {
            if(i != 0) return 0;
            return TANK_CAPACITY;
        }

        @Override
        public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
            if(i != 0) return false;
            return fluidStack.getFluid() == Fluids.LAVA;
        }

        @Override
        public int fill(FluidStack fluidStack, FluidAction fluidAction) {
            if(fluidStack == null || fluidStack.isEmpty())return 0;
            int accepted = Math.min(fluidStack.getAmount(),TANK_CAPACITY);
            return accepted;
        }

        @Override
        public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
            if(fluidStack == null || fluidStack.isEmpty()) return FluidStack.EMPTY;
            if(fluidStack.getFluid() != Fluids.LAVA) return FluidStack.EMPTY;
            int wanted = Math.min(fluidStack.getAmount(),TANK_CAPACITY);
            return new FluidStack(Fluids.LAVA,wanted);
        }

        @Override
        public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
            if(i <= 0) return FluidStack.EMPTY;
            int amount = Math.min(i,TANK_CAPACITY);
            return new FluidStack(Fluids.LAVA,amount);
        }
    }
}
