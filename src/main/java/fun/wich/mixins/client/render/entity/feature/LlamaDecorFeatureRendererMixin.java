package fun.wich.mixins.client.render.entity.feature;

import fun.wich.ModId;
import fun.wich.entity.ModDataHandlers;
import fun.wich.entity.passive.JollyLlamaEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.LlamaDecorFeatureRenderer;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LlamaDecorFeatureRenderer.class)
public abstract class LlamaDecorFeatureRendererMixin extends FeatureRenderer<LlamaEntity, LlamaEntityModel<LlamaEntity>> {
	@Shadow @Final private LlamaEntityModel<LlamaEntity> model;

	public LlamaDecorFeatureRendererMixin(FeatureRendererContext<LlamaEntity, LlamaEntityModel<LlamaEntity>> context) { super(context); }

	private static final Identifier JOLLY_LLAMA_DECOR = ModId.ID("textures/entity/llama/decor/jolly_llama.png");

	private static final Identifier RAINBOW_DECOR = ModId.ID("textures/entity/llama/decor/rainbow.png");
	private static final Identifier MOSS_DECOR = ModId.ID("textures/entity/llama/decor/moss.png");
	private static final Identifier GLOW_LICHEN_DECOR = ModId.ID("textures/entity/llama/decor/glow_lichen.png");
	private static final Identifier BEIGE_DECOR = ModId.ID("textures/entity/llama/decor/beige.png");
	private static final Identifier BURGUNDY_DECOR = ModId.ID("textures/entity/llama/decor/burgundy.png");
	private static final Identifier MINT_DECOR = ModId.ID("textures/entity/llama/decor/mint.png");
	private static final Identifier LAVENDER_DECOR = ModId.ID("textures/entity/llama/decor/lavender.png");

	@Inject(method="render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/LlamaEntity;FFFFFF)V", at=@At("HEAD"), cancellable=true)
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LlamaEntity llamaEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		Identifier identifier = null;
		if (llamaEntity instanceof JollyLlamaEntity) identifier = JOLLY_LLAMA_DECOR;
		else if (llamaEntity.getCarpetColor() != null || llamaEntity.isTrader()) return;
		int modDyeColor = llamaEntity.getDataTracker().get(ModDataHandlers.MOD_CARPET_COLOR);
		if (identifier == null) {
			if (modDyeColor == 0) identifier = RAINBOW_DECOR;
			else if (modDyeColor == 1) identifier = MOSS_DECOR;
			else if (modDyeColor == 2) identifier = GLOW_LICHEN_DECOR;
			else if (modDyeColor == 3) identifier = BEIGE_DECOR;
			else if (modDyeColor == 4) identifier = BURGUNDY_DECOR;
			else if (modDyeColor == 5) identifier = MINT_DECOR;
			else if (modDyeColor == 6) identifier = LAVENDER_DECOR;
			else return;
		}
		this.getContextModel().copyStateTo(this.model);
		this.model.setAngles(llamaEntity, f, g, j, k, l);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(identifier));
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		ci.cancel();
	}
}
