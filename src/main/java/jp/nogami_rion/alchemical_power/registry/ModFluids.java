package jp.nogami_rion.alchemical_power.registry;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    private ModFluids() {}

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Alchemical_power.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.Keys.FLUIDS, Alchemical_power.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Alchemical_power.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Alchemical_power.MODID);

    public static final class FluidEntry {
        public final RegistryObject<FluidType> type;
        public final RegistryObject<FlowingFluid> source;
        public final RegistryObject<FlowingFluid> flowing;
        public final RegistryObject<LiquidBlock> block;
        public final RegistryObject<Item> bucket;
        public final ForgeFlowingFluid.Properties props;

        private FluidEntry(RegistryObject<FluidType> type,
                           RegistryObject<FlowingFluid> source,
                           RegistryObject<FlowingFluid> flowing,
                           RegistryObject<LiquidBlock> block,
                           RegistryObject<Item> bucket,
                           ForgeFlowingFluid.Properties props) {
            this.type = type;
            this.source = source;
            this.flowing = flowing;
            this.block = block;
            this.bucket = bucket;
            this.props = props;
        }
    }

    private static FluidEntry registerFluid(String fluidId, String bucketId, int argbColor, int temperature, int light) {
        return registerFluid(
                fluidId,
                bucketId,
                argbColor,
                temperature,
                light,
                new ResourceLocation(Alchemical_power.MODID, "fluid/base_molten_ingot_still"),
                new ResourceLocation(Alchemical_power.MODID, "fluid/base_molten_ingot_flowing")
        );
    }

    private static FluidEntry registerFluid(String fluidId, String bucketId, int argbColor, int temperature, int light,
                                            ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        final String flowingId = fluidId + "_flowing";
        RegistryObject<FluidType> type = FLUID_TYPES.register(fluidId, () ->
                new FluidType(FluidType.Properties.create()
                        .lightLevel(light)
                        .temperature(temperature)
                        .viscosity(3000)
                        .density(3000)
                        .canDrown(false)
                        .supportsBoating(false)) {
            @Override
            public void initializeClient(java.util.function.Consumer<IClientFluidTypeExtensions> consumer){
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override public ResourceLocation getStillTexture(){return stillTexture;}
                    @Override public ResourceLocation getFlowingTexture(){return flowingTexture;}
                    @Override public int getTintColor() {return argbColor;}
                });
            }
        });


        final ForgeFlowingFluid.Properties[] holder = new ForgeFlowingFluid.Properties[1];

        // Source / Flowing
        RegistryObject<FlowingFluid> source = FLUIDS.register(fluidId,
                () -> new ForgeFlowingFluid.Source(holder[0]));
        RegistryObject<FlowingFluid> flowing = FLUIDS.register(flowingId,
                () -> new ForgeFlowingFluid.Flowing(holder[0]));

        // Block / Bucket
        RegistryObject<LiquidBlock> block = BLOCKS.register(fluidId,
                () -> new LiquidBlock(source, BlockBehaviour.Properties.of()
                        .noCollission().strength(100.0F).lightLevel(s -> light).noLootTable().replaceable()));
        RegistryObject<Item> bucket = ITEMS.register(bucketId,
                () -> new BucketItem(source, new Item.Properties()
                        .craftRemainder(Items.BUCKET).stacksTo(1)));

        holder[0] = new ForgeFlowingFluid.Properties(type, source, flowing)
                .bucket(bucket)
                .block(block)
                .slopeFindDistance(3)
                .levelDecreasePerBlock(1);

        return new FluidEntry(type, source, flowing, block, bucket, holder[0]);
    }

    private static FluidEntry registerMolten(String baseId, int argbColor, int temperature, int light) {
        final String fluidId = "molten_" + baseId;
        final String bucketId = fluidId + "_bucket";
        return registerFluid(fluidId, bucketId, argbColor, temperature, light);
    }

    public static final FluidEntry T1_PANAKEIA = registerMolten("t1_panakeia_ingot", 0xFFCCFFFF, 700, 8);
    public static final FluidEntry T2_PANAKEIA = registerMolten("t2_panakeia_ingot", 0xFFFFFF66, 800, 8);
    public static final FluidEntry T3_PANAKEIA = registerMolten("t3_panakeia_ingot", 0xFFFF6666, 900, 10);
    public static final FluidEntry T4_PANAKEIA = registerMolten("t4_panakeia_gem", 0xFFFF33FF, 1000, 10);
    public static final FluidEntry T5_PANAKEIA = registerMolten("t5_panakeia_gem", 0xFF9999FF, 1000, 12);
    public static final FluidEntry T6_PANAKEIA = registerMolten("t6_panakeia_ingot", 0xFFFFFFFF, 1300, 12);
    public static final FluidEntry UNITE_ALLOY = registerFluid(
            "molten_unite_alloy",
            "molten_unite_alloy_bucket",
            0xFFFFFFFF,
            1400,
            12,
            new ResourceLocation(Alchemical_power.MODID, "fluid/molten_unite_alloy_still"),
            new ResourceLocation(Alchemical_power.MODID, "fluid/molten_unite_alloy_flowing")
    );
    public static final FluidEntry SINGULARITY = registerMolten("singularity", 0xFFFFF9DC, 1500, 15);
    public static final FluidEntry LIQUID_PANAKEIA = registerFluid(
            "liquid_panakeia",
            "liquid_panakeia_bucket",
            0xFFFFFFFF,
            300,
            8,
            new ResourceLocation(Alchemical_power.MODID, "fluid/liquid_panakeia_still"),
            new ResourceLocation(Alchemical_power.MODID, "fluid/liquid_panakeia_flowing")
    );

    public static void register(IEventBus bus) {
        FLUID_TYPES.register(bus);
        FLUIDS.register(bus);
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

}
