package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.sounds.ModSoundEvents;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public class SniffTask extends Task<WardenEntity> {
	private static final double HORIZONTAL_RADIUS = 6.0;
	private static final double VERTICAL_RADIUS = 20.0;

	public SniffTask(int runTime) {
		super(ImmutableMap.copyOf(Map.ofEntries(
				Map.entry(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED),
				Map.entry(MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleState.REGISTERED)
		)), runTime);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WardenEntity entity) {
		return entity.isSniffing() && entity.getSniffingCooldown() > 0;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		return entity.isSniffing() && entity.getSniffingCooldown() > 0;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		entity.playSound(ModSoundEvents.ENTITY_WARDEN_SNIFF, 5.0f, 1.0f);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		if (entity.IsInPose(Poses.SNIFFING)) entity.SetPose(Poses.STANDING);
		entity.setSniffing(false);
		entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE).filter(entity::isValidTarget).ifPresent(target -> {
			if (entity.isInRange(target, HORIZONTAL_RADIUS, VERTICAL_RADIUS)) entity.increaseAngerAt(target);
			if (entity.getDisturbanceLocation() == null) WardenBrain.lookAtDisturbance(entity, target.getBlockPos());
		});
	}
}
