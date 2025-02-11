package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class blocklist {
    //ブロックリストの生成
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Alchemical_power.MODID);
    public static final RegistryObject<Block> STONE_CONTAINING_ALCHEMY_DUST = BLOCKS.register("stone_containing_alchemy_dust", Stone_Containing_Alchemy_Dust::new);
    public static final RegistryObject<Block> ALCHEMY_TABLE = BLOCKS.register("alchemy_table", Alchemy_Table::new);
    public static final RegistryObject<Block> ALCHEMICAL_PROCESSING_COPPER_BLOCK = BLOCKS.register("alchemical_processing_copper_block", Alchemical_Processing_Copper_Block::new);
    public static final RegistryObject<Block> ALCHEMICAL_PROCESSING_IRON_BLOCK = BLOCKS.register("alchemical_processing_iron_block", Alchemical_Processing_Iron_Block::new);
    public static final RegistryObject<Block> ALCHEMICAL_PROCESSING_GOLD_BLOCK = BLOCKS.register("alchemical_processing_gold_block", Alchemical_Processing_Gold_Block::new);
    public static final RegistryObject<Block> ALCHEMICAL_PROCESSING_DIAMOND_BLOCK = BLOCKS.register("alchemical_processing_diamond_block", Alchemical_Processing_Copper_Block::new);
    public static final RegistryObject<Block> ALCHEMICAL_PROCESSING_EMERALD_BLOCK = BLOCKS.register("alchemical_processing_emerald_block", Alchemical_Processing_Copper_Block::new);
    public static final RegistryObject<Block> ALCHEMICAL_PROCESSING_NETHERITE_BLOCK = BLOCKS.register("alchemical_processing_netherite_block", Alchemical_Processing_Copper_Block::new);
    public static final RegistryObject<Block> UNITE_ALLOY_BLOCK = BLOCKS.register("unite_alloy_block",Unite_Alloy_Block::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T0 = BLOCKS.register("alchemy_dust_reed_t0",Alchemy_Dust_Reed_T0::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T1 = BLOCKS.register("alchemy_dust_reed_t1",Alchemy_Dust_Reed_T1::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T2 = BLOCKS.register("alchemy_dust_reed_t2",Alchemy_Dust_Reed_T2::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T3 = BLOCKS.register("alchemy_dust_reed_t3",Alchemy_Dust_Reed_T3::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T4 = BLOCKS.register("alchemy_dust_reed_t4",Alchemy_Dust_Reed_T4::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T5 = BLOCKS.register("alchemy_dust_reed_t5",Alchemy_Dust_Reed_T5::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T6 = BLOCKS.register("alchemy_dust_reed_t6",Alchemy_Dust_Reed_T6::new);
    public static final RegistryObject<Block> ALCHEMY_DUST_REED_T7 = BLOCKS.register("alchemy_dust_reed_t7",Alchemy_Dust_Reed_T7::new);
    public static final RegistryObject<Block> HERMES_WORKBENCH = BLOCKS.register("hermes_workbench", Hermes_Workbench::new);

    //アイテムリストの登録用
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
