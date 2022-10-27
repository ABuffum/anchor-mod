package haven.rendering.models;

import haven.entities.passive.RaccoonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RaccoonEntityModel extends EntityModel<RaccoonEntity> {
	private final ModelPart head;
	private final ModelPart frontRightLeg;
	private final ModelPart frontLeftLeg;
	private final ModelPart backRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart tail;
	private final ModelPart bb_main;
	public RaccoonEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.frontRightLeg = root.getChild("frontrightleg");
		this.frontLeftLeg = root.getChild("frontleftleg");
		this.backRightLeg = root.getChild("backrightleg");
		this.backLeftLeg = root.getChild("backleftleg");
		this.tail = root.getChild("tail");
		this.bb_main = root.getChild("bb_main");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -4.0F, -7.0F, 10.0F, 7.0F, 7.0F)
		.uv(42, 27).cuboid(-2.0F, 1.0F, -9.0F, 4.0F, 2.0F, 2.0F)
		.uv(54, 13).cuboid(-5.0F, -6.0F, -6.0F, 2.0F, 2.0F, 1.0F)
		.uv(54, 16).cuboid(3.0F, -6.0F, -6.0F, 2.0F, 2.0F, 1.0F), ModelTransform.pivot(0.0F, 13.0F, -6.0F));

		ModelPartData frontrightleg = modelPartData.addChild("frontrightleg", ModelPartBuilder.create().uv(36, 1).cuboid(-0.5F, 0.0F, -1.5F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(-2.5F, 18.0F, -4.5F));

		ModelPartData frontleftleg = modelPartData.addChild("frontleftleg", ModelPartBuilder.create().uv(2, 15).cuboid(3.5F, 0.0F, -1.5F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(-2.5F, 18.0F, -4.5F));

		ModelPartData backrightleg = modelPartData.addChild("backrightleg", ModelPartBuilder.create().uv(33, 12).cuboid(-0.5F, 0.0F, -1.5F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(-2.5F, 18.0F, 3.5F));

		ModelPartData backleftleg = modelPartData.addChild("backleftleg", ModelPartBuilder.create().uv(50, 1).cuboid(-1.5F, 0.0F, -1.5F, 2.0F, 6.0F, 2.0F), ModelTransform.pivot(2.5F, 18.0F, 3.5F));

		ModelPartData tail = modelPartData.addChild("tail", ModelPartBuilder.create().uv(34, 12).cuboid(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 11.0F), ModelTransform.pivot(0.0F, 13.0F, 5.0F));

		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 14).cuboid(-4.0F, -13.0F, -8.0F, 8.0F, 7.0F, 13.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(RaccoonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;
		this.frontRightLeg.pitch = this.backRightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.frontLeftLeg.pitch = this.backLeftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.tail.pitch = -0.1F;
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