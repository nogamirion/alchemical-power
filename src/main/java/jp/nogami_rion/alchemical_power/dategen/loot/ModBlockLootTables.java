package jp.nogami_rion.alchemical_power.dategen.loot;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(blocklist.PANAKEIA_BEARING_STONE.get()
        ,block -> createOreDrop(blocklist.PANAKEIA_BEARING_STONE.get(),itemlist.T0_PANAKEIA.get()));
        this.dropSelf(blocklist.ALCHEMY_TABLE.get());
        this.dropSelf(blocklist.HERMES_WORKBENCH.get());
        this.dropSelf(blocklist.TRANSCENDENTAL_TABLE.get());
        this.dropSelf(blocklist.ALCHEMICAL_ENGRAVER.get());
        this.dropSelf(blocklist.RUNE_ACTIVATOR.get());
        this.dropSelf(blocklist.T1_PANAKEIA_INGOT_BLOCK.get());
        this.dropSelf(blocklist.T2_PANAKEIA_INGOT_BLOCK.get());
        this.dropSelf(blocklist.T3_PANAKEIA_INGOT_BLOCK.get());
        this.dropSelf(blocklist.T4_PANAKEIA_GEM_BLOCK.get());
        this.dropSelf(blocklist.T5_PANAKEIA_GEM_BLOCK.get());
        this.dropSelf(blocklist.T6_PANAKEIA_INGOT_BLOCK.get());
        this.dropSelf(blocklist.UNITE_ALLOY_BLOCK.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T0.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T1.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T2.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T3.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T4.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T5.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T6.get());
        this.dropSelf(blocklist.PANAKEIA_REED_T7.get());
        this.dropSelf(blocklist.X9_COBBLESTONE.get());
        this.dropSelf(blocklist.X225_COBBLESTONE.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE.get());
        this.dropSelf(blocklist.IMITATED_BEDROCK.get());
        this.dropSelf(blocklist.T1_COMBUSTION_RUNE_BLOCK.get());
        this.dropSelf(blocklist.T2_COMBUSTION_RUNE_BLOCK.get());
        this.dropSelf(blocklist.T3_COMBUSTION_RUNE_BLOCK.get());
        this.dropSelf(blocklist.T4_COMBUSTION_RUNE_BLOCK.get());
        this.dropSelf(blocklist.T5_COMBUSTION_RUNE_BLOCK.get());
        this.dropSelf(blocklist.T6_COMBUSTION_RUNE_BLOCK.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK1.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK2.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK3.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK4.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK5.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK6.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK7.get());
        this.dropSelf(blocklist.COBBLESTONE_GENERATOR_MK8.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK1.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK2.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK3.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK4.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK5.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK6.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK7.get());
        this.dropSelf(blocklist.X9_COBBLESTONE_GENERATOR_MK8.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK1.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK2.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK3.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK4.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK5.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK6.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK7.get());
        this.dropSelf(blocklist.X225_COBBLESTONE_GENERATOR_MK8.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK1.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK2.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK3.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK4.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK5.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK6.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK7.get());
        this.dropSelf(blocklist.X38025_COBBLESTONE_GENERATOR_MK8.get());


    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return blocklist.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
