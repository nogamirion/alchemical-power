package jp.nogami_rion.alchemical_power.dategen.server;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.worldgen.biome.BiomeModifiers;
import jp.nogami_rion.alchemical_power.worldgen.featuers.ModAdditionalOreFeatures;
import jp.nogami_rion.alchemical_power.worldgen.placement.ModAdditionalPlacement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModAdditionalOreFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModAdditionalPlacement::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifiers::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of(Alchemical_power.MODID));
    }
}
