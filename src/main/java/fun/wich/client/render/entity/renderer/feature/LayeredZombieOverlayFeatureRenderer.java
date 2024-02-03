package fun.wich.client.render.entity.renderer.feature;

import fun.wich.client.render.entity.model.LayeredZombieEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class LayeredZombieOverlayFeatureRenderer<T extends ZombieEntity> extends FeatureRenderer<T, LayeredZombieEntityModel<T>> {
	private final LayeredZombieEntityModel<T> model;
	private final Identifier skin;

	public LayeredZombieOverlayFeatureRenderer(FeatureRendererContext<T, LayeredZombieEntityModel<T>> context, EntityModelLoader loader, EntityModelLayer layer, Identifier skin) {
		super(context);
		this.model = new LayeredZombieEntityModel<>(loader.getModelPart(layer));
		this.skin = skin;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
		render(this.getContextModel(), this.model, skin, matrixStack, vertexConsumerProvider, i, entity, f, g, j, k, l, h, 1.0f, 1.0f, 1.0f);
	}
}