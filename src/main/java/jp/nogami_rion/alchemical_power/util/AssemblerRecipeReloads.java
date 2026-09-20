package jp.nogami_rion.alchemical_power.util;

import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/** Invalidates positive and negative machine caches after server datapack reloads. */
@Mod.EventBusSubscriber(modid = "alchemical_power")
public final class AssemblerRecipeReloads {
    private static long revision;

    public static long revision() { return revision; }

    @SubscribeEvent
    public static void onSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() == null) revision++;
    }
}
