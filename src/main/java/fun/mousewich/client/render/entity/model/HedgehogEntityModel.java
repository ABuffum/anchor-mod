package fun.mousewich.client.render.entity.model;

import fun.mousewich.entity.passive.HedgehogEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class HedgehogEntityModel extends EntityModel<HedgehogEntity> {
	private final ModelPart body;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	public HedgehogEntityModel(ModelPart root) {
		this.body = root.getChild(EntityModelPartNames.BODY);
		this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		/*body*/ modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0, -4.0F, 5.0F, 5.0F, 8.0F)
				.uv(4, 4).cuboid(-3.0F, 0, -3.0F, 1.0F, 2.0F, 0.0F)
				.uv(4, 2).cuboid(3.0F, 0, -3.0F, 1.0F, 2.0F, 0.0F)
				.uv(0, 0).cuboid(-1.0F, 4, -5.0F, 3.0F, 1.0F, 1.0F), ModelTransform.pivot(0, 17, 0));
		/*rightFrontLeg*/ modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(2, 2).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(2.0F, 21.0F, 3.0F));
		/*leftFrontLeg*/ modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 2).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(-2.0F, 21.0F, 3.0F));
		/*rightHindLeg*/ modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(2, 5).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(2.0F, 21.0F, -3.0F));
		/*leftHindLeg*/ modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 5).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(-2.0F, 21.0F, -3.0F));
		return TexturedModelData.of(modelData, 32, 16);
	}
	@Override
	public void setAngles(HedgehogEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.rightFrontLeg.pitch = this.rightHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftFrontLeg.pitch = this.leftHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		if (child) {
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0, 1.5F, 0);
		}
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightFrontLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftFrontLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightHindLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftHindLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}
}