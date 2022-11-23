package haven.rendering.features.sheep;

import haven.ModBase;
import haven.entities.passive.sheep.MossySheepEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MossySheepWoolFeatureRenderer extends FeatureRenderer<MossySheepEntity, SheepEntityModel<MossySheepEntity>> {
	private static final Identifier TEXTURE = ModBase.ID("textures/entity/sheep/mossy_sheep_fur.png");
	private final SheepWoolEntityModel<MossySheepEntity> sheepModel;

	public MossySheepWoolFeatureRenderer(FeatureRendererContext<MossySheepEntity, SheepEntityModel<MossySheepEntity>> featureRendererContext, EntityModelLoader entityModelLoader) {
		super(featureRendererContext);
		this.sheepModel = new SheepWoolEntityModel<>(entityModelLoader.getModelPart(EntityModelLayers.SHEEP_FUR));
	}

	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, MossySheepEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isSheared() && !entity.isInvisible()) {
			float f = 0.9019608F;
			float f1 = 0.9019608F;
			float f2 = 0.9019608F;
			render(this.getContextModel(), this.sheepModel, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, f, f1, f2);
		}
	}

}
