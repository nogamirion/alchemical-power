package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AlchemicalPowerTables13x13Category extends AlchemicalPowerTablesCategoryBase{

    public AlchemicalPowerTables13x13Category(IGuiHelper helper) {
        super(helper,
                new ResourceLocation("alchemical_power", "textures/gui/transcendental_table_jei.png"),
                blocklist.TRANSCENDENTAL_TABLE.get().asItem().getDefaultInstance(),
                247, 256,
                13,
                7, 4,
                88, 238,
                142, 238);
    }

    @Override
    public RecipeType<AlchemicalPowerTablesRecipe> getRecipeType() {
        return JEI_Alchemical_Power_Plugin.ALCHEMICAL_POWER_TABLES_RECIPE_TYPE_13X13;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.transcendental_table_re");
    }
}
