package jp.nogami_rion.alchemical_power.recipe;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Alchemical_power.MODID);

    public static final RegistryObject<RecipeSerializer<Alchemy_Table_Recipe>> ALCHEMY_TABLE_SERIALIZER =
            SERIALIZERS.register("alchemy_table_recipe",() -> Alchemy_Table_Recipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
