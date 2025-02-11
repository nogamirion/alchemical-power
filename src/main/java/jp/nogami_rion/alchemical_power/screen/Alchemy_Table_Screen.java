package jp.nogami_rion.alchemical_power.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;

public class Alchemy_Table_Screen extends AbstractContainerScreen<Alchemy_Table_Menu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/alchemy_table_gui.png");
    private static final int FONT_COLOR = new IForgeGuiGraphics(){}.getColorFromFormattingCharacter('f',false);


    public Alchemy_Table_Screen(Alchemy_Table_Menu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE,x,y,0,0,imageWidth,imageHeight,imageWidth,imageHeight);

        renderProgressArrow(guiGraphics,x,y);

    }

    private void renderProgressArrow(GuiGraphics guiGraphics,int x ,int y){

    }

    @Override
    public void render (GuiGraphics guiGraphics,int mouseX,int mouseY,float delta){
        renderBackground(guiGraphics);
        super.render(guiGraphics,mouseX,mouseY,delta);
        renderTooltip(guiGraphics,mouseX,mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

        int iStringX = ((width - imageWidth) / 2) - font.width("block.alchemical_power.alchemy_table")/2;

//        guiGraphics.drawString(this.font, Component.translatable("block.alchemical_power.alchemy_table"), iStringX, 4, FONT_COLOR,false);
        guiGraphics.drawCenteredString(this.font, Component.translatable("block.alchemical_power.alchemy_table"), imageWidth / 2, 4, FONT_COLOR);
    }

}
