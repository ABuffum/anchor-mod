package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class LookAtDisturbanceTask extends Task<WardenEntity> {
	public LookAtDisturbanceTask() {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT));
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return entity.getDisturbanceLocation() != null || entity.getRoarTarget() != null;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		LivingEntity roarTarget = entity.getRoarTarget();
		BlockPos blockPos;
		if (roarTarget != null) blockPos = roarTarget.getBlockPos();
		else blockPos = entity.getDisturbanceLocation();
		entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(blockPos));
	}
}