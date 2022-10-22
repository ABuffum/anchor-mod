package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import haven.entities.LivingTargetCache;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestLivingEntitiesSensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.*;
import java.util.function.Predicate;

public class WardenAttackablesSensor extends NearestLivingEntitiesSensor {
	@Override
	public Set<MemoryModuleType<?>> getOutputMemoryModules() {
		return ImmutableSet.copyOf(Iterables.concat(super.getOutputMemoryModules(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
	}

	@Override
	protected void sense(ServerWorld serverWorld, LivingEntity wardenEntity) {
		Box box = wardenEntity.getBoundingBox().expand(this.getHorizontalExpansion(), this.getHeightExpansion(), this.getHorizontalExpansion());
		List<LivingEntity> list = serverWorld.getEntitiesByClass(LivingEntity.class, box, e -> e != wardenEntity && e.isAlive());
		list.sort(Comparator.comparingDouble(arg_0 -> wardenEntity.squaredDistanceTo(arg_0)));
		Brain<?> brain = wardenEntity.getBrain();
		brain.remember(MemoryModuleType.MOBS, list);
		brain.remember(MemoryModuleType.VISIBLE_MOBS, new LivingTargetCache(wardenEntity, list).getEntities());
		WardenAttackablesSensor.findNearestTarget((WardenEntity)wardenEntity, entity -> entity.getType() == EntityType.PLAYER)
				.or(() -> WardenAttackablesSensor.findNearestTarget((WardenEntity)wardenEntity, livingEntity -> livingEntity.getType() != EntityType.PLAYER))
				.ifPresentOrElse(entity -> wardenEntity.getBrain().remember(MemoryModuleType.NEAREST_ATTACKABLE, entity),
						() -> wardenEntity.getBrain().forget(MemoryModuleType.NEAREST_ATTACKABLE));
	}

	private static Optional<LivingEntity> findNearestTarget(WardenEntity warden, Predicate<LivingEntity> targetPredicate) {
		return warden.getBrain().getOptionalMemory(MemoryModuleType.MOBS).stream().flatMap(Collection::stream).filter(warden::isValidTarget).filter(targetPredicate).findFirst();
	}

	//@Override
	protected int getHorizontalExpansion() {
		return 24;
	}

	//@Override
	protected int getHeightExpansion() {
		return 24;
	}
}