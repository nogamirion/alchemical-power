package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class High_Density_Alchemical_Copper extends Item {
    public High_Density_Alchemical_Copper() {
        super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
    }
}
