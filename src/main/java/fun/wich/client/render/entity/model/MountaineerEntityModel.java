package fun.wich.client.render.entity.model;

import fun.wich.entity.hostile.illager.MountaineerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.IllagerEntityModel;

@Environment(value= EnvType.CLIENT)
public class MountaineerEntityModel extends IllagerEntityModel<MountaineerEntity> {

	public MountaineerEntityModel(ModelPart root) { super(root); }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -10, -4, 8, 10, 8), ModelTransform.pivot(0, 0, 0));
		modelPartData2.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4, -10, -4, 8, 12, 8, new Dilation(0.45f)), ModelTransform.NONE);
		modelPartData2.addChild(EntityModelPartNames.NOSE, ModelPartBuilder.create().uv(24, 0).cuboid(-1, -1, -6, 2, 4, 2), ModelTransform.pivot(0, -2, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create()
						.uv(16, 20).cuboid(-4, 0, -3, 8, 12, 6)
						.uv(0, 38).cuboid(-4, 0, -3, 8, 18, 6, new Dilation(0.5f))
						//Backpack
						.uv(32, 0).cuboid(-3.5F, 1, 3, 7, 11, 4)
						.uv(38, 15).cuboid(3.5F, 4, 3, 2, 6, 4)
						.uv(50, 17).cuboid(-5.5F, 6, 3, 2, 4, 4)
				, ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create()
						.uv(0, 22).cuboid(-2, 0, -2, 4, 12, 4)
						.uv(34, 47).mirrored().cuboid(-2, 5, -3, 4, 2, 1).mirrored(false)
						.uv(28, 38).mirrored().cuboid(-2, 7, -2, 4, 5, 4, new Dilation(0.1F)).mirrored(false)
				, ModelTransform.pivot(-2, 12, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create()
						.uv(0, 22).mirrored().cuboid(-2, 0, -2, 4, 12, 4)
						.uv(34, 47).cuboid(-2, 5, -3, 4, 2, 1)
						.uv(28, 38).cuboid(-2, 7, -2, 4, 5, 4, new Dilation(0.1F))
				, ModelTransform.pivot(2, 12, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create()
						.uv(40, 46).cuboid(-3, -2, -2, 4, 12, 4)
						.uv(44, 25).mirrored().cuboid(-3, -2, -2, 4, 4, 4, new Dilation(0.1F)).mirrored(false)
						.uv(44, 33).mirrored().cuboid(-3, -2.75F, -2.5F, 4, 5, 5, new Dilation(0.25F)).mirrored(false)
				, ModelTransform.pivot(-5, 2, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create()
						.uv(40, 46).mirrored().cuboid(-1, -2, -2, 4, 12, 4)
						.uv(44, 25).cuboid(-1, -2, -2, 4, 4, 4, new Dilation(0.1F))
						.uv(44, 33).cuboid(-1, -2.75F, -2.5F, 4, 5, 5, new Dilation(0.25F))
				, ModelTransform.pivot(5, 2, 0));

		ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.ARMS, ModelPartBuilder.create().uv(44, 22).cuboid(-8, -2, -2, 4, 8, 4).uv(40, 38).cuboid(-4, 2, -2, 8, 4, 4), ModelTransform.of(0, 3, -1, -0.75f, 0, 0));
		modelPartData3.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4, -2, -2, 4, 8, 4), ModelTransform.NONE);

		return TexturedModelData.of(modelData, 64, 64);
	}
}
