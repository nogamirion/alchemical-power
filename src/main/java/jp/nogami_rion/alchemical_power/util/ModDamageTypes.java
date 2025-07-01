package jp.nogami_rion.alchemical_power.util;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> SINGULARITY_TRUE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Alchemical_power.MODID, "singularity_true"));

    public static DamageSource singularityTrue(ServerLevel level,@Nullable LivingEntity attacker) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(SINGULARITY_TRUE),attacker);
    }
}