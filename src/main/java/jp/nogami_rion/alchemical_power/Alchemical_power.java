package jp.nogami_rion.alchemical_power;

import jp.nogami_rion.alchemical_power.block.entity.ModBlockEntities;
import jp.nogami_rion.alchemical_power.event.Origins_armor_effect_event;
import jp.nogami_rion.alchemical_power.event.ModEventBusClientEvents;
import jp.nogami_rion.alchemical_power.event.ModItemEventHandler;
import jp.nogami_rion.alchemical_power.init.*;
import jp.nogami_rion.alchemical_power.integration.CooperativeModChecker;
import jp.nogami_rion.alchemical_power.integration.tinker.TinkersIntegration;
import jp.nogami_rion.alchemical_power.loot.ModLootModifiers;
import jp.nogami_rion.alchemical_power.recipe.ModRecipes;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import jp.nogami_rion.alchemical_power.registry.ModTiers;
import jp.nogami_rion.alchemical_power.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.awt.*;

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
        ModFluids.register(modEventBus);
        creativetab.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        jp.nogami_rion.alchemical_power.network.ReactorTransferNetwork.register();
        jp.nogami_rion.alchemical_power.network.ConstellationTreasuryNetwork.register();
        jp.nogami_rion.alchemical_power.network.AssemblerPreviewNetwork.register();
        ModLootModifiers.register(modEventBus);
        effectlist.register(modEventBus);
        entitylist.register(modEventBus);
        CooperativeModChecker.tryRegisterModStuff();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        //イベントの登録
        MinecraftForge.EVENT_BUS.register(ModItemEventHandler.class);
        MinecraftForge.EVENT_BUS.register(ModEventBusClientEvents.class);
        MinecraftForge.EVENT_BUS.register(Origins_armor_effect_event.class);


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

//        event.enqueueWork(CooperativeModChecker::tryRegisterModStuff);
        event.enqueueWork(ModTiers::register);
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
            MenuScreens.register(ModMenuTypes.CONSTELLATION_TREASURY_MENU.get(), ConstellationTreasuryScreen::new);
            MenuScreens.register(ModMenuTypes.HERMES_WORKBENCH_MENU.get(), HermesWorkbench_Screen::new);
            MenuScreens.register(ModMenuTypes.TRANSCENDENTAL_TABLE_MENU.get(), Transcendental_Table_Screen::new);
            MenuScreens.register(ModMenuTypes.ALCHEMICAL_ENGRAVER_MENU.get(), Alchemical_Engraver_Screen::new);
            MenuScreens.register(ModMenuTypes.RUNE_ACTIVATOR_MENU.get(), Rune_Activator_Screen::new);
            MenuScreens.register(ModMenuTypes.ALCHEMICAL_POWER_TABLES_3X3_MENU.get(),AlchemicalTablesTier1Screen::new);
            MenuScreens.register(ModMenuTypes.ALCHEMICAL_POWER_TABLES_5X5_MENU.get(),AlchemicalTablesTier2Screen::new);
            MenuScreens.register(ModMenuTypes.ALCHEMICAL_POWER_TABLES_13X13_MENU.get(),AlchemicalTablesTier3Screen::new);
            MenuScreens.register(ModMenuTypes.AUTO_ALCHEMICAL_ASSEMBLER_MENU.get(), AutoAlchemicalAssemblerScreen::new);
            MenuScreens.register(ModMenuTypes.PANAKEIA_GENERATOR_MENU.get(),PanakeiaGeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.PANAKEIA_EXTRACTOR_MENU.get(),PanakeiaExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.ELECTRIC_RUNE_ACTIVATOR_MENU.get(), ElectricRuneActivatorScreen::new);
            MenuScreens.<RuneAssemblyMenu, AbstractContainerScreen<RuneAssemblyMenu>>register(ModMenuTypes.RUNE_ASSEMBLY_MENU.get(), (menu,inventory,title) ->
                    menu.isCore ? new RuneAssemblyCoreScreen(menu,inventory,title) : new RuneAssemblyScreen(menu,inventory,title));
            MenuScreens.register(ModMenuTypes.ALCHEMICAL_REACTOR_MENU.get(), AlchemicalReactorScreen::new);

            //ブロックモデルのレンダーレイヤー指定
            ItemBlockRenderTypes.setRenderLayer(blocklist.PAIN_CONVERTER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(blocklist.ALCHEMY_MACHINE_FRAME.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_PANAKEIA.block.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_PANAKEIA.source.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.LIQUID_PANAKEIA.flowing.get(), RenderType.translucent());

            event.enqueueWork(ClientModEvents::registerItemProperties);
        }

        private static void registerItemProperties() {
            ItemProperties.register(itemlist.ARSENAL_BOW.get(), new ResourceLocation("pulling"), (stack, level, entity, seed) ->
                    entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(itemlist.ARSENAL_BOW.get(), new ResourceLocation("pull"), (stack, level, entity, seed) -> {
                if (entity == null) {
                    return 0.0F;
                }
                return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            });
        }
    }
}
