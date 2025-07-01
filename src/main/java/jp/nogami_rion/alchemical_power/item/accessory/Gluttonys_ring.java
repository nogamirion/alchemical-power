package jp.nogami_rion.alchemical_power.item.accessory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class Gluttonys_ring extends Item implements ICurioItem {

    public static final UUID HEALTH_MODIFIER = UUID.fromString("f7b3e0d8-1c9a-4b5f-8e2d-6a0c5b1d4e7f");
    private int lastFoodLevel = -1;

    public Gluttonys_ring() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) return;

        int current = player.getFoodData().getFoodLevel();

        CompoundTag tag = stack.getOrCreateTag();
        int last = tag.getInt("LastFood");

        if (current > last) {
            if(CuriosApi.getCuriosHelper().findEquippedCurio(itemlist.GLUTTONYS_RING.get(), player).isPresent()) {
                // 満腹度が上がった＝何か食べた
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
                player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600, 1));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 1));
            }
        }

        tag.putInt("LastFood", current);
    }



    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();


        modifiers.put(Attributes.MAX_HEALTH,
                new AttributeModifier(HEALTH_MODIFIER, "Gluttony's Ring Health Boost", 20.0, AttributeModifier.Operation.ADDITION));


        return modifiers;
    }

}
