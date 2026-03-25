package jp.nogami_rion.alchemical_power.util;

public record EnergyStats(
        int baseTime,
        int finalTime,
        int baseTotalEnergy,
        int finalTotalEnergy,
        int fePerTick
) {
}
