package fun.wich.entity.hostile.spider;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class BoneSpiderShootGoal extends Goal {
	protected final BoneSpiderEntity mob;
	protected LivingEntity target;
	protected int updateCountdownTicks = -1;
	protected final double mobSpeed;
	protected int seenTargetTicks;
	protected final int minIntervalTicks;
	protected final int maxIntervalTicks;
	protected final float maxShootRange;
	protected final float squaredMaxShootRange;
	public BoneSpiderShootGoal(BoneSpiderEntity mob, double mobSpeed, int intervalTicks, float maxShootRange) {
		this(mob, mobSpeed, intervalTicks, intervalTicks, maxShootRange);
	}
	public BoneSpiderShootGoal(BoneSpiderEntity mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
		this.mob = mob;
		this.mobSpeed = mobSpeed;
		this.minIntervalTicks = minIntervalTicks;
		this.maxIntervalTicks = maxIntervalTicks;
		this.maxShootRange = maxShootRange;
		this.squaredMaxShootRange = maxShootRange * maxShootRange;
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
	}
	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.mob.getTarget();
		if (livingEntity == null || !livingEntity.isAlive()) {
			return false;
		}
		this.target = livingEntity;
		return true;
	}
	@Override
	public boolean shouldContinue() { return this.canStart() || !this.mob.getNavigation().isIdle(); }
	@Override
	public void stop() {
		this.target = null;
		this.seenTargetTicks = 0;
		this.updateCountdownTicks = -1;
	}
	public boolean shouldRunEveryTick() { return true; }
	@Override
	public void tick() {
		double d = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
		boolean bl = this.mob.getVisibilityCache().canSee(this.target);
		//this.seenTargetTicks = bl ? ++this.seenTargetTicks : 0;
		if (d > (double)this.squaredMaxShootRange || !bl) {// || this.seenTargetTicks < 5) {
			this.mob.getNavigation().startMovingTo(this.target, this.mobSpeed);
		}
		else {
			this.mob.getNavigation().stop();
		}
		this.mob.getLookControl().lookAt(this.target, 30.0f, 30.0f);
		if (--this.updateCountdownTicks == 0) {
			if (!bl) return;
			float f = (float)Math.sqrt(d) / this.maxShootRange;
			this.mob.attack(this.target);
			this.updateCountdownTicks = MathHelper.floor(f * (float)(this.maxIntervalTicks - this.minIntervalTicks) + (float)this.minIntervalTicks);
		}
		else if (this.updateCountdownTicks < 0) {
			this.updateCountdownTicks = MathHelper.floor(MathHelper.lerp(Math.sqrt(d) / (double)this.maxShootRange, this.minIntervalTicks, this.maxIntervalTicks));
		}
	}
}
