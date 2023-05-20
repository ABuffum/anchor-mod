package fun.mousewich.mixins.client;

import fun.mousewich.util.ClientMixinStore;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
	@Final
	@Shadow
	private GameOptions settings;

	@Inject(method="tick", at = @At("HEAD"), cancellable = true)
	public void tick(boolean slowDown, CallbackInfo ci) {
		this.pressingForward = this.settings.keyForward.isPressed();
		this.pressingBack = this.settings.keyBack.isPressed();
		this.pressingLeft = this.settings.keyLeft.isPressed();
		this.pressingRight = this.settings.keyRight.isPressed();
		this.movementForward = getMovementMultiplier(this.pressingForward, this.pressingBack);
		this.movementSideways = getMovementMultiplier(this.pressingLeft, this.pressingRight);
		this.jumping = this.settings.keyJump.isPressed();
		this.sneaking = this.settings.keySneak.isPressed();
		if (slowDown) {
			float f = ClientMixinStore.clientPlayerSneakSpeed;
			this.movementSideways *= f;
			this.movementForward *= f;
		}
		ci.cancel();
	}

	private static float getMovementMultiplier(boolean positive, boolean negative) {
		return positive == negative ? 0 : positive ? 1 : -1;
	}
}
