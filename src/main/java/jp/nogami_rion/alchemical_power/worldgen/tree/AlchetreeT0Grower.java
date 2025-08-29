package jp.nogami_rion.alchemical_power.worldgen.tree;

import jp.nogami_rion.alchemical_power.worldgen.featuers.ModAdditionalOreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class AlchetreeT0Grower extends AbstractTreeGrower {
    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomSource, boolean b) {
        return ModAdditionalOreFeatures.ALCHETREE_T0_KEY;

    }
}
