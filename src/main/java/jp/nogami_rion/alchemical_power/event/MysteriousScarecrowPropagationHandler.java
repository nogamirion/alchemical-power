package jp.nogami_rion.alchemical_power.event;

import jp.nogami_rion.alchemical_power.Alchemical_power;
import jp.nogami_rion.alchemical_power.entity.AlchetreeMysteriousScarecrowEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

//@Mod.EventBusSubscriber(modid = Alchemical_power.MODID,bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MysteriousScarecrowPropagationHandler {
    private static boolean IN_PROPAGATION = false;

//    @SubscribeEvent
//    public static void onAttack(LivingHurtEvent event) {
//        if(!(event.getEntity() instanceof AlchetreeMysteriousScarecrowEntity scarecrow)) return;
//        if(scarecrow.level().isClientSide) return;
//        if(IN_PROPAGATION || scarecrow.isPropagationSuppressed()) return;
//
//        propagate(scarecrow,event.getSource(),event.getAmount(),false);
//        event.setCanceled(true);
//
//    }
//
//    @SubscribeEvent
//    public static void onScarecrowHurt(LivingHurtEvent event){
//        if(!(event.getEntity() instanceof AlchetreeMysteriousScarecrowEntity scarecrow)) return;
//        if(scarecrow.level().isClientSide) return;
//        if(IN_PROPAGATION || scarecrow.isPropagationSuppressed()) return;
//
//        propagate(scarecrow,event.getSource(),event.getAmount(),false);
//    }
//
//    @SubscribeEvent
//    public static void onScarecrowDeath(LivingDeathEvent event){
//        if (!(event.getEntity() instanceof AlchetreeMysteriousScarecrowEntity scarecrow)) return;
//        if (scarecrow.level().isClientSide) return;
//        if (IN_PROPAGATION || scarecrow.isPropagationSuppressed()) return;
//
//        propagate(scarecrow,event.getSource(),Float.MAX_VALUE,true);
//
//    }

    public static void propagate(AlchetreeMysteriousScarecrowEntity scarecrow, DamageSource src, float damage, boolean lethalIntent){
        IN_PROPAGATION = true;
        final UUID owner = scarecrow.getOwnerUUID();
        scarecrow.setPropagationSuppressed(true);
        try{
                double range = 32.0;
                AABB box = scarecrow.getBoundingBox().inflate(range);

                List<LivingEntity> targets = scarecrow.level().getEntitiesOfClass(LivingEntity.class,box);
                for(Entity e : targets){
                    if(e == scarecrow) continue; // 自分自身を除外
                    if(e instanceof AlchetreeMysteriousScarecrowEntity) continue; // 他の案山子を除外
                    if(owner != null && e.getUUID().equals(owner)) continue; // 設置者を除外
                    if(e instanceof LivingEntity living){
                        living.invulnerableTime = 0;
                        if(!lethalIntent) {
                            living.hurt(src, damage);
                        }
                        if(lethalIntent && living.isAlive()){
                            living.setHealth(0.0F);
                            living.die(src);
                        }
                        spawnLightning(scarecrow.level(),living.getX(),living.getY(),living.getZ());
                    }else{
                        e.invulnerableTime = 0;
                        e.hurt(src,damage);
                        spawnLightning(scarecrow.level(),e.getX(),e.getY(),e.getZ());
                    }
                }
        } finally {
            scarecrow.setPropagationSuppressed(false);
            IN_PROPAGATION = false;
        }

    }

    private static void spawnLightning(Level level,double x, double y, double z){
        if(level.isClientSide) return;
        ServerLevel slevel = (ServerLevel) level;
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(slevel);
        if(bolt == null) return;

        bolt.setVisualOnly(true);
        bolt.moveTo(x,y+0.5,z);
        slevel.addFreshEntity(bolt);

    }

}
