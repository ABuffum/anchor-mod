package haven.rendering.models;

import com.google.common.collect.ImmutableList;
import haven.entities.AnimationState;
import haven.entities.hostile.warden.WardenEntity;
import haven.rendering.animations.Animation;
import haven.rendering.animations.AnimationHelper;
import haven.rendering.animations.WardenAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WardenEntityModel<T extends WardenEntity> extends SinglePartEntityModel<T> {
	private static final float field_38324 = 13.0f;
	private static final float field_38325 = 1.0f;
	private final ModelPart root;
	protected final ModelPart bone;
	protected final ModelPart body;
	protected final ModelPart head;
	protected final ModelPart rightTendril;
	protected final ModelPart leftTendril;
	protected final ModelPart leftLeg;
	protected final ModelPart leftArm;
	protected final ModelPart leftRibcage;
	protected final ModelPart rightArm;
	protected final ModelPart rightLeg;
	protected final ModelPart rightRibcage;
	private final List<ModelPart> tendrils;
	private final List<ModelPart> justBody;
	private final List<ModelPart> headAndLimbs;
	private final List<ModelPart> bodyHeadAndLimbs;

	private Map<ModelPart, ModelTransform> defaultTransforms;
	/**
	 * The key of a right tendril model part, whose value is {@value}.
	 */
	public static final String RIGHT_TENDRIL = "right_tendril";
	/**
	 * The key of a left tendril model part, whose value is {@value}.
	 */
	public static final String LEFT_TENDRIL = "left_tendril";
	/**
	 * The key of a right ribcage model part, whose value is {@value}.
	 */
	public static final String RIGHT_RIBCAGE = "right_ribcage";
	/**
	 * The key of a left ribcage model part, whose value is {@value}.
	 */
	public static final String LEFT_RIBCAGE = "left_ribcage";
	/**
	 * The key of a bone model part, whose value is {@value}.
	 */
	public static final String BONE = "bone";

	public WardenEntityModel(ModelPart root) {
		super(RenderLayer::getEntityCutoutNoCull);
		this.root = root;
		this.bone = root.getChild(BONE);
		this.body = this.bone.getChild(EntityModelPartNames.BODY);
		this.head = this.body.getChild(EntityModelPartNames.HEAD);
		this.rightLeg = this.bone.getChild(EntityModelPartNames.RIGHT_LEG);
		this.leftLeg = this.bone.getChild(EntityModelPartNames.LEFT_LEG);
		this.rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
		this.rightTendril = this.head.getChild(RIGHT_TENDRIL);
		this.leftTendril = this.head.getChild(LEFT_TENDRIL);
		this.rightRibcage = this.body.getChild(RIGHT_RIBCAGE);
		this.leftRibcage = this.body.getChild(LEFT_RIBCAGE);
		this.tendrils = ImmutableList.of(this.leftTendril, this.rightTendril);
		this.justBody = ImmutableList.of(this.body);
		this.headAndLimbs = ImmutableList.of(this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
		this.bodyHeadAndLimbs = ImmutableList.of(this.body, this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);

		this.defaultTransforms = new HashMap<>();
		this.getPart().traverse().forEach(part -> defaultTransforms.put(part, part.getTransform()));
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild(BONE, ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f));
		ModelPartData modelPartData3 = modelPartData2.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-9.0f, -13.0f, -4.0f, 18.0f, 21.0f, 11.0f), ModelTransform.pivot(0.0f, -21.0f, 0.0f));
		modelPartData3.addChild(RIGHT_RIBCAGE, ModelPartBuilder.create().uv(90, 11).cuboid(-2.0f, -11.0f, -0.1f, 9.0f, 21.0f, 0.0f), ModelTransform.pivot(-7.0f, -2.0f, -4.0f));
		modelPartData3.addChild(LEFT_RIBCAGE, ModelPartBuilder.create().uv(90, 11).mirrored().cuboid(-7.0f, -11.0f, -0.1f, 9.0f, 21.0f, 0.0f).mirrored(false), ModelTransform.pivot(7.0f, -2.0f, -4.0f));
		ModelPartData modelPartData4 = modelPartData3.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 32).cuboid(-8.0f, -16.0f, -5.0f, 16.0f, 16.0f, 10.0f), ModelTransform.pivot(0.0f, -13.0f, 0.0f));
		modelPartData4.addChild(RIGHT_TENDRIL, ModelPartBuilder.create().uv(52, 32).cuboid(-16.0f, -13.0f, 0.0f, 16.0f, 16.0f, 0.0f), ModelTransform.pivot(-8.0f, -12.0f, 0.0f));
		modelPartData4.addChild(LEFT_TENDRIL, ModelPartBuilder.create().uv(58, 0).cuboid(0.0f, -13.0f, 0.0f, 16.0f, 16.0f, 0.0f), ModelTransform.pivot(8.0f, -12.0f, 0.0f));
		modelPartData3.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(44, 50).cuboid(-4.0f, 0.0f, -4.0f, 8.0f, 28.0f, 8.0f), ModelTransform.pivot(-13.0f, -13.0f, 1.0f));
		modelPartData3.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 58).cuboid(-4.0f, 0.0f, -4.0f, 8.0f, 28.0f, 8.0f), ModelTransform.pivot(13.0f, -13.0f, 1.0f));
		modelPartData2.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(76, 48).cuboid(-3.1f, 0.0f, -3.0f, 6.0f, 13.0f, 6.0f), ModelTransform.pivot(-5.9f, -13.0f, 0.0f));
		modelPartData2.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(76, 76).cuboid(-2.9f, 0.0f, -3.0f, 6.0f, 13.0f, 6.0f), ModelTransform.pivot(5.9f, -13.0f, 0.0f));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(T wardenEntity, float f, float g, float h, float i, float j) {
		this.getPart().traverse().forEach(part -> part.setTransform(defaultTransforms.getOrDefault(part, ModelTransform.NONE)));
		float k = h - (float)((WardenEntity)wardenEntity).age;
		this.setHeadAngle(i, j);
		this.setLimbAngles(f, g);
		this.setHeadAndBodyAngles(h);
		this.setTendrilPitches(wardenEntity, h, k);
		this.updateAnimation(((WardenEntity)wardenEntity).attackingAnimationState, WardenAnimations.ATTACKING, h);
		this.updateAnimation(((WardenEntity)wardenEntity).chargingSonicBoomAnimationState, WardenAnimations.CHARGING_SONIC_BOOM, h);
		this.updateAnimation(((WardenEntity)wardenEntity).diggingAnimationState, WardenAnimations.DIGGING, h);
		this.updateAnimation(((WardenEntity)wardenEntity).emergingAnimationState, WardenAnimations.EMERGING, h);
		this.updateAnimation(((WardenEntity)wardenEntity).roaringAnimationState, WardenAnimations.ROARING, h);
		this.updateAnimation(((WardenEntity)wardenEntity).sniffingAnimationState, WardenAnimations.SNIFFING, h);
	}

	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress) {
		this.updateAnimation(animationState, animation, animationProgress, 1.0f);
	}

	private static final Vec3f field_39195 = new Vec3f();

	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress, float speedMultiplier) {
		animationState.update(animationProgress, speedMultiplier);
		animationState.run(state -> AnimationHelper.animate(this, animation, state.getTimeRunning(), 1.0f, field_39195));
	}

	private void setHeadAngle(float yaw, float pitch) {
		this.head.pitch = pitch * ((float)Math.PI / 180);
		this.head.yaw = yaw * ((float)Math.PI / 180);
	}

	private void setHeadAndBodyAngles(float animationProgress) {
		float f = animationProgress * 0.1f;
		float g = MathHelper.cos(f);
		float h = MathHelper.sin(f);
		this.head.roll += 0.06f * g;
		this.head.pitch += 0.06f * h;
		this.body.roll += 0.025f * h;
		this.body.pitch += 0.025f * g;
	}

	private void setLimbAngles(float angle, float distance) {
		float f = Math.min(0.5f, 3.0f * distance);
		float g = angle * 0.8662f;
		float h = MathHelper.cos(g);
		float i = MathHelper.sin(g);
		float j = Math.min(0.35f, f);
		this.head.roll += 0.3f * i * f;
		this.head.pitch += 1.2f * MathHelper.cos(g + 1.5707964f) * j;
		this.body.roll = 0.1f * i * f;
		this.body.pitch = 1.0f * h * j;
		this.leftLeg.pitch = 1.0f * h * f;
		this.rightLeg.pitch = 1.0f * MathHelper.cos(g + (float)Math.PI) * f;
		this.leftArm.pitch = -(0.8f * h * f);
		this.leftArm.roll = 0.0f;
		this.rightArm.pitch = -(0.8f * i * f);
		this.rightArm.roll = 0.0f;
		this.setArmPivots();
	}

	private void setArmPivots() {
		this.leftArm.yaw = 0.0f;
		this.leftArm.pivotZ = 1.0f;
		this.leftArm.pivotX = 13.0f;
		this.leftArm.pivotY = -13.0f;
		this.rightArm.yaw = 0.0f;
		this.rightArm.pivotZ = 1.0f;
		this.rightArm.pivotX = -13.0f;
		this.rightArm.pivotY = -13.0f;
	}

	private void setTendrilPitches(T warden, float animationProgress, float tickDelta) {
		float f;
		this.leftTendril.pitch = f = ((WardenEntity)warden).getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25) * Math.PI * (double)0.1f);
		this.rightTendril.pitch = -f;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	public List<ModelPart> getTendrils() {
		return this.tendrils;
	}

	public List<ModelPart> getBody() {
		return this.justBody;
	}

	public List<ModelPart> getHeadAndLimbs() {
		return this.headAndLimbs;
	}

	public List<ModelPart> getBodyHeadAndLimbs() {
		return this.bodyHeadAndLimbs;
	}
}