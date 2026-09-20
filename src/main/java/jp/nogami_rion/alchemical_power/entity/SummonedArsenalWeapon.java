package jp.nogami_rion.alchemical_power.entity;

import jp.nogami_rion.alchemical_power.init.entitylist;
import jp.nogami_rion.alchemical_power.util.ArsenalSummonAnimation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

/** Render-only projection. It never owns, drops, or damages with the real inventory item. */
public class SummonedArsenalWeapon extends Entity {
    private static final EntityDataAccessor<ItemStack> WEAPON = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> IMPACT = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Long> START = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.LONG);
    private static final EntityDataAccessor<Float> X = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Y = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> Z = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PHASE = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<CompoundTag> DISMISSAL = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<CompoundTag> ROUTE = SynchedEntityData.defineId(SummonedArsenalWeapon.class, EntityDataSerializers.COMPOUND_TAG);
    public static final int DISMISS_TICKS = 8;
    private static final double RENDER_DISTANCE = 32.0;

    @Override public boolean shouldRenderAtSqrDistance(double distance) {
        // Visual weapons need a modest fixed range, independent of their tiny hitbox.
        return distance < RENDER_DISTANCE * RENDER_DISTANCE;
    }

    public SummonedArsenalWeapon(EntityType<? extends SummonedArsenalWeapon> type, Level level) {
        super(type, level);
        noPhysics = true;
    }

    public SummonedArsenalWeapon(Level level, LivingEntity target, ItemStack weapon, Vec3 anchor, int impact) {
        this(entitylist.SUMMONED_ARSENAL_WEAPON.get(), level);
        entityData.set(WEAPON, weapon.copyWithCount(1));
        entityData.set(TARGET, target.getId());
        entityData.set(IMPACT, impact);
        entityData.set(START, level.getGameTime());
        entityData.set(X, (float) anchor.x);
        entityData.set(Y, (float) anchor.y);
        entityData.set(Z, (float) anchor.z);
        entityData.set(PHASE, level.random.nextFloat() * (float) Math.PI * 2);
        CompoundTag route = new CompoundTag();
        putVector(route, "Center", center(target));
        entityData.set(ROUTE, route);
        setPos(center(target).add(animatedOffset(0)));
    }

    @Override protected void defineSynchedData() {
        entityData.define(WEAPON, ItemStack.EMPTY);
        entityData.define(TARGET, -1);
        entityData.define(IMPACT, 20);
        entityData.define(START, 0L);
        entityData.define(X, 0F);
        entityData.define(Y, 4F);
        entityData.define(Z, 0F);
        entityData.define(PHASE, 0F);
        entityData.define(DISMISSAL, new CompoundTag());
        entityData.define(ROUTE, new CompoundTag());
    }

    public ItemStack getWeapon() { return entityData.get(WEAPON); }

    public int getTargetId() { return entityData.get(TARGET); }
    public void setTarget(LivingEntity target) {
        if (isDismissing() || target.getId() == getTargetId()) return;
        // Rebase an in-flight route at its current position before changing its destination.
        float progress = flightProgress(0);
        if (progress > 0 && progress < 1) {
            CompoundTag route = entityData.get(ROUTE).copy();
            putVector(route, "From", renderPosition(0));
            route.putFloat("Progress", progress);
            entityData.set(ROUTE, route);
        }
        entityData.set(TARGET, target.getId());
    }
    private static void putVector(CompoundTag tag, String name, Vec3 value) {
        tag.putDouble(name + "X", value.x);
        tag.putDouble(name + "Y", value.y);
        tag.putDouble(name + "Z", value.z);
    }
    private static Vec3 vector(CompoundTag tag, String name) {
        return new Vec3(tag.getDouble(name + "X"), tag.getDouble(name + "Y"), tag.getDouble(name + "Z"));
    }
    public Vec3 getSpawnAnchor() { return vector(entityData.get(ROUTE), "Center").add(getAnchor()); }
    private float flightProgress(float partialTick) {
        return net.minecraft.util.Mth.clamp((getAge(partialTick) - entityData.get(IMPACT) + ArsenalSummonAnimation.FLIGHT_TICKS)
                / ArsenalSummonAnimation.FLIGHT_TICKS, 0, 1);
    }
    public Vec3 facingDirection(float partialTick) {
        if (isDismissing()) return vector(entityData.get(DISMISSAL), "Facing");
        Entity target = level().getEntity(getTargetId());
        Vec3 direction = target == null ? getAnchor().scale(-1)
                : target.getPosition(partialTick).add(0, target.getBbHeight() * 0.5, 0).subtract(renderPosition(partialTick));
        return direction.lengthSqr() < 1.0E-8 ? getAnchor().normalize().scale(-1) : direction.normalize();
    }
    public Vec3 getAnchor() { return new Vec3(entityData.get(X), entityData.get(Y), entityData.get(Z)); }
    public float getAge(float partialTick) { return Math.max(0, level().getGameTime() - entityData.get(START) + partialTick); }
    public boolean isDismissing() { return !entityData.get(DISMISSAL).isEmpty(); }

    /** Freeze the current pose and fade outward without tracking a dead or removed target. */
    public void dismiss() {
        if (level().isClientSide || isDismissing()) return;
        // Weapons whose strike already completed retain their normal disappearance.
        if (getAge(0) >= entityData.get(IMPACT)) { discard(); return; }
        Vec3 origin = renderPosition(0);
        CompoundTag state = new CompoundTag();
        state.putLong("Start", level().getGameTime());
        state.putDouble("X", origin.x);
        state.putDouble("Y", origin.y);
        state.putDouble("Z", origin.z);
        state.putFloat("Opacity", getOpacity(0));
        putVector(state, "Facing", facingDirection(0));
        entityData.set(DISMISSAL, state);
        setPos(origin);
    }

    private float dismissalProgress(float partialTick) {
        float t = net.minecraft.util.Mth.clamp((level().getGameTime() - entityData.get(DISMISSAL).getLong("Start") + partialTick) / DISMISS_TICKS, 0, 1);
        return t * t * (3 - 2 * t);
    }

    public float getOpacity(float partialTick) {
        return isDismissing() ? entityData.get(DISMISSAL).getFloat("Opacity") * (1 - dismissalProgress(partialTick))
                : ArsenalSummonAnimation.opacity(getAge(partialTick));
    }
    public Vec3 animatedOffset(float partialTick) {
        float age = isDismissing() ? Math.max(0, entityData.get(DISMISSAL).getLong("Start") - entityData.get(START)) : getAge(partialTick);
        return ArsenalSummonAnimation.offset(getAnchor(), age, entityData.get(IMPACT), entityData.get(PHASE));
    }
    private Vec3 center(Entity target) { return target.position().add(0, target.getBbHeight() * 0.5, 0); }
    public Vec3 renderPosition(float partialTick) {
        if (isDismissing()) {
            CompoundTag state = entityData.get(DISMISSAL);
            return new Vec3(state.getDouble("X"), state.getDouble("Y"), state.getDouble("Z"))
                    .subtract(vector(state, "Facing").scale(dismissalProgress(partialTick)));
        }
        CompoundTag route = entityData.get(ROUTE);
        float progress = flightProgress(partialTick);
        Vec3 spawnCenter = vector(route, "Center");
        if (progress <= 0) return spawnCenter.add(animatedOffset(partialTick));
        Vec3 from = route.contains("FromX") ? vector(route, "From")
                : getSpawnAnchor().add(getAnchor().normalize().scale(0.65));
        float previous = route.getFloat("Progress");
        float travel = net.minecraft.util.Mth.clamp((progress * progress - previous * previous) / (1 - previous * previous), 0, 1);
        Entity target = level().getEntity(getTargetId());
        Vec3 destination = target == null ? spawnCenter : target.getPosition(partialTick).add(0, target.getBbHeight() * 0.5, 0);
        return from.lerp(destination, travel);
    }

    @Override public void tick() {
        super.tick();
        if (isDismissing()) {
            setPos(renderPosition(0));
            if (!level().isClientSide && dismissalProgress(0) >= 1) discard();
            return;
        }
        Entity target = level().getEntity(entityData.get(TARGET));
        if (getAge(0) >= entityData.get(IMPACT)) {
            if (!level().isClientSide) discard();
            return;
        }
        // The owning sword selects the replacement; entity tick order must not discard waiting weapons.
        if (target == null || !target.isAlive()) return;
        Vec3 position = renderPosition(0);
        Vec3 direction = facingDirection(0);
        setYRot((float) (Math.atan2(direction.x, direction.z) * 180 / Math.PI));
        setXRot((float) (Math.atan2(direction.y, direction.horizontalDistance()) * 180 / Math.PI));
        setPos(position);
    }

    @Override protected void readAdditionalSaveData(CompoundTag tag) {}
    @Override protected void addAdditionalSaveData(CompoundTag tag) {}
    @Override public Packet<ClientGamePacketListener> getAddEntityPacket() { return NetworkHooks.getEntitySpawningPacket(this); }
}
