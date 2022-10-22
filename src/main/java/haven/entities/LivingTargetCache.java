package haven.entities;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.MemoryModuleType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LivingTargetCache {
	private static final LivingTargetCache EMPTY = new LivingTargetCache();
	private final List<LivingEntity> entities;
	public List<LivingEntity> getEntities() { return entities; }
	private final Predicate<LivingEntity> targetPredicate;

	private LivingTargetCache() {
		this.entities = List.of();
		this.targetPredicate = entity -> false;
	}

	private static final TargetPredicate TARGET_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0);
	private static final TargetPredicate TARGET_PREDICATE_IGNORE_DISTANCE_SCALING = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreDistanceScalingFactor();

	public static boolean testTargetPredicate(LivingEntity entity, LivingEntity target) {
		if (entity.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ATTACK_TARGET, target)) {
			return TARGET_PREDICATE_IGNORE_DISTANCE_SCALING.test(entity, target);
		}
		return TARGET_PREDICATE.test(entity, target);
	}

	public LivingTargetCache(LivingEntity owner, List<LivingEntity> entities) {
		this.entities = entities;
		Object2BooleanOpenHashMap object2BooleanOpenHashMap = new Object2BooleanOpenHashMap(entities.size());
		Predicate<LivingEntity> predicate = entity -> testTargetPredicate(owner, entity);
		this.targetPredicate = entity -> object2BooleanOpenHashMap.containsKey(entity) ? object2BooleanOpenHashMap.get(entity) : predicate.test(entity);
	}

	public static LivingTargetCache empty() {
		return EMPTY;
	}

	public Optional<LivingEntity> findFirst(Predicate<LivingEntity> predicate) {
		for (LivingEntity livingEntity : this.entities) {
			if (!predicate.test(livingEntity) || !this.targetPredicate.test(livingEntity)) continue;
			return Optional.of(livingEntity);
		}
		return Optional.empty();
	}

	public Iterable<LivingEntity> iterate(Predicate<LivingEntity> predicate) {
		return Iterables.filter(this.entities, entity -> predicate.test((LivingEntity)entity) && this.targetPredicate.test((LivingEntity)entity));
	}

	public Stream<LivingEntity> stream(Predicate<LivingEntity> predicate) {
		return this.entities.stream().filter(entity -> predicate.test((LivingEntity)entity) && this.targetPredicate.test((LivingEntity)entity));
	}

	public boolean contains(LivingEntity entity) {
		return this.entities.contains(entity) && this.targetPredicate.test(entity);
	}

	public boolean anyMatch(Predicate<LivingEntity> predicate) {
		for (LivingEntity livingEntity : this.entities) {
			if (!predicate.test(livingEntity) || !this.targetPredicate.test(livingEntity)) continue;
			return true;
		}
		return false;
	}
}
