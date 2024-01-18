package fun.mousewich.client.render.entity.renderer;

import fun.mousewich.client.render.entity.ModEntityModelLayers;
import fun.mousewich.entity.vehicle.DispenserMinecartEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class DispenserMinecartEntityRenderer extends EntityRenderer<DispenserMinecartEntity> {
	private static final Identifier TEXTURE = new Identifier("textures/entity/minecart.png");
	protected final EntityModel<DispenserMinecartEntity> model;
	public DispenserMinecartEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.shadowRadius = 0.7f;
		this.model = new MinecartEntityModel<>(context.getPart(ModEntityModelLayers.DISPENSER_MINECART));
	}
	@Override
	public Identifier getTexture(DispenserMinecartEntity minecart) { return TEXTURE; }
	@Override
	public void render(DispenserMinecartEntity minecart, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(minecart, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.push();
		long l = (long)(minecart).getId() * 493286711L;
		l = l * l * 4392167121L + l * 98761L;
		float h = (((float)(l >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float j = (((float)(l >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float k = (((float)(l >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		matrixStack.translate(h, j, k);
		double d = MathHelper.lerp(g, minecart.lastRenderX, minecart.getX());
		double e = MathHelper.lerp(g, minecart.lastRenderY, minecart.getY());
		double m = MathHelper.lerp(g, minecart.lastRenderZ, minecart.getZ());
		double n = 0.3f;
		Vec3d vec3d = minecart.snapPositionToRail(d, e, m);
		float adjustedF = f;
		float o = MathHelper.lerp(g, minecart.prevPitch, minecart.getPitch());
		if (vec3d != null) {
			Vec3d vec3d2 = minecart.snapPositionToRailWithOffset(d, e, m, 0.3f);
			Vec3d vec3d3 = minecart.snapPositionToRailWithOffset(d, e, m, -0.3f);
			if (vec3d2 == null) vec3d2 = vec3d;
			if (vec3d3 == null) vec3d3 = vec3d;
			matrixStack.translate(vec3d.x - d, (vec3d2.y + vec3d3.y) / 2.0 - e, vec3d.z - m);
			Vec3d vec3d4 = vec3d3.add(-vec3d2.x, -vec3d2.y, -vec3d2.z);
			if (vec3d4.length() != 0.0) {
				vec3d4 = vec3d4.normalize();
				adjustedF = (float)(Math.atan2(vec3d4.z, vec3d4.x) * 180.0 / Math.PI);
				o = (float)(Math.atan(vec3d4.y) * 73.0);
			}
		}
		matrixStack.translate(0.0, 0.375, 0.0);

		float delta = MathHelper.abs(minecart.getYaw() - adjustedF) % 360;
		if (delta > 90 && delta < 270) adjustedF -= 180;

		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - adjustedF));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-o));
		float p = (float) minecart.getDamageWobbleTicks() - g;
		float q = minecart.getDamageWobbleStrength() - g;
		if (q < 0.0f) q = 0.0f;
		if (p > 0.0f) {
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(p) * p * q / 10.0f * (float) minecart.getDamageWobbleSide()));
		}
		//
		int r = minecart.getBlockOffset();
		BlockState blockState = minecart.getContainedBlockForRender();
		if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
			matrixStack.push();
			matrixStack.scale(0.75f, 0.75f, 0.75f);
			matrixStack.translate(-0.5, (float)(r - 8) / 16.0f, 0.5);
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
			this.renderBlock(minecart, g, blockState, matrixStack, vertexConsumerProvider, i);
			matrixStack.pop();
		}
		//
		matrixStack.scale(-1.0f, -1.0f, 1.0f);
		this.model.setAngles(minecart, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(minecart)));
		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.pop();
	}

	protected void renderBlock(DispenserMinecartEntity minecart, float delta, BlockState blockState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
		MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, light, OverlayTexture.DEFAULT_UV);
	}
}
