package jp.nogami_rion.alchemical_power.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

/** Common-side colors plus a style marker for the client's animated tooltip renderer. */
public final class RainbowText {
    public static final String MARKER = "alchemical_power:rainbow_name";

    private RainbowText() {}

    public static int color(double seconds, int character) {
        float hue = (float) ((seconds * 0.22 + character * 0.065) % 1.0);
        return Mth.hsvToRgb(hue, 0.65F, 1.0F);
    }

    public static boolean hasMarker(FormattedText text) {
        return text.visit((style, part) -> MARKER.equals(style.getInsertion())
                ? java.util.Optional.of(true) : java.util.Optional.empty(), Style.EMPTY).orElse(false);
    }

    public static Component name(Component name) {
        var result = Component.empty();
        double seconds = Util.getMillis() / 1000.0;
        int[] character = {0};
        name.getString().codePoints().forEach(codePoint -> result.append(
                Component.literal(new String(Character.toChars(codePoint)))
                        .withStyle(name.getStyle().withColor(color(seconds, character[0]++)).withInsertion(MARKER))));
        return result;
    }
}
