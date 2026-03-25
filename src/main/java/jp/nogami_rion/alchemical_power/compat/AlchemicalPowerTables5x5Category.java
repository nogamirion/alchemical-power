package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AlchemicalPowerTables5x5Category extends AlchemicalPowerTablesCategoryBase{

    public AlchemicalPowerTables5x5Category(IGuiHelper helper) {
        super(helper,
                new ResourceLocation("alchemical_power", "textures/gui/hermes_workbench_jei.png"),
                blocklist.HERMES_WORKBENCH.get().asItem().getDefaultInstance(),
                192, 103,
                5,
                10, 7,
                109, 43,
                163, 43);
    }

    @Override
    public RecipeType<AlchemicalPowerTablesRecipe> getRecipeType() {
        return JEI_Alchemical_Power_Plugin.ALCHEMICAL_POWER_TABLES_RECIPE_TYPE_5X5;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.hermes_workbench_re");
    }
}
