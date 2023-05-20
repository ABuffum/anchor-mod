package fun.mousewich.entity.ai.goal.slime;

import fun.mousewich.entity.ai.SlimeMoveControl;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class FaceTowardTargetGoal extends Goal {
	private final SlimeEntity slime;
	private int ticksLeft;

	public FaceTowardTargetGoal(SlimeEntity slime) {
		this.slime = slime;
		this.setControls(EnumSet.of(Control.LOOK));
	}

	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.slime.getTarget();
		if (livingEntity == null) {
			return false;
		}
		if (!this.slime.canTarget(livingEntity)) {
			return false;
		}
		return this.slime.getMoveControl() instanceof SlimeMoveControl;
	}

	@Override
	public void start() {
		this.ticksLeft = -Math.floorDiv(-300, 2);
		super.start();
	}

	@Override
	public boolean shouldContinue() {
		LivingEntity livingEntity = this.slime.getTarget();
		if (livingEntity == null || !this.slime.canTarget(livingEntity)) return false;
		return --this.ticksLeft > 0;
	}

	public boolean shouldRunEveryTick() { return true; }

	@Override
	public void tick() {
		LivingEntity livingEntity = this.slime.getTarget();
		if (livingEntity != null) this.slime.lookAtEntity(livingEntity, 10.0f, 10.0f);
		((SlimeMoveControl)this.slime.getMoveControl()).look(this.slime.getYaw(), !this.slime.isSmall() && this.slime.canMoveVoluntarily());
	}
}