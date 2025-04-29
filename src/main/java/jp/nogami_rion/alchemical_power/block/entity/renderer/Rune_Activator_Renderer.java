package jp.nogami_rion.alchemical_power.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.nogami_rion.alchemical_power.block.entity.Rune_Activator_Entity;
import jp.nogami_rion.alchemical_power.item.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class Rune_Activator_Renderer implements BlockEntityRenderer<Rune_Activator_Entity> {
    public Rune_Activator_Renderer(BlockEntityRendererProvider.Context context){

    }

    @Override
    public void render(Rune_Activator_Entity pActivatorEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pActivatorEntity.getRenderStack();

        pPoseStack.pushPose();
        pPoseStack.translate(0.5f,1.05f,0.5f);
        pPoseStack.scale(0.35f,0.35f,0.35f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(270));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,getLightLevel(pActivatorEntity.getLevel(),pActivatorEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY,pPoseStack,pBuffer,pActivatorEntity.getLevel(),1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos){
        int bLight = level.getBrightness(LightLayer.BLOCK,pos);
        int sLight = level.getBrightness(LightLayer.SKY,pos);
        return LightTexture.pack(bLight,15);
    }
}
