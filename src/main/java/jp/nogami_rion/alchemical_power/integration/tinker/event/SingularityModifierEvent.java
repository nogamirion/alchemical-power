package jp.nogami_rion.alchemical_power.integration.tinker.event;

import jp.nogami_rion.alchemical_power.integration.tinker.TinkersIntegration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class SingularityModifierEvent {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        for (ItemStack armor : entity.getArmorSlots()){
            IToolStackView tool = ToolStack.from(armor);
            if(tool != null && tool.getModifierLevel(TinkersIntegration.SINGULARITY_MODIFIER.getId())>0){
                event.setCanceled(true);
                break;
            }
        }
    }
}
