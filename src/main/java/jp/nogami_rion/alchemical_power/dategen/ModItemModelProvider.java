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
        simpleItem(itemlist.T0_ALCHEMY_DUST);
        simpleItem(itemlist.T1_ALCHEMY_DUST);
        simpleItem(itemlist.T2_ALCHEMY_DUST);
        simpleItem(itemlist.T3_ALCHEMY_DUST);
        simpleItem(itemlist.T4_ALCHEMY_DUST);
        simpleItem(itemlist.T5_ALCHEMY_DUST);
        simpleItem(itemlist.T6_ALCHEMY_DUST);
        simpleItem(itemlist.T7_ALCHEMY_DUST);
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
        simpleItem(itemlist.ALCHEMY_DUST_REED_T0);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T1);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T2);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T3);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T4);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T5);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T6);
        simpleItem(itemlist.ALCHEMY_DUST_REED_T7);


    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Alchemical_power.MODID,"item/" + item.getId().getPath()));

    }

}
