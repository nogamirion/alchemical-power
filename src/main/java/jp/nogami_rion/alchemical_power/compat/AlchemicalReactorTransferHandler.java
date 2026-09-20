package jp.nogami_rion.alchemical_power.compat;

import jp.nogami_rion.alchemical_power.network.ReactorTransferNetwork;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalReactorRecipe;
import jp.nogami_rion.alchemical_power.screen.AlchemicalReactorMenu;
import jp.nogami_rion.alchemical_power.screen.ModMenuTypes;
import jp.nogami_rion.alchemical_power.util.ReactorRecipeTransfer;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.items.ItemStackHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import static jp.nogami_rion.alchemical_power.block.entity.AlchemicalReactorBlockEntity.*;

public class AlchemicalReactorTransferHandler implements IRecipeTransferHandler<AlchemicalReactorMenu, AlchemicalReactorRecipe> {
    private final IRecipeTransferHandlerHelper helper;
    public AlchemicalReactorTransferHandler(IRecipeTransferHandlerHelper helper) { this.helper = helper; }
    @Override public Class<AlchemicalReactorMenu> getContainerClass() { return AlchemicalReactorMenu.class; }
    @Override public Optional<MenuType<AlchemicalReactorMenu>> getMenuType() { return Optional.of(ModMenuTypes.ALCHEMICAL_REACTOR_MENU.get()); }
    @Override public RecipeType<AlchemicalReactorRecipe> getRecipeType() { return AlchemicalReactorCategory.TYPE; }
    @Override public @Nullable IRecipeTransferError transferRecipe(AlchemicalReactorMenu menu, AlchemicalReactorRecipe recipe,
            IRecipeSlotsView slots, Player player, boolean maxTransfer, boolean doTransfer) {
        var error = ReactorRecipeTransfer.transfer(menu, player, recipe, maxTransfer, false);
        if (error == ReactorRecipeTransfer.Error.MISSING_ITEMS) {
            // Count both machine inputs and player inventory, excluding the sample and upgrades.
            var pool = new ItemStackHandler(INPUTS + 36);
            for (int i = 0; i < pool.getSlots(); i++)
                pool.setStackInSlot(i, i < INPUTS ? menu.inputStack(i) : menu.getSlot(SLOTS + i - INPUTS).getItem().copy());
            var missing = new ArrayList<IRecipeSlotView>();
            for (int index : recipe.missingInputs(pool))
                slots.findSlotByName("material_" + index).ifPresent(missing::add);
            return helper.createUserErrorForMissingSlots(
                    Component.translatable("gui.alchemical_power.reactor.transfer.missing_items"), missing);
        }
        if (error == ReactorRecipeTransfer.Error.WRONG_SAMPLE) {
            var tooltip = helper.createUserErrorWithTooltip(Component.translatable("gui.alchemical_power.reactor.transfer.wrong_sample"));
            // Supply separate tooltip rows, rather than relying on newline handling in the renderer.
            return new IRecipeTransferError() {
                @Override public Type getType() { return tooltip.getType(); }
                @Override public List<Component> getTooltip() {
                    return tooltip.getTooltip().stream().flatMap(line -> Arrays.stream(line.getString().split("\n", -1))
                            .<Component>map(part -> Component.literal(part).setStyle(line.getStyle()))).toList();
                }
            };
        }
        if (error != ReactorRecipeTransfer.Error.NONE)
            return helper.createUserErrorWithTooltip(Component.translatable("gui.alchemical_power.reactor.transfer." + error.name().toLowerCase(Locale.ROOT)));
        if (doTransfer) ReactorTransferNetwork.send(menu.containerId, recipe.getId(), maxTransfer);
        return null;
    }
}
