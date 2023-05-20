package fun.mousewich.entity.ai.goal.slime;

import fun.mousewich.entity.ai.SlimeMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.SlimeEntity;

import java.util.EnumSet;

public class MoveGoal extends Goal {
	private final SlimeEntity slime;

	public MoveGoal(SlimeEntity slime) {
		this.slime = slime;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	@Override
	public boolean canStart() { return !this.slime.hasVehicle(); }

	@Override
	public void tick() { ((SlimeMoveControl)this.slime.getMoveControl()).move(1.0); }
}
