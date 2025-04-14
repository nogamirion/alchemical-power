package jp.nogami_rion.alchemical_power.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Alchemical_Engraver_Recipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public Alchemical_Engraver_Recipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }


    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        for (int i = 0 ; i < 3 ; i++){
            if( !(inputItems.get(i).test(simpleContainer.getItem(i)))){
                return false;
            }
        }

        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients(){
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }


    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<Alchemical_Engraver_Recipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemical_engraver_recipe";
    }

    public static class Serializer implements RecipeSerializer<Alchemical_Engraver_Recipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Alchemical_power.MODID, "alchemical_engraver_recipe");

        @Override
        public Alchemical_Engraver_Recipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(3, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new Alchemical_Engraver_Recipe(inputs, output, resourceLocation);
        }

        @Override
        public @Nullable Alchemical_Engraver_Recipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack output = friendlyByteBuf.readItem();
            return new Alchemical_Engraver_Recipe(inputs, output, resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, Alchemical_Engraver_Recipe alchemicalEngraverRecipe) {
            friendlyByteBuf.writeInt(alchemicalEngraverRecipe.inputItems.size());

            for (Ingredient ingredient : alchemicalEngraverRecipe.getIngredients()) {
                {
                    ingredient.toNetwork(friendlyByteBuf);
                }

                friendlyByteBuf.writeItemStack(alchemicalEngraverRecipe.getResultItem(null), false);

            }
        }
    }
}