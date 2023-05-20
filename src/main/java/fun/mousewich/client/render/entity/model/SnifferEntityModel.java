package fun.mousewich.client.render.entity.model;

import fun.mousewich.client.render.entity.animation.Animation;
import fun.mousewich.client.render.entity.animation.AnimationHelper;
import fun.mousewich.client.render.entity.animation.AnimationState;
import fun.mousewich.client.render.entity.animation.SnifferAnimations;
import fun.mousewich.entity.passive.sniffer.SnifferEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class SnifferEntityModel<T extends SnifferEntity> extends SinglePartEntityModelWithChildTransform<T> {
	private final ModelPart root;
	private final ModelPart bone;
	private final ModelPart body;
	private final ModelPart rightFrontLeg;
	private final ModelPart rightMidLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart leftMidLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart head;
	private final ModelPart leftEar;
	private final ModelPart rightEar;
	private final ModelPart nose;
	private final ModelPart lowerBeak;

	public SnifferEntityModel(ModelPart root) {
		super(0.5f, 24.0f);
		this.root = root.getChild(ModEntityModelPartNames.ROOT);
		this.bone = this.root.getChild(ModEntityModelPartNames.BONE);
		this.body = this.bone.getChild(EntityModelPartNames.BODY);
		this.rightFrontLeg = this.bone.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.rightMidLeg = this.bone.getChild(ModEntityModelPartNames.RIGHT_MID_LEG);
		this.rightHindLeg = this.bone.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftFrontLeg = this.bone.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.leftMidLeg = this.bone.getChild(ModEntityModelPartNames.LEFT_MID_LEG);
		this.leftHindLeg = this.bone.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.head = this.body.getChild(EntityModelPartNames.HEAD);
		this.leftEar = this.head.getChild(EntityModelPartNames.LEFT_EAR);
		this.rightEar = this.head.getChild(EntityModelPartNames.RIGHT_EAR);
		this.nose = this.head.getChild(EntityModelPartNames.NOSE);
		this.lowerBeak = this.head.getChild(ModEntityModelPartNames.LOWER_BEAK);
	}

	private static final ModelTransform rootTransform = ModelTransform.pivot(0.0f, 0.0f, 0.0f);
	private static final ModelTransform boneTransform = ModelTransform.pivot(0.0f, 5.0f, 0.0f);
	private static final ModelTransform bodyTransform = ModelTransform.pivot(0.0f, 0.0f, 0.0f);
	private static final ModelTransform rightFrontLegTransform = ModelTransform.pivot(-7.5f, 10.0f, -15.0f);
	private static final ModelTransform rightMidLegTransform = ModelTransform.pivot(-7.5f, 10.0f, 0.0f);
	private static final ModelTransform rightHindLegTransform = ModelTransform.pivot(-7.5f, 10.0f, 15.0f);
	private static final ModelTransform leftFrontLegTransform = ModelTransform.pivot(7.5f, 10.0f, -15.0f);
	private static final ModelTransform leftMidLegTransform = ModelTransform.pivot(7.5f, 10.0f, 0.0f);
	private static final ModelTransform leftHindLegTransform = ModelTransform.pivot(7.5f, 10.0f, 15.0f);
	private static final ModelTransform headTransform = ModelTransform.pivot(0.0f, 6.5f, -19.5f);
	private static final ModelTransform leftEarTransform = ModelTransform.pivot(6.5f, -7.5f, -4.5f);
	private static final ModelTransform rightEarTransform = ModelTransform.pivot(-6.5f, -7.5f, -4.5f);
	private static final ModelTransform noseTransform = ModelTransform.pivot(0.0f, -4.5f, -11.5f);
	private static final ModelTransform lowerBeakTransform = ModelTransform.pivot(0.0f, 2.5f, -12.5f);
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData ROOT = modelData.getRoot().addChild(ModEntityModelPartNames.ROOT, ModelPartBuilder.create(), rootTransform);
		ModelPartData BONE = ROOT.addChild(ModEntityModelPartNames.BONE, ModelPartBuilder.create(), boneTransform);
		ModelPartData BODY = BONE.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(62, 0).cuboid(-12.5f, -14.0f, -20.0f, 25.0f, 24.0f, 40.0f, new Dilation(0.5f)).uv(62, 68).cuboid(-12.5f, -14.0f, -20.0f, 25.0f, 29.0f, 40.0f, new Dilation(0.0f)).uv(87, 68).cuboid(-12.5f, 12.0f, -20.0f, 25.0f, 0.0f, 40.0f, new Dilation(0.0f)), bodyTransform);
		BONE.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(32, 87).cuboid(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new Dilation(0.0f)), rightFrontLegTransform);
		BONE.addChild(ModEntityModelPartNames.RIGHT_MID_LEG, ModelPartBuilder.create().uv(32, 105).cuboid(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new Dilation(0.0f)), rightMidLegTransform);
		BONE.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(32, 123).cuboid(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new Dilation(0.0f)), rightHindLegTransform);
		BONE.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 87).cuboid(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new Dilation(0.0f)), leftFrontLegTransform);
		BONE.addChild(ModEntityModelPartNames.LEFT_MID_LEG, ModelPartBuilder.create().uv(0, 105).cuboid(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new Dilation(0.0f)), leftMidLegTransform);
		BONE.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 123).cuboid(-3.5f, -1.0f, -4.0f, 7.0f, 10.0f, 8.0f, new Dilation(0.0f)), leftHindLegTransform);
		ModelPartData HEAD = BODY.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(8, 15).cuboid(-6.5f, -7.5f, -11.5f, 13.0f, 18.0f, 11.0f, new Dilation(0.0f)).uv(8, 4).cuboid(-6.5f, 7.5f, -11.5f, 13.0f, 0.0f, 11.0f, new Dilation(0.0f)), headTransform);
		HEAD.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(2, 0).cuboid(0.0f, 0.0f, -3.0f, 1.0f, 19.0f, 7.0f, new Dilation(0.0f)), leftEarTransform);
		HEAD.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(48, 0).cuboid(-1.0f, 0.0f, -3.0f, 1.0f, 19.0f, 7.0f, new Dilation(0.0f)), rightEarTransform);
		HEAD.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(10, 45).cuboid(-6.5f, -2.0f, -9.0f, 13.0f, 2.0f, 9.0f, new Dilation(0.0f)), noseTransform);
		HEAD.addChild(ModEntityModelPartNames.LOWER_BEAK, ModelPartBuilder.create().uv(10, 57).cuboid(-6.5f, -7.0f, -8.0f, 13.0f, 12.0f, 9.0f, new Dilation(0.0f)), lowerBeakTransform);
		return TexturedModelData.of(modelData, 192, 192);
	}
	public ModelTransform getDefaultTransform(ModelPart part) {
		if (part == this.root) return ModelTransform.NONE;
		else if (part == this.bone) return boneTransform;
		else if (part == this.body) return bodyTransform;
		else if (part == this.rightFrontLeg) return rightFrontLegTransform;
		else if (part == this.rightMidLeg) return rightMidLegTransform;
		else if (part == this.rightHindLeg) return rightHindLegTransform;
		else if (part == this.leftFrontLeg) return leftFrontLegTransform;
		else if (part == this.leftMidLeg) return leftMidLegTransform;
		else if (part == this.leftHindLeg) return leftHindLegTransform;
		else if (part == this.head) return headTransform;
		else if (part == this.leftEar) return leftEarTransform;
		else if (part == this.rightEar) return rightEarTransform;
		else if (part == this.nose) return noseTransform;
		else if (part == this.lowerBeak) return lowerBeakTransform;
		return ModelTransform.NONE;
	}
	public void resetTransform(ModelPart part) { part.setTransform(getDefaultTransform(part)); }
	@Override
	public void setAngles(T snifferEntity, float f, float g, float h, float i, float j) {
		this.getPart().traverse().forEach(this::resetTransform);
		float k = Math.min((float)snifferEntity.getVelocity().horizontalLengthSquared() * 9000.0f, 1.0f);
		float l = k * 2.0f;
		this.updateAnimation(snifferEntity.walkingAnimationState, SnifferAnimations.WALKING, h, k);
		this.updateAnimation(snifferEntity.panickingAnimationState, SnifferAnimations.WALKING, h, l);
		this.updateAnimation(snifferEntity.diggingAnimationState, SnifferAnimations.DIGGING, h);
		this.updateAnimation(snifferEntity.seachingAnimationState, SnifferAnimations.SEARCHING, h, k);
		this.updateAnimation(snifferEntity.sniffingAnimationState, SnifferAnimations.SNIFFING, h);
		this.updateAnimation(snifferEntity.risingAnimationState, SnifferAnimations.RISING, h);
		this.updateAnimation(snifferEntity.feelingHappyAnimationState, SnifferAnimations.FEELING_HAPPY, h);
		this.updateAnimation(snifferEntity.scentingAnimationState, SnifferAnimations.SCENTING, h);
	}
	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress) {
		this.updateAnimation(animationState, animation, animationProgress, 1.0f);
	}
	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress, float speedMultiplier) {
		animationState.update(animationProgress, speedMultiplier);
		animationState.run(state -> AnimationHelper.animate(this, animation, state.getTimeRunning(), 1.0f, new Vec3f()));
	}

	@Override
	public ModelPart getPart() { return this.root; }
}