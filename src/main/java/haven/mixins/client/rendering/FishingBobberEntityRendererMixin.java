package haven.mixins.client.rendering;

import haven.HavenMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class FishingBobberEntityRendererMixin extends EntityRenderer<FishingBobberEntity> {
	protected FishingBobberEntityRendererMixin(EntityRendererFactory.Context ctx) {
		super(ctx);
	}
	private static final Identifier TEXTURE = new Identifier("textures/entity/fishing_hook.png");
	private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);

	private static float percentage(int value, int max) {
		return (float)value / (float)max;
	}

	private static void vertex(VertexConsumer buffer, Matrix4f matrix, Matrix3f normalMatrix, int light, float x, int y, int u, int v) {
		buffer.vertex(matrix, x - 0.5F, (float)y - 0.5F, 0.0F).color(255, 255, 255, 255).texture((float)u, (float)v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
	}

	private static void method_23172(float x, float y, float z, VertexConsumer buffer, MatrixStack.Entry normal, float f, float g) {
		float h = x * f;
		float i = y * (f * f + f) * 0.5F + 0.25F;
		float j = z * f;
		float k = x * g - h;
		float l = y * (g * g + g) * 0.5F + 0.25F - i;
		float m = z * g - j;
		float n = MathHelper.sqrt(k * k + l * l + m * m);
		k /= n;
		l /= n;
		m /= n;
		buffer.vertex(normal.getModel(), h, i, j).color(0, 0, 0, 255).normal(normal.getNormal(), k, l, m).next();
	}

	@Inject(method="render(Lnet/minecraft/entity/projectile/FishingBobberEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
	public void Render(FishingBobberEntity fishingBobberEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		PlayerEntity playerEntity = fishingBobberEntity.getPlayerOwner();
		if (playerEntity != null) {
			matrixStack.push();
			matrixStack.push();
			matrixStack.scale(0.5F, 0.5F, 0.5F);
			matrixStack.multiply(this.dispatcher.getRotation());
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
			MatrixStack.Entry entry = matrixStack.peek();
			Matrix4f matrix4f = entry.getModel();
			Matrix3f matrix3f = entry.getNormal();
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);
			vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
			vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
			vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
			vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);
			matrixStack.pop();
			int j = playerEntity.getMainArm() == Arm.RIGHT ? 1 : -1;
			ItemStack itemStack = playerEntity.getMainHandStack();
			if (!itemStack.isOf(Items.FISHING_ROD) && !itemStack.isOf(HavenMod.GRAPPLING_ROD)) {
				j = -j;
			}

			float h = playerEntity.getHandSwingProgress(g);
			float k = MathHelper.sin(MathHelper.sqrt(h) * 3.1415927F);
			float l = MathHelper.lerp(g, playerEntity.prevBodyYaw, playerEntity.bodyYaw) * 0.017453292F;
			double d = (double)MathHelper.sin(l);
			double e = (double)MathHelper.cos(l);
			double m = (double)j * 0.35D;
			double n = 0.8D;
			double t;
			double u;
			double v;
			float w;
			double x;
			if ((this.dispatcher.gameOptions == null || this.dispatcher.gameOptions.getPerspective().isFirstPerson()) && playerEntity == MinecraftClient.getInstance().player) {
				x = 960.0D / this.dispatcher.gameOptions.fov;
				Vec3d vec3d = this.dispatcher.camera.getProjection().getPosition((float)j * 0.525F, -0.1F);
				vec3d = vec3d.multiply(x);
				vec3d = vec3d.rotateY(k * 0.5F);
				vec3d = vec3d.rotateX(-k * 0.7F);
				t = MathHelper.lerp((double)g, playerEntity.prevX, playerEntity.getX()) + vec3d.x;
				u = MathHelper.lerp((double)g, playerEntity.prevY, playerEntity.getY()) + vec3d.y;
				v = MathHelper.lerp((double)g, playerEntity.prevZ, playerEntity.getZ()) + vec3d.z;
				w = playerEntity.getStandingEyeHeight();
			} else {
				t = MathHelper.lerp((double)g, playerEntity.prevX, playerEntity.getX()) - e * m - d * 0.8D;
				u = playerEntity.prevY + (double)playerEntity.getStandingEyeHeight() + (playerEntity.getY() - playerEntity.prevY) * (double)g - 0.45D;
				v = MathHelper.lerp((double)g, playerEntity.prevZ, playerEntity.getZ()) - d * m + e * 0.8D;
				w = playerEntity.isInSneakingPose() ? -0.1875F : 0.0F;
			}

			x = MathHelper.lerp((double)g, fishingBobberEntity.prevX, fishingBobberEntity.getX());
			double y = MathHelper.lerp((double)g, fishingBobberEntity.prevY, fishingBobberEntity.getY()) + 0.25D;
			double z = MathHelper.lerp((double)g, fishingBobberEntity.prevZ, fishingBobberEntity.getZ());
			float aa = (float)(t - x);
			float ab = (float)(u - y) + w;
			float ac = (float)(v - z);
			VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getLineStrip());
			MatrixStack.Entry entry2 = matrixStack.peek();

			for(int ae = 0; ae <= 16; ++ae) {
				method_23172(aa, ab, ac, vertexConsumer2, entry2, percentage(ae, 16), percentage(ae + 1, 16));
			}

			matrixStack.pop();
			super.render(fishingBobberEntity, f, g, matrixStack, vertexConsumerProvider, i);
			ci.cancel();
		}
	}
}
