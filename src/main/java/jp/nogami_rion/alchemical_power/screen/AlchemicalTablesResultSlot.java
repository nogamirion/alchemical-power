package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;


public class AlchemicalTablesResultSlot extends Slot {

    private final Player player;
    private final CraftingContainer craftSlots;
    private final AbstractAlchemicalTableBlockEntity blockEntity;
    private final AbstractAlchemicalPowerTablesMenu menu;

    public AlchemicalTablesResultSlot(Player player,CraftingContainer craftSlots,Container container,
                                      AbstractAlchemicalTableBlockEntity blockEntity,AbstractAlchemicalPowerTablesMenu menu,int index, int x, int y) {
        super(container, index, x, y);
        this.player = player;
        this.craftSlots = craftSlots;
        this.blockEntity = blockEntity;
        this.menu = menu;
    }

    @Override
    public boolean mayPlace(ItemStack stack){
        return false;
    }

    @Override
    public void onTake(Player player, ItemStack stack){
        super.onTake(player, stack);

        if(player.level().isClientSide){
            menu.triggerPlayAnimation();
        }

        if(menu.isCustomRecipe()) {
            blockEntity.consumeTool(player);
        }
        blockEntity.consumeInputs();
        blockEntity.clearResult();

    }
}
