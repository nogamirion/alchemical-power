package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class High_Density_Alchemical_Netherite extends Item {
    public High_Density_Alchemical_Netherite() {
        super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
    }
}
