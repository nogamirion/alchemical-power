package jp.nogami_rion.alchemical_power.client;

import com.mojang.datafixers.util.Either;
import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Client-only animation: names saved to items remain ordinary localized components. */
@Mod.EventBusSubscriber(modid = "alchemical_power", value = Dist.CLIENT)
public final class ConstellationTreasuryName {
    private record NameLine(FormattedCharSequence text, int characterOffset, boolean markedOnly) implements TooltipComponent {}

    @SubscribeEvent
    public static void tooltip(RenderTooltipEvent.GatherComponents event) {
        int width = Math.max(1, event.getScreenWidth() - 24);
        if (event.getMaxWidth() > 0) width = Math.min(width, event.getMaxWidth());
        for (int element = event.getTooltipElements().size() - 1; element >= 0; element--) {
            var text = event.getTooltipElements().get(element).left();
            if (text.isEmpty()) continue;
            boolean markedOnly = element != 0 || !(event.getItemStack().getItem() instanceof ConstellationTreasuryItem);
            if (markedOnly && !jp.nogami_rion.alchemical_power.util.RainbowText.hasMarker(text.get())) continue;
            var lines = Minecraft.getInstance().font.split(text.get(), width);
            event.getTooltipElements().remove(element);
            int offset = 0;
            for (int i = 0; i < lines.size(); i++) {
                var line = lines.get(i);
                event.getTooltipElements().add(element + i, Either.right(new NameLine(line, offset, markedOnly)));
                int[] count = {0};
                line.accept((index, style, codePoint) -> {
                    if (!markedOnly || jp.nogami_rion.alchemical_power.util.RainbowText.MARKER.equals(style.getInsertion())) count[0]++;
                    return true;
                });
                offset += count[0];
            }
        }
    }

    @Mod.EventBusSubscriber(modid = "alchemical_power", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Registration {
        @SubscribeEvent
        public static void factories(RegisterClientTooltipComponentFactoriesEvent event) {
            event.register(NameLine.class, line -> new ClientTooltipComponent() {
                @Override public int getHeight() { return 14; }
                @Override public int getWidth(Font font) { return font.width(line.text()); }
                @Override public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
                    RainbowWaveText.draw(graphics, font, line.text(), x, y + 2, line.characterOffset(), line.markedOnly());
                }
            });
        }
    }

    public static void draw(GuiGraphics graphics, Font font, FormattedCharSequence text, int x, int y, int characterOffset) {
        RainbowWaveText.draw(graphics, font, text, x, y, characterOffset);
    }
}
