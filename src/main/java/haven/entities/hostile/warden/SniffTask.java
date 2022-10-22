package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.entities.ai.MemoryModules;
import haven.sounds.HavenSoundEvents;
import net.minecraft.entity.Entity;
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
				Map.entry(MemoryModules.IS_SNIFFING, MemoryModuleState.VALUE_PRESENT),
				Map.entry(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED),
				Map.entry(MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleState.REGISTERED),
				Map.entry(MemoryModules.DISTURBANCE_LOCATION, MemoryModuleState.REGISTERED),
				Map.entry(MemoryModules.SNIFF_COOLDOWN, MemoryModuleState.REGISTERED)
		)), runTime);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		return true;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		((Entity)wardenEntity).playSound(HavenSoundEvents.ENTITY_WARDEN_SNIFF, 5.0f, 1.0f);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		if (wardenEntity.IsInPose(Poses.SNIFFING)) wardenEntity.SetPose(Poses.STANDING);
		wardenEntity.getBrain().forget(MemoryModules.IS_SNIFFING);
		wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE).filter(wardenEntity::isValidTarget).ifPresent(target -> {
			if (wardenEntity.isInRange((Entity)target, 6.0)) wardenEntity.increaseAngerAt((Entity)target);
			if (!wardenEntity.getBrain().hasMemoryModule(MemoryModules.DISTURBANCE_LOCATION)) {
				WardenBrain.lookAtDisturbance(wardenEntity, target.getBlockPos());
			}
		});
	}
}
