package jp.nogami_rion.alchemical_power.item.baseclass;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class BasePickaxeItem extends PickaxeItem {
    public BasePickaxeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Item.Properties properties) {
        super(tier,  attackDamageModifier, attackSpeedModifier, properties);
    }
}