package fun.mousewich.client.render.entity.renderer.feature;

import fun.mousewich.ModId;
import fun.mousewich.entity.passive.SlimeHorseEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SlimeHorseOverlayFeatureRenderer extends FeatureRenderer<SlimeHorseEntity, HorseEntityModel<SlimeHorseEntity>> {
	public static final Identifier TEXTURE = ModId.ID("textures/entity/horse/slime_horse.png");
	private final HorseEntityModel<SlimeHorseEntity> model;

	public SlimeHorseOverlayFeatureRenderer(FeatureRendererContext<SlimeHorseEntity, HorseEntityModel<SlimeHorseEntity>> context, EntityModelLoader loader) {
		super(context);
		this.model = new HorseEntityModel<>(loader.getModelPart(EntityModelLayers.HORSE_ARMOR));
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SlimeHorseEntity livingEntity, float f, float g, float h, float j, float k, float l) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		boolean bl = minecraftClient.hasOutline(livingEntity) && livingEntity.isInvisible();
		if (livingEntity.isInvisible() && !bl) return;
		VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(livingEntity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
		this.getContextModel().copyStateTo(this.model);
		this.model.animateModel(livingEntity, f, g, h);
		this.model.setAngles(livingEntity, f, g, j, k, l);
		this.model.render(matrixStack, vertexConsumer, i, LivingEntityRenderer.getOverlay(livingEntity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public Identifier getTexture(SlimeHorseEntity horseEntity) { return TEXTURE; }
}
