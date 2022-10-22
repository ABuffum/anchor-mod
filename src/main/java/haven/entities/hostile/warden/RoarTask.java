package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.entities.ai.MemoryModules;
import haven.sounds.HavenSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;

public class RoarTask extends Task<WardenEntity> {
	private static final int SOUND_DELAY = 25;
	private static final int ANGER_INCREASE = 20;

	public RoarTask() {
		super(ImmutableMap.of(MemoryModules.ROAR_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModules.ROAR_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, MemoryModules.ROAR_SOUND_DELAY, MemoryModuleState.REGISTERED), WardenBrain.ROAR_DURATION);
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		Brain<WardenEntity> brain = wardenEntity.getBrain();
		brain.remember(MemoryModules.ROAR_SOUND_DELAY, Unit.INSTANCE, 25L);
		brain.forget(MemoryModuleType.WALK_TARGET);
		LivingEntity livingEntity = wardenEntity.getBrain().getOptionalMemory(MemoryModules.ROAR_TARGET).get();
		LookTargetUtil.lookAt(wardenEntity, livingEntity);
		wardenEntity.SetPose(Poses.ROARING);
		wardenEntity.increaseAngerAt(livingEntity, 20, false);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) { return true; }

	@Override
	protected void keepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		if (wardenEntity.getBrain().hasMemoryModule(MemoryModules.ROAR_SOUND_DELAY)
				|| wardenEntity.getBrain().hasMemoryModule(MemoryModules.ROAR_SOUND_COOLDOWN)) {
			return;
		}
		wardenEntity.getBrain().remember(MemoryModules.ROAR_SOUND_COOLDOWN, Unit.INSTANCE, WardenBrain.ROAR_DURATION - 25);
		wardenEntity.playSound(HavenSoundEvents.ENTITY_WARDEN_ROAR, 3.0f, 1.0f);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		if (wardenEntity.IsInPose(Poses.ROARING)) wardenEntity.SetPose(Poses.STANDING);
		wardenEntity.getBrain().getOptionalMemory(MemoryModules.ROAR_TARGET).ifPresent(wardenEntity::updateAttackTarget);
		wardenEntity.getBrain().forget(MemoryModules.ROAR_TARGET);
	}
}
