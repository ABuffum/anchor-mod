package fun.mousewich.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;

import java.util.EnumSet;

public abstract class MoveToHuntGoal extends Goal {
	public final MobEntity entity;
	public final double range;
	public final double speed;

	public MoveToHuntGoal(MobEntity entity, double range, double speed) {
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		this.entity = entity;
		this.range = range;
		this.speed = speed;
	}

	public abstract boolean canTarget(LivingEntity target);

	public boolean canStart() {
		if (entity.isSleeping()) return false;
		LivingEntity target = entity.getTarget();
		return target != null && target.isAlive() && canTarget(target) && entity.squaredDistanceTo(target) > range;
	}

	public void start() { }

	public void stop() {
		LivingEntity target = entity.getTarget();
		if (target != null) {
			entity.getNavigation().stop();
			entity.getLookControl().lookAt(target, (float)75, (float)40);
		}
	}

	public void tick() { onTick(); }

	public boolean onTick() {
		LivingEntity target = entity.getTarget();
		entity.getLookControl().lookAt(target, (float)75, (float)40);
		boolean inRange = entity.squaredDistanceTo(target) <= range;
		if (inRange) entity.getNavigation().stop();
		else entity.getNavigation().startMovingTo(target, speed);
		return inRange;
	}
}
