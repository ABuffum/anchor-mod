package haven.entities.hostile.warden;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class WardenGoToCelebrateTask extends Task<WardenEntity> {
	private final int completionRange;
	private final float speed;

	public WardenGoToCelebrateTask(int completionRange, float speed) {
		super(ImmutableMap.of(
				MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED));
		this.completionRange = completionRange;
		this.speed = speed;
	}

	@Override
	protected boolean shouldRun(ServerWorld serverWorld, WardenEntity entity) {
		return entity.getDisturbanceLocation() != null;
	}

	@Override
	protected void run(ServerWorld serverWorld, WardenEntity entity, long l) {
		BlockPos blockPos = entity.getDisturbanceLocation();
		boolean bl = blockPos.isWithinDistance(entity.getBlockPos(), this.completionRange);
		if (!bl) LookTargetUtil.walkTowards(entity, fuzz(entity, blockPos), this.speed, this.completionRange);
	}

	private static BlockPos fuzz(WardenEntity entity, BlockPos pos) {
		Random random = entity.world.random;
		return pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
	}
}
