package fun.mousewich.client.render.entity.model.spider;

import fun.mousewich.client.render.entity.model.ModEntityModelPartNames;
import fun.mousewich.entity.hostile.spider.SlimeSpiderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class SlimeSpiderEntityModel extends SinglePartEntityModel<SlimeSpiderEntity> {
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

	public SlimeSpiderEntityModel(ModelPart root) {
		this.root = root;
		this.head = this.root.getChild(EntityModelPartNames.HEAD);
		this.body0 = this.root.getChild(ModEntityModelPartNames.BODY0);
		this.body1 = this.root.getChild(ModEntityModelPartNames.BODY1);
		this.rightHindLeg = this.root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftHindLeg = this.root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.rightMiddleLeg = this.root.getChild(ModEntityModelPartNames.RIGHT_MIDDLE_HIND_LEG);
		this.leftMiddleLeg = this.root.getChild(ModEntityModelPartNames.LEFT_MIDDLE_HIND_LEG);
		this.rightMiddleFrontLeg = this.root.getChild(ModEntityModelPartNames.RIGHT_MIDDLE_FRONT_LEG);
		this.leftMiddleFrontLeg = this.root.getChild(ModEntityModelPartNames.LEFT_MIDDLE_FRONT_LEG);
		this.rightFrontLeg = this.root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.leftFrontLeg = this.root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
	}

	private static final ModelTransform headTransform = ModelTransform.pivot(0, 15, -3);
	private static final ModelTransform body0Transform = ModelTransform.pivot(0, 15, 0);
	private static final ModelTransform body1Transform = ModelTransform.pivot(0, 1, 9);

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()//.uv(32, 4).cuboid(-4, -4, -8, 8, 8, 8, new Dilation(0.1F))
				.uv(40, 36).cuboid(-3, -3, -7, 6, 6, 6)
				.uv(38, 36).cuboid(-4, -2, -8, 1, 2, 1)
				.uv(42, 36).cuboid(3, -2, -8, 1, 2, 1)
				.uv(28, 40).cuboid(-2, -1, -8, 4, 2, 2)
				.uv(18, 36).cuboid(1, -3, -8, 1, 1, 1)
				.uv(22, 36).cuboid(2, -4, -8, 1, 1, 1)
				.uv(26, 36).cuboid(-2, -3, -8, 1, 1, 1)
				.uv(30, 36).cuboid(-3, -4, -8, 1, 1, 1)
				.uv(18, 38).cuboid(-4, 1, -7, 1, 2, 2)
				.uv(20, 42).cuboid(-4, 3, -6, 1, 1, 1)
				.uv(4, 40).cuboid(-3, 2, -8, 2, 2, 1)
				.uv(10, 40).cuboid(1, 2, -8, 2, 2, 1)
				.uv(24, 38).cuboid(3, 1, -7, 1, 2, 2)
				.uv(24, 42).cuboid(3, 3, -6, 1, 1, 1), ModelTransform.pivot(0, 15, -3));
		modelPartData.addChild(ModEntityModelPartNames.BODY0, ModelPartBuilder.create()//.uv(0, 0).cuboid(-3, -3, -3, 6, 6, 6)
				.uv(0, 32).cuboid(-2, -2, -2, 4, 4, 4), ModelTransform.pivot(0, 15, 0));
		modelPartData.addChild(ModEntityModelPartNames.BODY1, ModelPartBuilder.create()//.uv(0, 12).cuboid(-5, -4, -6, 10, 8, 12)
				.uv(0, 48).cuboid(-4, -3, -5, 8, 6, 10), ModelTransform.pivot(0, 15, 9));
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-15, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(-4, 15, 4));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-1, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(4, 15, 4));
		modelPartData.addChild(ModEntityModelPartNames.RIGHT_MIDDLE_HIND_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-15, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(-4, 15, 1));
		modelPartData.addChild(ModEntityModelPartNames.LEFT_MIDDLE_HIND_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-1, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(4, 15, 1));
		modelPartData.addChild(ModEntityModelPartNames.RIGHT_MIDDLE_FRONT_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-15, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(-4, 15, -2));
		modelPartData.addChild(ModEntityModelPartNames.LEFT_MIDDLE_FRONT_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-1, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(4, 15, -2));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-15, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(-4, 15, -5));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create()//.uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2)
				.uv(18, 32).cuboid(-1, -1, -1, 16, 2, 2, new Dilation(-0.1f)), ModelTransform.pivot(4, 15, -5));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getOuterTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(32, 4).cuboid(-4, -4, -8, 8, 8, 8, new Dilation(0.1F)), ModelTransform.pivot(0, 15, -3));
		modelPartData.addChild(ModEntityModelPartNames.BODY0, ModelPartBuilder.create().uv(0, 0).cuboid(-3, -3, -3, 6, 6, 6), ModelTransform.pivot(0, 15, 0));
		modelPartData.addChild(ModEntityModelPartNames.BODY1, ModelPartBuilder.create().uv(0, 12).cuboid(-5, -4, -6, 10, 8, 12), ModelTransform.pivot(0, 15, 9));
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2), ModelTransform.pivot(-4, 15, 4));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2), ModelTransform.pivot(4, 15, 4));
		modelPartData.addChild(ModEntityModelPartNames.RIGHT_MIDDLE_HIND_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2), ModelTransform.pivot(-4, 15, 1));
		modelPartData.addChild(ModEntityModelPartNames.LEFT_MIDDLE_HIND_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2), ModelTransform.pivot(4, 15, 1));
		modelPartData.addChild(ModEntityModelPartNames.RIGHT_MIDDLE_FRONT_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2), ModelTransform.pivot(-4, 15, -2));
		modelPartData.addChild(ModEntityModelPartNames.LEFT_MIDDLE_FRONT_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2), ModelTransform.pivot(4, 15, -2));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-15, -1, -1, 16, 2, 2), ModelTransform.pivot(-4, 15, -5));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(18, 0).cuboid(-1, -1, -1, 16, 2, 2), ModelTransform.pivot(4, 15, -5));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public ModelPart getPart() { return this.root; }

	@Override
	public void setAngles(SlimeSpiderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
		this.rightHindLeg.roll = -0.7853982f;
		this.leftHindLeg.roll = 0.7853982f;
		this.rightMiddleLeg.roll = -0.58119464f;
		this.leftMiddleLeg.roll = 0.58119464f;
		this.rightMiddleFrontLeg.roll = -0.58119464f;
		this.leftMiddleFrontLeg.roll = 0.58119464f;
		this.rightFrontLeg.roll = -0.7853982f;
		this.leftFrontLeg.roll = 0.7853982f;
		this.rightHindLeg.yaw = 0.7853982f;
		this.leftHindLeg.yaw = -0.7853982f;
		this.rightMiddleLeg.yaw = 0.3926991f;
		this.leftMiddleLeg.yaw = -0.3926991f;
		this.rightMiddleFrontLeg.yaw = -0.3926991f;
		this.leftMiddleFrontLeg.yaw = 0.3926991f;
		this.rightFrontLeg.yaw = -0.7853982f;
		this.leftFrontLeg.yaw = 0.7853982f;
		float d = limbAngle * 0.6662f;
		float i = -(MathHelper.cos(d * 2) * 0.4f) * limbDistance;
		float j = -(MathHelper.cos(d * 2 + (float)Math.PI) * 0.4f) * limbDistance;
		float k = -(MathHelper.cos(d * 2 + 1.5707964f) * 0.4f) * limbDistance;
		float l = -(MathHelper.cos(d * 2 + 4.712389f) * 0.4f) * limbDistance;
		float m = Math.abs(MathHelper.sin(d) * 0.4f) * limbDistance;
		float n = Math.abs(MathHelper.sin(d + (float)Math.PI) * 0.4f) * limbDistance;
		float o = Math.abs(MathHelper.sin(d + 1.5707964f) * 0.4f) * limbDistance;
		float p = Math.abs(MathHelper.sin(d + 4.712389f) * 0.4f) * limbDistance;
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
