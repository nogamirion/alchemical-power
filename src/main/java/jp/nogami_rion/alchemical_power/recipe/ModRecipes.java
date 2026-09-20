package jp.nogami_rion.alchemical_power.recipe;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Alchemical_power.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Alchemical_power.MODID);

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

    public static final RegistryObject<RecipeType<AlchemicalPowerTablesRecipe>> ALCHEMICAL_POWER_TABLES_TYPE =
            TYPES.register("alchemical_power_tables", () -> new RecipeType<>() {
                        @Override
                        public String toString() {
                            return "alchemical_power_tables";
                        }}
            );

    public static final RegistryObject<RecipeSerializer<AlchemicalPowerTablesRecipe>> ALCHEMICAL_POWER_TABLES_SERIALIZER =
            SERIALIZERS.register("alchemical_power_tables_recipe",AlchemicalPowerTablesRecipeSerializer::new);


    public static final RegistryObject<RecipeType<AlchemicalReactorRecipe>> ALCHEMICAL_REACTOR_TYPE =
            TYPES.register("alchemical_reactor", () -> new RecipeType<>() {
                @Override public String toString() { return "alchemical_power:alchemical_reactor"; }
            });
    public static final RegistryObject<RecipeSerializer<AlchemicalReactorRecipe>> ALCHEMICAL_REACTOR_SERIALIZER =
            SERIALIZERS.register("alchemical_reactor", AlchemicalReactorRecipe.Serializer::new);

    public static final RegistryObject<RecipeSerializer<net.minecraft.world.item.crafting.SmeltingRecipe>> TAG_OUTPUT_SMELTING =
            SERIALIZERS.register("tag_output_smelting", () -> new TagOutputRecipeSerializer<>(RecipeSerializer.SMELTING_RECIPE, true));
    public static final RegistryObject<RecipeSerializer<net.minecraft.world.item.crafting.BlastingRecipe>> TAG_OUTPUT_BLASTING =
            SERIALIZERS.register("tag_output_blasting", () -> new TagOutputRecipeSerializer<>(RecipeSerializer.BLASTING_RECIPE, true));
    public static final RegistryObject<RecipeSerializer<net.minecraft.world.item.crafting.StonecutterRecipe>> TAG_OUTPUT_STONECUTTING =
            SERIALIZERS.register("tag_output_stonecutting", () -> new TagOutputRecipeSerializer<>(RecipeSerializer.STONECUTTER, false));

    public static void register(IEventBus eventBus){
        TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }
}
