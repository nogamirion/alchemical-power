package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.dategen.ModCuriosProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CuriosDataEvent {

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(
             // Tell generator to run only when server data are generating
             event.includeServer(),
             new ModCuriosProvider(
                     "alchemical_power",
                     event.getGenerator().getPackOutput(),
                     event.getExistingFileHelper(),
                     event.getLookupProvider()
            )
    );
}
}
