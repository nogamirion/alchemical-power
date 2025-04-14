package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID)
public class ModItemEventHandler {

    @SubscribeEvent
    public static void onItemEntityJoinWorld(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof ItemEntity itemEntity){
            if(itemEntity.getItem().is(itemlist.SINGULARITY.get())){
                itemEntity.setNoGravity(true);
            }
        }
    }
}
