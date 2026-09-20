package jp.nogami_rion.alchemical_power.util;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;

/** Adds bypass flags to one attack, without replacing its registered type or editing global tags.
 * Custom DamageSource methods are delegated; a wrapper cannot preserve subclass instanceof checks.
 */
public final class ArsenalDamageSource extends DamageSource {
    private final DamageSource original;

    private ArsenalDamageSource(DamageSource original) {
        super(original.typeHolder(), original.getDirectEntity(), original.getEntity(), original.sourcePositionRaw());
        this.original = original;
    }

    public static DamageSource penetrating(DamageSource original) {
        return original instanceof ArsenalDamageSource ? original : new ArsenalDamageSource(original);
    }

    public DamageSource original() { return original; }

    @Override public boolean is(TagKey<DamageType> tag) {
        return tag.equals(DamageTypeTags.BYPASSES_COOLDOWN)
                || tag.equals(DamageTypeTags.BYPASSES_INVULNERABILITY) || original.is(tag);
    }

    @Override public boolean is(ResourceKey<DamageType> key) { return original.is(key); }
    @Override public DamageType type() { return original.type(); }
    @Override public Holder<DamageType> typeHolder() { return original.typeHolder(); }
    @Override public float getFoodExhaustion() { return original.getFoodExhaustion(); }
    @Override public boolean isIndirect() { return original.isIndirect(); }
    @Override @Nullable public Entity getDirectEntity() { return original.getDirectEntity(); }
    @Override @Nullable public Entity getEntity() { return original.getEntity(); }
    @Override public Component getLocalizedDeathMessage(LivingEntity target) { return original.getLocalizedDeathMessage(target); }
    @Override public String getMsgId() { return original.getMsgId(); }
    @Override public boolean scalesWithDifficulty() { return original.scalesWithDifficulty(); }
    @Override public boolean isCreativePlayer() { return original.isCreativePlayer(); }
    @Override @Nullable public Vec3 getSourcePosition() { return original.getSourcePosition(); }
    @Override @Nullable public Vec3 sourcePositionRaw() { return original.sourcePositionRaw(); }
}
