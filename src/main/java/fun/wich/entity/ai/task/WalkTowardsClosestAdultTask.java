package fun.wich.entity.ai.task;

import fun.wich.util.ModLookTargetUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.Optional;

public class WalkTowardsClosestAdultTask<E extends LivingEntity> extends Task<E> {
	private final int completionRange;
	private final int searchRange;
	private final float speed;

	public WalkTowardsClosestAdultTask(int completionRange, int searchRange, float speed) {
		super(Map.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
		this.completionRange = completionRange;
		this.searchRange = searchRange;
		this.speed = speed;
	}

	private PassiveEntity getNearestAdult(E entity) {
		Optional<PassiveEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT);
		return optional.orElse(null);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, E entity) {
		PassiveEntity target = getNearestAdult(entity);
		if (target == null) return false;
		return !entity.getPos().isInRange(target.getPos(), this.searchRange);
	}

	@Override
	protected void run(ServerWorld world, E entity, long time) {
		PassiveEntity target = getNearestAdult(entity);
		if (target == null) return;
		ModLookTargetUtil.walkTowards(entity, new EntityLookTarget(target, false), this.speed, this.completionRange);
	}
}
