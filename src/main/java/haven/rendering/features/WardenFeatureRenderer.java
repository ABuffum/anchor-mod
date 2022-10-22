package haven.rendering.features;

import haven.entities.hostile.warden.WardenEntity;
import haven.rendering.models.WardenEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public class WardenFeatureRenderer<T extends WardenEntity, M extends WardenEntityModel<T>> extends FeatureRenderer<T, M> {
	private final Identifier texture;
	private final AnimationAngleAdjuster<T> animationAngleAdjuster;
	private final ModelPartVisibility<T, M> modelPartVisibility;

	public WardenFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture, AnimationAngleAdjuster<T> animationAngleAdjuster, ModelPartVisibility<T, M> modelPartVisibility) {
		super(context);
		this.texture = texture;
		this.animationAngleAdjuster = animationAngleAdjuster;
		this.modelPartVisibility = modelPartVisibility;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T wardenEntity, float f, float g, float h, float j, float k, float l) {
		if (wardenEntity.isInvisible()) return;
		this.updateModelPartVisibility();
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.texture, true)); //getEntityTranslucentEmissive
		this.getContextModel().render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(wardenEntity, 0.0f), 1.0f, 1.0f, 1.0f, this.animationAngleAdjuster.apply(wardenEntity, h, j));
		this.unhideAllModelParts();
	}

	private void updateModelPartVisibility() {
		List<ModelPart> list = this.modelPartVisibility.getPartsToDraw(this.getContextModel());
		//TODO: Hidden Model Parts
	//	this.getContextModel().getPart().traverse().forEach(part -> part.hidden = true);
	//	list.forEach(part -> part.hidden = false);
	}

	private void unhideAllModelParts() {
		//this.getContextModel().getPart().traverse().forEach(part -> part.hidden = false);
	}

	@Environment(value=EnvType.CLIENT)
	public static interface AnimationAngleAdjuster<T extends WardenEntity> {
		public float apply(T var1, float var2, float var3);
	}

	@Environment(value=EnvType.CLIENT)
	public static interface ModelPartVisibility<T extends WardenEntity, M extends EntityModel<T>> {
		public List<ModelPart> getPartsToDraw(M var1);
	}
}
