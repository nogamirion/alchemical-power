package jp.nogami_rion.alchemical_power.integration;


import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.openjdk.nashorn.internal.ir.CatchNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CooperativeModChecker {

    public static void tryRegisterModStuff(){
        if(ModList.get().isLoaded("tconstruct")) {
            try{
                Class<?> bootstrap = Class.forName("jp.nogami_rion.alchemical_power.integration.tinker.TinkersIntegration");
                System.out.println("Found Class:" + bootstrap.getName());
                Method register = bootstrap.getMethod("register");
                register.invoke(null);
                System.out.println("Invoked TinkersIntegration.register() successfully");
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + e.getMessage());
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                System.out.println("InvocationTargetException while invoking register()");
                e.getCause().printStackTrace();
            } catch (ReflectiveOperationException e){
                System.out.println("Reflective operation failed: " + e);
                e.printStackTrace();
            }
        }
    }
}
