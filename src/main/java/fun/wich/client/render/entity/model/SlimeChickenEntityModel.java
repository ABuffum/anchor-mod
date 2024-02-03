package fun.wich.client.render.entity.model;

import fun.wich.entity.passive.chicken.SlimeChickenEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

public class SlimeChickenEntityModel extends ChickenEntityModel<SlimeChickenEntity> {

	public SlimeChickenEntityModel(ModelPart root) { super(root); }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 10).cuboid(-1, -5, -1, 2, 4, 1), ModelTransform.pivot(0, 15, -4));
	//	modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2, -6, -2, 4, 6, 3)
	//			.uv(0, 10).cuboid(-1, -5, -1, 2, 4, 1), ModelTransform.pivot(0, 15, -4));
		//modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2, -6, -2, 4, 6, 3), ModelTransform.pivot(0, 15, -4));
		modelPartData.addChild(EntityModelPartNames.BEAK, ModelPartBuilder.create().uv(14, 0).cuboid(-2, -4, -4, 4, 2, 2), ModelTransform.pivot(0, 15, -4));
		modelPartData.addChild(RED_THING, ModelPartBuilder.create().uv(14, 4).cuboid(-1, -2, -3, 2, 2, 2), ModelTransform.pivot(0, 15, -4));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(38, 13).cuboid(-2F, -3F, -2F, 4F, 6F, 4F), ModelTransform.of(0, 16, 0, 1.5707964f, 0, 0));
	//	modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 9).cuboid(-3F, -4F, -3F, 6F, 8F, 6F)
	//			.uv(38, 13).cuboid(-2F, -3F, -2F, 4F, 6F, 4F), ModelTransform.of(0, 16, 0, 1.5707964f, 0, 0));
		//modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 9).cuboid(-3, -4, -3, 6, 8, 6), ModelTransform.of(0, 16, 0, 1.5707964f, 0, 0));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(26, 0).cuboid(-1, 0, -3, 3, 5, 3);
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, modelPartBuilder, ModelTransform.pivot(-2, 19, 1));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, modelPartBuilder, ModelTransform.pivot(1, 19, 1));
			modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(0, 0, -3, 0, 0, 0), ModelTransform.pivot(-4, 13, 0));
		//modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(0, 0, -3, 1, 4, 6), ModelTransform.pivot(-4, 13, 0));
			modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(-1, 0, -3, 0, 0, 0), ModelTransform.pivot(4, 13, 0));
		//modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(-1, 0, -3, 1, 4, 6), ModelTransform.pivot(4, 13, 0));
		return TexturedModelData.of(modelData, 64, 32);
	}

	public static TexturedModelData getOuterTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2, -6, -2, 4, 6, 3), ModelTransform.pivot(0, 15, -4));
			modelPartData.addChild(EntityModelPartNames.BEAK, ModelPartBuilder.create().uv(14, 0).cuboid(-2, -4, -4, 0, 0, 0), ModelTransform.pivot(0, 15, -4));
			modelPartData.addChild(RED_THING, ModelPartBuilder.create().uv(14, 4).cuboid(-1, -2, -3, 0, 0, 0), ModelTransform.pivot(0, 15, -4));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 9).cuboid(-3, -4, -3, 6, 8, 6), ModelTransform.of(0, 16, 0, 1.5707964f, 0, 0));
			ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(26, 0).cuboid(-1, 0, -3, 0, 0, 0);
			modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, modelPartBuilder, ModelTransform.pivot(-2, 19, 1));
			modelPartData.addChild(EntityModelPartNames.LEFT_LEG, modelPartBuilder, ModelTransform.pivot(1, 19, 1));
		modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(0, 0, -3, 1, 4, 6), ModelTransform.pivot(-4, 13, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(-1, 0, -3, 1, 4, 6), ModelTransform.pivot(4, 13, 0));
		return TexturedModelData.of(modelData, 64, 32);
	}
}