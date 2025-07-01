package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModArmorMaterials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class Leggings_of_Sage extends ArmorItem {
    public Leggings_of_Sage() {
        super(ModArmorMaterials.IMITATED_BEDROCK, Type.LEGGINGS,
                new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            // 🌊 水中：イルカの好意を付与
            if (player.isInWaterOrBubble()) {
                player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 60, 0, false, false, true));
            }
            // スロー系デバフを安全に削除
            List<MobEffect> toRemove = new ArrayList<>();
            for (MobEffectInstance effect : player.getActiveEffects()) {
                MobEffect mobEffect = effect.getEffect();
                if (!mobEffect.isBeneficial() && isLikelyMovementSlowdown(mobEffect)) {
                    toRemove.add(mobEffect);
                }
            }

            for (MobEffect effect : toRemove) {
                player.removeEffect(effect);
            }

            // 🍗 スタミナ（空腹）軽減
            if (player.tickCount % 40 == 0 && player.getFoodData().getFoodLevel() <= 20) {
                player.getFoodData().eat(0, 0.5F); // 飽和度補助で減少を抑える
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
        return "alchemical_power:models/armor/leggings_of_temperance.png";
    }

    private boolean isLikelyMovementSlowdown(MobEffect effect) {
        String id = effect.getDescriptionId().toLowerCase();

        return id.contains("slow")
                || id.contains("slowness")
                || id.contains("freeze")
                || id.contains("cripple")
                || id.contains("snare")
                || id.contains("bind")
                || id.contains("immobilize")
                || id.contains("movement_debuff")
                || effect == MobEffects.MOVEMENT_SLOWDOWN;
    }
}
