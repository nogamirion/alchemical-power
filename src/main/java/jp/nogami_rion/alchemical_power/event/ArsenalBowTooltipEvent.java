package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.ArsenalBowItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Locale;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ArsenalBowTooltipEvent {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack bowStack = event.getItemStack();
        if (!bowStack.is(itemlist.ARSENAL_BOW.get())) {
            return;
        }

        Player player = event.getEntity();
        if (player == null) {
            return;
        }

        ItemStack swordStack = ArsenalBowItem.findProjectileSword(player.getInventory());
        if (swordStack.isEmpty()) {
            addArsenalBowTooltip(event.getToolTip(), Component.translatable("tooltip.alchemical_power.arsenal_bow.no_sword").withStyle(ChatFormatting.DARK_GREEN));
            return;
        }

        addArsenalBowTooltip(
                event.getToolTip(),
                loadedSwordLine(swordStack),
                Component.translatable("tooltip.alchemical_power.arsenal_bow.full_charge_damage", String.format(Locale.ROOT, "%.1f", ArsenalBowItem.getFullChargeDamage(bowStack, swordStack))).withStyle(ChatFormatting.DARK_GREEN)
        );
    }

    private static MutableComponent loadedSwordLine(ItemStack swordStack) {
        return Component.translatable("tooltip.alchemical_power.arsenal_bow.loaded_sword_prefix")
                .withStyle(ChatFormatting.DARK_GREEN)
                .append(swordStack.getHoverName().copy().withStyle(swordStack.getRarity().color));
    }

    private static void addArsenalBowTooltip(List<Component> tooltip, Component... lines) {
        for (Component line : lines) {
            tooltip.add(line);
        }
    }
}
