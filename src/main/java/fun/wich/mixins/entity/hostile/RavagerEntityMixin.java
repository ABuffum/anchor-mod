package fun.wich.mixins.entity.hostile;

import fun.wich.ModBase;
import fun.wich.entity.RavagerRideableCompatibilityHook;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.event.ModGameEvent;
import fun.wich.origins.power.PowersUtil;
import fun.wich.origins.power.RideRavagersPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends RaiderEntity implements EntityWithBloodType, RavagerRideableCompatibilityHook {
	protected RavagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="roar", at = @At("TAIL"))
	private void roar(CallbackInfo ci) { if (this.isAlive()) this.emitGameEvent(ModGameEvent.ENTITY_ROAR); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.RAVAGER_BLOOD_TYPE; }

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (!this.hasPassengers() && !player.shouldCancelInteraction() && PowersUtil.Active(player, RideRavagersPower.class)) {
			if (!this.world.isClient) player.startRiding(this);
			return ActionResult.success(this.world.isClient);
		}
		else return super.interactMob(player, hand);
	}

	@Override
	public boolean RavagerRidableCompatibleTravel(Vec3d movementInput) {
		if (this.isAlive()) {
			if (this.hasPassengers() && this.getPrimaryPassenger() instanceof LivingEntity entity && PowersUtil.Active(entity, RideRavagersPower.class)) {
				this.setYaw(entity.getYaw());
				this.prevYaw = this.getYaw();
				this.setPitch(entity.getPitch() * 0.5F);
				this.setRotation(this.getYaw(), this.getPitch());
				this.bodyYaw = this.getYaw();
				this.headYaw = this.bodyYaw;
				float f = entity.sidewaysSpeed * 0.5F;
				float g = entity.forwardSpeed;
				this.flyingSpeed = this.getMovementSpeed() * 0.1F;
				if (this.isLogicalSideForUpdatingMovement()) {
					this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / 2.0F);
					super.travel(new Vec3d(f, movementInput.y, g));
				}
				else if (entity instanceof PlayerEntity) this.setVelocity(Vec3d.ZERO);
				this.updateLimbs(this, false);
				return true;
			}
		}
		return false;
	}
}
