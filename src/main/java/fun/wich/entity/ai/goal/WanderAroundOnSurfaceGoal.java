package fun.wich.entity.ai.goal;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;

public class WanderAroundOnSurfaceGoal extends Goal {
	private final PathAwareEntity mob;
	private double x;
	private double y;
	private double z;
	private final double speed;
	private final World world;

	public WanderAroundOnSurfaceGoal(PathAwareEntity mob, double speed) {
		this.mob = mob;
		this.speed = speed;
		this.world = mob.world;
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		if (!this.world.isDay()) return false;
		if (this.mob.isTouchingWater()) return false;
		Vec3d vec3d = this.getWanderTarget();
		if (vec3d == null) return false;
		this.x = vec3d.x;
		this.y = vec3d.y;
		this.z = vec3d.z;
		return true;
	}
	@Override
	public boolean shouldContinue() { return !this.mob.getNavigation().isIdle(); }
	@Override
	public void start() { this.mob.getNavigation().startMovingTo(this.x, this.y, this.z, this.speed); }
	private Vec3d getWanderTarget() {
		Random random = this.mob.getRandom();
		BlockPos blockPos = this.mob.getBlockPos();
		for (int i = 0; i < 10; ++i) {
			BlockPos blockPos2 = blockPos.add(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
			if (!this.world.getBlockState(blockPos2).isOf(Blocks.WATER)) continue;
			return Vec3d.ofBottomCenter(blockPos2);
		}
		return null;
	}
}
