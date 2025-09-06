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

public class Hermes_Workbench_Recipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public Hermes_Workbench_Recipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }


    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        for (int i = 0 ; i < 26 ; i++){
            if( !(inputItems.get(i).test(simpleContainer.getItem(i)))){
                return false;
            }
        }

        return true;
//        return inputItems.get(0).test(simpleContainer.getItem(0))
//                && inputItems.get(1).test(simpleContainer.getItem(1))
//                && inputItems.get(2).test(simpleContainer.getItem(2))
//                && inputItems.get(3).test(simpleContainer.getItem(3))
//                && inputItems.get(4).test(simpleContainer.getItem(4))
//                && inputItems.get(5).test(simpleContainer.getItem(5))
//                && inputItems.get(6).test(simpleContainer.getItem(6))
//                && inputItems.get(7).test(simpleContainer.getItem(7))
//                && inputItems.get(8).test(simpleContainer.getItem(8))
//                && inputItems.get(9).test(simpleContainer.getItem(9))
//                && inputItems.get(10).test(simpleContainer.getItem(10))
//                && inputItems.get(11).test(simpleContainer.getItem(11))
//                && inputItems.get(12).test(simpleContainer.getItem(12))
//                && inputItems.get(13).test(simpleContainer.getItem(13))
//                && inputItems.get(14).test(simpleContainer.getItem(14))
//                && inputItems.get(15).test(simpleContainer.getItem(15))
//                && inputItems.get(16).test(simpleContainer.getItem(16))
//                && inputItems.get(17).test(simpleContainer.getItem(17))
//                && inputItems.get(18).test(simpleContainer.getItem(18))
//                && inputItems.get(19).test(simpleContainer.getItem(19))
//                && inputItems.get(20).test(simpleContainer.getItem(20))
//                && inputItems.get(21).test(simpleContainer.getItem(21))
//                && inputItems.get(22).test(simpleContainer.getItem(22))
//                && inputItems.get(23).test(simpleContainer.getItem(23))
//                && inputItems.get(24).test(simpleContainer.getItem(24))
//                && inputItems.get(25).test(simpleContainer.getItem(25))
//
//                ;
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

    public static class Type implements RecipeType<Hermes_Workbench_Recipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "hermes_workbench_recipe";
    }

    public static class Serializer implements RecipeSerializer<Hermes_Workbench_Recipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Alchemical_power.MODID, "hermes_workbench_recipe");

        @Override
        public Hermes_Workbench_Recipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(26, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new Hermes_Workbench_Recipe(inputs, output, resourceLocation);
        }

        @Override
        public @Nullable Hermes_Workbench_Recipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack output = friendlyByteBuf.readItem();
            return new Hermes_Workbench_Recipe(inputs, output, resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, Hermes_Workbench_Recipe hermesWorkbenchRecipe) {
            friendlyByteBuf.writeInt(hermesWorkbenchRecipe.inputItems.size());

            for (Ingredient ingredient : hermesWorkbenchRecipe.getIngredients()) {
                {
                    ingredient.toNetwork(friendlyByteBuf);
                }
            }
            friendlyByteBuf.writeItemStack(hermesWorkbenchRecipe.getResultItem(null), false);
        }
    }
}