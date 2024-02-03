package fun.wich.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

/**
 * Represents the model of a {@linkplain VexEntity}.
 *
 * <div class="fabric">
 * <table border=1>
 * <caption>Model parts of this model</caption>
 * <tr>
 *   <th>Part Name</th><th>Parent</th><th>Corresponding Field</th>
 * </tr>
 * <tr>
 *   <td>{@value EntityModelPartNames#HEAD}</td><td>Root part</td><td></td>
 * </tr>
 * <tr>
 *   <td>{@value EntityModelPartNames#BODY}</td><td>Root part</td><td>{@link #body}</td>
 * </tr>
 * <tr>
 *   <td>{@value EntityModelPartNames#RIGHT_ARM}</td><td>Root part</td><td>{@link #rightArm}</td>
 * </tr>
 * <tr>
 *   <td>{@value EntityModelPartNames#LEFT_ARM}</td><td>Root part</td><td>{@link #leftArm}</td>
 * </tr>
 * <tr>
 *   <td>{@value EntityModelPartNames#RIGHT_WING}</td><td>Root part</td><td>{@link #rightWing}</td>
 * </tr>
 * <tr>
 *   <td>{@value EntityModelPartNames#LEFT_WING}</td><td>Root part</td><td>{@link #leftWing}</td>
 * </tr>
 * </table>
 * </div>
 */
@Environment(value=EnvType.CLIENT)
public class ModVexEntityModel extends SinglePartEntityModel<VexEntity> implements ModelWithArms {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightWing;
	private final ModelPart leftWing;

	public ModVexEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		this.root = root.getChild(ModEntityModelPartNames.ROOT);
		this.head = this.root.getChild(EntityModelPartNames.HEAD);
		this.body = this.root.getChild(EntityModelPartNames.BODY);
		this.rightArm = this.body.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = this.body.getChild(EntityModelPartNames.LEFT_ARM);
		this.rightWing = this.body.getChild(EntityModelPartNames.RIGHT_WING);
		this.leftWing = this.body.getChild(EntityModelPartNames.LEFT_WING);
	}

	private static final ModelTransform rootTransform = ModelTransform.pivot(0.0f, 0.0f, 0.0f);
	private static final ModelTransform headTransform = ModelTransform.pivot(0.0f, 20.0f, 0.0f);
	private static final ModelTransform bodyTransform = ModelTransform.pivot(0.0f, 20.0f, 0.0f);
	private static final ModelTransform rightArmTransform = ModelTransform.pivot(-1.75f, 0.25f, 0.0f);
	private static final ModelTransform leftArmTransform = ModelTransform.pivot(1.75f, 0.25f, 0.0f);
	private static final ModelTransform rightWingTransform = ModelTransform.pivot(-0.5f, 1.0f, 1.0f);
	private static final ModelTransform leftWingTransform = ModelTransform.pivot(0.5f, 1.0f, 1.0f);

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData ROOT = modelPartData.addChild(ModEntityModelPartNames.ROOT, ModelPartBuilder.create(), rootTransform);
		ROOT.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, -5.0f, -2.5f, 5.0f, 5.0f, 5.0f, new Dilation(0.0f)), headTransform);
		ModelPartData BODY = ROOT.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 10).cuboid(-1.5f, 0.0f, -1.0f, 3.0f, 4.0f, 2.0f, new Dilation(0.0f)).uv(0, 16).cuboid(-1.5f, 1.0f, -1.0f, 3.0f, 5.0f, 2.0f, new Dilation(-0.2f)), bodyTransform);
		BODY.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(23, 0).cuboid(-1.25f, -0.5f, -1.0f, 2.0f, 4.0f, 2.0f, new Dilation(-0.1f)), rightArmTransform);
		BODY.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(23, 6).cuboid(-0.75f, -0.5f, -1.0f, 2.0f, 4.0f, 2.0f, new Dilation(-0.1f)), leftArmTransform);
		BODY.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(16, 14).mirrored().cuboid(0.0f, 0.0f, 0.0f, 0.0f, 5.0f, 8.0f, new Dilation(0.0f)).mirrored(false), leftWingTransform);
		BODY.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(16, 14).cuboid(0.0f, 0.0f, 0.0f, 0.0f, 5.0f, 8.0f, new Dilation(0.0f)), rightWingTransform);
		return TexturedModelData.of(modelData, 32, 32);
	}

	public ModelTransform getDefaultTransform(ModelPart part) {
		if (part == this.root) return rootTransform;
		else if (part == this.head) return headTransform;
		else if (part == this.body) return bodyTransform;
		else if (part == this.rightArm) return rightArmTransform;
		else if (part == this.leftArm) return leftArmTransform;
		else if (part == this.rightWing) return rightWingTransform;
		else if (part == this.leftWing) return leftWingTransform;
		return ModelTransform.NONE;
	}
	public void resetTransform(ModelPart part) { part.setTransform(getDefaultTransform(part)); }
	@Override
	public void setAngles(VexEntity vexEntity, float f, float g, float h, float i, float j) {
		this.getPart().traverse().forEach(this::resetTransform);
		this.body.pitch = 6.440265f;
		float k = 0.62831855f + MathHelper.cos(h * 5.5f * ((float)Math.PI / 180)) * 0.1f;
		if (vexEntity.isCharging()) {
			this.body.pitch = 0.0f;
			this.rightArm.pitch = 3.6651914f;
			this.rightArm.yaw = 0.2617994f;
			this.rightArm.roll = -0.47123888f;
		} else {
			this.body.pitch = 0.15707964f;
			this.rightArm.pitch = 0.0f;
			this.rightArm.yaw = 0.0f;
			this.rightArm.roll = k;
		}
		this.leftArm.roll = -k;
		this.rightWing.pivotY = 1.0f;
		this.leftWing.pivotY = 1.0f;
		this.leftWing.yaw = 1.0995574f + MathHelper.cos(h * 45.836624f * ((float)Math.PI / 180)) * ((float)Math.PI / 180) * 16.2f;
		this.rightWing.yaw = -this.leftWing.yaw;
		this.leftWing.pitch = 0.47123888f;
		this.leftWing.roll = -0.47123888f;
		this.rightWing.pitch = 0.47123888f;
		this.rightWing.roll = 0.47123888f;
	}
	@Override
	public ModelPart getPart() { return this.root; }
	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.translateArmToPivot(matrices);
		this.rotateArm(matrices);
		matrices.scale(0.55f, 0.55f, 0.55f);
		this.translateArmAfterScale(matrices);
	}
	private void translateArmToPivot(MatrixStack matrices) {
		matrices.translate((this.body.pivotX + this.rightArm.pivotX) / 16.0f, (this.body.pivotY + this.rightArm.pivotY) / 16.0f, (this.body.pivotZ + this.rightArm.pivotZ) / 16.0f);
	}
	private void rotateArm(MatrixStack matrices) {
		matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(this.rightArm.roll));
		matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(this.rightArm.yaw));
		matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(this.rightArm.pitch));
	}
	private void translateArmAfterScale(MatrixStack matrices) { matrices.translate(0.046875, -0.15625, 0.078125); }
}