package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class JudicialCarapace extends ArmorItem {
    public JudicialCarapace() {
        super(ModArmorMaterials.SINGULARITY, Type.CHESTPLATE,
                new Properties().stacksTo(1).rarity(Rarity.EPIC));
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
