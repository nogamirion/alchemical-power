package jp.nogami_rion.alchemical_power.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class DeadEndRainbowUtils {

    // 攻撃スタックのNBTキー
    public static final String DEAD_END_RAINBOW = "alchemical_power.dead_end_rainbow";

    /**
     * 対象に攻撃マークを1加算する
     */
    public static void applyAttackMark(LivingEntity target) {
        CompoundTag tag = target.getPersistentData();
        int current = tag.getInt(DEAD_END_RAINBOW);
        tag.putInt(DEAD_END_RAINBOW, current + 1);
    }

    /**
     * 対象の攻撃マーク数を取得する
     */
    public static int getAttackMark(LivingEntity target) {
        return target.getPersistentData().getInt(DEAD_END_RAINBOW);
    }

    /**
     * 攻撃マークをリセットする
     */
    public static void resetAttackMark(LivingEntity target) {
        target.getPersistentData().remove(DEAD_END_RAINBOW);
    }

    /**
     * 対象のマーク数を確認し、7以上なら即死させる
     */
    public static void checkAttackMark(LivingEntity target,LivingEntity attacker){
        if (getAttackMark(target) >= 7) {
            ServerLevel level = (ServerLevel) target.level();
            DamageSource singularity = ModDamageTypes.singularityTrue(level, attacker);
            float maxHealth = target.getMaxHealth();

            target.hurt(singularity, maxHealth * 2.0f);// 最大HPの2倍のダメージ

            if (target.isAlive()) {
                target.kill(); // Entity.kill() はエンティティ固有の即死処理
            }

            if (target.isAlive()) {
                target.setHealth(0.0F);
                target.die(singularity);
            }

            resetAttackMark(target); // スタックリセット
        }
    }
}
