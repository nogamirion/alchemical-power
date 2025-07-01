package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.init.effectlist;
import jp.nogami_rion.alchemical_power.item.baseclass.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class Angel_Ring extends ArmorItem {
    public Angel_Ring() {
        super(ModArmorMaterials.IMITATED_BEDROCK, Type.HELMET,
                new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == this) {
                player.addEffect(new MobEffectInstance(effectlist.ANGEL_RING.get(), 9, 0, false, false, false));
            }
            // 再生能力3の持続（必要に応じて調整）
            MobEffectInstance regen = player.getEffect(MobEffects.REGENERATION);
            if (regen == null || regen.getAmplifier() < 2 || regen.getDuration() < 40) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2, false, false, false));
            }
            // 耐性2も同様
            MobEffectInstance resistance = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (resistance == null || resistance.getAmplifier() < 1 || resistance.getDuration() < 40) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1, false, false, false));
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
