package fun.wich.mixins.entity.projectile;

import fun.wich.entity.projectile.GravityEnchantmentCarrier;
import fun.wich.entity.projectile.RicochetEnchantmentCarrier;
import fun.wich.entity.projectile.WaterDragControllable;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin implements WaterDragControllable, GravityEnchantmentCarrier, RicochetEnchantmentCarrier {
	private float waterDrag = Float.NaN;
	@Inject(method="getDragInWater", at=@At("HEAD"), cancellable=true)
	private void OverrideGetDragInWater(CallbackInfoReturnable<Float> cir) {
		if (!Float.isNaN(waterDrag)) cir.setReturnValue(waterDrag);
	}
	@Override public void setDragInWater(float drag) { this.waterDrag = drag; }

	private int gravityLevel = 0;
	@Override public int getGravityLevel() { return this.gravityLevel; }
	@Override public void setGravityLevel(int level) { this.gravityLevel = level; }

	private int ricochetLevel = 0;
	@Override public int getRicochetLevel() { return this.ricochetLevel; }
	@Override public void setRicochetLevel(int level) { this.ricochetLevel = level; }

	@Inject(method="onBlockHit", at=@At(value="INVOKE", target="Lnet/minecraft/util/hit/BlockHitResult;getPos()Lnet/minecraft/util/math/Vec3d;"), cancellable=true)
	private void BounceTest(BlockHitResult blockHitResult, CallbackInfo ci) {
		int ricochet = getRicochetLevel();
		PersistentProjectileEntity ppe = (PersistentProjectileEntity)(Object)this;
		if (ricochet > 0) {
			Vec3d velocity = ppe.getVelocity();
			double magnitude = velocity.lengthSquared();
			if (magnitude < 0.36) return; //Insufficient speed to bounce
			Vec3d vec = new Vec3d(blockHitResult.getSide().getUnitVector());
			vec = vec.multiply(velocity.dotProduct(vec) * 2).normalize();
			ppe.setVelocity(velocity.subtract(vec).multiply(Math.sqrt(magnitude) * 0.6));
			setRicochetLevel(ricochet - 1);
			ci.cancel();
		}
	}
}
