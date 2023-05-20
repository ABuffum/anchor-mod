package fun.mousewich.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import fun.mousewich.client.render.entity.animation.Animation;
import fun.mousewich.client.render.entity.animation.AnimationHelper;
import fun.mousewich.client.render.entity.animation.AnimationState;
import fun.mousewich.client.render.entity.animation.WardenAnimations;
import fun.mousewich.entity.hostile.warden.WardenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public class WardenEntityModel<T extends WardenEntity> extends SinglePartEntityModel<T> {
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
	public WardenEntityModel(ModelPart root) {
		super(RenderLayer::getEntityCutoutNoCull);
		this.root = root;
		this.bone = root.getChild(ModEntityModelPartNames.BONE);
		this.rightLeg = this.bone.getChild(EntityModelPartNames.RIGHT_LEG);
		this.leftLeg = this.bone.getChild(EntityModelPartNames.LEFT_LEG);
		this.body = this.bone.getChild(EntityModelPartNames.BODY);
		this.rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
		this.rightRibcage = this.body.getChild(ModEntityModelPartNames.RIGHT_RIBCAGE);
		this.leftRibcage = this.body.getChild(ModEntityModelPartNames.LEFT_RIBCAGE);
		this.head = this.body.getChild(EntityModelPartNames.HEAD);
		this.rightTendril = this.head.getChild(ModEntityModelPartNames.RIGHT_TENDRIL);
		this.leftTendril = this.head.getChild(ModEntityModelPartNames.LEFT_TENDRIL);
		this.tendrils = ImmutableList.of(this.leftTendril, this.rightTendril);
		this.justBody = ImmutableList.of(this.body);
		this.headAndLimbs = ImmutableList.of(this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
		this.bodyHeadAndLimbs = ImmutableList.of(this.body, this.head, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
	}

	private static final ModelTransform boneTransform = ModelTransform.pivot(0, 24, 0);
	private static final ModelTransform rightLegTransform = ModelTransform.pivot(-5.9f, -13, 0);
	private static final ModelTransform leftLegTransform = ModelTransform.pivot(5.9f, -13, 0);
	private static final ModelTransform bodyTransform = ModelTransform.pivot(0, -21, 0);
	private static final ModelTransform rightRibcageTransform = ModelTransform.pivot(-7, -2, -4);
	private static final ModelTransform leftRibcageTransform = ModelTransform.pivot(7, -2, -4);
	private static final ModelTransform rightArmTransform = ModelTransform.pivot(-13, -13, 1);
	private static final ModelTransform leftArmTransform = ModelTransform.pivot(13, -13, 1);
	private static final ModelTransform headTransform = ModelTransform.pivot(0, -13, 0);
	private static final ModelTransform rightTendrilTransform = ModelTransform.pivot(-8, -12, 0);
	private static final ModelTransform leftTendrilTransform = ModelTransform.pivot(8, -12, 0);
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild(ModEntityModelPartNames.BONE, ModelPartBuilder.create(), boneTransform);
		ModelPartData modelPartData3 = modelPartData2.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0)
				.cuboid(-9, -13, -4, 18, 21, 11), bodyTransform);
		modelPartData3.addChild(ModEntityModelPartNames.RIGHT_RIBCAGE, ModelPartBuilder.create().uv(90, 11)
				.cuboid(-2, -11, -0.1f, 9, 21, 0), rightRibcageTransform);
		modelPartData3.addChild(ModEntityModelPartNames.LEFT_RIBCAGE, ModelPartBuilder.create().uv(90, 11).mirrored()
				.cuboid(-7, -11, -0.1f, 9, 21, 0).mirrored(false), leftRibcageTransform);
		ModelPartData modelPartData4 = modelPartData3.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 32)
				.cuboid(-8, -16, -5, 16, 16, 10), headTransform);
		modelPartData4.addChild(ModEntityModelPartNames.RIGHT_TENDRIL, ModelPartBuilder.create().uv(52, 32)
				.cuboid(-16, -13, 0, 16, 16, 0), rightTendrilTransform);
		modelPartData4.addChild(ModEntityModelPartNames.LEFT_TENDRIL, ModelPartBuilder.create().uv(58, 0)
				.cuboid(0, -13, 0, 16, 16, 0), leftTendrilTransform);
		modelPartData3.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(44, 50)
				.cuboid(-4, 0, -4, 8, 28, 8), rightArmTransform);
		modelPartData3.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 58)
				.cuboid(-4, 0, -4, 8, 28, 8), leftArmTransform);
		modelPartData2.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(76, 48)
				.cuboid(-3.1f, 0, -3, 6, 13, 6), rightLegTransform);
		modelPartData2.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(76, 76)
				.cuboid(-2.9f, 0, -3, 6, 13, 6), leftLegTransform);
		return TexturedModelData.of(modelData, 128, 128);
	}

	public ModelTransform getDefaultTransform(ModelPart part) {
		if (part == this.root) return ModelTransform.NONE;
		else if (part == this.bone) return boneTransform;
		else if (part == this.rightLeg) return rightLegTransform;
		else if (part == this.leftLeg) return leftLegTransform;
		else if (part == this.body) return bodyTransform;
		else if (part == this.rightRibcage) return rightRibcageTransform;
		else if (part == this.leftRibcage) return leftRibcageTransform;
		else if (part == this.rightArm) return rightArmTransform;
		else if (part == this.leftArm) return leftArmTransform;
		else if (part == this.head) return headTransform;
		else if (part == this.rightTendril) return rightTendrilTransform;
		else if (part == this.leftTendril) return leftTendrilTransform;
		return ModelTransform.NONE;
	}
	public void resetTransform(ModelPart part) { part.setTransform(getDefaultTransform(part)); }
	@Override
	public void setAngles(T wardenEntity, float f, float g, float h, float i, float j) {
		this.getPart().traverse().forEach(this::resetTransform);
		float k = h - (float)wardenEntity.age;
		this.setHeadAngle(i, j);
		this.setLimbAngles(f, g);
		this.setHeadAndBodyAngles(h);
		this.setTendrilPitches(wardenEntity, h, k);
		this.updateAnimation(wardenEntity.attackingAnimationState, WardenAnimations.ATTACKING, h);
		this.updateAnimation(wardenEntity.chargingSonicBoomAnimationState, WardenAnimations.CHARGING_SONIC_BOOM, h);
		this.updateAnimation(wardenEntity.diggingAnimationState, WardenAnimations.DIGGING, h);
		this.updateAnimation(wardenEntity.emergingAnimationState, WardenAnimations.EMERGING, h);
		this.updateAnimation(wardenEntity.roaringAnimationState, WardenAnimations.ROARING, h);
		this.updateAnimation(wardenEntity.sniffingAnimationState, WardenAnimations.SNIFFING, h);
	}
	protected void updateAnimation(AnimationState animationState, Animation animation, float animationProgress) {
		animationState.update(animationProgress, 1);
		animationState.run(state -> AnimationHelper.animate(this, animation, state.getTimeRunning(), 1, new Vec3f()));
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
		float f = Math.min(0.5f, 3 * distance);
		float g = angle * 0.8662f;
		float h = MathHelper.cos(g);
		float i = MathHelper.sin(g);
		float j = Math.min(0.35f, f);
		this.head.roll += 0.3f * i * f;
		this.head.pitch += 1.2f * MathHelper.cos(g + 1.5707964f) * j;
		this.body.roll = 0.1f * i * f;
		this.body.pitch = h * j;
		this.leftLeg.pitch = h * f;
		this.rightLeg.pitch = i * f; //MathHelper.cos(g + (float)Math.PI) * f;
		this.leftArm.pitch = -(0.8f * h * f);
		this.leftArm.roll = 0;
		this.rightArm.pitch = -(0.8f * i * f);
		this.rightArm.roll = 0;
		this.setArmPivots();
	}
	private void setArmPivots() {
		this.leftArm.yaw = 0;
		this.leftArm.pivotZ = 1;
		this.leftArm.pivotX = 13;
		this.leftArm.pivotY = -13;
		this.rightArm.yaw = 0;
		this.rightArm.pivotZ = 1;
		this.rightArm.pivotX = -13;
		this.rightArm.pivotY = -13;
	}
	private void setTendrilPitches(T warden, float animationProgress, float tickDelta) {
		float f;
		this.leftTendril.pitch = f = warden.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25) * Math.PI * (double)0.1f);
		this.rightTendril.pitch = -f;
	}
	@Override
	public ModelPart getPart() { return this.root; }
	public List<ModelPart> getTendrils() { return this.tendrils; }
	public List<ModelPart> getBody() { return this.justBody; }
	public List<ModelPart> getHeadAndLimbs() { return this.headAndLimbs; }
	public List<ModelPart> getBodyHeadAndLimbs() { return this.bodyHeadAndLimbs; }
}
