package jp.nogami_rion.alchemical_power.item.baseclass;

import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.materials.Singularity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModMaterialTiers implements Tier {
    T1_INGOT(2, 270, 5.0F, 1.0f, 10, () -> Ingredient.of(itemlist.T1_PANAKEIA_INGOT.get())),
    T2_INGOT(2, 375, 6.0F, 2.5f, 15, () -> Ingredient.of(itemlist.T2_PANAKEIA_INGOT.get())),
    T3_INGOT(2, 425, 7.0F, 3.0f, 20, () -> Ingredient.of(itemlist.T3_PANAKEIA_INGOT.get())),
    T4_GEM(3, 2341, 8.5F, 3.5f, 14, () -> Ingredient.of(itemlist.T4_PANAKEIA_GEM.get())),
    T5_GEM(3, 2716, 9.0F, 4.0f, 14, () -> Ingredient.of(itemlist.T5_PANAKEIA_GEM.get())),
    T6_INGOT(4, 3047, 9.5F, 5.5f, 16, () -> Ingredient.of(itemlist.T6_PANAKEIA_INGOT.get())),
    UNITE_ALLOY(6, 8192, 11.0F, 11.0f, 30, () -> Ingredient.of(itemlist.UNITE_ALLOY.get())),
    IMITATED_BEDROCK(Integer.MAX_VALUE, 4681142, 13.0F, 15.0f, 30, () -> Ingredient.of(itemlist.IMITATED_BEDROCK.get())),
    SINGULARITY(Integer.MAX_VALUE,9362284,20.0f,30.0f,60, () -> Ingredient.of(itemlist.SINGULARITY_INGOT.orElse(Items.AIR)));

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;

    ModMaterialTiers(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient.get();
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }
}
