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

import java.util.HashMap;

public class HermesWorkbench_Screen extends AbstractContainerScreen<Hermes_Workbench_Menu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/hermes_workbench_gui.png");
    private static final ResourceLocation PROGRESS =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/hermes_workbench_gui2.png");
    private static final int FONT_COLOR = new IForgeGuiGraphics(){}.getColorFromFormattingCharacter('f',false);

    public HermesWorkbench_Screen(Hermes_Workbench_Menu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 200;
        this.imageHeight = 200;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 20000;
        this.titleLabelY = 20000;
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
            guiGraphics.blit(PROGRESS,x + 136,y + 57,136,57,menu.getScaledProgress(),13,this.imageWidth,this.imageHeight);
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

        int iStringX = ((width - this.imageWidth) / 2) - font.width("block.alchemical_power.hermes_workbench")/2;

//        guiGraphics.drawString(this.font, Component.translatable("block.alchemical_power.alchemy_table"), iStringX, 4, FONT_COLOR,false);
        guiGraphics.drawCenteredString(this.font, Component.translatable("block.alchemical_power.hermes_workbench"), this.imageWidth / 2, 4, FONT_COLOR);
    }

}
