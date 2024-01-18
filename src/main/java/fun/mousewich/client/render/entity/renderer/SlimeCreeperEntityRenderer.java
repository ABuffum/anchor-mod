package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.SlimeCreeperEntityModel;
import fun.mousewich.client.render.entity.renderer.feature.SlimeCreeperChargeFeatureRenderer;
import fun.mousewich.client.render.entity.renderer.feature.SlimeCreeperOverlayFeatureRenderer;
import fun.mousewich.entity.hostile.SlimeCreeperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class SlimeCreeperEntityRenderer extends MobEntityRenderer<SlimeCreeperEntity, SlimeCreeperEntityModel<SlimeCreeperEntity>> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/creeper/slime.png");

	public SlimeCreeperEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new SlimeCreeperEntityModel<>(context.getPart(ModEntityModelLayers.SLIME_CREEPER)), 0.5f);
		this.addFeature(new SlimeCreeperOverlayFeatureRenderer(this, context.getModelLoader()));
		this.addFeature(new SlimeCreeperChargeFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	protected void scale(SlimeCreeperEntity creeperEntity, MatrixStack matrixStack, float f) {
		float g = creeperEntity.getClientFuseTime(f);
		float h = 1.0f + MathHelper.sin(g * 100.0f) * g * 0.01f;
		g = MathHelper.clamp(g, 0.0f, 1.0f);
		g *= g;
		g *= g;
		float i = (1.0f + g * 0.4f) * h;
		float j = (1.0f + g * 0.1f) / h;
		matrixStack.scale(i, j, i);
	}

	@Override
	protected float getAnimationCounter(SlimeCreeperEntity creeperEntity, float f) {
		float g = creeperEntity.getClientFuseTime(f);
		if ((int)(g * 10.0f) % 2 == 0) {
			return 0.0f;
		}
		return MathHelper.clamp(g, 0.5f, 1.0f);
	}

	@Override
	public Identifier getTexture(SlimeCreeperEntity creeperEntity) { return TEXTURE; }
}
