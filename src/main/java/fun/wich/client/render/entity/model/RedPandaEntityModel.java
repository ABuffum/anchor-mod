package fun.wich.client.render.entity.model;

import fun.wich.entity.passive.RedPandaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class RedPandaEntityModel extends EntityModel<RedPandaEntity> {
	private final ModelPart bb_main;
	private final ModelPart head;
	private final ModelPart frontRightLeg;
	private final ModelPart frontLeftLeg;
	private final ModelPart backRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart tail;
	public RedPandaEntityModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.frontRightLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.frontLeftLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.backRightLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.backLeftLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.tail = root.getChild(EntityModelPartNames.TAIL);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -4.0F, -7.0F, 10.0F, 7.0F, 7.0F)
				.uv(53, 20).cuboid(-2.0F, 1.0F, -8.0F, 4.0F, 2.0F, 1.0F)
				.uv(53, 12).cuboid(-6.0F, -5.0F, -6.0F, 4.0F, 2.0F, 1.0F)
				.uv(53, 10).cuboid(-5.0F, -6.0F, -6.0F, 3.0F, 1.0F, 1.0F)
				.uv(53, 17).cuboid(2.0F, -5.0F, -6.0F, 4.0F, 2.0F, 1.0F)
				.uv(53, 15).cuboid(2.0F, -6.0F, -6.0F, 3.0F, 1.0F, 1.0F), ModelTransform.pivot(0.0F, 13.0F, -6.0F));
		ModelPartData frontrightleg = modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(34, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(-2.5F, 18.0F, -4.5F));
		ModelPartData frontleftleg = modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 14).cuboid(3.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(-2.5F, 18.0F, -4.5F));
		ModelPartData backrightleg = modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(31, 11).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(-2.5F, 18.0F, 3.5F));
		ModelPartData backleftleg = modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(48, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), ModelTransform.pivot(2.5F, 18.0F, 3.5F));
		ModelPartData tail = modelPartData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(34, 12).cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 11.0F), ModelTransform.pivot(0.0F, 13.0F, 5.0F));
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 14).cuboid(-4.0F, -13.0F, -8.0F, 8.0F, 7.0F, 13.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(RedPandaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;
		this.frontRightLeg.pitch = this.backRightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.frontLeftLeg.pitch = this.backLeftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.tail.pitch = -0.05235988F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		if (child) {
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0, 1.5F, 0);
		}
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		frontRightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		frontLeftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		backRightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		backLeftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}
}
