package jp.nogami_rion.alchemical_power.util;

import jp.nogami_rion.alchemical_power.recipe.AlchemicalReactorRecipe;
import jp.nogami_rion.alchemical_power.screen.AlchemicalReactorMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import static jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity.*;

/** Shared client preview / authoritative server planner. Samples, upgrades, fluids and output are excluded. */
public final class ReactorRecipeTransfer {
    private ReactorRecipeTransfer() {}
    public enum Error { NONE, MISSING_ITEMS, NO_SPACE, INVALID_MENU, WRONG_SAMPLE }
    public record Plan(Error error, ItemStack[] inputs, ItemStack[] inventory, int batches) {
        static Plan failure(Error error) { return new Plan(error, null, null, 0); }
    }
    public static Error transfer(AlchemicalReactorMenu menu, Player player, AlchemicalReactorRecipe recipe, boolean max, boolean execute) {
        if (player.isSpectator() || !menu.stillValid(player)) return Error.INVALID_MENU;
        if (!recipe.matchesSample(menu.getSlot(SAMPLE).getItem())) return Error.WRONG_SAMPLE;
        ItemStackHandler pool = new ItemStackHandler(INPUTS + 36);
        for (int i = 0; i < pool.getSlots(); i++) {
            var slot = menu.getSlot(i < INPUTS ? i : SLOTS + i - INPUTS);
            if (!slot.mayPickup(player)) return Error.INVALID_MENU;
            pool.setStackInSlot(i, i < INPUTS ? menu.inputStack(i) : slot.getItem().copy());
        }
        Plan plan = plan(recipe, pool, max);
        if (execute && plan.error == Error.NONE) {
            // All validation/packing has finished. Commit on the server thread as one operation.
            for (int i = 0; i < INPUTS; i++) menu.setInputStack(i, plan.inputs[i]);
            for (int i = 0; i < 36; i++) menu.getSlot(SLOTS + i).set(plan.inventory[i]);
            menu.broadcastChanges();
        }
        return plan.error;
    }
    /** Pool order: twelve existing material slots, then the player's 36 inventory slots. */
    public static Plan plan(AlchemicalReactorRecipe recipe, IItemHandler pool, boolean max) {
        if (pool.getSlots() != INPUTS + 36) return Plan.failure(Error.INVALID_MENU);
        int upper = 1;
        if (max) {
            int required = recipe.inputs().stream().mapToInt(AlchemicalReactorRecipe.Input::count).sum();
            upper = ReactorItemHandler.TOTAL_INPUT_LIMIT / required;
            for (var input : recipe.inputs()) {
                int available = 0;
                for (int i = 0; i < pool.getSlots(); i++) {
                    ItemStack stack = pool.getStackInSlot(i);
                    if (input.ingredient().test(stack)) available += stack.getCount();
                }
                upper = Math.min(upper, available / input.count());
            }
        }
        boolean matched = false;
        for (int batches = upper; batches >= 1; batches--) {
            int[] allocation = recipe.allocate(pool, pool.getSlots(), batches);
            if (allocation == null) continue;
            matched = true;
            ItemStack[] inputs = empty(INPUTS), inventory = empty(36);
            boolean fits = true;
            // Keep unconsumed player items in their original slots; pack selected materials into the reactor.
            for (int i = 0; i < pool.getSlots(); i++) {
                ItemStack original = pool.getStackInSlot(i);
                if (allocation[i] > 0) {
                    ItemStack selected = original.copy(); selected.setCount(allocation[i]);
                    if (!insert(inputs, selected, true)) { fits = false; break; }
                }
                if (i >= INPUTS) {
                    ItemStack remainder = original.copy(); remainder.shrink(allocation[i]);
                    inventory[i - INPUTS] = remainder;
                }
            }
            if (!fits) continue;
            // Return old/excess machine ingredients to the player. Fail atomically if there is no room.
            for (int i = 0; i < INPUTS; i++) {
                ItemStack remainder = pool.getStackInSlot(i).copy(); remainder.shrink(allocation[i]);
                if (!insert(inventory, remainder, false)) { fits = false; break; }
            }
            if (fits) return new Plan(Error.NONE, inputs, inventory, batches);
        }
        return Plan.failure(matched ? Error.NO_SPACE : Error.MISSING_ITEMS);
    }
    private static ItemStack[] empty(int size) {
        ItemStack[] result = new ItemStack[size];
        java.util.Arrays.fill(result, ItemStack.EMPTY);
        return result;
    }
    private static boolean insert(ItemStack[] slots, ItemStack source, boolean reactor) {
        if (source.isEmpty()) return true;
        ItemStack remaining = source.copy();
        for (ItemStack slot : slots) if (!slot.isEmpty() && ItemStack.isSameItemSameTags(slot, remaining)) {
            int amount = Math.min(remaining.getCount(), (reactor ? ReactorItemHandler.INPUT_LIMIT : Math.min(64, slot.getMaxStackSize())) - slot.getCount());
            if (amount > 0) { slot.grow(amount); remaining.shrink(amount); }
            if (remaining.isEmpty()) return true;
        }
        for (int i = 0; i < slots.length; i++) if (slots[i].isEmpty()) {
            int amount = Math.min(remaining.getCount(), (reactor ? ReactorItemHandler.INPUT_LIMIT : Math.min(64, remaining.getMaxStackSize())));
            slots[i] = remaining.copy(); slots[i].setCount(amount); remaining.shrink(amount);
            if (remaining.isEmpty()) return true;
        }
        return false;
    }
}
