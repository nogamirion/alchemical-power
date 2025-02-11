package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.creativetab;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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
                .add(blocklist.STONE_CONTAINING_ALCHEMY_DUST.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get())
                .add(blocklist.UNITE_ALLOY_BLOCK.get())
        ;

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(blocklist.STONE_CONTAINING_ALCHEMY_DUST.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get())
        ;

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get())
                .add(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get())
                ;

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get())
                .add(blocklist.UNITE_ALLOY_BLOCK.get())
                ;




    }
}
