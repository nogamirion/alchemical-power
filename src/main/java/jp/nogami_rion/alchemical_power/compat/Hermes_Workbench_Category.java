package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.Hermes_Workbench_Recipe;
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
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class Hermes_Workbench_Category implements IRecipeCategory<Hermes_Workbench_Recipe> {
    public static final ResourceLocation UID = new ResourceLocation(Alchemical_power.MODID,"hermes_workbench_recipe");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID,
            "textures/gui/hermes_workbench_jei.png");

    public static final RecipeType<Hermes_Workbench_Recipe> HERMES_WORKBENCH_RECIPE_TYPE =
            new RecipeType<>(UID,Hermes_Workbench_Recipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public Hermes_Workbench_Category(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,192,103);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(blocklist.HERMES_WORKBENCH.get()));
    }

    @Override
    public RecipeType<Hermes_Workbench_Recipe> getRecipeType() {
        return HERMES_WORKBENCH_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.hermes_workbench");
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
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, Hermes_Workbench_Recipe hermesWorkbenchRecipe, IFocusGroup iFocusGroup) {

        for (int i = 0; i < 5; ++i) {
            for (int l = 0; l < 5; ++l) {
                iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,10 + l * 18, 7 + i * 18).addIngredients(hermesWorkbenchRecipe.getIngredients().get(l + i * 5));
            }
        }
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT,109,43).addIngredients(hermesWorkbenchRecipe.getIngredients().get(25));;

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,163,43).addItemStack(hermesWorkbenchRecipe.getResultItem(null));
    }
}
