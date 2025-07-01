package jp.nogami_rion.alchemical_power.item.accessory;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@Mod.EventBusSubscriber(modid = Alchemical_power.MODID)
public class Greed_Ring extends Item implements ICurioItem {

    public Greed_Ring() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }
    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        if(player.level().isClientSide) return;

        if(CuriosApi.getCuriosHelper().findEquippedCurio(itemlist.GREED_RING.get(), player).isPresent())
        {
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 0, false, false));
        }
    }
}
