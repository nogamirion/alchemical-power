package jp.nogami_rion.alchemical_power.item.custom;

import jp.nogami_rion.alchemical_power.block.entity.PainConverterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyBlockItem extends BlockItem {
    public EnergyBlockItem(Block block, Properties properties){
        super(block,properties);

    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, Player player, ItemStack stack, BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof PainConverterBlockEntity genbe) {
            stack.getOrCreateTag().putInt("Energy",genbe.getEnergyStored());
        }
        return false;
    }
}
