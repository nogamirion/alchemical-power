package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.item.baseclass.ModMaterialTiers;
import jp.nogami_rion.alchemical_power.screen.ConstellationTreasuryMenu;
import jp.nogami_rion.alchemical_power.util.ConstellationTreasuryCombat;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import java.util.List;

/** The real weapons remain in this stack; summoned entities are visual copies only. */
public class ConstellationTreasuryItem extends SwordItem {
    public static final int SLOTS = 36;
    private static final String INVENTORY = "ArsenalInventory";

    public ConstellationTreasuryItem() {
        // Base player damage (1) + singularity bonus (30) + sword damage (5) = 36.
        super(ModMaterialTiers.SINGULARITY, SLOTS - 1 - (int) ModMaterialTiers.SINGULARITY.getAttackDamageBonus(),
                -2.4F, new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    @Override public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new net.minecraftforge.client.extensions.common.IClientItemExtensions() {
            @Override public net.minecraft.client.gui.Font getFont(ItemStack stack, FontContext context) {
                return context == FontContext.SELECTED_ITEM_NAME
                        ? jp.nogami_rion.alchemical_power.client.RainbowWaveText.SELECTED_ITEM_FONT : null;
            }
        });
    }

    public static boolean accepts(ItemStack stack) {
        return !isTreasury(stack) && ArsenalBowItem.isProjectileItem(stack);
    }

    public static boolean isTreasury(ItemStack stack) {
        return stack.getItem() instanceof ConstellationTreasuryItem
                || jp.nogami_rion.alchemical_power.integration.tinker.TinkerTreasuryBridge.hasModifier(stack);
    }

    private static CompoundTag storage(ItemStack stack, boolean create) {
        if (jp.nogami_rion.alchemical_power.integration.tinker.TinkerTreasuryBridge.hasModifier(stack))
            return jp.nogami_rion.alchemical_power.integration.tinker.TinkerTreasuryBridge.storage(stack, create);
        return create ? stack.getOrCreateTag() : stack.getTag();
    }

    @Nullable public static CompoundTag data(ItemStack stack, String key) {
        CompoundTag storage = storage(stack, false);
        return storage != null && storage.contains(key, net.minecraft.nbt.Tag.TAG_COMPOUND) ? storage.getCompound(key) : null;
    }

    public static CompoundTag createData(ItemStack stack, String key) {
        CompoundTag storage = storage(stack, true);
        if (!storage.contains(key, net.minecraft.nbt.Tag.TAG_COMPOUND)) storage.put(key, new CompoundTag());
        return storage.getCompound(key);
    }

    public static void removeData(ItemStack stack, String key) {
        if (stack.getItem() instanceof ConstellationTreasuryItem) {
            stack.removeTagKey(key);
            return;
        }
        CompoundTag storage = storage(stack, false);
        if (storage != null) storage.remove(key);
    }

    public static ItemStackHandler inventory(ItemStack sword) {
        ItemStackHandler handler = new ItemStackHandler(SLOTS) {
            @Override public int getSlotLimit(int slot) { return 1; }
            @Override public boolean isItemValid(int slot, ItemStack stack) { return accepts(stack); }
            @Override protected void onContentsChanged(int slot) { saveInventory(sword, this); }
        };
        // Keep the slot count fixed, including when loading externally edited NBT.
        CompoundTag saved = data(sword, INVENTORY);
        if (saved != null) {
            CompoundTag normalized = saved.copy();
            normalized.putInt("Size", SLOTS);
            handler.deserializeNBT(normalized);
        }
        return handler;
    }

    public static void saveInventory(ItemStack sword, ItemStackHandler handler) {
        storage(sword, true).put(INVENTORY, handler.serializeNBT());
    }

    @Override public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isShiftKeyDown()) return InteractionResultHolder.pass(stack);
        if (ConstellationTreasuryCombat.isActive(stack)) {
            if (!level.isClientSide) player.displayClientMessage(Component.translatable("message.alchemical_power.constellation_treasury.busy"), true);
            return InteractionResultHolder.fail(stack);
        }
        if (player instanceof ServerPlayer serverPlayer) {
            int slot = hand == InteractionHand.MAIN_HAND ? player.getInventory().selected : 40;
            NetworkHooks.openScreen(serverPlayer,
                    new SimpleMenuProvider((id, inv, p) -> new ConstellationTreasuryMenu(id, inv, slot), stack.getHoverName()),
                    buf -> buf.writeVarInt(slot));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    @Override public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!player.level().isClientSide) ConstellationTreasuryCombat.attack(player);
        return true; // The custom attack is the only damage path, avoiding a second vanilla hit.
    }

    @Override public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && entity instanceof LivingEntity owner) ConstellationTreasuryCombat.tickSalvo(stack, owner);
    }

    @Override public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide) ConstellationTreasuryCombat.cancelSalvo(stack);
        return false;
    }

    // A storage-bearing sword must not break and destroy its contents. Stored weapons do wear out.
    @Override public boolean isDamageable(ItemStack stack) { return false; }
    @Override public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) { return true; }

    @Override public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (slotChanged || !ItemStack.isSameItem(oldStack, newStack)) return true;
        // Salvo deadlines and stored-weapon durability are not a change of held weapon.
        ItemStack oldAppearance = oldStack.copy();
        ItemStack newAppearance = newStack.copy();
        oldAppearance.removeTagKey(INVENTORY);
        newAppearance.removeTagKey(INVENTORY);
        ConstellationTreasuryCombat.cancelSalvo(oldAppearance);
        ConstellationTreasuryCombat.cancelSalvo(newAppearance);
        return !ItemStack.matches(oldAppearance, newAppearance);
    }

    @Override public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        var weapons = inventory(stack);
        int count = 0;
        double damage = 0;
        for (int slot = 0; slot < weapons.getSlots(); slot++) {
            ItemStack weapon = weapons.getStackInSlot(slot);
            if (weapon.isEmpty()) continue;
            count += weapon.getCount();
            if (accepts(weapon)) damage += ArsenalBowItem.getProjectileBaseDamage(weapon);
        }
        String total = java.math.BigDecimal.valueOf(damage).setScale(2, java.math.RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        lines.add(Component.translatable("tooltip.alchemical_power.constellation_treasury.count", count, SLOTS).withStyle(ChatFormatting.GRAY));
        lines.add(Component.translatable("tooltip.alchemical_power.constellation_treasury.total_damage", total).withStyle(ChatFormatting.GRAY));
    }
}
