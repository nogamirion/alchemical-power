package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
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

public abstract class AlchemicalPowerTablesCategoryBase implements IRecipeCategory<AlchemicalPowerTablesRecipe>{
    protected final IDrawable background;
    protected final IDrawable icon;
    protected final int size;
    protected final int startX;
    protected final int startY;
    protected final int toolX;
    protected final int toolY;
    protected final int resultX;
    protected final int resultY;

    protected AlchemicalPowerTablesCategoryBase(
            IGuiHelper helper,
            ResourceLocation texture,
            ItemStack iconStack,
            int bgWidth,
            int bgHeight,
            int size,
            int startX, int startY,
            int toolX, int toolY,
            int resultX, int resultY
    ) {
        this.background = helper.createDrawable(texture, 0, 0, bgWidth, bgHeight);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,iconStack);
        this.size = size;
        this.startX = startX;
        this.startY = startY;
        this.toolX = toolX;
        this.toolY = toolY;
        this.resultX = resultX;
        this.resultY = resultY;
    }

    @Override
    public abstract RecipeType<AlchemicalPowerTablesRecipe> getRecipeType();

    @Override
    public abstract net.minecraft.network.chat.Component getTitle();

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,
                          AlchemicalPowerTablesRecipe recipe,
                          IFocusGroup focuses) {

        if(recipe.getMode() == AlchemicalPowerTablesRecipe.Mode.SHAPELESS){
            builder.setShapeless();
        }

        var ingredients = recipe.getIngredients();
        int slotSize = 18;

        for (int i = 0; i < size * size; i++) {

            int x = i % size;
            int y = i / size;

            builder.addSlot(RecipeIngredientRole.INPUT,
                            startX + x * slotSize,
                            startY + y * slotSize)
                    .addIngredients(ingredients.get(i));
        }

        builder.addSlot(RecipeIngredientRole.INPUT, toolX, toolY)
                .addIngredients(recipe.getTool());

        builder.addSlot(RecipeIngredientRole.OUTPUT, resultX, resultY)
                .addItemStack(recipe.getResultItem(null));
    }

}
