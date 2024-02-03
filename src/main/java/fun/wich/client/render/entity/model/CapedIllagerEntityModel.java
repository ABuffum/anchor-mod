package fun.wich.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(value= EnvType.CLIENT)
public class CapedIllagerEntityModel<T extends IllagerEntity> extends IllagerEntityModel<T> {
	protected final ModelPart root;
	protected final ModelPart head;
	protected final ModelPart hat;
	private final ModelPart arms;
	public final ModelPart cape;
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	protected final ModelPart rightArm;
	protected final ModelPart leftArm;

	public CapedIllagerEntityModel(ModelPart root) {
		super(root);
		this.root = root;
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.hat = this.head.getChild(EntityModelPartNames.HAT);
		this.cape = root.getChild(ModEntityModelPartNames.CAPE);
		this.cape.visible = false;
		this.arms = root.getChild(EntityModelPartNames.ARMS);
		this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
		this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
		this.leftArm = root.getChild(EntityModelPartNames.LEFT_ARM);
		this.rightArm = root.getChild(EntityModelPartNames.RIGHT_ARM);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -10, -4, 8, 10, 8), ModelTransform.pivot(0, 0, 0));
		modelPartData2.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(70, 33).cuboid(-4.5F, -10, -4, 9, 11, 9, new Dilation(0.45F)), ModelTransform.NONE);
		//modelPartData2.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4, -10, -4, 8, 12, 8, new Dilation(0.45f)), ModelTransform.NONE);
		modelPartData2.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(24, 0).cuboid(-1, -1, -6, 2, 4, 2), ModelTransform.pivot(0, -2, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 20).cuboid(-4, 0, -3, 8, 12, 6).uv(0, 38).cuboid(-4, 0, -3, 8, 18, 6, new Dilation(0.5f)), ModelTransform.pivot(0, 0, 0));

		//modelPartData.addChild(ModEntityModelPartNames.CAPE, ModelPartBuilder.create().uv(99, 16).cuboid(-4.5F, -10, 3, 9, 20, 1), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(ModEntityModelPartNames.CAPE, ModelPartBuilder.create().uv(99, 16).cuboid(-4.5F, 0, 0, 9, 20, 1), ModelTransform.pivot(0, 0, 0));

		ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(44, 22).cuboid(-8, -2, -2, 4, 8, 4).uv(40, 38).cuboid(-4, 2, -2, 8, 4, 4), ModelTransform.of(0, 3, -1, -0.75f, 0, 0));
		modelPartData3.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4, -2, -2, 4, 8, 4), ModelTransform.NONE);
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 22).cuboid(-2, 0, -2, 4, 12, 4), ModelTransform.pivot(-2, 12, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2, 0, -2, 4, 12, 4), ModelTransform.pivot(2, 12, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 46).cuboid(-3, -2, -2, 4, 12, 4), ModelTransform.pivot(-5, 2, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1, -2, -2, 4, 12, 4), ModelTransform.pivot(5, 2, 0));
		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public ModelPart getPart() { return this.root; }

	@Override
	public void setAngles(T illagerEntity, float f, float g, float h, float i, float j) {
		boolean bl;
		this.head.yaw = i * ((float)Math.PI / 180);
		this.head.pitch = j * ((float)Math.PI / 180);
		if (this.riding) {
			this.rightArm.pitch = -0.62831855f;
			this.rightArm.yaw = 0;
			this.rightArm.roll = 0;
			this.leftArm.pitch = -0.62831855f;
			this.leftArm.yaw = 0;
			this.leftArm.roll = 0;
			this.rightLeg.pitch = -1.4137167f;
			this.rightLeg.yaw = 0.31415927f;
			this.rightLeg.roll = 0.07853982f;
			this.leftLeg.pitch = -1.4137167f;
			this.leftLeg.yaw = -0.31415927f;
			this.leftLeg.roll = -0.07853982f;
		}
		else {
			this.rightArm.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2 * g * 0.5f;
			this.rightArm.yaw = 0;
			this.rightArm.roll = 0;
			this.leftArm.pitch = MathHelper.cos(f * 0.6662f) * 2 * g * 0.5f;
			this.leftArm.yaw = 0;
			this.leftArm.roll = 0;
			this.rightLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * g * 0.5f;
			this.rightLeg.yaw = 0;
			this.rightLeg.roll = 0;
			this.leftLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * g * 0.5f;
			this.leftLeg.yaw = 0;
			this.leftLeg.roll = 0;
		}
		IllagerEntity.State state = illagerEntity.getState();
		if (state == IllagerEntity.State.ATTACKING) {
			if (illagerEntity.getMainHandStack().isEmpty()) {
				CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, h);
			}
			else {
				CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, illagerEntity, this.handSwingProgress, h);
			}
		}
		else if (state == IllagerEntity.State.SPELLCASTING) {
			this.rightArm.pivotZ = 0;
			this.rightArm.pivotX = -5;
			this.leftArm.pivotZ = 0;
			this.leftArm.pivotX = 5;
			this.rightArm.pitch = MathHelper.cos(h * 0.6662f) * 0.25f;
			this.leftArm.pitch = MathHelper.cos(h * 0.6662f) * 0.25f;
			this.rightArm.roll = 2.3561945f;
			this.leftArm.roll = -2.3561945f;
			this.rightArm.yaw = 0;
			this.leftArm.yaw = 0;
		}
		else if (state == IllagerEntity.State.BOW_AND_ARROW) {
			this.rightArm.yaw = -0.1f + this.head.yaw;
			this.rightArm.pitch = -1.5707964f + this.head.pitch;
			this.leftArm.pitch = -0.9424779f + this.head.pitch;
			this.leftArm.yaw = this.head.yaw - 0.4f;
			this.leftArm.roll = 1.5707964f;
		}
		else if (state == IllagerEntity.State.CROSSBOW_HOLD) {
			CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
		}
		else if (state == IllagerEntity.State.CROSSBOW_CHARGE) {
			CrossbowPosing.charge(this.rightArm, this.leftArm, illagerEntity, true);
		}
		else if (state == IllagerEntity.State.CELEBRATING) {
			this.rightArm.pivotZ = 0;
			this.rightArm.pivotX = -5;
			this.rightArm.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
			this.rightArm.roll = 2.670354f;
			this.rightArm.yaw = 0;
			this.leftArm.pivotZ = 0;
			this.leftArm.pivotX = 5;
			this.leftArm.pitch = MathHelper.cos(h * 0.6662f) * 0.05f;
			this.leftArm.roll = -2.3561945f;
			this.leftArm.yaw = 0;
		}
		this.arms.visible = bl = state == IllagerEntity.State.CROSSED;
		this.leftArm.visible = !bl;
		this.rightArm.visible = !bl;
	}
	private ModelPart getAttackingArm(Arm arm) { return arm == Arm.LEFT ? this.leftArm : this.rightArm; }
	@Override
	public ModelPart getHat() { return this.hat; }
	@Override
	public ModelPart getHead() { return this.head; }
	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) { this.getAttackingArm(arm).rotate(matrices); }
}
