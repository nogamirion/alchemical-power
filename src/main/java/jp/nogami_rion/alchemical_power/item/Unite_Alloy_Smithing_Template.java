package jp.nogami_rion.alchemical_power.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class Unite_Alloy_Smithing_Template extends SmithingTemplateItem {
    public Unite_Alloy_Smithing_Template() {
        super(
                Component.translatable("upgrade.unite_upgrade.applies_to"),
                Component.translatable("upgrade.unite_upgrade.ingredients"),
                Component.translatable("upgrade.unite_upgrade.description"),
                Component.translatable("upgrade.unite_upgrade.base_slot"),
                Component.translatable("upgrade.unite_upgrade.addition_slot"),
                List.of(new ResourceLocation("item/empty_armor_slot_chestplate")),
                List.of(new ResourceLocation("item/empty_slot_ingot"))
        );
    }
}
