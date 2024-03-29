package fun.wich.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import fun.wich.entity.passive.chicken.FancyChickenEntity;
import fun.wich.mixins.client.render.entity.model.ChickenEntityModelAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;

@Environment(EnvType.CLIENT)
public class FancyChickenEntityModel<T extends FancyChickenEntity> extends ChickenEntityModel<T> {

	private final ModelPart tail;
	private final ModelPart crest;

	public FancyChickenEntityModel(ModelPart root) {
		super(root);
		this.tail = root.getChild(EntityModelPartNames.TAIL);
		this.crest = root.getChild(ModEntityModelPartNames.CREST);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -8.0F, -2.0F, 4.0F, 6.0F, 3.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild(EntityModelPartNames.BEAK, ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -6.0F, -4.0F, 4.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild(RED_THING, ModelPartBuilder.create().uv(14, 4).cuboid(-0.5F, -4.0F, -3.0F, 1.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 6.0F), ModelTransform.of(0.0F, 16.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, -2.0F, -3.0F, 3.0F, 7.0F, 3.0F);
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, modelPartBuilder, ModelTransform.pivot(-2.0F, 19.0F, 1.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, modelPartBuilder, ModelTransform.pivot(1.0F, 19.0F, 1.0F));
		modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(-4.0F, 11.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(4.0F, 11.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(48, 15).cuboid(-1.0F, -12.0F, 8.0F, 1.0F, 10.0F, 7.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild(ModEntityModelPartNames.CREST, ModelPartBuilder.create().uv(48, 0).cuboid(-1.0F, -12.0F, -3.0F, 1.0F, 5.0F, 5.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		return TexturedModelData.of(modelData, 64, 32);

	}

	protected Iterable<ModelPart> getHeadParts() {
		return new ImmutableList.Builder<ModelPart>().addAll(super.getHeadParts()).add(this.crest).build();
	}

	protected Iterable<ModelPart> getBodyParts() {
		return new ImmutableList.Builder<ModelPart>().addAll(super.getBodyParts()).add(this.tail).build();
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
		ModelPart head = ((ChickenEntityModelAccessor)this).getHead();
		this.crest.pitch = head.pitch;
		this.crest.yaw = head.yaw;
	}
}
