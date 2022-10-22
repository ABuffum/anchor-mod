package haven.rendering.entities;

import haven.HavenMod;
import haven.HavenModClient;
import haven.entities.hostile.warden.WardenEntity;
import haven.rendering.features.WardenFeatureRenderer;
import haven.rendering.models.WardenEntityModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class WardenEntityRenderer extends MobEntityRenderer<WardenEntity, WardenEntityModel<WardenEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/warden/warden.png");
	private static final Identifier BIOLUMINESCENT_LAYER_TEXTURE = HavenMod.ID("textures/entity/warden/warden_bioluminescent_layer.png");
	private static final Identifier HEART_TEXTURE = HavenMod.ID("textures/entity/warden/warden_heart.png");
	private static final Identifier PULSATING_SPOTS_1_TEXTURE = HavenMod.ID("textures/entity/warden/warden_pulsating_spots_1.png");
	private static final Identifier PULSATING_SPOTS_2_TEXTURE = HavenMod.ID("textures/entity/warden/warden_pulsating_spots_2.png");

	public WardenEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WardenEntityModel(context.getPart(HavenModClient.MODEL_WARDEN_LAYER)), 0.9f);
		this.addFeature(new WardenFeatureRenderer<WardenEntity, WardenEntityModel<WardenEntity>>(this, BIOLUMINESCENT_LAYER_TEXTURE, (warden, tickDelta, animationProgress) -> 1.0f, WardenEntityModel::getHeadAndLimbs));
		this.addFeature(new WardenFeatureRenderer<WardenEntity, WardenEntityModel<WardenEntity>>(this, PULSATING_SPOTS_1_TEXTURE, (warden, tickDelta, animationProgress) -> Math.max(0.0f, MathHelper.cos(animationProgress * 0.045f) * 0.25f), WardenEntityModel::getBodyHeadAndLimbs));
		this.addFeature(new WardenFeatureRenderer<WardenEntity, WardenEntityModel<WardenEntity>>(this, PULSATING_SPOTS_2_TEXTURE, (warden, tickDelta, animationProgress) -> Math.max(0.0f, MathHelper.cos(animationProgress * 0.045f + (float)Math.PI) * 0.25f), WardenEntityModel::getBodyHeadAndLimbs));
		this.addFeature(new WardenFeatureRenderer<WardenEntity, WardenEntityModel<WardenEntity>>(this, TEXTURE, (warden, tickDelta, animationProgress) -> warden.getTendrilPitch(tickDelta), WardenEntityModel::getTendrils));
		this.addFeature(new WardenFeatureRenderer<WardenEntity, WardenEntityModel<WardenEntity>>(this, HEART_TEXTURE, (warden, tickDelta, animationProgress) -> warden.getHeartPitch(tickDelta), WardenEntityModel::getBody));
	}

	@Override
	public Identifier getTexture(WardenEntity wardenEntity) {
		return TEXTURE;
	}
}