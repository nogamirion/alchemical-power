package jp.nogami_rion.alchemical_power.item.rune;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Rune_of_Equivalent extends Item {
    public Rune_of_Equivalent() {
        super(new Properties().durability(64));
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }
}
