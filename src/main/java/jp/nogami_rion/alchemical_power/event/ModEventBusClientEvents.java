package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.entity.ModBlockEntities;
import jp.nogami_rion.alchemical_power.block.entity.renderer.Rune_Activator_Renderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.RUNE_ACTIVATOR_BE.get(), Rune_Activator_Renderer::new);
    }
}
