package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.HedgehogEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermiteEntity.class)
public abstract class EndermiteEntityMixin extends HostileEntity implements EntityWithBloodType {
	protected EndermiteEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initGoals", at = @At("TAIL"))
	private void addGoals(CallbackInfo info) {
		Goal fleeGoal = new FleeEntityGoal<>(this, HedgehogEntity.class, 16.0F, 1.6D, 1.4D);
		this.goalSelector.add(3, fleeGoal);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.ENDERMITE_BLOOD_TYPE; }
}
