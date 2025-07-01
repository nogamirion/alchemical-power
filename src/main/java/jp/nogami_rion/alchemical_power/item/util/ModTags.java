package jp.nogami_rion.alchemical_power.item.util;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{


        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(Alchemical_power.MODID,name));
        }

    }

    public static class Items{
        public static final TagKey<Item> ALCHEMY_KIT_TIER1 = tag("alchemy_kit_tier1");
        public static final TagKey<Item> ALCHEMY_KIT_TIER2 = tag("alchemy_kit_tier2");
        public static final TagKey<Item> ALCHEMY_KIT_TIER3 = tag("alchemy_kit_tier3");
        public static final TagKey<Item> ALCHEMY_KIT_TIER4 = tag("alchemy_kit_tier4");
        public static final TagKey<Item> ALCHEMY_KIT_TIER5 = tag("alchemy_kit_tier5");
        public static final TagKey<Item> JEI_EMPTY_TAG = tag("jei_empty_tag");
        public static final TagKey<Item> T1_INGOT_MATERIAL = tag("t1_ingot_material");
        public static final TagKey<Item> T2_INGOT_MATERIAL = tag("t2_ingot_material");
        public static final TagKey<Item> T3_INGOT_MATERIAL = tag("t3_ingot_material");
        public static final TagKey<Item> T4_GEM_MATERIAL = tag("t4_gem_material");
        public static final TagKey<Item> T5_GEM_MATERIAL = tag("t5_gem_material");
        public static final TagKey<Item> T6_INGOT_MATERIAL = tag("t6_ingot_material");
        public static final TagKey<Item> T7_INGOT_MATERIAL = tag("t7_ingot_material");
        public static final TagKey<Item> COLLECTED_DOUBLING_COPPER = tag("collected_doubling_copper");
        public static final TagKey<Item> COLLECTED_DOUBLING_IRON = tag("collected_doubling_iron");
        public static final TagKey<Item> COLLECTED_DOUBLING_GOLD = tag("collected_doubling_gold");
        public static final TagKey<Item> COLLECTED_DOUBLING_DIAMOND = tag("collected_doubling_diamond");
        public static final TagKey<Item> COLLECTED_DOUBLING_EMERALD = tag("collected_doubling_emerald");
        public static final TagKey<Item> COLLECTED_DOUBLING_ANCIENT_DEBRIS = tag("collected_doubling_ancient_debris");
        public static final TagKey<Item> PACKAGED_DOUBLING_COPPER = tag("packaged_doubling_copper");
        public static final TagKey<Item> PACKAGED_DOUBLING_IRON = tag("packaged_doubling_iron");
        public static final TagKey<Item> PACKAGED_DOUBLING_GOLD = tag("packaged_doubling_gold");
        public static final TagKey<Item> PACKAGED_DOUBLING_DIAMOND = tag("packaged_doubling_diamond");
        public static final TagKey<Item> PACKAGED_DOUBLING_EMERALD = tag("packaged_doubling_emerald");
        public static final TagKey<Item> PACKAGED_DOUBLING_ANCIENT_DEBRIS = tag("packaged_doubling_ancient_debris");
        public static final TagKey<Item> JAMS = tag("jams");
        public static final TagKey<Item> BEEF_OR_PORK = tag("beef_or_pork");
        public static final TagKey<Item> NETHERITE_UPGRADE_TEMPLATE_MATERIALS = tag("netherite_upgrade_template_materials");


        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(Alchemical_power.MODID,name));
        }

    }
}
