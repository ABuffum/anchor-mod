package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.ai.MemoryModules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;
import java.util.function.Function;

public class FindRoarTargetTask extends Task<WardenEntity> {
	private final Function<WardenEntity, Optional<? extends LivingEntity>> targetFinder;

	public FindRoarTargetTask(Function<WardenEntity, Optional<? extends LivingEntity>> targetFinder) {
		super(ImmutableMap.of(MemoryModules.ROAR_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED));
		this.targetFinder = targetFinder;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity wardenEntity) {
		return this.targetFinder.apply(wardenEntity).filter(arg_0 -> wardenEntity.isValidTarget(arg_0)).isPresent();
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity wardenEntity, long l) {
		this.targetFinder.apply(wardenEntity).ifPresent(target -> {
			wardenEntity.getBrain().remember(MemoryModules.ROAR_TARGET, target);
			wardenEntity.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		});
	}
}