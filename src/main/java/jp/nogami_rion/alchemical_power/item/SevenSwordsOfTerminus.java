package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import jp.nogami_rion.alchemical_power.util.DeadEndRainbowUtils;
import jp.nogami_rion.alchemical_power.util.ModDamageTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

public class SevenSwordsOfTerminus extends SwordItem {
    public SevenSwordsOfTerminus() {
        super(ModMaterialTiers.SINGULARITY, 1, 7.0f,  new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player && !player.level().isClientSide) {
            // 前方直線攻撃：3ブロック範囲
            Vec3 look = player.getLookAngle().normalize();
            AABB area = player.getBoundingBox().expandTowards(look.scale(3)).inflate(1.0, 0.5, 1.0);

            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class, area, e ->
                    e != player && e != target && e.isAlive() && player.hasLineOfSight(e)
            );

            for (LivingEntity entity : entities) {
                entity.hurt(player.damageSources().playerAttack(player), 6.0F); // 副ターゲットには軽減ダメージ
            }

            // エフェクト演出（音・粒子など）
            player.level().playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK,
                    player.getX(), player.getY() + 1.0D, player.getZ(), 5, 0.2D, 0.0D, 0.2D, 0.0D);
        }
        if (!target.level().isClientSide && target instanceof LivingEntity) {
            DeadEndRainbowUtils.applyAttackMark(target);
            DeadEndRainbowUtils.checkAttackMark(target,attacker);

        }
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        // 耐久を無限にする
        return false;
    }
}