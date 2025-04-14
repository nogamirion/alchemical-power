package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.Rune_Activator_Recipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Rune_Activator_Category implements IRecipeCategory<Rune_Activator_Recipe> {
    public static final ResourceLocation UID = new ResourceLocation(Alchemical_power.MODID,"rune_activator_recipe");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID,
            "textures/gui/rune_activator_jei.png");

    public static final RecipeType<Rune_Activator_Recipe> RUNE_ACTIVATOR_RECIPE_TYPE =
            new RecipeType<>(UID, Rune_Activator_Recipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public Rune_Activator_Category(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,147,33);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(blocklist.RUNE_ACTIVATOR.get()));
    }

    @Override
    public RecipeType<Rune_Activator_Recipe> getRecipeType() {
        return RUNE_ACTIVATOR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.rune_activator");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, Rune_Activator_Recipe runeActivatorRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,9,9).addIngredients(runeActivatorRecipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,123,9).addIngredients(runeActivatorRecipe.getIngredients().get(1));


        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,66,9).addItemStack(runeActivatorRecipe.getResultItem(null));
    }
}
