package jp.nogami_rion.alchemical_power.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.nogami_rion.alchemical_power.entity.SummonedArsenalWeapon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SummonedArsenalWeaponRenderer extends EntityRenderer<SummonedArsenalWeapon> {
    private final ItemRenderer items;
    public SummonedArsenalWeaponRenderer(EntityRendererProvider.Context context) {
        super(context);
        items = context.getItemRenderer();
    }
    @Override public void render(SummonedArsenalWeapon entity, float yaw, float partialTick, PoseStack pose,
                                 MultiBufferSource buffer, int light) {
        pose.pushPose();
        Vec3 visual = entity.renderPosition(partialTick);
        pose.translate(visual.x - Mth.lerp(partialTick, entity.xo, entity.getX()),
                visual.y - Mth.lerp(partialTick, entity.yo, entity.getY()),
                visual.z - Mth.lerp(partialTick, entity.zo, entity.getZ()));
        Vec3 direction = entity.facingDirection(partialTick);
        float facing = (float) Math.toDegrees(Math.atan2(direction.x, direction.z));
        float pitch = (float) Math.toDegrees(Math.atan2(direction.y, direction.horizontalDistance()));
        pose.mulPose(Axis.YP.rotationDegrees(facing - 90));
        pose.mulPose(Axis.ZP.rotationDegrees(pitch + 225));
        pose.scale(1.8F, 1.8F, 1.8F);
        float alpha = entity.getOpacity(partialTick);
        if (alpha > 0) items.renderStatic(entity.getWeapon(), ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY,
                pose, alpha < 1 ? new ArsenalFadeBuffer(buffer, alpha) : buffer, entity.level(), entity.getId());
        pose.popPose();
        super.render(entity, yaw, partialTick, pose, buffer, light);
    }
    @Override public ResourceLocation getTextureLocation(SummonedArsenalWeapon entity) { return TextureAtlas.LOCATION_BLOCKS; }
}
