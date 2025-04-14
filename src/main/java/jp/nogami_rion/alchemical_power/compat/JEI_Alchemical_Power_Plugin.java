package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.recipe.*;
import jp.nogami_rion.alchemical_power.screen.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
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
        registration.addRecipeCategories(new Hermes_Workbench_Category(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new Transcendental_Table_Category(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new Alchemical_Engraver_Category(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new Rune_Activator_Category(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<Alchemy_Table_Recipe> AlchemyTableRecipes = recipeManager.getAllRecipesFor(Alchemy_Table_Recipe.Type.INSTANCE);
        registration.addRecipes(Alchemy_Table_Category.ALCHEMY_TABLE_RECIPE_TYPE,AlchemyTableRecipes);

        List<Hermes_Workbench_Recipe> HermesWorkbenchRecipes = recipeManager.getAllRecipesFor(Hermes_Workbench_Recipe.Type.INSTANCE);
        registration.addRecipes(Hermes_Workbench_Category.HERMES_WORKBENCH_RECIPE_TYPE,HermesWorkbenchRecipes);

        List<Transcendental_Table_Recipe> TranscendentalTableRecipe = recipeManager.getAllRecipesFor(Transcendental_Table_Recipe.Type.INSTANCE);
        registration.addRecipes(Transcendental_Table_Category.TRANSCENDENTAL_TABLE_RECIPE_TYPE, TranscendentalTableRecipe);

        List<Alchemical_Engraver_Recipe> AlchemicalEngraverRecipe = recipeManager.getAllRecipesFor(Alchemical_Engraver_Recipe.Type.INSTANCE);
        registration.addRecipes(Alchemical_Engraver_Category.ALCHEMICAL_ENGRAVER_RECIPE_TYPE,AlchemicalEngraverRecipe );

        List<Rune_Activator_Recipe> RuneActivatorRecipe = recipeManager.getAllRecipesFor(Rune_Activator_Recipe.Type.INSTANCE);
        registration.addRecipes(Rune_Activator_Category.RUNE_ACTIVATOR_RECIPE_TYPE,RuneActivatorRecipe );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(Alchemy_Table_Screen.class,116,42,16,13,
                Alchemy_Table_Category.ALCHEMY_TABLE_RECIPE_TYPE);

        registration.addRecipeClickArea(HermesWorkbench_Screen.class,136,57,16,13,
                Hermes_Workbench_Category.HERMES_WORKBENCH_RECIPE_TYPE);

        registration.addRecipeClickArea(Transcendental_Table_Screen.class,290,64,58,18,
                Transcendental_Table_Category.TRANSCENDENTAL_TABLE_RECIPE_TYPE);

        registration.addRecipeClickArea(Alchemical_Engraver_Screen.class,93,40,16,13,
                Alchemical_Engraver_Category.ALCHEMICAL_ENGRAVER_RECIPE_TYPE);

        registration.addRecipeClickArea(Rune_Activator_Screen.class,4,4,Minecraft.getInstance().font.width("block.alchemical_power.rune_activator"),13,
                Rune_Activator_Category.RUNE_ACTIVATOR_RECIPE_TYPE);
    }
}
