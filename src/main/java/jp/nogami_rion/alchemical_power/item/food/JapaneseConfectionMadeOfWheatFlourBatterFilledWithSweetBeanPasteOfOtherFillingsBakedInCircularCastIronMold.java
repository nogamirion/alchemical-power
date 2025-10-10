package jp.nogami_rion.alchemical_power.item.food;

import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class JapaneseConfectionMadeOfWheatFlourBatterFilledWithSweetBeanPasteOfOtherFillingsBakedInCircularCastIronMold extends Item {

    public JapaneseConfectionMadeOfWheatFlourBatterFilledWithSweetBeanPasteOfOtherFillingsBakedInCircularCastIronMold() {
        super(new Properties().stacksTo(64).food((new FoodProperties.Builder()).nutrition(246).saturationMod(1.0f).build()));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.EAT;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        long seconds = System.currentTimeMillis() / 3000;
        Random RANDOM = new Random(seconds);
        int variant = RANDOM.nextInt(2) + 1;
        String key = "tooltip.alchemical_power.jcmowfbfwsbpoofbiccim.tooltip." + variant;
        pTooltipComponents.add(Component.translatable(key));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
