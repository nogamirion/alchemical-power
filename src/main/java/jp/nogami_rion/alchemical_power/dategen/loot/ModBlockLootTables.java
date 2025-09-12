package jp.nogami_rion.alchemical_power.dategen.loot;

import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import static net.minecraft.world.level.storage.loot.predicates.AnyOfCondition.anyOf;

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

        this.dropSelf(blocklist.ALCHETREE_LOG.get());
        this.dropSelf(blocklist.ALCHETREE_WOOD.get());
        this.dropSelf(blocklist.STRIPPED_ALCHETREE_LOG.get());
        this.dropSelf(blocklist.STRIPPED_ALCHETREE_WOOD.get());
        this.dropSelf(blocklist.ALCHETREE_PLANKS.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T0.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T1.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T2.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T3.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T4.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T5.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T6.get());
        this.dropSelf(blocklist.ALCHETREE_SAPLINGS_T7.get());

        this.dropSelf(blocklist.INFINITE_WATER_BARREL.get());
        this.dropSelf(blocklist.INFINITE_LAVA_BARREL.get());


        this.add(blocklist.ALCHETREE_LEAVES[0].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T0.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T0_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[1].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T1.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T1_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[2].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T2.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T2_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[3].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T3.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T3_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[4].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T4.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T4_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[5].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T5.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T5_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[6].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T6.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T6_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );
        this.add(blocklist.ALCHETREE_LEAVES[7].get(),block -> createLeavesDrops(block,blocklist.ALCHETREE_SAPLINGS_T7.get(),
                NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0f))
                        .when(anyOf(HAS_SHEARS,HAS_SILK_TOUCH).invert())
                        .when(LootItemRandomChanceCondition.randomChance(0.25f))
                        .add(applyExplosionCondition(block, LootItem.lootTableItem(itemlist.T7_PANAKEIA.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f,2.0f)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE,1)))))
        );

        this.dropSelf(blocklist.ALCHETREE_STAIRS.get());
        this.dropSelf(blocklist.ALCHETREE_BUTTON.get());
        this.dropSelf(blocklist.ALCHETREE_PRESSURE_PLATE.get());
        this.dropSelf(blocklist.ALCHETREE_FENCE.get());
        this.dropSelf(blocklist.ALCHETREE_FENCE_GATE.get());
        this.dropSelf(blocklist.ALCHETREE_WALL.get());
        this.dropSelf(blocklist.ALCHETREE_TRAPDOOR.get());

        this.add(blocklist.ALCHETREE_SLAB.get(), block -> createSlabItemTable(blocklist.ALCHETREE_SLAB.get()));
        this.add(blocklist.ALCHETREE_DOOR.get(), block -> createDoorTable(blocklist.ALCHETREE_DOOR.get()));

    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return blocklist.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
