package jp.nogami_rion.alchemical_power.integration.tinker;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class TinkerArsenalBridge {
    private static final String HOOKS_CLASS = "jp.nogami_rion.alchemical_power.integration.tinker.ArsenalTinkerCombat";
    private static Method getMeleeDamage;
    private static Method beforeMeleeHit;
    private static Method afterMeleeHit;
    private static Method failedMeleeHit;
    private static boolean initialized;

    private TinkerArsenalBridge() {
    }

    public static float getMeleeDamage(ItemStack weapon, LivingEntity owner, LivingEntity target, float baseDamage, float damage) {
        if (!available()) {
            return damage;
        }
        try {
            return (float) getMeleeDamage.invoke(null, weapon, owner, target, baseDamage, damage);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return damage;
        }
    }

    public static float beforeMeleeHit(ItemStack weapon, LivingEntity owner, LivingEntity target, float damage) {
        if (!available()) {
            return damage;
        }
        try {
            return (float) beforeMeleeHit.invoke(null, weapon, owner, target, damage);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return damage;
        }
    }

    public static void afterMeleeHit(ItemStack weapon, LivingEntity owner, LivingEntity target, float damageDealt) {
        if (!available()) {
            return;
        }
        try {
            afterMeleeHit.invoke(null, weapon, owner, target, damageDealt);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    public static void failedMeleeHit(ItemStack weapon, LivingEntity owner, LivingEntity target, float damageAttempted) {
        if (!available()) {
            return;
        }
        try {
            failedMeleeHit.invoke(null, weapon, owner, target, damageAttempted);
        } catch (IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    private static boolean available() {
        if (!ModList.get().isLoaded("tconstruct")) {
            return false;
        }
        if (initialized) {
            return getMeleeDamage != null;
        }
        initialized = true;
        try {
            Class<?> hooks = Class.forName(HOOKS_CLASS);
            getMeleeDamage = hooks.getMethod("getMeleeDamage", ItemStack.class, LivingEntity.class, LivingEntity.class, float.class, float.class);
            beforeMeleeHit = hooks.getMethod("beforeMeleeHit", ItemStack.class, LivingEntity.class, LivingEntity.class, float.class);
            afterMeleeHit = hooks.getMethod("afterMeleeHit", ItemStack.class, LivingEntity.class, LivingEntity.class, float.class);
            failedMeleeHit = hooks.getMethod("failedMeleeHit", ItemStack.class, LivingEntity.class, LivingEntity.class, float.class);
            return true;
        } catch (ReflectiveOperationException | LinkageError e) {
            getMeleeDamage = null;
            beforeMeleeHit = null;
            afterMeleeHit = null;
            failedMeleeHit = null;
            return false;
        }
    }
}
