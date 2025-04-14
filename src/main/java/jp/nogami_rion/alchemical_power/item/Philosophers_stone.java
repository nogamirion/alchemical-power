package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Philosophers_stone extends Item {
    public Philosophers_stone() {
        super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack){
        return true;
    }

}
