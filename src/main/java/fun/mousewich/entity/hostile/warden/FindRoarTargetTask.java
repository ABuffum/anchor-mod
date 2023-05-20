package fun.mousewich.entity.hostile.warden;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.ai.ModMemoryModules;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;
import java.util.function.Function;

public class FindRoarTargetTask <E extends WardenEntity> extends Task<E> {
	private final Function<E, Optional<? extends LivingEntity>> targetFinder;
	public FindRoarTargetTask(Function<E, Optional<? extends LivingEntity>> targetFinder) {
		super(ImmutableMap.of(ModMemoryModules.ROAR_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED));
		this.targetFinder = targetFinder;
	}
	@Override
	protected boolean shouldRun(ServerWorld serverWorld, E wardenEntity) {
		return this.targetFinder.apply(wardenEntity).filter(wardenEntity::isValidTarget).isPresent();
	}
	@Override
	protected void run(ServerWorld serverWorld, E wardenEntity, long l) {
		this.targetFinder.apply(wardenEntity).ifPresent(target -> {
			wardenEntity.getBrain().remember(ModMemoryModules.ROAR_TARGET, target);
			wardenEntity.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		});
	}
}
