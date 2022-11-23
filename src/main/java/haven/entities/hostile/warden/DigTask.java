package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.sounds.ModSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class DigTask extends Task<WardenEntity> {
	public DigTask(int duration) {
		super(ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), duration);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		return entity.getRemovalReason() == null;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return entity.getDigCooldown() <= 0 && entity.getRoarTarget() == null && entity.isOnGround() || entity.isTouchingWater() || entity.isInLava();
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		if (entity.isOnGround()) {
			entity.SetPose(Poses.DIGGING);
			entity.playSound(ModSoundEvents.ENTITY_WARDEN_DIG, 5.0f, 1.0f);
		} else {
			entity.playSound(ModSoundEvents.ENTITY_WARDEN_AGITATED, 5.0f, 1.0f);
			this.finishRunning(serverWorld, entity, l);
		}
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		if (entity.getRemovalReason() == null) {
			entity.remove(Entity.RemovalReason.DISCARDED);
		}
	}
}