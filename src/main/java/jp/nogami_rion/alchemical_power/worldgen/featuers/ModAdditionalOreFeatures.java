package jp.nogami_rion.alchemical_power.worldgen.featuers;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModAdditionalOreFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> PANAKEIA_BEARING_STONE_KEY = createKey("panakeia_bearing_stone");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T0_KEY = createKey("alchetree_t0");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T1_KEY = createKey("alchetree_t1");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T2_KEY = createKey("alchetree_t2");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T3_KEY = createKey("alchetree_t3");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T4_KEY = createKey("alchetree_t4");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T5_KEY = createKey("alchetree_t5");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T6_KEY = createKey("alchetree_t6");
    public static final ResourceKey<ConfiguredFeature<?,?>> ALCHETREE_T7_KEY = createKey("alchetree_t7");



    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context){
        RuleTest stone = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslate = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> panakeia_bearing_stone = List.of(
                OreConfiguration.target(stone, blocklist.PANAKEIA_BEARING_STONE.get().defaultBlockState()),
                OreConfiguration.target(deepslate, blocklist.PANAKEIA_BEARING_STONE.get().defaultBlockState()));

        FeatureUtils.register(context,PANAKEIA_BEARING_STONE_KEY,Feature.ORE,
                new OreConfiguration(panakeia_bearing_stone,9));

        register(context,ALCHETREE_T0_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4, 1, 1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[0].get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context,ALCHETREE_T1_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[1].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

        register(context,ALCHETREE_T2_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[2].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

        register(context,ALCHETREE_T3_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[3].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

        register(context,ALCHETREE_T4_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[4].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

        register(context,ALCHETREE_T5_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[5].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

        register(context,ALCHETREE_T6_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[6].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

        register(context,ALCHETREE_T7_KEY,Feature.TREE,new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(blocklist.ALCHETREE_LOG.get()),
                new StraightTrunkPlacer(4,1,1),
                BlockStateProvider.simple(blocklist.ALCHETREE_LEAVES[7].get()),
                new BlobFoliagePlacer(ConstantInt.of(3),ConstantInt.of(2),3),
                        new TwoLayersFeatureSize(1,0,2)).build());

    }

    public static ResourceKey<ConfiguredFeature<?,?>> createKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,new ResourceLocation(Alchemical_power.MODID,name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
