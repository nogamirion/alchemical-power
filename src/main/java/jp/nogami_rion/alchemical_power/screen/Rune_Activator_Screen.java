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

public class Rune_Activator_Screen extends AbstractContainerScreen<Rune_Activator_Menu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/rune_activator_gui.png");
    private static final ResourceLocation PROGRESS =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/rune_activator_gui_2.png");
    private static final int FONT_COLOR = new IForgeGuiGraphics(){}.getColorFromFormattingCharacter('f',false);
    private static final int GUIimageWidth = 176;
    private static final int GUIimageHeight = 160;


    public Rune_Activator_Screen(Rune_Activator_Menu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 200;
        this.imageHeight = 200;
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
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        guiGraphics.blit(TEXTURE,x,y,0,0,this.imageWidth,this.imageHeight,this.imageWidth,this.imageHeight);

        renderProgressArrow(guiGraphics,x,y);

    }

    private void renderProgressArrow(GuiGraphics guiGraphics,int x ,int y){
        if(menu.isCrafting()){
            guiGraphics.blit(PROGRESS,x + 99-(menu.getScaledProgress()/2),y + 57-(menu.getScaledProgress()/2),99-(menu.getScaledProgress()/2),57-(menu.getScaledProgress()/2),menu.getScaledProgress(),menu.getScaledProgress(),this.imageWidth,this.imageHeight);
        }

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

//        int iStringX = ((width - this.imageWidth) / 2) - font.width("block.alchemical_power.rune_activator")/2;

        guiGraphics.drawString(this.font, Component.translatable("block.alchemical_power.rune_activator"), 4, 4, FONT_COLOR,false);
//        guiGraphics.drawCenteredString(this.font, Component.translatable("block.alchemical_power.rune_activator"), this.imageWidth / 2, 4, FONT_COLOR);
    }

}
