package fun.mousewich.entity.ai.task;

import fun.mousewich.util.ModLookTargetUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.LookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class GoTowardsLookTargetTask<E extends LivingEntity> extends Task<E> {
	private final Predicate<E> predicate;
	private final int completionRange;
	private final float speed;

	public GoTowardsLookTargetTask(Predicate<E> predicate, int completionRange, float speed) {
		super(Map.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_PRESENT));
		this.predicate = predicate;
		this.completionRange = completionRange;
		this.speed = speed;
	}

	@Override
	protected boolean shouldRun(ServerWorld world, E entity) {
		Optional<LookTarget> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.LOOK_TARGET);
		if (optional.isEmpty()) return false;
		return predicate.test(entity);
	}

	@Override
	protected void run(ServerWorld world, E entity, long time) {
		Optional<LookTarget> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.LOOK_TARGET);
		optional.ifPresent(lookTarget -> ModLookTargetUtil.walkTowards(entity, lookTarget, this.speed, this.completionRange));
	}
}
