package fun.mousewich.client.render.block.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import fun.mousewich.block.ragdoll.RagdollBlockEntity;
import fun.mousewich.block.ragdoll.RagdollPose;
import fun.mousewich.client.render.entity.model.ModEntityModelPartNames;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Environment(value= EnvType.CLIENT)
public class RagdollBlockEntityRenderer implements BlockEntityRenderer<RagdollBlockEntity> {
	private static final Identifier DEFAULT_SKIN = DefaultSkinHelper.getTexture();

	private final ModelPart root;
	
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart jacket;

	private final ModelPart rightArm;
	private final ModelPart rightSleeve;
	private final ModelPart leftArm;
	private final ModelPart leftSleeve;

	private final ModelPart rightArm_slim;
	private final ModelPart rightSleeve_slim;
	private final ModelPart leftArm_slim;
	private final ModelPart leftSleeve_slim;

	private final ModelPart rightLeg;
	private final ModelPart rightPants;
	private final ModelPart leftLeg;
	private final ModelPart leftPants;

	private final ModelPart error;

	public static SkullBlockEntityModel getSkullModel(EntityModelLoader modelLoader) { return new SkullEntityModel(modelLoader.getModelPart(EntityModelLayers.PLAYER_HEAD)); }

	private static ModelPart createPart(List<ModelCuboidData> cuboidData, ModelTransform rotationData) {
		List<ModelPart.Cuboid> list = cuboidData.stream()
				.map(modelCuboidData -> modelCuboidData.createCuboid(64, 64))
				.collect(ImmutableList.toImmutableList());
		ModelPart part = new ModelPart(list, Collections.emptyMap());
		part.setTransform(rotationData);
		return part;
	}
	public RagdollBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		//Head
		this.head = createPart(ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, Dilation.NONE).build(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		this.hat = createPart(ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		//Torso
		this.body = createPart(ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, Dilation.NONE).build(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		this.jacket = createPart(ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		//Arms
		this.rightArm = createPart(ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation.NONE).build(), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
		this.rightSleeve = createPart(ModelPartBuilder.create().uv(40, 32).cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(-5.0f, 2.0f, 0.0f));

		this.rightArm_slim = createPart(ModelPartBuilder.create().uv(40, 16).cuboid(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE).build(), ModelTransform.pivot(-5.0f, 2.5f, 0.0f));
		this.rightSleeve_slim = createPart(ModelPartBuilder.create().uv(40, 32).cuboid(-2.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(-5.0f, 2.5f, 0.0f));

		this.leftArm = createPart(ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE).build(), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
		this.leftSleeve = createPart(ModelPartBuilder.create().uv(48, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(5.0f, 2.0f, 0.0f));

		this.leftArm_slim = createPart(ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE).build(), ModelTransform.pivot(5.0f, 2.5f, 0.0f));
		this.leftSleeve_slim = createPart(ModelPartBuilder.create().uv(48, 48).cuboid(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(5.0f, 2.5f, 0.0f));
		//Legs
		this.rightLeg = createPart(ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation.NONE).build(), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		this.rightPants = createPart(ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
		this.leftLeg = createPart(ModelPartBuilder.create().uv(16, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation.NONE).build(), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		this.leftPants = createPart(ModelPartBuilder.create().uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, Dilation.NONE.add(0.25f)).build(), ModelTransform.pivot(1.9F, 12.0F, 0.0F));
		//Error
		this.error = createPart(ModelPartBuilder.create().uv(0, 0).cuboid(0, 0, 0, 16, 16, 16, Dilation.NONE).build(), ModelTransform.NONE);
		//Root
		this.root = new ModelPart(
				List.of(),
				Map.ofEntries(
						Map.entry(EntityModelPartNames.HEAD, this.head),
						Map.entry(EntityModelPartNames.HAT, this.hat),

						Map.entry(EntityModelPartNames.BODY, this.body),
						Map.entry(EntityModelPartNames.JACKET, this.jacket),

						Map.entry(EntityModelPartNames.RIGHT_ARM, this.rightArm),
						Map.entry(ModEntityModelPartNames.RIGHT_SLEEVE, this.rightSleeve),
						Map.entry(ModEntityModelPartNames.RIGHT_ARM_SLIM, this.rightArm_slim),
						Map.entry(ModEntityModelPartNames.RIGHT_SLEEVE_SLIM, this.rightSleeve_slim),

						Map.entry(EntityModelPartNames.LEFT_ARM, this.leftArm),
						Map.entry(ModEntityModelPartNames.LEFT_SLEEVE, this.leftSleeve),
						Map.entry(ModEntityModelPartNames.LEFT_ARM_SLIM, this.leftArm_slim),
						Map.entry(ModEntityModelPartNames.LEFT_SLEEVE_SLIM, this.leftSleeve_slim),

						Map.entry(EntityModelPartNames.RIGHT_LEG, this.rightLeg),
						Map.entry(ModEntityModelPartNames.RIGHT_PANTS, this.rightPants),
						Map.entry(EntityModelPartNames.LEFT_LEG, this.leftLeg),
						Map.entry(ModEntityModelPartNames.LEFT_PANTS, this.leftPants),

						Map.entry(ModEntityModelPartNames.ERROR, this.error)
				)
		);
	}

	private static final float RAD = 180 / (float)Math.PI;
	private static final float DEGREE = (float)Math.PI / 180;

	private static final double POS_INCREMENT = 0.06666666666;//0.0625;//0.05859375;

	@Override
	public void render(RagdollBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		// adjust the render tick
		float tick = (entity.prevTick += tickDelta) / 8;

		GameProfile profile = entity.getOwner();
		RenderLayer renderLayer = getRenderLayer(profile);
		matrices.push();
		//Move into place
		matrices.translate(0.5, 1.4375, 0.5);
		//Scale to size
		matrices.scale(-0.9375f, -0.9375f, 0.9375f);
		matrices.translate(entity.getRagdollX() * POS_INCREMENT, entity.getRagdollY() * POS_INCREMENT, entity.getRagdollZ() * POS_INCREMENT);
		matrices.multiply(Quaternion.method_35823(new Vec3f(entity.getRagdollPitch(), entity.getRagdollYaw(), entity.getRagdollRoll())));

		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);

		RagdollPose pose = entity.getRagdollPose();


		//Reset Angles
		this.head.setAngles(0, 0, 0);
		this.body.setAngles(0, 0, 0);
		this.rightArm.setAngles(0, 0, 0);
		this.leftArm.setAngles(0, 0, 0);
		this.rightLeg.setAngles(0, 0, 0);
		this.leftLeg.setAngles(0, 0, 0);
		if (pose == RagdollPose.WALKING) {
			float sin = entity.isPowered() ? (float)Math.sin(tick) : 1;
			this.rightArm.setAngles(sin * 30 * DEGREE, 0, 0);
			this.leftArm.setAngles(-sin * 30 * DEGREE, 0, 0);
			this.rightLeg.setAngles(-sin * 30 * DEGREE, 0, 0);
			this.leftLeg.setAngles(sin * 30 * DEGREE, 0, 0);
		}
		else if (pose == RagdollPose.T_POSE) {
			this.rightArm.setAngles(0, 0, 90 * DEGREE);
			this.leftArm.setAngles(0, 0, -90 * DEGREE);
		}
		if (entity.renderHead()) {
			this.head.render(matrices, vertexConsumer, light, overlay);
			this.hat.copyTransform(this.head);
			this.hat.render(matrices, vertexConsumer, light, overlay);
		}
		if (entity.renderBody()) {
			this.body.render(matrices, vertexConsumer, light, overlay);
			this.jacket.copyTransform(this.body);
			this.jacket.render(matrices, vertexConsumer, light, overlay);
		}
		if (entity.renderLeftArm()) {
			this.leftSleeve.copyTransform(this.leftArm);
			if (entity.isSlim()) {
				this.leftArm_slim.copyTransform(this.leftArm);
				this.leftArm_slim.render(matrices, vertexConsumer, light, overlay);
				this.leftSleeve_slim.copyTransform(this.leftArm_slim);
				this.leftSleeve_slim.render(matrices, vertexConsumer, light, overlay);
			}
			else {
				this.leftArm.render(matrices, vertexConsumer, light, overlay);
				this.leftSleeve.render(matrices, vertexConsumer, light, overlay);
			}
		}
		if (entity.renderRightArm()) {
			this.rightSleeve.copyTransform(this.rightArm);
			if (entity.isSlim()) {
				this.rightArm_slim.copyTransform(this.rightArm);
				this.rightArm_slim.render(matrices, vertexConsumer, light, overlay);
				this.rightSleeve_slim.copyTransform(this.rightArm_slim);
				this.rightSleeve_slim.render(matrices, vertexConsumer, light, overlay);
			}
			else {
				this.rightArm.render(matrices, vertexConsumer, light, overlay);
				this.rightSleeve.render(matrices, vertexConsumer, light, overlay);
			}
		}
		if (entity.renderLeftLeg()) {
			this.leftLeg.render(matrices, vertexConsumer, light, overlay);
			this.leftPants.copyTransform(this.leftLeg);
			this.leftPants.render(matrices, vertexConsumer, light, overlay);
		}
		if (entity.renderRightLeg()) {
			this.rightLeg.render(matrices, vertexConsumer, light, overlay);
			this.rightPants.copyTransform(this.rightLeg);
			this.rightPants.render(matrices, vertexConsumer, light, overlay);
		}
		if (!entity.renderAny()) this.error.render(matrices, vertexConsumer, light, overlay);
		matrices.pop();
	}

	public static void renderSkull(float yaw, float animationProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SkullBlockEntityModel model, RenderLayer renderLayer) {
		matrices.push();
		matrices.translate(0.5, 0.0, 0.5);
		matrices.scale(-1.0f, -1.0f, 1.0f);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
		model.setHeadRotation(animationProgress, yaw, 0.0f);
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		matrices.pop();
	}

	public static RenderLayer getRenderLayer(GameProfile profile) {
		if (profile == null) return RenderLayer.getEntityCutoutNoCullZOffset(DEFAULT_SKIN, false);
		MinecraftClient minecraftClient = MinecraftClient.getInstance();
		Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraftClient.getSkinProvider().getTextures(profile);
		if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
			return RenderLayer.getEntityTranslucent(minecraftClient.getSkinProvider().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN), false);
		}
		return RenderLayer.getEntityCutoutNoCull(DefaultSkinHelper.getTexture(PlayerEntity.getUuidFromProfile(profile)), false);
	}
}
