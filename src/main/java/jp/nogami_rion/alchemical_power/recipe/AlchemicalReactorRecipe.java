package jp.nogami_rion.alchemical_power.recipe;

import com.google.gson.*;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.*;
import static jp.nogami_rion.alchemical_power.util.ReactorItemHandler.TOTAL_INPUT_LIMIT;

/** Explicit reactor recipes: sample and fluid may be omitted when they are not required. */
public class AlchemicalReactorRecipe implements Recipe<Container> {
    public record Input(Ingredient ingredient, int count) {}
    private final ResourceLocation id;
    private final List<Input> inputs;
    private final ItemStack sample, result;
    private final FluidStack fluid;
    private final int toolTier, time;
    private final long energy;
    private final long fluidAmount;

    public AlchemicalReactorRecipe(ResourceLocation id, List<Input> inputs, ItemStack sample,
                                  ItemStack result, FluidStack fluid, int toolTier, int time, long energy) {
        this(id, inputs, sample, result, fluid, toolTier, time, energy, fluid.getAmount());
    }

    public AlchemicalReactorRecipe(ResourceLocation id, List<Input> inputs, ItemStack sample,
                                  ItemStack result, FluidStack fluid, int toolTier, int time, long energy, long fluidAmount) {
        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.sample = sample.copy();
        this.result = result.copy();
        this.fluid = fluid.copy();
        if (fluidAmount < 0 || (fluid.isEmpty() != (fluidAmount == 0)))
            throw new JsonSyntaxException("Invalid reactor fluid amount: " + id);
        this.fluidAmount = fluidAmount;
        if (!this.fluid.isEmpty()) this.fluid.setAmount((int) Math.min(Integer.MAX_VALUE, fluidAmount));
        this.toolTier = toolTier;
        this.time = time;
        this.energy = energy;
        if (inputs.isEmpty() || inputs.size() > 12 || toolTier < 0 || toolTier > 4 || time < 1 || energy < 1
                || result.isEmpty() || result.getCount() > result.getMaxStackSize())
            throw new JsonSyntaxException("Invalid reactor recipe: " + id);
        for (Input input : inputs) {
            if (input.count < 1 || input.count > TOTAL_INPUT_LIMIT || input.ingredient == Ingredient.EMPTY)
                throw new JsonSyntaxException("Invalid reactor ingredient: " + id);
        }
        if (!sample.isEmpty() && (!ItemStack.isSameItemSameTags(sample, result) || sample.getCount() != 1))
            throw new JsonSyntaxException("Sample must be one of the output item: " + id);
    }

    public List<Input> inputs() { return inputs; }
    public ItemStack sample() { return sample.copy(); }
    public FluidStack fluid() { return fluid.copy(); }
    /** Exact operation cost; FluidStack is only an int-sized fluid descriptor. */
    public long fluidAmount() { return fluidAmount; }
    public int toolTier() { return toolTier; }
    public int time() { return time; }
    /** Total FE per operation before upgrades, not FE/t. */
    public long energy() { return energy; }
    public boolean matchesSample(ItemStack stack) {
        return sample.isEmpty() ? stack.isEmpty() : !stack.isEmpty() && ItemStack.isSameItemSameTags(sample, stack);
    }

    /** Max-flow allocation handles overlapping tags, split stacks, and repeated ingredients. */
    public int[] allocate(IItemHandler handler) {
        return allocate(handler, 12, 1);
    }

    /** Also used to plan counted inventory transfers without mutating any stacks. */
    public int[] allocate(IItemHandler handler, int slots, int batches) {
        long total = inputs.stream().mapToLong(Input::count).sum() * batches;
        if (batches < 1 || total > TOTAL_INPUT_LIMIT) return null;
        Allocation allocation = allocatePartial(handler, slots, batches);
        return allocation.missing.length == 0 ? allocation.consumed : null;
    }

    /** Indices of material requirements that cannot be filled for one operation. */
    public int[] missingInputs(IItemHandler handler) {
        return allocatePartial(handler, handler.getSlots(), 1).missing;
    }

    private record Allocation(int[] consumed, int[] missing) {}

