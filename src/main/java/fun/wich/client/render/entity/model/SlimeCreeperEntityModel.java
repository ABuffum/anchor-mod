package fun.wich.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(value=EnvType.CLIENT)
public class SlimeCreeperEntityModel<T extends Entity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;

	public SlimeCreeperEntityModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()//.uv(0, 0).cuboid(-4, -8, -4, 8, 8, 8)
				.uv(0, 32).cuboid(-4, -7, -3, 8, 7, 6, new Dilation(-0.1F)), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create()//.uv(16, 16).cuboid(-4, 0, -2, 8, 12, 4)
				.uv(28, 34).cuboid(-2, 8, -2, 4, 4, 4, new Dilation(-0.2F))
				.uv(16, 45).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(-0.1F)), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create()//.uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4)
				.uv(0, 26).cuboid(-1, 0.1F, -2, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 51).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(-2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create()//.uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4)
				.uv(0, 26).cuboid(-1, 0.1F, -2, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 51).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create()//.uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4)
				.uv(0, 26).cuboid(-1, 0.1F, 0, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 45).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(-2, 18, -4));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create()//.uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4)
				.uv(0, 26).cuboid(-1, 0.1F, 0, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 45).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(2, 18, -4));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getOuterTexturedModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4, 0, -2, 8, 12, 4, dilation), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation), ModelTransform.pivot(-2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation), ModelTransform.pivot(2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation), ModelTransform.pivot(-2, 18, -4));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation), ModelTransform.pivot(2, 18, -4));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getTexturedModelDataTemp(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation)
				.uv(0, 32).cuboid(-4, -7, -3, 8, 7, 6, new Dilation(-0.1F)), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4, 0, -2, 8, 12, 4, dilation)
				.uv(28, 34).cuboid(-2, 8, -2, 4, 4, 4, new Dilation(-0.2F))
				.uv(16, 45).cuboid(-4, 0, -2, 8, 12, 4, new Dilation(-0.1F)), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation)
				.uv(0, 26).cuboid(-1, 0.1F, -2, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 51).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(-2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation)
				.uv(0, 26).cuboid(-1, 0.1F, -2, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 51).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation)
				.uv(0, 26).cuboid(-1, 0.1F, 0, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 45).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(-2, 18, -4));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation)
				.uv(0, 26).cuboid(-1, 0.1F, 0, 2, 4, 2, new Dilation(-0.1F))
				.uv(0, 45).cuboid(-2, 3.9F, -2, 4, 2, 4, new Dilation(-0.1F)), ModelTransform.pivot(2, 18, -4));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation), ModelTransform.pivot(0, 6, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4, 0, -2, 8, 12, 4, dilation), ModelTransform.pivot(0, 6, 0));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 6, 4, dilation);
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(2, 18, 4));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-2, 18, -4));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(2, 18, -4));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
		this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
		this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;
		this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;
		this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
	}
}

