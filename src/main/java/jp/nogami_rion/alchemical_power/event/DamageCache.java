package jp.nogami_rion.alchemical_power.event;

import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DamageCache{
    private static final Map<UUID,Float> damageMap = new HashMap<>();

    public static void putDamage(LivingEntity entity, float amount){
        if(entity == null) return;
        damageMap.put(entity.getUUID(),amount);
    }

    public static float consumeDamage(LivingEntity entity){
        if(entity == null) return 0f;
        Float val = damageMap.remove(entity.getUUID());
        return val == null ? 0f : val;
    }

}