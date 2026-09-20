package jp.nogami_rion.alchemical_power.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.nogami_rion.alchemical_power.entity.ThrownSwordEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ThrownSwordRenderer extends EntityRenderer<ThrownSwordEntity> {
    private static final float VANILLA_SWORD_TIP_OFFSET_DEGREES = 225.0F;
    private final ItemRenderer itemRenderer;

    public ThrownSwordRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ThrownSwordEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entity.getYRot() - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(VANILLA_SWORD_TIP_OFFSET_DEGREES));
        poseStack.scale(1.8F, 1.8F, 1.8F);
        ItemStack swordStack = entity.getSwordStack();
        this.itemRenderer.renderStatic(swordStack, ItemDisplayContext.FIXED, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownSwordEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
