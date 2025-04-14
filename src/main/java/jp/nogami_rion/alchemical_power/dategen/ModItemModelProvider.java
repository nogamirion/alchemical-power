package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Alchemical_power.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(itemlist.T0_PANAKEIA);
        simpleItem(itemlist.T1_PANAKEIA);
        simpleItem(itemlist.T2_PANAKEIA);
        simpleItem(itemlist.T3_PANAKEIA);
        simpleItem(itemlist.T4_PANAKEIA);
        simpleItem(itemlist.T5_PANAKEIA);
        simpleItem(itemlist.T6_PANAKEIA);
        simpleItem(itemlist.T7_PANAKEIA);
        simpleItem(itemlist.ALCHEMICAL_PROCESSING_COPPER);
        simpleItem(itemlist.ALCHEMY_BEGINNERS_KIT);
        simpleItem(itemlist.ALCHEMY_INTERMEDIATE_KIT);
        simpleItem(itemlist.ALCHEMY_EXPERTS_KIT);
        simpleItem(itemlist.ULTIMATE_ALCHEMY_KIT);
        simpleItem(itemlist.ALCHEMICAL_PROCESSING_IRON);
        simpleItem(itemlist.ALCHEMICAL_PROCESSING_GOLD);
        simpleItem(itemlist.ALCHEMICAL_PROCESSING_DIAMOND);
        simpleItem(itemlist.ALCHEMICAL_PROCESSING_EMERALD);
        simpleItem(itemlist.ALCHEMICAL_PROCESSING_NETHERITE);
        simpleItem(itemlist.UNITE_ALLOY);
        simpleItem(itemlist.PANAKEIA_REED_T0);
        simpleItem(itemlist.PANAKEIA_REED_T1);
        simpleItem(itemlist.PANAKEIA_REED_T2);
        simpleItem(itemlist.PANAKEIA_REED_T3);
        simpleItem(itemlist.PANAKEIA_REED_T4);
        simpleItem(itemlist.PANAKEIA_REED_T5);
        simpleItem(itemlist.PANAKEIA_REED_T6);
        simpleItem(itemlist.PANAKEIA_REED_T7);
        simpleItem(itemlist.HIGH_DENSITY_ALCHEMICAL_COPPER);
        simpleItem(itemlist.HIGH_DENSITY_ALCHEMICAL_IRON);
        simpleItem(itemlist.HIGH_DENSITY_ALCHEMICAL_GOLD);
        simpleItem(itemlist.HIGH_DENSITY_ALCHEMICAL_DIAMOND);
        simpleItem(itemlist.HIGH_DENSITY_ALCHEMICAL_EMERALD);
        simpleItem(itemlist.HIGH_DENSITY_ALCHEMICAL_NETHERITE);
        simpleItem(itemlist.HIGH_DENSITY_UNITE_ALLOY);
        simpleItem(itemlist.WISDOM_CRYSTAL);
        simpleItem(itemlist.COURAGE_CRYSTAL);
        simpleItem(itemlist.JUSTICE_CRYSTAL);
        simpleItem(itemlist.LOVE_CRYSTAL);
        simpleItem(itemlist.HOPE_CRYSTAL);
        simpleItem(itemlist.TEMPERANCE_CRYSTAL);
        simpleItem(itemlist.FAITH_CRYSTAL);
        simpleItem(itemlist.BLANK_RUNE);
        simpleItem(itemlist.VOID_RUNE);
        simpleItem(itemlist.BASIC_ENGRAVING_INK);
        simpleItem(itemlist.ADVANCED_ENGRAVING_INK);
        simpleItem(itemlist.ELITE_ENGRAVING_INK);
        simpleItem(itemlist.ULTIMATE_ENGRAVING_INK);
        simpleItem(itemlist.BASIC_GRINDING_RUNE);
        simpleItem(itemlist.ADVANCED_GRINDING_RUNE);
        simpleItem(itemlist.ELITE_GRINDING_RUNE);
        simpleItem(itemlist.ULTIMATE_GRINDING_RUNE);
        simpleItem(itemlist.BASIC_GRANULATING_RUNE);
        simpleItem(itemlist.ADVANCED_GRANULATING_RUNE);
        simpleItem(itemlist.ELITE_GRANULATING_RUNE);
        simpleItem(itemlist.ULTIMATE_GRANULATING_RUNE);
        simpleItem(itemlist.BASIC_SOFTENING_RUNE);
        simpleItem(itemlist.ADVANCED_SOFTENING_RUNE);
        simpleItem(itemlist.ELITE_SOFTENING_RUNE);
        simpleItem(itemlist.ULTIMATE_SOFTENING_RUNE);
        simpleItem(itemlist.BASIC_PURIFICATION_RUNE);
        simpleItem(itemlist.ADVANCED_PURIFICATION_RUNE);
        simpleItem(itemlist.ELITE_PURIFICATION_RUNE);
        simpleItem(itemlist.ULTIMATE_PURIFICATION_RUNE);
        simpleItem(itemlist.CRUSHED_RAW_COPPER);
        simpleItem(itemlist.CRUSHED_RAW_IRON);
        simpleItem(itemlist.CRUSHED_RAW_GOLD);
        simpleItem(itemlist.CRUSHED_DIAMOND_ORE);
        simpleItem(itemlist.CRUSHED_EMERALD_ORE);
        simpleItem(itemlist.CRUSHED_ANCIENT_DEBRIS);
        simpleItem(itemlist.GRANULATED_COPPER);
        simpleItem(itemlist.GRANULATED_IRON);
        simpleItem(itemlist.GRANULATED_GOLD);
        simpleItem(itemlist.GRANULATED_DIAMOND);
        simpleItem(itemlist.GRANULATED_EMERALD);
        simpleItem(itemlist.GRANULATED_ANCIENT_DEBRIS);
        simpleItem(itemlist.SOFTENED_COPPER);
        simpleItem(itemlist.SOFTENED_IRON);
        simpleItem(itemlist.SOFTENED_GOLD);
        simpleItem(itemlist.SOFTENED_DIAMOND);
        simpleItem(itemlist.SOFTENED_EMERALD);
        simpleItem(itemlist.SOFTENED_ANCIENT_DEBRIS);
        simpleItem(itemlist.PURIFIED_COPPER);
        simpleItem(itemlist.PURIFIED_IRON);
        simpleItem(itemlist.PURIFIED_GOLD);
        simpleItem(itemlist.PURIFIED_DIAMOND);
        simpleItem(itemlist.PURIFIED_EMERALD);
        simpleItem(itemlist.PURIFIED_ANCIENT_DEBRIS);
        simpleItem(itemlist.PHILOSOPHERS_STONE);
        simpleItem(itemlist.ARCANE_STAR);
        simpleItem(itemlist.ARCANE_DRAGON_EGG);
        simpleItem(itemlist.T1_COMBUSTION_RUNE);
        simpleItem(itemlist.T2_COMBUSTION_RUNE);
        simpleItem(itemlist.T3_COMBUSTION_RUNE);
        simpleItem(itemlist.T4_COMBUSTION_RUNE);
        simpleItem(itemlist.T5_COMBUSTION_RUNE);
        simpleItem(itemlist.T6_COMBUSTION_RUNE);
        simpleItem(itemlist.ETERNAL_FUEL);
        simpleItem(itemlist.SINGULARITY);



    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemical_power.MODID,"item/" + item.getId().getPath()));

    }

}
