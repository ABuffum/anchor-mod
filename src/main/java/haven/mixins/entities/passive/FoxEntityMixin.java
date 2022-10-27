package haven.mixins.entities.passive;

import haven.entities.ai.MoveToHuntGoal;
import haven.mixins.entities.LivingEntityAccessor;
import haven.origins.powers.AttackedByFoxesPower;
import haven.origins.powers.ScareFoxesPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoxEntity.class)
public abstract class FoxEntityMixin extends AnimalEntity {
	protected FoxEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initGoals", at = @At("TAIL"))
	private void addGoals(CallbackInfo info) {
		Goal fleeGoal = new FleeEntityGoal<>(this, PlayerEntity.class, ScareFoxesPower::HasActivePower, 16.0F, 1.6D, 1.4D, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test);
		this.goalSelector.add(4, fleeGoal);
		Goal moveToHuntGoal = new MoveToHuntGoal(this, 36, 1.5D) {
			public boolean canTarget(LivingEntity target) {
				return target != null && target.isAlive()
						&& AttackedByFoxesPower.HasActivePower(target)
						&& !entity.isInSneakingPose()
						&& !((FoxEntity)entity).isRollingHead() && !((LivingEntityAccessor)entity).getJumping();
			}

			public void start() {
				((FoxEntity)entity).setSitting(false);
				((FoxEntityInvoker)entity).invokeSetWalking(false);
			}

			public void stop() {
				LivingEntity target = entity.getTarget();
				if (target != null && FoxEntity.canJumpChase(((FoxEntity)entity), target)) {
					((FoxEntity)entity).setRollingHead(true);
					((FoxEntity)entity).setCrouching(true);
					entity.getNavigation().stop();
					entity.getLookControl().lookAt(target, (float)entity.getBodyYawSpeed(), (float)entity.getLookPitchSpeed());
				}
				else {
					((FoxEntity)entity).setRollingHead(false);
					((FoxEntity)entity).setCrouching(false);
				}
			}

			public void tick() {
				boolean inRange = super.onTick();
				if (inRange) {
					((FoxEntity)entity).setRollingHead(true);
					((FoxEntity)entity).setCrouching(true);
				}
			}
		};
		this.goalSelector.add(5, moveToHuntGoal);
	}
}
