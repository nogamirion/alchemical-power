package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import jp.nogami_rion.alchemical_power.util.ModDamageTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BladeOfValor extends SwordItem {
    public BladeOfValor() {
        super(ModMaterialTiers.IMITATED_BEDROCK, 1, 7.0f,  new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
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
                entity.hurt(ModDamageTypes.singularityTrue((ServerLevel)entity.level(),player), 6.0F); // 副ターゲットには特異点ダメージ
            }

            // エフェクト演出（音・粒子など）
            player.level().playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
            ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK,
                    player.getX(), player.getY() + 1.0D, player.getZ(), 5, 0.2D, 0.0D, 0.2D, 0.0D);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        // 耐久を無限にする
        return false;
    }
}