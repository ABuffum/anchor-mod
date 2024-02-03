package fun.wich.client.render.entity.model;

import fun.wich.entity.passive.sheep.RainbowSheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;

@Environment(value= EnvType.CLIENT)
public class RainbowSheepEntityModel extends QuadrupedEntityModel<RainbowSheepEntity> {
	private float headPitchModifier;
	public RainbowSheepEntityModel(ModelPart root) { super(root, false, 8.0f, 4.0f, 2.0f, 2.0f, 24); }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = QuadrupedEntityModel.getModelData(12, Dilation.NONE);
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-3.0f, -4.0f, -6.0f, 6.0f, 6.0f, 8.0f), ModelTransform.pivot(0.0f, 6.0f, -8.0f));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(32, 8).cuboid(-4.0f, -10.0f, -7.0f, 8.0f, 16.0f, 6.0f), ModelTransform.of(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(16, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12, 4.0f, Dilation.NONE);
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-3.0f, 24 - 12, -5.0f));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(3.0f, 24 - 12, -5.0f));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void animateModel(RainbowSheepEntity entity, float f, float g, float h) {
		super.animateModel(entity, f, g, h);
		this.head.pivotY = 6.0f + (entity).getNeckAngle(h) * 9.0f;
		this.headPitchModifier = (entity).getHeadAngle(h);
	}

	@Override
	public void setAngles(RainbowSheepEntity entity, float f, float g, float h, float i, float j) {
		super.setAngles(entity, f, g, h, i, j);
		this.head.pitch = this.headPitchModifier;
	}
}