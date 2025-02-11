package jp.nogami_rion.alchemical_power.dategen.loot;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(blocklist.STONE_CONTAINING_ALCHEMY_DUST.get()
        ,block -> createOreDrop(blocklist.STONE_CONTAINING_ALCHEMY_DUST.get(),itemlist.T0_ALCHEMY_DUST.get()));
        this.dropSelf(blocklist.ALCHEMY_TABLE.get());
        this.dropSelf(blocklist.HERMES_WORKBENCH.get());
        this.dropSelf(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get());
        this.dropSelf(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get());
        this.dropSelf(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get());
        this.dropSelf(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get());
        this.dropSelf(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get());
        this.dropSelf(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get());
        this.dropSelf(blocklist.UNITE_ALLOY_BLOCK.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T0.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T1.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T2.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T3.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T4.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T5.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T6.get());
        this.dropSelf(blocklist.ALCHEMY_DUST_REED_T7.get());


    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return blocklist.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
