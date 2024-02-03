package fun.wich.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class ModMeleeAttackGoal extends MeleeAttackGoal {
	public ModMeleeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenIdle) { super(mob, speed, pauseWhenIdle); }
	protected void attack(LivingEntity target, double squaredDistance) {
		double d = 2 + target.getWidth();
		if (squaredDistance <= d && this.isCooledDown()) {
			this.resetCooldown();
			this.mob.tryAttack(target);
		}
	}
}
