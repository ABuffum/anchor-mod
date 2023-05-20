package fun.mousewich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ModEntityPose;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class DigTask <E extends WardenEntity> extends Task<E> {
	public DigTask(int duration) {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), duration);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, E wardenEntity, long l) {
		return wardenEntity.getRemovalReason() == null;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, E wardenEntity) {
		return wardenEntity.isOnGround() || wardenEntity.isTouchingWater() || wardenEntity.isInLava();
	}

	@Override
	protected void run(ServerWorld serverWorld, E wardenEntity, long l) {
		if (wardenEntity.isOnGround()) {
			wardenEntity.SetPose(ModEntityPose.DIGGING);
			wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_DIG, 5.0f, 1.0f);
		}
		else {
			wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_AGITATED, 5.0f, 1.0f);
			this.finishRunning(serverWorld, wardenEntity, l);
		}
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, E wardenEntity, long l) {
		if (wardenEntity.getRemovalReason() == null) wardenEntity.remove(Entity.RemovalReason.DISCARDED);
	}
}