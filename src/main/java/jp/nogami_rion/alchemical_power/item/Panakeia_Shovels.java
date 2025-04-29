package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.BaseShovelItem;
import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Panakeia_Shovels {
    public static final BaseShovelItem T1_PANAKEIA_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.T1_INGOT,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseShovelItem T2_PANAKEIA_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.T2_INGOT,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseShovelItem T3_PANAKEIA_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.T3_INGOT,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseShovelItem T4_PANAKEIA_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.T4_GEM,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseShovelItem T5_PANAKEIA_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.T5_GEM,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseShovelItem T6_PANAKEIA_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.T6_INGOT,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
    );
    public static final BaseShovelItem UNITE_ALLOY_SHOVEL = new BaseShovelItem(
            ModMaterialTiers.UNITE_ALLOY,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
    );

}
