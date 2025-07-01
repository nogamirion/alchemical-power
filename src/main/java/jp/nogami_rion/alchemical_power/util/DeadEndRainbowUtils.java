package jp.nogami_rion.alchemical_power.util;

import net.minecraft.nbt.CompoundTag;
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
}