    private Allocation allocatePartial(IItemHandler handler, int slots, int batches) {
        if (slots < 1 || slots > handler.getSlots()) throw new IllegalArgumentException("Invalid source slots");
        int n = inputs.size(), sink = n + slots + 1, size = sink + 1;
        int[][] residual = new int[size][size];
        int demand = 0;
        for (int i = 0; i < n; i++) {
            Input input = inputs.get(i);
            residual[0][i + 1] = input.count * batches;
            demand += input.count * batches;
            for (int slot = 0; slot < slots; slot++)
                if (input.ingredient.test(handler.getStackInSlot(slot))) residual[i + 1][n + 1 + slot] = TOTAL_INPUT_LIMIT;
        }
        for (int slot = 0; slot < slots; slot++) residual[n + 1 + slot][sink] = handler.getStackInSlot(slot).getCount();
        int flow = 0;
        while (flow < demand) {
            int[] parent = new int[size];
            Arrays.fill(parent, -1);
            parent[0] = 0;
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            queue.add(0);
            while (!queue.isEmpty() && parent[sink] < 0) {
                int u = queue.remove();
                for (int v = 1; v < size; v++) if (parent[v] < 0 && residual[u][v] > 0) {
                    parent[v] = u;
                    queue.add(v);
                }
            }
            if (parent[sink] < 0) break;
            int amount = demand - flow;
            for (int v = sink; v != 0; v = parent[v]) amount = Math.min(amount, residual[parent[v]][v]);
            for (int v = sink; v != 0; v = parent[v]) {
                residual[parent[v]][v] -= amount;
                residual[v][parent[v]] += amount;
            }
            flow += amount;
        }
        int[] consumed = new int[slots];
        for (int slot = 0; slot < slots; slot++) consumed[slot] = residual[sink][n + 1 + slot];
        int[] missing = java.util.stream.IntStream.range(0, n)
                .filter(i -> residual[0][i + 1] > 0).toArray();
        return new Allocation(consumed, missing);
    }

    @Override public boolean matches(Container container, Level level) { return false; }
    @Override public ItemStack assemble(Container container, RegistryAccess access) { return result.copy(); }
    @Override public boolean canCraftInDimensions(int width, int height) { return false; }
    @Override public ItemStack getResultItem(RegistryAccess access) { return result.copy(); }
    @Override public ResourceLocation getId() { return id; }
    @Override public RecipeSerializer<?> getSerializer() { return ModRecipes.ALCHEMICAL_REACTOR_SERIALIZER.get(); }
    @Override public RecipeType<?> getType() { return ModRecipes.ALCHEMICAL_REACTOR_TYPE.get(); }
    @Override public boolean isSpecial() { return true; }

    public static class Serializer implements RecipeSerializer<AlchemicalReactorRecipe> {
        @Override public AlchemicalReactorRecipe fromJson(ResourceLocation id, JsonObject json) {
            List<Input> inputs = new ArrayList<>();
            for (JsonElement element : GsonHelper.getAsJsonArray(json, "ingredients")) {
                JsonObject entry = element.getAsJsonObject();
                inputs.add(new Input(Ingredient.fromJson(entry.get("ingredient")), GsonHelper.getAsInt(entry, "count", 1)));
            }
            FluidStack fluid = FluidStack.EMPTY;
            long amount = 0;
            if (json.has("fluid")) {
                JsonObject liquid = GsonHelper.getAsJsonObject(json, "fluid");
                ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(liquid, "fluid"));
                if (!ForgeRegistries.FLUIDS.containsKey(fluidId)) throw new JsonSyntaxException("Unknown fluid: " + fluidId);
                amount = GsonHelper.getAsLong(liquid, "amount");
                if (amount < 1) throw new JsonSyntaxException("Invalid reactor fluid amount: " + id);
                fluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(fluidId), 1);
                if (fluid.isEmpty()) throw new JsonSyntaxException("Invalid reactor fluid: " + id);
            }
            ItemStack sample = json.has("sample") ? ShapedRecipe.itemStackFromJson(json.getAsJsonObject("sample")) : ItemStack.EMPTY;
            return new AlchemicalReactorRecipe(id, inputs, sample,
                    ShapedRecipe.itemStackFromJson(json.getAsJsonObject("result")), fluid,
                    GsonHelper.getAsInt(json, "tool_tier"), GsonHelper.getAsInt(json, "time"), GsonHelper.getAsLong(json, "energy"), amount);
        }
        @Override public AlchemicalReactorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int size = buf.readVarInt();
            if (size < 1 || size > 12) throw new IllegalArgumentException("Invalid reactor ingredient count");
            List<Input> inputs = new ArrayList<>();
            for (int i = 0; i < size; i++) inputs.add(new Input(Ingredient.fromNetwork(buf), buf.readVarInt()));
            return new AlchemicalReactorRecipe(id, inputs, buf.readItem(), buf.readItem(), buf.readFluidStack(),
                    buf.readVarInt(), buf.readVarInt(), buf.readVarLong(), buf.readVarLong());
        }
        @Override public void toNetwork(FriendlyByteBuf buf, AlchemicalReactorRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            for (Input input : recipe.inputs) { input.ingredient.toNetwork(buf); buf.writeVarInt(input.count); }
            buf.writeItem(recipe.sample); buf.writeItem(recipe.result); buf.writeFluidStack(recipe.fluid);
            buf.writeVarInt(recipe.toolTier); buf.writeVarInt(recipe.time); buf.writeVarLong(recipe.energy);
            buf.writeVarLong(recipe.fluidAmount);
        }
    }
}
