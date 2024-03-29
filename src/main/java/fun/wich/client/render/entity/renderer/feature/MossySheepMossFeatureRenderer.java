package fun.wich.client.render.entity.renderer.feature;

import fun.wich.ModId;
import fun.wich.entity.passive.sheep.MossySheepEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MossySheepMossFeatureRenderer extends FeatureRenderer<MossySheepEntity, SheepEntityModel<MossySheepEntity>> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/sheep/mossy_sheep_fur.png");
	private final SheepWoolEntityModel<MossySheepEntity> model;
	public MossySheepMossFeatureRenderer(FeatureRendererContext<MossySheepEntity, SheepEntityModel<MossySheepEntity>> featureRendererContext, EntityModelLoader entityModelLoader) {
		super(featureRendererContext);
		this.model = new SheepWoolEntityModel<>(entityModelLoader.getModelPart(EntityModelLayers.SHEEP_FUR));
	}
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, MossySheepEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isSheared() && !entity.isInvisible()) {
			render(this.getContextModel(), this.model, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1, 1, 1);
		}
	}
}
