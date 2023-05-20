package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.camel.CamelEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin extends AnimalEntity implements InventoryChangedListener, JumpingMount, Saddleable, EntityWithBloodType {
	protected HorseBaseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }
	@Shadow
	private int angryTicks;
	@Shadow
	protected int soundTicks;
	@Shadow
	protected float jumpStrength;

	@Inject(method="updateAnger", at = @At("HEAD"), cancellable = true)
	private void updateAnger(CallbackInfo ci) {
		//noinspection ConstantConditions
		if ((Object)this instanceof CamelEntity camel) {
			if (this.canMoveVoluntarily()) {
				this.angryTicks = 1;
				camel.setAngry(true);
			}
			ci.cancel();
		}
	}
	@Inject(method="travel", at = @At("HEAD"), cancellable = true)
	public void travel(Vec3d movementInput, CallbackInfo ci) {
		//noinspection ConstantConditions
		if (!((Object)this instanceof CamelEntity camel)) return;
		if (!this.isAlive()) {
			ci.cancel();
			return;
		}
		LivingEntity livingEntity = camel.getPrimaryPassenger();
		if (!this.hasPassengers() || livingEntity == null || camel.ignoresMovementInput(livingEntity)) {
			this.flyingSpeed = 0.02f;
			super.travel(movementInput);
			ci.cancel();
			return;
		}
		this.setRotation(livingEntity.getYaw(), livingEntity.getPitch() * 0.5f);
		this.bodyYaw = this.headYaw = this.getYaw();
		this.prevYaw = this.headYaw;
		float f = livingEntity.sidewaysSpeed * 0.5f;
		float g = livingEntity.forwardSpeed;
		if (g <= 0.0f) {
			g *= 0.25f;
			this.soundTicks = 0;
		}
		if (this.onGround && this.jumpStrength == 0.0f && camel.isAngry() && !this.jumping) {
			f = 0.0f;
			g = 0.0f;
		}
		if (this.jumpStrength > 0.0f && !camel.isInAir() && this.onGround) {
			camel.jump(this.jumpStrength);
			this.jumpStrength = 0.0f;
		}
		this.flyingSpeed = this.getMovementSpeed() * 0.1f;
		if (this.isLogicalSideForUpdatingMovement()) {
			this.setMovementSpeed(camel.getHorsebackMovementSpeed(livingEntity));
			super.travel(new Vec3d(f, movementInput.y, g));
		}
		else if (livingEntity instanceof PlayerEntity) this.setVelocity(this.getX() - this.lastRenderX, this.getY() - this.lastRenderY, this.getZ() - this.lastRenderZ);
		if (this.onGround) {
			this.jumpStrength = 0.0f;
			camel.setInAir(false);
		}
		this.updateLimbs(this, false);
		this.tryCheckBlockCollision();
		ci.cancel();
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.HORSE_BLOOD_TYPE; }
}
