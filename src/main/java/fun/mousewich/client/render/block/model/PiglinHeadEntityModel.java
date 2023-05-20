package fun.mousewich.client.render.block.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

@Environment(value= EnvType.CLIENT)
public class PiglinHeadEntityModel extends Model {
	private final ModelPart head;
	private final ModelPart leftEar;
	private final ModelPart rightEar;

	public PiglinHeadEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		this.head = root.getChild(EntityModelPartNames.HEAD);
		this.leftEar = this.head.getChild(EntityModelPartNames.LEFT_EAR);
		this.rightEar = this.head.getChild(EntityModelPartNames.RIGHT_EAR);
	}

	public static ModelData getModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelData2 = modelData.getRoot().addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0)
				.cuboid(-5.0f, -8.0f, -4.0f, 10.0f, 8.0f, 8.0f, Dilation.NONE).uv(31, 1)
				.cuboid(-2.0f, -4.0f, -5.0f, 4.0f, 4.0f, 1.0f, Dilation.NONE).uv(2, 4)
				.cuboid(2.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, Dilation.NONE).uv(2, 0)
				.cuboid(-3.0f, -2.0f, -5.0f, 1.0f, 2.0f, 1.0f, Dilation.NONE), ModelTransform.NONE);
		modelData2.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(51, 6).cuboid(0.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, Dilation.NONE), ModelTransform.of(4.5f, -6.0f, 0.0f, 0.0f, 0.0f, -0.5235988f));
		modelData2.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(39, 6).cuboid(-1.0f, 0.0f, -2.0f, 1.0f, 5.0f, 4.0f, Dilation.NONE), ModelTransform.of(-4.5f, -6.0f, 0.0f, 0.0f, 0.0f, 0.5235988f));
		return modelData;
	}
	public void setHeadRotation(float animationProgress, float yaw, float pitch) {
		this.head.yaw = yaw * ((float)Math.PI / 180);
		this.head.pitch = pitch * ((float)Math.PI / 180);
		this.leftEar.roll = (float)(-(Math.cos(animationProgress * (float)Math.PI * 0.2f * 1.2f) + 2.5)) * 0.2f;
		this.rightEar.roll = (float)(Math.cos(animationProgress * (float)Math.PI * 0.2f) + 2.5) * 0.2f;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.head.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
	public static TexturedModelData getTexturedModelData() {
		return TexturedModelData.of(PiglinHeadEntityModel.getModelData(), 64, 64);
	}
}
