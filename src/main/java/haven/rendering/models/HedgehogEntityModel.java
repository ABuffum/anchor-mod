package haven.rendering.models;

import haven.entities.passive.HedgehogEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class HedgehogEntityModel extends EntityModel<HedgehogEntity> {
	private final ModelPart bb_main;
	private final ModelPart frontRightLeg;
	private final ModelPart frontLeftLeg;
	private final ModelPart backRightLeg;
	private final ModelPart backLeftLeg;
	public HedgehogEntityModel(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
		this.frontRightLeg = root.getChild("frontrightleg");
		this.frontLeftLeg = root.getChild("frontleftleg");
		this.backRightLeg = root.getChild("backrightleg");
		this.backLeftLeg = root.getChild("backleftleg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0, -4.0F, 5.0F, 5.0F, 8.0F)
				.uv(4, 4).cuboid(-3.0F, 0, -3.0F, 1.0F, 2.0F, 0.0F)
				.uv(4, 2).cuboid(3.0F, 0, -3.0F, 1.0F, 2.0F, 0.0F)
				.uv(0, 0).cuboid(-1.0F, 4, -5.0F, 3.0F, 1.0F, 1.0F), ModelTransform.pivot(0, 17, 0));
		ModelPartData frontrightleg = modelPartData.addChild("frontrightleg", ModelPartBuilder.create().uv(2, 2).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(2.0F, 21.0F, 3.0F));
		ModelPartData frontleftleg = modelPartData.addChild("frontleftleg", ModelPartBuilder.create().uv(0, 2).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(-2.0F, 21.0F, 3.0F));
		ModelPartData backrightleg = modelPartData.addChild("backrightleg", ModelPartBuilder.create().uv(2, 5).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(2.0F, 21.0F, -3.0F));
		ModelPartData backleftleg = modelPartData.addChild("backleftleg", ModelPartBuilder.create().uv(0, 5).cuboid(0, 0, 0, 1.0F, 3.0F, 0.0F), ModelTransform.pivot(-2.0F, 21.0F, -3.0F));
		return TexturedModelData.of(modelData, 32, 16);
	}
	@Override
	public void setAngles(HedgehogEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.frontRightLeg.pitch = this.backRightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.frontLeftLeg.pitch = this.backLeftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		if (child) {
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.translate(0, 1.5F, 0);
		}
		bb_main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		frontRightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		frontLeftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		backRightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		backLeftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}
}