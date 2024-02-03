package fun.wich.mixins.client.render.entity.feature;

import fun.wich.util.dye.ModDyeColor;
import fun.wich.util.dye.ModDyedSheep;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepWoolFeatureRenderer.class)
public abstract class SheepWoolFeatureRendererMixin extends FeatureRenderer<SheepEntity, SheepEntityModel<SheepEntity>> {
	@Shadow @Final private SheepWoolEntityModel<SheepEntity> model;

	@Shadow @Final private static Identifier SKIN;

	public SheepWoolFeatureRendererMixin(FeatureRendererContext<SheepEntity, SheepEntityModel<SheepEntity>> context) { super(context); }

	@Inject(method="render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V", at=@At("HEAD"), cancellable=true)
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, SheepEntity sheepEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		if (sheepEntity.isSheared()) return;
		if (sheepEntity.isInvisible()) return;
		float s, t, u;
		if (sheepEntity.hasCustomName() && "jeb_".equals(sheepEntity.getName().asString())) {
			int n = sheepEntity.age / 25 + sheepEntity.getId();
			int o = ModDyeColor.ALL_VALUES.length;
			float r = ((float)(sheepEntity.age % 25) + h) / 25.0f;
			float[] fs = ModDyeColor.byId(n % o).getSheepColors();
			float[] gs = ModDyeColor.byId((n + 1) % o).getSheepColors();
			s = fs[0] * (1.0f - r) + gs[0] * r;
			t = fs[1] * (1.0f - r) + gs[1] * r;
			u = fs[2] * (1.0f - r) + gs[2] * r;
		}
		else {
			float[] hs = ((ModDyedSheep)sheepEntity).GetModColor().getSheepColors();
			s = hs[0];
			t = hs[1];
			u = hs[2];
		}
		SheepWoolFeatureRenderer.render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, i, sheepEntity, f, g, j, k, l, h, s, t, u);
		ci.cancel();
	}
}
