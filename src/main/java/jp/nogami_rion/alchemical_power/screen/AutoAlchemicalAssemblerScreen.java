package jp.nogami_rion.alchemical_power.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class AutoAlchemicalAssemblerScreen extends AbstractContainerScreen<AutoAlchemicalAssemblerMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("alchemical_power", "textures/gui/auto_alchemical_assembler_gui.png");
    private float scrollProgress = 0.0f;
    private boolean isScrolling = false;

    private static final int TOTAL_ROWS = 13;
    private static final int VISIBLE_ROWS = 3;
    private static final int MAX_SCROLL = TOTAL_ROWS - VISIBLE_ROWS;



    public AutoAlchemicalAssemblerScreen(AutoAlchemicalAssemblerMenu menu, Inventory playerInv, Component title){
        super(menu, playerInv, title);
        this.imageWidth = 256;
        this.imageHeight = 200;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics,
                            float partialTick,
                            int mouseX,
                            int mouseY) {

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE,
                x, y,
                0, 0,
                imageWidth,
                imageHeight);

        // ===== スクロールバー =====
        int barY = y + 20;
        int barHeight = 52 - 8;
        int handleY = (int)(barY + scrollProgress * barHeight);
        guiGraphics.blit(TEXTURE,
                x + 245,
                handleY,
                251, 201,
                4, 8);

        // ===== progressバー =====

        int progress = menu.getProgress();
        int max = menu.getMaxProgress();

        if(max > 0 && progress > 0){
            int barWidth = progress * 99 / max;

            guiGraphics.blit(TEXTURE,
                    x + 10,
                    y + 87,
                    0, 201,
                    barWidth,
                    10);
        }

        // ===== energyバー =====
        int energy = menu.getEnergyStored();
        int maxEnergy = menu.getMaxEnergyStored();

        if(maxEnergy > 0){
            int energyHeight = energy * 52 / maxEnergy;

            guiGraphics.blit(TEXTURE,
                    x + 181,
                    y + 114 + (52 - energyHeight),
                    241, 202 + (52 - energyHeight),
                    7,
                    energyHeight);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics,
                       int mouseX,
                       int mouseY,
                       float partialTick) {

        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private boolean isMouseOverScrollBar(double mouseX, double mouseY){
        int x = (width - imageWidth) /2;
        int y = (height - imageHeight) /2;

        int barX = x + 242;
        int barY = y + 18;
        int barHeight = 54;

        return mouseX >= barX && mouseX <= barX + 12 && mouseY >= barY && mouseY <= barY + barHeight;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button){
        if(button == 0 && isMouseOverScrollBar(mouseX, mouseY)){
            isScrolling = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button){
        isScrolling = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isScrolling) {
            int y = (height - imageHeight) / 2 + 18;
            int berHeight = 54;

            scrollProgress = (float) ((mouseY - y) / berHeight);
            scrollProgress = Mth.clamp(scrollProgress, 0.0f, 1.0f);

            int row = (int) (scrollProgress * MAX_SCROLL + 0.5);
            minecraft.gameMode.handleInventoryButtonClick(menu.containerId,row);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX,double mouseY, double delta){
        scrollProgress -= (float) (delta / MAX_SCROLL);
        scrollProgress = Mth.clamp(scrollProgress, 0.0f, 1.0f);

        int row = (int) (scrollProgress * MAX_SCROLL + 0.5);
        minecraft.gameMode.handleInventoryButtonClick(menu.containerId,row);
        return true;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int centeredX = (this.imageWidth - font.width(this.title)) / 2;
        guiGraphics.drawString(font,this.title,centeredX,6,0x404040,false);

        // energy表示
        int energy = menu.getEnergyStored();
        int maxEnergy = menu.getMaxEnergyStored();
        int usage = menu.getEnergyPerTick();

        String energyText1 = formatFE(energy);
        String energyText2 = "/" + formatFE(maxEnergy) + "FE";
        String usageText = formatFE(usage) + "FE/t";

        guiGraphics.drawString(font, energyText1, 195, 115, 0x25FF57, false);
        guiGraphics.drawString(font, energyText2, 195, 125, 0x25FF57, false);
        guiGraphics.drawString(font, usageText, 195, 140, 0x25FF57, false);

    }

    private String formatFE(int value) {
        if (value >= 1_000_000)
            return String.format("%.1fM", value / 1_000_000.0);
        if (value >= 1_000)
            return String.format("%.1fk", value / 1_000.0);
        return Integer.toString(value);
    }


}
