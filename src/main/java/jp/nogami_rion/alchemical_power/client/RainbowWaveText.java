package jp.nogami_rion.alchemical_power.client;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import jp.nogami_rion.alchemical_power.util.RainbowText;
import org.joml.Matrix4f;

/** Shared name effect for tooltips, screens, and the selected-item HUD. */
public final class RainbowWaveText {
    private RainbowWaveText() {}

    private interface GlyphRenderer {
        void draw(FormattedCharSequence glyph, float x, float y, int color);
    }

    private static float glyphs(Font font, FormattedCharSequence text, int offset, int alpha, GlyphRenderer renderer) {
        return glyphs(font, text, offset, alpha, false, renderer);
    }

    private static float glyphs(Font font, FormattedCharSequence text, int offset, int alpha, boolean markedOnly, GlyphRenderer renderer) {
        double seconds = Util.getMillis() / 1000.0;
        float[] cursor = {0};
        int[] character = {offset};
        text.accept((index, style, codePoint) -> {
            boolean animated = !markedOnly || RainbowText.MARKER.equals(style.getInsertion());
            int ordinal = animated ? character[0]++ : 0;
            int rgb = animated ? RainbowText.color(seconds, ordinal) : style.getColor() == null ? 0xFFFFFF : style.getColor().getValue();
            int color = alpha << 24 | rgb;
            float wave = animated ? (float) Math.sin(seconds * 3.0 - ordinal * 0.5) * 1.5F : 0;
            var glyph = FormattedCharSequence.forward(new String(Character.toChars(codePoint)), style.withColor(color & 0xFFFFFF));
            renderer.draw(glyph, cursor[0], wave, color);
            cursor[0] += font.getSplitter().stringWidth(glyph);
            return true;
        });
        return cursor[0];
    }

    public static void draw(GuiGraphics graphics, Font font, FormattedCharSequence text, int x, int y, int offset) {
        draw(graphics, font, text, x, y, offset, false);
    }

    public static void draw(GuiGraphics graphics, Font font, FormattedCharSequence text, int x, int y, int offset, boolean markedOnly) {
        glyphs(font, text, offset, 255, markedOnly, (glyph, dx, dy, color) -> {
            graphics.pose().pushPose();
            graphics.pose().translate(x + dx, y + dy, 0);
            graphics.drawString(font, glyph, 0, 0, color, true);
            graphics.pose().popPose();
        });
    }

    /** Used only for SELECTED_ITEM_NAME. Vanilla owns the position, timer and fade alpha. */
    public static final Font SELECTED_ITEM_FONT = new Font(id -> {
        throw new IllegalStateException("Selected-item font must delegate glyph access to the vanilla font");
    }, false) {
        private Font delegate() { return Minecraft.getInstance().font; }
        @Override public int width(String text) { return delegate().width(text); }
        @Override public int width(FormattedText text) { return delegate().width(text); }
        @Override public int width(FormattedCharSequence text) { return delegate().width(text); }
        @Override public net.minecraft.client.StringSplitter getSplitter() { return delegate().getSplitter(); }
        @Override public int drawInBatch(FormattedCharSequence text, float x, float y, int color, boolean shadow,
                                        Matrix4f matrix, MultiBufferSource buffers, DisplayMode mode, int background, int light) {
            int alpha = color >>> 24;
            // Match Font's handling of RGB-only colors, while preserving the HUD's fade alpha.
            if ((color & 0xFC000000) == 0) alpha = 255;
            float width = glyphs(delegate(), text, 0, alpha, (glyph, dx, dy, rgb) ->
                    delegate().drawInBatch(glyph, x + dx, y + dy, rgb, shadow, matrix, buffers, mode, background, light));
            return (int) (x + width) + (shadow ? 1 : 0);
        }
    };
}
