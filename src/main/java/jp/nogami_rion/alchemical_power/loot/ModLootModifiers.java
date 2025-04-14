package jp.nogami_rion.alchemical_power.loot;

import com.mojang.serialization.Codec;
import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Alchemical_power.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_SERIALIZERS.register("add_item",AddItemModifier.CODEC);

    public static void register(IEventBus eventBus){
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

}
