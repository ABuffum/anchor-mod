package fun.mousewich.mixins.entity.hostile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntity implements EntityWithBloodType {
	protected SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at=@At("HEAD"))
	private void AddHostility(CallbackInfo ci) {
		this.targetSelector.add(2, MobHostilityPower.makeHostilityGoal(this, getType()));
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.SPIDER_BLOOD_TYPE; }
}
