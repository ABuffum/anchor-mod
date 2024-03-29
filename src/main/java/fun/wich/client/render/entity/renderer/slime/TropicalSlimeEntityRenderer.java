package fun.wich.client.render.entity.renderer.slime;

import fun.wich.ModId;
import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.TropicalSlimeEntityModel;
import fun.wich.client.render.entity.renderer.feature.TropicalSlimeOverlayFeatureRenderer;
import fun.wich.entity.hostile.slime.TropicalSlimeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TropicalSlimeEntityRenderer extends MobEntityRenderer<TropicalSlimeEntity, TropicalSlimeEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/slime/tropical.png");

	public TropicalSlimeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new TropicalSlimeEntityModel(context.getPart(ModEntityModelLayers.TROPICAL_SLIME)), 0.25f);
		this.addFeature(new TropicalSlimeOverlayFeatureRenderer(this, context.getModelLoader()));
	}

	@Override
	public void render(TropicalSlimeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		// adjust the render tick
		if ((entity.prevTick += tickDelta) >= 10) {
			entity.frame = entity.frame >= 47 ? 0 : entity.frame + 1;
			entity.prevTick = 0;
		}
		this.shadowRadius = 0.25f * (float)entity.getSize();
		for (int i = 0; i < 48; i++) {
			this.model.bodies[i].visible = i == entity.frame;
		}
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
	}

	@Override
	protected void scale(TropicalSlimeEntity entity, MatrixStack matrixStack, float f) {
		matrixStack.scale(0.999f, 0.999f, 0.999f);
		matrixStack.translate(0.0, 0.001f, 0.0);
		float h = entity.getSize();
		float i = MathHelper.lerp(f, entity.lastStretch, entity.stretch) / (h * 0.5f + 1.0f);
		float j = 1.0f / (i + 1.0f);
		matrixStack.scale(j * h, 1.0f / j * h, j * h);
	}

	@Override
	public Identifier getTexture(TropicalSlimeEntity entity) { return TEXTURE; }
}
