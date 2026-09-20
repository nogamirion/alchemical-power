package jp.nogami_rion.alchemical_power.util;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import java.util.List;

/** Shared deterministic animation math; random anchors are selected once on the server. */
public final class ArsenalSummonAnimation {
    public static final int APPEAR_TICKS = 6;
    public static final int PULLBACK_TICKS = 4;
    public static final int FLIGHT_TICKS = 4;
    public static final double MIN_SEPARATION = 2.6;
    private ArsenalSummonAnimation() {}

    public static Vec3 chooseAnchor(RandomSource random, double radius, List<Vec3> occupied) {
        radius = Math.max(radius, MIN_SEPARATION * Math.sqrt((occupied.size() + 1) / 3.0));
        for (int attempt = 0; attempt < 512; attempt++) {
            Vec3 candidate = randomDirection(random).scale(radius + random.nextDouble());
            boolean clear = true;
            for (Vec3 other : occupied) {
                if (candidate.distanceToSqr(other) < MIN_SEPARATION * MIN_SEPARATION) { clear = false; break; }
            }
            if (clear) return candidate;
        }
        // A new outer shell guarantees separation even with several overlapping volleys.
        double outer = radius;
        for (Vec3 other : occupied) outer = Math.max(outer, other.length());
        return randomDirection(random).scale(outer + MIN_SEPARATION + 0.01);
    }

    private static Vec3 randomDirection(RandomSource random) {
        double y = 0.15 + random.nextDouble() * 0.85;
        double angle = random.nextDouble() * Math.PI * 2;
        double horizontal = Math.sqrt(1 - y * y);
        return new Vec3(Math.cos(angle) * horizontal, y, Math.sin(angle) * horizontal);
    }

    public static float opacity(float age) { return smooth(age / APPEAR_TICKS); }

    public static Vec3 offset(Vec3 anchor, float age, int impact, float phase) {
        Vec3 direction = anchor.normalize();
        float flightStart = impact - FLIGHT_TICKS;
        float pullStart = flightStart - PULLBACK_TICKS;
        Vec3 bob = new Vec3(0, Math.sin(age * 0.22 + phase) * 0.12, 0);
        if (age < pullStart) {
            // Materialize slightly toward the target, then settle back into the hovering anchor.
            return anchor.add(direction.scale(-0.55 * (1 - opacity(age)))).add(bob);
        }
        if (age < flightStart) {
            float pull = smooth((age - pullStart) / PULLBACK_TICKS);
            return anchor.add(direction.scale(0.65 * pull)).add(bob.scale(1 - pull));
        }
        float flight = Mth.clamp((age - flightStart) / FLIGHT_TICKS, 0, 1);
        return anchor.add(direction.scale(0.65)).scale(1 - flight * flight);
    }

    private static float smooth(float value) {
        float t = Mth.clamp(value, 0, 1);
        return t * t * (3 - 2 * t);
    }
}
