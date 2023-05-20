package fun.mousewich.mixins.client.render.entity;

import fun.mousewich.entity.passive.camel.CamelEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
	public MobEntityRendererMixin(EntityRendererFactory.Context ctx, M model, float shadowRadius) { super(ctx, model, shadowRadius); }
	@Shadow
	private static void renderLeashPiece(VertexConsumer vertexConsumer, Matrix4f positionMatrix, float f, float g, float h, int leashedEntityBlockLight, int holdingEntityBlockLight, int leashedEntitySkyLight, int holdingEntitySkyLight, float i, float j, float k, float l, int pieceIndex, boolean isLeashKnot) { }

	@Inject(method="renderLeash", at = @At("HEAD"), cancellable = true)
	private <E extends Entity> void RenderLeash(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, E holdingEntity, CallbackInfo ci) {
		if (entity instanceof CamelEntity camel) {
			int u;
			matrices.push();
			Vec3d vec3d = holdingEntity.getLerpedPos(tickDelta).add(0.0, holdingEntity.getStandingEyeHeight() * 0.7, 0.0);
			double d = (double)(MathHelper.lerp(tickDelta, entity.prevBodyYaw, entity.bodyYaw) * ((float)Math.PI / 180)) + 1.5707963267948966;
			Vec3d vec3d2 = camel.getLeashOffset(tickDelta);
			double e = Math.cos(d) * vec3d2.z + Math.sin(d) * vec3d2.x;
			double f = Math.sin(d) * vec3d2.z - Math.cos(d) * vec3d2.x;
			double g = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) + e;
			double h = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) + vec3d2.y;
			double i = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) + f;
			matrices.translate(e, vec3d2.y, f);
			float j = (float)(vec3d.x - g);
			float k = (float)(vec3d.y - h);
			float l = (float)(vec3d.z - i);
			VertexConsumer vertexConsumer = provider.getBuffer(RenderLayer.getLeash());
			Matrix4f matrix4f = matrices.peek().getModel();
			float n = MathHelper.fastInverseSqrt(j * j + l * l) * 0.025f / 2.0f;
			float o = l * n;
			float p = j * n;
			BlockPos blockPos = new BlockPos(entity.getCameraPosVec(tickDelta));
			BlockPos blockPos2 = new BlockPos(holdingEntity.getCameraPosVec(tickDelta));
			int q = this.getBlockLight(entity, blockPos);
			int r = ((EntityRendererAccessor<? super E>)this.dispatcher.getRenderer(holdingEntity)).GetBlockLight(holdingEntity, blockPos2);
			int s = entity.world.getLightLevel(LightType.SKY, blockPos);
			int t = entity.world.getLightLevel(LightType.SKY, blockPos2);
			for (u = 0; u <= 24; ++u) renderLeashPiece(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025f, 0.025f, o, p, u, false);
			for (u = 24; u >= 0; --u) renderLeashPiece(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025f, 0.0f, o, p, u, true);
			matrices.pop();
			ci.cancel();
		}
	}
}
