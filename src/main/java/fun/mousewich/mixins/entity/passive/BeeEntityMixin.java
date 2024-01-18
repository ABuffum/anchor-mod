package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.ai.goal.PollinateFlowerCowGoal;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity implements Angerable, Flutterer, EntityWithBloodType {
	private PollinateFlowerCowGoal pollinateFlowerCowGoal;

	protected BeeEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="initGoals", at=@At("TAIL"))
	private void AddPollinateFlowerCowGoal(CallbackInfo ci) {
		this.pollinateFlowerCowGoal = new PollinateFlowerCowGoal(((BeeEntity)(Object)this));
		this.goalSelector.add(4, this.pollinateFlowerCowGoal);
	}

	@Inject(method="damage", at=@At("HEAD"))
	private void CancelOnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!this.isInvulnerableTo(source)) {
			if (!this.world.isClient()) {
				if (this.pollinateFlowerCowGoal != null) this.pollinateFlowerCowGoal.cancel();
			}
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.BEE_BLOOD_TYPE; }
}
