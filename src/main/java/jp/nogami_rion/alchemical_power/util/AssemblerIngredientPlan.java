package jp.nogami_rion.alchemical_power.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandler;

/** Assigns each required item once, including overlapping tags and exact ingredients. */
public final class AssemblerIngredientPlan {
    public static int[] create(List<Ingredient> ingredients, IItemHandler inventory) {
        int slots = inventory.getSlots();
        boolean[][] matches = new boolean[ingredients.size()][slots];
        int[] capacity = new int[slots];
        for (int slot = 0; slot < slots; slot++) {
            var stack = inventory.getStackInSlot(slot);
            capacity[slot] = stack.getCount();
            if (!stack.isEmpty()) {
                for (int i = 0; i < ingredients.size(); i++) matches[i][slot] = ingredients.get(i).test(stack);
            }
        }
        int[] assignment = new int[ingredients.size()];
        Arrays.fill(assignment, -1);
        int[] used = new int[slots];
        for (int i = 0; i < ingredients.size(); i++) {
            if (!assign(i, matches, capacity, assignment, used, new boolean[slots])) return null;
        }
        return used;
    }

    private static boolean assign(int ingredient, boolean[][] matches, int[] capacity,
                                  int[] assignment, int[] used, boolean[] visited) {
        for (int slot = 0; slot < capacity.length; slot++) {
            if (!matches[ingredient][slot] || visited[slot]) continue;
            visited[slot] = true;
            if (used[slot] < capacity[slot]) {
                used[slot]++;
                assignment[ingredient] = slot;
                return true;
            }
            for (int other = 0; other < assignment.length; other++) {
                if (assignment[other] == slot && assign(other, matches, capacity, assignment, used, visited)) {
                    assignment[ingredient] = slot;
                    return true;
                }
            }
        }
        return false;
    }
}
