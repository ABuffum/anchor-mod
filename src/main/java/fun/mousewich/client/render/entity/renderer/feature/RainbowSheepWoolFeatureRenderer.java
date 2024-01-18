package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.ModId;
import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.client.render.entity.model.RainbowSheepEntityModel;
import fun.mousewich.client.render.entity.model.RainbowSheepWoolEntityModel;
import fun.mousewich.entity.passive.sheep.RainbowSheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class RainbowSheepWoolFeatureRenderer extends FeatureRenderer<RainbowSheepEntity, RainbowSheepEntityModel> {
	private static final Identifier TEXTURE = ModId.ID("textures/entity/sheep/rainbow_sheep_fur.png");
	private final RainbowSheepWoolEntityModel model;
	public RainbowSheepWoolFeatureRenderer(FeatureRendererContext<RainbowSheepEntity, RainbowSheepEntityModel> context, EntityModelLoader loader) {
		super(context);
		this.model = new RainbowSheepWoolEntityModel(loader.getModelPart(ModEntityModelLayers.RAINBOW_SHEEP_FUR));
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, RainbowSheepEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isSheared() && !entity.isInvisible()) {
			render(this.getContextModel(), this.model, TEXTURE, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1, 1, 1);
		}
	}
}
