package jp.nogami_rion.alchemical_power.client;

import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.network.ConstellationTreasuryNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "alchemical_power", value = Dist.CLIENT)
public final class ConstellationTreasuryInput {
    private static final net.minecraft.client.KeyMapping OPEN_STORAGE = new net.minecraft.client.KeyMapping(
            "key.alchemical_power.open_treasury", net.minecraftforge.client.settings.KeyConflictContext.IN_GAME,
            com.mojang.blaze3d.platform.InputConstants.Type.KEYSYM, org.lwjgl.glfw.GLFW.GLFW_KEY_V,
            "key.categories.alchemical_power");

    @Mod.EventBusSubscriber(modid = "alchemical_power", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Keys {
        @SubscribeEvent public static void register(net.minecraftforge.client.event.RegisterKeyMappingsEvent event) {
            event.register(OPEN_STORAGE);
        }
    }

    @SubscribeEvent public static void tick(net.minecraftforge.event.TickEvent.ClientTickEvent event) {
        if (event.phase != net.minecraftforge.event.TickEvent.Phase.END) return;
        var minecraft = Minecraft.getInstance();
        while (OPEN_STORAGE.consumeClick()) {
            if (minecraft.player != null && minecraft.screen == null
                    && jp.nogami_rion.alchemical_power.util.ConstellationTreasuryStorage.heldSlot(minecraft.player) >= 0)
                ConstellationTreasuryNetwork.openStorage();
        }
    }

    @SubscribeEvent public static void attack(InputEvent.InteractionKeyMappingTriggered event) {
        var minecraft = Minecraft.getInstance();
        if (!event.isAttack() || minecraft.player == null || minecraft.screen != null
                || !(minecraft.player.getMainHandItem().getItem() instanceof ConstellationTreasuryItem)) return;
        // The packet contains no target/damage/reach; the server determines everything.
        ConstellationTreasuryNetwork.attack();
        // Allow normal block mining. Entity and air clicks use only the custom attack.
        if (minecraft.hitResult == null || minecraft.hitResult.getType() != HitResult.Type.BLOCK) {
            event.setCanceled(true);
            event.setSwingHand(true);
            if (minecraft.player.getAttackStrengthScale(0.5F) >= 0.9F) minecraft.player.resetAttackStrengthTicker();
        }
    }
}
