package fun.wich.mixins.entity.hostile;

import fun.wich.entity.WaterConversionEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin implements WaterConversionEntity {
	public boolean canConvertInWater() { return true; }
	@Inject(method="isShaking", at=@At("HEAD"), cancellable = true)
	public void IsShaking(CallbackInfoReturnable<Boolean> cir) { if (this.isConvertingInWater()) cir.setReturnValue(true); }
}
