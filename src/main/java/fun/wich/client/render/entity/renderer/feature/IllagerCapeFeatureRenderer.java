package fun.wich.client.render.entity.renderer.feature;

import fun.wich.client.render.entity.model.CapedIllagerEntityModel;
import fun.wich.entity.hostile.illager.CapedSpellcastingIllagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class IllagerCapeFeatureRenderer <T extends CapedSpellcastingIllagerEntity> extends FeatureRenderer<T, IllagerEntityModel<T>> {
	public IllagerCapeFeatureRenderer(FeatureRendererContext<T, IllagerEntityModel<T>> featureRendererContext) { super(featureRendererContext); }

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		if (entity.isInvisible() && !(minecraftClient.hasOutline(entity) && entity.isInvisible())) return;
		boolean bl = minecraftClient.hasOutline(entity) && entity.isInvisible();
		if (entity.isInvisible() && !bl) return;
		VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(entity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(this.getTexture(entity)));
		matrixStack.push();
		matrixStack.translate(0, 0, 0.25);
		double d = MathHelper.lerp(h, entity.prevCapeX, entity.capeX) - MathHelper.lerp(h, entity.prevX, entity.getX());
		double e = MathHelper.lerp(h, entity.prevCapeY, entity.capeY) - MathHelper.lerp(h, entity.prevY, entity.getY());
		double m = MathHelper.lerp(h, entity.prevCapeZ, entity.capeZ) - MathHelper.lerp(h, entity.prevZ, entity.getZ());
		float n = entity.prevBodyYaw + (entity.bodyYaw - entity.prevBodyYaw);
		double o = MathHelper.sin(n * ((float)Math.PI / 180));
		double p = -MathHelper.cos(n * ((float)Math.PI / 180));
		float q = MathHelper.clamp((float)e * 10, -6, 32);
		float r = MathHelper.clamp((float)(d * o + m * p) * 100, 0, 150);
		float s = MathHelper.clamp((float)(d * p - m * o) * 100, -20, 20);
		if (r < 0) r = 0;
		float t = MathHelper.lerp(h, entity.prevStrideDistance, entity.strideDistance);
		q += MathHelper.sin(MathHelper.lerp(h, entity.prevHorizontalSpeed, entity.horizontalSpeed) * 6) * 32 * t;
		if (entity.isInSneakingPose()) q += 25;
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(6 + r / 2 + q));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(s / 2));
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180 - s / 2));
		CapedIllagerEntityModel<?> model = ((CapedIllagerEntityModel<?>)this.getContextModel());
		model.cape.visible = true;
		model.cape.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
		model.cape.visible = false;
		matrixStack.pop();
	}
}