package fun.wich.client.render.entity.renderer.feature;

import fun.wich.client.render.entity.model.WardenEntityModel;
import fun.wich.entity.hostile.warden.WardenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Environment(value= EnvType.CLIENT)
public class WardenFeatureRenderer<T extends WardenEntity, M extends WardenEntityModel<T>> extends FeatureRenderer<T, M> {
	private final Identifier texture;
	private final AnimationAngleAdjuster<T> angleAdjuster;
	private final ModelPartVisibility<T, M> modelPartVisibility;

	public WardenFeatureRenderer(FeatureRendererContext<T, M> context, Identifier texture, AnimationAngleAdjuster<T> animationAngleAdjuster, ModelPartVisibility<T, M> modelPartVisibility) {
		super(context);
		this.texture = texture;
		this.angleAdjuster = animationAngleAdjuster;
		this.modelPartVisibility = modelPartVisibility;
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
		if (entity.isInvisible()) return;
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.texture));// ENTITY_TRANSLUCENT_EMISSIVE.apply(this.texture, true));//.getEyes(this.texture));
		this.getContextModel().render(matrixStack, vertexConsumer, 1, LivingEntityRenderer.getOverlay(entity,  0), 1, 1, 1, this.angleAdjuster.apply(entity, h, j));
	}
/*
	private static final BiFunction<Identifier, Boolean, RenderLayer> ENTITY_TRANSLUCENT_EMISSIVE = Util.memoize(
			((texture, affectsOutline) -> {
				RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder()
						.shader(RenderPhase.EYES_SHADER)
						.texture(new RenderPhase.Texture(texture, false, false))
						.transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
						.cull(RenderPhase.DISABLE_CULLING)
						.writeMaskState(RenderPhase.COLOR_MASK)
						.overlay(RenderPhase.ENABLE_OVERLAY_COLOR)
						.build(affectsOutline);
				return new RenderLayer.MultiPhase(
						"entity_translucent_emissive",
						VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
						VertexFormat.DrawMode.QUADS,
						256,
						true,
						true,
						multiPhaseParameters);
			})
	);*/

	private static void renderHidden(ModelPart part, MatrixStack matrices, Set<ModelPart> parts) {
		if (!parts.contains(part)) {
			parts.add(part);
			matrices.push();
			part.rotate(matrices);
			part.traverse().forEach(child -> renderHidden(child, matrices, parts));
			matrices.pop();
		}
	}

	public void renderModel(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		List<ModelPart> list = this.modelPartVisibility.getPartsToDraw(this.getContextModel());
		Set<ModelPart> parts = new HashSet<>();
		this.getContextModel().getPart().traverse().forEach(part -> renderHidden(part, matrices, parts));
		list.forEach(part -> part.render(matrices, vertices, light, overlay, red, green, blue, alpha));
	}

	//private void updateModelPartVisibility() {
	//	List<ModelPart> list = this.modelPartVisibility.getPartsToDraw(this.getContextModel());
	//	this.getContextModel().getPart().traverse().forEach(part -> part.hidden = true);
	//	list.forEach(part -> part.hidden = false);
	//}
	//private void unhideAllModelParts() { this.getContextModel().getPart().traverse().forEach(part -> part.hidden = false); }
	@Environment(value=EnvType.CLIENT)
	public interface AnimationAngleAdjuster<T extends WardenEntity> { float apply(T var1, float var2, float var3); }
	@Environment(value=EnvType.CLIENT)
	public interface ModelPartVisibility<T extends WardenEntity, M extends EntityModel<T>> { List<ModelPart> getPartsToDraw(M var1); }
}