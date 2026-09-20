package jp.nogami_rion.alchemical_power.util;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> ARSENAL_BOW_PROJECTILES = TagKey.create(Registries.ITEM, new ResourceLocation(Alchemical_power.MODID, "arsenal_bow_projectiles"));
    public static final TagKey<Item> ARSENAL_BOW_PROJECTILE_BLACKLIST = TagKey.create(Registries.ITEM, new ResourceLocation(Alchemical_power.MODID, "arsenal_bow_projectile_blacklist"));
}
