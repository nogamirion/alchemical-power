package jp.nogami_rion.alchemical_power.init;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

@JeiPlugin
public class ModJEIInformation implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("alchemical_power", "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(itemlist.T1_PANAKEIA.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.t1_panakeia.tooltip"));

        registration.addIngredientInfo(new ItemStack(itemlist.T2_PANAKEIA.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.t2_panakeia.tooltip"));

        registration.addIngredientInfo(new ItemStack(itemlist.T3_PANAKEIA.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.t3_panakeia.tooltip"));

        registration.addIngredientInfo(new ItemStack(itemlist.T4_PANAKEIA.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.t4_panakeia.tooltip"));

        registration.addIngredientInfo(new ItemStack(itemlist.T5_PANAKEIA.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.t5_panakeia.tooltip"));

        registration.addIngredientInfo(new ItemStack(itemlist.T6_PANAKEIA.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.t6_panakeia.tooltip"));

        registration.addIngredientInfo(new ItemStack(itemlist.THE_DESTROYER_OF_THE_RIAL.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.alchemical_power.the_destroyer_of_the_rial.tooltip"));
    }
}
