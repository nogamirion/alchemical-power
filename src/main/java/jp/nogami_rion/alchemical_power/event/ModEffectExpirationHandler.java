package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.effectlist;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID)
public class ModEffectExpirationHandler {

    //天使の輪効果の適応かどうかの判断用
    private static final String ANGEL_RING_TAG = "AngelRingFlight";

    //代替案としてプレイヤーを常時監視して天使の輪効果の有無を監視
    //天使の輪の効果適応中かの監視も併せて行い、適応外である場合1度だけクリエ飛行を剥奪
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if(!(player instanceof ServerPlayer sPlayer)) return;


        if (!player.level().isClientSide) {
            CompoundTag persistentData = player.getPersistentData();

            // 天使の輪効果がある場合
            if (player.hasEffect(effectlist.ANGEL_RING.get())) {
                if (!persistentData.getBoolean(ANGEL_RING_TAG)) {
                    persistentData.putBoolean(ANGEL_RING_TAG, true);
                }
            } else {
                // 天使の輪効果がない場合
                if (persistentData.getBoolean(ANGEL_RING_TAG)) {
                    // 天使の輪による飛行を無効化
                    if (!player.isCreative() && !player.isSpectator()) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.onUpdateAbilities();
                    }
                    persistentData.putBoolean(ANGEL_RING_TAG, false);
                }
            }

        }
    }
}

