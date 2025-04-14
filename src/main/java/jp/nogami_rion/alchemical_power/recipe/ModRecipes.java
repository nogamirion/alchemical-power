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

    public static final RegistryObject<RecipeSerializer<Hermes_Workbench_Recipe>> HERMES_WORKBENCH_SERIALIZER =
            SERIALIZERS.register("hermes_workbench_recipe",() -> Hermes_Workbench_Recipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<Transcendental_Table_Recipe>> TRANSCENDENTAL_TABLE_SERIALIZER =
            SERIALIZERS.register("transcendental_table_recipe",() -> Transcendental_Table_Recipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<Alchemical_Engraver_Recipe>> ALCHEMICAL_ENGRAVER_SERIALIZER =
            SERIALIZERS.register("alchemical_engraver_recipe",() -> Alchemical_Engraver_Recipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<Rune_Activator_Recipe>> RUNE_ACTIVATOR_SERIALIZER =
            SERIALIZERS.register("rune_activator_recipe",() -> Rune_Activator_Recipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
