package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class T7_Alchemy_Dust extends Item {
    public T7_Alchemy_Dust(){
        super (new Properties().stacksTo(64).rarity(Rarity.EPIC));

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack){
        return true;
    }
}
