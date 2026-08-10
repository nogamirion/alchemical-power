package jp.nogami_rion.alchemical_power.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AlchemicalPowerTablesRecipeSerializer implements RecipeSerializer<AlchemicalPowerTablesRecipe> {

    @Override
    public AlchemicalPowerTablesRecipe fromJson(ResourceLocation id, JsonObject json) {

        //レシピモードチェック
        String mode = GsonHelper.getAsString(json,"mode");
        int gridSize = GsonHelper.getAsInt(json,"grid",3);
        if(gridSize < 3 || gridSize > 13){
            throw new JsonSyntaxException("grid_size must be between 3 and 13:" + gridSize);
        }
        //ツールチェック
        Ingredient tool = json.has("tool") ? Ingredient.fromJson(json.get("tool")):Ingredient.EMPTY;

        //成果物チェック
        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"result"));

        return switch(mode){
            case "shaped" -> readShaped(id,json,gridSize,tool,result);
            case "shapeless" -> readShapeless(id,json,gridSize,tool,result);
            default -> throw new JsonSyntaxException("Unknown mode:"+mode);
        };
    }

    @Override
    public @Nullable AlchemicalPowerTablesRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        AlchemicalPowerTablesRecipe.Mode mode = buf.readEnum(AlchemicalPowerTablesRecipe.Mode.class);
        int gridSize = buf.readInt();
        Ingredient tool = Ingredient.fromNetwork(buf);
        ItemStack result = buf.readItem();
//        int progressingTime = buf.readInt();

        if(mode == AlchemicalPowerTablesRecipe.Mode.SHAPED){
            int height = buf.readInt();
            int width = buf.readInt();
            Ingredient[][] matrix = new Ingredient[height][width];

            for(int y =0; y < height; y++){
                for(int x =0; x < width; x++){
                    matrix[y][x] = Ingredient.fromNetwork(buf);
                }
            }
            return AlchemicalPowerTablesRecipe.shaped(
                    id,gridSize,matrix,tool,result
            );

        } else {
            int size = buf.readInt();
            List<Ingredient> Ingredients = new ArrayList<>();

            for(int i =0; i < size; i++){
                Ingredients.add(Ingredient.fromNetwork(buf));
            }

            return AlchemicalPowerTablesRecipe.shapeless(
                    id,gridSize,Ingredients,tool,result
            );

        }

    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, AlchemicalPowerTablesRecipe recipe) {
        buf.writeEnum(recipe.getMode());
        buf.writeInt(recipe.getRequiredGridSize());
        recipe.getTool().toNetwork(buf);
        buf.writeItem(recipe.getResultItem(null));

        if (recipe.getMode() == AlchemicalPowerTablesRecipe.Mode.SHAPED) {
            Ingredient[][] pattern = recipe.getPattern();
            buf.writeInt(pattern.length);
            buf.writeInt(pattern[0].length);

            for (Ingredient[] row : pattern) {
                for (Ingredient ing : row) {
                    ing.toNetwork(buf);
                }
            }
        } else {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
        }
    }

    private AlchemicalPowerTablesRecipe readShaped(
            ResourceLocation id,
            JsonObject json,
            int gridSize,
            Ingredient tool,
            ItemStack result
    ){
        JsonArray jsonArray = GsonHelper.getAsJsonArray(json,"pattern");
        List<String> pattern = new ArrayList<>();

        for(JsonElement element : jsonArray){
            pattern.add(GsonHelper.convertToString(element,"pattern"));
        }

        JsonObject keyJson = GsonHelper.getAsJsonObject(json,"key");
        Ingredient[][] matrix = PatternParser.parse(pattern,keyJson);

        return AlchemicalPowerTablesRecipe.shaped(
                id,gridSize,matrix,tool,result
        );
    }
    private AlchemicalPowerTablesRecipe readShapeless(
            ResourceLocation id,
            JsonObject json,
            int gridSize,
            Ingredient tool,
            ItemStack result
    ){
        JsonArray jsonArray = GsonHelper.getAsJsonArray(json,"ingredients");
        List<Ingredient> ingredients = new ArrayList<>();

        for(JsonElement element : jsonArray){
            JsonObject obj = element.getAsJsonObject();
            Ingredient ing = Ingredient.fromJson(obj);
            int count = GsonHelper.getAsInt(obj, "count", 1);

            for (int i = 0; i < count; i++) {
                ingredients.add(ing);
            }
        }

        return AlchemicalPowerTablesRecipe.shapeless(
                id, gridSize, ingredients, tool, result
        );
    }
}
