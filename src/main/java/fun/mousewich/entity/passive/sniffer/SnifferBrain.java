package fun.mousewich.entity.passive.sniffer;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import fun.mousewich.ModBase;
import fun.mousewich.entity.ModActivities;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.entity.ai.task.GoTowardsLookTargetTask;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SnifferBrain {
	static final List<SensorType<? extends Sensor<? super SnifferEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.NEAREST_PLAYERS);
	static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, ModMemoryModules.IS_PANICKING, ModMemoryModules.SNIFFER_SNIFFING_TARGET, ModMemoryModules.SNIFFER_DIGGING, ModMemoryModules.SNIFFER_HAPPY, ModMemoryModules.SNIFF_COOLDOWN, ModMemoryModules.SNIFFER_EXPLORED_POSITIONS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.BREED_TARGET);

	protected static Brain<?> create(Brain<SnifferEntity> brain) {
		SnifferBrain.addCoreActivities(brain);
		SnifferBrain.addIdleActivities(brain);
		SnifferBrain.addSniffActivities(brain);
		SnifferBrain.addDigActivities(brain);
		brain.setCoreActivities(Set.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}
	private static void addCoreActivities(Brain<SnifferEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8f), new WalkTask(2.0f){
			@Override
			protected void run(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
				pathAwareEntity.getBrain().forget(ModMemoryModules.SNIFFER_DIGGING);
				pathAwareEntity.getBrain().forget(ModMemoryModules.SNIFFER_SNIFFING_TARGET);
				((SnifferEntity)pathAwareEntity).startState(SnifferEntity.State.IDLING);
				super.run(serverWorld, pathAwareEntity, l);
			}
		}, new WanderAroundTask(10000, 15000)));
	}
	private static void addSniffActivities(Brain<SnifferEntity> brain) {
		brain.setTaskList(ModActivities.SNIFF, ImmutableList.of(Pair.of(0, new SearchingTask())), Set.of(Pair.of(ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_ABSENT), Pair.of(ModMemoryModules.SNIFFER_SNIFFING_TARGET, MemoryModuleState.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT)));
	}
	private static void addDigActivities(Brain<SnifferEntity> brain) {
		brain.setTaskList(ModActivities.DIG, ImmutableList.of(Pair.of(0, new DiggingTask(160, 180)), Pair.of(0, new FinishDiggingTask(40))), Set.of(Pair.of(ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), Pair.of(ModMemoryModules.SNIFFER_DIGGING, MemoryModuleState.VALUE_PRESENT)));
	}
	private static void addIdleActivities(Brain<SnifferEntity> brain) {
		brain.setTaskList(Activity.IDLE, ImmutableList.of(
				Pair.of(0, new LookAroundTask(45, 90)),
				Pair.of(0, new FeelHappyTask(40, 100)),
				Pair.of(0, new RandomTask<>(ImmutableList.of(
						Pair.of(new GoTowardsLookTargetTask<>(entity -> true, 3, 1.0f), 2),
						Pair.of(new ScentingTask(40, 80), 1),
						Pair.of(new SniffingTask(40, 80), 1),
						Pair.of(new BreedTask(ModBase.SNIFFER_ENTITY, 1.0f), 1),
						Pair.of(new FollowMobTask(EntityType.PLAYER, 6.0f), 1),
						Pair.of(new StrollTask(1.0f), 1),
						Pair.of(new WaitTask(5, 20), 2))))),
				Set.of(Pair.of(ModMemoryModules.SNIFFER_DIGGING, MemoryModuleState.VALUE_ABSENT)));
	}

	static void updateActivities(SnifferEntity sniffer) {
		sniffer.getBrain().resetPossibleActivities(ImmutableList.of(ModActivities.DIG, ModActivities.SNIFF, Activity.IDLE));
	}

	static class SearchingTask extends Task<SnifferEntity> {
		SearchingTask() {
			super(Map.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFFER_SNIFFING_TARGET, MemoryModuleState.VALUE_PRESENT), 600);
		}

		@Override
		protected boolean shouldRun(ServerWorld serverWorld, SnifferEntity snifferEntity) {
			return !snifferEntity.isPanicking() && !snifferEntity.isTouchingWater();
		}

		@Override
		protected boolean shouldKeepRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			if (snifferEntity.isPanicking() && !snifferEntity.isTouchingWater()) return false;
			Optional<BlockPos> optional = snifferEntity.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET).map(WalkTarget::getLookTarget).map(LookTarget::getBlockPos);
			Optional<BlockPos> optional2 = snifferEntity.getBrain().getOptionalMemory(ModMemoryModules.SNIFFER_SNIFFING_TARGET);
			if (optional.isEmpty() || optional2.isEmpty()) return false;
			return optional2.get().equals(optional.get());
		}
		@Override
		protected void run(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.SEARCHING);
		}
		@Override
		protected void finishRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			if (snifferEntity.canDig()) snifferEntity.getBrain().remember(ModMemoryModules.SNIFFER_DIGGING, true);
			snifferEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
			snifferEntity.getBrain().forget(ModMemoryModules.SNIFFER_SNIFFING_TARGET);
		}
	}

	static class DiggingTask extends Task<SnifferEntity> {
		DiggingTask(int minRunTime, int maxRunTime) {
			super(Map.of(ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFFER_DIGGING, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.SNIFF_COOLDOWN, MemoryModuleState.VALUE_ABSENT), minRunTime, maxRunTime);
		}
		@Override
		protected boolean shouldRun(ServerWorld serverWorld, SnifferEntity snifferEntity) {
			return !snifferEntity.isPanicking() && !snifferEntity.isTouchingWater();
		}
		@Override
		protected boolean shouldKeepRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			return snifferEntity.getBrain().getOptionalMemory(ModMemoryModules.SNIFFER_DIGGING).isPresent() && !snifferEntity.isPanicking();
		}
		@Override
		protected void run(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.DIGGING);
		}
		@Override
		protected void finishRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.getBrain().remember(ModMemoryModules.SNIFF_COOLDOWN, Unit.INSTANCE, 9600L);
		}
	}

	static class FinishDiggingTask extends Task<SnifferEntity> {
		FinishDiggingTask(int runTime) {
			super(Map.of(ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFFER_DIGGING, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.SNIFF_COOLDOWN, MemoryModuleState.VALUE_PRESENT), runTime, runTime);
		}
		@Override
		protected boolean shouldRun(ServerWorld serverWorld, SnifferEntity snifferEntity) { return true; }
		@Override
		protected boolean shouldKeepRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			return snifferEntity.getBrain().getOptionalMemory(ModMemoryModules.SNIFFER_DIGGING).isPresent();
		}
		@Override
		protected void run(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.RISING);
		}
		@Override
		protected void finishRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			boolean bl = this.isTimeLimitExceeded(l);
			snifferEntity.startState(SnifferEntity.State.IDLING).finishDigging(bl);
			snifferEntity.getBrain().forget(ModMemoryModules.SNIFFER_DIGGING);
			snifferEntity.getBrain().remember(ModMemoryModules.SNIFFER_HAPPY, true);
		}
	}

	static class FeelHappyTask extends Task<SnifferEntity> {
		FeelHappyTask(int minRunTime, int maxRunTime) {
			super(Map.of(ModMemoryModules.SNIFFER_HAPPY, MemoryModuleState.VALUE_PRESENT), minRunTime, maxRunTime);
		}
		@Override
		protected boolean shouldKeepRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) { return true; }
		@Override
		protected void run(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.FEELING_HAPPY);
		}
		@Override
		protected void finishRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.IDLING);
			snifferEntity.getBrain().forget(ModMemoryModules.SNIFFER_HAPPY);
		}
	}

	static class ScentingTask extends Task<SnifferEntity> {
		ScentingTask(int minRunTime, int maxRunTime) {
			super(Map.of(ModMemoryModules.SNIFFER_DIGGING, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFFER_SNIFFING_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFFER_HAPPY, MemoryModuleState.VALUE_ABSENT), minRunTime, maxRunTime);
		}
		@Override
		protected boolean shouldKeepRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) { return true; }
		@Override
		protected void run(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.SCENTING);
		}
		@Override
		protected void finishRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.IDLING);
		}
	}

	static class SniffingTask extends Task<SnifferEntity> {
		SniffingTask(int minRunTime, int maxRunTime) {
			super(Map.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFFER_SNIFFING_TARGET, MemoryModuleState.VALUE_ABSENT, ModMemoryModules.SNIFF_COOLDOWN, MemoryModuleState.VALUE_ABSENT), minRunTime, maxRunTime);
		}
		@Override
		protected boolean shouldRun(ServerWorld serverWorld, SnifferEntity snifferEntity) {
			return !snifferEntity.isBaby() && !snifferEntity.isTouchingWater();
		}
		@Override
		protected boolean shouldKeepRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			return !snifferEntity.isPanicking();
		}
		@Override
		protected void run(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			snifferEntity.startState(SnifferEntity.State.SNIFFING);
		}
		@Override
		protected void finishRunning(ServerWorld serverWorld, SnifferEntity snifferEntity, long l) {
			boolean bl = this.isTimeLimitExceeded(l);
			snifferEntity.startState(SnifferEntity.State.IDLING);
			if (bl) {
				snifferEntity.findSniffingTargetPos().ifPresent(pos -> {
					snifferEntity.getBrain().remember(ModMemoryModules.SNIFFER_SNIFFING_TARGET, pos);
					snifferEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget((BlockPos)pos, 1.25f, 0));
				});
			}
		}
	}
}