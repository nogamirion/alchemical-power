package jp.nogami_rion.alchemical_power.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

public class FuelBlock extends Block {
    private int burnTime = 0;

    public FuelBlock(Properties pProperties, int burnTime) {
        super(pProperties);
        this.burnTime = burnTime;
        MinecraftForge.EVENT_BUS.register(this); // イベントバスに登録
    }

    // Forge イベントハンドラー
    @SubscribeEvent
    public void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() == this.asItem()) {
            event.setBurnTime(this.burnTime);
        }
    }
}
