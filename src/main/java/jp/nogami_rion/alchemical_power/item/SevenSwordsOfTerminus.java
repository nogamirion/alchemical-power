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
            int count = DeadEndRainbowUtils.getAttackMark(target);

            // パーティクルの色リスト
            List<Vec3> colors = List.of(
                    new Vec3(1.0, 0.0, 0.0), // 赤
                    new Vec3(1.0, 0.5, 0.0), // 橙
                    new Vec3(1.0, 1.0, 0.0), // 黄
                    new Vec3(0.0, 1.0, 0.0), // 緑
                    new Vec3(0.0, 1.0, 1.0), // 水
                    new Vec3(0.0, 0.0, 1.0), // 青
                    new Vec3(0.5, 0.0, 1.0)  // 紫
            );

            if (target.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < count && i < colors.size(); i++) {
                    Vec3 color = colors.get(i);
                    serverLevel.sendParticles(
                            new DustParticleOptions(new Vector3f((float) color.x, (float) color.y, (float) color.z), 2.0f),
                            target.getX(), target.getY() + 1.0, target.getZ(),
                            5, 0.2, 0.2, 0.2, 0.0
                    );
                }
            }

            if (count >= 7) {
                ServerLevel level = (ServerLevel) target.level();
                DamageSource singularity = ModDamageTypes.singularityTrue(level, attacker);
                float maxHealth = target.getMaxHealth();

                target.hurt(singularity, maxHealth * 2.0f);// 最大HPの2倍のダメージ

                if (target.isAlive()) {
                    target.kill(); // Entity.kill() はエンティティ固有の即死処理
                }

                if (target.isAlive()) {
                    target.setHealth(0.0F);
                }

                DeadEndRainbowUtils.resetAttackMark(target); // スタックリセット
            }

        }
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        // 耐久を無限にする
        return false;
    }
}