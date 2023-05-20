package fun.mousewich.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class FollowMobWithIntervalTask extends Task<LivingEntity> {
	private final Predicate<LivingEntity> predicate;
	private final Interval interval;
	private final float maxDistanceSquared;
	private Optional<LivingEntity> target = Optional.empty();

	public FollowMobWithIntervalTask(Tag.Identified<EntityType<?>> entityType, UniformIntProvider interval, float maxDistance) {
		this((LivingEntity entity) -> entity.getType().isIn(entityType), interval, maxDistance);
	}
	public FollowMobWithIntervalTask(SpawnGroup group, UniformIntProvider interval, float maxDistance) {
		this((LivingEntity entity) -> group.equals(entity.getType().getSpawnGroup()), interval, maxDistance);
	}
	public FollowMobWithIntervalTask(EntityType<?> entityType, UniformIntProvider interval, float maxDistance) {
		this((LivingEntity entity) -> entityType.equals(entity.getType()), interval, maxDistance);
	}
	public FollowMobWithIntervalTask(UniformIntProvider interval, float maxDistance) {
		this((LivingEntity entity) -> true, interval, maxDistance);
	}
	public FollowMobWithIntervalTask(Predicate<LivingEntity> predicate, UniformIntProvider interval, float maxDistance) {
		super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT));
		this.predicate = predicate;
		this.interval = new Interval(interval);
		this.maxDistanceSquared = maxDistance * maxDistance;
	}

	@Override
	protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
		if (!interval.shouldRun(world.random)) return false;
		List<LivingEntity> livingTargetCache = entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get();
		this.target = livingTargetCache.stream().filter(this.predicate.and(livingEntity2 -> livingEntity2.squaredDistanceTo(entity) <= (double)this.maxDistanceSquared)).findFirst();
		return this.target.isPresent();
	}

	@Override
	protected void run(ServerWorld world, LivingEntity entity, long time) {
		entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(this.target.get(), true));
		this.target = Optional.empty();
	}

	public static final class Interval {
		private final UniformIntProvider interval;
		private int remainingTicks;
		public Interval(UniformIntProvider interval) {
			if (interval.getMin() <= 1) throw new IllegalArgumentException();
			this.interval = interval;
		}
		public boolean shouldRun(Random random) {
			if (this.remainingTicks == 0) {
				this.remainingTicks = this.interval.get(random) - 1;
				return false;
			}
			return --this.remainingTicks == 0;
		}
	}
}
