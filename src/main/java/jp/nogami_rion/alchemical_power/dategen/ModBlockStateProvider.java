package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output , ExistingFileHelper exFileHelper) {
        super(output, Alchemical_power.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(blocklist.STONE_CONTAINING_ALCHEMY_DUST);
        blockWithItem(blocklist.ALCHEMICAL_PROCESSING_COPPER_BLOCK);
        blockWithItem(blocklist.ALCHEMICAL_PROCESSING_IRON_BLOCK);
        blockWithItem(blocklist.ALCHEMICAL_PROCESSING_GOLD_BLOCK);
        blockWithItem(blocklist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK);
        blockWithItem(blocklist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK);
        blockWithItem(blocklist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK);
        blockWithItem(blocklist.UNITE_ALLOY_BLOCK);


    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }


}
