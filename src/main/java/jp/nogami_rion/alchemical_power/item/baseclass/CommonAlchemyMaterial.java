package jp.nogami_rion.alchemical_power.item.baseclass;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class CommonAlchemyMaterial extends Item {
    public CommonAlchemyMaterial() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
