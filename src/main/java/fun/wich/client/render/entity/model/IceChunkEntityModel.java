package fun.wich.client.render.entity.model;

import fun.wich.entity.IceChunkEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

@Environment(value= EnvType.CLIENT)
public class IceChunkEntityModel extends EntityModel<IceChunkEntity> {
	private final ModelPart body;
	public IceChunkEntityModel(ModelPart root) { this.body = root.getChild(EntityModelPartNames.BODY); }
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		modelData.getRoot().addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -24.0F, -16.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F))
				.uv(64, 0).cuboid(0.0F, -24.0F, -16.0F, 16.0F, 24.0F, 16.0F, new Dilation(0.0F))
				.uv(0, 56).cuboid(0.0F, -16.0F, 0.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F))
				.uv(0, 32).cuboid(-16.0F, -16.0F, 0.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F))
				.uv(64, 40).cuboid(0.0F, -24.0F, 0.0F, 16.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(IceChunkEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) { }
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.body.render(matrices, vertices, light, overlay, red, green, blue, alpha);

	}
}
