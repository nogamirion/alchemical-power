package jp.nogami_rion.alchemical_power.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ConstellationTreasuryScreen extends AbstractContainerScreen<ConstellationTreasuryMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("alchemical_power", "textures/gui/constellation_treasury_gui.png");

    public ConstellationTreasuryScreen(ConstellationTreasuryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = imageHeight = ConstellationTreasuryLayout.SIZE;
    }

    @Override protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
    }

    @Override protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        // Keep either item or Modifier title, including the existing rainbow treatment.
        float scale = Math.min(1.0F, 108.0F / Math.max(1, font.width(title)));
        graphics.pose().pushPose();
        graphics.pose().translate((imageWidth - font.width(title) * scale) / 2.0F, 12, 0);
        graphics.pose().scale(scale, scale, 1);
        jp.nogami_rion.alchemical_power.client.ConstellationTreasuryName.draw(graphics, font, title.getVisualOrderText(), 0, 0, 0);
        graphics.pose().popPose();
    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
