package fun.wich.entity.passive.frog;

import com.google.common.collect.ImmutableMap;
import fun.wich.ModBase;
import fun.wich.entity.ModEntityPose;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.entity.variants.FrogVariant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FrogEatEntityTask extends Task<FrogEntity> {
	public static final int RUN_TIME = 100;
	public static final int CATCH_DURATION = 6;
	public static final int EAT_DURATION = 10;
	private static final float MAX_DISTANCE = 1.75f;
	private static final float VELOCITY_MULTIPLIER = 0.75f;
	public static final int UNREACHABLE_TONGUE_TARGETS_START_TIME = 100;
	public static final int MAX_UNREACHABLE_TONGUE_TARGETS = 5;
	private int eatTick;
	private int moveToTargetTick;
	private final SoundEvent tongueSound;
	private final SoundEvent eatSound;
	private Vec3d targetPos;
	private Phase phase = Phase.DONE;

	public FrogEatEntityTask(SoundEvent tongueSound, SoundEvent eatSound) {
		super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_ABSENT), RUN_TIME);
		this.tongueSound = tongueSound;
		this.eatSound = eatSound;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, FrogEntity frogEntity) {
		LivingEntity livingEntity = frogEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
		boolean bl = this.isTargetReachable(frogEntity, livingEntity);
		if (!bl) {
			frogEntity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
			this.markTargetAsUnreachable(frogEntity, livingEntity);
		}
		return bl && frogEntity.GetPose() != ModEntityPose.CROAKING && FrogEntity.isValidFrogFood(livingEntity);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, FrogEntity frogEntity, long l) {
		return frogEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && this.phase != Phase.DONE && !frogEntity.getBrain().hasMemoryModule(ModMemoryModules.IS_PANICKING);
	}

	@Override
	protected void run(ServerWorld serverWorld, FrogEntity frogEntity, long l) {
		LivingEntity livingEntity = frogEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
		LookTargetUtil.lookAt(frogEntity, livingEntity);
		frogEntity.setFrogTarget(livingEntity);
		frogEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(livingEntity.getPos(), 2.0f, 0));
		this.moveToTargetTick = EAT_DURATION;
		this.phase = Phase.MOVE_TO_TARGET;
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, FrogEntity frogEntity, long l) {
		frogEntity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
		frogEntity.clearFrogTarget();
		frogEntity.SetPose(ModEntityPose.STANDING);
	}

	private void eat(ServerWorld world, FrogEntity frog) {
		Entity entity;
		world.playSoundFromEntity(null, frog, this.eatSound, SoundCategory.NEUTRAL, 2.0f, 1.0f);
		Optional<Entity> optional = frog.getFrogTarget();
		if (optional.isPresent() && (entity = optional.get()).isAlive()) {
			frog.tryAttack(entity);
			if (!entity.isAlive()) {
				if (entity instanceof MagmaCubeEntity) {
					FrogVariant variant = frog.getVariant();
					ItemStack stack = new ItemStack(switch (variant) {
						case WARM -> ModBase.PEARLESCENT_FROGLIGHT;
						case COLD -> ModBase.VERDANT_FROGLIGHT;
						case TEMPERATE -> ModBase.OCHRE_FROGLIGHT;
					});
					frog.dropStack(stack);
				}
				entity.remove(Entity.RemovalReason.KILLED);
			}
		}
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, FrogEntity frogEntity, long l) {
		LivingEntity livingEntity = frogEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
		frogEntity.setFrogTarget(livingEntity);
		switch (this.phase) {
			case MOVE_TO_TARGET -> {
				if (livingEntity.distanceTo(frogEntity) < MAX_DISTANCE) {
					serverWorld.playSoundFromEntity(null, frogEntity, this.tongueSound, SoundCategory.NEUTRAL, 2.0f, 1.0f);
					frogEntity.SetPose(ModEntityPose.USING_TONGUE);
					livingEntity.setVelocity(livingEntity.getPos().relativize(frogEntity.getPos()).normalize().multiply(VELOCITY_MULTIPLIER));
					this.targetPos = livingEntity.getPos();
					this.eatTick = 0;
					this.phase = Phase.CATCH_ANIMATION;
					break;
				}
				if (this.moveToTargetTick <= 0) {
					frogEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(livingEntity.getPos(), 2.0f, 0));
					this.moveToTargetTick = EAT_DURATION;
					break;
				}
				--this.moveToTargetTick;
			}
			case CATCH_ANIMATION -> {
				if (this.eatTick++ < CATCH_DURATION) break;
				this.phase = Phase.EAT_ANIMATION;
				this.eat(serverWorld, frogEntity);
			}
			case EAT_ANIMATION -> {
				if (this.eatTick >= EAT_DURATION) {
					this.phase = Phase.DONE;
					break;
				}
				++this.eatTick;
			}
		}
	}

	private boolean isTargetReachable(FrogEntity entity, LivingEntity target) {
		Path path = entity.getNavigation().findPathTo(target, 0);
		return path != null && path.getManhattanDistanceFromTarget() < MAX_DISTANCE;
	}

	private void markTargetAsUnreachable(FrogEntity entity, LivingEntity target) {
		List<UUID> list = entity.getBrain().getOptionalMemory(ModMemoryModules.UNREACHABLE_TONGUE_TARGETS).orElseGet(ArrayList::new);
		boolean bl = !list.contains(target.getUuid());
		if (list.size() == MAX_UNREACHABLE_TONGUE_TARGETS && bl) list.remove(0);
		if (bl) list.add(target.getUuid());
		entity.getBrain().remember(ModMemoryModules.UNREACHABLE_TONGUE_TARGETS, list, UNREACHABLE_TONGUE_TARGETS_START_TIME);
	}
	enum Phase {
		MOVE_TO_TARGET,
		CATCH_ANIMATION,
		EAT_ANIMATION,
		DONE;
	}
}