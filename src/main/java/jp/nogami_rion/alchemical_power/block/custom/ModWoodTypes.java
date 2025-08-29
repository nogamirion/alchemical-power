package jp.nogami_rion.alchemical_power.block.custom;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final BlockSetType ALCHE_TREE_BLOCK_SET = BlockSetType.register(new BlockSetType("alche_tree"));

    public static final WoodType ALCHE_TREE = WoodType.register(new WoodType("alche_tree", ALCHE_TREE_BLOCK_SET));
}
