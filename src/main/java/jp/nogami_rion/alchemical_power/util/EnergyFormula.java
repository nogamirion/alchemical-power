package jp.nogami_rion.alchemical_power.util;

public class EnergyFormula {
    public static final int BASE_ENERGY_PARTICK = 200;
    public static final int BASE_TIME = 10;

    public static EnergyStats calculate(
            int ingredientCount,
            int speedTier,
            int efficiencyTier
    ){
        int baseTime = BASE_TIME * ingredientCount;
        int baseTotalEnergy = BASE_ENERGY_PARTICK * 10 * ingredientCount;
        double speedMultiplier = 1.0 + 0.36 * (speedTier * speedTier);
        double efficiencyMultiplier = Math.max(0.2,1.0 - 0.15 * efficiencyTier);
        int finalTime = (int) (baseTime / speedMultiplier);
        int fePerTick = (int) (BASE_ENERGY_PARTICK * Math.pow(speedMultiplier,2) * efficiencyMultiplier);
        int finalTotalEnergy = finalTime * fePerTick;

//        System.out.println("==== AutoAssembler Calculation ====");
//        System.out.println("Ingredients: " + ingredientCount);
//        System.out.println("SpeedTier: " + speedTier);
//        System.out.println("EfficiencyTier: " + efficiencyTier);
//        System.out.println("SpeedMultiplier: " + speedMultiplier);
//        System.out.println("EfficiencyMultiplier: " + efficiencyMultiplier);
//        System.out.println("BaseTime: " + baseTime);
//        System.out.println("FinalTime: " + finalTime);
//        System.out.println("FE/t: " + fePerTick);
//        System.out.println("BASETotalEnergy: " + baseTotalEnergy);
//        System.out.println("FinalTotalEnergy: " + finalTotalEnergy);
//        System.out.println("===================================");

        return new EnergyStats(baseTime,finalTime,baseTotalEnergy,finalTotalEnergy,fePerTick);
    }
}
