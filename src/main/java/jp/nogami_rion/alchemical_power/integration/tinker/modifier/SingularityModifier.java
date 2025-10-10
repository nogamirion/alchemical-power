package jp.nogami_rion.alchemical_power.integration.tinker.modifier;

import jp.nogami_rion.alchemical_power.util.DeadEndRainbowUtils;
import jp.nogami_rion.alchemical_power.util.ModDamageTypes;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ProtectionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.DurabilityShieldModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.shared.TinkerEffects;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class SingularityModifier extends Modifier implements ProjectileHitModifierHook, MeleeHitModifierHook, OnAttackedModifierHook, ProtectionModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.MELEE_HIT, ModifierHooks.ON_ATTACKED,ModifierHooks.PROTECTION);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getPlayerAttacker();
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
                    target.die(singularity);
                }

                DeadEndRainbowUtils.resetAttackMark(target); // スタックリセット
            }
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (!target.level().isClientSide && target != attacker && target instanceof LivingEntity) {
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
                    target.die(singularity);
                }

                DeadEndRainbowUtils.resetAttackMark(target); // スタックリセット
            }

        }
        return false;
    }

    @Override
    public void onAttacked(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount, boolean b) {
        LivingEntity wearer = equipmentContext.getEntity();
        Entity attacker = null;
        Entity direct = damageSource.getDirectEntity();
        if(direct != null){
            if(direct instanceof Projectile proj){
                Entity owner = proj.getOwner();
                if(owner != null) attacker = owner;
            }
        }
        if(attacker == null) attacker = damageSource.getEntity();
        if(attacker == null) attacker = direct;
        if(attacker == null) return;

        if(iToolStackView.hasTag(TinkerTags.Items.ARMOR) && attacker instanceof LivingEntity trueAttacker && trueAttacker != wearer){
            if (!trueAttacker.level().isClientSide && attacker instanceof LivingEntity) {

                float reflected = amount * 2.0F;
                trueAttacker.hurt(damageSource, reflected);


                DeadEndRainbowUtils.applyAttackMark(trueAttacker);
                int count = DeadEndRainbowUtils.getAttackMark(trueAttacker);

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

                if (trueAttacker.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < count && i < colors.size(); i++) {
                        Vec3 color = colors.get(i);
                        serverLevel.sendParticles(
                                new DustParticleOptions(new Vector3f((float) color.x, (float) color.y, (float) color.z), 2.0f),
                                trueAttacker.getX(), trueAttacker.getY() + 1.0, trueAttacker.getZ(),
                                5, 0.2, 0.2, 0.2, 0.0
                        );
                    }
                }

                if (count >= 7) {
                    ServerLevel level = (ServerLevel) trueAttacker.level();
                    DamageSource singularity = ModDamageTypes.singularityTrue(level, wearer);
                    float maxHealth = trueAttacker.getMaxHealth();

                    trueAttacker.hurt(singularity, maxHealth * 2.0f);// 最大HPの2倍のダメージ

                    if (trueAttacker.isAlive()) {
                        trueAttacker.kill(); // Entity.kill() はエンティティ固有の即死処理
                    }

                    if (trueAttacker.isAlive()) {
                        trueAttacker.setHealth(0.0F);
                        trueAttacker.die(singularity);
                    }

                    DeadEndRainbowUtils.resetAttackMark(trueAttacker); // スタックリセット
                }

            }
        }

    }

    @Override
    public float getProtectionModifier(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v) {
        return Integer.MAX_VALUE;
    }

    @Override
    public @NotNull Component getDisplayName(int level) {
        float hue = (System.currentTimeMillis() % 5000L) / 5000f;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return Component.literal(this.getDisplayName().getString()).withStyle(style -> style.withColor(TextColor.fromRgb(rgb)));
    }

}
