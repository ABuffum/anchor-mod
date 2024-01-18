package fun.mousewich.entity.ai.goal.llama;

import fun.mousewich.entity.passive.JollyLlamaEntity;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class FrolicGoal extends WanderAroundGoal {
	protected final float probability;
	protected final int duration;
	protected int timer;

	public FrolicGoal(PathAwareEntity pathAwareEntity, double speed, int duration) {
		this(pathAwareEntity, speed, 0.0001f, duration);
	}

	public FrolicGoal(PathAwareEntity mob, double speed, float probability, int duration) {
		super(mob, speed);
		this.probability = probability;
		this.duration = duration;
	}
	@Override
	public boolean canStart() {
		return ((JollyLlamaEntity)this.mob).canFrolic && super.canStart();
	}
	@Override
	public void start() {
		this.timer = duration;
		((JollyLlamaEntity)this.mob).canFrolic = true;
		super.start();
	}
	@Override
	public void stop() {
		this.timer = 0;
		((JollyLlamaEntity)this.mob).canFrolic = false;
		super.stop();
	}
	@Override
	public boolean shouldContinue() { return super.shouldContinue() && this.timer > 0; }
	public int getTimer() { return this.timer; }

	@Override
	public void tick() {
		this.timer = Math.max(0, this.timer - 1);
		if (this.timer % 20 == 0) this.mob.playSound(ModSoundEvents.ENTITY_JOLLY_LLAMA_PRANCE, 1, 1);
		super.tick();
	}

	@Override
	@Nullable
	protected Vec3d getWanderTarget() {
		if (this.mob.isInsideWaterOrBubbleColumn()) {
			Vec3d vec3d = FuzzyTargeting.find(this.mob, 15, 7);
			return vec3d == null ? super.getWanderTarget() : vec3d;
		}
		if (this.mob.getRandom().nextFloat() >= this.probability) {
			return FuzzyTargeting.find(this.mob, 10, 7);
		}
		return super.getWanderTarget();
	}
}
