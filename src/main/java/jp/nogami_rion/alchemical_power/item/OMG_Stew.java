package jp.nogami_rion.alchemical_power.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class OMG_Stew extends Item {
    public OMG_Stew() {
        super(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC).food((new FoodProperties.Builder()).nutrition(64).saturationMod(200.0f).alwaysEat().build()));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.EAT;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            // 多重バフの付与（プレイヤー限定）
            if (entity instanceof net.minecraft.world.entity.player.Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 600, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 600, 2));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 600, 1));
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 600, 3));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 600, 4));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 600, 1));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 600, 0));
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 600, 0));
                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20 * 600, 0));
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

}
