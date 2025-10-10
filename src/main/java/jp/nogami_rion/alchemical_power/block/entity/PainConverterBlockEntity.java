package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.block.custom.ModGenEnergyStorage;
import jp.nogami_rion.alchemical_power.event.DamageCache;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PainConverterBlockEntity extends BlockEntity {
    private final ModGenEnergyStorage energyStorage = new ModGenEnergyStorage(1000000,1000000);
    private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energyStorage);;
    private final Map<UUID,Float> lastHurthMap = new HashMap<>();
    private final int GenFactor = 20;

    public PainConverterBlockEntity(BlockPos pos,BlockState state) {
        super(ModBlockEntities.PAIN_CONVERTER_BE.get(),pos,state);
    }

    public void tick(Level level, BlockPos pos, BlockState state){
        if(!level.isClientSide()){
            //発電機構
            AABB box = new AABB(pos).inflate(0.5,0.5,0.5);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,box);

            for(LivingEntity entity : entities){
                float currentHurt = entity.hurtTime;
                float lastHurt = this.lastHurthMap.getOrDefault(entity.getUUID(),0.0f);

                if(currentHurt > 0 && lastHurt == 0){
                    float damage = DamageCache.consumeDamage(entity);
                    if(damage > 0){
                        int energyGain =(int)(Math.pow(damage,1.5) * GenFactor);
                        this.energyStorage.receiveEnergy(energyGain,false);
                    }
                }

                this.lastHurthMap.put(entity.getUUID(),currentHurt);

            }

            //隣接装置への送電
            for(Direction dir : Direction.values()){
                BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
                if(neighbor != null){
                    neighbor.getCapability(ForgeCapabilities.ENERGY,dir.getOpposite()).ifPresent(storage ->{
                        int sent = this.energyStorage.extractEnergy(this.energyStorage.getMaxEnergyStored(),true);
                        int received = storage.receiveEnergy(sent,false);
                        this.energyStorage.extractEnergy(received,false);
                    });
                }
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps(){
        super.invalidateCaps();
        energyCap.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag){
        super.saveAdditional(tag);
        tag.putInt("Energy",energyStorage.getEnergyStored());
    }

    @Override
    public void load(CompoundTag tag){
        super.load(tag);
        if(tag.contains("Energy")) {
            energyStorage.setEnergy(tag.getInt("Energy"));
        }
    }

    public int getEnergyStored(){
        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergyStored(){
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public void onLoad(){
        super.onLoad();
        setChanged();
    }

}
