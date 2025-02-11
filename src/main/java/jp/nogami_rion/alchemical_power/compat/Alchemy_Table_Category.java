package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.Alchemy_Table_Recipe;
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

public class Alchemy_Table_Category implements IRecipeCategory<Alchemy_Table_Recipe> {
    public static final ResourceLocation UID = new ResourceLocation(Alchemical_power.MODID,"alchemy_table_recipe");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID,
            "textures/gui/alchemy_table_jei.png");

    public static final RecipeType<Alchemy_Table_Recipe> ALCHEMY_TABLE_RECIPE_TYPE =
            new RecipeType<>(UID,Alchemy_Table_Recipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public Alchemy_Table_Category(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,160,61);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(blocklist.ALCHEMY_TABLE.get()));
    }

    @Override
    public RecipeType<Alchemy_Table_Recipe> getRecipeType() {
        return ALCHEMY_TABLE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.alchemy_table");
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
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, Alchemy_Table_Recipe alchemyTableRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,8,5).addIngredients(alchemyTableRecipe.getIngredients().get(0));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,5).addIngredients(alchemyTableRecipe.getIngredients().get(1));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,44,5).addIngredients(alchemyTableRecipe.getIngredients().get(2));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,8,23).addIngredients(alchemyTableRecipe.getIngredients().get(3));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,23).addIngredients(alchemyTableRecipe.getIngredients().get(4));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,44,23).addIngredients(alchemyTableRecipe.getIngredients().get(5));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,8,41).addIngredients(alchemyTableRecipe.getIngredients().get(6));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,26,41).addIngredients(alchemyTableRecipe.getIngredients().get(7));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,44,41).addIngredients(alchemyTableRecipe.getIngredients().get(8));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,80,23).addIngredients(alchemyTableRecipe.getIngredients().get(9));;

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,137,23).addItemStack(alchemyTableRecipe.getResultItem(null));
    }
}
