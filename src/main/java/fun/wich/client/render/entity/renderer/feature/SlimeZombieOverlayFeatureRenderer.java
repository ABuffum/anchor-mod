package fun.wich.client.render.entity.renderer.feature;

import fun.wich.client.render.entity.model.SlimeZombieEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SlimeZombieOverlayFeatureRenderer<T extends ZombieEntity> extends FeatureRenderer<T, SlimeZombieEntityModel<T>> {
	private final SlimeZombieEntityModel<T> model;
	private final Identifier skin;

	public SlimeZombieOverlayFeatureRenderer(FeatureRendererContext<T, SlimeZombieEntityModel<T>> context, EntityModelLoader loader, EntityModelLayer layer, Identifier skin) {
		super(context);
		this.model = new SlimeZombieEntityModel<>(loader.getModelPart(layer));
		this.skin = skin;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		boolean bl = minecraftClient.hasOutline(entity) && entity.isInvisible();
		if (entity.isInvisible() && !bl) return;
		VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(entity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));
		this.getContextModel().copyStateTo(this.model);
		this.model.animateModel(entity, f, g, h);
		this.model.setAngles(entity, f, g, j, k, l);
		this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
	}
}