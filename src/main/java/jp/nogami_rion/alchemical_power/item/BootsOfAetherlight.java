package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModArmorMaterials;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.minecraft.world.entity.ai.behavior.HarvestFarmland.SPEED_MODIFIER;

public class BootsOfAetherlight extends ArmorItem {
    public BootsOfAetherlight() {
        super(ModArmorMaterials.SINGULARITY, Type.BOOTS,
                new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {

            // 落下ダメージ無効化
            if (player.fallDistance > 6.0f) {
                player.fallDistance = 0.0f;
            }

            // ■常時移動速度補正（Attributeによる補正、装備中のみ）
            if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER)) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER);
            }

            // デバフ完全除去：HARMFULカテゴリ全てを除去
            List<MobEffect> toRemove = new ArrayList<>();
            for (MobEffectInstance effect : player.getActiveEffects()) {
                MobEffect mobEffect = effect.getEffect();
                if (mobEffect.getCategory() == MobEffectCategory.HARMFUL) {
                    toRemove.add(mobEffect);
                }
            }
            toRemove.forEach(player::removeEffect);

        }
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        // 耐久を無限にする
        return false;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "alchemical_power:models/armor/crown_of_the_seraph.png";
    }

    private static final UUID SPEED_MODIFIER_ID = UUID.fromString("3a1b8e72-d3e2-4e89-9b41-9c382671a491");
    private static final AttributeModifier SPEED_MODIFIER =
            new AttributeModifier(SPEED_MODIFIER_ID, "paradox_speed_bonus", 0.15D, AttributeModifier.Operation.ADDITION);
}
