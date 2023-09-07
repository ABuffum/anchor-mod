package fun.mousewich.client.render.entity.model;

import fun.mousewich.entity.passive.cow.SlimeCowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(value= EnvType.CLIENT)
public class SlimeCowEntityModel extends CowEntityModel<SlimeCowEntity> {
	public SlimeCowEntityModel(ModelPart root) { super(root); }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
				.uv(22, 40).cuboid(-3, -3, -5, 6, 6, 4)
				.uv(22, 0).cuboid(EntityModelPartNames.RIGHT_HORN, -5, -5, -4, 0, 0, 0)
				.uv(22, 0).cuboid(EntityModelPartNames.LEFT_HORN, 4, -5, -4, 0, 0, 0), ModelTransform.pivot(0, 4, -8));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create()
				.uv(52, 0).cuboid(-2, 2, -8, 4, 6, 1)
				.uv(36, 44).cuboid(-4, -8, -5, 8, 14, 6), ModelTransform.of(0, 5, 2, 1.5708F, 0, 0));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(28, 52).cuboid(-1, 1, -1, 2, 10, 2);
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-4, 12, 7));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(4, 12, 7));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-4, 12, -6));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(4, 12, -6));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static TexturedModelData getOuterTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
				.uv(0, 0).cuboid(-4, -4, -6, 8, 8, 6)
				.uv(22, 0).cuboid(EntityModelPartNames.RIGHT_HORN, -5, -5, -4, 1, 3, 1)
				.uv(22, 0).cuboid(EntityModelPartNames.LEFT_HORN, 4, -5, -4, 1, 3, 1), ModelTransform.pivot(0, 4, -8));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(18, 4).cuboid(-6, -10, -7, 12, 18, 10), ModelTransform.of(0, 5, 2, 1.5707964f, 0, 0));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 16).cuboid(-2, 0, -2, 4, 12, 4);
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-4, 12, 7));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(4, 12, 7));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-4, 12, -6));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(4, 12, -6));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public ModelPart getHead() { return this.head; }
}