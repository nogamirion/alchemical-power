package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalReactorRecipe;
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
import net.minecraft.world.item.ItemStack;
import java.util.Arrays;
import java.util.Locale;

public class AlchemicalReactorCategory implements IRecipeCategory<AlchemicalReactorRecipe> {
    public static final RecipeType<AlchemicalReactorRecipe> TYPE = RecipeType.create("alchemical_power", "alchemical_reactor", AlchemicalReactorRecipe.class);
    private final IDrawable background, icon, slot;
    public AlchemicalReactorCategory(IGuiHelper helper) {
        background = helper.createBlankDrawable(220, 104);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(blocklist.ALCHEMICAL_REACTOR.get()));
        slot = helper.getSlotDrawable();
    }
    @Override public RecipeType<AlchemicalReactorRecipe> getRecipeType() { return TYPE; }
    @Override public Component getTitle() { return Component.translatable("block.alchemical_power.alchemical_reactor"); }
    @Override public IDrawable getBackground() { return background; }
    @Override public IDrawable getIcon() { return icon; }
    @Override public void setRecipe(IRecipeLayoutBuilder builder, AlchemicalReactorRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.inputs().size(); i++) {
            var input = recipe.inputs().get(i);
            var stacks = Arrays.stream(input.ingredient().getItems()).map(stack -> {
                ItemStack copy = stack.copy(); copy.setCount(input.count()); return copy;
            }).toList();
            builder.addSlot(RecipeIngredientRole.INPUT, 5 + (i % 4) * 18, 12 + (i / 4) * 18)
                    .setSlotName("material_" + i)
                    .setBackground(slot, -1, -1).addItemStacks(stacks);
        }
        if (!recipe.fluid().isEmpty()) builder.addSlot(RecipeIngredientRole.INPUT, 86, 12)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.fluid())
                .setFluidRenderer(recipe.fluid().getAmount(), false, 16, 52)
                .addTooltipCallback((view, tooltip) -> {
                    if (recipe.fluidAmount() > Integer.MAX_VALUE) {
                        tooltip.clear();
                        tooltip.add(recipe.fluid().getDisplayName());
                        tooltip.add(Component.literal(String.format(Locale.ROOT, "%,d mB", recipe.fluidAmount())));
                    }
                });
        if (!recipe.sample().isEmpty()) builder.addSlot(RecipeIngredientRole.CATALYST, 126, 28)
                .setBackground(slot, -1, -1).addItemStack(recipe.sample())
                .addTooltipCallback((view, tooltip) -> tooltip.add(Component.translatable("gui.alchemical_power.reactor.sample")));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 195, 28).setBackground(slot, -1, -1)
                .addItemStack(recipe.getResultItem(null));
    }
    @Override public void draw(AlchemicalReactorRecipe recipe, IRecipeSlotsView slots, GuiGraphics graphics, double mouseX, double mouseY) {
        var font = Minecraft.getInstance().font;
        graphics.drawString(font, Component.translatable("gui.alchemical_power.reactor.inputs"), 4, 2, 0xff404040, false);
        graphics.drawCenteredString(font, Component.translatable("gui.alchemical_power.reactor.sample_label"), 134, 14, 0xff404040);
        graphics.drawCenteredString(font, Component.translatable("gui.alchemical_power.reactor.output"), 203, 14, 0xff404040);
        if (recipe.sample().isEmpty()) graphics.drawString(font, "—", 130, 33, 0xff404040, false);
        String fluidText = String.format(Locale.ROOT, "%,d mB", recipe.fluidAmount());
        float fluidScale = Math.min(1.0f, 106.0f / font.width(fluidText));
        graphics.pose().pushPose();
        graphics.pose().translate(110, 53, 0);
        graphics.pose().scale(fluidScale, fluidScale, 1);
        graphics.drawString(font, fluidText, 0, 0, 0xff404040, false);
        graphics.pose().popPose();
        Component requirement = Component.translatable("gui.alchemical_power.reactor.required_upgrade", requiredUpgradeName(recipe.toolTier()));
        graphics.drawWordWrap(font, requirement, 4, 68, 212, 0xff404040);
        graphics.drawString(font, String.format(Locale.ROOT, "%.2f s  /  %,d FE", recipe.time() / 20.0, recipe.energy()),
                4, 92, 0xff404040, false);
    }

    private Component requiredUpgradeName(int tier) {
        return switch (tier) {
            case 0 -> Component.translatable("gui.alchemical_power.reactor.no_upgrade");
            case 1 -> itemlist.CRAFTING_TOOL_UPGRADE_T2.get().getDescription();
            case 2 -> itemlist.CRAFTING_TOOL_UPGRADE_T3.get().getDescription();
            case 3 -> itemlist.CRAFTING_TOOL_UPGRADE_T4.get().getDescription();
            case 4 -> itemlist.CRAFTING_TOOL_UPGRADE_T5.get().getDescription();
            default -> throw new IllegalArgumentException("Invalid reactor tool tier: " + tier);
        };
    }
}
