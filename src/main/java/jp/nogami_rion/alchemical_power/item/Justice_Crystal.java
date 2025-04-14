package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Justice_Crystal extends Item {
    public Justice_Crystal() {
        super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
    }
}
