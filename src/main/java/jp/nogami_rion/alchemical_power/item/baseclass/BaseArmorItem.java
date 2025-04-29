package jp.nogami_rion.alchemical_power.item.baseclass;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String layer = slot == net.minecraft.world.entity.EquipmentSlot.LEGS ? "2" : "1";
        return "alchemical_power:models/armor/" + getMaterial().getName() + "_layer_" + layer + ".png";
    }
}
