package haven.mixins.entities.decoration;

import haven.events.HavenGameEvent;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorStandEntity.class)
public abstract class ArmorStandEntityMixin extends LivingEntity {

	protected ArmorStandEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="kill", at = @At("HEAD"))
	public void Kill(CallbackInfo ci) {
		((ArmorStandEntity)(Object)this).emitGameEvent(HavenGameEvent.ENTITY_DIE);
	}

	@Inject(method = "damage", at = @At("TAIL"))
	public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		ArmorStandEntity ase = (ArmorStandEntity)(Object)this;
		if (this.world.isClient || this.isRemoved()) return;
		if (DamageSource.OUT_OF_WORLD.equals(source)) return;
		if (this.isInvulnerableTo(source) || ase.isInvisible() || ase.isMarker()) return;
		if (source.isExplosive()) return;
		if (DamageSource.IN_FIRE.equals(source)) return;
		if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5f) return;
		boolean bl = source.getSource() instanceof PersistentProjectileEntity;
		if (!"player".equals(source.getName()) && !bl) return;
		if (source.getAttacker() instanceof PlayerEntity && !((PlayerEntity)source.getAttacker()).getAbilities().allowModifyWorld) return;
		if (source.isSourceCreativePlayer()) return;
		long l = this.world.getTime();
		if (!(l - ase.lastHitTime <= 5L || bl)) {
			this.emitGameEvent(HavenGameEvent.ENTITY_DAMAGE, source.getAttacker());
		}
	}

	@Inject(method="updateHealth", at = @At("TAIL"))
	private void updateHealth(DamageSource damageSource, float amount, CallbackInfo ci) {
		if (this.getHealth() - amount > 0.5f) this.emitGameEvent(HavenGameEvent.ENTITY_DAMAGE, damageSource.getAttacker());
	}
}
