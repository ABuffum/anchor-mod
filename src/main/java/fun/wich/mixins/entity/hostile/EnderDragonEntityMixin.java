package fun.wich.mixins.entity.hostile;

import fun.wich.ModBase;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.event.ModGameEvent;
import fun.wich.origins.power.DragonNeutralityPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntity implements Monster, EntityWithBloodType {
	protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="kill", at = @At("HEAD"))
	public void Kill(CallbackInfo ci) { this.emitGameEvent(ModGameEvent.ENTITY_DIE); }

	@Inject(method="updatePostDeath", at = @At("TAIL"))
	protected void updatePostDeath(CallbackInfo ci) {
		if (((EnderDragonEntity)(Object)this).ticksSinceDeath == 200 && this.world instanceof ServerWorld) {
			this.emitGameEvent(ModGameEvent.ENTITY_DIE);
		}
	}

	@Shadow @Final private EnderDragonPart body;
	@Shadow @Final private PhaseManager phaseManager;

	@Inject(method="launchLivingEntities", at = @At("HEAD"), cancellable = true)
	private void LaunchLivingEntities(List<Entity> entities, CallbackInfo ci) {
		if (entities.stream().anyMatch(DragonNeutralityPower::ShouldNotLaunch)) {
			double d = (this.body.getBoundingBox().minX + this.body.getBoundingBox().maxX) / 2.0;
			double e = (this.body.getBoundingBox().minZ + this.body.getBoundingBox().maxZ) / 2.0;
			for (Entity entity : entities) {
				if (!(entity instanceof LivingEntity)) continue;
				if (DragonNeutralityPower.ShouldNotLaunch(entity)) continue;
				double f = entity.getX() - d;
				double g = entity.getZ() - e;
				double h = Math.max(f * f + g * g, 0.1);
				entity.addVelocity(f / h * 4.0, 0.2f, g / h * 4.0);
				if (this.phaseManager.getCurrent().isSittingOrHovering() || ((LivingEntity)entity).getLastAttackedTime() >= entity.age - 2) continue;
				entity.damage(DamageSource.mob(this), 5.0f);
				this.applyDamageEffects(this, entity);
			}
			ci.cancel();
		}
	}
	@Inject(method="damageLivingEntities", at = @At("HEAD"), cancellable = true)
	private void DamageLivingEntities(List<Entity> entities, CallbackInfo ci) {
		if (entities.stream().anyMatch(DragonNeutralityPower::ShouldNotDamage)) {
			for (Entity entity : entities) {
				if (!(entity instanceof LivingEntity)) continue;
				if (DragonNeutralityPower.ShouldNotDamage(entity)) continue;
				entity.damage(DamageSource.mob(this), 10.0f);
				this.applyDamageEffects(this, entity);
			}
			ci.cancel();
		}
	}
	@Override public BloodType GetDefaultBloodType() { return ModBase.ENDER_DRAGON_BLOOD_TYPE; }
}
