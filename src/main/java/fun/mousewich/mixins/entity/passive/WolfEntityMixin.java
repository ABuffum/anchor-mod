package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.variants.WolfVariant;
import fun.mousewich.event.ModGameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable, EntityWithBloodType {
	protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow private boolean furWet;
	@Shadow private boolean canShakeWaterOff;
	@Shadow private float shakeProgress;

	@Inject(method="tick", at = @At("HEAD"))
	public void tick(CallbackInfo ci) {
		if (this.isAlive() && !this.isWet()) {
			if ((this.furWet || this.canShakeWaterOff) && this.canShakeWaterOff) {
				if (this.shakeProgress == 0.0f) this.emitGameEvent(ModGameEvent.ENTITY_SHAKE);
			}
		}
	}

	@Inject(method="initDataTracker", at = @At("TAIL"))
	protected void AddVariantToDataTracker(CallbackInfo ci) { this.dataTracker.startTracking(WolfVariant.VARIANT, 0); }

	@Inject(at=@At(value="RETURN"), method="createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/WolfEntity;")
	public void CreateChild(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<WolfEntity> cir) {
		WolfEntity child = cir.getReturnValue();
		if (child != null) {
			int variant = WolfVariant.getVariant((WolfEntity)(Object)this);
			int otherVariant = WolfVariant.getVariant((WolfEntity)passiveEntity);
			if (variant == otherVariant) {
				boolean darker = random.nextInt(10) == 0;
				if (variant == WolfVariant.DEFAULT.ordinal()) WolfVariant.setVariant(child, darker ? WolfVariant.GRAY.ordinal() : variant);
				else if (variant == WolfVariant.GRAY.ordinal()) WolfVariant.setVariant(child, darker ? WolfVariant.BLACK.ordinal() : variant);
				else WolfVariant.setVariant(child, variant);
			}
			else WolfVariant.setVariant(child, random.nextBoolean() ? variant : otherVariant);
		}
	}
	@Inject(method="writeCustomDataToNbt", at = @At("TAIL"))
	public void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putInt("Variant", this.getDataTracker().get(WolfVariant.VARIANT));
	}

	@Inject(method="readCustomDataFromNbt", at = @At("TAIL"))
	public void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains("Variant")) this.dataTracker.set(WolfVariant.VARIANT, nbt.getInt("Variant"));
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.WOLF_BLOOD_TYPE; }
}
