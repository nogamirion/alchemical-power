package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.recipe.Alchemy_Table_Recipe;
import jp.nogami_rion.alchemical_power.screen.Alchemy_Table_Screen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEI_Alchemical_Power_Plugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Alchemical_power.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new Alchemy_Table_Category(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<Alchemy_Table_Recipe> AlchemyTableRecipes = recipeManager.getAllRecipesFor(Alchemy_Table_Recipe.Type.INSTANCE);
        registration.addRecipes(Alchemy_Table_Category.ALCHEMY_TABLE_RECIPE_TYPE,AlchemyTableRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(Alchemy_Table_Screen.class,116,42,15,12,
                Alchemy_Table_Category.ALCHEMY_TABLE_RECIPE_TYPE);
    }
}
