package fun.wich.entity.ai.goal.slime;

import fun.wich.entity.ai.SlimeMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.SlimeEntity;

import java.util.EnumSet;

public class SwimmingGoal extends Goal {
	private final SlimeEntity slime;

	public SwimmingGoal(SlimeEntity slime) {
		this.slime = slime;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
		slime.getNavigation().setCanSwim(true);
	}

	@Override
	public boolean canStart() {
		return (this.slime.isTouchingWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof SlimeMoveControl;
	}

	public boolean shouldRunEveryTick() { return true; }

	@Override
	public void tick() {
		if (this.slime.getRandom().nextFloat() < 0.8f) this.slime.getJumpControl().setActive();
		((SlimeMoveControl)this.slime.getMoveControl()).move(1.2);
	}
}
