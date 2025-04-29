package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.BaseHoeItem;
import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Panakeia_Hoes {
    public static final BaseHoeItem T1_PANAKEIA_HOE = new BaseHoeItem(
            ModMaterialTiers.T1_INGOT,
            0,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseHoeItem T2_PANAKEIA_HOE = new BaseHoeItem(
            ModMaterialTiers.T2_INGOT,
            0,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseHoeItem T3_PANAKEIA_HOE = new BaseHoeItem(
            ModMaterialTiers.T3_INGOT,
            0,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
    );
    public static final BaseHoeItem T4_PANAKEIA_HOE = new BaseHoeItem(
            ModMaterialTiers.T4_GEM,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseHoeItem T5_PANAKEIA_HOE = new BaseHoeItem(
            ModMaterialTiers.T5_GEM,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseHoeItem T6_PANAKEIA_HOE = new BaseHoeItem(
            ModMaterialTiers.T6_INGOT,
            1,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
    );
    public static final BaseHoeItem UNITE_ALLOY_HOE = new BaseHoeItem(
            ModMaterialTiers.UNITE_ALLOY,
            1,
            -1.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
    );

}
