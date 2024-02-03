package fun.wich.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(value=EnvType.CLIENT)
public class JavelinEntityModel extends Model {
	private final ModelPart root;

	public JavelinEntityModel(ModelPart root) {
		super(RenderLayer::getEntitySolid);
		this.root = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("body",
				ModelPartBuilder.create()
						//javelin body
						.uv(0, 0).cuboid(-0.5f, -4, -0.5f, 1, 31, 1)
						.uv(4, 0).cuboid(-1.5f, 0, -0.5f, 3, 4, 1)
						.uv(4, 5).cuboid(-2.5f, 2, -0.5f, 1, 1, 1)
						.uv(8, 5).cuboid(1.5f, 2, -0.5f, 1, 1, 1)
						.uv(8, 7).cuboid(2.5f, 3, -0.5f, 1, 1, 1)
						.uv(4, 7).cuboid(-3.5f, 3, -0.5f, 1, 1, 1)
						.uv(4, 9).cuboid(-2.5f, 4, -0.5f, 1, 1, 1)
						.uv(8, 9).cuboid(1.5f, 4, -0.5f, 1, 1, 1)
						.uv(4, 11).cuboid(0.5f, 20, -0.5f, 2, 1, 1)
						.uv(8, 13).cuboid(1.5f, 21, -0.5f, 1, 4, 1)
						.uv(4, 18).cuboid(0.5f, 25, -0.5f, 2, 1, 1)
						//trident body & prongs
						.uv(12, 0).cuboid(-1.5F, 0, -0.5F, 3, 2, 1)
						.uv(12, 3).cuboid(-2.5F, -3, -0.5F, 1, 4, 1)
						.uv(12, 3).cuboid(1.5F, -3, -0.5F, 1, 4, 1)
				, ModelTransform.NONE);
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}

