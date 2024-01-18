package fun.mousewich.client.render.entity.renderer.feature.spider;

import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.spider.SlimeSpiderEntityModel;
import fun.mousewich.entity.hostile.spider.SlimeSpiderEntity;
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
public class SlimeSpiderOverlayFeatureRenderer extends FeatureRenderer<SlimeSpiderEntity, SlimeSpiderEntityModel> {
	private final EntityModel<SlimeSpiderEntity> model;

	public SlimeSpiderOverlayFeatureRenderer(FeatureRendererContext<SlimeSpiderEntity, SlimeSpiderEntityModel> context, EntityModelLoader loader) {
		super(context);
		this.model = new SlimeSpiderEntityModel(loader.getModelPart(ModEntityModelLayers.SLIME_SPIDER_OUTER));
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SlimeSpiderEntity livingEntity, float f, float g, float h, float j, float k, float l) {
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
