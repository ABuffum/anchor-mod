package fun.wich.client.render.entity.model;

import fun.wich.entity.hostile.PiranhaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class PiranhaEntityModel extends EntityModel<PiranhaEntity> {
	private final ModelPart body;
	private final ModelPart jaw;
	private final ModelPart leftFin;
	private final ModelPart rightFin;
	private final ModelPart tail;
	public PiranhaEntityModel(ModelPart root) {
		this.body = root.getChild(EntityModelPartNames.BODY);
		this.jaw = this.body.getChild(EntityModelPartNames.JAW);
		this.leftFin = this.body.getChild(EntityModelPartNames.LEFT_FIN);
		this.rightFin = this.body.getChild(EntityModelPartNames.RIGHT_FIN);
		this.tail = this.body.getChild(EntityModelPartNames.TAIL);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -4.0F, -3.0F, 3.0F, 4.0F, 6.0F, new Dilation(0.0F))
				.uv(0, 14).cuboid(-1.5F, -6.0F, -2.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(-1.5F, -5.0F, 2.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 7).cuboid(0.0F, -7.0F, -1.0F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(1, 1).cuboid(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData leftFin = body.addChild(EntityModelPartNames.LEFT_FIN, ModelPartBuilder.create().uv(2, 3)
				.cuboid(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(1.5F, 0.0F, -2.0F, 0.0F, 0.3927F, 0.0F));
		ModelPartData rightFin = body.addChild(EntityModelPartNames.RIGHT_FIN, ModelPartBuilder.create().uv(0, 3)
				.cuboid(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(-1.5F, 0.0F, -2.0F, 0.0F, -0.3927F, 0.0F));
		ModelPartData jaw = body.addChild("jaw", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -3.0F));
		jaw.addChild(ModEntityModelPartNames.JAW_INNER, ModelPartBuilder.create().uv(12, 3).cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, new Dilation(0.0F))
				.uv(12, 0).cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		jaw.addChild(ModEntityModelPartNames.JAW_INNER, ModelPartBuilder.create().uv(12, 3).cuboid(-1.5F, -2.0F, 0.1F, 3.0F, 2.0F, 0.0F, new Dilation(0.0F))
				.uv(12, 0).cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		ModelPartData tail = body.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(8, 7)
				.cuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 3.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, -2.5F, 3.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(PiranhaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float f = 1.0f;
		if (!(entity).isTouchingWater()) {
			f = 1.5f;
		}
		this.leftFin.yaw = MathHelper.sin(0.5f * ageInTicks);
		this.rightFin.yaw = -leftFin.yaw;
		this.tail.yaw = -f * 0.45f * MathHelper.sin(0.6f * ageInTicks);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}