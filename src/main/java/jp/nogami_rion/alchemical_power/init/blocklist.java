package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.*;
import jp.nogami_rion.alchemical_power.block.custom.ModFlammableRotatePillarBlock;
import jp.nogami_rion.alchemical_power.block.custom.ModLeavesBlock;
import jp.nogami_rion.alchemical_power.block.custom.ModSaplingsBlock;
import jp.nogami_rion.alchemical_power.item.custom.FuelBlock;
import jp.nogami_rion.alchemical_power.worldgen.tree.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
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

    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK1 = BLOCKS.register("cobblestone_generator_mk1", () -> new CobblestoneGeneratorMk1(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK2 = BLOCKS.register("cobblestone_generator_mk2", () -> new CobblestoneGeneratorMk2(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK3 = BLOCKS.register("cobblestone_generator_mk3", () -> new CobblestoneGeneratorMk3(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK4 = BLOCKS.register("cobblestone_generator_mk4", () -> new CobblestoneGeneratorMk4(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK5 = BLOCKS.register("cobblestone_generator_mk5", () -> new CobblestoneGeneratorMk5(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK6 = BLOCKS.register("cobblestone_generator_mk6", () -> new CobblestoneGeneratorMk6(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK7 = BLOCKS.register("cobblestone_generator_mk7", () -> new CobblestoneGeneratorMk7(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> COBBLESTONE_GENERATOR_MK8 = BLOCKS.register("cobblestone_generator_mk8", () -> new CobblestoneGeneratorMk8(()-> Items.COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK1 = BLOCKS.register("x9_cobblestone_generator_mk1", () -> new CobblestoneGeneratorMk1(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK2 = BLOCKS.register("x9_cobblestone_generator_mk2", () -> new CobblestoneGeneratorMk2(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK3 = BLOCKS.register("x9_cobblestone_generator_mk3", () -> new CobblestoneGeneratorMk3(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK4 = BLOCKS.register("x9_cobblestone_generator_mk4", () -> new CobblestoneGeneratorMk4(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK5 = BLOCKS.register("x9_cobblestone_generator_mk5", () -> new CobblestoneGeneratorMk5(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK6 = BLOCKS.register("x9_cobblestone_generator_mk6", () -> new CobblestoneGeneratorMk6(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK7 = BLOCKS.register("x9_cobblestone_generator_mk7", () -> new CobblestoneGeneratorMk7(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X9_COBBLESTONE_GENERATOR_MK8 = BLOCKS.register("x9_cobblestone_generator_mk8", () -> new CobblestoneGeneratorMk8(itemlist.X9_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK1 = BLOCKS.register("x225_cobblestone_generator_mk1", () -> new CobblestoneGeneratorMk1(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK2 = BLOCKS.register("x225_cobblestone_generator_mk2", () -> new CobblestoneGeneratorMk2(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK3 = BLOCKS.register("x225_cobblestone_generator_mk3", () -> new CobblestoneGeneratorMk3(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK4 = BLOCKS.register("x225_cobblestone_generator_mk4", () -> new CobblestoneGeneratorMk4(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK5 = BLOCKS.register("x225_cobblestone_generator_mk5", () -> new CobblestoneGeneratorMk5(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK6 = BLOCKS.register("x225_cobblestone_generator_mk6", () -> new CobblestoneGeneratorMk6(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK7 = BLOCKS.register("x225_cobblestone_generator_mk7", () -> new CobblestoneGeneratorMk7(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X225_COBBLESTONE_GENERATOR_MK8 = BLOCKS.register("x225_cobblestone_generator_mk8", () -> new CobblestoneGeneratorMk8(itemlist.X225_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK1 = BLOCKS.register("x38025_cobblestone_generator_mk1", () -> new CobblestoneGeneratorMk1(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK2 = BLOCKS.register("x38025_cobblestone_generator_mk2", () -> new CobblestoneGeneratorMk2(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK3 = BLOCKS.register("x38025_cobblestone_generator_mk3", () -> new CobblestoneGeneratorMk3(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK4 = BLOCKS.register("x38025_cobblestone_generator_mk4", () -> new CobblestoneGeneratorMk4(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK5 = BLOCKS.register("x38025_cobblestone_generator_mk5", () -> new CobblestoneGeneratorMk5(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK6 = BLOCKS.register("x38025_cobblestone_generator_mk6", () -> new CobblestoneGeneratorMk6(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK7 = BLOCKS.register("x38025_cobblestone_generator_mk7", () -> new CobblestoneGeneratorMk7(itemlist.X38025_COBBLESTONE));
    public static final RegistryObject<Block> X38025_COBBLESTONE_GENERATOR_MK8 = BLOCKS.register("x38025_cobblestone_generator_mk8", () -> new CobblestoneGeneratorMk8(itemlist.X38025_COBBLESTONE));

    public static final RegistryObject<Block> ALCHETREE_LOG = BLOCKS.register("alchetree_log",() -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3.0f)));
    public static final RegistryObject<Block> ALCHETREE_WOOD = BLOCKS.register("alchetree_wood",() -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3.0f)));
    public static final RegistryObject<Block> STRIPPED_ALCHETREE_LOG = BLOCKS.register("stripped_alchetree_log",() -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3.0f)));
    public static final RegistryObject<Block> STRIPPED_ALCHETREE_WOOD = BLOCKS.register("stripped_alchetree_wood",() -> new ModFlammableRotatePillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3.0f)));

    public static final RegistryObject<Block> ALCHETREE_PLANKS = BLOCKS.register("alchetree_planks",() -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 20;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    });
    public static final RegistryObject<Block>[] ALCHETREE_LEAVES = new RegistryObject[8];
    static {
        for (int tier = 0; tier <8 ; tier++){
            int finalTier = tier;
            ALCHETREE_LEAVES[tier] = BLOCKS.register("alchetree_leaves_t"+tier,() -> new ModLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
        }
    }

    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T0 = BLOCKS.register("alchetree_sapling_t0",() -> new ModSaplingsBlock(new AlchetreeT0Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T1 = BLOCKS.register("alchetree_sapling_t1",() -> new ModSaplingsBlock(new AlchetreeT1Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T2 = BLOCKS.register("alchetree_sapling_t2",() -> new ModSaplingsBlock(new AlchetreeT2Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T3 = BLOCKS.register("alchetree_sapling_t3",() -> new ModSaplingsBlock(new AlchetreeT3Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T4 = BLOCKS.register("alchetree_sapling_t4",() -> new ModSaplingsBlock(new AlchetreeT4Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T5 = BLOCKS.register("alchetree_sapling_t5",() -> new ModSaplingsBlock(new AlchetreeT5Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T6 = BLOCKS.register("alchetree_sapling_t6",() -> new ModSaplingsBlock(new AlchetreeT6Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALCHETREE_SAPLINGS_T7 = BLOCKS.register("alchetree_sapling_t7",() -> new ModSaplingsBlock(new AlchetreeT7Grower(),BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistryObject<Block> ALCHETREE_STAIRS = BLOCKS.register("alchetree_stairs",() -> new StairBlock( ()-> blocklist.ALCHETREE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ALCHETREE_SLAB = BLOCKS.register("alchetree_slab",() -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ALCHETREE_BUTTON = BLOCKS.register("alchetree_button",() -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetType.OAK,10,true));
    public static final RegistryObject<Block> ALCHETREE_PRESSURE_PLATE = BLOCKS.register("alchetree_pressure_plate",() -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),BlockSetType.OAK));
    public static final RegistryObject<Block> ALCHETREE_FENCE = BLOCKS.register("alchetree_fence",() -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ALCHETREE_FENCE_GATE = BLOCKS.register("alchetree_fence_gate",() -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> ALCHETREE_WALL = BLOCKS.register("alchetree_wall",() -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ALCHETREE_DOOR = BLOCKS.register("alchetree_door",() -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),BlockSetType.OAK));
    public static final RegistryObject<Block> ALCHETREE_TRAPDOOR = BLOCKS.register("alchetree_trapdoor",() -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),BlockSetType.OAK));


    //アイテムリストの登録用
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
