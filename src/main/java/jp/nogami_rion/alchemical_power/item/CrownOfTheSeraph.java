package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.init.effectlist;
import jp.nogami_rion.alchemical_power.item.baseclass.ModArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class CrownOfTheSeraph extends ArmorItem {
    public CrownOfTheSeraph() {
        super(ModArmorMaterials.SINGULARITY, Type.HELMET,
                new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == this) {
                player.addEffect(new MobEffectInstance(effectlist.ANGEL_RING.get(), 9, 0, false, false, false));
            }
            MobEffectInstance regen = player.getEffect(MobEffects.REGENERATION);
            if (regen == null || regen.getAmplifier() < 2 || regen.getDuration() < 40) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 3, false, false, false));
            }
            MobEffectInstance resistance = player.getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (resistance == null || resistance.getAmplifier() < 1 || resistance.getDuration() < 40) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2, false, false, false));
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
        return "alchemical_power:models/armor/crown_of_the_seraph.png";
    }
}
