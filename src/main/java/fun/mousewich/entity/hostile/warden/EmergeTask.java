package fun.mousewich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ModEntityPose;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class EmergeTask<E extends WardenEntity> extends Task<E> {
	public EmergeTask(int duration) {
		super(ImmutableMap.of(ModMemoryModules.IS_EMERGING, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED), duration);
	}
	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, E wardenEntity, long l) { return true; }
	@Override
	protected void run(ServerWorld serverWorld, E wardenEntity, long l) {
		wardenEntity.SetPose(ModEntityPose.EMERGING);
		wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_EMERGE, 5.0f, 1.0f);
	}
	@Override
	protected void finishRunning(ServerWorld serverWorld, E wardenEntity, long l) {
		if (wardenEntity.IsInPose(ModEntityPose.EMERGING)) wardenEntity.SetPose(ModEntityPose.STANDING);
	}
}
