package jp.nogami_rion.alchemical_power.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import java.util.Objects;

/** Server-side Forge bus hook for supplying a weapon's native source for the primary arsenal hit.
 * The default source is player/mob attack for melee and summoned strikes, or trident for bow shots.
 * Listeners must supply a source only, without dealing damage or consuming durability themselves.
 * Bypass flags are applied afterwards; damage emitted by the weapon's own callbacks is untouched.
 */
public final class ArsenalDamageSourceEvent extends Event {
    private final ItemStack weapon;
    private final LivingEntity owner;
    private final LivingEntity target;
    private final Entity direct;
    private DamageSource source;

    public ArsenalDamageSourceEvent(ItemStack weapon, LivingEntity owner, LivingEntity target, Entity direct, DamageSource source) {
        this.weapon = weapon;
        this.owner = owner;
        this.target = target;
        this.direct = direct;
        this.source = Objects.requireNonNull(source);
    }

    public ItemStack getWeapon() { return weapon; }
    public LivingEntity getOwner() { return owner; }
    public LivingEntity getTarget() { return target; }
    public Entity getDirectEntity() { return direct; }
    public DamageSource getSource() { return source; }
    public void setSource(DamageSource source) { this.source = Objects.requireNonNull(source); }
}
