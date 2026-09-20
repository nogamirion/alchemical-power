package jp.nogami_rion.alchemical_power.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

public class AlchemicalReactorScreen extends AbstractContainerScreen<AlchemicalReactorMenu> {
    public static final int TITLE_AREA_X = 90, TITLE_AREA_Y = 8, TITLE_AREA_WIDTH = 180, TITLE_AREA_HEIGHT = 16;
    private static final ResourceLocation TEXTURE = new ResourceLocation("alchemical_power", "textures/gui/alchemical_reactor_gui.png");
    private static final double CENTER_X = 112.5, CENTER_Y = 88.0;
    // The two transparent arcs run from approximately -80 to +80 degrees.
    private static final double ARC_START = Math.toRadians(-80.023), ARC_SWEEP = Math.toRadians(160.046);

    public AlchemicalReactorScreen(AlchemicalReactorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 360;
        imageHeight = 284;
    }
    @Override protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        drawGauge(graphics, false, menu.value(2), menu.value(3));
        drawGauge(graphics, true, menu.value(4), menu.value(5));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, 360, 360);
    }
    private void drawGauge(GuiGraphics graphics, boolean fluid, int amount, int capacity) {
        int x0 = fluid ? 122 : 57, x1 = fluid ? 169 : 104;
        double ratio = capacity <= 0 ? 0 : Math.max(0, Math.min(1, amount / (double) capacity));
        TextureAtlasSprite sprite = null;
        int tint = 0xffffffff;
        if (fluid) {
            var extension = IClientFluidTypeExtensions.of(ModFluids.LIQUID_PANAKEIA.source.get());
            sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(extension.getStillTexture());
            tint = extension.getTintColor();
        }
        // Draw behind the original PNG, which is the exact mask. Each pixel is filled by
        // its angular distance along the arc, rather than by a rectangular tank's height.
        for (int y = 34; y <= 142; y++) {
            int start = -1;
            graphics.fill(leftPos + x0, topPos + y, leftPos + x1, topPos + y + 1, 0xffffffff);
            for (int x = x0; x <= x1; x++) {
                double angle = Math.atan2(CENTER_Y - y, Math.abs(x - CENTER_X));
                boolean filled = x < x1 && ratio > 0 && (angle - ARC_START) / ARC_SWEEP <= ratio;
                if (filled && start < 0) start = x;
                if (!filled && start >= 0) {
                    if (!fluid) graphics.fill(leftPos + start, topPos + y, leftPos + x, topPos + y + 1, 0xff38e85b);
                    else {
                        graphics.enableScissor(leftPos + start, topPos + y, leftPos + x, topPos + y + 1);
                        graphics.setColor(((tint >> 16) & 255) / 255f, ((tint >> 8) & 255) / 255f, (tint & 255) / 255f, 1);
                        for (int tileX = x0; tileX < x1; tileX += 16)
                            graphics.blit(leftPos + tileX, topPos + (y / 16) * 16, 0, 16, 16, sprite);
                        graphics.setColor(1, 1, 1, 1);
                        graphics.disableScissor();
                    }
                    start = -1;
                }
            }
        }
    }
    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        for (int i = 0; i < AlchemicalReactorBlockEntity.INPUTS; i++) {
            int count = menu.inputCount(i);
            if (count <= 1 || !menu.getSlot(i).hasItem()) continue;
            String text = Integer.toString(count);
            float scale = Math.min(1.0f, 16.0f / font.width(text));
            var slot = menu.getSlot(i);
            graphics.pose().pushPose();
            graphics.pose().translate(leftPos + slot.x + 17, topPos + slot.y + 17 - 8 * scale, 300);
            graphics.pose().scale(scale, scale, 1);
            graphics.drawString(font, text, -font.width(text), 0, 0xffffff, true);
            graphics.pose().popPose();
        }
        drawProcessing(graphics);
        renderTooltip(graphics, mouseX, mouseY);
        int x = mouseX - leftPos, y = mouseY - topPos;
        if (overArc(x, y, false))
            graphics.renderTooltip(font, Component.literal(format(menu.value(2)) + " / " + format(menu.value(3)) + " FE"), mouseX, mouseY);
        else if (overArc(x, y, true))
            graphics.renderTooltip(font, Component.literal(format(menu.value(4)) + " / " + format(menu.value(5)) + " mB"), mouseX, mouseY);
        else if (hoveredSlot != null && hoveredSlot.index == AlchemicalReactorBlockEntity.SAMPLE && !hoveredSlot.hasItem())
            graphics.renderTooltip(font, Component.translatable("gui.alchemical_power.reactor.sample"), mouseX, mouseY);
    }

    @Override protected List<Component> getTooltipFromContainerItem(ItemStack stack) {
        List<Component> tooltip = new ArrayList<>(super.getTooltipFromContainerItem(stack));
        if (hoveredSlot == null || hoveredSlot.index < AlchemicalReactorBlockEntity.UPGRADES
                || hoveredSlot.index >= AlchemicalReactorBlockEntity.SLOTS) return tooltip;

        // Use synchronized menu values so the explanation updates as stored resources change.
        boolean processing = menu.value(1) > 0;
        boolean excessEnergy = hoveredSlot.index == AlchemicalReactorBlockEntity.UPGRADES + 3
                && menu.value(2) > AlchemicalReactorBlockEntity.BASE_ENERGY_CAPACITY;
        boolean excessFluid = hoveredSlot.index == AlchemicalReactorBlockEntity.UPGRADES + 4
                && menu.value(4) > AlchemicalReactorBlockEntity.BASE_TANK_CAPACITY;
        if (!processing && !excessEnergy && !excessFluid) return tooltip;

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("gui.alchemical_power.reactor.locked.title").withStyle(ChatFormatting.RED));
        if (processing)
            tooltip.add(Component.translatable("gui.alchemical_power.reactor.locked.processing").withStyle(ChatFormatting.RED));
        if (excessEnergy) {
            tooltip.add(Component.translatable("gui.alchemical_power.reactor.locked.energy").withStyle(ChatFormatting.RED));
            tooltip.add(Component.translatable("gui.alchemical_power.reactor.locked.energy_limit",
                    format(AlchemicalReactorBlockEntity.BASE_ENERGY_CAPACITY)).withStyle(ChatFormatting.GRAY));
        }
        if (excessFluid) {
            tooltip.add(Component.translatable("gui.alchemical_power.reactor.locked.tank").withStyle(ChatFormatting.RED));
            tooltip.add(Component.translatable("gui.alchemical_power.reactor.locked.tank_limit",
                    format(AlchemicalReactorBlockEntity.BASE_TANK_CAPACITY)).withStyle(ChatFormatting.GRAY));
        }
        return tooltip;
    }
    private boolean overArc(int x, int y, boolean fluid) {
        if (y < 34 || y > 142 || (fluid ? x < 122 || x > 168 : x < 57 || x > 103)) return false;
        double radius = Math.hypot(x - CENTER_X, y - CENTER_Y);
        return radius >= 51 && radius <= 57;
    }
    private void drawProcessing(GuiGraphics graphics) {
        int duration = menu.value(1);
        if (duration <= 0) return;
        double progress = Math.min(1, menu.value(0) / (double) duration);
        graphics.pose().pushPose();
        graphics.pose().translate(leftPos, topPos, 220);
        for (int slot = 0; slot < 12; slot++) {
            ItemStack stack = menu.animationInput(slot);
            if (stack.isEmpty()) continue;
            int count = stack.getCount();
            // One sprite for each consumed item. Stagger arrivals; never animate the surplus stack.
            for (int item = 0; item < count; item++) {
                double start = 0.45 * item / Math.max(1, count - 1);
                double finish = count == 1 ? 1.0 : 0.55 + start;
                if (progress >= finish) continue;
                double t = Math.max(0, (progress - start) / (finish - start));
                double eased = t * t * (3 - 2 * t);
                int[] origin = AlchemicalReactorMenu.INPUT_POSITIONS[slot];
                double x = origin[0] + (AlchemicalReactorMenu.OUTPUT_X - origin[0]) * eased;
                double y = origin[1] + (AlchemicalReactorMenu.OUTPUT_Y - origin[1]) * eased - 24 * Math.sin(Math.PI * t);
                float scale = (float) (1 - 0.7 * Math.pow(t, 4));
                graphics.pose().pushPose();
                graphics.pose().translate(x + 8, y + 8, 0);
                graphics.pose().scale(scale, scale, 1);
                graphics.renderItem(stack, -8, -8);
                graphics.pose().popPose();
            }
        }
        graphics.pose().popPose();
    }
    @Override protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawCenteredString(font, title, TITLE_AREA_X + TITLE_AREA_WIDTH / 2, TITLE_AREA_Y + 2, 0xffdddddd);
    }
    private String format(int value) { return String.format(Locale.ROOT, "%,d", value); }
}
