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
  //      public static final TagKey<Item> ALCHEMY_KIT_TIER5 = tag("alchemy_kit_tier5");

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(Alchemical_power.MODID,name));
        }

    }
}
