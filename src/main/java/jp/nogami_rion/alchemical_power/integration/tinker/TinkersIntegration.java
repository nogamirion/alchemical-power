package jp.nogami_rion.alchemical_power.integration.tinker;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.integration.tinker.modifier.SingularityModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TinkersIntegration {

    private static final Logger LOGGER = LogManager.getLogger("Alchemical_Power-TiC");

    public static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(Alchemical_power.MODID);

    public static final StaticModifier<Modifier> PANAKEIA_MODIFIER = null;
    public static final StaticModifier<Modifier> SUPERPOLYMERIZATION_MODIFIER = null;
    public static final StaticModifier<Modifier> AKASHIC_RECORDS_MODIFIER = null;
    public static final StaticModifier<Modifier> SINGULARITY_MODIFIER = MODIFIERS.register("singularity", SingularityModifier::new);

    public static void register()
    {
        LOGGER.info("[TinkersIntegration] register() start. Thread: " + Thread.currentThread().getName());
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        try {
            MODIFIERS.register(bus);
            LOGGER.info("[TinkersIntegration] MODIFIERS.register(bus) called");
        } catch(Throwable t){
            LOGGER.info("[TinkersIntegration] registration failed",t);
            if(t.getCause() != null){
                LOGGER.info("[TinkersIntegration] cause:",t.getCause());
            }
        }
        LOGGER.info("[TinkersIntegration] register() end");
    }

 }
