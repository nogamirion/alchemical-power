package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class T1_Alchemy_Dust extends Item {
    public T1_Alchemy_Dust(){
        super (new Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
