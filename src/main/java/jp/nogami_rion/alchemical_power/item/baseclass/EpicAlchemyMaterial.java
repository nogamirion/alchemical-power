package jp.nogami_rion.alchemical_power.item.baseclass;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EpicAlchemyMaterial extends Item {
    public EpicAlchemyMaterial() {
        super(new Properties().stacksTo(64).rarity(Rarity.EPIC).fireResistant());
    }
}
