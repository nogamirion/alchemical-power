package jp.nogami_rion.alchemical_power.util;

/** Reasons an installed upgrade cannot currently be removed. */
public record UpgradeRemovalState(int reasons, int energyLimit, int tankLimit) {
    public static final int PROCESSING = 1, ENERGY = 2, WATER = 4, OUTPUT = 8, GENERATING = 16;
    public boolean allowed() { return reasons == 0; }
}
