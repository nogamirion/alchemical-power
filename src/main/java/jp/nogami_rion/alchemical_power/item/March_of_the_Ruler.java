package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModArmorMaterials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class March_of_the_Ruler extends ArmorItem {
    public March_of_the_Ruler() {
        super(ModArmorMaterials.IMITATED_BEDROCK, Type.BOOTS,
                new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {

            // 落下ダメージ無効化
            if (player.fallDistance > 6.0f) {
                player.fallDistance = 0.0f;
            }

            // 毎秒だけ処理
            if (player.tickCount % 20 == 0) {
                List<MobEffectInstance> toShorten = new ArrayList<>();

                // デバフの抽出（直接削除せずリストに追加）
                for (MobEffectInstance effect : player.getActiveEffects()) {
                    MobEffect mobEffect = effect.getEffect();
                    if (!mobEffect.isBeneficial() && effect.getDuration() > 20) {
                        toShorten.add(effect);
                    }
                }

                // 効果を短縮して再付与
                for (MobEffectInstance effect : toShorten) {
                    MobEffect mobEffect = effect.getEffect();
                    int newDuration = effect.getDuration() - 20;
                    if (newDuration > 0) {
                        player.removeEffect(mobEffect);
                        player.addEffect(new MobEffectInstance(mobEffect, newDuration, effect.getAmplifier(),
                                effect.isAmbient(), effect.isVisible(), effect.showIcon()));
                    }
                }
            }
        }
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        // 耐久を無限にする
        return false;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "alchemical_power:models/armor/angel_ring.png";
    }
}
