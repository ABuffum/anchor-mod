package fun.mousewich.entity.ai.goal;

import fun.mousewich.entity.FishBreedableEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class FishMateGoal extends Goal {
	private static final TargetPredicate VALID_MATE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility();
	protected final FishEntity fish;
	private final Class<? extends FishEntity> entityClass;
	protected final World world;
	protected FishEntity mate;
	private int timer;
	private final double chance;

	public FishMateGoal(FishEntity fish, double chance) { this(fish, chance, fish.getClass()); }
	public FishMateGoal(FishEntity fish, double chance, Class<? extends FishEntity> entityClass) {
		this.fish = fish;
		this.world = fish.world;
		this.entityClass = entityClass;
		this.chance = chance;
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
	}

	@Override
	public boolean canStart() {
		if (!((FishBreedableEntity)this.fish).isInLove()) return false;
		this.mate = this.findMate();
		return this.mate != null;
	}

	@Override
	public boolean shouldContinue() {
		return this.mate != null && this.mate.isAlive() && ((FishBreedableEntity)this.mate).isInLove() && this.timer < 60;
	}

	@Override
	public void stop() {
		this.mate = null;
		this.timer = 0;
	}
	protected static int toGoalTicks(int serverTicks) { return -Math.floorDiv(-serverTicks, 2); }

	@Override
	public void tick() {
		this.fish.getLookControl().lookAt(this.mate, 10.0f, 40);
		this.fish.getNavigation().startMovingTo(this.mate, this.chance);
		++this.timer;
		if (this.timer >= this.toGoalTicks(60) && this.fish.squaredDistanceTo(this.mate) < 9.0) {
			this.breed();
		}
	}

	private FishEntity findMate() {
		List<? extends FishEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.fish, this.fish.getBoundingBox().expand(8.0));
		double d = Double.MAX_VALUE;
		FishEntity fish = null;
		for (FishEntity fish2 : list) {
			if (!((FishBreedableEntity)this.fish).canBreedWith(fish2) || !(this.fish.squaredDistanceTo(fish2) < d)) continue;
			fish = fish2;
			d = this.fish.squaredDistanceTo(fish2);
		}
		return fish;
	}

	protected void breed() {
		((FishBreedableEntity)this.fish).breed((ServerWorld)this.world, this.mate);
	}
}
