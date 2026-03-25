package jp.nogami_rion.alchemical_power.block.entity;


import jp.nogami_rion.alchemical_power.grid.AlchemicalTableGrid;
import jp.nogami_rion.alchemical_power.screen.AlchemicalPowerTables3x3Menu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AlchemicalTablesTier1BlockEntity extends AbstractAlchemicalTableBlockEntity implements MenuProvider {

    private final AlchemicalTableGrid grid = new AlchemicalTableGrid(3);
    private ItemStack tool = ItemStack.EMPTY;
    private ItemStack result = ItemStack.EMPTY;

    public AlchemicalTablesTier1BlockEntity(BlockPos pos, BlockState state){
        super(ModBlockEntities.ALCHEMY_TABLE_BE_RE.get(),pos,state,3);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.alchemical_power.alchemical_table_tier1");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new AlchemicalPowerTables3x3Menu(id,playerInventory,this);
    }


}
