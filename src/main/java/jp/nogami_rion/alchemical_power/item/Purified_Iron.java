package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Purified_Iron extends Item {
    public Purified_Iron() {
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
