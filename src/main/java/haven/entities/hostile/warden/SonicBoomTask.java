package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.ModBase;
import haven.damage.HavenEntityDamageSource;
import haven.sounds.ModSoundEvents;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SonicBoomTask extends Task<WardenEntity> {
	private static final int HORIZONTAL_RANGE = 15;
	private static final int VERTICAL_RANGE = 20;
	public static final int COOLDOWN = 40;
	private static final int SOUND_DELAY = 34;
	private static final int RUN_TIME = 60;
	private static final int PLAY_SOUND_AT = RUN_TIME - SOUND_DELAY;

	public SonicBoomTask() {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT), RUN_TIME);
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return entity.getSonicBoomCooldown() <= 0 && entity.isInRange(entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get(), HORIZONTAL_RANGE, VERTICAL_RANGE);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		return true;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		entity.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, RUN_TIME);
		entity.setSonicBoom(RUN_TIME);
		serverWorld.sendEntityStatus(entity, (byte)62);
		entity.playSound(ModSoundEvents.ENTITY_WARDEN_SONIC_CHARGE, 3.0f, 1.0f);
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> entity.getLookControl().lookAt(target.getPos()));
		if (entity.getRemaingSonicBoomDuration() != PLAY_SOUND_AT) return;
		entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(entity::isValidTarget).filter(target -> entity.isInRange(target, HORIZONTAL_RANGE, VERTICAL_RANGE)).ifPresent(target -> {
			Vec3d vec3d = entity.getPos().add(0.0, 1.6f, 0.0);
			Vec3d vec3d2 = target.getEyePos().subtract(vec3d);
			Vec3d vec3d3 = vec3d2.normalize();
			for (int i = 1; i < MathHelper.floor(vec3d2.length()) + 7; ++i) {
				Vec3d vec3d4 = vec3d.add(vec3d3.multiply(i));
				serverWorld.spawnParticles(ModBase.SONIC_BOOM_PARTICLE, vec3d4.x, vec3d4.y, vec3d4.z, 1, 0.0, 0.0, 0.0, 0.0);
			}
			entity.playSound(ModSoundEvents.ENTITY_WARDEN_SONIC_BOOM, 3.0f, 1.0f);
			target.damage(HavenEntityDamageSource.sonicBoom(entity), 10.0f);
			double d = 0.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
			double e = 2.5 * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
			target.addVelocity(vec3d3.getX() * e, vec3d3.getY() * d, vec3d3.getZ() * e);
		});
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		entity.setSonicBoomCooldown(COOLDOWN);
	}
}
