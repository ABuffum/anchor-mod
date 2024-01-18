package fun.mousewich.entity.ai.goal;

import fun.mousewich.origins.power.EnableTradePower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.EnumSet;

public class StopAndLookAtTradingEntityGoal extends LookAtEntityGoal {
	protected TargetPredicate targetPredicateOverride;

	public StopAndLookAtTradingEntityGoal(MobEntity mobEntity, Class<? extends LivingEntity> class_, float f) {
		super(mobEntity, class_, f);
		this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
		this.targetPredicateOverride = TargetPredicate.createNonAttackable().setBaseMaxDistance(range)
				.setPredicate(entity -> EnableTradePower.canTrade(entity, mobEntity.getType()));
		if (targetType == PlayerEntity.class) {
			this.targetPredicateOverride = this.targetPredicateOverride.setPredicate(entity -> EntityPredicates.rides(mob).test(entity));
		}
	}

	public StopAndLookAtTradingEntityGoal(MobEntity mobEntity, Class<? extends LivingEntity> class_, float f, float g) {
		super(mobEntity, class_, f, g);
		this.setControls(EnumSet.of(Control.LOOK, Control.MOVE));
		this.targetPredicateOverride = TargetPredicate.createNonAttackable().setBaseMaxDistance(range)
				.setPredicate(entity -> EnableTradePower.canTrade(entity, mobEntity.getType()));
		if (targetType == PlayerEntity.class) {
			this.targetPredicateOverride = this.targetPredicateOverride.setPredicate(entity -> EntityPredicates.rides(mob).test(entity));
		}
	}

	@Override
	public boolean canStart() {
		if (this.mob.getRandom().nextFloat() >= this.chance) return false;
		if (this.mob.getTarget() != null) this.target = this.mob.getTarget();
		this.target = this.targetType == PlayerEntity.class
				? this.mob.world.getClosestPlayer(this.targetPredicateOverride, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ())
				: this.mob.world.getClosestEntity(
						this.mob.world.getEntitiesByClass(this.targetType, this.mob.getBoundingBox().expand(this.range, 3.0, this.range), livingEntity -> true),
				this.targetPredicateOverride, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		return this.target != null;
	}
}
