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

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
