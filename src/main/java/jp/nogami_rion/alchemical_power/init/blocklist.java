package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.*;
import jp.nogami_rion.alchemical_power.item.custom.FuelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class blocklist {
    //ブロックリストの生成
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Alchemical_power.MODID);
    public static final RegistryObject<Block> PANAKEIA_BEARING_STONE = BLOCKS.register("panakeia_bearing_stone", Panakeia_Bearing_Stone::new);
    public static final RegistryObject<Block> ALCHEMY_TABLE = BLOCKS.register("alchemy_table", Alchemy_Table::new);
    public static final RegistryObject<Block> T1_PANAKEIA_INGOT_BLOCK = BLOCKS.register("t1_panakeia_ingot_block", T1_Panakeia_Ingot_Block::new);
    public static final RegistryObject<Block> T2_PANAKEIA_INGOT_BLOCK = BLOCKS.register("t2_panakeia_ingot_block", T2_Panakeia_Ingot_Block::new);
    public static final RegistryObject<Block> T3_PANAKEIA_INGOT_BLOCK = BLOCKS.register("t3_panakeia_ingot_block", T3_Panakeia_Ingot_Block::new);
    public static final RegistryObject<Block> T4_PANAKEIA_GEM_BLOCK = BLOCKS.register("t4_panakeia_gem_block", T4_Panakeia_Gem_Block::new);
    public static final RegistryObject<Block> T5_PANAKEIA_GEM_BLOCK = BLOCKS.register("t5_panakeia_gem_block", T5_Panakeia_Gem_Block::new);
    public static final RegistryObject<Block> T6_PANAKEIA_INGOT_BLOCK = BLOCKS.register("t6_panakeia_ingot_block", T6_Panakeia_Ingot_Block::new);
    public static final RegistryObject<Block> UNITE_ALLOY_BLOCK = BLOCKS.register("unite_alloy_block",Unite_Alloy_Block::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T0 = BLOCKS.register("panakeia_reed_t0", Panakeia_Reed_T0::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T1 = BLOCKS.register("panakeia_reed_t1", Panakeia_Reed_T1::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T2 = BLOCKS.register("panakeia_reed_t2", Panakeia_Reed_T2::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T3 = BLOCKS.register("panakeia_reed_t3", Panakeia_Reed_T3::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T4 = BLOCKS.register("panakeia_reed_t4", Panakeia_Reed_T4::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T5 = BLOCKS.register("panakeia_reed_t5", Panakeia_Reed_T5::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T6 = BLOCKS.register("panakeia_reed_t6", Panakeia_Reed_T6::new);
    public static final RegistryObject<Block> PANAKEIA_REED_T7 = BLOCKS.register("panakeia_reed_t7", Panakeia_Reed_T7::new);
    public static final RegistryObject<Block> HERMES_WORKBENCH = BLOCKS.register("hermes_workbench", Hermes_Workbench::new);
    public static final RegistryObject<Block> TRANSCENDENTAL_TABLE = BLOCKS.register("transcendental_table", Transcendental_Table::new);
    public static final RegistryObject<Block> ALCHEMICAL_ENGRAVER = BLOCKS.register("alchemical_engraver", Alchemical_Engraver::new);
    public static final RegistryObject<Block> RUNE_ACTIVATOR = BLOCKS.register("rune_activator", Rune_Activator::new);
    public static final RegistryObject<Block> X9_COBBLESTONE = BLOCKS.register("x9_cobblestone", X9_Cobblestone::new);
    public static final RegistryObject<Block> X225_COBBLESTONE = BLOCKS.register("x225_cobblestone", X225_Cobblestone::new);
    public static final RegistryObject<Block> X38025_COBBLESTONE = BLOCKS.register("x38025_cobblestone", X38025_Cobblestone::new);
    public static final RegistryObject<Block> IMITATED_BEDROCK = BLOCKS.register("imitated_bedrock", Imitated_Bedrock::new);
    public static final RegistryObject<Block> T1_COMBUSTION_RUNE_BLOCK = BLOCKS.register("t1_combustion_rune_block",() -> new FuelBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops(),2400*9));
    public static final RegistryObject<Block> T2_COMBUSTION_RUNE_BLOCK = BLOCKS.register("t2_combustion_rune_block",() -> new FuelBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops(),3600*9));
    public static final RegistryObject<Block> T3_COMBUSTION_RUNE_BLOCK = BLOCKS.register("t3_combustion_rune_block",() -> new FuelBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops(),5400*9));
    public static final RegistryObject<Block> T4_COMBUSTION_RUNE_BLOCK = BLOCKS.register("t4_combustion_rune_block",() -> new FuelBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops(),8100*9));
    public static final RegistryObject<Block> T5_COMBUSTION_RUNE_BLOCK = BLOCKS.register("t5_combustion_rune_block",() -> new FuelBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops(),12150*9));
    public static final RegistryObject<Block> T6_COMBUSTION_RUNE_BLOCK = BLOCKS.register("t6_combustion_rune_block",() -> new FuelBlock(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(18f,54f).requiresCorrectToolForDrops(),18225*9));


    //アイテムリストの登録用
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
