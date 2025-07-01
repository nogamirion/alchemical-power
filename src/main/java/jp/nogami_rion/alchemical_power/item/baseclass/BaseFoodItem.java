package jp.nogami_rion.alchemical_power.item.baseclass;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class BaseFoodItem extends Item {
    public BaseFoodItem(int stackSize,int nutrition,float saturation) {
        super(new Item.Properties().stacksTo(stackSize).food((new FoodProperties.Builder()).nutrition(nutrition).saturationMod(saturation).build()));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.EAT;
    }
}
