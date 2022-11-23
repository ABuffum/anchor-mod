package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.sounds.ModSoundEvents;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class EmergeTask extends Task<WardenEntity> {
	public EmergeTask(int duration) {
		super(ImmutableMap.of(
				MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED), duration);
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return entity.isEmerging();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		return true;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		wardenEntity.SetPose(Poses.EMERGING);
		wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_EMERGE, 5.0f, 1.0f);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		if (wardenEntity.IsInPose(Poses.EMERGING)) {
			wardenEntity.SetPose(Poses.STANDING);
		}
	}
}
