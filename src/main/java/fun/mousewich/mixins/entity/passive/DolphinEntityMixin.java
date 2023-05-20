package fun.mousewich.mixins.entity.passive;

import fun.mousewich.ModBase;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.sound.IdentifiedSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DolphinEntity.class)
public abstract class DolphinEntityMixin extends WaterCreatureEntity implements EntityWithBloodType {
	protected DolphinEntityMixin(EntityType<? extends DolphinEntity> entityType, World world) { super(entityType, world); }

	@Inject(method="getSwimSound", at = @At("HEAD"), cancellable = true)
	protected void GetSwimSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSwimSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getSplashSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.DOLPHIN_BLOOD_TYPE; }
}
