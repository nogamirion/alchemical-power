package jp.nogami_rion.alchemical_power.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class InventoryDropUtil {

    public static void drop(Level level, BlockPos pos, IItemHandler handler) {
        if (level.isClientSide) return;

        for(int i = 0; i < handler.getSlots(); i++){
            ItemStack stack = handler.getStackInSlot(i);

            if(!stack.isEmpty()){
                Containers.dropItemStack(level,pos.getX(),pos.getY(),pos.getZ(),stack);
            }
        }
    }
}
