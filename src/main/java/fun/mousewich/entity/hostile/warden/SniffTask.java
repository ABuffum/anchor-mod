package fun.mousewich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ModEntityPose;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public class SniffTask<E extends WardenEntity> extends Task<E> {
	private static final double HORIZONTAL_RADIUS = 6.0;
	private static final double VERTICAL_RADIUS = 20.0;

	public SniffTask(int runTime) {
		super(Map.ofEntries(
				Map.entry(ModMemoryModules.IS_SNIFFING, MemoryModuleState.VALUE_PRESENT),
				Map.entry(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED),
				Map.entry(MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleState.REGISTERED),
				Map.entry(ModMemoryModules.DISTURBANCE_LOCATION, MemoryModuleState.REGISTERED),
				Map.entry(ModMemoryModules.SNIFF_COOLDOWN, MemoryModuleState.REGISTERED)
		), runTime);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, E wardenEntity, long l) { return true; }

	@Override
	protected void run(ServerWorld serverWorld, E wardenEntity, long l) {
		wardenEntity.playSound(ModSoundEvents.ENTITY_WARDEN_SNIFF, 5.0f, 1.0f);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, E wardenEntity, long l) {
		if (wardenEntity.IsInPose(ModEntityPose.SNIFFING)) {
			wardenEntity.SetPose(ModEntityPose.STANDING);
		}
		wardenEntity.getBrain().forget(ModMemoryModules.IS_SNIFFING);
		wardenEntity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE).filter(wardenEntity::isValidTarget).ifPresent(target -> {
			if (wardenEntity.isInRange(target, HORIZONTAL_RADIUS, VERTICAL_RADIUS)) wardenEntity.increaseAngerAt(target);
			if (!wardenEntity.getBrain().hasMemoryModule(ModMemoryModules.DISTURBANCE_LOCATION)) {
				WardenBrain.lookAtDisturbance(wardenEntity, target.getBlockPos());
			}
		});
	}
}
