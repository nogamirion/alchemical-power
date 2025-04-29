package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.BasePickaxeItem;
import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Panakeia_Pickaxes {
    public static final BasePickaxeItem T1_PANAKEIA_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.T1_INGOT,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1)
    );
    public static final BasePickaxeItem T2_PANAKEIA_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.T2_INGOT,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1)
    );
    public static final BasePickaxeItem T3_PANAKEIA_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.T3_INGOT,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1)
    );
    public static final BasePickaxeItem T4_PANAKEIA_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.T4_GEM,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BasePickaxeItem T5_PANAKEIA_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.T5_GEM,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)
    );
    public static final BasePickaxeItem T6_PANAKEIA_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.T6_INGOT,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1).rarity(Rarity.RARE)
    );
    public static final BasePickaxeItem UNITE_ALLOY_PICKAXE = new BasePickaxeItem(
            ModMaterialTiers.UNITE_ALLOY,
            1,
            -2.8F,
            new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)
    );

}
