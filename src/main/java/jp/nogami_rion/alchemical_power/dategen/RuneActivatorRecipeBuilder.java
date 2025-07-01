package jp.nogami_rion.alchemical_power.dategen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.nogami_rion.alchemical_power.recipe.Rune_Activator_Recipe;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RuneActivatorRecipeBuilder {
    private final Ingredient input1;
    private final Ingredient input2;
    private final Item output;
    private final int count;
    private final ResourceLocation id;

    public RuneActivatorRecipeBuilder(ResourceLocation id, Ingredient input1, Ingredient input2, Item output, int count) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.count = count;
    }

    public void save(Consumer<FinishedRecipe> consumer, @Nullable String customId) {
        ResourceLocation recipeId = new ResourceLocation("alchemical_power",customId);

        consumer.accept(new FinishedRecipe() {
            @Override
            public void serializeRecipeData(@NotNull JsonObject json) {
                JsonArray ingredients = new JsonArray();
                ingredients.add(input1.toJson());
                ingredients.add(input2.toJson());
                json.add("ingredients", ingredients);

                JsonObject result = new JsonObject();
                result.addProperty("item", net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(output).toString());
                result.addProperty("count", count);
                json.add("output", result);
            }

            @Override
            public @NotNull ResourceLocation getId() {
                return recipeId;
            }

            @Override
            public @NotNull RecipeSerializer<?> getType() {
                return Rune_Activator_Recipe.Serializer.INSTANCE;
            }

            @Override
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public ResourceLocation getAdvancementId() {
                return null;
            }
        });
    }
}