package jp.nogami_rion.alchemical_power.item.baseclass;

import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    T1_INGOT("t1_ingot", 6, new int[]{2, 5, 4, 2}, 10, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F,() -> Ingredient.of(itemlist.T1_PANAKEIA_INGOT.get())),
    T2_INGOT("t2_ingot", 8, new int[]{2, 6, 5, 2}, 15, SoundEvents.ARMOR_EQUIP_IRON, 0.5F, 0.0F, () -> Ingredient.of(itemlist.T2_PANAKEIA_INGOT.get())),
    T3_INGOT("t3_ingot", 9, new int[]{2, 6, 6, 3}, 20, SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 0.0F, () -> Ingredient.of(itemlist.T3_PANAKEIA_INGOT.get())),
    T4_GEM("t4_gem", 50, new int[]{3, 8, 6, 3}, 14, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5F, 0.0F, () -> Ingredient.of(itemlist.T4_PANAKEIA_GEM.get())),
    T5_GEM("t5_gem", 58, new int[]{3, 8, 6, 3}, 14, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.5F, 0.1F, () -> Ingredient.of(itemlist.T5_PANAKEIA_GEM.get())),
    T6_INGOT("t6_ingot", 65, new int[]{3, 8, 6, 3}, 16, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5F, 0.1F, () -> Ingredient.of(itemlist.T6_PANAKEIA_INGOT.get())),
    UNITE_ALLOY("unite_alloy", 175, new int[]{5, 10, 8, 5}, 30, SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0F, 0.3F, () -> Ingredient.of(itemlist.UNITE_ALLOY.get())),
    IMITATED_BEDROCK("imitated_bedrock", 100000, new int[]{10, 20, 16, 10}, 30, SoundEvents.ARMOR_EQUIP_NETHERITE, 10.0F, 1.0F, () -> Ingredient.of(itemlist.IMITATED_BEDROCK.get())),
    SINGULARITY("singularity",200000,new int []{20,40,32,20},100,SoundEvents.ARMOR_EQUIP_ELYTRA,20.0f,1.0f,() -> Ingredient.of(itemlist.SINGULARITY_INGOT.orElse(Items.AIR)));


    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient.get();
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        int index;
        if(type.getSlot() == EquipmentSlot.HEAD) {
            index = 0;
        } else if(type.getSlot() == EquipmentSlot.CHEST) {
            index = 1;
        } else if(type.getSlot() == EquipmentSlot.LEGS) {
            index = 2;
        } else {
            index = 3;
        }
        return DURABILITY_PER_SLOT[index] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        int index;
        if(type.getSlot() == EquipmentSlot.HEAD) {
            index = 0;
        } else if(type.getSlot() == EquipmentSlot.CHEST) {
            index = 1;
        } else if(type.getSlot() == EquipmentSlot.LEGS) {
            index = 2;
        } else {
            index = 3;
        }
        return this.protectionAmounts[index];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    private static final int[] DURABILITY_PER_SLOT = new int[]{13, 15, 16, 11};
}
