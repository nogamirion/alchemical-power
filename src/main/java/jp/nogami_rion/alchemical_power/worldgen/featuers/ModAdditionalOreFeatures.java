package jp.nogami_rion.alchemical_power.worldgen.featuers;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModAdditionalOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> PANAKEIA_BEARING_STONE_KEY = createKey("panakeia_bearing_stone");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context){
        RuleTest stone = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslate = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> panakeia_bearing_stone = List.of(
                OreConfiguration.target(stone, blocklist.PANAKEIA_BEARING_STONE.get().defaultBlockState()),
                OreConfiguration.target(deepslate, blocklist.PANAKEIA_BEARING_STONE.get().defaultBlockState()));

        FeatureUtils.register(context,PANAKEIA_BEARING_STONE_KEY,Feature.ORE,
                new OreConfiguration(panakeia_bearing_stone,9));
    }

    public static ResourceKey<ConfiguredFeature<?,?>> createKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(Alchemical_power.MODID,name));
    }
}
