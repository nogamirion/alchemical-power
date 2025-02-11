package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Alchemical_power.MODID);

    public static final RegistryObject<MenuType<Alchemy_Table_Menu>> ALCHEMY_TABLE_MENU =
            registerMenuType("alchemy_table_menu",Alchemy_Table_Menu::new);

    public static final RegistryObject<MenuType<Hermes_Workbench_Menu>> HERMES_WORKBENCH_MENU =
            registerMenuType("hermes_workbench_menu",Hermes_Workbench_Menu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENUS.register(name,() -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
