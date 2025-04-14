package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.Alchemical_Engraver_Recipe;
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

public class Alchemical_Engraver_Category implements IRecipeCategory<Alchemical_Engraver_Recipe> {
    public static final ResourceLocation UID = new ResourceLocation(Alchemical_power.MODID,"alchemical_engraver_recipe");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID,
            "textures/gui/alchemical_engraver_jei.png");

    public static final RecipeType<Alchemical_Engraver_Recipe> ALCHEMICAL_ENGRAVER_RECIPE_TYPE =
            new RecipeType<>(UID,Alchemical_Engraver_Recipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public Alchemical_Engraver_Category(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,115,61);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(blocklist.ALCHEMICAL_ENGRAVER.get()));
    }

    @Override
    public RecipeType<Alchemical_Engraver_Recipe> getRecipeType() {
        return ALCHEMICAL_ENGRAVER_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.alchemical_engraver");
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
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, Alchemical_Engraver_Recipe alchemicalEngraverRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,37,12).addIngredients(alchemicalEngraverRecipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,37,43).addIngredients(alchemicalEngraverRecipe.getIngredients().get(1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,9,4).addIngredients(alchemicalEngraverRecipe.getIngredients().get(2));

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,91,27).addItemStack(alchemicalEngraverRecipe.getResultItem(null));
    }
}
