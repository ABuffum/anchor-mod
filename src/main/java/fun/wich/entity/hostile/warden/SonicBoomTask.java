package fun.wich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.wich.damage.ModEntityDamageSource;
import fun.wich.entity.ModEntityStatuses;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.particle.ModParticleTypes;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Unit;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SonicBoomTask extends Task<WardenEntity> {
	public static final int HORIZONTAL_RANGE = 15;
	public static final int VERTICAL_RANGE = 20;
	public static final int COOLDOWN = 40;
	private static final int SOUND_DELAY = MathHelper.ceil(34.0);
	private static final int RUN_TIME = MathHelper.ceil(60.0f);


	public SonicBoomTask() {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.SONIC_BOOM_COOLDOWN, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SONIC_BOOM_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, ModMemoryModules.SONIC_BOOM_SOUND_DELAY, MemoryModuleState.REGISTERED), RUN_TIME);
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity wardenEntity) {
		return wardenEntity.isInRange(wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get(), HORIZONTAL_RANGE, VERTICAL_RANGE);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) { return true; }

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		wardenEntity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
		wardenEntity.getBrain().remember(ModMemoryModules.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);
		serverWorld.sendEntityStatus(wardenEntity, ModEntityStatuses.SONIC_BOOM);
		wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_SONIC_CHARGE, 3.0f, 1.0f);
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> wardenEntity.getLookControl().lookAt(target.getPos()));
		if (wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.SONIC_BOOM_SOUND_DELAY)
				|| wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.SONIC_BOOM_SOUND_COOLDOWN)) return;
		wardenEntity.getBrain().remember(ModMemoryModules.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, RUN_TIME - SOUND_DELAY);
		wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(wardenEntity::isValidTarget)
				.filter(target -> wardenEntity.isInRange(target, HORIZONTAL_RANGE, VERTICAL_RANGE))
				.ifPresent(target -> SonicBoom(wardenEntity, target, serverWorld, 10.0f));
	}
	public static void SonicBoom(LivingEntity source, ServerWorld world, float damage) {
		world.playSound(null, source.getBlockPos(), ModSoundEvents.ENTITY_WARDEN_SONIC_CHARGE, SoundCategory.HOSTILE, 3.0f, 1.0f);
		HitResult result = source.raycast(25, 10f, false);
		sonicBoom(source, result.getPos(), world, damage);
	}
	private static void sonicBoom(LivingEntity source, Vec3d pos, ServerWorld world, float damage) {
		Vec3d vec3d = source.getPos().add(0.0, 1.6f, 0.0);
		Vec3d vec3d2 = pos.subtract(vec3d);
		Vec3d vec3d3 = vec3d2.normalize();
		Set<LivingEntity> hitEntities = new HashSet<>();
		int len = MathHelper.floor(vec3d2.length());
		for (int i = 1; i < len + 7; ++i) {
			Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
			List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, Box.of(vec3d4, 1, 1, 1), LivingEntity::isAlive);
			hitEntities.addAll(entities);
			world.spawnParticles(ModParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
		}
		world.playSound(null, source.getBlockPos(), ModSoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.HOSTILE, 3.0f, 1.0f);
		for (LivingEntity entity : hitEntities) {
			entity.damage(ModEntityDamageSource.sonicBoom(source), damage);
			double d = 0.5 * (1.0 - entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
			double e = 2.5 * (1.0 - entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
			entity.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
		}
	}
	public static void SonicBoom(LivingEntity source, LivingEntity target, ServerWorld world, float damage) {
		Vec3d vec3d = source.getPos().add(0.0, 1.6f, 0.0);
		Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
		Vec3d vec3d3 = vec3d2.normalize();
		for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
			Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
			world.spawnParticles(ModParticleTypes.SONIC_BOOM, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
		}
		source.playSound(ModSoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0f, 1.0f);
		target.damage(ModEntityDamageSource.sonicBoom(source), damage);
		double d = 0.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
		double e = 2.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
		target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
	}
	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) { SonicBoomTask.cooldown(wardenEntity, COOLDOWN); }
	public static void cooldown(LivingEntity warden, int cooldown) { warden.getBrain().remember(ModMemoryModules.SONIC_BOOM_COOLDOWN, Unit.INSTANCE, cooldown); }
}