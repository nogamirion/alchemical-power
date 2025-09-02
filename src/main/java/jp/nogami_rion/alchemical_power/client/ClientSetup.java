package jp.nogami_rion.alchemical_power.client;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.registry.ModFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = "alchemical_power",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientSetup {
    public ClientSetup(){}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent e){
        e.enqueueWork(() -> {
//            registerFluidColor(ModFluids.T1_PANAKEIA, "molten_ti_panakeia_ingot");
//            registerFluidColor(ModFluids.T2_PANAKEIA, "molten_t2_panakeia_ingot");
//            registerFluidColor(ModFluids.T3_PANAKEIA, "molten_t3_panakeia_ingot");
//            registerFluidColor(ModFluids.T4_PANAKEIA, "molten_t4_panakeia_gem");
//            registerFluidColor(ModFluids.T5_PANAKEIA, "molten_t5_panakeia_gem");
//            registerFluidColor(ModFluids.T6_PANAKEIA, "molten_t6_panakeia_ingot");
//            registerFluidColor(ModFluids.UNITE_ALLOY, "molten_unite_alloy");
//            registerFluidColor(ModFluids.SINGULARITY, "molten_singularity");

        });
    }

    @SubscribeEvent
    public static void onRegisterItemColors(net.minecraftforge.client.event.RegisterColorHandlersEvent.Item e) {
        e.register((stack, tintIndex) -> {
                    if (tintIndex == 1 && stack.getItem() instanceof net.minecraft.world.item.BucketItem bucket) {
                        var fluid = bucket.getFluid();
                        var ext = net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions.of(fluid);
                        return ext.getTintColor();
                    }
                    return -1;
                },
                ModFluids.T1_PANAKEIA.bucket.get(),
                ModFluids.T2_PANAKEIA.bucket.get(),
                ModFluids.T3_PANAKEIA.bucket.get(),
                ModFluids.T4_PANAKEIA.bucket.get(),
                ModFluids.T5_PANAKEIA.bucket.get(),
                ModFluids.T6_PANAKEIA.bucket.get(),
                ModFluids.UNITE_ALLOY.bucket.get(),
                ModFluids.SINGULARITY.bucket.get()
        );
    }


    private static void registerFluidColor(ModFluids.FluidEntry entry, String idPath) {
        var mc = Minecraft.getInstance();
        mc.getItemColors().register(
                (stack, tintIndex) -> ColorTable.colorOf(new net.minecraft.resources.ResourceLocation("alchemical_power", idPath), 0xFFFFFFFF),
                entry.bucket.get()
        );
        mc.getBlockColors().register(
                (state, reader, pos, tintIndex) -> ColorTable.colorOf(new net.minecraft.resources.ResourceLocation("alchemical_power", idPath), 0xFFFFFFFF),
                entry.block.get()
        );
    }

    private static void registerItemColor(Item item, String idPath, int layerTintIndex) {
        var mc = Minecraft.getInstance();
        mc.getItemColors().register((stack, tintIndex) ->
                        (tintIndex == layerTintIndex)
                                ? ColorTable.colorOf(new net.minecraft.resources.ResourceLocation("alchemical_power", idPath), 0xFFFFFFFF)
                                : 0xFFFFFFFF,
                item
        );
    }

    private static void registerItemColorWithLayers(Item item, java.util.function.IntFunction<Integer> colorByLayer) {
        Minecraft.getInstance().getItemColors().register((stack, idx) -> colorByLayer.apply(idx), item);
    }

    private static void registerBlockColor(Block block, String idPath) {
        Minecraft.getInstance().getBlockColors().register(
                (state, reader, pos, tintIndex) ->
                        ColorTable.colorOf(new net.minecraft.resources.ResourceLocation("alchemical_power", idPath), 0xFFFFFFFF),
                block
        );
    }
}
