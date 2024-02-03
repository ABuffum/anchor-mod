package fun.wich.entity.ai.goal;

import fun.wich.entity.passive.cow.FlowerCowEntity;
import fun.wich.mixins.entity.passive.BeeEntityAccessor;
import fun.wich.mixins.entity.passive.BeePollinateGoalAccessor;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class PollinateFlowerCowGoal extends Goal {
	protected TargetPredicate VALID_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor();

	protected final BeeEntity bee;
	private final BeeEntityAccessor beeAccessor;
	protected FlowerCowEntity target;

	private boolean running;
	private int ticks = 0;
	private int pollinationTicks = 0;
	private int lastPollinationTick = 0;

	public PollinateFlowerCowGoal(BeeEntity bee) {
		this.beeAccessor = (BeeEntityAccessor)(this.bee = bee);
		this.setControls(EnumSet.of(Control.MOVE));
	}

	public boolean canStart() {
		if (this.beeAccessor.GetTicksUntilCanPollinate() > 0) return false;
		else if (this.bee.hasAngerTime()) return false;
		else if (this.bee.hasNectar()) return false;
		else if (this.bee.world.isRaining()) return false;
		else if (this.bee.getRandom().nextFloat() < 0.5f) return false;
		else if (((BeePollinateGoalAccessor)this.beeAccessor.GetPollinateGoal()).InvokeIsRunning()) return false;
		FlowerCowEntity target = this.findFlowerCow();
		if (target == null) return false;
		this.target = target;
		Vec3d pos = this.getTargetPollinationPos();
		this.bee.getNavigation().startMovingTo(pos.getX(), pos.getY(), pos.getZ(), 1.2000000476837158D);
		return true;
	}

	public boolean shouldContinue() {
		if (!this.running) return false;
		else if (this.bee.hasAngerTime()) return false;
		else if (this.bee.getEntityWorld().isRaining()) return false;
		else if (this.completedPollination()) return this.bee.getRandom().nextFloat() < 0.2F;
		else if (this.target == null) return false;
		else return this.target.isAlive();
	}

	public void start() {
		this.pollinationTicks = 0;
		this.ticks = 0;
		this.lastPollinationTick = 0;
		this.running = true;
		this.bee.resetPollinationTicks();
	}

	public void cancel() { this.running = false; }

	public void stop() {
		if (this.completedPollination()) {
			this.pollinate();
			this.beeAccessor.InvokeSetHasNectar(true);
		}
		this.running = false;
		this.bee.getNavigation().stop();
		this.beeAccessor.SetTicksUntilCanPollinate(200);
	}

	public void tick() {
		if (++this.ticks > 600) {
			this.target = null;
			return;
		}
		Vec3d pos = this.getTargetPollinationPos();
		double dinstanceToMoobloom = this.bee.getPos().distanceTo(pos);

		if (dinstanceToMoobloom >= 0.5) {
			this.bee.getMoveControl().moveTo(pos.getX(), pos.getY(), pos.getZ(), .9D);
			this.bee.getLookControl().lookAt(pos.getX(), this.target.getY(), pos.getZ());
		}

		if (dinstanceToMoobloom <= 1.5D) {
			this.pollinationTicks++;
			if (this.bee.getRandom().nextFloat() < 0.05F && this.pollinationTicks > this.lastPollinationTick + 60) {
				this.lastPollinationTick = this.pollinationTicks;
				this.bee.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0F, 1.0F);
			}
		}
	}

	private void pollinate() {
		for (int i = 0; i < 7; ++i) {
			double d = this.bee.getRandom().nextGaussian() * 0.02D;
			double e = this.bee.getRandom().nextGaussian() * 0.02D;
			double f = this.bee.getRandom().nextGaussian() * 0.02D;
			((ServerWorld) this.bee.world).spawnParticles(ParticleTypes.HEART, this.bee.getParticleX(1), this.bee.getRandomBodyY() + 0.5, this.bee.getParticleZ(1), 1, d, e, f, 1);
		}
	}

	private FlowerCowEntity findFlowerCow() {
		List<FlowerCowEntity> entities = this.bee.world.getTargets(FlowerCowEntity.class, VALID_TARGET_PREDICATE, this.bee, this.bee.getBoundingBox().expand(32));
		double d = Double.MAX_VALUE;
		FlowerCowEntity closest = null;
		for (FlowerCowEntity entity : entities) {
			if (!entity.isBaby() && this.bee.squaredDistanceTo(entity) < d) {
				closest = entity;
				d = this.bee.squaredDistanceTo(entity);
			}
		}
		return closest;
	}
	public Vec3d getTargetPollinationPos() {
		double pos = this.target.getY() + this.target.getHeight() * 1.5;
		return new Vec3d(this.target.getX(), pos, this.target.getZ());
	}
	public boolean completedPollination() { return this.pollinationTicks > 200; }
}
