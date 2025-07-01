package jp.nogami_rion.alchemical_power.block.entity;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Alchemical_power.MODID);

    public static final RegistryObject<BlockEntityType<Alchemy_Table_Entity>> ALCHEMY_TABLE_BE =
            BLOCK_ENTITIES.register("alchemy_table_be",() ->
                    BlockEntityType.Builder.of(Alchemy_Table_Entity::new, blocklist.ALCHEMY_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<Hermes_Workbench_Entity>> HERMES_WORKBENCH_BE =
            BLOCK_ENTITIES.register("hermes_workbench_be",() ->
                    BlockEntityType.Builder.of(Hermes_Workbench_Entity::new, blocklist.HERMES_WORKBENCH.get()).build(null));

    public static final RegistryObject<BlockEntityType<Transcendental_Table_Entity>> TRANSCENDENTAL_TABLE_BE =
            BLOCK_ENTITIES.register("transcendental_table_be",() ->
                    BlockEntityType.Builder.of(Transcendental_Table_Entity::new, blocklist.TRANSCENDENTAL_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<Alchemical_Engraver_Entity>> ALCHEMICAL_ENGRAVER_BE =
            BLOCK_ENTITIES.register("alchemical_engraver_be",() ->
                    BlockEntityType.Builder.of(Alchemical_Engraver_Entity::new, blocklist.ALCHEMICAL_ENGRAVER.get()).build(null));

    public static final RegistryObject<BlockEntityType<Rune_Activator_Entity>> RUNE_ACTIVATOR_BE =
            BLOCK_ENTITIES.register("rune_activator_be",() ->
                    BlockEntityType.Builder.of(Rune_Activator_Entity::new, blocklist.RUNE_ACTIVATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk1Entity>> COBBLESTONE_GENERATOR_MK1_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk1_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk1Entity::new, blocklist.COBBLESTONE_GENERATOR_MK1.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK1.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK1.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK1.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk2Entity>> COBBLESTONE_GENERATOR_MK2_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk2_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk2Entity::new, blocklist.COBBLESTONE_GENERATOR_MK2.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK2.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK2.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK2.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk3Entity>> COBBLESTONE_GENERATOR_MK3_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk3_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk3Entity::new, blocklist.COBBLESTONE_GENERATOR_MK3.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK3.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK3.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK3.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk4Entity>> COBBLESTONE_GENERATOR_MK4_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk4_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk4Entity::new, blocklist.COBBLESTONE_GENERATOR_MK4.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK4.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK4.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK4.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk5Entity>> COBBLESTONE_GENERATOR_MK5_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk5_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk5Entity::new, blocklist.COBBLESTONE_GENERATOR_MK5.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK5.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK5.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK5.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk6Entity>> COBBLESTONE_GENERATOR_MK6_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk6_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk6Entity::new, blocklist.COBBLESTONE_GENERATOR_MK6.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK6.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK6.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK6.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk7Entity>> COBBLESTONE_GENERATOR_MK7_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk7_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk7Entity::new, blocklist.COBBLESTONE_GENERATOR_MK7.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK7.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK7.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK7.get()).build(null));

    public static final RegistryObject<BlockEntityType<CobblestoneGeneratorMk8Entity>> COBBLESTONE_GENERATOR_MK8_BE =
            BLOCK_ENTITIES.register("cobblestone_generator_mk8_be",() ->
                    BlockEntityType.Builder.of(CobblestoneGeneratorMk8Entity::new, blocklist.COBBLESTONE_GENERATOR_MK8.get(),blocklist.X9_COBBLESTONE_GENERATOR_MK8.get(),blocklist.X225_COBBLESTONE_GENERATOR_MK8.get(),blocklist.X38025_COBBLESTONE_GENERATOR_MK8.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
