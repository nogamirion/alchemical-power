package jp.nogami_rion.alchemical_power.block.entity;


import jp.nogami_rion.alchemical_power.grid.AlchemicalTableGrid;
import jp.nogami_rion.alchemical_power.screen.AlchemicalPowerTables5x5Menu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AlchemicalTablesTier2BlockEntity extends AbstractAlchemicalTableBlockEntity implements MenuProvider {

    private final AlchemicalTableGrid grid = new AlchemicalTableGrid(5);
    private ItemStack tool = ItemStack.EMPTY;
    private ItemStack result = ItemStack.EMPTY;

    public AlchemicalTablesTier2BlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.HERMES_WORKBENCH_BE_RE.get(),pos,state,5);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.alchemical_power.alchemical_table_tier2");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new AlchemicalPowerTables5x5Menu(id,playerInventory,this);
    }
}
