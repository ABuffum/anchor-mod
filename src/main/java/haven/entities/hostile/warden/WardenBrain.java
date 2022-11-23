package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import haven.entities.ai.Activities;
import haven.entities.ai.DismountVehicleTask;
import haven.mixins.entities.ai.SensorTypeInvoker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AxolotlBrain;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.List;

public class WardenBrain {
	private static final int DIG_DURATION = 100;
	public static final int EMERGE_DURATION = 134;
	public static final int ROAR_DURATION = 84;
	private static final int SNIFF_DURATION = 84;
	public static final int DIG_COOLDOWN = 1200;

	public static final SensorType<WardenAttackablesSensor> WARDEN_ENTITY_SENSOR = SensorTypeInvoker.InvokeRegister("warden_entity_sensor", WardenAttackablesSensor::new);
	private static final List<SensorType<? extends Sensor<? super WardenEntity>>> SENSORS = List.of(SensorType.NEAREST_PLAYERS, WARDEN_ENTITY_SENSOR);
	private static final List<MemoryModuleType<?>> MEMORY_MODULES = List.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_ATTACKABLE);
	private static final Task<WardenEntity> RESET_DIG_COOLDOWN_TASK = new Task<WardenEntity>(ImmutableMap.of()){
		@Override
		protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
			entity.setDigCooldown(DIG_COOLDOWN);
		}
	};

	public static void updateActivities(WardenEntity entity) {
		Brain<WardenEntity> brain = entity.getBrain();
		brain.resetPossibleActivities(ImmutableList.of(Activities.EMERGE, Activities.DIG, Activities.ROAR, Activity.FIGHT, Activities.INVESTIGATE, Activities.SNIFF, Activity.IDLE));
	}

	protected static Brain<?> create(WardenEntity warden, Dynamic<?> dynamic) {
		Brain.Profile<WardenEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<WardenEntity> brain = profile.deserialize(dynamic);
		//Core Activities
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
				new StayAboveWaterTask(0.8f),
				new LookAtDisturbanceTask(),
				new LookAroundTask(45, 90),
				new WanderAroundTask()));
		//Emerge Activities
		brain.setTaskList(Activities.EMERGE, 5, ImmutableList.of(new EmergeTask(EMERGE_DURATION)));
		//Dig Activities
		brain.setTaskList(Activities.DIG, ImmutableList.of(
				Pair.of(0, new DismountVehicleTask()),
				Pair.of(1, new DigTask(DIG_DURATION))));
		//Idle Activities
		addIdleActivities(brain);
		//Roar Activities
		brain.setTaskList(Activities.ROAR, 10, ImmutableList.of(new RoarTask()));
		//Fight Activities
		brain.setTaskList(Activity.FIGHT, 10, ImmutableList.of(RESET_DIG_COOLDOWN_TASK,
						new HavenForgetAttackTargetTask<>(target -> !warden.getAngriness().isAngry() || !warden.isValidTarget(target), WardenBrain::removeDeadSuspect, false),
						new FollowMobTask(target -> WardenBrain.isTargeting(warden, target), (float)warden.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
						new RangedApproachTask(1.2f),
						new SonicBoomTask(),
						new MeleeAttackTask(18)), MemoryModuleType.ATTACK_TARGET);
		//Investigate Activities
		brain.setTaskList(Activities.INVESTIGATE, 5, ImmutableList.of(
				new FindRoarTargetTask(WardenEntity::getPrimeSuspect),
				new WardenGoToCelebrateTask(2, 0.7f)));
		//Sniff Activities
		brain.setTaskList(Activities.SNIFF, 5, ImmutableList.of(
				new FindRoarTargetTask(WardenEntity::getPrimeSuspect),
				new SniffTask(SNIFF_DURATION)));
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}

	private static void addIdleActivities(Brain<WardenEntity> brain) {
		brain.setTaskList(Activity.IDLE, ImmutableList.of(
				Pair.of(10, new FindRoarTargetTask(WardenEntity::getPrimeSuspect)),
				Pair.of(11, new StartSniffingTask()),
				Pair.of(12, new StrollOrWaitTask(ImmutableList.of(
						Pair.of(new StrollTask(0.5f), 2),
						Pair.of(new WaitTask(30, 60), 1))))));

		/*brain.setTaskList(Activity.IDLE, ImmutableList.of(
				Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))),
				Pair.of(1, new BreedTask(EntityType.AXOLOTL, 0.2f)),
				Pair.of(2, new RandomTask(ImmutableList.of(
						Pair.of(new TemptTask(AxolotlBrain::method_33248), 1),
						Pair.of(new WalkTowardClosestAdultTask(WALK_TOWARD_ADULT_RANGE, AxolotlBrain::method_33245), 1)))),
				Pair.of(3, new UpdateAttackTargetTask<AxolotlEntity>(AxolotlBrain::getAttackTarget)),
				Pair.of(3, new SeekWaterTask(6, 0.15f)),
				Pair.of(4, new CompositeTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(
						Pair.of(new AquaticStrollTask(0.5f), 2),
						Pair.of(new StrollTask(0.15f, false), 2),
						Pair.of(new GoTowardsLookTarget(AxolotlBrain::canGoToLookTarget, AxolotlBrain::method_33248, 3), 3),
						Pair.of(new ConditionalTask<LivingEntity>(Entity::isInsideWaterOrBubbleColumn, new WaitTask(30, 60)), 5),
						Pair.of(new ConditionalTask<LivingEntity>(Entity::isOnGround, new WaitTask(200, 400)), 5))))));*/
	}

	private static boolean isTargeting(WardenEntity entity, LivingEntity target) {
		return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(e -> e == target).isPresent();
	}

	public static void removeDeadSuspect(WardenEntity entity, LivingEntity suspect) {
		if (!entity.isValidTarget(suspect)) entity.removeSuspect(suspect);
		entity.setDigCooldown(DIG_COOLDOWN);
	}

	public static void lookAtDisturbance(WardenEntity entity, BlockPos pos) {
		if (!entity.world.getWorldBorder().contains(pos) || entity.getPrimeSuspect().isPresent() || entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).isPresent()) {
			return;
		}
		entity.setDigCooldown(DIG_COOLDOWN);
		entity.setSniffingCooldown(100);
		entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(pos), 100L);
		entity.setDisturbanceLocation(pos, 100);
		entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
	}
}
