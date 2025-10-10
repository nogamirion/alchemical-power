package jp.nogami_rion.alchemical_power.item.baseclass;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class EpicFoilAlchemyMaterial extends Item {
    public EpicFoilAlchemyMaterial() {
        super(new Properties().stacksTo(64).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
