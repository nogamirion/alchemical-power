package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.datafix.fixes.ItemIdFix;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ALCHEMY_DUST_STONE = List.of(
            blocklist.STONE_CONTAINING_ALCHEMY_DUST.get());




    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        oreSmelting(consumer,ALCHEMY_DUST_STONE,RecipeCategory.MISC,itemlist.T0_ALCHEMY_DUST.get(),0.25f,200,"t0_alchemy_dust");
        oreBlasting(consumer,ALCHEMY_DUST_STONE,RecipeCategory.MISC,itemlist.T0_ALCHEMY_DUST.get(),0.25f,100,"t0_alchemy_dust");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, itemlist.ALCHEMY_BEGINNERS_KIT.get())
                .pattern("aba")
                .pattern("aca")
                .pattern("aaa")
                .define('a',Items.GLASS.asItem())
                .define('b',ItemTags.WOODEN_BUTTONS)
                .define('c',itemlist.T1_ALCHEMY_DUST.get())
                .unlockedBy(getHasName(itemlist.T1_ALCHEMY_DUST.get()),has(itemlist.T1_ALCHEMY_DUST.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHEMICAL_PROCESSING_COPPER.get())
                .unlockedBy(getHasName(itemlist.ALCHEMICAL_PROCESSING_COPPER.get()),has(itemlist.ALCHEMICAL_PROCESSING_COPPER.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHEMICAL_PROCESSING_IRON.get())
                .unlockedBy(getHasName(itemlist.ALCHEMICAL_PROCESSING_IRON.get()),has(itemlist.ALCHEMICAL_PROCESSING_IRON.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHEMICAL_PROCESSING_GOLD.get())
                .unlockedBy(getHasName(itemlist.ALCHEMICAL_PROCESSING_GOLD.get()),has(itemlist.ALCHEMICAL_PROCESSING_GOLD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHEMICAL_PROCESSING_DIAMOND.get())
                .unlockedBy(getHasName(itemlist.ALCHEMICAL_PROCESSING_DIAMOND.get()),has(itemlist.ALCHEMICAL_PROCESSING_DIAMOND.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHEMICAL_PROCESSING_EMERALD.get())
                .unlockedBy(getHasName(itemlist.ALCHEMICAL_PROCESSING_EMERALD.get()),has(itemlist.ALCHEMICAL_PROCESSING_EMERALD.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.ALCHEMICAL_PROCESSING_NETHERITE.get())
                .unlockedBy(getHasName(itemlist.ALCHEMICAL_PROCESSING_NETHERITE.get()),has(itemlist.ALCHEMICAL_PROCESSING_NETHERITE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, blocklist.UNITE_ALLOY_BLOCK.get())
                .pattern("aaa")
                .pattern("aaa")
                .pattern("aaa")
                .define('a',itemlist.UNITE_ALLOY.get())
                .unlockedBy(getHasName(itemlist.UNITE_ALLOY.get()),has(itemlist.UNITE_ALLOY.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHEMICAL_PROCESSING_COPPER.get(),9)
                .requires(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get())
                .unlockedBy(getHasName(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get()),has(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHEMICAL_PROCESSING_IRON.get(),9)
                .requires(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get())
                .unlockedBy(getHasName(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get()),has(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHEMICAL_PROCESSING_GOLD.get(),9)
                .requires(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get())
                .unlockedBy(getHasName(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get()),has(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHEMICAL_PROCESSING_DIAMOND.get(),9)
                .requires(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get())
                .unlockedBy(getHasName(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get()),has(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHEMICAL_PROCESSING_EMERALD.get(),9)
                .requires(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get())
                .unlockedBy(getHasName(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get()),has(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.ALCHEMICAL_PROCESSING_NETHERITE.get(),9)
                .requires(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get())
                .unlockedBy(getHasName(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get()),has(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,itemlist.UNITE_ALLOY.get(),9)
                .requires(blocklist.UNITE_ALLOY_BLOCK.get())
                .unlockedBy(getHasName(blocklist.UNITE_ALLOY_BLOCK.get()),has(blocklist.UNITE_ALLOY_BLOCK.get()))
                .save(consumer);

    }
}
