package fun.mousewich.mixins.client.render.entity.model;

import fun.mousewich.haven.entity.AngelBatEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BatEntityModel.class)
public class BatEntityModelMixin {
	@Shadow @Final private ModelPart head;
	@Shadow @Final private ModelPart rightWing;
	@Shadow @Final private ModelPart leftWing;
	@Shadow @Final private ModelPart body;
	@Shadow @Final private ModelPart rightWingTip;
	@Shadow @Final private ModelPart leftWingTip;
	@Inject(method="setAngles(Lnet/minecraft/entity/passive/BatEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
	public void SetAngles(BatEntity batEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		if (batEntity instanceof AngelBatEntity) {
			this.head.pitch = j * 0.017453292F;
			this.head.yaw = i * 0.017453292F;
			this.head.roll = 0.0F;
			this.head.setPivot(0.0F, 0.0F, 0.0F);
			this.rightWing.setPivot(0.0F, 0.0F, 0.0F);
			this.leftWing.setPivot(0.0F, 0.0F, 0.0F);
			this.body.pitch = 0.7853982F + MathHelper.cos(h * 0.1F) * 0.15F;
			this.body.yaw = 0.0F;
			this.rightWing.yaw = MathHelper.cos(h * 74.48451F * 0.017453292F) * 3.1415927F * 0.25F;
			this.leftWing.yaw = -this.rightWing.yaw;
			this.rightWingTip.yaw = this.rightWing.yaw * 0.5F;
			this.leftWingTip.yaw = -this.rightWing.yaw * 0.5F;
			ci.cancel();
		}
	}
}
