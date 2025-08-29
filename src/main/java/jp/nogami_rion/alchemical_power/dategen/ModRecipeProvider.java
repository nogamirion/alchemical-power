package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ALCHEMY_DUST_STONE = List.of(
            blocklist.PANAKEIA_BEARING_STONE.get());
    private static final List<ItemLike> DOUBLING_COPPER = List.of(itemlist.CRUSHED_RAW_COPPER.get(),itemlist.GRANULATED_COPPER.get(),itemlist.SOFTENED_COPPER.get(),itemlist.PURIFIED_COPPER.get());
    private static final List<ItemLike> DOUBLING_IRON = List.of(itemlist.CRUSHED_RAW_IRON.get(),itemlist.GRANULATED_IRON.get(),itemlist.SOFTENED_IRON.get(),itemlist.PURIFIED_IRON.get());
    private static final List<ItemLike> DOUBLING_GOLD = List.of(itemlist.CRUSHED_RAW_GOLD.get(),itemlist.GRANULATED_GOLD.get(),itemlist.SOFTENED_GOLD.get(),itemlist.PURIFIED_GOLD.get());
    private static final List<ItemLike> DOUBLING_DIAMOND = List.of(itemlist.CRUSHED_DIAMOND_ORE.get(),itemlist.GRANULATED_DIAMOND.get(),itemlist.SOFTENED_DIAMOND.get(),itemlist.PURIFIED_DIAMOND.get());
    private static final List<ItemLike> DOUBLING_EMERALD = List.of(itemlist.CRUSHED_EMERALD_ORE.get(),itemlist.GRANULATED_EMERALD.get(),itemlist.SOFTENED_EMERALD.get(),itemlist.PURIFIED_EMERALD.get());
    private static final List<ItemLike> DOUBLING_ANCIENT_DEBRIS = List.of(itemlist.CRUSHED_ANCIENT_DEBRIS.get(),itemlist.GRANULATED_ANCIENT_DEBRIS.get(),itemlist.SOFTENED_ANCIENT_DEBRIS.get(),itemlist.PURIFIED_ANCIENT_DEBRIS.get());

    private static final List<ItemLike> COLLECTED_DOUBLING_COPPER = List.of(itemlist.COLLECTED_CRUSHED_RAW_COPPER.get(),itemlist.COLLECTED_GRANULATED_COPPER.get(),itemlist.COLLECTED_SOFTENED_COPPER.get(),itemlist.COLLECTED_PURIFIED_COPPER.get());
    private static final List<ItemLike> COLLECTED_DOUBLING_IRON = List.of(itemlist.COLLECTED_CRUSHED_RAW_IRON.get(),itemlist.COLLECTED_GRANULATED_IRON.get(),itemlist.COLLECTED_SOFTENED_IRON.get(),itemlist.COLLECTED_PURIFIED_IRON.get());
    private static final List<ItemLike> COLLECTED_DOUBLING_GOLD = List.of(itemlist.COLLECTED_CRUSHED_RAW_GOLD.get(),itemlist.COLLECTED_GRANULATED_GOLD.get(),itemlist.COLLECTED_SOFTENED_GOLD.get(),itemlist.COLLECTED_PURIFIED_GOLD.get());
    private static final List<ItemLike> COLLECTED_DOUBLING_DIAMOND = List.of(itemlist.COLLECTED_CRUSHED_DIAMOND_ORE.get(),itemlist.COLLECTED_GRANULATED_DIAMOND.get(),itemlist.COLLECTED_SOFTENED_DIAMOND.get(),itemlist.COLLECTED_PURIFIED_DIAMOND.get());
    private static final List<ItemLike> COLLECTED_DOUBLING_EMERALD = List.of(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get(),itemlist.COLLECTED_GRANULATED_EMERALD.get(),itemlist.COLLECTED_SOFTENED_EMERALD.get(),itemlist.COLLECTED_PURIFIED_EMERALD.get());
    private static final List<ItemLike> COLLECTED_DOUBLING_ANCIENT_DEBRIS = List.of(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get(),itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),itemlist.COLLECTED_SOFTENED_ANCIENT_DEBRIS.get(),itemlist.COLLECTED_PURIFIED_ANCIENT_DEBRIS.get());

    private static final List<ItemLike> BAKING_PIZZA = List.of(itemlist.PIZZA_BEFORE_BAKING.get());
    private static final List<ItemLike> BAKING_CALZONE = List.of(itemlist.CALZONE_BEFORE_BAKING.get());
    private static final List<ItemLike> COAL_ORES = List.of(Items.COAL_ORE, Items.DEEPSLATE_COAL_ORE);



    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        oreSmelting(consumer,ALCHEMY_DUST_STONE,RecipeCategory.MISC,itemlist.T0_PANAKEIA.get(),0.25f,200,"t0_panakeia");
        oreBlasting(consumer,ALCHEMY_DUST_STONE,RecipeCategory.MISC,itemlist.T0_PANAKEIA.get(),0.25f,100,"t0_panakeia");
        oreSmelting(consumer,DOUBLING_COPPER,RecipeCategory.MISC,Items.COPPER_INGOT,0.25f,200,"copper_ingot");
        oreBlasting(consumer,DOUBLING_COPPER,RecipeCategory.MISC,Items.COPPER_INGOT,0.25f,100,"copper_ingot");
        oreSmelting(consumer, DOUBLING_IRON,RecipeCategory.MISC,Items.IRON_INGOT,0.25f,200,"iron_ingot");
        oreBlasting(consumer, DOUBLING_IRON,RecipeCategory.MISC,Items.IRON_INGOT,0.25f,100,"iron_ingot");
        oreSmelting(consumer, DOUBLING_GOLD,RecipeCategory.MISC,Items.GOLD_INGOT,0.25f,200,"gold_ingot");
        oreBlasting(consumer, DOUBLING_GOLD,RecipeCategory.MISC,Items.GOLD_INGOT,0.25f,100,"gold_ingot");
        oreSmelting(consumer, DOUBLING_ANCIENT_DEBRIS,RecipeCategory.MISC,Items.NETHERITE_SCRAP,0.25f,200,"netherite_scrap");
        oreBlasting(consumer, DOUBLING_ANCIENT_DEBRIS,RecipeCategory.MISC,Items.NETHERITE_SCRAP,0.25f,100,"netherite_scrap");


        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.CRUSHED_DIAMOND_ORE.get()),RecipeCategory.MISC,Items.DIAMOND)
                .unlockedBy(getHasName(itemlist.CRUSHED_DIAMOND_ORE.get()),has(itemlist.CRUSHED_DIAMOND_ORE.get()))
                .save(consumer,"stonecutting_diamond_from_crushed_diamond");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.GRANULATED_DIAMOND.get()),RecipeCategory.MISC,Items.DIAMOND)
                .unlockedBy(getHasName(itemlist.GRANULATED_DIAMOND.get()),has(itemlist.GRANULATED_DIAMOND.get()))
                .save(consumer,"stonecutting_diamond_from_granulated_diamond");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.SOFTENED_DIAMOND.get()),RecipeCategory.MISC,Items.DIAMOND)
                .unlockedBy(getHasName(itemlist.SOFTENED_DIAMOND.get()),has(itemlist.SOFTENED_DIAMOND.get()))
                .save(consumer,"stonecutting_diamond_from_softened_diamond");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.PURIFIED_DIAMOND.get()),RecipeCategory.MISC,Items.DIAMOND)
                .unlockedBy(getHasName(itemlist.PURIFIED_DIAMOND.get()),has(itemlist.PURIFIED_DIAMOND.get()))
                .save(consumer,"stonecutting_diamond_from_purified_diamond");

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.CRUSHED_EMERALD_ORE.get()),RecipeCategory.MISC,Items.EMERALD)
                .unlockedBy(getHasName(itemlist.CRUSHED_EMERALD_ORE.get()),has(itemlist.CRUSHED_EMERALD_ORE.get()))
                .save(consumer,"stonecutting_emerald_from_crushed_emerald");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.GRANULATED_EMERALD.get()),RecipeCategory.MISC,Items.EMERALD)
                .unlockedBy(getHasName(itemlist.GRANULATED_EMERALD.get()),has(itemlist.GRANULATED_EMERALD.get()))
                .save(consumer,"stonecutting_emerald_from_granulated_emerald");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.SOFTENED_EMERALD.get()),RecipeCategory.MISC,Items.EMERALD)
                .unlockedBy(getHasName(itemlist.SOFTENED_EMERALD.get()),has(itemlist.SOFTENED_EMERALD.get()))
                .save(consumer,"stonecutting_emerald_from_softened_emerald");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(itemlist.PURIFIED_EMERALD.get()),RecipeCategory.MISC,Items.EMERALD)
                .unlockedBy(getHasName(itemlist.PURIFIED_EMERALD.get()),has(itemlist.PURIFIED_EMERALD.get()))
                .save(consumer,"stonecutting_emerald_from_purified_emerald");







        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.ALCHEMY_BEGINNERS_KIT.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aaa")
                .define('a',Items.GLASS.asItem())
                .define('b',ItemTags.WOODEN_BUTTONS)
                .define('c',itemlist.T1_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA.get()),has(itemlist.T1_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.T1_PANAKEIA_INGOT_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.T2_PANAKEIA_INGOT_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.T3_PANAKEIA_INGOT_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.T4_PANAKEIA_GEM_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.T5_PANAKEIA_GEM_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.T6_PANAKEIA_INGOT_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.UNITE_ALLOY_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.UNITE_ALLOY.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T1_PANAKEIA_INGOT.get(),9)
                .requires(blocklist.T1_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(blocklist.T1_PANAKEIA_INGOT_BLOCK.get()),has(blocklist.T1_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T2_PANAKEIA_INGOT.get(),9)
                .requires(blocklist.T2_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(blocklist.T2_PANAKEIA_INGOT_BLOCK.get()),has(blocklist.T2_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T3_PANAKEIA_INGOT.get(),9)
                .requires(blocklist.T3_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(blocklist.T3_PANAKEIA_INGOT_BLOCK.get()),has(blocklist.T3_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T4_PANAKEIA_GEM.get(),9)
                .requires(blocklist.T4_PANAKEIA_GEM_BLOCK.get())
                .unlockedBy(getHasName(blocklist.T4_PANAKEIA_GEM_BLOCK.get()),has(blocklist.T4_PANAKEIA_GEM_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T5_PANAKEIA_GEM.get(),9)
                .requires(blocklist.T5_PANAKEIA_GEM_BLOCK.get())
                .unlockedBy(getHasName(blocklist.T5_PANAKEIA_GEM_BLOCK.get()),has(blocklist.T5_PANAKEIA_GEM_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T6_PANAKEIA_INGOT.get(),9)
                .requires(blocklist.T6_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(blocklist.T6_PANAKEIA_INGOT_BLOCK.get()),has(blocklist.T6_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.UNITE_ALLOY.get(),9)
                .requires(blocklist.UNITE_ALLOY_BLOCK.get())
                .unlockedBy(getHasName(blocklist.UNITE_ALLOY_BLOCK.get()),has(blocklist.UNITE_ALLOY_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND)
                .pattern("aa")
                .pattern("aa")
                .define('a',itemlist.CRUSHED_DIAMOND_ORE.get())
                .unlockedBy(getHasName(itemlist.CRUSHED_DIAMOND_ORE.get()),has(itemlist.CRUSHED_DIAMOND_ORE.get()))
                .save(consumer,"craft_diamond_ore_from_crushed_ore");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.EMERALD)
                .pattern("aa")
                .pattern("aa")
                .define('a',itemlist.CRUSHED_EMERALD_ORE.get())
                .unlockedBy(getHasName(itemlist.CRUSHED_EMERALD_ORE.get()),has(itemlist.CRUSHED_EMERALD_ORE.get()))
                .save(consumer,"craft_emerald_ore_from_crushed_ore");

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, blocklist.ALCHEMY_TABLE.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("ada")
                .define('a',ItemTags.LOGS)
                .define('b',itemlist.T1_PANAKEIA.get())
                .define('c',itemlist.ALCHEMY_BEGINNERS_KIT.get())
                .define('d',Items.CRAFTING_TABLE)
                .unlockedBy(getHasName(itemlist.ALCHEMY_BEGINNERS_KIT.get()),has(itemlist.ALCHEMY_BEGINNERS_KIT.get()))
                .save(consumer);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blocklist.T1_COMBUSTION_RUNE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T1_COMBUSTION_RUNE.get())
                .unlockedBy(getHasName(itemlist.T1_COMBUSTION_RUNE.get()),has(itemlist.T1_COMBUSTION_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blocklist.T2_COMBUSTION_RUNE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T2_COMBUSTION_RUNE.get())
                .unlockedBy(getHasName(itemlist.T2_COMBUSTION_RUNE.get()),has(itemlist.T2_COMBUSTION_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blocklist.T3_COMBUSTION_RUNE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T3_COMBUSTION_RUNE.get())
                .unlockedBy(getHasName(itemlist.T3_COMBUSTION_RUNE.get()),has(itemlist.T3_COMBUSTION_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blocklist.T4_COMBUSTION_RUNE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T4_COMBUSTION_RUNE.get())
                .unlockedBy(getHasName(itemlist.T4_COMBUSTION_RUNE.get()),has(itemlist.T4_COMBUSTION_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blocklist.T5_COMBUSTION_RUNE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T5_COMBUSTION_RUNE.get())
                .unlockedBy(getHasName(itemlist.T5_COMBUSTION_RUNE.get()),has(itemlist.T5_COMBUSTION_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, blocklist.T6_COMBUSTION_RUNE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T6_COMBUSTION_RUNE.get())
                .unlockedBy(getHasName(itemlist.T6_COMBUSTION_RUNE.get()),has(itemlist.T6_COMBUSTION_RUNE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T0_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T0.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T0.get()),has(blocklist.PANAKEIA_REED_T0.get()))
                .save(consumer,"t0_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T1_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T1.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T1.get()),has(blocklist.PANAKEIA_REED_T1.get()))
                .save(consumer,"t1_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T2_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T2.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T2.get()),has(blocklist.PANAKEIA_REED_T2.get()))
                .save(consumer,"t2_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T3_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T3.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T3.get()),has(blocklist.PANAKEIA_REED_T3.get()))
                .save(consumer,"t3_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T4_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T4.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T4.get()),has(blocklist.PANAKEIA_REED_T4.get()))
                .save(consumer,"t4_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T5_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T5.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T5.get()),has(blocklist.PANAKEIA_REED_T5.get()))
                .save(consumer,"t5_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T6_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T6.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T6.get()),has(blocklist.PANAKEIA_REED_T6.get()))
                .save(consumer,"t6_panakeia_from_panakeia_reed");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T7_PANAKEIA.get(),1)
                .requires(blocklist.PANAKEIA_REED_T7.get())
                .unlockedBy(getHasName(blocklist.PANAKEIA_REED_T7.get()),has(blocklist.PANAKEIA_REED_T7.get()))
                .save(consumer,"t7_panakeia_from_panakeia_reed");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T1_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T1_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA.get()),has(itemlist.T1_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T2_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T2_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA.get()),has(itemlist.T2_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T3_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T3_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA.get()),has(itemlist.T3_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T4_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T4_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA.get()),has(itemlist.T4_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T5_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T5_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA.get()),has(itemlist.T5_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T6_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T6_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA.get()),has(itemlist.T6_PANAKEIA.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T7_PANAKEIA_CUBE.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T7_PANAKEIA.get())
                .unlockedBy(getHasName(itemlist.T7_PANAKEIA.get()),has(itemlist.T7_PANAKEIA.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T1_PANAKEIA.get(),9)
                .requires(itemlist.T1_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_CUBE.get()),has(itemlist.T1_PANAKEIA_CUBE.get()))
                .save(consumer,"t1_panakeia_from_cube");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T2_PANAKEIA.get(),9)
                .requires(itemlist.T2_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_CUBE.get()),has(itemlist.T2_PANAKEIA_CUBE.get()))
                .save(consumer,"t2_panakeia_from_cube");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T3_PANAKEIA.get(),9)
                .requires(itemlist.T3_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_CUBE.get()),has(itemlist.T3_PANAKEIA_CUBE.get()))
                .save(consumer,"t3_panakeia_from_cube");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T4_PANAKEIA.get(),9)
                .requires(itemlist.T4_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_CUBE.get()),has(itemlist.T4_PANAKEIA_CUBE.get()))
                .save(consumer,"t4_panakeia_from_cube");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T5_PANAKEIA.get(),9)
                .requires(itemlist.T5_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_CUBE.get()),has(itemlist.T5_PANAKEIA_CUBE.get()))
                .save(consumer,"t5_panakeia_from_cube");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T6_PANAKEIA.get(),9)
                .requires(itemlist.T6_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_CUBE.get()),has(itemlist.T6_PANAKEIA_CUBE.get()))
                .save(consumer,"t6_panakeia_from_cube");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T7_PANAKEIA.get(),9)
                .requires(itemlist.T7_PANAKEIA_CUBE.get())
                .unlockedBy(getHasName(itemlist.T7_PANAKEIA_CUBE.get()),has(itemlist.T7_PANAKEIA_CUBE.get()))
                .save(consumer,"t7_panakeia_from_cube");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T1_PANAKEIA_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T2_PANAKEIA_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T3_PANAKEIA_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T4_PANAKEIA_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T5_PANAKEIA_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T6_PANAKEIA_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T1_PANAKEIA_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T2_PANAKEIA_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T3_PANAKEIA_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T4_PANAKEIA_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T5_PANAKEIA_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T6_PANAKEIA_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T1_PANAKEIA_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T2_PANAKEIA_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T3_PANAKEIA_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T4_PANAKEIA_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T5_PANAKEIA_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T6_PANAKEIA_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T1_PANAKEIA_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T2_PANAKEIA_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T3_PANAKEIA_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T4_PANAKEIA_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T5_PANAKEIA_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T6_PANAKEIA_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T1_PANAKEIA_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T2_PANAKEIA_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T3_PANAKEIA_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T4_PANAKEIA_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T5_PANAKEIA_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.T6_PANAKEIA_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T1_PANAKEIA_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T2_PANAKEIA_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T3_PANAKEIA_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T4_PANAKEIA_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T5_PANAKEIA_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T6_PANAKEIA_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T1_PANAKEIA_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T2_PANAKEIA_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T3_PANAKEIA_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T4_PANAKEIA_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T5_PANAKEIA_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T6_PANAKEIA_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T1_PANAKEIA_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T2_PANAKEIA_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T3_PANAKEIA_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T4_PANAKEIA_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T5_PANAKEIA_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T6_PANAKEIA_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T1_PANAKEIA_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T1_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT.get()),has(itemlist.T1_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T2_PANAKEIA_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T2_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT.get()),has(itemlist.T2_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T3_PANAKEIA_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T3_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT.get()),has(itemlist.T3_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T4_PANAKEIA_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T4_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM.get()),has(itemlist.T4_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T5_PANAKEIA_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T5_PANAKEIA_GEM.get())
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM.get()),has(itemlist.T5_PANAKEIA_GEM.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.T6_PANAKEIA_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.T6_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT.get()),has(itemlist.T6_PANAKEIA_INGOT.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_CRUSHED_RAW_COPPER.get(),1)
                .requires(itemlist.CRUSHED_RAW_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.CRUSHED_RAW_COPPER.get()),has(itemlist.CRUSHED_RAW_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_CRUSHED_RAW_IRON.get(),1)
                .requires(itemlist.CRUSHED_RAW_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.CRUSHED_RAW_IRON.get()),has(itemlist.CRUSHED_RAW_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_CRUSHED_RAW_GOLD.get(),1)
                .requires(itemlist.CRUSHED_RAW_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.CRUSHED_RAW_GOLD.get()),has(itemlist.CRUSHED_RAW_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_CRUSHED_DIAMOND_ORE.get(),1)
                .requires(itemlist.CRUSHED_DIAMOND_ORE.get(),8)
                .unlockedBy(getHasName(itemlist.CRUSHED_DIAMOND_ORE.get()),has(itemlist.CRUSHED_DIAMOND_ORE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get(),1)
                .requires(itemlist.CRUSHED_EMERALD_ORE.get(),8)
                .unlockedBy(getHasName(itemlist.CRUSHED_EMERALD_ORE.get()),has(itemlist.CRUSHED_EMERALD_ORE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.CRUSHED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.CRUSHED_ANCIENT_DEBRIS.get()),has(itemlist.CRUSHED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_GRANULATED_COPPER.get(),1)
                .requires(itemlist.GRANULATED_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.GRANULATED_COPPER.get()),has(itemlist.GRANULATED_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_GRANULATED_IRON.get(),1)
                .requires(itemlist.GRANULATED_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.GRANULATED_IRON.get()),has(itemlist.GRANULATED_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_GRANULATED_GOLD.get(),1)
                .requires(itemlist.GRANULATED_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.GRANULATED_GOLD.get()),has(itemlist.GRANULATED_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_GRANULATED_DIAMOND.get(),1)
                .requires(itemlist.GRANULATED_DIAMOND.get(),8)
                .unlockedBy(getHasName(itemlist.GRANULATED_DIAMOND.get()),has(itemlist.GRANULATED_DIAMOND.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_GRANULATED_EMERALD.get(),1)
                .requires(itemlist.GRANULATED_EMERALD.get(),8)
                .unlockedBy(getHasName(itemlist.GRANULATED_EMERALD.get()),has(itemlist.GRANULATED_EMERALD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.GRANULATED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.GRANULATED_ANCIENT_DEBRIS.get()),has(itemlist.GRANULATED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_SOFTENED_COPPER.get(),1)
                .requires(itemlist.SOFTENED_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.SOFTENED_COPPER.get()),has(itemlist.SOFTENED_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_SOFTENED_IRON.get(),1)
                .requires(itemlist.SOFTENED_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.SOFTENED_IRON.get()),has(itemlist.SOFTENED_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_SOFTENED_GOLD.get(),1)
                .requires(itemlist.SOFTENED_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.SOFTENED_GOLD.get()),has(itemlist.SOFTENED_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_SOFTENED_DIAMOND.get(),1)
                .requires(itemlist.SOFTENED_DIAMOND.get(),8)
                .unlockedBy(getHasName(itemlist.SOFTENED_DIAMOND.get()),has(itemlist.SOFTENED_DIAMOND.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_SOFTENED_EMERALD.get(),1)
                .requires(itemlist.SOFTENED_EMERALD.get(),8)
                .unlockedBy(getHasName(itemlist.SOFTENED_EMERALD.get()),has(itemlist.SOFTENED_EMERALD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_SOFTENED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.SOFTENED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.SOFTENED_ANCIENT_DEBRIS.get()),has(itemlist.SOFTENED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_PURIFIED_COPPER.get(),1)
                .requires(itemlist.PURIFIED_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.PURIFIED_COPPER.get()),has(itemlist.PURIFIED_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_PURIFIED_IRON.get(),1)
                .requires(itemlist.PURIFIED_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.PURIFIED_IRON.get()),has(itemlist.PURIFIED_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_PURIFIED_GOLD.get(),1)
                .requires(itemlist.PURIFIED_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.PURIFIED_GOLD.get()),has(itemlist.PURIFIED_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_PURIFIED_DIAMOND.get(),1)
                .requires(itemlist.PURIFIED_DIAMOND.get(),8)
                .unlockedBy(getHasName(itemlist.PURIFIED_DIAMOND.get()),has(itemlist.PURIFIED_DIAMOND.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_PURIFIED_EMERALD.get(),1)
                .requires(itemlist.PURIFIED_EMERALD.get(),8)
                .unlockedBy(getHasName(itemlist.PURIFIED_EMERALD.get()),has(itemlist.PURIFIED_EMERALD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLLECTED_PURIFIED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.PURIFIED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.PURIFIED_ANCIENT_DEBRIS.get()),has(itemlist.PURIFIED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_CRUSHED_RAW_COPPER.get(),1)
                .requires(itemlist.COLLECTED_CRUSHED_RAW_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_CRUSHED_RAW_COPPER.get()),has(itemlist.COLLECTED_CRUSHED_RAW_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_CRUSHED_RAW_IRON.get(),1)
                .requires(itemlist.COLLECTED_CRUSHED_RAW_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_CRUSHED_RAW_IRON.get()),has(itemlist.COLLECTED_CRUSHED_RAW_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_CRUSHED_RAW_GOLD.get(),1)
                .requires(itemlist.COLLECTED_CRUSHED_RAW_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_CRUSHED_RAW_GOLD.get()),has(itemlist.COLLECTED_CRUSHED_RAW_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get(),1)
                .requires(itemlist.COLLECTED_CRUSHED_DIAMOND_ORE.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_CRUSHED_DIAMOND_ORE.get()),has(itemlist.COLLECTED_CRUSHED_DIAMOND_ORE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get(),1)
                .requires(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()),has(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()),has(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_GRANULATED_COPPER.get(),1)
                .requires(itemlist.COLLECTED_GRANULATED_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_GRANULATED_COPPER.get()),has(itemlist.COLLECTED_GRANULATED_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_GRANULATED_IRON.get(),1)
                .requires(itemlist.COLLECTED_GRANULATED_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_GRANULATED_IRON.get()),has(itemlist.COLLECTED_GRANULATED_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_GRANULATED_GOLD.get(),1)
                .requires(itemlist.COLLECTED_GRANULATED_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_GRANULATED_GOLD.get()),has(itemlist.COLLECTED_GRANULATED_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_GRANULATED_DIAMOND.get(),1)
                .requires(itemlist.COLLECTED_GRANULATED_DIAMOND.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_GRANULATED_DIAMOND.get()),has(itemlist.COLLECTED_GRANULATED_DIAMOND.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_GRANULATED_EMERALD.get(),1)
                .requires(itemlist.COLLECTED_GRANULATED_EMERALD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_GRANULATED_EMERALD.get()),has(itemlist.COLLECTED_GRANULATED_EMERALD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get()),has(itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_SOFTENED_COPPER.get(),1)
                .requires(itemlist.COLLECTED_SOFTENED_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_SOFTENED_COPPER.get()),has(itemlist.COLLECTED_SOFTENED_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_SOFTENED_IRON.get(),1)
                .requires(itemlist.COLLECTED_SOFTENED_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_SOFTENED_IRON.get()),has(itemlist.COLLECTED_SOFTENED_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_SOFTENED_GOLD.get(),1)
                .requires(itemlist.COLLECTED_SOFTENED_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_SOFTENED_GOLD.get()),has(itemlist.COLLECTED_SOFTENED_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_SOFTENED_DIAMOND.get(),1)
                .requires(itemlist.COLLECTED_SOFTENED_DIAMOND.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_SOFTENED_DIAMOND.get()),has(itemlist.COLLECTED_SOFTENED_DIAMOND.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_SOFTENED_EMERALD.get(),1)
                .requires(itemlist.COLLECTED_SOFTENED_EMERALD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_SOFTENED_EMERALD.get()),has(itemlist.COLLECTED_SOFTENED_EMERALD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.COLLECTED_SOFTENED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_SOFTENED_ANCIENT_DEBRIS.get()),has(itemlist.COLLECTED_SOFTENED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_PURIFIED_COPPER.get(),1)
                .requires(itemlist.COLLECTED_PURIFIED_COPPER.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_PURIFIED_COPPER.get()),has(itemlist.COLLECTED_PURIFIED_COPPER.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_PURIFIED_IRON.get(),1)
                .requires(itemlist.COLLECTED_PURIFIED_IRON.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_PURIFIED_IRON.get()),has(itemlist.COLLECTED_PURIFIED_IRON.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_PURIFIED_GOLD.get(),1)
                .requires(itemlist.COLLECTED_PURIFIED_GOLD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_PURIFIED_GOLD.get()),has(itemlist.COLLECTED_PURIFIED_GOLD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_PURIFIED_DIAMOND.get(),1)
                .requires(itemlist.COLLECTED_PURIFIED_DIAMOND.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_PURIFIED_DIAMOND.get()),has(itemlist.COLLECTED_PURIFIED_DIAMOND.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_PURIFIED_EMERALD.get(),1)
                .requires(itemlist.COLLECTED_PURIFIED_EMERALD.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_PURIFIED_EMERALD.get()),has(itemlist.COLLECTED_PURIFIED_EMERALD.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get(),1)
                .requires(itemlist.COLLECTED_PURIFIED_ANCIENT_DEBRIS.get(),8)
                .unlockedBy(getHasName(itemlist.COLLECTED_PURIFIED_ANCIENT_DEBRIS.get()),has(itemlist.COLLECTED_PURIFIED_ANCIENT_DEBRIS.get()))
                .save(consumer);

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()),
                itemlist.COLLECTED_GRANULATED_EMERALD.get(),
                2
        ).save(consumer,"collected_emerald_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()),
                itemlist.COLLECTED_GRANULATED_EMERALD.get(),
                4
        ).save(consumer,"collected_emerald_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()),
                itemlist.COLLECTED_GRANULATED_EMERALD.get(),
                6
        ).save(consumer,"collected_emerald_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()),
                itemlist.COLLECTED_GRANULATED_EMERALD.get(),
                8
        ).save(consumer,"collected_emerald_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_EMERALD_ORE.get()),
                itemlist.COLLECTED_GRANULATED_EMERALD.get(),
                64
        ).save(consumer,"collected_emerald_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),
                2
        ).save(consumer,"collected_ancient_debris_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),
                4
        ).save(consumer,"collected_ancient_debris_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),
                6
        ).save(consumer,"collected_ancient_debris_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),
                8
        ).save(consumer,"collected_ancient_debris_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.COLLECTED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.COLLECTED_GRANULATED_ANCIENT_DEBRIS.get(),
                64
        ).save(consumer,"collected_ancient_debris_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_COPPER.get()),
                itemlist.PACKAGED_GRANULATED_COPPER.get(),
                2
        ).save(consumer,"packaged_copper_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_COPPER.get()),
                itemlist.PACKAGED_GRANULATED_COPPER.get(),
                4
        ).save(consumer,"packaged_copper_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_COPPER.get()),
                itemlist.PACKAGED_GRANULATED_COPPER.get(),
                6
        ).save(consumer,"packaged_copper_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_COPPER.get()),
                itemlist.PACKAGED_GRANULATED_COPPER.get(),
                8
        ).save(consumer,"packaged_copper_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_COPPER.get()),
                itemlist.PACKAGED_GRANULATED_COPPER.get(),
                64
        ).save(consumer,"packaged_copper_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_IRON.get()),
                itemlist.PACKAGED_GRANULATED_IRON.get(),
                2
        ).save(consumer,"packaged_iron_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_IRON.get()),
                itemlist.PACKAGED_GRANULATED_IRON.get(),
                4
        ).save(consumer,"packaged_iron_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_IRON.get()),
                itemlist.PACKAGED_GRANULATED_IRON.get(),
                6
        ).save(consumer,"packaged_iron_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_IRON.get()),
                itemlist.PACKAGED_GRANULATED_IRON.get(),
                8
        ).save(consumer,"packaged_iron_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_IRON.get()),
                itemlist.PACKAGED_GRANULATED_IRON.get(),
                64
        ).save(consumer,"packaged_iron_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_GOLD.get()),
                itemlist.PACKAGED_GRANULATED_GOLD.get(),
                2
        ).save(consumer,"packaged_gold_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_GOLD.get()),
                itemlist.PACKAGED_GRANULATED_GOLD.get(),
                4
        ).save(consumer,"packaged_gold_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_GOLD.get()),
                itemlist.PACKAGED_GRANULATED_GOLD.get(),
                6
        ).save(consumer,"packaged_gold_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_GOLD.get()),
                itemlist.PACKAGED_GRANULATED_GOLD.get(),
                8
        ).save(consumer,"packaged_gold_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_RAW_GOLD.get()),
                itemlist.PACKAGED_GRANULATED_GOLD.get(),
                64
        ).save(consumer,"packaged_gold_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get()),
                itemlist.PACKAGED_GRANULATED_DIAMOND.get(),
                2
        ).save(consumer,"packaged_diamond_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get()),
                itemlist.PACKAGED_GRANULATED_DIAMOND.get(),
                4
        ).save(consumer,"packaged_diamond_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get()),
                itemlist.PACKAGED_GRANULATED_DIAMOND.get(),
                6
        ).save(consumer,"packaged_diamond_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get()),
                itemlist.PACKAGED_GRANULATED_DIAMOND.get(),
                8
        ).save(consumer,"packaged_diamond_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_DIAMOND_ORE.get()),
                itemlist.PACKAGED_GRANULATED_DIAMOND.get(),
                64
        ).save(consumer,"packaged_diamond_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get()),
                itemlist.PACKAGED_GRANULATED_EMERALD.get(),
                2
        ).save(consumer,"packaged_emerald_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get()),
                itemlist.PACKAGED_GRANULATED_EMERALD.get(),
                4
        ).save(consumer,"packaged_emerald_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get()),
                itemlist.PACKAGED_GRANULATED_EMERALD.get(),
                6
        ).save(consumer,"packaged_emerald_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get()),
                itemlist.PACKAGED_GRANULATED_EMERALD.get(),
                8
        ).save(consumer,"packaged_emerald_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_EMERALD_ORE.get()),
                itemlist.PACKAGED_GRANULATED_EMERALD.get(),
                64
        ).save(consumer,"packaged_emerald_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get(),
                2
        ).save(consumer,"packaged_ancient_debris_granulated_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get(),
                4
        ).save(consumer,"packaged_ancient_debris_granulated_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get(),
                6
        ).save(consumer,"packaged_ancient_debris_granulated_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRANULATING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get(),
                8
        ).save(consumer,"packaged_ancient_debris_granulated_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_CRUSHED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get(),
                64
        ).save(consumer,"packaged_ancient_debris_granulated_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_COPPER.get()),
                itemlist.PACKAGED_SOFTENED_COPPER.get(),
                2
        ).save(consumer,"packaged_copper_softened_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_COPPER.get()),
                itemlist.PACKAGED_SOFTENED_COPPER.get(),
                4
        ).save(consumer,"packaged_copper_softened_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_COPPER.get()),
                itemlist.PACKAGED_SOFTENED_COPPER.get(),
                6
        ).save(consumer,"packaged_copper_softened_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_COPPER.get()),
                itemlist.PACKAGED_SOFTENED_COPPER.get(),
                8
        ).save(consumer,"packaged_copper_softened_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_COPPER.get()),
                itemlist.PACKAGED_SOFTENED_COPPER.get(),
                64
        ).save(consumer,"packaged_copper_softened_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_IRON.get()),
                itemlist.PACKAGED_SOFTENED_IRON.get(),
                2
        ).save(consumer,"packaged_iron_softened_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_IRON.get()),
                itemlist.PACKAGED_SOFTENED_IRON.get(),
                4
        ).save(consumer,"packaged_iron_softened_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_IRON.get()),
                itemlist.PACKAGED_SOFTENED_IRON.get(),
                6
        ).save(consumer,"packaged_iron_softened_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_IRON.get()),
                itemlist.PACKAGED_SOFTENED_IRON.get(),
                8
        ).save(consumer,"packaged_iron_softened_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_IRON.get()),
                itemlist.PACKAGED_SOFTENED_IRON.get(),
                64
        ).save(consumer,"packaged_iron_softened_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_GOLD.get()),
                itemlist.PACKAGED_SOFTENED_GOLD.get(),
                2
        ).save(consumer,"packaged_gold_softened_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_GOLD.get()),
                itemlist.PACKAGED_SOFTENED_GOLD.get(),
                4
        ).save(consumer,"packaged_gold_softened_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_GOLD.get()),
                itemlist.PACKAGED_SOFTENED_GOLD.get(),
                6
        ).save(consumer,"packaged_gold_softened_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_GOLD.get()),
                itemlist.PACKAGED_SOFTENED_GOLD.get(),
                8
        ).save(consumer,"packaged_gold_softened_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_GOLD.get()),
                itemlist.PACKAGED_SOFTENED_GOLD.get(),
                64
        ).save(consumer,"packaged_gold_softened_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_DIAMOND.get()),
                itemlist.PACKAGED_SOFTENED_DIAMOND.get(),
                2
        ).save(consumer,"packaged_diamond_softened_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_DIAMOND.get()),
                itemlist.PACKAGED_SOFTENED_DIAMOND.get(),
                4
        ).save(consumer,"packaged_diamond_softened_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_DIAMOND.get()),
                itemlist.PACKAGED_SOFTENED_DIAMOND.get(),
                6
        ).save(consumer,"packaged_diamond_softened_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_DIAMOND.get()),
                itemlist.PACKAGED_SOFTENED_DIAMOND.get(),
                8
        ).save(consumer,"packaged_diamond_softened_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_DIAMOND.get()),
                itemlist.PACKAGED_SOFTENED_DIAMOND.get(),
                64
        ).save(consumer,"packaged_diamond_softened_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_EMERALD.get()),
                itemlist.PACKAGED_SOFTENED_EMERALD.get(),
                2
        ).save(consumer,"packaged_emerald_softened_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_EMERALD.get()),
                itemlist.PACKAGED_SOFTENED_EMERALD.get(),
                4
        ).save(consumer,"packaged_emerald_softened_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_EMERALD.get()),
                itemlist.PACKAGED_SOFTENED_EMERALD.get(),
                6
        ).save(consumer,"packaged_emerald_softened_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_EMERALD.get()),
                itemlist.PACKAGED_SOFTENED_EMERALD.get(),
                8
        ).save(consumer,"packaged_emerald_softened_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_EMERALD.get()),
                itemlist.PACKAGED_SOFTENED_EMERALD.get(),
                64
        ).save(consumer,"packaged_emerald_softened_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get(),
                2
        ).save(consumer,"packaged_ancient_debris_softened_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get(),
                4
        ).save(consumer,"packaged_ancient_debris_softened_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get(),
                6
        ).save(consumer,"packaged_ancient_debris_softened_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_SOFTENING_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get(),
                8
        ).save(consumer,"packaged_ancient_debris_softened_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_GRANULATED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get(),
                64
        ).save(consumer,"packaged_ancient_debris_softened_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_COPPER.get()),
                itemlist.PACKAGED_PURIFIED_COPPER.get(),
                2
        ).save(consumer,"packaged_copper_purification_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_COPPER.get()),
                itemlist.PACKAGED_PURIFIED_COPPER.get(),
                4
        ).save(consumer,"packaged_copper_purification_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_COPPER.get()),
                itemlist.PACKAGED_PURIFIED_COPPER.get(),
                6
        ).save(consumer,"packaged_copper_purification_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_COPPER.get()),
                itemlist.PACKAGED_PURIFIED_COPPER.get(),
                8
        ).save(consumer,"packaged_copper_purification_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_COPPER.get()),
                itemlist.PACKAGED_PURIFIED_COPPER.get(),
                64
        ).save(consumer,"packaged_copper_purification_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_IRON.get()),
                itemlist.PACKAGED_PURIFIED_IRON.get(),
                2
        ).save(consumer,"packaged_iron_purification_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_IRON.get()),
                itemlist.PACKAGED_PURIFIED_IRON.get(),
                4
        ).save(consumer,"packaged_iron_purification_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_IRON.get()),
                itemlist.PACKAGED_PURIFIED_IRON.get(),
                6
        ).save(consumer,"packaged_iron_purification_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_IRON.get()),
                itemlist.PACKAGED_PURIFIED_IRON.get(),
                8
        ).save(consumer,"packaged_iron_purification_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_IRON.get()),
                itemlist.PACKAGED_PURIFIED_IRON.get(),
                64
        ).save(consumer,"packaged_iron_purification_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_GOLD.get()),
                itemlist.PACKAGED_PURIFIED_GOLD.get(),
                2
        ).save(consumer,"packaged_gold_purification_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_GOLD.get()),
                itemlist.PACKAGED_PURIFIED_GOLD.get(),
                4
        ).save(consumer,"packaged_gold_purification_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_GOLD.get()),
                itemlist.PACKAGED_PURIFIED_GOLD.get(),
                6
        ).save(consumer,"packaged_gold_purification_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_GOLD.get()),
                itemlist.PACKAGED_PURIFIED_GOLD.get(),
                8
        ).save(consumer,"packaged_gold_purification_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_GOLD.get()),
                itemlist.PACKAGED_PURIFIED_GOLD.get(),
                64
        ).save(consumer,"packaged_gold_purification_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_DIAMOND.get()),
                itemlist.PACKAGED_PURIFIED_DIAMOND.get(),
                2
        ).save(consumer,"packaged_diamond_purification_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_DIAMOND.get()),
                itemlist.PACKAGED_PURIFIED_DIAMOND.get(),
                4
        ).save(consumer,"packaged_diamond_purification_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_DIAMOND.get()),
                itemlist.PACKAGED_PURIFIED_DIAMOND.get(),
                6
        ).save(consumer,"packaged_diamond_purification_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_DIAMOND.get()),
                itemlist.PACKAGED_PURIFIED_DIAMOND.get(),
                8
        ).save(consumer,"packaged_diamond_purification_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_DIAMOND.get()),
                itemlist.PACKAGED_PURIFIED_DIAMOND.get(),
                64
        ).save(consumer,"packaged_diamond_purification_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_EMERALD.get()),
                itemlist.PACKAGED_PURIFIED_EMERALD.get(),
                2
        ).save(consumer,"packaged_emerald_purification_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_EMERALD.get()),
                itemlist.PACKAGED_PURIFIED_EMERALD.get(),
                4
        ).save(consumer,"packaged_emerald_purification_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_EMERALD.get()),
                itemlist.PACKAGED_PURIFIED_EMERALD.get(),
                6
        ).save(consumer,"packaged_emerald_purification_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_EMERALD.get()),
                itemlist.PACKAGED_PURIFIED_EMERALD.get(),
                8
        ).save(consumer,"packaged_emerald_purification_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_EMERALD.get()),
                itemlist.PACKAGED_PURIFIED_EMERALD.get(),
                64
        ).save(consumer,"packaged_emerald_purification_philosophers_stone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get(),
                2
        ).save(consumer,"packaged_ancient_debris_purification_basic_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get(),
                4
        ).save(consumer,"packaged_ancient_debris_purification_advanced_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get(),
                6
        ).save(consumer,"packaged_ancient_debris_purification_elite_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_PURIFICATION_RUNE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get(),
                8
        ).save(consumer,"packaged_ancient_debris_purification_ultimate_rune");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(itemlist.PACKAGED_SOFTENED_ANCIENT_DEBRIS.get()),
                itemlist.PACKAGED_PURIFIED_ANCIENT_DEBRIS.get(),
                64
        ).save(consumer,"packaged_ancient_debris_purification_philosophers_stone");


        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk1.get())
                .pattern("aaa")
                .pattern("bac")
                .pattern("aaa")
                .define('a',itemlist.X9_COBBLESTONE.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE.get()),has(itemlist.X9_COBBLESTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk2.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk1.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T1_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk1.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk1.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk3.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk2.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T2_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk2.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk2.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk4.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk3.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T3_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk3.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk3.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk5.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk4.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T4_PANAKEIA_GEM_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk4.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk4.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk6.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk5.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T5_PANAKEIA_GEM_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk5.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk5.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk7.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk6.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T6_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk6.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk6.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.COBBLESTONE_GENERATOR_Mk8.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.COBBLESTONE_GENERATOR_Mk7.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.UNITE_ALLOY_BLOCK.get())
                .unlockedBy(getHasName(itemlist.COBBLESTONE_GENERATOR_Mk7.get()),has(itemlist.COBBLESTONE_GENERATOR_Mk7.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SINGULARITY_INGOT.get(),1)
                .requires(itemlist.SINGULARITY_NUGGET.get(),9)
                .unlockedBy(getHasName(itemlist.SINGULARITY_NUGGET.get()),has(itemlist.SINGULARITY_NUGGET.get()))
                .save(consumer);

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(Items.APPLE),
                itemlist.TOMATO.get(),
                1
        ).save(consumer,"activator_recipe_tomato");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(Items.BEETROOT),
                itemlist.CABBAGE.get(),
                1
        ).save(consumer,"activator_recipe_cabbage");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(Items.CARROT),
                itemlist.ONION.get(),
                1
        ).save(consumer,"activator_recipe_onion");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(Items.SWEET_BERRIES),
                itemlist.LEMON.get(),
                1
        ).save(consumer,"activator_recipe_lemon");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(Items.WATER_BUCKET),
                itemlist.SALT.get(),
                8
        ).save(consumer,"activator_recipe_x8salt");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(itemlist.FLOUR.get()),
                itemlist.RICE.get(),
                1
        ).save(consumer,"activator_recipe_rice");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_FRUITS.get()),
                Ingredient.of(Items.HAY_BLOCK),
                itemlist.RICE.get(),
                9
        ).save(consumer,"activator_recipe_x9rice");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHEESE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.FLOUR.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.WHEAT)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.DOUGH.get(),1)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(Items.WATER_BUCKET)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "dough_from_flour_and_water");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.DOUGH.get(),4)
                .requires(itemlist.FLOUR.get(),4)
                .requires(Items.WATER_BUCKET,4)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "x4dough_from_flour_and_water");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.RAW_PASTA.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","dough")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SLICED_APPLE.get(),4)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.APPLE)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHOPPED_SEAWEED.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.SEAGRASS)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,itemlist.NORI.get())
                .pattern("aa")
                .pattern("aa")
                .define('a',itemlist.CHOPPED_SEAWEED.get())
                .unlockedBy(getHasName(itemlist.CHOPPED_SEAWEED.get()),has(itemlist.CHOPPED_SEAWEED.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHOCOLATE.get(),1)
                .requires(Items.COCOA_BEANS)
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .requires(Items.SUGAR)
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.VINEGAR.get(),1)
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(Items.WATER_BUCKET)
                .requires(Items.HONEY_BOTTLE)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "vinegar_from_rice_and_water_with_honey");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.VINEGAR.get(),4)
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(Items.WATER_BUCKET)
                .requires(itemlist.VINEGAR.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "vinegar_from_rice_and_water_with_vinegar");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COOKING_OIL.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "cooking_oil_from_rice");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COOKING_OIL.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ModTags.Items.BEEF_OR_PORK)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer,"cooking_oil_from_meat");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.BUTTER.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "butter_from_milk");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.BUTTER.get(),4)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.MILK_BUCKET)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer, "x4butter_from_milk_bucket");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SWEET_BERRY_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.GLOW_BERRY_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.GLOW_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHORUS_FRUIT_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.CHORUS_FRUIT)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.TOMATO_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.APPLE_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.APPLE)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.LEMON_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","lemons")))
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.MELON_JAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.MELON_SLICE)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PUMPKIN_JAM.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.PUMPKIN)
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.MILK_BOTTLE.get(),4)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.MILK_BUCKET)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.TOMATO_SAUCE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(itemlist.COOKING_OIL.get())
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHEESE_SAUCE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CREAM_SAUCE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.VINAIGRETTE_SAUCE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(itemlist.VINEGAR.get())
                .requires(itemlist.COOKING_OIL.get())
                .requires(Items.SUGAR)
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(ItemTags.create(new ResourceLocation("forge","lemons")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.TOMATO_CREAM_SOUP.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SEAFOOD_CHOWDER.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.FISHES)
                .requires(Items.POTATO)
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PUMPKIN_THICK_SOUP.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.PUMPKIN)
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.POTATO_THICK_SOUP.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.POTATO)
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.MEAT_SAUCE_PASTA.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.COOKED_BEEF)
                .requires(itemlist.TOMATO_SAUCE.get())
                .requires(ItemTags.create(new ResourceLocation("forge","pasta/raw_pasta")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CREAM_PASTA.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.BROWN_MUSHROOM)
                .requires(itemlist.CREAM_SAUCE.get())
                .requires(ItemTags.create(new ResourceLocation("forge","pasta/raw_pasta")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.FRIED_RICE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.COOKED_PORKCHOP)
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(itemlist.COOKING_OIL.get())
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.OMELETTE_RICE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.EGG)
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(Items.CHICKEN)
                .requires(itemlist.COOKING_OIL.get())
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.RICE_BALL.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(itemlist.NORI.get())
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SALMON_SUSHI.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.SALMON)
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(itemlist.VINEGAR.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.KOMBUJIME_COD_SUSHI.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.COD)
                .requires(Items.SEAGRASS)
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(itemlist.VINEGAR.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SUSHI_ROLLS.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.SALMON)
                .requires(Items.COD)
                .requires(itemlist.NORI.get())
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(itemlist.VINEGAR.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.BREAD,1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","dough/wheat")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHEESE_BREAD.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(ItemTags.create(new ResourceLocation("forge","dough/wheat")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ONION_BREAD.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","dough/wheat")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.TOMATO_FOCACCIA.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(ItemTags.create(new ResourceLocation("forge","dough/wheat")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(itemlist.COOKING_OIL.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.PIZZA_BEFORE_BAKING.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(itemlist.PIZZA_DOUGH.get())
                .requires(itemlist.TOMATO_SAUCE.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(Items.BEEF)
                .requires(Items.BROWN_MUSHROOM)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        oreSmelting(consumer,BAKING_PIZZA,RecipeCategory.MISC,itemlist.PIZZA.get(),0.25f,200,"baking_pizza");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CALZONE_BEFORE_BAKING.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(itemlist.PIZZA_DOUGH.get())
                .requires(itemlist.PIZZA_BEFORE_BAKING.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        oreSmelting(consumer,BAKING_CALZONE,RecipeCategory.MISC,itemlist.CALZONE.get(),0.25f,200,"baking_calzone");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHEESE_CRACKER.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHEESE_CAKE.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHOCOLATE_CAKE.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(itemlist.CHOCOLATE.get())
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.LEMON_CAKE.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","lemons")))
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(ItemTags.create(new ResourceLocation("forge","butters")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.APPLE_PIE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.APPLE,2)
                .requires(Items.SUGAR)
                .requires(ItemTags.create(new ResourceLocation("forge","dough/wheat")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COLESLAW.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cabbage")))
                .requires(Items.CARROT)
                .requires(itemlist.VINAIGRETTE_SAUCE.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHEESE_BAKED_POTATO.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(Items.BAKED_POTATO)
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ONION_RING.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(Items.EGG)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(itemlist.COOKING_OIL.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.GRILLED_TOMATO_AND_CHEESE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CABBAGE_ROLL.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cabbage")))
                .requires(Items.BEEF)
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(itemlist.TOMATO_SAUCE.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.GREEN_SALAD.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cabbage")))
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(Items.CARROT)
                .requires(itemlist.VINAIGRETTE_SAUCE.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.POTATO_SALAD.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","cabbage")))
                .requires(Items.BAKED_POTATO)
                .requires(Items.EGG)
                .requires(itemlist.COOKING_OIL.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.FRIED_CHICKEN.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.CHICKEN)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(itemlist.COOKING_OIL.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.FRIED_FISH.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.COD)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(itemlist.COOKING_OIL.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.HAMBURG_STEAK.get(),2)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.BEEF)
                .requires(Items.PORKCHOP)
                .requires(Items.BREAD)
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.HAMBURGER.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(itemlist.HAMBURG_STEAK.get())
                .requires(Items.BREAD)
                .requires(ItemTags.create(new ResourceLocation("forge","onions")))
                .requires(ItemTags.create(new ResourceLocation("forge","cabbage")))
                .requires(ItemTags.create(new ResourceLocation("forge","tomatoes")))
                .requires(ItemTags.create(new ResourceLocation("forge","cheeses")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.STEVES_LAVA_CHICKEN.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.CHICKEN)
                .requires(Items.LAVA_BUCKET)
                .requires(Items.REDSTONE)
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ICE_CREAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .requires(Items.SNOWBALL)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.CHOCOLATE_ICE_CREAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(itemlist.CHOCOLATE.get())
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .requires(Items.SNOWBALL)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.LEMON_ICE_CREAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","lemons")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .requires(Items.SNOWBALL)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.APPLE_ICE_CREAM.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.APPLE)
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .requires(Items.SNOWBALL)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SWEET_BERRY_SMOOTHIE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.SWEET_BERRIES)
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .requires(Items.SNOWBALL)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.GLOW_BERRY_SMOOTHIE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.GLOW_BERRIES)
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .requires(Items.SNOWBALL)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.RICE_PUDDING.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","rice")))
                .requires(ItemTags.create(new ResourceLocation("forge","milk")))
                .requires(Items.SUGAR)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.JAM_BUN.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.BREAD)
                .requires(ItemTags.create(new ResourceLocation("alchemical_power","jams")))
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.FRIED_POTATO.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.POTATO)
                .requires(ItemTags.create(new ResourceLocation("forge","flour")))
                .requires(ItemTags.create(new ResourceLocation("forge","salt")))
                .requires(itemlist.COOKING_OIL.get())
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T1_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.T1_PANAKEIA_INGOT_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.T1_PANAKEIA_INGOT_BLOCK.get()),has(itemlist.T1_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T2_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.T2_PANAKEIA_INGOT_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA_INGOT_BLOCK.get()),has(itemlist.T2_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T3_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.T3_PANAKEIA_INGOT_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.T3_PANAKEIA_INGOT_BLOCK.get()),has(itemlist.T3_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T4_PANAKEIA_GEM_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.T4_PANAKEIA_GEM_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA_GEM_BLOCK.get()),has(itemlist.T4_PANAKEIA_GEM_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T5_PANAKEIA_GEM_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.T5_PANAKEIA_GEM_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.T5_PANAKEIA_GEM_BLOCK.get()),has(itemlist.T5_PANAKEIA_GEM_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.T6_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.T6_PANAKEIA_INGOT_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA_INGOT_BLOCK.get()),has(itemlist.T6_PANAKEIA_INGOT_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.UNITE_ALLOY_X81_COMPRESSED_PLATE.get(),1)
                .requires(itemlist.UNITE_ALLOY_BLOCK.get(),9)
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY_BLOCK.get()),has(itemlist.UNITE_ALLOY_BLOCK.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk2.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk1.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T1_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk1.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk1.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk3.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk2.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T2_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk2.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk2.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk4.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk3.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T3_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk3.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk3.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk5.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk4.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T4_PANAKEIA_GEM_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk4.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk4.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk6.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk5.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T5_PANAKEIA_GEM_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk5.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk5.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk7.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk6.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T6_PANAKEIA_INGOT_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk6.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk6.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X9_COBBLESTONE_GENERATOR_Mk8.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X9_COBBLESTONE_GENERATOR_Mk7.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.UNITE_ALLOY_X81_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X9_COBBLESTONE_GENERATOR_Mk7.get()),has(itemlist.X9_COBBLESTONE_GENERATOR_Mk7.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk2.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk1.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T1_PANAKEIA_INGOT_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk1.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk1.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk3.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk2.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T2_PANAKEIA_INGOT_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk2.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk2.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk4.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk3.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T3_PANAKEIA_INGOT_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk3.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk3.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk5.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk4.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T4_PANAKEIA_GEM_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk4.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk4.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk6.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk5.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T5_PANAKEIA_GEM_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk5.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk5.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk7.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk6.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.T6_PANAKEIA_INGOT_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk6.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk6.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X225_COBBLESTONE_GENERATOR_Mk8.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X225_COBBLESTONE_GENERATOR_Mk7.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.UNITE_ALLOY_X2025_COMPRESSED_PLATE.get())
                .unlockedBy(getHasName(itemlist.X225_COBBLESTONE_GENERATOR_Mk7.get()),has(itemlist.X225_COBBLESTONE_GENERATOR_Mk7.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk2.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk1.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.WISDOM_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk1.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk1.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk3.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk2.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.COURAGE_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk2.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk2.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk4.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk3.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.JUSTICE_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk3.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk3.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk5.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk4.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.LOVE_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk4.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk4.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk6.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk5.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.HOPE_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk5.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk5.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk7.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk6.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.TEMPERANCE_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk6.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk6.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE,itemlist.X38025_COBBLESTONE_GENERATOR_Mk8.get())
                .pattern("ada")
                .pattern("bdc")
                .pattern("ada")
                .define('a',itemlist.X38025_COBBLESTONE_GENERATOR_Mk7.get())
                .define('b',Items.WATER_BUCKET)
                .define('c',Items.LAVA_BUCKET)
                .define('d',itemlist.FAITH_CRYSTAL_PLATE.get())
                .unlockedBy(getHasName(itemlist.X38025_COBBLESTONE_GENERATOR_Mk7.get()),has(itemlist.X38025_COBBLESTONE_GENERATOR_Mk7.get()))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.SALMON_ROE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.SALMON)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COD_ROE.get(),1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(Items.COD)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRINDING_RUNE.get()),
                Ingredient.of(Items.COAL_ORE),
                Items.COAL,
                4
        ).save(consumer,"basic_grinding_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRINDING_RUNE.get()),
                Ingredient.of(Items.COAL_ORE),
                Items.COAL,
                8
        ).save(consumer,"advanced_grinding_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRINDING_RUNE.get()),
                Ingredient.of(Items.COAL_ORE),
                Items.COAL,
                16
        ).save(consumer,"elite_grinding_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRINDING_RUNE.get()),
                Ingredient.of(Items.COAL_ORE),
                Items.COAL,
                32
        ).save(consumer,"ultimate_grinding_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(Items.COAL_ORE),
                Items.COAL_BLOCK,
                64
        ).save(consumer,"philosophers_stone_grinding_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRINDING_RUNE.get()),
                Ingredient.of(Items.DEEPSLATE_COAL_ORE),
                Items.COAL,
                4
        ).save(consumer,"basic_grinding_deepslate_deepslate_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRINDING_RUNE.get()),
                Ingredient.of(Items.DEEPSLATE_COAL_ORE),
                Items.COAL,
                8
        ).save(consumer,"advanced_grinding_deepslate_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRINDING_RUNE.get()),
                Ingredient.of(Items.DEEPSLATE_COAL_ORE),
                Items.COAL,
                16
        ).save(consumer,"elite_grinding_deepslate_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRINDING_RUNE.get()),
                Ingredient.of(Items.DEEPSLATE_COAL_ORE),
                Items.COAL,
                32
        ).save(consumer,"ultimate_grinding_deepslate_coal_ores");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(Items.DEEPSLATE_COAL_ORE),
                Items.COAL_BLOCK,
                64
        ).save(consumer,"philosophers_stone_grinding_deepslate_coal_ores");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.T1_PANAKEIA_CUBE.get()),
                Ingredient.of(Items.COAL_BLOCK),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.T1_COMBUSTION_RUNE_BLOCK.get(),
                1
        ).save(consumer,"x9_t1_combustion_rune_engraving");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.T2_PANAKEIA_CUBE.get()),
                Ingredient.of(itemlist.T1_COMBUSTION_RUNE_BLOCK.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.T2_COMBUSTION_RUNE_BLOCK.get(),
                1
        ).save(consumer,"x9_t2_combustion_rune_engraving");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.T3_PANAKEIA_CUBE.get()),
                Ingredient.of(itemlist.T2_COMBUSTION_RUNE_BLOCK.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.T3_COMBUSTION_RUNE_BLOCK.get(),
                1
        ).save(consumer,"x9_t3_combustion_rune_engraving");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.T4_PANAKEIA_CUBE.get()),
                Ingredient.of(itemlist.T3_COMBUSTION_RUNE_BLOCK.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.T4_COMBUSTION_RUNE_BLOCK.get(),
                1
        ).save(consumer,"x9_t4_combustion_rune_engraving");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.T5_PANAKEIA_CUBE.get()),
                Ingredient.of(itemlist.T4_COMBUSTION_RUNE_BLOCK.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.T5_COMBUSTION_RUNE_BLOCK.get(),
                1
        ).save(consumer,"x9_t5_combustion_rune_engraving");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.T6_PANAKEIA_CUBE.get()),
                Ingredient.of(itemlist.T5_COMBUSTION_RUNE_BLOCK.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.T6_COMBUSTION_RUNE_BLOCK.get(),
                1
        ).save(consumer,"x9_t6_combustion_rune_engraving");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRINDING_RUNE.get()),
                Ingredient.of(Items.QUARTZ_BLOCK),
                Items.QUARTZ,
                2
        ).save(consumer,"basic_grinding_quartz_block");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRINDING_RUNE.get()),
                Ingredient.of(Items.QUARTZ_BLOCK),
                Items.QUARTZ,
                4
        ).save(consumer,"advanced_grinding_quartz_block");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRINDING_RUNE.get()),
                Ingredient.of(Items.QUARTZ_BLOCK),
                Items.QUARTZ,
                6
        ).save(consumer,"elite_grinding_quartz_block");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRINDING_RUNE.get()),
                Ingredient.of(Items.QUARTZ_BLOCK),
                Items.QUARTZ,
                8
        ).save(consumer,"ultimate_grinding_quartz_block");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(Items.QUARTZ_BLOCK),
                Items.QUARTZ,
                64
        ).save(consumer,"philosophers_stone_grinding_quartz_block");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.BASIC_GRINDING_RUNE.get()),
                Ingredient.of(Items.BLAZE_ROD),
                Items.BLAZE_POWDER,
                3
        ).save(consumer,"basic_grinding_blaze_rod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ADVANCED_GRINDING_RUNE.get()),
                Ingredient.of(Items.BLAZE_ROD),
                Items.BLAZE_POWDER,
                6
        ).save(consumer,"advanced_grinding_blaze_rod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ELITE_GRINDING_RUNE.get()),
                Ingredient.of(Items.BLAZE_ROD),
                Items.BLAZE_POWDER,
                9
        ).save(consumer,"elite_grinding_blaze_rod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.ULTIMATE_GRINDING_RUNE.get()),
                Ingredient.of(Items.BLAZE_ROD),
                Items.BLAZE_POWDER,
                12
        ).save(consumer,"ultimate_grinding_blaze_rod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.PHILOSOPHERS_STONE.get()),
                Ingredient.of(Items.BLAZE_ROD),
                Items.BLAZE_POWDER,
                64
        ).save(consumer,"philosophers_stone_grinding_blaze_rod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BLAZE_ROD),
                Items.GHAST_TEAR,
                1
        ).save(consumer,"rune_of_equivalent_blaze_rod_to_ghast_tear");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.GHAST_TEAR),
                Items.BLAZE_ROD,
                1
        ).save(consumer,"rune_of_equivalent_ghast_tear_to_blaze_rod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SKELETON_SKULL),
                Items.WITHER_SKELETON_SKULL,
                1
        ).save(consumer,"rune_of_equivalent_skeleton_skull_to_wither_skeleton_skull");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.WITHER_SKELETON_SKULL),
                Items.ZOMBIE_HEAD,
                1
        ).save(consumer,"rune_of_equivalent_wither_skeleton_skull_to_zombie_head");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ZOMBIE_HEAD),
                Items.PLAYER_HEAD,
                1
        ).save(consumer,"rune_of_equivalent_zombie_head_to_player_head");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PLAYER_HEAD),
                Items.CREEPER_HEAD,
                1
        ).save(consumer,"rune_of_equivalent_player_head_to_creeper_head");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.CREEPER_HEAD),
                Items.PIGLIN_HEAD,
                1
        ).save(consumer,"rune_of_equivalent_creeper_head_to_piglin_head");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PIGLIN_HEAD),
                Items.SKELETON_SKULL,
                1
        ).save(consumer,"rune_of_equivalent_piglin_head_to_skeleton_skull");


        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.DANDELION),
                Items.POPPY,
                1
        ).save(consumer,"rune_of_equivalent_dandelion_to_poppy");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.POPPY),
                Items.BLUE_ORCHID,
                1
        ).save(consumer,"rune_of_equivalent_poppy_to_blue_orchid");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BLUE_ORCHID),
                Items.ALLIUM,
                1
        ).save(consumer,"rune_of_equivalent_blue_orchid_to_allium");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ALLIUM),
                Items.AZURE_BLUET,
                1
        ).save(consumer,"rune_of_equivalent_allium_to_azure_bluet");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.AZURE_BLUET),
                Items.RED_TULIP,
                1
        ).save(consumer,"rune_of_equivalent_azure_bluet_to_red_tulip");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.RED_TULIP),
                Items.ORANGE_TULIP,
                1
        ).save(consumer,"rune_of_equivalent_red_tulip_to_orange_tulip");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ORANGE_TULIP),
                Items.WHITE_TULIP,
                1
        ).save(consumer,"rune_of_equivalent_orange_tulip_to_white_tulip");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.WHITE_TULIP),
                Items.PINK_TULIP,
                1
        ).save(consumer,"rune_of_equivalent_white_tulip_to_pink_tulip");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PINK_TULIP),
                Items.OXEYE_DAISY,
                1
        ).save(consumer,"rune_of_equivalent_pink_tulip_to_oxeye_daisy");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.OXEYE_DAISY),
                Items.CORNFLOWER,
                1
        ).save(consumer,"rune_of_equivalent_oxeye_daisy_to_cornflower");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.CORNFLOWER),
                Items.LILY_OF_THE_VALLEY,
                1
        ).save(consumer,"rune_of_equivalent_cornflower_to_lily_of_the_valley");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.LILY_OF_THE_VALLEY),
                Items.SUNFLOWER,
                1
        ).save(consumer,"rune_of_equivalent_lily_of_the_valley_to_sunflower");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SUNFLOWER),
                Items.LILAC,
                1
        ).save(consumer,"rune_of_equivalent_sunflower_to_lilac");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.LILAC),
                Items.ROSE_BUSH,
                1
        ).save(consumer,"rune_of_equivalent_lilac_to_rose_bush");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ROSE_BUSH),
                Items.PEONY,
                1
        ).save(consumer,"rune_of_equivalent_rose_bush_to_peony");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PEONY),
                Items.DANDELION,
                1
        ).save(consumer,"rune_of_equivalent_peony_to_dandelion");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.WITHER_ROSE),
                Items.TORCHFLOWER,
                1
        ).save(consumer,"rune_of_equivalent_wither_rose_to_torchflower");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.TORCHFLOWER),
                Items.PITCHER_PLANT,
                1
        ).save(consumer,"rune_of_equivalent_torchflower_to_pitcher_plant");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PITCHER_PLANT),
                Items.WITHER_ROSE,
                1
        ).save(consumer,"rune_of_equivalent_pitcher_plant_to_wither_rose");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.TOTEM_OF_UNDYING),
                Items.SPONGE,
                1
        ).save(consumer,"rune_of_equivalent_totem_of_undying_to_sponge");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SPONGE),
                Items.TOTEM_OF_UNDYING,
                1
        ).save(consumer,"rune_of_equivalent_sponge_to_totem_of_undying");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PUMPKIN),
                Items.MELON,
                1
        ).save(consumer,"rune_of_equivalent_pumpkin_to_melon");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MELON),
                Items.PUMPKIN,
                1
        ).save(consumer,"rune_of_equivalent_melon_to_pumpkin");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.OAK_SAPLING),
                Items.SPRUCE_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_oak_sapling_to_spruce_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SPRUCE_SAPLING),
                Items.BIRCH_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_spruce_sapling_to_birch_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BIRCH_SAPLING),
                Items.JUNGLE_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_birch_sapling_to_jungle_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.JUNGLE_SAPLING),
                Items.ACACIA_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_jungle_sapling_to_acacia_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ACACIA_SAPLING),
                Items.DARK_OAK_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_acacia_sapling_to_dark_oak_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.DARK_OAK_SAPLING),
                Items.MANGROVE_PROPAGULE,
                1
        ).save(consumer,"rune_of_equivalent_dark_oak_sapling_to_mangrove_propagule");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MANGROVE_PROPAGULE),
                Items.CHERRY_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_mangrove_propagule_to_cherry_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.CHERRY_SAPLING),
                Items.OAK_SAPLING,
                1
        ).save(consumer,"rune_of_equivalent_cherry_sapling_to_oak_sapling");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BAMBOO),
                Items.CACTUS,
                1
        ).save(consumer,"rune_of_equivalent_bamboo_to_cactus");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.CACTUS),
                Items.LILY_PAD,
                1
        ).save(consumer,"rune_of_equivalent_cactus_to_lily_pad");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.LILY_PAD),
                Items.BAMBOO,
                1
        ).save(consumer,"rune_of_equivalent_lily_pad_to_bamboo");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.POTATO),
                Items.POISONOUS_POTATO,
                1
        ).save(consumer,"rune_of_equivalent_potato_to_poisonous_potato");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.POISONOUS_POTATO),
                Items.POTATO,
                1
        ).save(consumer,"rune_of_equivalent_poisonous_potato_to_potato");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MOSS_BLOCK),
                Items.MOSSY_COBBLESTONE,
                1
        ).save(consumer,"rune_of_equivalent_moss_block_to_mossy_cobblestone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MOSSY_COBBLESTONE),
                Items.MOSS_BLOCK,
                1
        ).save(consumer,"rune_of_equivalent_mossy_cobblestone_to_moss_block");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_coast_armor_trim_to_dune_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_dune_armor_trim_to_eye_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_eye_armor_trim_to_host_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_host_armor_trim_to_raiser_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_raiser_armor_trim_to_rib_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_rib_armor_trim_to_sentry_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_sentry_armor_trim_to_shaper_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_shaper_armor_trim_to_silence_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_silence_armor_trim_to_snout_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_snout_armor_trim_to_spire_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_spire_armor_trim_to_tide_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_tide_armor_trim_to_vex_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_vex_armor_trim_to_ward_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_ward_armor_trim_to_wayfinder_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_wayfinder_armor_trim_to_wild_armor_trim");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE),
                Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE,
                1
        ).save(consumer,"rune_of_equivalent_wild_armor_trim_to_coast_armor_trim");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .pattern("cbc")
                .pattern("bab")
                .pattern("cbc")
                .define('a',ModTags.Items.NETHERITE_UPGRADE_TEMPLATE_MATERIALS)
                .define('b',Items.NETHERITE_BLOCK)
                .define('c',Items.DIAMOND_BLOCK)
                .unlockedBy(getHasName(Items.NETHERITE_BLOCK),has(Items.NETHERITE_BLOCK))
                .save(consumer,"netherite_upgrade_smithing_template_crafting");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.CLAY),
                Items.CLAY_BALL,
                4
        ).save(consumer,"disassembly_clay_to_clay_ball");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SNOW_BLOCK),
                Items.SNOWBALL,
                4
        ).save(consumer,"disassembly_snow_block_to_snowball");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ELYTRA),
                Items.DRAGON_EGG,
                1
        ).save(consumer,"rune_of_equivalent_elytra_to_dragon_egg");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.DRAGON_EGG),
                Items.HEART_OF_THE_SEA,
                1
        ).save(consumer,"rune_of_equivalent_dragon_egg_to_heart_of_the_sea");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.HEART_OF_THE_SEA),
                Items.ELYTRA,
                1
        ).save(consumer,"rune_of_equivalent_heart_of_the_sea_to_elytra");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_13),
                Items.MUSIC_DISC_CAT,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_13_to_music_disc_cat");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_CAT),
                Items.MUSIC_DISC_BLOCKS,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_cat_to_music_disc_blocks");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_BLOCKS),
                Items.MUSIC_DISC_CHIRP,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_blocks_to_music_disc_chirp");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_CHIRP),
                Items.MUSIC_DISC_FAR,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_chirp_to_music_disc_far");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_FAR),
                Items.MUSIC_DISC_MALL,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_far_to_music_disc_mall");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_MALL),
                Items.MUSIC_DISC_MELLOHI,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_mall_to_music_disc_mellohi");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_MELLOHI),
                Items.MUSIC_DISC_STAL,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_mellohi_to_music_disc_stal");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_STAL),
                Items.MUSIC_DISC_STRAD,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_stal_to_music_disc_strad");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_STRAD),
                Items.MUSIC_DISC_WARD,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_strad_to_music_disc_ward");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_WARD),
                Items.MUSIC_DISC_11,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_ward_to_music_disc_11");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_11),
                Items.MUSIC_DISC_WAIT,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_11_to_music_disc_wait");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_WAIT),
                Items.MUSIC_DISC_OTHERSIDE,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_wait_to_music_disc_otherside");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_OTHERSIDE),
                Items.MUSIC_DISC_PIGSTEP,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_otherside_to_music_disc_pigstep");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_PIGSTEP),
                Items.MUSIC_DISC_5,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_pigstep_to_music_disc_5");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_5),
                Items.MUSIC_DISC_RELIC,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_5_to_music_disc_relic");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUSIC_DISC_RELIC),
                Items.MUSIC_DISC_13,
                1
        ).save(consumer,"rune_of_equivalent_music_disc_relic_to_music_disc_13");


        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BAKED_POTATO),
                Items.POTATO,
                1
        ).save(consumer,"return_baked_potato_to_potato");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_CHICKEN),
                Items.CHICKEN,
                1
        ).save(consumer,"return_cooked_chicken_to_chicken");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_BEEF),
                Items.BEEF,
                1
        ).save(consumer,"return_cooked_beef_to_beef");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_MUTTON),
                Items.MUTTON,
                1
        ).save(consumer,"return_cooked_mutton_to_mutton");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_PORKCHOP),
                Items.PORKCHOP,
                1
        ).save(consumer,"return_cooked_porkchop_to_porkchop");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_RABBIT),
                Items.RABBIT,
                1
        ).save(consumer,"return_cooked_rabbit_to_rabbit");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_SALMON),
                Items.SALMON,
                1
        ).save(consumer,"return_cooked_salmon_to_salmon");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COOKED_COD),
                Items.COD,
                1
        ).save(consumer,"return_cooked_cod_to_cod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.DRIED_KELP),
                Items.KELP,
                1
        ).save(consumer,"return_dried_kelp_to_kelp");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.COD),
                Items.TROPICAL_FISH,
                1
        ).save(consumer,"rune_of_equivalent_cod_to_tropical_fish");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.TROPICAL_FISH),
                Items.PUFFERFISH,
                1
        ).save(consumer,"rune_of_equivalent_tropical_fish_to_pufferfish");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PUFFERFISH),
                Items.SALMON,
                1
        ).save(consumer,"rune_of_equivalent_pufferfish_to_salmon");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SALMON),
                Items.COD,
                1
        ).save(consumer,"rune_of_equivalent_salmon_to_cod");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BEEF),
                Items.PORKCHOP,
                1
        ).save(consumer,"rune_of_equivalent_beef_to_porkchop");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PORKCHOP),
                Items.CHICKEN,
                1
        ).save(consumer,"rune_of_equivalent_porkchop_to_chicken");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.CHICKEN),
                Items.MUTTON,
                1
        ).save(consumer,"rune_of_equivalent_chicken_to_mutton");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.MUTTON),
                Items.RABBIT,
                1
        ).save(consumer,"rune_of_equivalent_mutton_to_rabbit");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.RABBIT),
                Items.BEEF,
                1
        ).save(consumer,"rune_of_equivalent_rabbit_to_beef");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.COOKWARE_SET.get(),1)
                .requires(itemlist.T3_COMBUSTION_RUNE.get())
                .requires(Items.IRON_SWORD)
                .requires(Items.CAULDRON)
                .requires(ItemTags.WOODEN_PRESSURE_PLATES)
                .unlockedBy(getHasName(Items.IRON_SWORD),has(Items.IRON_SWORD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get())
                .pattern("cbc")
                .pattern("bab")
                .pattern("cbc")
                .define('a',Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
                .define('b',itemlist.UNITE_ALLOY_BLOCK.get())
                .define('c',itemlist.T6_PANAKEIA_INGOT_BLOCK.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY_BLOCK.get()),has(itemlist.UNITE_ALLOY_BLOCK.get()))
                .save(consumer,"unite_alloy_upgrade_smithing_template_crafting");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get(),2)
                .pattern("cac")
                .pattern("cbc")
                .pattern("ccc")
                .define('a',itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get())
                .define('b',itemlist.UNITE_ALLOY.get())
                .define('c',itemlist.T6_PANAKEIA_INGOT.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_upgrade_smithing_template_increase");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_PICKAXE.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_PICKAXE.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_pickaxe_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_AXE.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_AXE.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_axe_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_SHOVEL.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_SHOVEL.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_shovel_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_HOE.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_HOE.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_hoe_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_SWORD.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_SWORD.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_sword_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_HELMET.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_HELMET.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_helmet_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_CHESTPLATE.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_CHESTPLATE.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_chestplate_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_LEGGINGS.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_LEGGINGS.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_leggings_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.T6_PANAKEIA_BOOTS.get()),
                Ingredient.of(itemlist.UNITE_ALLOY.get()),
                RecipeCategory.TOOLS,itemlist.UNITE_ALLOY_BOOTS.get())
                .unlocks(getHasName(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()),has(itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get()))
                .save(consumer,"unite_alloy_boots_upgrade");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get())
                .pattern("cbc")
                .pattern("bab")
                .pattern("cbc")
                .define('a',itemlist.UNITE_ALLOY_SMITHING_TEMPLATE.get())
                .define('b',itemlist.SINGULARITY_INGOT.get())
                .define('c',itemlist.IMITATED_BEDROCK.get())
                .unlockedBy(getHasName(itemlist.SINGULARITY_INGOT.get()),has(itemlist.SINGULARITY_INGOT.get()))
                .save(consumer,"singularity_ingot_upgrade_smithing_template_crafting");

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),
                        Ingredient.of(itemlist.ANGEL_RING.get()),
                        Ingredient.of(itemlist.SINGULARITY_INGOT.get()),
                        RecipeCategory.TOOLS,itemlist.CROWN_OF_THE_SERAPH.get())
                .unlocks(getHasName(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),has(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()))
                .save(consumer,"crown_of_the_seraph_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.CRIMSON_PLATE_OF_JUSTICE.get()),
                Ingredient.of(itemlist.SINGULARITY_INGOT.get()),
                RecipeCategory.TOOLS,itemlist.JUDICIAL_CARAPACE.get())
                .unlocks(getHasName(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),has(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()))
                .save(consumer,"judicial_carapace_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.LEGGINGS_OF_SAGA.get()),
                Ingredient.of(itemlist.SINGULARITY_INGOT.get()),
                RecipeCategory.TOOLS,itemlist.GAIT_OF_INSIGHT.get())
                .unlocks(getHasName(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),has(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()))
                .save(consumer,"gait_of_insight_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.MARCH_OF_THE_RULER.get()),
                Ingredient.of(itemlist.SINGULARITY_INGOT.get()),
                RecipeCategory.TOOLS,itemlist.BOOTS_OF_AETHERLIGHT.get())
                .unlocks(getHasName(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),has(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()))
                .save(consumer,"boots_of_aetherlight_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),
                Ingredient.of(itemlist.BLADE_OF_VALOR.get()),
                Ingredient.of(itemlist.SINGULARITY_INGOT.get()),
                RecipeCategory.TOOLS,itemlist.SEVEN_SWORDS_OF_TERMINUS.get())
                .unlocks(getHasName(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()),has(itemlist.SINGULARITY_INGOT_SMITHING_TEMPLATE.get()))
                .save(consumer,"seven_swords_of_terminus_upgrade");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(Items.AMETHYST_SHARD),
                Ingredient.of(itemlist.BLANK_RUNE.get()),
                Ingredient.of(itemlist.BASIC_ENGRAVING_INK.get()),
                itemlist.RUNE_OF_EQUIVALENT.get(),
                1
        ).save(consumer,"rune_of_equivalent_recipe");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(Items.DIAMOND_BLOCK),
                Ingredient.of(itemlist.FOUNDATION_OF_RING.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.ALCHEMISTS_RING.get(),
                1
        ).save(consumer,"alchemists_ring_recipe");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.OMG_STEW.get()),
                Ingredient.of(itemlist.FOUNDATION_OF_RING.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.GLUTTONYS_RING.get(),
                1
        ).save(consumer,"gluttonys_ring_recipe");

        new AlchemicalEngraverRecipeBuilder(
                new ResourceLocation("alchemical_power","alchemical_engraver_recipe"),
                Ingredient.of(itemlist.UNITE_ALLOY_PICKAXE.get()),
                Ingredient.of(itemlist.FOUNDATION_OF_RING.get()),
                Ingredient.of(ModTags.Items.JEI_EMPTY_TAG),
                itemlist.GREED_RING.get(),
                1
        ).save(consumer,"greed_ring_recipe");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ROTTEN_FLESH),
                Items.BONE,
                1
        ).save(consumer,"rune_of_equivalent_rotten_flesh_to_bone");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.BONE),
                Items.GUNPOWDER,
                1
        ).save(consumer,"rune_of_equivalent_bone_to_gunpowder");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.GUNPOWDER),
                Items.SPIDER_EYE,
                1
        ).save(consumer,"rune_of_equivalent_gunpowder_to_spider_eye");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SPIDER_EYE),
                Items.SLIME_BALL,
                1
        ).save(consumer,"rune_of_equivalent_spider_eye_to_slime_ball");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SLIME_BALL),
                Items.ROTTEN_FLESH,
                1
        ).save(consumer,"rune_of_equivalent_slime_ball_to_rotten_flesh");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,Items.SLIME_BALL,1)
                .requires(itemlist.COOKWARE_SET.get())
                .requires(ItemTags.create(new ResourceLocation("forge","dough")))
                .requires(Items.GREEN_DYE)
                .unlockedBy(getHasName(itemlist.COOKWARE_SET.get()),has(itemlist.COOKWARE_SET.get()))
                .save(consumer);

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.FEATHER),
                Items.RABBIT_HIDE,
                1
        ).save(consumer,"rune_of_equivalent_feather_to_rabbit_hide");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.RABBIT_HIDE),
                Items.FEATHER,
                1
        ).save(consumer,"rune_of_equivalent_rabbit_hide_to_feather");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.LEATHER),
                Items.RABBIT_FOOT,
                1
        ).save(consumer,"rune_of_equivalent_leather_to_rabbit_foot");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.RABBIT_FOOT),
                Items.RABBIT_HIDE,
                4
        ).save(consumer,"rune_of_equivalent_rabbit_foot_to_rabbit_hide_x4");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.INK_SAC),
                Items.GLOW_INK_SAC,
                1
        ).save(consumer,"rune_of_equivalent_ink_sac_to_glow_ink_sac");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.GLOW_INK_SAC),
                Items.SCUTE,
                1
        ).save(consumer,"rune_of_equivalent_glow_ink_sac_to_scute");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SCUTE),
                Items.PRISMARINE_SHARD,
                1
        ).save(consumer,"rune_of_equivalent_scute_to_prismarine_shard");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PRISMARINE_SHARD),
                Items.PRISMARINE_CRYSTALS,
                1
        ).save(consumer,"rune_of_equivalent_prismarine_shard_to_prismarine_crystals");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PRISMARINE_CRYSTALS),
                Items.INK_SAC,
                1
        ).save(consumer,"rune_of_equivalent_prismarine_crystals_to_ink_sac");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.PHANTOM_MEMBRANE),
                Items.ENDER_PEARL,
                1
        ).save(consumer,"rune_of_equivalent_phantom_membrane_to_ender_pearl");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.ENDER_PEARL),
                Items.PHANTOM_MEMBRANE,
                1
        ).save(consumer,"rune_of_equivalent_ender_pearl_to_phantom_membrane");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.NAUTILUS_SHELL),
                Items.SHULKER_SHELL,
                1
        ).save(consumer,"rune_of_equivalent_nautilus_shell_to_shulker_shell");

        new RuneActivatorRecipeBuilder(
                new ResourceLocation("alchemical_power","rune_activator_recipe"),
                Ingredient.of(itemlist.RUNE_OF_EQUIVALENT.get()),
                Ingredient.of(Items.SHULKER_SHELL),
                Items.NAUTILUS_SHELL,
                1
        ).save(consumer,"rune_of_equivalent_shulker_shell_to_nautilus_shell");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHETREE_PLANKS.get(),4)
                .requires(itemlist.ALCHETREE_LOG.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_LOG.get()),has(itemlist.ALCHETREE_LOG.get()))
                .save(consumer,"alchetree_planks_from_log");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHETREE_PLANKS.get(),4)
                .requires(itemlist.ALCHETREE_WOOD.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_WOOD.get()),has(itemlist.ALCHETREE_WOOD.get()))
                .save(consumer,"alchetree_planks_from_wood");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHETREE_PLANKS.get(),4)
                .requires(itemlist.STRIPPED_ALCHETREE_LOG.get())
                .unlockedBy(getHasName(itemlist.STRIPPED_ALCHETREE_LOG.get()),has(itemlist.STRIPPED_ALCHETREE_LOG.get()))
                .save(consumer,"alchetree_planks_from_stripped_log");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHETREE_PLANKS.get(),4)
                .requires(itemlist.STRIPPED_ALCHETREE_WOOD.get())
                .unlockedBy(getHasName(itemlist.STRIPPED_ALCHETREE_WOOD.get()),has(itemlist.STRIPPED_ALCHETREE_WOOD.get()))
                .save(consumer,"alchetree_planks_from_stripped_wood");


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemlist.ALCHETREE_STAIRS.get(),4)
                .pattern("a  ")
                .pattern("aa ")
                .pattern("aaa")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemlist.ALCHETREE_SLAB.get(),6)
                .pattern("aaa")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemlist.ALCHETREE_FENCE.get(),3)
                .pattern("aba")
                .pattern("aba")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemlist.ALCHETREE_FENCE_GATE.get(),1)
                .pattern("aba")
                .pattern("aba")
                .define('b',itemlist.ALCHETREE_PLANKS.get())
                .define('a',Items.STICK)
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, itemlist.ALCHETREE_DOOR.get(),3)
                .pattern("aa")
                .pattern("aa")
                .pattern("aa")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE,itemlist.ALCHETREE_BUTTON.get(),1)
                .requires(itemlist.ALCHETREE_PLANKS.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, itemlist.ALCHETREE_PRESSURE_PLATE.get(),1)
                .pattern("aa")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemlist.ALCHETREE_TRAPDOOR.get(),2)
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, itemlist.ALCHETREE_WALL.get(),3)
                .pattern("aba")
                .pattern("aba")
                .define('a',itemlist.ALCHETREE_PLANKS.get())
                .define('b',itemlist.ALCHETREE_LOG.get())
                .unlockedBy(getHasName(itemlist.ALCHETREE_PLANKS.get()),has(itemlist.ALCHETREE_PLANKS.get()))
                .save(consumer);



    }
}
