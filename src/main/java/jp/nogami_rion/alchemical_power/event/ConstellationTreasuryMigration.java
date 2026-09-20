package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.screen.ModMenuTypes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

/** Preserve existing worlds and the unchanged ArsenalInventory/ArsenalSalvo NBT payloads. */
@Mod.EventBusSubscriber(modid = "alchemical_power")
public final class ConstellationTreasuryMigration {
    @SubscribeEvent
    public static void remap(MissingMappingsEvent event) {
        for (var mapping : event.getMappings(ForgeRegistries.Keys.ITEMS, "alchemical_power")) {
            if (mapping.getKey().getPath().equals("arsenal_sword")) mapping.remap(itemlist.CONSTELLATION_TREASURY.get());
        }
        for (var mapping : event.getMappings(ForgeRegistries.Keys.MENU_TYPES, "alchemical_power")) {
            if (mapping.getKey().getPath().equals("arsenal_sword_menu")) mapping.remap(ModMenuTypes.CONSTELLATION_TREASURY_MENU.get());
        }
    }
}
