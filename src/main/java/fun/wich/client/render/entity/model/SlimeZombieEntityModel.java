package fun.wich.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.mob.ZombieEntity;


@Environment(value=EnvType.CLIENT)
public class SlimeZombieEntityModel<T extends ZombieEntity> extends ZombieEntityModel<T> {
	public SlimeZombieEntityModel(ModelPart modelPart) { super(modelPart); }

	private static final TexturedModelData texturedModelData4 = TexturedModelData.of(getOuterModelData(new Dilation(0.5f)), 64, 32);
	public static TexturedModelData getArmorModelData() { return texturedModelData4; }
	public static TexturedModelData getInnerModelData() { return getInnerModelData(Dilation.NONE); }
	public static TexturedModelData getOuterModelData() { return TexturedModelData.of(getOuterModelData(Dilation.NONE), 64, 64); }
	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = getModelData(dilation, 0);
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static ModelData getOuterModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation.add(0.5f)), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 16).cuboid(-4, 0, -2, 8, 12, 4, dilation), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1, -2, -2, 4, 12, 4, dilation).mirrored(false), ModelTransform.pivot(5, 2, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-3, -2, -2, 4, 12, 4, dilation), ModelTransform.pivot(-5, 2, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-1.9F, 0, -2, 4, 12, 4, dilation).mirrored(false), ModelTransform.pivot(1.9F, 12, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.1F, 0, -2, 4, 12, 4, dilation), ModelTransform.pivot(-1.9F, 12, 0));
		return modelData;
	}
	public static TexturedModelData getInnerModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 32).cuboid(-4, -8, -4, 8, 8, 8, dilation.add(-0.1F)), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create().uv(32, 0).cuboid(-4, -8, -4, 8, 8, 8, dilation.add(0.4F)), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(16, 48).cuboid(-4, 0, -2, 8, 12, 4, dilation.add(-0.1F)), ModelTransform.pivot(0, 0, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(44, 50).mirrored().cuboid(0, -2, -1, 2, 12, 2, dilation.add(-0.1F)).mirrored(false), ModelTransform.pivot(5, 2, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(44, 50).mirrored().cuboid(-2, -2, -1, 2, 12, 2, dilation.add(-0.1F)).mirrored(false), ModelTransform.pivot(-5, 2, 0));
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(4, 50).mirrored().cuboid(-0.9F, 0, -1, 2, 12, 2, dilation.add(-0.1F)).mirrored(false), ModelTransform.pivot(1.9F, 12, 0));
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(4, 50).mirrored().cuboid(-1.1F, 0, -1, 2, 12, 2, dilation.add(-0.1F)).mirrored(false), ModelTransform.pivot(-1.9F, 12, 0));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T hostileEntity, float f, float g, float h, float i, float j) {
		super.setAngles(hostileEntity, f, g, h, i, j);
		CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, this.isAttacking(hostileEntity), this.handSwingProgress, h);
	}
	@Override
	public boolean isAttacking(T zombieEntity) { return (zombieEntity).isAttacking(); }
}