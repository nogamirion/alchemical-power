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
        }


    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String layer = slot == net.minecraft.world.entity.EquipmentSlot.LEGS ? "2" : "1";
        return "alchemical_power:models/armor/angel_ring.png";
    }
}
