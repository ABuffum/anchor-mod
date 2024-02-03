package fun.wich.mixins.entity.ai;

import fun.wich.util.GoatUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.RamImpactTask;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

@Mixin(RamImpactTask.class)
public abstract class RamImpactTaskMixin<E extends PathAwareEntity> {
	@Shadow
	@Final
	private TargetPredicate targetPredicate;
	@Shadow
	@Final
	private ToDoubleFunction<E> strengthMultiplierFactory;

	protected RamImpactTaskMixin() { }
	@Shadow
	protected abstract void finishRam(ServerWorld world, E entity);

	@Shadow
	@Final
	private Function<E, SoundEvent> soundFactory;

	@Shadow
	private Vec3d direction;

	@Inject(method="keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/mob/PathAwareEntity;J)V", at = @At("HEAD"), cancellable = true)
	protected void keepRunning(ServerWorld serverWorld, E goatEntity, long l, CallbackInfo ci) {
		List<LivingEntity> list = serverWorld.getTargets(LivingEntity.class, this.targetPredicate, goatEntity, goatEntity.getBoundingBox());
		Brain<?> brain = goatEntity.getBrain();
		if (!list.isEmpty()) {
			LivingEntity livingEntity = list.get(0);
			livingEntity.damage(DamageSource.mob(goatEntity).setNeutral(), (float)goatEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
			int i = goatEntity.hasStatusEffect(StatusEffects.SPEED) ? goatEntity.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1 : 0;
			int j = goatEntity.hasStatusEffect(StatusEffects.SLOWNESS) ? goatEntity.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier() + 1 : 0;
			float g = MathHelper.clamp(goatEntity.getMovementSpeed() * 1.65f, 0.2f, 3.0f) + (0.25f * (float)(i - j));
			float h = livingEntity.blockedByShield(DamageSource.mob(goatEntity)) ? 0.5f : 1.0f;
			livingEntity.takeKnockback((double)(h * g) * this.strengthMultiplierFactory.applyAsDouble(goatEntity), this.direction.getX(), this.direction.getZ());
			this.finishRam(serverWorld, goatEntity);
			serverWorld.playSoundFromEntity(null, goatEntity, this.soundFactory.apply(goatEntity), SoundCategory.HOSTILE, 1.0f, 1.0f);
		}
		else if (GoatUtil.shouldSnapHorn(serverWorld, goatEntity)) {
			serverWorld.playSoundFromEntity(null, goatEntity, this.soundFactory.apply(goatEntity), SoundCategory.HOSTILE, 1.0f, 1.0f);
			if (GoatUtil.dropHorn(goatEntity)) serverWorld.playSoundFromEntity(null, goatEntity, GoatUtil.getHornBreakSound((GoatEntity)goatEntity), SoundCategory.HOSTILE, 1.0f, 1.0f);
			this.finishRam(serverWorld, goatEntity);
		}
		else {
			Optional<WalkTarget> optional = brain.getOptionalMemory(MemoryModuleType.WALK_TARGET);
			Optional<Vec3d> optional2 = brain.getOptionalMemory(MemoryModuleType.RAM_TARGET);
			if (optional.isEmpty() || optional2.isEmpty() || optional.get().getLookTarget().getPos().isInRange(optional2.get(), 0.25)) this.finishRam(serverWorld, goatEntity);
		}
		ci.cancel();
	}
}
