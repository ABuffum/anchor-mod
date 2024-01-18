package fun.mousewich.client.render.entity.model.spider;

import fun.mousewich.client.render.entity.model.ModEntityModelPartNames;
import fun.mousewich.entity.hostile.spider.BoneSpiderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class BoneSpiderEntityModel extends SinglePartEntityModel<BoneSpiderEntity> {
	protected final ModelPart root;
	protected final ModelPart body0;
	protected final ModelPart body1;
	protected final ModelPart head;
	protected final ModelPart rightHindLeg;
	protected final ModelPart leftHindLeg;
	protected final ModelPart rightMiddleLeg;
	protected final ModelPart leftMiddleLeg;
	protected final ModelPart rightMiddleFrontLeg;
	protected final ModelPart leftMiddleFrontLeg;
	protected final ModelPart rightFrontLeg;
	protected final ModelPart leftFrontLeg;

	public BoneSpiderEntityModel(ModelPart root) {
		this.root = root;
		this.head = this.root.getChild(EntityModelPartNames.HEAD);
		this.body0 = this.root.getChild(ModEntityModelPartNames.BODY0);
		this.body1 = this.body0.getChild(ModEntityModelPartNames.BODY1);
		this.rightHindLeg = this.root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftHindLeg = this.root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.rightMiddleLeg = this.root.getChild(ModEntityModelPartNames.RIGHT_MIDDLE_HIND_LEG);
		this.leftMiddleLeg = this.root.getChild(ModEntityModelPartNames.LEFT_MIDDLE_HIND_LEG);
		this.rightMiddleFrontLeg = this.root.getChild(ModEntityModelPartNames.RIGHT_MIDDLE_FRONT_LEG);
		this.leftMiddleFrontLeg = this.root.getChild(ModEntityModelPartNames.LEFT_MIDDLE_FRONT_LEG);
		this.rightFrontLeg = this.root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.leftFrontLeg = this.root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
	}

	private static final ModelTransform headTransform = ModelTransform.pivot(0.0f, 15.0f, -3.0f);
	private static final ModelTransform body0Transform = ModelTransform.pivot(0.0f, 15.0f, 0.0f);
	private static final ModelTransform body1Transform = ModelTransform.pivot(0.0f, 1.0f, 9.0f);

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(32, 4)
				.cuboid(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f), headTransform);
		ModelPartData body = root.addChild(ModEntityModelPartNames.BODY0, ModelPartBuilder.create().uv(0, 0)
				.cuboid(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f), body0Transform);
		body.addChild(ModEntityModelPartNames.BODY1, ModelPartBuilder.create().uv(0, 12)
				.cuboid(-5.0f, -4.0f, -6.0f, 10.0f, 8.0f, 12.0f), body1Transform);
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(18, 0)
				.cuboid(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
		ModelPartBuilder modelPartBuilder2 = ModelPartBuilder.create().uv(18, 0)
				.cuboid(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
		root.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-4.0f, 15.0f, 2.0f));
		root.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder2, ModelTransform.pivot(4.0f, 15.0f, 2.0f));
		root.addChild(ModEntityModelPartNames.RIGHT_MIDDLE_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-4.0f, 15.0f, 1.0f));
		root.addChild(ModEntityModelPartNames.LEFT_MIDDLE_HIND_LEG, modelPartBuilder2, ModelTransform.pivot(4.0f, 15.0f, 1.0f));
		root.addChild(ModEntityModelPartNames.RIGHT_MIDDLE_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-4.0f, 15.0f, 0.0f));
		root.addChild(ModEntityModelPartNames.LEFT_MIDDLE_FRONT_LEG, modelPartBuilder2, ModelTransform.pivot(4.0f, 15.0f, 0.0f));
		root.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-4.0f, 15.0f, -1.0f));
		root.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder2, ModelTransform.pivot(4.0f, 15.0f, -1.0f));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public ModelPart getPart() { return this.root; }

	@Override
	public void setAngles(BoneSpiderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		int fireTime = entity.getFireTime();
		float fireProgress = 1 - ((float)fireTime / BoneSpiderEntity.MAX_FIRE_TIME);
		float headY = 15, bodyPitch = 0;
		if (fireTime > 0) {
			headY += fireProgress * 1.5;
			bodyPitch = fireProgress * 45 * ((float)Math.PI / 180);
		}
		this.body0.pitch = bodyPitch;
		this.head.setPivot(0, headY, 0);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
		float f = 0.7853982f;
		this.rightHindLeg.roll = -0.7853982f;
		this.leftHindLeg.roll = 0.7853982f;
		this.rightMiddleLeg.roll = -0.58119464f;
		this.leftMiddleLeg.roll = 0.58119464f;
		this.rightMiddleFrontLeg.roll = -0.58119464f;
		this.leftMiddleFrontLeg.roll = 0.58119464f;
		this.rightFrontLeg.roll = -0.7853982f;
		this.leftFrontLeg.roll = 0.7853982f;
		float g = -0.0f;
		float h = 0.3926991f;
		this.rightHindLeg.yaw = 0.7853982f;
		this.leftHindLeg.yaw = -0.7853982f;
		this.rightMiddleLeg.yaw = 0.3926991f;
		this.leftMiddleLeg.yaw = -0.3926991f;
		this.rightMiddleFrontLeg.yaw = -0.3926991f;
		this.leftMiddleFrontLeg.yaw = 0.3926991f;
		this.rightFrontLeg.yaw = -0.7853982f;
		this.leftFrontLeg.yaw = 0.7853982f;
		float i = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + 0.0f) * 0.4f) * limbDistance;
		float j = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + (float)Math.PI) * 0.4f) * limbDistance;
		float k = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + 1.5707964f) * 0.4f) * limbDistance;
		float l = -(MathHelper.cos(limbAngle * 0.6662f * 2.0f + 4.712389f) * 0.4f) * limbDistance;
		float m = Math.abs(MathHelper.sin(limbAngle * 0.6662f + 0.0f) * 0.4f) * limbDistance;
		float n = Math.abs(MathHelper.sin(limbAngle * 0.6662f + (float)Math.PI) * 0.4f) * limbDistance;
		float o = Math.abs(MathHelper.sin(limbAngle * 0.6662f + 1.5707964f) * 0.4f) * limbDistance;
		float p = Math.abs(MathHelper.sin(limbAngle * 0.6662f + 4.712389f) * 0.4f) * limbDistance;
		this.rightHindLeg.yaw += i;
		this.leftHindLeg.yaw += -i;
		this.rightMiddleLeg.yaw += j;
		this.leftMiddleLeg.yaw += -j;
		this.rightMiddleFrontLeg.yaw += k;
		this.leftMiddleFrontLeg.yaw += -k;
		this.rightFrontLeg.yaw += l;
		this.leftFrontLeg.yaw += -l;
		this.rightHindLeg.roll += m;
		this.leftHindLeg.roll += -m;
		this.rightMiddleLeg.roll += n;
		this.leftMiddleLeg.roll += -n;
		this.rightMiddleFrontLeg.roll += o;
		this.leftMiddleFrontLeg.roll += -o;
		this.rightFrontLeg.roll += p;
		this.leftFrontLeg.roll += -p;
	}
}
