package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.entity.variants.DrownedVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.DrownedOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.DrownedEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedOverlayFeatureRenderer.class)
public abstract class DrownedOverlayFeatureRendererMixin<T extends DrownedEntity> extends FeatureRenderer<T, DrownedEntityModel<T>> {
	@Shadow @Final private DrownedEntityModel<T> model;
	public DrownedOverlayFeatureRendererMixin(FeatureRendererContext<T, DrownedEntityModel<T>> context) { super(context); }
	@Inject(at=@At("HEAD"), cancellable=true, method="render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/mob/DrownedEntity;FFFFFF)V")
	private void RenderVariantTexture(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T drownedEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		DrownedVariant variant = DrownedVariant.get(drownedEntity);
		if (variant != DrownedVariant.DEFAULT) {
			DrownedOverlayFeatureRenderer.render(this.getContextModel(), this.model, variant.outer, matrixStack, vertexConsumerProvider, i, drownedEntity, f, g, j, k, l, h, 1.0f, 1.0f, 1.0f);
			ci.cancel();
		}
	}
}
