package jp.nogami_rion.alchemical_power.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class Singularity_Ingot_Smithing_Template extends SmithingTemplateItem {
    public Singularity_Ingot_Smithing_Template() {
        super(
                Component.translatable("upgrade.singularity_upgrade.applies_to"),
                Component.translatable("upgrade.singularity_upgrade.ingredients"),
                Component.translatable("upgrade.singularity_upgrade.description"),
                Component.translatable("upgrade.singularity_upgrade.base_slot"),
                Component.translatable("upgrade.singularity_upgrade.addition_slot"),
                List.of(new ResourceLocation("item/empty_armor_slot_chestplate")),
                List.of(new ResourceLocation("item/empty_slot_ingot"))
        );
    }
}
