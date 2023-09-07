package fun.mousewich.client.render.entity.renderer.chicken;

import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.FancyChickenEntityModel;
import fun.mousewich.entity.passive.chicken.FancyChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FancyChickenEntityRenderer extends MobEntityRenderer<FancyChickenEntity, FancyChickenEntityModel<FancyChickenEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/chicken/fancy.png");

	public FancyChickenEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FancyChickenEntityModel<>(context.getPart(ModEntityModelLayers.FANCY_CHICKEN)), 0.3F);
	}

	public Identifier getTexture(FancyChickenEntity entity) { return TEXTURE; }

	protected float getAnimationProgress(FancyChickenEntity chickenEntity, float f) {
		float g = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
		float h = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.maxWingDeviation);
		return (MathHelper.sin(g) + 1.0F) * h;
	}
}