package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.blocklist;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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

        logBlock((RotatedPillarBlock) blocklist.ALCHETREE_LOG.get());
        axisBlock(((RotatedPillarBlock) blocklist.ALCHETREE_WOOD.get()),blockTexture(blocklist.ALCHETREE_LOG.get()),blockTexture(blocklist.ALCHETREE_LOG.get()));

        axisBlock(((RotatedPillarBlock) blocklist.STRIPPED_ALCHETREE_LOG.get()),blockTexture(blocklist.STRIPPED_ALCHETREE_LOG.get()),new ResourceLocation(Alchemical_power.MODID,"block/stripped_alchetree_log_top"));
        axisBlock(((RotatedPillarBlock) blocklist.STRIPPED_ALCHETREE_WOOD.get()),blockTexture(blocklist.STRIPPED_ALCHETREE_LOG.get()),blockTexture(blocklist.STRIPPED_ALCHETREE_LOG.get()));

        blockItem(blocklist.ALCHETREE_LOG);
        blockItem(blocklist.ALCHETREE_WOOD);
        blockItem(blocklist.STRIPPED_ALCHETREE_LOG);
        blockItem(blocklist.STRIPPED_ALCHETREE_WOOD);

        blockWithItem(blocklist.ALCHETREE_PLANKS);
        stairsBlock(((StairBlock) blocklist.ALCHETREE_STAIRS.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        slabBlock(((SlabBlock) blocklist.ALCHETREE_SLAB.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        buttonBlock(((ButtonBlock) blocklist.ALCHETREE_BUTTON.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) blocklist.ALCHETREE_PRESSURE_PLATE.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        fenceBlock(((FenceBlock) blocklist.ALCHETREE_FENCE.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) blocklist.ALCHETREE_FENCE_GATE.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        wallBlock(((WallBlock) blocklist.ALCHETREE_WALL.get()),blockTexture(blocklist.ALCHETREE_PLANKS.get()));
        doorBlockWithRenderType(((DoorBlock) blocklist.ALCHETREE_DOOR.get()),modLoc("block/alchetree_door_bottom"),modLoc("block/alchetree_door_top"),"cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) blocklist.ALCHETREE_TRAPDOOR.get()),modLoc("block/alchetree_trapdoor"),true,"cutout");

        for (int tier = 0; tier < 8; tier++) {
            blockWithItem(blocklist.ALCHETREE_LEAVES[tier]);
        }

        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T0);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T1);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T2);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T3);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T4);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T5);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T6);
        saplingBlock(blocklist.ALCHETREE_SAPLINGS_T7);




    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Alchemical_power.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(),models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

}
