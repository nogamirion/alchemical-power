package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.BaseSwordItem;
import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Panakeia_Swords {
    public static final BaseSwordItem T1_PANAKEIA_SWORD = new BaseSwordItem(
            ModMaterialTiers.T1_INGOT,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseSwordItem T2_PANAKEIA_SWORD = new BaseSwordItem(
            ModMaterialTiers.T2_INGOT,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseSwordItem T3_PANAKEIA_SWORD = new BaseSwordItem(
            ModMaterialTiers.T3_INGOT,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseSwordItem T4_PANAKEIA_SWORD = new BaseSwordItem(
            ModMaterialTiers.T4_GEM,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseSwordItem T5_PANAKEIA_SWORD = new BaseSwordItem(
            ModMaterialTiers.T5_GEM,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseSwordItem T6_PANAKEIA_SWORD = new BaseSwordItem(
            ModMaterialTiers.T6_INGOT,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
    );
    public static final BaseSwordItem UNITE_ALLOY_SWORD = new BaseSwordItem(
            ModMaterialTiers.UNITE_ALLOY,
            3,
            -2.4F,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
    );

}
