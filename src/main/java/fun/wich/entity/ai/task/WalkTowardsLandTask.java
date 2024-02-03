package fun.wich.entity.ai.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class WalkTowardsLandTask extends Task<PathAwareEntity> {
	private static final int TASK_COOLDOWN = 60;
	private final int range;
	private final float speed;
	private long walkTowardsLandTime;
	public WalkTowardsLandTask(int range, float speed) {
		super(ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED));
		this.range = range;
		this.speed = speed;
	}
	@Override
	protected void finishRunning(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
		this.walkTowardsLandTime = l + TASK_COOLDOWN;
	}
	@Override
	protected boolean shouldRun(ServerWorld serverWorld, PathAwareEntity pathAwareEntity) {
		return pathAwareEntity.world.getFluidState(pathAwareEntity.getBlockPos()).isIn(FluidTags.WATER);
	}
	@Override
	protected void run(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
		if (l < this.walkTowardsLandTime) return;
		BlockPos blockPos = pathAwareEntity.getBlockPos();
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		ShapeContext shapeContext = ShapeContext.of(pathAwareEntity);
		for (BlockPos blockPos2 : BlockPos.iterateOutwards(blockPos, this.range, this.range, this.range)) {
			if (blockPos2.getX() == blockPos.getX() && blockPos2.getZ() == blockPos.getZ()) continue;
			BlockState blockState = serverWorld.getBlockState(blockPos2);
			BlockState blockState2 = serverWorld.getBlockState(mutable.set(blockPos2, Direction.DOWN));
			if (blockState.isOf(Blocks.WATER) || !serverWorld.getFluidState(blockPos2).isEmpty() || !blockState.getCollisionShape(serverWorld, blockPos2, shapeContext).isEmpty() || !blockState2.isSideSolidFullSquare(serverWorld, mutable, Direction.UP)) continue;
			this.walkTowardsLandTime = l + TASK_COOLDOWN;
			LookTargetUtil.walkTowards(pathAwareEntity, blockPos2.toImmutable(), this.speed, 1);
			return;
		}
	}
}
