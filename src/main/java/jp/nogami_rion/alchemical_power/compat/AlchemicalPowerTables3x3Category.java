package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AlchemicalPowerTables3x3Category extends AlchemicalPowerTablesCategoryBase{

    public AlchemicalPowerTables3x3Category(IGuiHelper helper) {
        super(helper,
                new ResourceLocation("alchemical_power", "textures/gui/alchemy_table_jei.png"),
                blocklist.ALCHEMY_TABLE_RE.get().asItem().getDefaultInstance(),
                160, 61,
                3,
                8, 5,
                80, 23,
                137, 23);
    }

    @Override
    public RecipeType<AlchemicalPowerTablesRecipe> getRecipeType() {
        return JEI_Alchemical_Power_Plugin.ALCHEMICAL_POWER_TABLES_RECIPE_TYPE_3X3;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.alchemy_table_re");
    }


}
