package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.passive.cow.BlueMooshroomEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MooshroomEntity.class)
public abstract class MooshroomEntityMixin extends AnimalEntity implements EntityWithBloodType {
	@Shadow protected abstract void setType(MooshroomEntity.Type type);
	@Shadow public abstract MooshroomEntity.Type getMooshroomType();

	protected MooshroomEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

	@Override
	protected void initGoals() {
		super.initGoals();
		if (getClass().equals(MooshroomEntity.class)) {
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, MooshroomEntity.class));
			this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D, BlueMooshroomEntity.class));
		}
	}

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		Class<?> otherClass = other.getClass();
		if (otherClass != this.getClass()
				&& otherClass != MooshroomEntity.class
				&& otherClass != BlueMooshroomEntity.class
		) return false;
		else return this.isInLove() && other.isInLove();
	}

	@Inject(method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/MooshroomEntity;", at = @At("HEAD"), cancellable = true)
	public void createChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<MooshroomEntity> cir) {
		if (passiveEntity instanceof BlueMooshroomEntity) {
			MooshroomEntity mooshroomEntity = EntityType.MOOSHROOM.create(serverWorld);
			((MooshroomEntityMixin)(Object)mooshroomEntity).setType(this.getMooshroomType());
			cir.setReturnValue(mooshroomEntity);
		}
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.MOOSHROOM_BLOOD_TYPE; }
}
