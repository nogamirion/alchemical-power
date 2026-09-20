package jp.nogami_rion.alchemical_power.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.client.ForgeRenderTypes;

/** Per-vertex alpha: no global shader color leaks into other entities. */
final class ArsenalFadeBuffer implements MultiBufferSource {
    private final MultiBufferSource buffers;
    private final float alpha;
    ArsenalFadeBuffer(MultiBufferSource buffers, float alpha) { this.buffers = buffers; this.alpha = alpha; }

    @Override public VertexConsumer getBuffer(RenderType type) {
        // Glint has no vertex alpha channel; suppress it until materialization is complete.
        boolean glint = type == RenderType.glint() || type == RenderType.glintDirect()
                || type == RenderType.entityGlint() || type == RenderType.entityGlintDirect()
                || type == RenderType.glintTranslucent() || type == RenderType.armorGlint() || type == RenderType.armorEntityGlint();
        if (glint) return new AlphaConsumer(null, alpha);
        // Baked weapon models share the block atlas. Leave custom renderer textures intact.
        if (type == Sheets.solidBlockSheet() || type == Sheets.cutoutBlockSheet()
                || type == Sheets.translucentCullBlockSheet()
                || type == ForgeRenderTypes.ITEM_LAYERED_SOLID.get()
                || type == ForgeRenderTypes.ITEM_LAYERED_CUTOUT.get()
                || type == ForgeRenderTypes.ITEM_LAYERED_CUTOUT_MIPPED.get()) type = Sheets.translucentItemSheet();
        return new AlphaConsumer(buffers.getBuffer(type), alpha);
    }

    private record AlphaConsumer(VertexConsumer delegate, float alpha) implements VertexConsumer {
        @Override public VertexConsumer vertex(double x, double y, double z) { if (delegate != null) delegate.vertex(x, y, z); return this; }
        @Override public VertexConsumer color(int r, int g, int b, int a) { if (delegate != null) delegate.color(r, g, b, Math.round(a * alpha)); return this; }
        @Override public VertexConsumer uv(float u, float v) { if (delegate != null) delegate.uv(u, v); return this; }
        @Override public VertexConsumer overlayCoords(int u, int v) { if (delegate != null) delegate.overlayCoords(u, v); return this; }
        @Override public VertexConsumer uv2(int u, int v) { if (delegate != null) delegate.uv2(u, v); return this; }
        @Override public VertexConsumer normal(float x, float y, float z) { if (delegate != null) delegate.normal(x, y, z); return this; }
        @Override public void endVertex() { if (delegate != null) delegate.endVertex(); }
        @Override public void defaultColor(int r, int g, int b, int a) { if (delegate != null) delegate.defaultColor(r, g, b, Math.round(a * alpha)); }
        @Override public void unsetDefaultColor() { if (delegate != null) delegate.unsetDefaultColor(); }
    }
}
