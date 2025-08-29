package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Alchemical_power.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.ALCHEMY_KIT_TIER1)
                .add(itemlist.ALCHEMY_BEGINNERS_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER2);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER2)
                .add(itemlist.ALCHEMY_INTERMEDIATE_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER3);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER3)
                .add(itemlist.ALCHEMY_EXPERTS_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER4);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER4)
                .add(itemlist.ULTIMATE_ALCHEMY_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER5);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER5)
                .add(itemlist.PHILOSOPHERS_STONE.get());

        this.tag(ModTags.Items.JEI_EMPTY_TAG)
                .add(Blocks.AIR.asItem());

        this.tag(ModTags.Items.T1_INGOT_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "ingots/copper"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/tin"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/lead"));

        this.tag(ModTags.Items.T2_INGOT_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "ingots/iron"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/bronze"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/ironwood"))
                .addOptionalTag(new ResourceLocation("forge", "gems/zanite"))
                .addOptionalTag(new ResourceLocation("blue_skies", "gems/aquite"))
                .addOptionalTag(new ResourceLocation("forge", "ingot/inferium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/wrought_iron"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/cloggrum"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/invar"));

        this.tag(ModTags.Items.T3_INGOT_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "ingots/gold"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/silver"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/sterling_silver"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/steel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/rose_gold"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/steeleaf"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/prudentium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/aluminum"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/froststeel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/manasteel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/cobalt"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/cobalt_brass"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/osmium"));

        this.tag(ModTags.Items.T4_GEM_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "gems/diamond"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/platinum"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/refined_glowstone"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/knightmetal"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/tertium"))
                .addOptionalTag(new ResourceLocation("blue_skies", "gems/diopside"))
                .addOptionalTag(new ResourceLocation("blue_skies", "gems/charoite"))
                .addOptionalTag(new ResourceLocation("forge", "ingot/titanium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/tangsten"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/elementium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/stainless_steel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/blue_steel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/damascus_steel"));

        this.tag(ModTags.Items.T5_GEM_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "gems/emerald"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/iridium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/imperium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/horizonite"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/ultimet"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/tungsten_carbide"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/tungsten_steel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/hsse"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/forgotten_metal"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/draconium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/uterium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/vanadium_steel"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/red_steel"));

        this.tag(ModTags.Items.T6_INGOT_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "ingots/netherite"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/refined_obsidian"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/supremium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/naquadah_alloy"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/duranium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/regalium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/draconium_awakened"));

        this.tag(ModTags.Items.T7_INGOT_MATERIAL)
                .addOptionalTag(new ResourceLocation("forge", "ingots/awakened_supremium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/neutronium"))
                .addOptionalTag(new ResourceLocation("forge", "ingots/allthemodium"));

        this.tag(ModTags.Items.COLLECTED_DOUBLING_COPPER)
                .add(itemlist.COLLECTED_CRUSHED_RAW_COPPER.get())
                .add(itemlist.COLLECTED_GRANULATED_COPPER.get())
                .add(itemlist.COLLECTED_SOFTENED_COPPER.get())
                .add(itemlist.COLLECTED_PURIFIED_COPPER.get());

        this.tag(ModTags.Items.COLLECTED_DOUBLING_IRON)
                .add(itemlist.COLLECTED_CRUSHED_RAW_IRON.get())
                .add(itemlist.COLLECTED_GRANULATED_IRON.get())
                .add(itemlist.COLLECTED_SOFTENED_IRON.get())
                .add(itemlist.COLLECTED_PURIFIED_IRON.get());

        this.tag(ModTags.Items.COLLECTED_DOUBLING_GOLD)
                .add(itemlist.COLLECTED_CRUSHED_RAW_GOLD.get())
                .add(itemlist.COLLECTED_GRANULATED_GOLD.get())
                .add(itemlist.COLLECTED_SOFTENED_GOLD.get())
                .add(itemlist.COLLECTED_PURIFIED_GOLD.get());

        this.tag(ModTags.Items.COLLECTED_DOUBLING_DIAMOND)
                .add(itemlist.COLLECTED_CRUSHED_DIAMOND_ORE.get())
                .add(itemlist.COLLECTED_GRANULATED_DIAMOND.get())
                .add(itemlist.COLLECTED_SOFTENED_DIAMOND.get())
                .add(itemlist.COLLECTED_PURIFIED_DIAMOND.get());

        this.tag(ModTags.Items.COLLECTED_DOUBLING_EMERALD)
                .add(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get())
                .add(itemlist.COLLECTED_GRANULATED_EMERALD.get())
                .add(itemlist.COLLECTED_SOFTENED_EMERALD.get())
                .add(itemlist.COLLECTED_PURIFIED_EMERALD.get());

        this.tag(ModTags.Items.COLLECTED_DOUBLING_ANCIENT_DEBRIS)
                .add(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get())
                .add(itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get())
                .add(itemlist.COLLECTED_SOFTENED_ANCIENT_DEBRIS.get())
                .add(itemlist.COLLECTED_PURIFIED_ANCIENT_DEBRIS.get());

        this.tag(ModTags.Items.PACKAGED_DOUBLING_COPPER)
                .add(itemlist.PACKAGED_CRUSHED_RAW_COPPER.get())
                .add(itemlist.PACKAGED_GRANULATED_COPPER.get())
                .add(itemlist.PACKAGED_SOFTENED_COPPER.get())
                .add(itemlist.PACKAGED_PURIFIED_COPPER.get());

        this.tag(ModTags.Items.PACKAGED_DOUBLING_IRON)
                .add(itemlist.PACKAGED_CRUSHED_RAW_IRON.get())
                .add(itemlist.PACKAGED_GRANULATED_IRON.get())
                .add(itemlist.PACKAGED_SOFTENED_IRON.get())
                .add(itemlist.PACKAGED_PURIFIED_IRON.get());

        this.tag(ModTags.Items.PACKAGED_DOUBLING_GOLD)
                .add(itemlist.PACKAGED_CRUSHED_RAW_GOLD.get())
                .add(itemlist.PACKAGED_GRANULATED_GOLD.get())
                .add(itemlist.PACKAGED_SOFTENED_GOLD.get())
                .add(itemlist.PACKAGED_PURIFIED_GOLD.get());

        this.tag(ModTags.Items.PACKAGED_DOUBLING_DIAMOND)
                .add(itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get())
                .add(itemlist.PACKAGED_GRANULATED_DIAMOND.get())
                .add(itemlist.PACKAGED_SOFTENED_DIAMOND.get())
                .add(itemlist.PACKAGED_PURIFIED_DIAMOND.get());

        this.tag(ModTags.Items.PACKAGED_DOUBLING_EMERALD)
                .add(itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get())
                .add(itemlist.PACKAGED_GRANULATED_EMERALD.get())
                .add(itemlist.PACKAGED_SOFTENED_EMERALD.get())
                .add(itemlist.PACKAGED_PURIFIED_EMERALD.get());

        this.tag(ModTags.Items.PACKAGED_DOUBLING_ANCIENT_DEBRIS)
                .add(itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get())
                .add(itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get())
                .add(itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get())
                .add(itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get());

        this.tag(ModTags.Items.JAMS)
                .add(itemlist.SWEET_BERRY_JAM.get())
                .add(itemlist.GLOW_BERRY_JAM.get())
                .add(itemlist.PUMPKIN_JAM.get())
                .add(itemlist.MELON_JAM.get())
                .add(itemlist.APPLE_JAM.get())
                .add(itemlist.CHORUS_FRUIT_JAM.get())
                .add(itemlist.TOMATO_JAM.get())
                .add(itemlist.LEMON_JAM.get());

        this.tag(ModTags.Items.BEEF_OR_PORK)
                .add(Items.BEEF)
                .add(Items.PORKCHOP);

        this.tag(ModTags.Items.NETHERITE_UPGRADE_TEMPLATE_MATERIALS)
                .add(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE)
                .add(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(blocklist.ALCHETREE_LOG.get().asItem())
                .add(blocklist.ALCHETREE_WOOD.get().asItem())
                .add(blocklist.STRIPPED_ALCHETREE_LOG.get().asItem())
                .add(blocklist.STRIPPED_ALCHETREE_WOOD.get().asItem())
        ;

        this.tag(ItemTags.PLANKS)
                .add(blocklist.ALCHETREE_PLANKS.get().asItem());





    }
}
