package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.entity.PanakeiaExtractorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PanakeiaExtractorCategory implements IRecipeCategory<PanakeiaExtractorCategory.Extraction> {
    public static final RecipeType<Extraction> TYPE = RecipeType.create(
            Alchemical_power.MODID, "panakeia_extractor", Extraction.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public record Extraction(Item input, int amount) {}

    public static List<Extraction> getRecipes() {
        return PanakeiaExtractorBlockEntity.PANAKEIA_OUTPUTS.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> new Extraction(entry.getKey(), entry.getValue()))
                .toList();
    }

    public PanakeiaExtractorCategory(IGuiHelper helper) {
        background = helper.createBlankDrawable(220, 84);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
                new ItemStack(blocklist.PANAKEIA_EXTRACTOR.get()));
        slot = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<Extraction> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() {
        return Component.translatable("block.alchemical_power.panakeia_extractor");
    }

    @Override
    public IDrawable getBackground() { return background; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, Extraction recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 24)
                .setBackground(slot, -1, -1).addItemStack(new ItemStack(recipe.input()));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 14)
                .addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(Fluids.WATER, recipe.amount()))
                .setFluidRenderer(recipe.amount(), false, 16, 32);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 174, 14)
                .addIngredient(ForgeTypes.FLUID_STACK,
                        new FluidStack(ModFluids.LIQUID_PANAKEIA.source.get(), recipe.amount()))
                .setFluidRenderer(recipe.amount(), false, 16, 32);
    }

    @Override
    public void draw(Extraction recipe, IRecipeSlotsView slots, GuiGraphics graphics,
                     double mouseX, double mouseY) {
        var font = Minecraft.getInstance().font;
        int color = 0xff404040;
        graphics.drawString(font, Component.translatable("gui.alchemical_power.reactor.inputs"), 4, 2, color, false);
        graphics.drawString(font, Component.translatable("gui.alchemical_power.reactor.output"), 156, 2, color, false);
        graphics.drawString(font, "+", 38, 28, color, false);
        graphics.drawString(font, "->", 112, 28, color, false);
        String amount = String.format(Locale.ROOT, "%,d mB", recipe.amount());
        graphics.drawString(font, amount, 74 - font.width(amount) / 2, 50, color, false);
        graphics.drawString(font, amount, 176 - font.width(amount) / 2, 50, color, false);
        graphics.drawString(font, String.format(Locale.ROOT, "%.2f s / %,d FE",
                PanakeiaExtractorBlockEntity.PROCESS_TIME / 20.0, recipe.amount()), 4, 70, color, false);
    }
}
