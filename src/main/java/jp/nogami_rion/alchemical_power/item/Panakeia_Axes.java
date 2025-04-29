package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.BaseAxeItem;
import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Panakeia_Axes {
    public static final BaseAxeItem T1_PANAKEIA_AXE = new BaseAxeItem(
            ModMaterialTiers.T1_INGOT,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1)
    );
    public static final BaseAxeItem T2_PANAKEIA_AXE = new BaseAxeItem(
            ModMaterialTiers.T2_INGOT,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1)
    );
    public static final BaseAxeItem T3_PANAKEIA_AXE = new BaseAxeItem(
            ModMaterialTiers.T3_INGOT,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1)
    );
    public static final BaseAxeItem T4_PANAKEIA_AXE = new BaseAxeItem(
            ModMaterialTiers.T4_GEM,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseAxeItem T5_PANAKEIA_AXE = new BaseAxeItem(
            ModMaterialTiers.T5_GEM,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BaseAxeItem T6_PANAKEIA_AXE = new BaseAxeItem(
            ModMaterialTiers.T6_INGOT,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
    );
    public static final BaseAxeItem UNITE_ALLOY_AXE = new BaseAxeItem(
            ModMaterialTiers.UNITE_ALLOY,
            5,
            -3.0F,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
    );

}
