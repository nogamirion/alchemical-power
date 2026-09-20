package jp.nogami_rion.alchemical_power.integration.tinker.modifier;

import jp.nogami_rion.alchemical_power.integration.tinker.TinkersIntegration;
import jp.nogami_rion.alchemical_power.item.ConstellationTreasuryItem;
import jp.nogami_rion.alchemical_power.screen.ConstellationTreasuryMenu;
import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import javax.annotation.Nullable;
import java.util.UUID;

/** Real weapons stay in persistent tool data; projectiles carry only a reference to the tool. */
public class ConstellationTreasuryModifier extends Modifier implements MeleeHitModifierHook,
        ProjectileLaunchModifierHook, ProjectileHitModifierHook, GeneralInteractionModifierHook,
        InventoryTickModifierHook, ModifierRemovalHook {
    public static final ResourceLocation STORAGE = new ResourceLocation("alchemical_power", "constellation_treasury");
    private static final ResourceLocation SOURCE = new ResourceLocation("alchemical_power", "treasury_source");
    private static final String HIT_TARGETS = "AlchemicalPowerTreasuryTargets";

    @Override protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.PROJECTILE_LAUNCH,
                ModifierHooks.PROJECTILE_HIT, ModifierHooks.GENERAL_INTERACT,
                ModifierHooks.INVENTORY_TICK, ModifierHooks.REMOVE);
    }

    // Handle the storage gesture before bow drawing and other default tool interactions.
    @Override public int getPriority() { return 200; }

    @Override public Component getDisplayName() {
        return jp.nogami_rion.alchemical_power.util.RainbowText.name(super.getDisplayName());
    }

    @Override public Component getDisplayName(int level) { return getDisplayName(); }

    public static boolean hasModifier(ItemStack stack) {
        return stack.getItem() instanceof IModifiable
                && ToolStack.from(stack).getModifierLevel(TinkersIntegration.CONSTELLATION_TREASURY_MODIFIER.getId()) > 0;
    }

    @Nullable public static CompoundTag storage(ItemStack stack, boolean create) {
        return storage(ToolStack.from(stack), create);
    }

    @Nullable private static CompoundTag storage(IToolStackView tool, boolean create) {
        ModDataNBT data = tool.getPersistentData();
        if (!data.contains(STORAGE, Tag.TAG_COMPOUND)) {
            if (!create) return null;
            data.put(STORAGE, new CompoundTag());
        }
        return data.get(STORAGE, CompoundTag::getCompound);
    }

    @Override public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player,
                                                 InteractionHand hand, InteractionSource source) {
        if (source != InteractionSource.RIGHT_CLICK || !player.isShiftKeyDown()) return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(hand);
        if (ConstellationTreasuryCombat.isActive(stack)) {
            if (!player.level().isClientSide)
                player.displayClientMessage(Component.translatable("message.alchemical_power.constellation_treasury.busy"), true);
            return InteractionResult.FAIL;
        }
        if (player instanceof ServerPlayer serverPlayer) {
            int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider((id, inv, p) -> new ConstellationTreasuryMenu(id, inv, slot), Component.translatable(getTranslationKey())),
                    buf -> buf.writeVarInt(slot));
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    private static boolean canStart(LivingEntity owner, ItemStack stack) {
        return !owner.level().isClientSide && owner.isAlive() && hasModifier(stack)
                && !ToolStack.from(stack).isBroken()
                && (!(owner instanceof Player player) || (!player.isSpectator() && player.containerMenu == player.inventoryMenu));
    }

    @Override public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity owner = context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        ItemStack stack = owner.getItemBySlot(context.getSlotType());
        // TiC invokes this hook only after an accepted hit. damageDealt is the health delta,
        // which can be zero (absorption) or negative (a target healing inside hurt()).
        if (target != null && !context.isExtraAttack() && canStart(owner, stack))
            ConstellationTreasuryCombat.startSalvo(stack, owner, target);
    }

    @Override public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter,
                                              Projectile projectile, @Nullable AbstractArrow arrow,
                                              ModDataNBT persistentData, boolean primary) {
        if (shooter.level().isClientSide || tool.isBroken()) return;
        CompoundTag data = storage(tool, true);
        if (!data.hasUUID("Id")) data.putUUID("Id", UUID.randomUUID());
        persistentData.putString(SOURCE, data.getUUID("Id").toString());
    }

    /** Resolve the original tool even if the shooter changed selected hotbar slots after firing. */
    private static ItemStack findSource(LivingEntity owner, String id) {
        if (owner instanceof Player player) {
            for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
                ItemStack stack = player.getInventory().getItem(slot);
                if (matchesSource(stack, id)) return stack;
            }
        } else {
            for (ItemStack stack : owner.getHandSlots()) if (matchesSource(stack, id)) return stack;
        }
        return ItemStack.EMPTY;
    }

    private static boolean matchesSource(ItemStack stack, String id) {
        if (!hasModifier(stack)) return false;
        CompoundTag data = storage(stack, false);
        return data != null && data.hasUUID("Id") && data.getUUID("Id").toString().equals(id);
    }

    @Override public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData,
            ModifierEntry modifier, Projectile projectile, EntityHitResult hit,
            @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker == null || target == null || projectile.level().isClientSide
                || !ConstellationTreasuryCombat.canTarget(attacker, target)) return false;
        String id = persistentData.getString(SOURCE);
        if (id.isEmpty()) return false;
        ItemStack stack = findSource(attacker, id);
        if (!canStart(attacker, stack)) return false;
        CompoundTag hitTargets = projectile.getPersistentData().getCompound(HIT_TARGETS);
        String targetId = target.getUUID().toString();
        if (hitTargets.getBoolean(targetId)) return false;
        hitTargets.putBoolean(targetId, true);
        projectile.getPersistentData().put(HIT_TARGETS, hitTargets);
        ConstellationTreasuryCombat.startSalvo(stack, attacker, target);
        return false;
    }

    @Override public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level,
            LivingEntity holder, int slot, boolean selected, boolean correctSlot, ItemStack stack) {
        if (!level.isClientSide) {
            if (tool.isBroken()) ConstellationTreasuryCombat.cancelSalvo(stack);
            else ConstellationTreasuryCombat.tickSalvo(stack, holder);
        }
    }

    @Nullable @Override public Component onRemoved(IToolStackView tool, Modifier modifier) {
        CompoundTag data = storage(tool, false);
        if (data != null && (data.contains("ArsenalSalvo")
                || !data.getCompound("ArsenalInventory").getList("Items", Tag.TAG_COMPOUND).isEmpty()))
            return Component.translatable("modifier.alchemical_power.constellation_treasury.remove_error");
        tool.getPersistentData().remove(STORAGE);
        return null;
    }
}
