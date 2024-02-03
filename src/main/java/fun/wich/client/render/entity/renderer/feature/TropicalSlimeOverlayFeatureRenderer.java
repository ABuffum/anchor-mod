package fun.wich.client.render.entity.renderer.feature;

import fun.wich.client.render.entity.ModEntityModelLayers;
import fun.wich.client.render.entity.model.TropicalSlimeEntityModel;
import fun.wich.entity.hostile.slime.TropicalSlimeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;

@Environment(value= EnvType.CLIENT)
public class TropicalSlimeOverlayFeatureRenderer extends FeatureRenderer<TropicalSlimeEntity, TropicalSlimeEntityModel> {
	private final EntityModel<TropicalSlimeEntity> model;

	public TropicalSlimeOverlayFeatureRenderer(FeatureRendererContext<TropicalSlimeEntity, TropicalSlimeEntityModel> context, EntityModelLoader loader) {
		super(context);
		this.model = new TropicalSlimeEntityModel(loader.getModelPart(ModEntityModelLayers.TROPICAL_SLIME_OUTER));
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, TropicalSlimeEntity livingEntity, float f, float g, float h, float j, float k, float l) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		boolean bl = minecraftClient.hasOutline(livingEntity) && livingEntity.isInvisible();
		if (livingEntity.isInvisible() && !bl) return;
		VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(livingEntity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
		this.getContextModel().copyStateTo(this.model);
		this.model.animateModel(livingEntity, f, g, h);
		this.model.setAngles(livingEntity, f, g, j, k, l);
		this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(livingEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
	}
}
