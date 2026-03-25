package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.block.PanakeiaGeneratorBlock;
import jp.nogami_rion.alchemical_power.block.entity.PanakeiaGeneratorBlockEntity;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID, bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ModBlockColors {

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

        event.register((state,level,pos,tintIndex) ->{
            if(level == null || pos == null) return 0xFF00FF;

            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof PanakeiaGeneratorBlockEntity gen){
                int tier = gen.getFuelTier();
                if(tier <= 6) {
                    return gen.getFuelTint();
                }else{
                    int phase = state.getValue(PanakeiaGeneratorBlock.RAINBOW);
                    float hue = phase / 24f;
                    return Color.HSBtoRGB(hue,1f,1f) & 0xffffff;
                }
            }
            return 0xFF0000;
        }, blocklist.PANAKEIA_GENERATOR.get());
    }

}

