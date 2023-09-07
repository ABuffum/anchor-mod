package fun.mousewich.mixins.client.render.entity.model;

import fun.mousewich.util.GoatUtil;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.GoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GoatEntityModel.class)
public abstract class GoatEntityModelMixin<T extends GoatEntity> extends QuadrupedEntityModel<T> {
	protected GoatEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
		super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
	}

	@Inject(method="setAngles(Lnet/minecraft/entity/passive/GoatEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
	public void setAngles(T goatEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		this.head.getChild(EntityModelPartNames.LEFT_HORN).visible = GoatUtil.hasLeftHorn(goatEntity);
		this.head.getChild(EntityModelPartNames.RIGHT_HORN).visible = GoatUtil.hasRightHorn(goatEntity);
		super.setAngles(goatEntity, f, g, h, i, j);
		float k = goatEntity.method_36283();
		if (k != 0.0F) this.head.pitch = k;
		ci.cancel();
	}
}
