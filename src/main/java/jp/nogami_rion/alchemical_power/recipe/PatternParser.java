package jp.nogami_rion.alchemical_power.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternParser {
    private PatternParser(){};

    public static Ingredient[][] parse(
            List<String> pattern,
            JsonObject keyJson
    ){
        int height = pattern.size();
        int width = pattern.get(0).length();

        Map<Character,Ingredient> key = new HashMap<>();

        for(Map.Entry<String, JsonElement> entry : keyJson.entrySet()){
            if(entry.getKey().length() != 1){
                throw new JsonSyntaxException("key must be single character:" + entry.getKey());
            }

            char symbol = entry.getKey().charAt(0);
            if(symbol == ' '){
                throw new JsonSyntaxException("key cannot be whitespace");
            }

            Ingredient ingredient = Ingredient.fromJson(entry.getValue());

            key.put(symbol,ingredient);
        }

        key.put(' ',Ingredient.EMPTY);

        Ingredient[][] matrix = new Ingredient[height][width];

        for(int y = 0; y < height; y++){
            String row = pattern.get(y);
            if(row.length() != width){
                throw new JsonSyntaxException("All pattern rows must have some length");
            }
            for(int x =0; x < width; x++){
                char c = row.charAt(x);
                Ingredient ing = key.get(c);
                if(ing == null){
                    throw new JsonSyntaxException("Pattern references undefined key '" + c +"'");
                }
                matrix[y][x] = ing;
            }
        }
        return matrix;
    }
}
