package jp.nogami_rion.alchemical_power.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.client.extensions.IForgeGuiGraphics;

public class Rune_Activator_Screen extends AbstractContainerScreen<Rune_Activator_Menu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/rune_activator_gui.png");
    private static final ResourceLocation MAGIC_CIRCLE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/magic_circle.png");
    private static final int FONT_COLOR = new IForgeGuiGraphics(){}.getColorFromFormattingCharacter('f',false);
    private static final int GUIimageWidth = 176;
    private static final int GUIimageHeight = 160;
    private float rotation = 0.0f;
    private float rotationSpeed = 0.0f;


    public Rune_Activator_Screen(Rune_Activator_Menu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 200;
        this.imageHeight = 200;
    }

//    @Override
//    protected void containerTick() {
//        super.containerTick();
//        rotation += 1.0f; // 回転速度を調整
//    }

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


        Slot outputSlot = menu.getOutputSlot();
        int centerX = this.leftPos + outputSlot.x + 8; // スロットの中心X座標
        int centerY = this.topPos + outputSlot.y + 8; // スロットの中心Y座標
        float scale = 0.23F; // テクスチャの縮小率
        float halfScaled = 256;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        poseStack.translate(centerX, centerY, 0); // スロットの中心に移動
        if(menu.isCrafting()){
            float progressRatio = (float) menu.getProgress() / (float) menu.getMaxProgress();
            float minSpeed = 1.0f;
            float maxSpeed = 10.0f;
            float targetSpeed = minSpeed + (maxSpeed - minSpeed) * (progressRatio * progressRatio); // 進行度に応じて回転速度を加速
            if(progressRatio > 0.9f){
                targetSpeed *= 2.0f;
            }
            rotationSpeed = targetSpeed;
        } else {
            rotationSpeed *= 0.9f;
        }
        rotation += rotationSpeed;

        poseStack.mulPose(Axis.ZP.rotationDegrees(rotation));
        poseStack.scale(scale, scale, 1.0F); // テクスチャを縮小
        poseStack.translate(-halfScaled, -halfScaled, 0); // テクスチャの中心に移動

        RenderSystem.setShaderTexture(0, MAGIC_CIRCLE);
        guiGraphics.blit(MAGIC_CIRCLE,0,0,0,0,512,512,512,512);
        poseStack.popPose();
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
