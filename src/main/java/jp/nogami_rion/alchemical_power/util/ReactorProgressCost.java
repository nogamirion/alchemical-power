package jp.nogami_rion.alchemical_power.util;

/** Exact cumulative costs, including totals larger than an int, without multiplication overflow. */
public final class ReactorProgressCost {
    private ReactorProgressCost() {}
    public static long cumulative(long total, int elapsed, int duration) {
        if (total < 0 || duration < 1 || elapsed < 0 || elapsed > duration)
            throw new IllegalArgumentException("Invalid reactor progress cost");
        return (total / duration) * elapsed + (total % duration) * elapsed / duration;
    }
    public static long efficientEnergy(long total, int tier) {
        int percent = Math.max(20, 100 - 15 * tier);
        return Math.max(1, (total / 100) * percent + ((total % 100) * percent + 99) / 100);
    }
}
