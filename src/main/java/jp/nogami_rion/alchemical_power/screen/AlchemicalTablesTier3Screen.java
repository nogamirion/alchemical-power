package jp.nogami_rion.alchemical_power.screen;

import com.mojang.blaze3d.systems.RenderSystem;
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

public class AlchemicalTablesTier3Screen extends AbstractContainerScreen<AlchemicalPowerTables13x13Menu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/transcendental_table_noncircle_gui.png");
    private static final ResourceLocation MAGIC_CIRCLE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/magic_circle.png");
    private static final ResourceLocation MAGIC_CIRCLE_INNER =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/magic_circle_inner.png");
    private static final ResourceLocation MAGIC_CIRCLE_GUI =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/magic_circle_gui.png");
    private static final int FONT_COLOR = new IForgeGuiGraphics(){}.getColorFromFormattingCharacter('f',false);
    private boolean playingAnimation = false;
    private long animationStartTime = 0;
    private static final float ANIMATION_LENGTH = 2400; // アニメーションの持続時間（ミリ秒）
    private float magicCircleRotation = 0f;

    public AlchemicalTablesTier3Screen(AlchemicalPowerTables13x13Menu menu, Inventory playerInventory, Component title){
        super(menu,playerInventory,title);
        this.imageWidth = 428;
        this.imageHeight = 240;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - this.imageWidth) / 2;
        int y = (height - this.imageHeight) / 2;

        guiGraphics.blit(TEXTURE,x,y,0,0,this.imageWidth,this.imageHeight,this.imageWidth,this.imageHeight);
    }

    @Override
    public void render (GuiGraphics guiGraphics,int mouseX,int mouseY,float delta){
        renderBackground(guiGraphics);
        super.render(guiGraphics,mouseX,mouseY,delta);
        renderTooltip(guiGraphics,mouseX,mouseY);

        if (playingAnimation) {
            renderCraftEffect(guiGraphics);
        }

        drawMagicCircle(guiGraphics,delta);

    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.drawCenteredString(this.font, Component.translatable("block.alchemical_power.transcendental_table_re"), 329, 225, FONT_COLOR);
    }

    @Override
    public void containerTick() {
        super.containerTick();

        if(menu.shouldPlayAnimation()){
            playingAnimation = true;
            animationStartTime = System.currentTimeMillis();
            menu.resetPlayAnimation();
        }

        magicCircleRotation += 0.5f;
        if(magicCircleRotation >= 360f) {
            magicCircleRotation -= 360f;
        }
    }

    private void renderCraftEffect(GuiGraphics guiGraphics) {

        // 成果物スロットの中心座標を計算
        Slot resultSlot = menu.getResultSlot();

        int centerX = leftPos + resultSlot.x + 8;
        int centerY = topPos + resultSlot.y + 8;

        //成果物スロットから取り出したときの描画
        long elapsed = System.currentTimeMillis() - animationStartTime;
        float progress = Math.min(1f, elapsed / ANIMATION_LENGTH); // 少し長め

        if (progress >= 1f) {
            playingAnimation = false;
            return;
        }

        float fade = 1f - progress;

        // 緩やかな脈動
        float pulse = 1f + 0.04f * (float)Math.sin(progress * Math.PI * 6);

        float rotationOuter = progress * 40f;   // 外側ゆっくり
        float rotationInner = -progress * 80f;  // 内側やや速い逆回転

        float scaleOuter = 0.20f * pulse;
        float scaleInner = 0.16f * pulse;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(
                com.mojang.blaze3d.platform.GlStateManager.SourceFactor.SRC_ALPHA,
                com.mojang.blaze3d.platform.GlStateManager.DestFactor.ONE
        );

        // ===== 外側 =====
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(centerX, centerY, 300);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rotationOuter));
        guiGraphics.pose().scale(scaleOuter, scaleOuter, 1f);

        guiGraphics.setColor(1f, 1f, 1f, fade);

        guiGraphics.blit(
                MAGIC_CIRCLE,
                -256, -256,
                0, 0,
                512, 512,
                512, 512
        );

        guiGraphics.pose().popPose();

// ===== 内側（通常ブレンド）=====
        RenderSystem.defaultBlendFunc();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(centerX, centerY, 310);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rotationInner));
        guiGraphics.pose().scale(scaleInner, scaleInner, 1f);

        guiGraphics.setColor(1f, 1f, 1f, fade * 0.5f); // ← 透明度半分

        guiGraphics.blit(
                MAGIC_CIRCLE_INNER,
                -256, -256,
                0, 0,
                512, 512,
                512, 512
        );

        guiGraphics.pose().popPose();

        guiGraphics.setColor(1f, 1f, 1f, 1f);
        RenderSystem.defaultBlendFunc();
    }

    private void drawMagicCircle(GuiGraphics guiGraphics,float partialTick){
        // 成果物スロットの中心座標を計算
        Slot resultSlot = menu.getResultSlot();

        int centerX = leftPos + resultSlot.x + 8;
        int centerY = topPos + resultSlot.y + 8;
        float scale = 0.56f;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(centerX, centerY, 300);
        guiGraphics.pose().mulPose(com.mojang.math.Axis.ZP.rotationDegrees(magicCircleRotation + partialTick));
        guiGraphics.pose().scale(scale, scale, 1f);

        guiGraphics.blit(
                MAGIC_CIRCLE_GUI,
                -128, -128,
                0, 0,
                256, 256,
                256, 256
        );

        guiGraphics.pose().popPose();

    }
}
