package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.Transcendental_Table_Recipe;
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

public class Transcendental_Table_Category implements IRecipeCategory<Transcendental_Table_Recipe> {
    public static final ResourceLocation UID = new ResourceLocation(Alchemical_power.MODID,"transcendental_table_recipe");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID,
            "textures/gui/transcendental_table_jei.png");

    public static final RecipeType<Transcendental_Table_Recipe> TRANSCENDENTAL_TABLE_RECIPE_TYPE =
            new RecipeType<>(UID,Transcendental_Table_Recipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public Transcendental_Table_Category(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,247,256);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(blocklist.TRANSCENDENTAL_TABLE.get()));
    }

    @Override
    public RecipeType<Transcendental_Table_Recipe> getRecipeType() {
        return TRANSCENDENTAL_TABLE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.transcendental_table");
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
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, Transcendental_Table_Recipe transcendentalTableRecipe, IFocusGroup iFocusGroup) {

        for (int i = 0; i < 13; ++i) {
            for (int l = 0; l < 13; ++l) {
                iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,7 + l * 18, 4 + i * 18).addIngredients(transcendentalTableRecipe.getIngredients().get(l + i * 13));
            }
        }
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,88,238).addIngredients(transcendentalTableRecipe.getIngredients().get(169));;

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,142,238).addItemStack(transcendentalTableRecipe.getResultItem(null));
    }
}
