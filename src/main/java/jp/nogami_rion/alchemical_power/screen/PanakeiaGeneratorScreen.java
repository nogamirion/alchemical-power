package jp.nogami_rion.alchemical_power.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import jp.nogami_rion.alchemical_power.Alchemical_power;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class PanakeiaGeneratorScreen extends AbstractContainerScreen<PanakeiaGeneratorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Alchemical_power.MODID,"textures/gui/panakeia_generator_gui.png");
    private float displayedEnergy = 0;

    public PanakeiaGeneratorScreen(PanakeiaGeneratorMenu menu, Inventory inventory, Component title){
        super(menu, inventory, title);

        this.imageWidth = 170;
        this.imageHeight = 170;
    }

    @Override
    protected void init(){
        super.init();
        this.titleLabelX = 6;
        this.titleLabelY = 4;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick,int mouseX,int mouseY){
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int centerX = x + 77;
        int centerY = y + 32;

        graphics.blit(TEXTURE,x,y,0,0,imageWidth,imageHeight,170,183);

        //燃焼時間
        ItemStack fuel = menu.getActiveFuel();
        if(menu.getBurnTime() > 0 && !fuel.isEmpty()) {
                float burnTime = menu.getBurnTime();
                float maxBurn = 200;

                float burnRatio = maxBurn == 0 ? 0 : burnTime / maxBurn;
                //透明度
                float alpha = burnRatio > 0.6f ? 1f : 0.2f + (burnRatio * 2f) * (burnRatio * 2f);
                //起動半径
                float radius = 6f + (20f * (float)Math.sqrt(burnRatio));
                //回転角
                long time = minecraft.level.getGameTime();
                float rotation = (time * 6f) % 360;

                double angle = Math.toRadians(rotation);
                int orbitX = (int) (Math.cos(angle) * radius);
                int orbitY = (int) (Math.sin(angle) * radius);
                int itemX = centerX + orbitX;
                int itemY = centerY + orbitY;

                RenderSystem.setShaderColor(1f, 1f, 1f, alpha);
                graphics.renderItem(fuel, itemX, itemY);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        }


        //エネルギー総量
        int energy = getEnergyScaled();
        graphics.blit(TEXTURE,
                x + 11,
                y + 61,
                0,
                171,
                energy,
                12,
                170,
                183);

    }

    @Override
    public void render(GuiGraphics graphics,int mouseX,int mouseY, float partialTick){
        int targetEnergy = menu.getEnergy();
        displayedEnergy += (targetEnergy - displayedEnergy) * 0.2f;

        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics,int mouseX,int mouseY){
        graphics.drawString(this.font,this.title,this.titleLabelX,this.titleLabelY,4210752,false);

        int energy = menu.getEnergy();
        int maxEnergy = menu.getMaxEnergy();
        int output = menu.getOutput();

        String energyText = formatFE(energy) + " / " + formatFE(maxEnergy) + " FE";
        int energyTextWidth = font.width(energyText);
        graphics.drawString(font,energyText,(148 - energyTextWidth)/2,64,0x000000,false);

        String outputText = formatFE(output) + " FE/t";
        int outputTextWidth = font.width(outputText);
        graphics.drawString(font,outputText,120 - (outputTextWidth / 2),50,0x000000,false);

    }

    private String formatFE(long value){

        if(value >= 1_000_000_000)
            return String.format("%.1fG",value / 1_000_000_000.0);

        if(value >= 1_000_000)
            return String.format("%.1fM", value / 1_000_000.0);

        if(value >= 1_000)
            return String.format("%.1fk", value / 1_000.0);

        return Long.toString(value);
    }


    //progress helpers
    private int getBurnProgress(){
        int burn = menu.getBurnTime();
        int max = 200;
        return burn * 13 / max;
    }

    private int getEnergyScaled(){
        int max = menu.getMaxEnergy();
        if(max == 0) return 0;
        return (int)(128 * (displayedEnergy / (float)max));
    }

}
