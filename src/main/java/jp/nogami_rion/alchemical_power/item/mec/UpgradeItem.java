package jp.nogami_rion.alchemical_power.item.mec;

import jp.nogami_rion.alchemical_power.block.entity.AutoAlchemicalAssemblerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class UpgradeItem extends Item {

    private final UpgradeType type;
    private final int tier;

    public UpgradeItem(UpgradeType type,int tier,Properties props){
        super(props);
        this.type = type;
        this.tier = tier;
    }

    public  UpgradeType getType() {
        return type;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context){
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if(level.isClientSide || player ==null) return InteractionResult.SUCCESS;

        if(!player.isCrouching()) return InteractionResult.PASS;

        BlockEntity be = level.getBlockEntity(pos);

        if(be instanceof AutoAlchemicalAssemblerBlockEntity assembler){
            if(assembler.insertUpgrade(context.getItemInHand())){
                context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

}
