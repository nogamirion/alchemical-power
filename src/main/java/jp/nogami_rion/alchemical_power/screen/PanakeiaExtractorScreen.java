package jp.nogami_rion.alchemical_power.screen;

import static jp.nogami_rion.alchemical_power.screen.PanakeiaExtractorLayout.*;

import com.mojang.blaze3d.systems.RenderSystem;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.entity.PanakeiaExtractorBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PanakeiaExtractorScreen extends AbstractContainerScreen<PanakeiaExtractorMenu> {
    @Override
    protected java.util.List<Component> getTooltipFromContainerItem(net.minecraft.world.item.ItemStack stack) {
        var tooltip = new java.util.ArrayList<>(UpgradeLockTooltip.append(super.getTooltipFromContainerItem(stack), hoveredSlot));
        if (hoveredSlot != menu.getSlot(0)) return tooltip;
        int required = PanakeiaExtractorBlockEntity.PANAKEIA_OUTPUTS.getOrDefault(stack.getItem(), 0);
        if (required <= 0) return tooltip;
        int capacity = Math.min(menu.getMaxWater(), menu.getMaxOutputFluid());
        if (required > capacity) {
            tooltip.add(Component.translatable("gui.alchemical_power.extractor.tank_too_small").withStyle(ChatFormatting.RED));
            tooltip.add(Component.translatable("gui.alchemical_power.extractor.tank_capacity",
                    String.format(java.util.Locale.ROOT, "%,d", required),
                    String.format(java.util.Locale.ROOT, "%,d", capacity)).withStyle(ChatFormatting.GRAY));
        } else if (menu.getActiveOutput() == 0 && required > menu.getMaxOutputFluid() - menu.getOutputFluid()) {
            tooltip.add(Component.translatable("gui.alchemical_power.extractor.output_full").withStyle(ChatFormatting.RED));
        }
        return tooltip;
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID, "textures/gui/panakeia_extractor_gui.png");

    public PanakeiaExtractorScreen(PanakeiaExtractorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = WIDTH;
        imageHeight = HEIGHT;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        drawLiquid(graphics, WATER_X, menu.getWater(), menu.getMaxWater(), Fluids.WATER, 0xFF3F76E4);
        var panakeia = ModFluids.LIQUID_PANAKEIA.source.get();
        drawLiquid(graphics, OUTPUT_X, menu.getOutputFluid(), menu.getMaxOutputFluid(), panakeia,
                IClientFluidTypeExtensions.of(panakeia).getTintColor());
        graphics.fill(leftPos + PROGRESS_X, topPos + PROGRESS_Y,
                leftPos + PROGRESS_X + scale(menu.getProgress(), menu.getMaxProgress(), PROGRESS_WIDTH),
                topPos + PROGRESS_Y + PROGRESS_HEIGHT, 0xFFE9CE72);
        graphics.fill(leftPos + ENERGY_X, topPos + ENERGY_Y,
                leftPos + ENERGY_X + scale(menu.getEnergy(), menu.getMaxEnergy(), ENERGY_WIDTH),
                topPos + ENERGY_Y + ENERGY_HEIGHT, 0xFF38E85B);
    }

    private void drawLiquid(GuiGraphics graphics, int localX, int amount, int capacity, Fluid fluid, int tint) {
        int filled = scale(amount, capacity, TANK_HEIGHT);
        if (filled == 0) return;
        var sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
        int x = leftPos + localX, bottom = topPos + TANK_Y + TANK_HEIGHT;
        graphics.enableScissor(x, bottom - filled, x + TANK_WIDTH, bottom);
        RenderSystem.enableBlend();
        graphics.setColor(((tint >> 16) & 255) / 255f, ((tint >> 8) & 255) / 255f,
                (tint & 255) / 255f, ((tint >>> 24) & 255) / 255f);
        for (int offset = 0; offset < filled; offset += 16)
            graphics.blit(x, bottom - offset - 16, 0, 16, 16, sprite);
        graphics.setColor(1, 1, 1, 1);
        graphics.disableScissor();
        RenderSystem.disableBlend();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
        int x = mouseX - leftPos, y = mouseY - topPos;
        if (inside(x, y, WATER_X, TANK_Y, TANK_WIDTH, TANK_HEIGHT))
            amountTooltip(graphics, mouseX, mouseY, "water", menu.getWater(), menu.getMaxWater(), "mB");
        else if (inside(x, y, OUTPUT_X, TANK_Y, TANK_WIDTH, TANK_HEIGHT))
            amountTooltip(graphics, mouseX, mouseY, "liquid_panakeia", menu.getOutputFluid(), menu.getMaxOutputFluid(), "mB");
        else if (inside(x, y, ENERGY_X, ENERGY_Y, ENERGY_WIDTH, ENERGY_HEIGHT))
            amountTooltip(graphics, mouseX, mouseY, "energy", menu.getEnergy(), menu.getMaxEnergy(), "FE");
        else if (inside(x, y, PROGRESS_X, PROGRESS_Y, PROGRESS_WIDTH, PROGRESS_HEIGHT))
            graphics.renderTooltip(font, Component.literal(scale(menu.getProgress(), menu.getMaxProgress(), 100)
                    + "%  |  +" + format(menu.getActiveOutput()) + " mB"), mouseX, mouseY);
    }

    private void amountTooltip(GuiGraphics graphics, int mouseX, int mouseY,
                               String name, int amount, int capacity, String unit) {
        graphics.renderTooltip(font, Component.translatable("tooltip.alchemical_power.panakeia_extractor." + name)
                .append(" " + format(amount) + " / " + format(capacity) + " " + unit), mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(font, title, (imageWidth - font.width(title)) / 2, 8, 0xE9CE72, false);
    }

    private static boolean inside(int x, int y, int left, int top, int width, int height) {
        return x >= left && x < left + width && y >= top && y < top + height;
    }

    private static int scale(int value, int max, int size) {
        return max <= 0 ? 0 : (int) Math.max(0, Math.min(size, (long) value * size / max));
    }

    private static String format(long value) {
        return String.format(java.util.Locale.ROOT, "%,d", value);
    }
}
