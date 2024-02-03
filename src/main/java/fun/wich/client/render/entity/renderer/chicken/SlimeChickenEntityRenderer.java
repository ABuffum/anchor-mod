package fun.wich.client.render.entity.renderer.chicken;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.SlimeChickenEntityModel;
import fun.wich.client.render.entity.renderer.feature.SlimeChickenOverlayFeatureRenderer;
import fun.wich.entity.passive.chicken.SlimeChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SlimeChickenEntityRenderer extends MobEntityRenderer<SlimeChickenEntity, SlimeChickenEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/chicken/slime.png");

	public SlimeChickenEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SlimeChickenEntityModel(context.getPart(ModEntityModelLayers.SLIME_CHICKEN)), 0.3F);
		this.addFeature(new SlimeChickenOverlayFeatureRenderer(this, context.getModelLoader()));
	}

	public Identifier getTexture(SlimeChickenEntity entity) { return TEXTURE; }

	@Override
	protected float getAnimationProgress(SlimeChickenEntity entity, float f) {
		float g = MathHelper.lerp(f, entity.prevFlapProgress, entity.flapProgress);
		float h = MathHelper.lerp(f, entity.prevMaxWingDeviation, entity.maxWingDeviation);
		return (MathHelper.sin(g) + 1.0f) * h;
	}
}