package jp.nogami_rion.alchemical_power.dategen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.itemlist;
import jp.nogami_rion.alchemical_power.item.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Alchemical_power.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.ALCHEMY_KIT_TIER1)
                .add(itemlist.ALCHEMY_BEGINNERS_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER2);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER2)
                .add(itemlist.ALCHEMY_INTERMEDIATE_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER3);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER3)
                .add(itemlist.ALCHEMY_EXPERTS_KIT.get())
                .addTag(ModTags.Items.ALCHEMY_KIT_TIER4);

        this.tag(ModTags.Items.ALCHEMY_KIT_TIER4)
                .add(itemlist.ULTIMATE_ALCHEMY_KIT.get());
  //              .addTag(ModTags.Items.ALCHEMY_KIT_TIER5);



    }
}
