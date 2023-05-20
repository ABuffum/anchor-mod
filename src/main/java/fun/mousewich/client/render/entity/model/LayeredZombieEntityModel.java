package fun.mousewich.client.render.entity.model;

import fun.mousewich.entity.hostile.zombie.FrozenZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class LayeredZombieEntityModel<T extends ZombieEntity> extends ZombieEntityModel<T> {
	public LayeredZombieEntityModel(ModelPart modelPart) { super(modelPart); }
	private static final TexturedModelData texturedModelData4 = TexturedModelData.of(getModelData(new Dilation(0.5f), 0.0f), 64, 32);
	public static TexturedModelData getArmorModelData() { return texturedModelData4; }
	public static TexturedModelData getInnerModelData() { return getTexturedModelData(Dilation.NONE); }
	public static TexturedModelData getOuterModelData() { return getTexturedModelData(new Dilation(0.25f)); }
	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = getModelData(dilation, 0.0f);
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(5.0f, 2.0f, 0.0f));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(16, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), ModelTransform.pivot(1.9f, 12.0f, 0.0f));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void animateModel(T zombieEntity, float f, float g, float h) {
		this.rightArmPose = ArmPose.EMPTY;
		this.leftArmPose = ArmPose.EMPTY;
		ItemStack itemStack = zombieEntity.getStackInHand(Hand.MAIN_HAND);
		if (itemStack.isOf(Items.TRIDENT) && zombieEntity.isAttacking()) {
			if (zombieEntity.getMainArm() == Arm.RIGHT) this.rightArmPose = ArmPose.THROW_SPEAR;
			else this.leftArmPose = ArmPose.THROW_SPEAR;
		}
		super.animateModel(zombieEntity, f, g, h);
	}

	@Override
	public void setAngles(T zombieEntity, float f, float g, float h, float i, float j) {
		super.setAngles(zombieEntity, f, g, h, i, j);
		if (this.leftArmPose == ArmPose.THROW_SPEAR) {
			this.leftArm.pitch = this.leftArm.pitch * 0.5f - (float)Math.PI;
			this.leftArm.yaw = 0.0f;
		}
		if (this.rightArmPose == ArmPose.THROW_SPEAR) {
			this.rightArm.pitch = this.rightArm.pitch * 0.5f - (float)Math.PI;
			this.rightArm.yaw = 0.0f;
		}
		if (this.leaningPitch > 0.0f) {
			this.rightArm.pitch = this.lerpAngle(this.leaningPitch, this.rightArm.pitch, -2.5132742f) + this.leaningPitch * 0.35f * MathHelper.sin(0.1f * h);
			this.leftArm.pitch = this.lerpAngle(this.leaningPitch, this.leftArm.pitch, -2.5132742f) - this.leaningPitch * 0.35f * MathHelper.sin(0.1f * h);
			this.rightArm.roll = this.lerpAngle(this.leaningPitch, this.rightArm.roll, -0.15f);
			this.leftArm.roll = this.lerpAngle(this.leaningPitch, this.leftArm.roll, 0.15f);
			this.leftLeg.pitch -= this.leaningPitch * 0.55f * MathHelper.sin(0.1f * h);
			this.rightLeg.pitch += this.leaningPitch * 0.55f * MathHelper.sin(0.1f * h);
			this.head.pitch = 0.0f;
		}
	}
}