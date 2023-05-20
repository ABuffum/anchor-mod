package fun.mousewich.entity.passive.frog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class TadpoleBrain {
	protected static Brain<?> create(Brain<TadpoleEntity> brain) {
		TadpoleBrain.addCoreActivities(brain);
		TadpoleBrain.addIdleActivities(brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}
	private static void addCoreActivities(Brain<TadpoleEntity> brain) {
		brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new WalkTask(2.0f), new LookAroundTask(45, 90), new WanderAroundTask(), new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)));
	}
	private static void addIdleActivities(Brain<TadpoleEntity> brain) {
		brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask<LivingEntity>(new FollowMobTask(EntityType.PLAYER, 6.0f), UniformIntProvider.create(30, 60))), Pair.of(1, new TemptTask(livingEntity -> 1.25f)), Pair.of(2, new CompositeTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL, ImmutableList.of(Pair.of(new AquaticStrollTask(0.5f), 2), Pair.of(new GoTowardsLookTarget(0.5f, 3), 3), Pair.of(new ConditionalTask<>(Entity::isInsideWaterOrBubbleColumn, new WaitTask(30, 60)), 5))))));
	}
	public static void updateActivities(TadpoleEntity tadpole) { tadpole.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE)); }
}
