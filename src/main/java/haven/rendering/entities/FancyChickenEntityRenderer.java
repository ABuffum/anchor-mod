package haven.rendering.entities;

import haven.HavenMod;
import haven.HavenModClient;
import haven.entities.FancyChickenEntity;
import haven.rendering.models.FancyChickenModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FancyChickenEntityRenderer extends MobEntityRenderer<FancyChickenEntity, FancyChickenModel<FancyChickenEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/fancy_chicken.png");

	public FancyChickenEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FancyChickenModel<>(context.getPart(HavenModClient.FANCY_CHICKEN_ENTITY_MODEL_LAYER)), 0.3F);
	}

	protected float getAnimationProgress(FancyChickenEntity chickenEntity, float f) {
		float g = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
		float h = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.maxWingDeviation);
		return (MathHelper.sin(g) + 1.0F) * h;
	}

	public Identifier getTexture(FancyChickenEntity entity) {
		return TEXTURE;
	}
}
