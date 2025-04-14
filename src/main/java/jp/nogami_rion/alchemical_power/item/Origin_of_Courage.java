package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Origin_of_Courage extends Item {
    public Origin_of_Courage() {
        super(new Properties().stacksTo(64).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack){
        return true;
    }
}
