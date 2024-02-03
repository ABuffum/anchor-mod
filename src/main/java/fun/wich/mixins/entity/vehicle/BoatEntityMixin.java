package fun.wich.mixins.entity.vehicle;

import fun.wich.sound.IdentifiedSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity {
	public BoatEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="getPaddleSoundEvent", at = @At("HEAD"), cancellable = true)
	protected void GetPaddleSoundEvent(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent paddleSound = IdentifiedSounds.getPaddleSound((BoatEntity)(Object)this);
		if (paddleSound != null) cir.setReturnValue(paddleSound);
	}
}
