package fun.wich.mixins.entity.projectile;

import fun.wich.entity.projectile.GravityEnchantmentCarrier;
import fun.wich.entity.projectile.WaterDragControllable;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin implements WaterDragControllable, GravityEnchantmentCarrier {
	private float waterDrag = Float.NaN;
	@Inject(method="getDragInWater", at=@At("HEAD"), cancellable=true)
	private void OverrideGetDragInWater(CallbackInfoReturnable<Float> cir) {
		if (!Float.isNaN(waterDrag)) cir.setReturnValue(waterDrag);
	}
	@Override public void setDragInWater(float drag) { this.waterDrag = drag; }

	private int gravityLevel = 0;
	@Override public int getGravityLevel() { return this.gravityLevel; }
	@Override public void setGravityLevel(int level) { this.gravityLevel = level; }
}
