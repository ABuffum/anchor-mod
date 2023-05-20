package fun.mousewich.client.render.entity.model;

import fun.mousewich.entity.hostile.skeleton.SunkenSkeletonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@Environment(value= EnvType.CLIENT)
public class SunkenSkeletonEntityModel extends BipedEntityModel<SunkenSkeletonEntity> {
	public ModelPart[] fans;
	public SunkenSkeletonEntityModel(ModelPart modelPart) {
		super(modelPart);
		fans = List.of(
				this.head.getChild("top_fan"),
				this.head.getChild("side_fan"),
				this.head.getChild("wrap")
		).toArray(ModelPart[]::new);
	}
	private static final TexturedModelData texturedModelData4 = TexturedModelData.of(getModelData(new Dilation(0.5f), 0), 64, 32);
	public static TexturedModelData getArmorModelData() { return getTexturedModelData(new Dilation(0.5f)); }
	public static TexturedModelData getTexturedModelData() { return getTexturedModelData(Dilation.NONE); }
	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = getModelData(dilation, 0);
		ModelPartData root = modelData.getRoot();
		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation), ModelTransform.pivot(0, 0, 0));
		head.addChild("top_fan", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -16.0F, -4.0F, 9.0F, 8.0F, 0.0F), ModelTransform.NONE);
		head.addChild("side_fan", ModelPartBuilder.create().uv(32, 8).cuboid(4.0F, -8.0F, -4.0F, 4.0F, 2.0F, 0.0F), ModelTransform.NONE);
		head.addChild("wrap", ModelPartBuilder.create().uv(28, 12).cuboid(-1.0F, -3.0F, 2.0F, 6.0F, 0.0F, 4.0F), ModelTransform.NONE);
		ModelPartData body = root.getChild(EntityModelPartNames.BODY);
		body.addChild("rib", ModelPartBuilder.create().uv(14, 24).cuboid(-5.0F, 4.0F, -2.0F, 1.0F, 1.0F, 0.0F), ModelTransform.NONE);
		body.addChild("waste", ModelPartBuilder.create().uv(12, 30).cuboid(1.0F, 12.0F, -2.0F, 2.0F, 2.0F, 0.0F), ModelTransform.NONE);
		root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-1, -2, -1, 2, 12, 2), ModelTransform.pivot(-5, 2, 0));
		root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1, -2, -1, 2, 12, 2), ModelTransform.pivot(5, 2, 0));
		root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-1, 0, -1, 2, 12, 2), ModelTransform.pivot(-2, 12, 0));
		root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-1, 0, -1, 2, 12, 2), ModelTransform.pivot(2, 12, 0));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void animateModel(SunkenSkeletonEntity mobEntity, float f, float g, float h) {
		this.rightArmPose = ArmPose.EMPTY;
		this.leftArmPose = ArmPose.EMPTY;
		ItemStack itemStack = mobEntity.getStackInHand(Hand.MAIN_HAND);
		if (itemStack.isOf(Items.BOW) && mobEntity.isAttacking()) {
			if (mobEntity.getMainArm() == Arm.RIGHT) this.rightArmPose = ArmPose.BOW_AND_ARROW;
			else this.leftArmPose = ArmPose.BOW_AND_ARROW;
		}
		else if (itemStack.isOf(Items.CROSSBOW)) {
			if (mobEntity.isAttacking()) {
				if (mobEntity.getMainArm() == Arm.RIGHT) this.rightArmPose = ArmPose.CROSSBOW_CHARGE;
				else this.leftArmPose = ArmPose.CROSSBOW_CHARGE;
			}
			else if (CrossbowItem.isCharged(itemStack)) {
				if (mobEntity.getMainArm() == Arm.RIGHT) this.rightArmPose = ArmPose.CROSSBOW_HOLD;
				else this.leftArmPose = ArmPose.CROSSBOW_HOLD;
			}
		}
		super.animateModel(mobEntity, f, g, h);
	}

	@Override
	public void setAngles(SunkenSkeletonEntity mobEntity, float f, float g, float h, float i, float j) {
		super.setAngles(mobEntity, f, g, h, i, j);
		ItemStack itemStack = mobEntity.getMainHandStack();
		if (mobEntity.isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
			float k = MathHelper.sin(this.handSwingProgress * (float)Math.PI);
			float l = MathHelper.sin((1 - (1 - this.handSwingProgress) * (1 - this.handSwingProgress)) * (float)Math.PI);
			this.rightArm.roll = 0;
			this.leftArm.roll = 0;
			this.rightArm.yaw = -(0.1f - k * 0.6f);
			this.leftArm.yaw = 0.1f - k * 0.6f;
			this.rightArm.pitch = -1.5707964f;
			this.leftArm.pitch = -1.5707964f;
			this.rightArm.pitch -= k * 1.2f - l * 0.4f;
			this.leftArm.pitch -= k * 1.2f - l * 0.4f;
			CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
		}
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		float f = arm == Arm.RIGHT ? 1 : -1;
		ModelPart modelPart = this.getArm(arm);
		modelPart.pivotX += f;
		modelPart.rotate(matrices);
		modelPart.pivotX -= f;
	}
}
