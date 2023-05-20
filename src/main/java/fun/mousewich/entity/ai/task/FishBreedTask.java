package fun.mousewich.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import fun.mousewich.entity.FishBreedableEntity;
import fun.mousewich.entity.ai.ModMemoryModules;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class FishBreedTask extends Task<FishEntity> {
	private static final int MAX_RANGE = 3;
	private static final int MIN_BREED_TIME = 60;
	private static final int RUN_TIME = 110;
	private final EntityType<? extends FishEntity> targetType;
	private final float speed;
	private long breedTime;

	public FishBreedTask(EntityType<? extends FishEntity> targetType, float speed) {
		super(ImmutableMap.of(MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.FISH_BREED_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED), RUN_TIME);
		this.targetType = targetType;
		this.speed = speed;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, FishEntity fish) {
		return fish instanceof FishBreedableEntity breedable && breedable.isInLove() && this.findBreedTarget(fish).isPresent();
	}

	@Override
	protected void run(ServerWorld serverWorld, FishEntity fish, long l) {
		FishEntity fish2 = this.findBreedTarget(fish).get();
		fish.getBrain().remember(ModMemoryModules.FISH_BREED_TARGET, fish2);
		fish2.getBrain().remember(ModMemoryModules.FISH_BREED_TARGET, fish);
		LookTargetUtil.lookAtAndWalkTowardsEachOther(fish, fish2, this.speed);
		int i = MIN_BREED_TIME + fish.getRandom().nextInt(50);
		this.breedTime = l + (long)i;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, FishEntity fish, long l) {
		if (!this.hasBreedTarget(fish)) return false;
		FishEntity fish2 = this.getBreedTarget(fish);
		return fish2.isAlive() && fish instanceof FishBreedableEntity breedable
				&& breedable.canBreedWith(fish2) && LookTargetUtil.canSee(fish.getBrain(), fish2) && l <= this.breedTime;
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, FishEntity fish, long l) {
		FishEntity fish2 = this.getBreedTarget(fish);
		LookTargetUtil.lookAtAndWalkTowardsEachOther(fish, fish2, this.speed);
		if (!fish.isInRange(fish2, MAX_RANGE)) return;
		if (l >= this.breedTime && fish instanceof FishBreedableEntity breedable) {
			breedable.breed(serverWorld, fish2);
			fish.getBrain().forget(ModMemoryModules.FISH_BREED_TARGET);
			fish2.getBrain().forget(ModMemoryModules.FISH_BREED_TARGET);
		}
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, FishEntity fish, long l) {
		fish.getBrain().forget(ModMemoryModules.FISH_BREED_TARGET);
		fish.getBrain().forget(MemoryModuleType.WALK_TARGET);
		fish.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		this.breedTime = 0L;
	}

	private FishEntity getBreedTarget(FishEntity fish) {
		return fish.getBrain().getOptionalMemory(ModMemoryModules.FISH_BREED_TARGET).get();
	}

	private boolean hasBreedTarget(FishEntity fish) {
		Brain<?> brain = fish.getBrain();
		return brain.hasMemoryModule(ModMemoryModules.FISH_BREED_TARGET)
				&& brain.getOptionalMemory(ModMemoryModules.FISH_BREED_TARGET).get().getType() == this.targetType;
	}

	private Optional<? extends FishEntity> findBreedTarget(FishEntity fish) {
		if (!(fish instanceof FishBreedableEntity breedable)) return Optional.empty();
		return fish.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().filter(
				entity -> entity.getType() == this.targetType && entity instanceof FishEntity fe
						&& breedable.canBreedWith(fe)).map(e -> (FishEntity)e).findFirst();
	}
}
