package jp.nogami_rion.alchemical_power.client;

import jp.nogami_rion.alchemical_power.entity.AlchetreeMysteriousScarecrowEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScarecrowRenderer extends MobRenderer<AlchetreeMysteriousScarecrowEntity, HumanoidModel<AlchetreeMysteriousScarecrowEntity>> {
    private static final ResourceLocation TEX =
            new ResourceLocation("alchemical_power:textures/entity/alchetree_scarecrow.png");

    public ScarecrowRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new HumanoidModel<>(ctx.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override public ResourceLocation getTextureLocation(AlchetreeMysteriousScarecrowEntity entity) { return TEX; }
}
