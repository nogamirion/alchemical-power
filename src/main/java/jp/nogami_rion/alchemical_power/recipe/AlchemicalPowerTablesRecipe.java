package jp.nogami_rion.alchemical_power.recipe;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.container.AlchemicalPowerTablesContainerView;
import jp.nogami_rion.alchemical_power.grid.AlchemicalTableGrid;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AlchemicalPowerTablesRecipe implements Recipe<Container> {

    private final ResourceLocation id;

    private final Mode mode;
    private final int requiredGridSize;
    private final Ingredient tool;
    private final ItemStack result;

    private final Ingredient[][] pattern;
    private final List<Ingredient> ingredients;

    private AlchemicalPowerTablesRecipe(
            ResourceLocation id,
            Mode mode,
            int requiredGridSize,
            Ingredient tool,
            ItemStack result,
            Ingredient[][] pattern,
            List<Ingredient> ingredients
    ) {
        this.id = id;
        this.mode = mode;
        this.requiredGridSize = requiredGridSize;
        this.tool = tool;
        this.result = result;
        this.pattern = pattern;
        this.ingredients = ingredients;
//        this.ingredients = NonNullList.create();
//        this.ingredients.addAll(ingredients);
    }


    public enum Mode{
        SHAPED,
        SHAPELESS
    }

    public static AlchemicalPowerTablesRecipe shaped(
            ResourceLocation id,
            int grid,
            Ingredient[][] pattern,
            Ingredient tool,
            ItemStack result
    ){
        return new AlchemicalPowerTablesRecipe(
                id,
                Mode.SHAPED,
                grid,
                tool,
                result,
                pattern,
                null
        );
    }

    public static AlchemicalPowerTablesRecipe shapeless(
            ResourceLocation id,
            int grid,
            List<Ingredient> ingredients,
            Ingredient tool,
            ItemStack result
    ){
        return new AlchemicalPowerTablesRecipe(
                id,
                Mode.SHAPELESS,
                grid,
                tool,
                result,
                null,
                ingredients
        );
    }

    @Override
    public boolean matches(Container container, Level level){
        if(!(container instanceof AlchemicalPowerTablesContainerView view)){
            return false;
        }

        if(view.getGrid().getGridSize() < requiredGridSize){
            return false;
        }

        AbstractAlchemicalTableBlockEntity be = view.getBlockEntity();

        if(!tool.test(be.getTool())){
            return false;
        }

        AlchemicalTableGrid grid = view.getGrid();

        return switch(mode){
            case SHAPED -> matchesShaped(grid);
            case SHAPELESS -> matchesShapeless(grid);
        };
    }

    @Override
    public @NotNull ItemStack assemble(Container grid, RegistryAccess registryAccess) {
        return result.copy();
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }


    private boolean matchesShaped(AlchemicalTableGrid grid){
        int patternHeight = pattern.length;
        int patternWidth = pattern[0].length;

        for (int y = 0; y <= grid.getGridSize() - patternHeight; y++) {
            for (int x = 0; x <= grid.getGridSize() - patternWidth; x++) {
                if (matchesPatternAt(grid, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matchesShapeless(AlchemicalTableGrid grid){
        List<Ingredient> remaining = new ArrayList<>(ingredients);

        for (int i = 0; i < grid.getGridSize() * grid.getGridSize(); i++) {
            ItemStack stack = grid.getItem(i);
            if(stack.isEmpty()){
                continue;
            }
            boolean matched = false;

            Iterator<Ingredient> it = remaining.iterator();
            while (it.hasNext()) {
                Ingredient ing = it.next();
                if (ing.test(stack)) {
                    matched = true;
                    it.remove();
                    break;
                }
            }

            if (!matched) {
                return false;
            }
        }

        return remaining.isEmpty();
    }

    private boolean matchesPatternAt(
            AlchemicalTableGrid grid,
            int offsetX,
            int offsetY
    ) {
        for (int y = 0; y < pattern.length; y++) {
            for (int x = 0; x < pattern[y].length; x++) {
                Ingredient ingredient = pattern[y][x];
                ItemStack stack = grid.getItem(offsetX + x, offsetY + y);

                if (!ingredient.test(stack)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess access){
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALCHEMICAL_POWER_TABLES_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.ALCHEMICAL_POWER_TABLES_TYPE.get();
    }

    public int getRequiredGridSize(){
        return requiredGridSize;
    }

    public Mode getMode(){
        return mode;
    }

    public Ingredient getTool(){
        return tool;
    }

    public ItemStack getResult(){
        return result;
    }

    public Ingredient[][] getPattern() {
        return pattern;
    }

    @Override
    public NonNullList<Ingredient> getIngredients(){
        int gridSize = requiredGridSize; // 例: 3 や 5
        int totalGridSlots = gridSize * gridSize;

        // +1 は tool スロット
        NonNullList<Ingredient> list =
                NonNullList.withSize(totalGridSlots + 1, Ingredient.EMPTY);

        // pattern を flatten
        if (mode == Mode.SHAPED && pattern != null) {
            for (int y = 0; y < pattern.length; y++) {
                for (int x = 0; x < pattern[y].length; x++) {
                    int index = y * gridSize + x;
                    if (index < totalGridSlots) {
                        Ingredient ing = pattern[y][x];
                        list.set(index, ing == null ? Ingredient.EMPTY : ing);
                    }
                }
            }
        }

        if (mode == Mode.SHAPELESS && ingredients != null) {
            for (int i = 0; i < ingredients.size(); i++) {
                list.set(i, ingredients.get(i));
            }
        }

        // tool は最後に入れる
        if (tool != null) {
            list.set(totalGridSlots, tool);
        }

        return list;
    }

}


