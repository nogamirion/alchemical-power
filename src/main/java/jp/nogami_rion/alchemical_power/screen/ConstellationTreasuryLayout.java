package jp.nogami_rion.alchemical_power.screen;

/** Item interiors in the 224x224 star-chart background. */
public final class ConstellationTreasuryLayout {
    private ConstellationTreasuryLayout() {}
    public static final int SIZE = 224;
    private static final int[] COLUMNS = {27, 46, 66, 85, 104, 124, 143, 162, 182};
    private static final int[] WEAPON_ROWS = {27, 47, 66, 86};
    private static final int[] INVENTORY_ROWS = {124, 144, 163};
    public static final int HOTBAR_Y = 188;
    public static int column(int column) { return COLUMNS[column]; }
    public static int weaponRow(int row) { return WEAPON_ROWS[row]; }
    public static int inventoryRow(int row) { return INVENTORY_ROWS[row]; }
}
