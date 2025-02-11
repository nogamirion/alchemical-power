package jp.nogami_rion.alchemical_power.init;


import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class itemlist {
    //アイテムリストを生成
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Alchemical_power.MODID);
    public static final RegistryObject<Item> T0_ALCHEMY_DUST = ITEMS.register("t0_alchemy_dust", T0_Alchemy_Dust::new);
    public static final RegistryObject<Item> T1_ALCHEMY_DUST = ITEMS.register("t1_alchemy_dust", T1_Alchemy_Dust::new);
    public static final RegistryObject<Item> T2_ALCHEMY_DUST = ITEMS.register("t2_alchemy_dust", T2_Alchemy_Dust::new);
    public static final RegistryObject<Item> T3_ALCHEMY_DUST = ITEMS.register("t3_alchemy_dust", T3_Alchemy_Dust::new);
    public static final RegistryObject<Item> T4_ALCHEMY_DUST = ITEMS.register("t4_alchemy_dust", T4_Alchemy_Dust::new);
    public static final RegistryObject<Item> T5_ALCHEMY_DUST = ITEMS.register("t5_alchemy_dust", T5_Alchemy_Dust::new);
    public static final RegistryObject<Item> T6_ALCHEMY_DUST = ITEMS.register("t6_alchemy_dust", T6_Alchemy_Dust::new);
    public static final RegistryObject<Item> T7_ALCHEMY_DUST = ITEMS.register("t7_alchemy_dust", T7_Alchemy_Dust::new);
    public static final RegistryObject<Item> STONE_CONTAINING_ALCHEMY_DUST = ITEMS.register("stone_containing_alchemy_dust",() -> new BlockItem(blocklist.STONE_CONTAINING_ALCHEMY_DUST.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_BEGINNERS_KIT = ITEMS.register("alchemy_beginners_kit",Alchemy_Bginners_Kit::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_COPPER = ITEMS.register("alchemical_processing_copper", Alchemical_Processing_Copper::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_IRON = ITEMS.register("alchemical_processing_iron", Alchemical_Processing_Iron::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_GOLD = ITEMS.register("alchemical_processing_gold", Alchemical_Processing_Gold::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_DIAMOND = ITEMS.register("alchemical_processing_diamond", Alchemical_Processing_Diamond::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_EMERALD = ITEMS.register("alchemical_processing_emerald", Alchemical_Processing_Emerald::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_NETHERITE = ITEMS.register("alchemical_processing_netherite", Alchemical_Processing_Netherite::new);
    public static final RegistryObject<Item> UNITE_ALLOY = ITEMS.register("unite_alloy", Unite_Alloy::new);
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_COPPER_BLOCK = ITEMS.register("alchemical_processing_copper_block",() -> new BlockItem(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_IRON_BLOCK = ITEMS.register("alchemical_processing_iron_block",() -> new BlockItem(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_GOLD_BLOCK = ITEMS.register("alchemical_processing_gold_block",() -> new BlockItem(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_DIAMOND_BLOCK = ITEMS.register("alchemical_processing_diamond_block",() -> new BlockItem(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_EMERALD_BLOCK = ITEMS.register("alchemical_processing_emerald_block",() -> new BlockItem(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMICAL_PROCESSING_NETHERITE_BLOCK = ITEMS.register("alchemical_processing_netherite_block",() -> new BlockItem(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> UNITE_ALLOY_BLOCK = ITEMS.register("unite_alloy_block",() -> new BlockItem(blocklist.UNITE_ALLOY_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_TABLE = ITEMS.register("alchemy_table",() -> new BlockItem(blocklist.ALCHEMY_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T0 = ITEMS.register("alchemy_dust_reed_t0",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T0.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T1 = ITEMS.register("alchemy_dust_reed_t1",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T1.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T2 = ITEMS.register("alchemy_dust_reed_t2",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T2.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T3 = ITEMS.register("alchemy_dust_reed_t3",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T3.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T4 = ITEMS.register("alchemy_dust_reed_t4",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T4.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T5 = ITEMS.register("alchemy_dust_reed_t5",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T5.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T6 = ITEMS.register("alchemy_dust_reed_t6",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T6.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_DUST_REED_T7 = ITEMS.register("alchemy_dust_reed_t7",() -> new BlockItem(blocklist.ALCHEMY_DUST_REED_T7.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_INTERMEDIATE_KIT = ITEMS.register("alchemy_intermediate_kit", Alchemy_Intermediate_Kit::new);
    public static final RegistryObject<Item> ALCHEMY_EXPERTS_KIT = ITEMS.register("alchemy_experts_kit", Alchemy_Experts_Kit::new);
    public static final RegistryObject<Item> ULTIMATE_ALCHEMY_KIT = ITEMS.register("ultimate_alchemy_kit", Ultimate_Alchemy_Kit::new);
    public static final RegistryObject<Item> HERMES_WORKBENCH = ITEMS.register("hermes_workbench",() -> new BlockItem(blocklist.HERMES_WORKBENCH.get(), new Item.Properties()));


    //アイテムリストの登録用
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
