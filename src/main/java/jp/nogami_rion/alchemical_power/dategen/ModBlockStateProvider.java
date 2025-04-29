package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output , ExistingFileHelper exFileHelper) {
        super(output, Alchemical_power.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(blocklist.PANAKEIA_BEARING_STONE);
        blockWithItem(blocklist.T1_PANAKEIA_INGOT_BLOCK);
        blockWithItem(blocklist.T2_PANAKEIA_INGOT_BLOCK);
        blockWithItem(blocklist.T3_PANAKEIA_INGOT_BLOCK);
        blockWithItem(blocklist.T4_PANAKEIA_GEM_BLOCK);
        blockWithItem(blocklist.T5_PANAKEIA_GEM_BLOCK);
        blockWithItem(blocklist.T6_PANAKEIA_INGOT_BLOCK);
        blockWithItem(blocklist.UNITE_ALLOY_BLOCK);
        blockWithItem(blocklist.X9_COBBLESTONE);
        blockWithItem(blocklist.X225_COBBLESTONE);
        blockWithItem(blocklist.X38025_COBBLESTONE);
        blockWithItem(blocklist.IMITATED_BEDROCK);
        blockWithItem(blocklist.T1_COMBUSTION_RUNE_BLOCK);
        blockWithItem(blocklist.T2_COMBUSTION_RUNE_BLOCK);
        blockWithItem(blocklist.T3_COMBUSTION_RUNE_BLOCK);
        blockWithItem(blocklist.T4_COMBUSTION_RUNE_BLOCK);
        blockWithItem(blocklist.T5_COMBUSTION_RUNE_BLOCK);
        blockWithItem(blocklist.T6_COMBUSTION_RUNE_BLOCK);



    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }


}
