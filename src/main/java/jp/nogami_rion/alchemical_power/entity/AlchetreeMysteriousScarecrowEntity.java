package jp.nogami_rion.alchemical_power.entity;

import jp.nogami_rion.alchemical_power.event.MysteriousScarecrowPropagationHandler;
import jp.nogami_rion.alchemical_power.init.itemlist;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AlchetreeMysteriousScarecrowEntity extends Mob {
    private boolean suppressPropagation = false; // 再帰防止
    @Nullable private UUID ownerUUID;

    public AlchetreeMysteriousScarecrowEntity(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.setYRot(0);
        this.setRot(0, 0);
        this.setNoAi(true);
        this.noPhysics = false;
    }

    public static AttributeSupplier.Builder CreateAttributes(){
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,1024)
                .add(Attributes.ARMOR,0)
                .add(Attributes.ARMOR_TOUGHNESS,0)
                .add(Attributes.ATTACK_DAMAGE,0)
                .add(Attributes.KNOCKBACK_RESISTANCE,1.0d)
                .add(Attributes.MOVEMENT_SPEED,0)
                .add(Attributes.FOLLOW_RANGE,0);
    }

    public boolean isPropagationSuppressed() { return suppressPropagation; }
    public void setPropagationSuppressed(boolean b) { suppressPropagation = b; }

    @Override
    public boolean isPushable() { return false; }
    @Override
    protected void pushEntities() {};
    @Override
    protected void doPush(Entity entity) {}

    @Override
    public boolean isAffectedByPotions() { return false; } // 必要に応じて

    @Override
    protected void dropAllDeathLoot(DamageSource source){}; // 死亡時にアイテムをドロップしない

    public void setOwner(@Nullable UUID uuid) { this.ownerUUID = uuid; }
    @Nullable  public  UUID getOwnerUUID() { return this.ownerUUID; }

    @Override
    public void addAdditionalSaveData(CompoundTag tag){
        super.addAdditionalSaveData(tag);
        if(ownerUUID != null) tag.putUUID("Owner",ownerUUID);
        tag.putFloat("ScarecrowHealth",this.getHealth());
        tag.putFloat("Yaw",this.getYRot());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag){
        super.readAdditionalSaveData(tag);
        if(tag.hasUUID("Owner")) this.ownerUUID = tag.getUUID("Owner");

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1024.0d);

        if(tag.contains("ScarecrowHealth")){
            float h = tag.getFloat("ScarecrowHealth");
            this.setHealth(Math.max(1.0f,Math.min(h,this.getMaxHealth())));
        } else {
            this.setHealth(1.0f);
        }

        if(tag.contains("Yaw")){
            float yaw = tag.getFloat("Yaw");
            this.setYRot(yaw);
            this.setYHeadRot(yaw);
            this.setYBodyRot(yaw);
            this.yRotO = yaw;
            this.yHeadRot = yaw;
            this.yBodyRot = yaw;
        }

        this.setPersistenceRequired();

    }

    public void initPlacementFrom(@Nullable Entity placer) {
        if (placer != null) {
            float yaw = placer.getYHeadRot() + 180.0f;
            this.setYRot(yaw);
            this.setYHeadRot(yaw);
            this.setYBodyRot(yaw);
            this.yRotO = yaw; this.yHeadRotO = yaw; this.yBodyRotO = yaw;
        }
        this.setPersistenceRequired();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(){
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer){
        return false;
    }


    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand){
        if(!level().isClientSide && player.isShiftKeyDown()){
            this.spawnAtLocation(itemlist.ALCHETREE_MYSTERIOUS_SCARECROW.get());
            this.discard();
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean hurt(DamageSource source, float amount){
        if(level().isClientSide) return true;
        final float originaldamage = amount;
        float cap = Math.max(0f, this.getHealth() - 1.0f);
        if(amount > cap) amount = cap;

        boolean result = super.hurt(source, amount);
        if(this.getHealth() <= 0.0f){
            this.setHealth(this.getMaxHealth());
        }
        if(this.isAlive()) {
            MysteriousScarecrowPropagationHandler.propagate(this, source, originaldamage,false);
        }
        return result;
    }

    @Override
    public void die(DamageSource source) {
        if(!level().isClientSide) {
            MysteriousScarecrowPropagationHandler.propagate(this, source,(float) Integer.MAX_VALUE, true);
        }
        super.die(source);
    }

}
