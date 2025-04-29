package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.effect.Angel_Ring;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class effectlist {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Alchemical_power.MODID);

    public static final RegistryObject<MobEffect> ANGEL_RING = EFFECTS.register("angel_ring", Angel_Ring::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
