package jp.nogami_rion.alchemical_power.registry;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModTiers {
    private ModTiers() {
    }

    public static final ResourceLocation UNITE_ID = new ResourceLocation(Alchemical_power.MODID, "unite_alloy");
    public static final ResourceLocation SINGULARITY_ID = new ResourceLocation(Alchemical_power.MODID, "singularity");

    private static final Tier UNITE_ALLOY = ModMaterialTiers.UNITE_ALLOY;
    private static final Tier SINGULARITY = ModMaterialTiers.SINGULARITY;

    public static void register(){
        TierSortingRegistry.registerTier(
                UNITE_ALLOY,
                UNITE_ID,
                List.of(Tiers.NETHERITE),
                List.of()
        );
        TierSortingRegistry.registerTier(
                SINGULARITY,
                SINGULARITY_ID,
                List.of(Tiers.NETHERITE),
                List.of()
        );

    }

}
