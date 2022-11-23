package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import haven.entities.Poses;
import haven.sounds.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class RoarTask extends Task<WardenEntity> {
	private static final int SOUND_DELAY = 25;
	private static final int PLAY_SOUND_AT = WardenBrain.ROAR_DURATION - SOUND_DELAY;
	private static final int ANGER_INCREASE = 20;

	public RoarTask() {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT), WardenBrain.ROAR_DURATION);
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		Brain<WardenEntity> brain = entity.getBrain();
		entity.setRoaring(WardenBrain.ROAR_DURATION);
		brain.forget(MemoryModuleType.WALK_TARGET);
		LivingEntity livingEntity = entity.getRoarTarget();
		LookTargetUtil.lookAt(entity, livingEntity);
		entity.SetPose(Poses.ROARING);
		entity.increaseAngerAt(livingEntity, ANGER_INCREASE, false);
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		return entity.getRemaingRoarDuration() > 0;
	}

	@Override
	protected void keepRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		if (entity.getRemaingRoarDuration() == PLAY_SOUND_AT) {
			entity.playSound(ModSoundEvents.ENTITY_WARDEN_ROAR, 3.0f, 1.0f);
		}
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, WardenEntity entity, long l) {
		if (entity.IsInPose(Poses.ROARING)) entity.SetPose(Poses.STANDING);
		LivingEntity target = entity.getRoarTarget();
		if (target != null) entity.updateAttackTarget(target);
		entity.setRoarTarget(null);
	}
}
