package jp.nogami_rion.alchemical_power;

import jp.nogami_rion.alchemical_power.block.entity.ModBlockEntities;
import jp.nogami_rion.alchemical_power.event.ModEventBusClientEvents;
import jp.nogami_rion.alchemical_power.event.ModItemEventHandler;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.creativetab;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.loot.ModLootModifiers;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import jp.nogami_rion.alchemical_power.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Alchemical_power.MODID)
public class Alchemical_power {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "alchemical_power";

    public Alchemical_power() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //アイテムリスト・ブロックリスト・クリエイティブタブ・MODレシピなどの追加要素登録
        itemlist.register(modEventBus);
        blocklist.register(modEventBus);
        creativetab.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //イベントの登録
        MinecraftForge.EVENT_BUS.register(ModItemEventHandler.class);
        MinecraftForge.EVENT_BUS.register(ModEventBusClientEvents.class);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            //GUIの追加
            MenuScreens.register(ModMenuTypes.ALCHEMY_TABLE_MENU.get(), Alchemy_Table_Screen::new);
            MenuScreens.register(ModMenuTypes.HERMES_WORKBENCH_MENU.get(), HermesWorkbench_Screen::new);
            MenuScreens.register(ModMenuTypes.TRANSCENDENTAL_TABLE_MENU.get(), Transcendental_Table_Screen::new);
            MenuScreens.register(ModMenuTypes.ALCHEMICAL_ENGRAVER_MENU.get(), Alchemical_Engraver_Screen::new);
            MenuScreens.register(ModMenuTypes.RUNE_ACTIVATOR_MENU.get(), Rune_Activator_Screen::new);
        }
    }
}
