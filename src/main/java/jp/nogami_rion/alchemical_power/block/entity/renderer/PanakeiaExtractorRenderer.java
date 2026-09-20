package jp.nogami_rion.alchemical_power.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import jp.nogami_rion.alchemical_power.block.PanakeiaExtractorBlock;
import jp.nogami_rion.alchemical_power.block.entity.PanakeiaExtractorBlockEntity;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.util.AlchemicalMachineShapes;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class PanakeiaExtractorRenderer implements BlockEntityRenderer<PanakeiaExtractorBlockEntity> {
    public PanakeiaExtractorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PanakeiaExtractorBlockEntity be, float partialTick, PoseStack pose,
                       MultiBufferSource buffers, int light, int overlay) {
        if (be.getLevel() == null) return;
        pose.pushPose();
        pose.translate(0.5, 0, 0.5);
        pose.mulPose(Axis.YP.rotationDegrees(180 - be.getBlockState().getValue(PanakeiaExtractorBlock.FACING).toYRot()));
        pose.translate(-0.5, 0, -0.5);
        MachineFluidRenderer.drawTank(pose, buffers, AlchemicalMachineShapes.EXTRACTOR_WATER,
                be.getWaterAmount(), be.getWaterCapacity(), Fluids.WATER,
                0xFF000000 | BiomeColors.getAverageWaterColor(be.getLevel(), be.getBlockPos()), light);
        Fluid panakeia = ModFluids.LIQUID_PANAKEIA.source.get();
        MachineFluidRenderer.drawTank(pose, buffers, AlchemicalMachineShapes.EXTRACTOR_OUTPUT,
                be.getOutputAmount(), be.getOutputCapacity(), panakeia,
                IClientFluidTypeExtensions.of(panakeia).getTintColor(new FluidStack(panakeia, 1)), light);
        pose.popPose();
    }

}
