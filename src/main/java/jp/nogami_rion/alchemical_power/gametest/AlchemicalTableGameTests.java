package jp.nogami_rion.alchemical_power.gametest;

import jp.nogami_rion.alchemical_power.block.entity.AbstractAlchemicalTableBlockEntity;
import jp.nogami_rion.alchemical_power.container.AlchemicalPowerTablesContainerView;
import jp.nogami_rion.alchemical_power.init.blocklist;
import jp.nogami_rion.alchemical_power.recipe.AlchemicalPowerTablesRecipe;
import jp.nogami_rion.alchemical_power.screen.AbstractAlchemicalPowerTablesMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("alchemical_power")
@PrefixGameTestTemplate(false)
public class AlchemicalTableGameTests {
    @GameTest(template = "reactor_test")
    public static void allViewersRefreshAndStaleResultsCannotBeTaken(GameTestHelper helper) {
        for (Block block : tables()) {
            var be = table(helper, block);
            Player first = helper.makeMockPlayer();
            Player second = helper.makeMockPlayer();
            var a = menu(be, first);
            var b = menu(be, second);
            be.getGrid().setItem(0, new ItemStack(Items.OAK_LOG));
            a.broadcastChanges();
            b.broadcastChanges();
            helper.assertTrue(a.getResultSlot().getItem().is(Items.OAK_PLANKS)
                    && b.getResultSlot().getItem().is(Items.OAK_PLANKS), "Both viewers must refresh");

            a.clicked(a.resultSlotIndex, 0, ClickType.PICKUP, first);
            helper.assertTrue(a.getCarried().is(Items.OAK_PLANKS) && a.getCarried().getCount() == 4,
                    "Normal pickup produces one batch");
            helper.assertTrue(!b.getResultSlot().mayPickup(second)
                    && b.quickMoveStack(second, b.resultSlotIndex).isEmpty(),
                    "A stale result must be rejected before the next broadcast");
            b.clicked(b.resultSlotIndex, 0, ClickType.PICKUP, second);
            helper.assertTrue(b.getCarried().isEmpty(), "Second viewer must not duplicate the batch");

            b.removed(second);
            be.getGrid().setItem(0, new ItemStack(Items.BIRCH_LOG));
            a.broadcastChanges();
            helper.assertTrue(a.getResultSlot().getItem().is(Items.BIRCH_PLANKS),
                    "Remaining viewer must still refresh after the last opener closes");
            a.setCarried(ItemStack.EMPTY);
            be.getGrid().setItem(0, new ItemStack(Items.OAK_LOG));
            a.clicked(a.resultSlotIndex, 0, ClickType.PICKUP, first);
            helper.assertTrue(a.getCarried().isEmpty() && be.getGrid().getItem(0).is(Items.OAK_LOG),
                    "A click on birch preview must not silently craft oak instead");
        }
        helper.succeed();
    }

    @GameTest(template = "reactor_test")
    public static void shiftClickConsumesEachBatchOnce(GameTestHelper helper) {
        for (Block block : tables()) {
            var be = table(helper, block);
            be.getGrid().setItem(0, new ItemStack(Items.OAK_LOG, 3));
            Player player = helper.makeMockPlayer();
            var menu = menu(be, player);
            menu.clicked(menu.resultSlotIndex, 0, ClickType.QUICK_MOVE, player);
            helper.assertTrue(be.getGrid().getItem(0).isEmpty()
                    && player.getInventory().countItem(Items.OAK_PLANKS) == 12
                    && menu.getResultSlot().getItem().isEmpty(),
                    "Shift-click must consume exactly three logs and produce twelve planks");
            for (int i = 0; i < 36; i++) {
                player.getInventory().setItem(i, new ItemStack(Items.COBBLESTONE, 64));
            }
            be.getGrid().setItem(0, new ItemStack(Items.OAK_LOG));
            menu.broadcastChanges();
            menu.clicked(menu.resultSlotIndex, 0, ClickType.QUICK_MOVE, player);
            helper.assertTrue(be.getGrid().getItem(0).getCount() == 1,
                    "Full inventory must not consume input on shift-click");
        }
        helper.succeed();
    }

    @GameTest(template = "reactor_test")
    public static void shapedRecipeRejectsExtraItemsAtEveryOffset(GameTestHelper helper) {
        var recipe = AlchemicalPowerTablesRecipe.shaped(
                new ResourceLocation("alchemical_power", "test_extra_inputs"), 3,
                new Ingredient[][] {{Ingredient.of(Items.COBBLESTONE), Ingredient.EMPTY},
                        {Ingredient.EMPTY, Ingredient.of(Items.DIRT)}},
                Ingredient.EMPTY, new ItemStack(Items.DIAMOND));
        for (Block block : tables()) {
            var be = table(helper, block);
            var grid = be.getGrid();
            var view = new AlchemicalPowerTablesContainerView(be);
            for (int y = 0; y < grid.getGridSize() - 1; y++) {
                for (int x = 0; x < grid.getGridSize() - 1; x++) {
                    grid.clearContent();
                    grid.setItem(x, y, new ItemStack(Items.COBBLESTONE));
                    grid.setItem(x + 1, y + 1, new ItemStack(Items.DIRT));
                    helper.assertTrue(recipe.matches(view, helper.getLevel()), "Offset recipe must match");
                    // Add an unrelated item outside this pattern, including at grid edges.
                    for (int i = 0; i < be.getCraftingSize(); i++) {
                        int sx = i % grid.getGridSize(), sy = i / grid.getGridSize();
                        if (sx >= x && sx <= x + 1 && sy >= y && sy <= y + 1) continue;
                        grid.setItem(i, new ItemStack(Items.EMERALD));
                        helper.assertTrue(!recipe.matches(view, helper.getLevel()), "Extra input must reject craft");
                        grid.setItem(i, ItemStack.EMPTY);
                    }
                }
            }
        }
        helper.succeed();
    }

    private static Block[] tables() {
        return new Block[] {blocklist.ALCHEMY_TABLE_RE.get(), blocklist.HERMES_WORKBENCH_RE.get(),
                blocklist.TRANSCENDENTAL_TABLE_RE.get()};
    }

    private static AbstractAlchemicalTableBlockEntity table(GameTestHelper helper, Block block) {
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, block);
        return (AbstractAlchemicalTableBlockEntity) helper.getLevel().getBlockEntity(helper.absolutePos(pos));
    }

    private static AbstractAlchemicalPowerTablesMenu menu(AbstractAlchemicalTableBlockEntity be, Player player) {
        return (AbstractAlchemicalPowerTablesMenu) ((MenuProvider) be).createMenu(1, player.getInventory(), player);
    }
}
