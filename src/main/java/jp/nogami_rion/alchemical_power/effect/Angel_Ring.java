package jp.nogami_rion.alchemical_power.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

public class Angel_Ring extends MobEffect {
    public Angel_Ring() {
        super(MobEffectCategory.BENEFICIAL, 0xADD8E6);
    }

    @Override
    public void applyEffectTick(net.minecraft.world.entity.LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = true; // 飛行を有効化
            player.onUpdateAbilities();

            // 耐性と自然回復の効果を付与
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 10, 1, false, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 11, 0, false, false, false));

        }
    }

//    @Override
//    public void removeAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
//        if (entity instanceof Player player) {
//            player.getAbilities().mayfly = false; // 飛行を無効化
//            player.getAbilities().flying = false; // 飛行状態を解除
//            player.onUpdateAbilities();
//        }
//    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 効果が毎ティック適用されるようにする
    }



}
