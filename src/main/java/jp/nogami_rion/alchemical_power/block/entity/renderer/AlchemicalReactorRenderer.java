package jp.nogami_rion.alchemical_power.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.nogami_rion.alchemical_power.block.AlchemicalReactorBlock;
import jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.util.AlchemicalMachineShapes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class AlchemicalReactorRenderer implements BlockEntityRenderer<AlchemicalReactorBlockEntity> {
    public AlchemicalReactorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(AlchemicalReactorBlockEntity be, float partialTick, PoseStack pose,
                       MultiBufferSource buffers, int light, int overlay) {
        if (be.getLevel() == null) return;
        pose.pushPose();
        pose.translate(0.5, 0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(180 - be.getBlockState().getValue(AlchemicalReactorBlock.FACING).toYRot()));
        pose.translate(-0.5, 0, -0.5);
        Fluid fluid = ModFluids.LIQUID_PANAKEIA.source.get();
        MachineFluidRenderer.drawTank(pose, buffers, AlchemicalMachineShapes.REACTOR_FLUID,
                be.getFluidAmount(), be.getTankCapacity(), fluid,
                IClientFluidTypeExtensions.of(fluid).getTintColor(new FluidStack(fluid, 1)), light);
        pose.popPose();
    }
}
