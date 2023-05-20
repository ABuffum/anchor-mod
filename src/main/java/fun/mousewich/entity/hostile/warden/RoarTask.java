package fun.mousewich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ModEntityPose;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.EntityPose;
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
		super(ImmutableMap.of(ModMemoryModules.ROAR_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.ROAR_SOUND_COOLDOWN, MemoryModuleState.REGISTERED, ModMemoryModules.ROAR_SOUND_DELAY, MemoryModuleState.REGISTERED), WardenBrain.ROAR_DURATION);
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		Brain<WardenEntity> brain = wardenEntity.getBrain();
		brain.remember(ModMemoryModules.ROAR_SOUND_DELAY, Unit.INSTANCE, SOUND_DELAY);
		brain.forget(MemoryModuleType.WALK_TARGET);
		LivingEntity livingEntity = wardenEntity.getBrain().getOptionalMemory(ModMemoryModules.ROAR_TARGET).get();
		LookTargetUtil.lookAt(wardenEntity, livingEntity);
		wardenEntity.SetPose(ModEntityPose.ROARING);
		wardenEntity.increaseAngerAt(livingEntity, ANGER_INCREASE, false);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		return true;
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		if (wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.ROAR_SOUND_DELAY) || wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.ROAR_SOUND_COOLDOWN)) {
			return;
		}
		wardenEntity.getBrain().remember(ModMemoryModules.ROAR_SOUND_COOLDOWN, Unit.INSTANCE, WardenBrain.ROAR_DURATION - SOUND_DELAY);
		wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_ROAR, 3.0f, 1.0f);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		if (wardenEntity.IsInPose(ModEntityPose.ROARING)) wardenEntity.setPose(EntityPose.STANDING);
		wardenEntity.getBrain().getOptionalMemory(ModMemoryModules.ROAR_TARGET).ifPresent(wardenEntity::updateAttackTarget);
		wardenEntity.getBrain().forget(ModMemoryModules.ROAR_TARGET);
	}
}