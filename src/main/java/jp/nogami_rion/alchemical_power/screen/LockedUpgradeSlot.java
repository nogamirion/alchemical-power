package jp.nogami_rion.alchemical_power.screen;

import jp.nogami_rion.alchemical_power.util.UpgradeRemovalState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import java.util.function.Supplier;

public class LockedUpgradeSlot extends SlotItemHandler {
    private final boolean client;
    private final Supplier<UpgradeRemovalState> state;
    private final int[] values = new int[5];

    public LockedUpgradeSlot(IItemHandler handler, int index, int x, int y,
                             boolean client, Supplier<UpgradeRemovalState> state) {
        super(handler, index, x, y);
        this.client = client;
        this.state = state;
    }

    public UpgradeRemovalState removalState() {
        return client ? new UpgradeRemovalState(values[0], values[1] | values[2] << 16,
                values[3] | values[4] << 16) : state.get();
    }

    // Menu integers travel as shorts; split capacities to preserve their full range.
    public ContainerData syncData() {
        return new ContainerData() {
            public int get(int index) {
                if (client) return values[index];
                UpgradeRemovalState s = state.get();
                return switch (index) {
                    case 0 -> s.reasons();
                    case 1 -> s.energyLimit() & 0xffff;
                    case 2 -> s.energyLimit() >>> 16;
                    case 3 -> s.tankLimit() & 0xffff;
                    case 4 -> s.tankLimit() >>> 16;
                    default -> 0;
                };
            }
            public void set(int index, int value) { values[index] = value & 0xffff; }
            public int getCount() { return values.length; }
        };
    }

    @Override public boolean mayPickup(Player player) { return removalState().allowed(); }
    @Override public boolean mayPlace(ItemStack stack) {
        return (!hasItem() || removalState().allowed()) && super.mayPlace(stack);
    }
    @Override public int getMaxStackSize() { return 1; }
}
