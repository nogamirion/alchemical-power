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

public class ConfectionByBakingBatterIntoCylindricalShape extends Item {
    private static final int NAME_VARIANTS = 41;

    public ConfectionByBakingBatterIntoCylindricalShape() {
        super(new Properties().stacksTo(64).food((new FoodProperties.Builder()).nutrition(6).saturationMod(1.0f).build()));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.EAT;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.alchemical_power.confectionbybakingbatterintocylindricalshape.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public Component getName(@NotNull ItemStack stack) {
        long seconds = System.currentTimeMillis() / 1000;
        Random RANDOM = new Random(seconds);
        int variant = RANDOM.nextInt(NAME_VARIANTS) + 1; // 1から40までのランダムな数値を生成
        String key = "item.alchemical_power.confectionbybakingbatterintocylindricalshape.name." + variant;
        return Component.translatable(key);
    }
}
