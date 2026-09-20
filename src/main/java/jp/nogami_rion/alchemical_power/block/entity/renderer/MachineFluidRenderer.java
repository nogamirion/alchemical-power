package jp.nogami_rion.alchemical_power.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import jp.nogami_rion.alchemical_power.util.AlchemicalMachineShapes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;

/** Shared liquid surface rendering for the two glass machines. */
final class MachineFluidRenderer {
    private MachineFluidRenderer() {}

    static void drawTank(PoseStack pose, MultiBufferSource buffers, AABB tank,
                                 int amount, int capacity, Fluid fluid, int tint, int light) {
        if (amount <= 0 || capacity <= 0) return;
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(IClientFluidTypeExtensions.of(fluid).getStillTexture());
        VertexConsumer out = buffers.getBuffer(RenderType.entityTranslucent(InventoryMenu.BLOCK_ATLAS));
        double x0=tank.minX, x1=tank.maxX, z0=tank.minZ, z1=tank.maxZ;
        double y0=tank.minY, y1=AlchemicalMachineShapes.fluidTop(tank, amount, capacity);
        double height=(y1-y0)*16, width=(x1-x0)*16, depth=(z1-z0)*16;
        quad(pose,out,sprite,tint,light,0,0,-1,width,height,
                new double[][]{{x0,y0,z0},{x0,y1,z0},{x1,y1,z0},{x1,y0,z0}});
        quad(pose,out,sprite,tint,light,0,0,1,width,height,
                new double[][]{{x1,y0,z1},{x1,y1,z1},{x0,y1,z1},{x0,y0,z1}});
        quad(pose,out,sprite,tint,light,1,0,0,depth,height,
                new double[][]{{x1,y0,z0},{x1,y1,z0},{x1,y1,z1},{x1,y0,z1}});
        quad(pose,out,sprite,tint,light,-1,0,0,depth,height,
                new double[][]{{x0,y0,z1},{x0,y1,z1},{x0,y1,z0},{x0,y0,z0}});
        quad(pose,out,sprite,tint,light,0,1,0,width,depth,
                new double[][]{{x0,y1,z0},{x0,y1,z1},{x1,y1,z1},{x1,y1,z0}});
        quad(pose,out,sprite,tint,light,0,-1,0,width,depth,
                new double[][]{{x0,y0,z1},{x0,y0,z0},{x1,y0,z0},{x1,y0,z1}});
    }

    private static void quad(PoseStack pose, VertexConsumer out, TextureAtlasSprite sprite,
                             int tint, int light, float nx, float ny, float nz,
                             double width, double height, double[][] corners) {
        for (int i=0; i<4; i++) {
            double[] v=corners[i];
            out.vertex(pose.last().pose(),(float)v[0],(float)v[1],(float)v[2])
                    .color((tint >> 16)&255,(tint >> 8)&255,tint&255,(tint >>> 24)&255)
                    .uv(sprite.getU(i < 2 ? 0 : width),sprite.getV(i == 0 || i == 3 ? 16 : 16-height))
                    .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light)
                    .normal(pose.last().normal(),nx,ny,nz).endVertex();
        }
    }
}
