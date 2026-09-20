package jp.nogami_rion.alchemical_power.integration.tinker;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public final class ArsenalTinkerCombat {
    private ArsenalTinkerCombat() {
    }

    public static float getMeleeDamage(ItemStack weapon, LivingEntity owner, LivingEntity target, float baseDamage, float damage) {
        ToolStack tool = tool(weapon);
        if (tool == null) {
            return damage;
        }
        ToolAttackContext context = context(owner, target);
        float modifiedDamage = damage;
        for (ModifierEntry modifier : tool.getModifierList()) {
            modifiedDamage = modifier.getHook(ModifierHooks.MELEE_DAMAGE).getMeleeDamage(tool, modifier, context, baseDamage, modifiedDamage);
        }
        tool.updateStack(weapon);
        return modifiedDamage;
    }

    public static float beforeMeleeHit(ItemStack weapon, LivingEntity owner, LivingEntity target, float damage) {
        ToolStack tool = tool(weapon);
        if (tool == null) {
            return damage;
        }
        ToolAttackContext context = context(owner, target);
        float modifiedDamage = damage;
        for (ModifierEntry modifier : tool.getModifierList()) {
            modifiedDamage = modifier.getHook(ModifierHooks.MELEE_HIT).beforeMeleeHit(tool, modifier, context, modifiedDamage, modifiedDamage, modifiedDamage);
        }
        tool.updateStack(weapon);
        return modifiedDamage;
    }

    public static void afterMeleeHit(ItemStack weapon, LivingEntity owner, LivingEntity target, float damageDealt) {
        ToolStack tool = tool(weapon);
        if (tool == null) {
            return;
        }
        ToolAttackContext context = context(owner, target);
        for (ModifierEntry modifier : tool.getModifierList()) {
            // Synthetic arsenal hits must not open another treasury (or use the owner's held tool).
            if (modifier.getModifier() instanceof jp.nogami_rion.alchemical_power.integration.tinker.modifier.ConstellationTreasuryModifier) continue;
            modifier.getHook(ModifierHooks.MELEE_HIT).afterMeleeHit(tool, modifier, context, damageDealt);
        }
        tool.updateStack(weapon);
    }

    public static void failedMeleeHit(ItemStack weapon, LivingEntity owner, LivingEntity target, float damageAttempted) {
        ToolStack tool = tool(weapon);
        if (tool == null) {
            return;
        }
        ToolAttackContext context = context(owner, target);
        for (ModifierEntry modifier : tool.getModifierList()) {
            modifier.getHook(ModifierHooks.MELEE_HIT).failedMeleeHit(tool, modifier, context, damageAttempted);
        }
        tool.updateStack(weapon);
    }

    private static ToolStack tool(ItemStack weapon) {
        if (!(weapon.getItem() instanceof IModifiable)) {
            return null;
        }
        ToolStack tool = ToolStack.from(weapon);
        return tool.isBroken() ? null : tool;
    }

    private static ToolAttackContext context(LivingEntity owner, LivingEntity target) {
        Player player = owner instanceof Player ownerPlayer ? ownerPlayer : null;
        return new ToolAttackContext(owner, player, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND, target, target, false, 1.0F, false);
    }
}
