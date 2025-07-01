package jp.nogami_rion.alchemical_power.item.materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Wisdom_Crystal extends Item {
    public Wisdom_Crystal() {
        super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
    }
}
