package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Blank_Rune extends Item {
    public Blank_Rune() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
