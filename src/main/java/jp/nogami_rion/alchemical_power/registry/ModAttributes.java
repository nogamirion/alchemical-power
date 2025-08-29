package jp.nogami_rion.alchemical_power.registry;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.entity.AlchetreeMysteriousScarecrowEntity;
import jp.nogami_rion.alchemical_power.init.entitylist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAttributes {
    @SubscribeEvent
    public static void onAttributes(EntityAttributeCreationEvent event){
        event.put(entitylist.ALCHETREE_MYSTERIOUS_SCARECROW.get(), AlchetreeMysteriousScarecrowEntity.CreateAttributes().build());
    }
}
