package fun.wich.entity.passive.frog;

import com.google.common.collect.ImmutableMap;
import fun.wich.entity.ModEntityPose;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class CroakTask extends Task<FrogEntity> {
	private static final int MAX_RUN_TICK = 60;
	private static final int RUN_TIME = 100;
	private int runningTicks;
	public CroakTask() {
		super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), RUN_TIME);
	}
	@Override
	protected boolean shouldRun(ServerWorld serverWorld, FrogEntity frogEntity) {
		return frogEntity.GetPose() == ModEntityPose.STANDING;
	}
	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, FrogEntity frogEntity, long l) { return this.runningTicks < MAX_RUN_TICK; }
	@Override
	protected void run(ServerWorld serverWorld, FrogEntity frogEntity, long l) {
		if (frogEntity.isInsideWaterOrBubbleColumn() || frogEntity.isInLava()) return;
		frogEntity.SetPose(ModEntityPose.CROAKING);
		this.runningTicks = 0;
	}
	@Override
	protected void finishRunning(ServerWorld serverWorld, FrogEntity frogEntity, long l) {
		frogEntity.SetPose(ModEntityPose.STANDING);
	}
	@Override
	protected void keepRunning(ServerWorld serverWorld, FrogEntity frogEntity, long l) { ++this.runningTicks; }
}