package jp.nogami_rion.alchemical_power.item;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class T0_Alchemy_Dust extends Item {
    public T0_Alchemy_Dust(){
        super (new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext context){
        super.useOn(context);
        if ((context.getLevel().getBlockState(BlockPos.containing(context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ()))).getBlock() == Blocks.WATER_CAULDRON
            && (context.getPlayer() instanceof LivingEntity ? ((LivingEntity) context.getPlayer()).getMainHandItem() : ItemStack.EMPTY).getItem() == itemlist.T0_ALCHEMY_DUST.get()){
            (context.getPlayer() instanceof LivingEntity ? ((LivingEntity) context.getPlayer()).getMainHandItem() : ItemStack.EMPTY).shrink(1);
            if (context.getLevel() instanceof ServerLevel _level){
                ItemEntity entityToSpawn = new ItemEntity(_level,(context.getClickedPos().getX() + 0.5),(context.getClickedPos().getY() + 1),(context.getClickedPos().getZ() + 0.5 ),new ItemStack(itemlist.T1_ALCHEMY_DUST.get()));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);

                //大釜の水を減らす　コードがよくわからず実装保留
                //BlockState _state = context.getLevel().getBlockState(BlockPos.containing(context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ()))
                //        .getBlock().defaultBlockState();

                //context.getLevel().getBlockState(BlockPos.containing(context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ()))
                //        .setValue(_state.getProperties(),);

            }
        }
        return InteractionResult.SUCCESS;
    }
}
