package haven.rendering.entities;

import haven.ModClient;
import haven.entities.hostile.warden.WardenEntity;
import haven.rendering.features.WardenFeatureRenderer;
import haven.rendering.models.WardenEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class WardenEntityRenderer extends MobEntityRenderer<WardenEntity, WardenEntityModel<WardenEntity>> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/warden/warden.png");
	private static final Identifier BIOLUMINESCENT_LAYER_TEXTURE = new Identifier("textures/entity/warden/warden_bioluminescent_layer.png");
	private static final Identifier HEART_TEXTURE = new Identifier("textures/entity/warden/warden_heart.png");
	private static final Identifier PULSATING_SPOTS_1_TEXTURE = new Identifier("textures/entity/warden/warden_pulsating_spots_1.png");
	private static final Identifier PULSATING_SPOTS_2_TEXTURE = new Identifier("textures/entity/warden/warden_pulsating_spots_2.png");

	public WardenEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WardenEntityModel<>(context.getPart(ModClient.MODEL_WARDEN_LAYER)), 0.9f);
		this.addFeature(new WardenFeatureRenderer<>(this, BIOLUMINESCENT_LAYER_TEXTURE, (warden, tickDelta, animationProgress) -> 1.0f, WardenEntityModel::getHeadAndLimbs));
		this.addFeature(new WardenFeatureRenderer<>(this, PULSATING_SPOTS_1_TEXTURE, (warden, tickDelta, animationProgress) -> Math.max(0.0f, MathHelper.cos(animationProgress * 0.045f) * 0.25f), WardenEntityModel::getBodyHeadAndLimbs));
		this.addFeature(new WardenFeatureRenderer<>(this, PULSATING_SPOTS_2_TEXTURE, (warden, tickDelta, animationProgress) -> Math.max(0.0f, MathHelper.cos(animationProgress * 0.045f + (float)Math.PI) * 0.25f), WardenEntityModel::getBodyHeadAndLimbs));
		this.addFeature(new WardenFeatureRenderer<>(this, TEXTURE, (warden, tickDelta, animationProgress) -> warden.getTendrilPitch(tickDelta), WardenEntityModel::getTendrils));
		this.addFeature(new WardenFeatureRenderer<>(this, HEART_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getHeartPitch(tickDelta), WardenEntityModel::getBody));
	}

	@Override
	public Identifier getTexture(WardenEntity wardenEntity) { return TEXTURE; }
}