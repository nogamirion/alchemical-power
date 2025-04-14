package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Alchemical_power.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(blocklist.PANAKEIA_BEARING_STONE.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get())
                .add(blocklist.UNITE_ALLOY_BLOCK.get())
                .add(blocklist.X9_COBBLESTONE.get())
                .add(blocklist.X225_COBBLESTONE.get())
                .add(blocklist.X38025_COBBLESTONE.get())
                .add(blocklist.IMITATED_BEDROCK.get())
                .add(blocklist.T1_COMBUSTION_RUNE_BLOCK.get())
                .add(blocklist.T2_COMBUSTION_RUNE_BLOCK.get())
                .add(blocklist.T3_COMBUSTION_RUNE_BLOCK.get())
                .add(blocklist.T4_COMBUSTION_RUNE_BLOCK.get())
                .add(blocklist.T5_COMBUSTION_RUNE_BLOCK.get())
                .add(blocklist.T6_COMBUSTION_RUNE_BLOCK.get())
        ;

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(blocklist.PANAKEIA_BEARING_STONE.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get())
        ;

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get())
                .add(blocklist.X9_COBBLESTONE.get())
                ;

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get())
                .add(blocklist.UNITE_ALLOY_BLOCK.get())
                .add(blocklist.X225_COBBLESTONE.get())
                .add(blocklist.X38025_COBBLESTONE.get())
                .add(blocklist.IMITATED_BEDROCK.get())
                ;






    }
}
