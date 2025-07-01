package jp.nogami_rion.alchemical_power.item.materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Temperance_Crystal extends Item {
    public Temperance_Crystal() {
        super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
    }
}
