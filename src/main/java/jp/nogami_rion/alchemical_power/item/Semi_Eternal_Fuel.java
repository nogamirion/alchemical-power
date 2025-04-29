package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class Semi_Eternal_Fuel extends Item {
    public Semi_Eternal_Fuel() {
        super(new Properties().durability(64).fireResistant().rarity(Rarity.EPIC));
    }


    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 18225;
    }

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue());
        double consumeProbability = 0.0833333;
        if(Math.random() < consumeProbability) {
            retval.setDamageValue(itemstack.getDamageValue());
            if (retval.getDamageValue() >= retval.getMaxDamage() + 1) {
                return ItemStack.EMPTY;
            }
        }
        return retval;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        return false;
    }
}
