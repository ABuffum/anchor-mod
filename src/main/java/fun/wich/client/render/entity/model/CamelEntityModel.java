package fun.wich.client.render.entity.model;

import fun.wich.client.render.entity.animation.Animation;
import fun.wich.client.render.entity.animation.AnimationHelper;
import fun.wich.client.render.entity.animation.AnimationState;
import fun.wich.client.render.entity.animation.CamelAnimations;
import fun.wich.entity.passive.camel.CamelEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class CamelEntityModel<T extends CamelEntity> extends SinglePartEntityModel<T> {
	private static final String SADDLE = "saddle";
	private static final String BRIDLE = "bridle";
	private static final String REINS = "reins";
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart hump;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart saddle;
	private final ModelPart bridle;
	private final ModelPart reins;

	public CamelEntityModel(ModelPart root) {
		this.root = root;
		this.body = this.root.getChild(EntityModelPartNames.BODY);
		this.hump = this.body.getChild(ModEntityModelPartNames.HUMP);
		this.tail = this.body.getChild(EntityModelPartNames.TAIL);
		this.head = this.body.getChild(EntityModelPartNames.HEAD);
		this.leftEar = this.head.getChild(EntityModelPartNames.LEFT_EAR);
		this.rightEar = this.head.getChild(EntityModelPartNames.RIGHT_EAR);
		this.leftHindLeg = this.root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.rightHindLeg = this.root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftFrontLeg = this.root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.rightFrontLeg = this.root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.saddle = this.body.getChild(SADDLE);
		this.bridle = this.head.getChild(BRIDLE);
		this.reins = this.head.getChild(REINS);
	}

	private static final ModelTransform bodyTransform = ModelTransform.pivot(0.0f, 4.0f, 9.5f);
	private static final ModelTransform humpTransform = ModelTransform.pivot(0.0f, -12.0f, -10.0f);
	private static final ModelTransform tailTransform = ModelTransform.pivot(0.0f, -9.0f, 3.5f);
	private static final ModelTransform headTransform = ModelTransform.pivot(0.0f, -3.0f, -19.5f);
	private static final ModelTransform leftEarTransform = ModelTransform.pivot(3.0f, -21.0f, -9.5f);
	private static final ModelTransform rightEarTransform = ModelTransform.pivot(-3.0f, -21.0f, -9.5f);
	private static final ModelTransform leftHindLegTransform = ModelTransform.pivot(4.9f, 1.0f, 9.5f);
	private static final ModelTransform rightHindLegTransform = ModelTransform.pivot(-4.9f, 1.0f, 9.5f);
	private static final ModelTransform leftFrontLegTransform = ModelTransform.pivot(4.9f, 1.0f, -10.5f);
	private static final ModelTransform rightFrontLegTransform = ModelTransform.pivot(-4.9f, 1.0f, -10.5f);
	private static final ModelTransform saddleTransform = ModelTransform.pivot(0.0f, 0.0f, 0.0f);
	private static final ModelTransform reinsTransform = ModelTransform.pivot(0.0f, 0.0f, 0.0f);
	private static final ModelTransform bridleTransform = ModelTransform.pivot(0.0f, 0.0f, 0.0f);

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData ROOT = modelData.getRoot();
		Dilation dilation = new Dilation(0.1f);
		ModelPartData BODY = ROOT.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 25).cuboid(-7.5f, -12.0f, -23.5f, 15.0f, 12.0f, 27.0f), bodyTransform);
		BODY.addChild(ModEntityModelPartNames.HUMP, ModelPartBuilder.create().uv(74, 0).cuboid(-4.5f, -5.0f, -5.5f, 9.0f, 5.0f, 11.0f), humpTransform);
		BODY.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(122, 0).cuboid(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 0.0f), tailTransform);
		ModelPartData HEAD = BODY.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(60, 24).cuboid(-3.5f, -7.0f, -15.0f, 7.0f, 8.0f, 19.0f).uv(21, 0).cuboid(-3.5f, -21.0f, -15.0f, 7.0f, 14.0f, 7.0f).uv(50, 0).cuboid(-2.5f, -21.0f, -21.0f, 5.0f, 5.0f, 6.0f), headTransform);
		HEAD.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(45, 0).cuboid(-0.5f, 0.5f, -1.0f, 3.0f, 1.0f, 2.0f), leftEarTransform);
		HEAD.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(67, 0).cuboid(-2.5f, 0.5f, -1.0f, 3.0f, 1.0f, 2.0f), rightEarTransform);
		ROOT.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(58, 16).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), leftHindLegTransform);
		ROOT.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(94, 16).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), rightHindLegTransform);
		ROOT.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), leftFrontLegTransform);
		ROOT.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 26).cuboid(-2.5f, 2.0f, -2.5f, 5.0f, 21.0f, 5.0f), rightFrontLegTransform);
		BODY.addChild(SADDLE, ModelPartBuilder.create().uv(74, 64).cuboid(-4.5f, -17.0f, -15.5f, 9.0f, 5.0f, 11.0f, dilation).uv(92, 114).cuboid(-3.5f, -20.0f, -15.5f, 7.0f, 3.0f, 11.0f, dilation).uv(0, 89).cuboid(-7.5f, -12.0f, -23.5f, 15.0f, 12.0f, 27.0f, dilation), saddleTransform);
		HEAD.addChild(REINS, ModelPartBuilder.create().uv(98, 42).cuboid(3.51f, -18.0f, -17.0f, 0.0f, 7.0f, 15.0f).uv(84, 57).cuboid(-3.5f, -18.0f, -2.0f, 7.0f, 7.0f, 0.0f).uv(98, 42).cuboid(-3.51f, -18.0f, -17.0f, 0.0f, 7.0f, 15.0f), reinsTransform);
		HEAD.addChild(BRIDLE, ModelPartBuilder.create().uv(60, 87).cuboid(-3.5f, -7.0f, -15.0f, 7.0f, 8.0f, 19.0f, dilation).uv(21, 64).cuboid(-3.5f, -21.0f, -15.0f, 7.0f, 14.0f, 7.0f, dilation).uv(50, 64).cuboid(-2.5f, -21.0f, -21.0f, 5.0f, 5.0f, 6.0f, dilation).uv(74, 70).cuboid(2.5f, -19.0f, -18.0f, 1.0f, 2.0f, 2.0f).uv(74, 70).mirrored().cuboid(-3.5f, -19.0f, -18.0f, 1.0f, 2.0f, 2.0f), bridleTransform);
		return TexturedModelData.of(modelData, 128, 128);
	}

	public ModelTransform getDefaultTransform(ModelPart part) {
		if (part == this.root) return ModelTransform.NONE;
		else if (part == this.body) return bodyTransform;
		else if (part == this.hump) return humpTransform;
		else if (part == this.tail) return tailTransform;
		else if (part == this.head) return headTransform;
		else if (part == this.leftEar) return leftEarTransform;
		else if (part == this.rightEar) return rightEarTransform;
		else if (part == this.leftHindLeg) return leftHindLegTransform;
		else if (part == this.rightHindLeg) return rightHindLegTransform;
		else if (part == this.leftFrontLeg) return leftFrontLegTransform;
		else if (part == this.rightFrontLeg) return rightFrontLegTransform;
		else if (part == this.saddle) return saddleTransform;
		else if (part == this.bridle) return bridleTransform;
		else if (part == this.reins) return reinsTransform;
		return ModelTransform.NONE;
	}
	public void resetTransform(ModelPart part) { part.setTransform(getDefaultTransform(part)); }
	@Override
	public void setAngles(T camelEntity, float f, float g, float h, float i, float j) {
		this.getPart().traverse().forEach(this::resetTransform);
		this.setHeadAngles(camelEntity, i, j, h);
		this.updateVisibleParts(camelEntity);
		float k = (float) camelEntity.getVelocity().horizontalLengthSquared();
		float l = MathHelper.clamp(k * 400.0f, 0.3f, 2.0f);
		this.updateAnimation(camelEntity.walkingAnimationState, CamelAnimations.WALKING, h, l);
		this.updateAnimation(camelEntity.sittingTransitionAnimationState, CamelAnimations.SITTING_TRANSITION, h);
		this.updateAnimation(camelEntity.sittingAnimationState, CamelAnimations.SITTING, h);
		this.updateAnimation(camelEntity.standingTransitionAnimationState, CamelAnimations.STANDING_TRANSITION, h);
		this.updateAnimation(camelEntity.idlingAnimationState, CamelAnimations.IDLING, h);
		this.updateAnimation(camelEntity.dashingAnimationState, CamelAnimations.DASHING, h);
	}
	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress) {
		this.updateAnimation(animationState, animation, animationProgress, 1.0f);
	}
	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress, float speedMultiplier) {
		animationState.update(animationProgress, speedMultiplier);
		animationState.run(state -> AnimationHelper.animate(this, animation, state.getTimeRunning(), 1.0f, new Vec3f()));
	}

	private void setHeadAngles(T entity, float headYaw, float headPitch, float animationProgress) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
		if (entity.getJumpCooldown() > 0) {
			float f = animationProgress - (float) entity.age;
			float g = 45.0f * ((float) entity.getJumpCooldown() - f) / 55.0f;
			headPitch = MathHelper.clamp(headPitch + g, -25.0f, 70.0f);
		}
		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

	private void updateVisibleParts(T camel) {
		boolean bl = camel.isSaddled();
		this.saddle.visible = this.bridle.visible = bl;
		this.reins.visible = bl && camel.hasPassengers();
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		if (this.child) {
			matrices.push();
			matrices.scale(0.45454544f, 0.41322312f, 0.45454544f);
			matrices.translate(0.0f, 2.0625f, 0.0f);
			this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
			matrices.pop();
		}
		else this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
	@Override
	public ModelPart getPart() { return this.root; }
}
