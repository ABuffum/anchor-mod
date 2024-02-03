package fun.wich.entity.ai.goal.llama;

import fun.wich.entity.passive.JollyLlamaEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.EnumSet;
import java.util.function.Predicate;

public class EatFernGoal extends Goal {
	private static final int MAX_TIMER = 40;
	private static final Predicate<BlockState> FERN_PREDICATE = state -> state.isOf(Blocks.FERN) || state.isOf(Blocks.LARGE_FERN);
	private final JollyLlamaEntity mob;
	private final World world;
	private int timer;

	public EatFernGoal(JollyLlamaEntity mob) {
		this.mob = mob;
		this.world = mob.world;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK, Goal.Control.JUMP));
	}

	@Override
	public boolean canStart() {
		if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) return false;
		BlockPos blockPos = this.mob.getBlockPos();
		return FERN_PREDICATE.test(this.world.getBlockState(blockPos));
	}
	@Override
	public void start() {
		this.timer = MAX_TIMER;
		this.world.sendEntityStatus(this.mob, (byte)10);
		this.mob.getNavigation().stop();
	}
	@Override
	public void stop() { this.timer = 0; }
	@Override
	public boolean shouldContinue() { return this.timer > 0; }
	public int getTimer() { return this.timer; }

	@Override
	public void tick() {
		this.timer = Math.max(0, this.timer - 1);
		if (this.timer != 4) return;
		BlockPos blockPos = this.mob.getBlockPos();
		if (FERN_PREDICATE.test(this.world.getBlockState(blockPos))) {
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				this.world.breakBlock(blockPos, false);
			}
			this.mob.onEatingFern();
			this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
		}
	}
}
