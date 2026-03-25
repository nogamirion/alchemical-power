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

public class AlchemicalTablesTier2Screen extends AbstractContainerScreen<AlchemicalPowerTables5x5Menu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/hermes_workbench_gui.png");
    private static final ResourceLocation MAGIC_CIRCLE =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/magic_circle.png");
    private static final ResourceLocation MAGIC_CIRCLE_INNER =
            new ResourceLocation(Alchemical_power.MODID,"textures/gui/magic_circle_inner.png");
    private static final int FONT_COLOR = new IForgeGuiGraphics(){}.getColorFromFormattingCharacter('f',false);
    private boolean playingAnimation = false;
    private long animationStartTime = 0;
    private static final float ANIMATION_LENGTH = 2400; // アニメーションの持続時間（ミリ秒）

    public AlchemicalTablesTier2Screen(AlchemicalPowerTables5x5Menu menu, Inventory playerInventory, Component title){
        super(menu,playerInventory,title);
        this.imageWidth = 200;
        this.imageHeight = 200;
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
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Font font = Minecraft.getInstance().font;

//        int iStringX = ((width - this.imageWidth) / 2) - font.width("block.alchemical_power.hermes_workbench_re")/2;
//        guiGraphics.drawCenteredString(this.font, Component.translatable("block.alchemical_power.hermes_workbench_re"), this.imageWidth / 2, 4, FONT_COLOR);

        int centeredX = (this.imageWidth - font.width(this.title)) / 2;
        guiGraphics.drawString(font,this.title,centeredX,6,FONT_COLOR,false);
    }

    @Override
    public void containerTick() {
        super.containerTick();

        if(menu.shouldPlayAnimation()){
            playingAnimation = true;
            animationStartTime = System.currentTimeMillis();
            menu.resetPlayAnimation();
        }
    }

    private void renderCraftEffect(GuiGraphics guiGraphics) {
        long elapsed = System.currentTimeMillis() - animationStartTime;
        float progress = Math.min(1f, elapsed / ANIMATION_LENGTH); // 少し長め

        if (progress >= 1f) {
            playingAnimation = false;
            return;
        }

        Slot resultSlot = menu.getResultSlot();

        int centerX = leftPos + resultSlot.x + 8;
        int centerY = topPos + resultSlot.y + 8;

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

        float guiCircleScale = 0.2f;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(centerX, centerY, 310);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(rotationInner));
        guiGraphics.pose().scale(guiCircleScale, guiCircleScale, 1f);

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
}
