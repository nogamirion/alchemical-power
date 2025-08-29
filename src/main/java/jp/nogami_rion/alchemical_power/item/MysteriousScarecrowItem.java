package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.entity.AlchetreeMysteriousScarecrowEntity;
import jp.nogami_rion.alchemical_power.init.entitylist;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MysteriousScarecrowItem extends Item {
    public MysteriousScarecrowItem() {
        super((new Item.Properties()));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx){
        Level level = ctx.getLevel();
        if(level.isClientSide) return InteractionResult.SUCCESS;

        BlockPos place = ctx.getClickedPos().relative(ctx.getClickedFace());

        EntityType<AlchetreeMysteriousScarecrowEntity> type = entitylist.ALCHETREE_MYSTERIOUS_SCARECROW.get();
        AlchetreeMysteriousScarecrowEntity e = type.create(level);
        if(e == null) return InteractionResult.FAIL;

        e.moveTo(place.getX() + 0.5,place.getY(),place.getZ() +0.5,ctx.getPlayer() != null ? ctx.getPlayer().getYRot() : 0f,0f);
        level.addFreshEntity(e);

        if (ctx.getPlayer() != null && !ctx.getPlayer().getAbilities().instabuild) {
            ctx.getItemInHand().shrink(1);
        }
        if (ctx.getPlayer() != null){
            e.setOwner(ctx.getPlayer().getUUID());
        }

        return InteractionResult.CONSUME;
    }

}
