package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModBase;
import fun.mousewich.ModClient;
import fun.mousewich.client.render.entity.model.FancyChickenModel;
import fun.mousewich.entity.passive.FancyChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FancyChickenEntityRenderer extends MobEntityRenderer<FancyChickenEntity, FancyChickenModel<FancyChickenEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/fancy_chicken.png");

	public FancyChickenEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FancyChickenModel<>(context.getPart(ModClient.FANCY_CHICKEN_MODEL_LAYER)), 0.3F);
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