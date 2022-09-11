package haven.rendering.features;

import haven.HavenMod;
import haven.entities.RainbowSheepEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RainbowSheepWoolFeatureRenderer extends FeatureRenderer<RainbowSheepEntity, SheepEntityModel<RainbowSheepEntity>> {
	private static final Identifier TEXTURE = HavenMod.ID("textures/entity/sheep/rainbow_sheep_fur.png");
	private final SheepWoolEntityModel<RainbowSheepEntity> sheepModel;

	public RainbowSheepWoolFeatureRenderer(FeatureRendererContext<RainbowSheepEntity, SheepEntityModel<RainbowSheepEntity>> featureRendererContext, EntityModelLoader entityModelLoader) {
		super(featureRendererContext);
		this.sheepModel = new SheepWoolEntityModel<>(entityModelLoader.getModelPart(EntityModelLayers.SHEEP_FUR));
	}

	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, RainbowSheepEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isSheared() && !entity.isInvisible()) {
			float f = 0.9019608F;
			float f1 = 0.9019608F;
			float f2 = 0.9019608F;
			render(this.getContextModel(), this.sheepModel, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, f, f1, f2);
		}
	}

}
