package jp.nogami_rion.alchemical_power.item;

import com.google.common.collect.Multimap;
import jp.nogami_rion.alchemical_power.entity.ThrownSwordEntity;
import jp.nogami_rion.alchemical_power.util.ModItemTags;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class ArsenalBowItem extends BowItem {
    private static final int USE_DURATION = 72000;

    public ArsenalBowItem() {
        super(new Item.Properties().stacksTo(1).durability(768).rarity(Rarity.EPIC));
    }

    @Override
    public void releaseUsing(ItemStack bowStack, Level level, net.minecraft.world.entity.LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        int charge = this.getUseDuration(bowStack) - timeLeft;
        float power = getPowerForTime(charge);
        if (power < 0.1F) {
            return;
        }

        ProjectileSlot projectileSlot = findProjectileItem(player.getInventory());
        if (projectileSlot == null) {
            return;
        }

        ItemStack swordStack = projectileSlot.stack().copy();
        swordStack.setCount(1);

        if (!level.isClientSide) {
            ThrownSwordEntity thrownSword = new ThrownSwordEntity(level, player, swordStack, EnchantmentHelper.getLoyalty(bowStack), EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bowStack), power);
            thrownSword.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3.0F, 1.0F);
            level.addFreshEntity(thrownSword);

            if (!player.getAbilities().instabuild) {
                projectileSlot.stack().shrink(1);
                bowStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
            }
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack bowStack = player.getItemInHand(hand);
        if (findProjectileItem(player.getInventory()) == null) {
            return InteractionResultHolder.fail(bowStack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(bowStack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.INFINITY_ARROWS) {
            return false;
        }
        return enchantment == Enchantments.LOYALTY || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ListTag enchantments = EnchantedBookItem.getEnchantments(book);
        ResourceLocation loyaltyId = ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.LOYALTY);
        ResourceLocation infinityId = ForgeRegistries.ENCHANTMENTS.getKey(Enchantments.INFINITY_ARROWS);
        boolean hasLoyalty = false;
        for (int i = 0; i < enchantments.size(); i++) {
            ResourceLocation enchantmentId = EnchantmentHelper.getEnchantmentId(enchantments.getCompound(i));
            if (infinityId != null && infinityId.equals(enchantmentId)) {
                return false;
            }
            if (loyaltyId != null && loyaltyId.equals(enchantmentId)) {
                hasLoyalty = true;
            }
        }
        return hasLoyalty || super.isBookEnchantable(stack, book);
    }

    public static ItemStack findProjectileSword(Inventory inventory) {
        ProjectileSlot projectileSlot = findProjectileItem(inventory);
        return projectileSlot == null ? ItemStack.EMPTY : projectileSlot.stack();
    }

    public static float getFullChargeDamage(ItemStack bowStack, ItemStack swordStack) {
        float damage = getProjectileBaseDamage(swordStack);
        damage += EnchantmentHelper.getDamageBonus(swordStack, MobType.UNDEFINED);
        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bowStack);
        if (powerLevel > 0) {
            damage += (float) powerLevel * 0.5F + 0.5F;
        }
        return damage * 1.25F;
    }

    public static float getProjectileBaseDamage(ItemStack stack) {
        if (stack.getItem() instanceof SwordItem swordItem) {
            return swordItem.getDamage();
        }
        return getMainHandAttackDamage(stack);
    }

    private static ProjectileSlot findProjectileItem(Inventory inventory) {
        for (int slot = 9; slot < inventory.items.size(); slot++) {
            ItemStack stack = inventory.items.get(slot);
            if (isProjectileItem(stack)) {
                return new ProjectileSlot(stack);
            }
        }

        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = inventory.items.get(slot);
            if (isProjectileItem(stack)) {
                return new ProjectileSlot(stack);
            }
        }

        return null;
    }

    public static boolean isProjectileItem(ItemStack stack) {
        if (stack.isEmpty() || stack.getItem() instanceof ArsenalBowItem || stack.is(ModItemTags.ARSENAL_BOW_PROJECTILE_BLACKLIST)) {
            return false;
        }
        if (stack.getItem() instanceof SwordItem || stack.is(ModItemTags.ARSENAL_BOW_PROJECTILES)) {
            return true;
        }
        return stack.getMaxStackSize() == 1 && getMainHandAttackDamage(stack) > 1.0F;
    }

    private static float getMainHandAttackDamage(ItemStack stack) {
        float damage = 0.0F;
        Multimap<Attribute, AttributeModifier> modifiers = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        for (AttributeModifier modifier : modifiers.get(Attributes.ATTACK_DAMAGE)) {
            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                damage += (float) modifier.getAmount();
            }
        }
        return damage;
    }

    private record ProjectileSlot(ItemStack stack) {
    }
}
