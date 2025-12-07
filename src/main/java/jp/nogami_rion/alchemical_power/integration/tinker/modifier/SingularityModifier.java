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
        Entity targetEntity = context.getTarget();
        LivingEntity attacker = context.getPlayerAttacker();
        if (!targetEntity.level().isClientSide && targetEntity instanceof LivingEntity target) {
            DeadEndRainbowUtils.applyAttackMark(target);
            DeadEndRainbowUtils.checkAttackMark(target,attacker);
        }
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if(target == null) return false;
        if (!target.level().isClientSide && target != attacker && target instanceof LivingEntity) {
            DeadEndRainbowUtils.applyAttackMark(target);
            DeadEndRainbowUtils.checkAttackMark(target,attacker);

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
                DeadEndRainbowUtils.checkAttackMark(trueAttacker,wearer);

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