package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.item.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
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
                .add(blocklist.T1_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.T2_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.T3_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.T4_PANAKEIA_GEM_BLOCK.get())
                .add(blocklist.T5_PANAKEIA_GEM_BLOCK.get())
                .add(blocklist.T6_PANAKEIA_INGOT_BLOCK.get())
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
                .add(blocklist.COBBLESTONE_GENERATOR_MK1.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK2.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK3.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK4.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK5.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK6.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK7.get())
                .add(blocklist.COBBLESTONE_GENERATOR_MK8.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK1.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK2.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK3.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK4.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK5.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK6.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK7.get())
                .add(blocklist.X9_COBBLESTONE_GENERATOR_MK8.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK1.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK2.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK3.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK4.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK5.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK6.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK7.get())
                .add(blocklist.X225_COBBLESTONE_GENERATOR_MK8.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK1.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK2.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK3.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK4.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK5.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK6.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK7.get())
                .add(blocklist.X38025_COBBLESTONE_GENERATOR_MK8.get())

        ;

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(blocklist.PANAKEIA_BEARING_STONE.get())
                .add(blocklist.T1_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.T2_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.INFINITE_WATER_BARREL.get())
                .add(blocklist.INFINITE_LAVA_BARREL.get())

        ;

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(blocklist.T3_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.T4_PANAKEIA_GEM_BLOCK.get())
                .add(blocklist.T5_PANAKEIA_GEM_BLOCK.get())
                .add(blocklist.X9_COBBLESTONE.get())
                ;

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(blocklist.T6_PANAKEIA_INGOT_BLOCK.get())
                .add(blocklist.UNITE_ALLOY_BLOCK.get())
                .add(blocklist.X225_COBBLESTONE.get())
                .add(blocklist.X38025_COBBLESTONE.get())
                .add(blocklist.IMITATED_BEDROCK.get())
                ;

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(blocklist.ALCHETREE_LOG.get())
                .add(blocklist.ALCHETREE_WOOD.get())
                .add(blocklist.STRIPPED_ALCHETREE_LOG.get())
                .add(blocklist.STRIPPED_ALCHETREE_WOOD.get())
        ;

        this.tag(BlockTags.LEAVES)
                .add(blocklist.ALCHETREE_LEAVES[0].get())
                .add(blocklist.ALCHETREE_LEAVES[1].get())
                .add(blocklist.ALCHETREE_LEAVES[2].get())
                .add(blocklist.ALCHETREE_LEAVES[3].get())
                .add(blocklist.ALCHETREE_LEAVES[4].get())
                .add(blocklist.ALCHETREE_LEAVES[5].get())
                .add(blocklist.ALCHETREE_LEAVES[6].get())
                .add(blocklist.ALCHETREE_LEAVES[7].get())
                ;

        this.tag(BlockTags.PLANKS)
                .add(blocklist.ALCHETREE_PLANKS.get());

        this.tag(BlockTags.WOODEN_FENCES)
                .add(blocklist.ALCHETREE_FENCE.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(blocklist.ALCHETREE_FENCE_GATE.get());
        this.tag(BlockTags.WALLS)
                .add(blocklist.ALCHETREE_WALL.get());
        this.tag(BlockTags.WOODEN_SLABS)
                .add(blocklist.ALCHETREE_SLAB.get());
        this.tag(BlockTags.WOODEN_STAIRS)
                .add(blocklist.ALCHETREE_STAIRS.get());
        this.tag(BlockTags.WOODEN_DOORS)
                .add(blocklist.ALCHETREE_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(blocklist.ALCHETREE_TRAPDOOR.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(blocklist.ALCHETREE_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_BUTTONS)
                .add(blocklist.ALCHETREE_BUTTON.get());




    }
}
