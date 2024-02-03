package fun.wich.mixins.client.render.entity.feature;

import fun.wich.util.dye.ModDyedCollar;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CatCollarFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatCollarFeatureRenderer.class)
public abstract class CatCollarFeatureRendererMixin extends FeatureRenderer<CatEntity, CatEntityModel<CatEntity>> {
	@Shadow @Final private CatEntityModel<CatEntity> model;
	@Shadow @Final private static Identifier SKIN;

	public CatCollarFeatureRendererMixin(FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>> context) { super(context); }

	@Inject(method="render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/CatEntity;FFFFFF)V", at=@At("HEAD"), cancellable=true)
	public void UseModCollarColor(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CatEntity catEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		if (!catEntity.isTamed()) return;
		if (!(catEntity instanceof ModDyedCollar dyed)) return;
		float[] fs = dyed.GetModCollarColor().getColorComponents();
		render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, catEntity, f, g, j, k, l, h, fs[0], fs[1], fs[2]);
		ci.cancel();
	}
}
