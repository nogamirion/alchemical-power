package jp.nogami_rion.alchemical_power.init;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class creativetab {
    //クリエイティブタグのレジストリ生成
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Alchemical_power.MODID);

    //レジストリにタブを追加
    public static final RegistryObject<CreativeModeTab> ALCHEMICAL_POWER_TAB;

    static {
        ALCHEMICAL_POWER_TAB = TABS.register("alchemical_power_tab", () -> CreativeModeTab.builder()
                .title(Component.translatable("creativetabs.alchemical_power_tab"))
                .icon(itemlist.T0_ALCHEMY_DUST.get()::getDefaultInstance)
                .displayItems((itemDisplayParameters, output) -> {
                    output.accept(itemlist.T0_ALCHEMY_DUST.get());
                    output.accept(itemlist.T1_ALCHEMY_DUST.get());
                    output.accept(itemlist.T2_ALCHEMY_DUST.get());
                    output.accept(itemlist.T3_ALCHEMY_DUST.get());
                    output.accept(itemlist.T4_ALCHEMY_DUST.get());
                    output.accept(itemlist.T5_ALCHEMY_DUST.get());
                    output.accept(itemlist.T6_ALCHEMY_DUST.get());
                    output.accept(itemlist.T7_ALCHEMY_DUST.get());
                    output.accept(itemlist.STONE_CONTAINING_ALCHEMY_DUST.get());
                    output.accept(itemlist.ALCHEMY_BEGINNERS_KIT.get());
                    output.accept(itemlist.ALCHEMY_INTERMEDIATE_KIT.get());
                    output.accept(itemlist.ALCHEMY_EXPERTS_KIT.get());
                    output.accept(itemlist.ULTIMATE_ALCHEMY_KIT.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_COPPER.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_IRON.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_GOLD.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_DIAMOND.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_EMERALD.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_NETHERITE.get());
                    output.accept(itemlist.UNITE_ALLOY.get());
                    output.accept(itemlist.ALCHEMY_TABLE.get());
                    output.accept(itemlist.HERMES_WORKBENCH.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_COPPER_BLOCK.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_IRON_BLOCK.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_GOLD_BLOCK.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_DIAMOND_BLOCK.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_EMERALD_BLOCK.get());
                    output.accept(itemlist.ALCHEMICAL_PROCESSING_NETHERITE_BLOCK.get());
                    output.accept(itemlist.UNITE_ALLOY_BLOCK.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T0.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T1.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T2.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T3.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T4.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T5.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T6.get());
                    output.accept(itemlist.ALCHEMY_DUST_REED_T7.get());




                })
                .build());
    }

    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }
}
