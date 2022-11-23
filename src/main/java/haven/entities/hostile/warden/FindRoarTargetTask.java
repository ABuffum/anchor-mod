package haven.entities.hostile.warden;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FindRoarTargetTask extends Task<WardenEntity> {
	private final Function<WardenEntity, Optional<? extends LivingEntity>> targetFinder;

	public FindRoarTargetTask(Function<WardenEntity, Optional<? extends LivingEntity>> targetFinder) {
		super(Map.ofEntries(
				Map.entry(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT),
				Map.entry(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED)
		));
		this.targetFinder = targetFinder;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return entity.getRoarTarget() == null && this.targetFinder.apply(entity).filter(entity::isValidTarget).isPresent();
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		this.targetFinder.apply(wardenEntity).ifPresent(target -> {
			wardenEntity.setRoarTarget(target);
			wardenEntity.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		});
	}
}