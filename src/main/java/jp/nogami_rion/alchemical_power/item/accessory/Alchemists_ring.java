package jp.nogami_rion.alchemical_power.item.accessory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class Alchemists_ring extends Item implements ICurioItem {

    // ランダムに生成した固定UUID（クラス内で一意であればOK）
    private static final UUID ATTACK_UUID = UUID.fromString("b79a9d8b-1a45-40a4-84de-f6d6a9b25777");
    private static final UUID ARMOR_UUID  = UUID.fromString("d80f524c-1f7e-41e3-bf4b-5bbf4d8d13f0");

    public Alchemists_ring() {
        super(new Item.Properties().stacksTo(1));
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_UUID, "Curio attack boost", 2.0, AttributeModifier.Operation.ADDITION));
        modifiers.put(Attributes.ARMOR,
                new AttributeModifier(ARMOR_UUID, "Curio armor boost", 4.0, AttributeModifier.Operation.ADDITION));

        return modifiers;
    }

}
