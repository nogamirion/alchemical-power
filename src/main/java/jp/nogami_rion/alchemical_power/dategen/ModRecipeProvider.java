package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
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


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.BASIC_ENGRAVING_INK.get(),1)
                .requires(getWaterBottleIngredient())
                .requires(Items.INK_SAC)
                .requires(itemlist.T2_PANAKEIA.get())
                .requires(Items.FEATHER)
                .unlockedBy(getHasName(itemlist.T2_PANAKEIA.get()),has(itemlist.T2_PANAKEIA.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ADVANCED_ENGRAVING_INK.get(),1)
                .requires(getWaterBottleIngredient())
                .requires(Items.INK_SAC)
                .requires(itemlist.T4_PANAKEIA.get())
                .requires(Items.FEATHER)
                .unlockedBy(getHasName(itemlist.T4_PANAKEIA.get()),has(itemlist.T4_PANAKEIA.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ELITE_ENGRAVING_INK.get(),1)
                .requires(getWaterBottleIngredient())
                .requires(Items.INK_SAC)
                .requires(itemlist.T6_PANAKEIA.get())
                .requires(Items.FEATHER)
                .unlockedBy(getHasName(itemlist.T6_PANAKEIA.get()),has(itemlist.T6_PANAKEIA.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ULTIMATE_ENGRAVING_INK.get(),1)
                .requires(getWaterBottleIngredient())
                .requires(Items.INK_SAC)
                .requires(itemlist.T7_PANAKEIA.get())
                .requires(Items.FEATHER)
                .unlockedBy(getHasName(itemlist.T7_PANAKEIA.get()),has(itemlist.T7_PANAKEIA.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.UNITE_ALLOY_PICKAXE.get())
                .pattern("aaa")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.UNITE_ALLOY.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.UNITE_ALLOY_AXE.get())
                .pattern("aa ")
                .pattern("ab ")
                .pattern(" b ")
                .define('a',itemlist.UNITE_ALLOY.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.UNITE_ALLOY_SHOVEL.get())
                .pattern(" a ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.UNITE_ALLOY.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.UNITE_ALLOY_SWORD.get())
                .pattern(" a ")
                .pattern(" a ")
                .pattern(" b ")
                .define('a',itemlist.UNITE_ALLOY.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.UNITE_ALLOY_HOE.get())
                .pattern("aa ")
                .pattern(" b ")
                .pattern(" b ")
                .define('a',itemlist.UNITE_ALLOY.get())
                .define('b',Items.STICK)
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.UNITE_ALLOY_HELMET.get())
                .pattern("aaa")
                .pattern("a a")
                .define('a',itemlist.UNITE_ALLOY.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.UNITE_ALLOY_CHESTPLATE.get())
                .pattern("a a")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.UNITE_ALLOY.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.UNITE_ALLOY_LEGGINGS.get())
                .pattern("aaa")
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.UNITE_ALLOY.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT,itemlist.UNITE_ALLOY_BOOTS.get())
                .pattern("a a")
                .pattern("a a")
                .define('a',itemlist.UNITE_ALLOY.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
                .save(consumer);




    }


    private Ingredient getWaterBottleIngredient() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("Potion", "minecraft:water");
        return Ingredient.of(new ItemStack(Items.POTION, 1, nbt));
    }
}
