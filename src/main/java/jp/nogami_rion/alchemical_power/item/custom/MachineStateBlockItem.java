package jp.nogami_rion.alchemical_power.item.custom;

import jp.nogami_rion.alchemical_power.item.mec.UpgradeItem;
import jp.nogami_rion.alchemical_power.item.mec.UpgradeType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MachineStateBlockItem extends BlockItem {
    private static final String BLOCK_ENTITY_TAG = "BlockEntityTag";

    public MachineStateBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (!stack.hasTag() || !stack.getTag().contains(BLOCK_ENTITY_TAG)) {
            return;
        }

        CompoundTag tag = stack.getTag().getCompound(BLOCK_ENTITY_TAG);
        if(getBlock() instanceof jp.nogami_rion.alchemical_power.block.ElectricRuneActivatorBlock) {
            var savedItems=tag.getCompound("Inventory").getList("Items",net.minecraft.nbt.Tag.TAG_COMPOUND);
            for(int i=0;i<savedItems.size();i++) {
                var saved=savedItems.getCompound(i);
                if(saved.getInt("Slot")!=0)continue;
                ItemStack chip=ItemStack.of(saved);
                if(chip.getItem() instanceof jp.nogami_rion.alchemical_power.item.RuneCoreChipItem)
                    tooltip.add(Component.translatable("tooltip.alchemical_power.machine.core_chip",chip.getHoverName()).withStyle(ChatFormatting.AQUA));
                break;
            }
        }
        addStoredAmount(tooltip, tag, "Energy", "MaxEnergy", "tooltip.alchemical_power.machine.energy", " FE", ChatFormatting.GREEN);
        addStoredAmount(tooltip, tag, "WaterTank", "WaterCapacity", "tooltip.alchemical_power.machine.water", " mB", ChatFormatting.BLUE);
        addStoredAmount(tooltip, tag, "OutputTank", "OutputCapacity", "tooltip.alchemical_power.machine.liquid_panakeia", " mB", ChatFormatting.BLUE);
        addStoredItem(tooltip, tag);

        if (tag.contains("Upgrades")) {
            CompoundTag upgrades = tag.getCompound("Upgrades");
            addUpgradeItemNameTooltip(tooltip, upgrades, "ToolUpgrade", UpgradeType.TOOL, "tooltip.alchemical_power.machine.tool_upgrade");
            addUpgradeTooltip(tooltip, upgrades, "SpeedUpgrade", UpgradeType.SPEED, "tooltip.alchemical_power.machine.speed_upgrade");
            addUpgradeTooltip(tooltip, upgrades, "EfficiencyUpgrade", UpgradeType.EFFICIENCY, "tooltip.alchemical_power.machine.efficiency_upgrade");
            addUpgradeTooltip(tooltip, upgrades, "EnergyUpgrade", UpgradeType.ENERGY, "tooltip.alchemical_power.machine.energy_upgrade");
            addUpgradeTooltip(tooltip, upgrades, "TankUpgrade", UpgradeType.TANK, "tooltip.alchemical_power.machine.tank_upgrade");
        }
    }

    private void addStoredAmount(List<Component> tooltip, CompoundTag tag, String amountKey, String maxKey,
                                 String labelKey, String unit, ChatFormatting labelColor) {
        if (!tag.contains(amountKey)) {
            return;
        }

        int amount = amountKey.endsWith("Tank") ? tag.getCompound(amountKey).getInt("Amount") : tag.getInt(amountKey);
        int max = tag.contains(maxKey) ? tag.getInt(maxKey) : 0;
        String value = max > 0 ? format(amount) + "/" + format(max) + unit : format(amount) + unit;
        tooltip.add(labeledLine(labelKey, labelColor, value));
    }

    private void addUpgradeTooltip(List<Component> tooltip, CompoundTag upgrades, String tagName, UpgradeType type, String labelKey) {
        String tiers = getUpgradeTiers(upgrades, tagName, type);
        if (!tiers.isEmpty()) {
            tooltip.add(labeledLine(labelKey, ChatFormatting.BLUE, tiers));
        }
    }

    private void addUpgradeItemNameTooltip(List<Component> tooltip, CompoundTag upgrades, String tagName, UpgradeType type, String labelKey) {
        Component names = getUpgradeItemNames(upgrades, tagName, type);
        if (names != null) {
            tooltip.add(Component.translatable(labelKey)
                    .withStyle(ChatFormatting.BLUE)
                    .append(Component.literal(" ").withStyle(ChatFormatting.GRAY))
                    .append(names));
        }
    }

    private String getUpgradeTiers(CompoundTag upgrades, String tagName, UpgradeType type) {
        StringBuilder tiers = new StringBuilder();
        appendUpgradeTier(tiers, upgrades, tagName, type);
        for (int i = 1; i <= 9; i++) {
            appendUpgradeTier(tiers, upgrades, tagName + i, type);
        }
        return tiers.toString();
    }

    private void appendUpgradeTier(StringBuilder tiers, CompoundTag upgrades, String tagName, UpgradeType type) {
        int tier = getUpgradeTier(upgrades, tagName, type);
        if (tier <= 0) {
            return;
        }
        if (!tiers.isEmpty()) {
            tiers.append(", ");
        }
        tiers.append("Tier").append(tier);
    }

    private int getUpgradeTier(CompoundTag upgrades, String tagName, UpgradeType type) {
        if (!upgrades.contains(tagName)) {
            return 0;
        }

        ItemStack upgradeStack = ItemStack.of(upgrades.getCompound(tagName));
        if (upgradeStack.getItem() instanceof UpgradeItem upgrade && upgrade.getType() == type) {
            return upgrade.getTier();
        }
        return 0;
    }

    private Component getUpgradeItemNames(CompoundTag upgrades, String tagName, UpgradeType type) {
        Component names = null;
        names = appendUpgradeItemName(names, upgrades, tagName, type);
        for (int i = 1; i <= 9; i++) {
            names = appendUpgradeItemName(names, upgrades, tagName + i, type);
        }
        return names;
    }

    private Component appendUpgradeItemName(Component names, CompoundTag upgrades, String tagName, UpgradeType type) {
        if (!upgrades.contains(tagName)) {
            return names;
        }

        ItemStack upgradeStack = ItemStack.of(upgrades.getCompound(tagName));
        if (!(upgradeStack.getItem() instanceof UpgradeItem upgrade) || upgrade.getType() != type) {
            return names;
        }

        Component name = upgradeStack.getHoverName().copy().withStyle(ChatFormatting.GRAY);
        if (names == null) {
            return name;
        }
        return names.copy()
                .append(Component.literal(", ").withStyle(ChatFormatting.GRAY))
                .append(name);
    }

    private Component labeledLine(String labelKey, ChatFormatting labelColor, String value) {
        return Component.translatable(labelKey)
                .withStyle(labelColor)
                .append(Component.literal(" " + value).withStyle(ChatFormatting.GRAY));
    }

    private void addStoredItem(List<Component> tooltip, CompoundTag tag) {
        if (!tag.contains("StoredItem") || !tag.contains("StoredItemCount")) {
            return;
        }

        ItemStack storedItem = ItemStack.of(tag.getCompound("StoredItem"));
        long count = tag.getLong("StoredItemCount");
        if (storedItem.isEmpty() || count <= 0) {
            return;
        }

        tooltip.add(Component.translatable("tooltip.alchemical_power.machine.stored_item")
                .withStyle(ChatFormatting.BLUE)
                .append(Component.literal(" ").withStyle(ChatFormatting.GRAY))
                .append(storedItem.getHoverName().copy().withStyle(ChatFormatting.GRAY))
                .append(Component.literal(" x " + format(count)).withStyle(ChatFormatting.GRAY)));
    }

    private String format(int value) {
        return String.format("%,d", value);
    }

    private String format(long value) {
        return String.format("%,d", value);
    }
}
