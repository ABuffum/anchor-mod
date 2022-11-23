package haven.mixins.client;

import ca.weblite.objc.Client;
import com.mojang.authlib.GameProfile;
import haven.enchantments.SwiftSneakEnchantment;
import haven.origins.powers.SneakSpeedPower;
import haven.util.ClientMixinStore;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Shadow
	private Hand activeHand;

	@Inject(method="getActiveHand", at = @At("RETURN"), cancellable = true)
	public void GetActiveHand(CallbackInfoReturnable<Hand> cir) {
		Hand hand = cir.getReturnValue();
		cir.setReturnValue((hand == null ? activeHand : hand) == Hand.OFF_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);
	}

	@Inject(method="tickMovement", at = @At(value="INVOKE", target="Lnet/minecraft/client/input/Input;tick(Z)V"))
	public void TickMovement(CallbackInfo ci) {
		ClientMixinStore.clientPlayerSneakSpeed = MathHelper.clamp(0.3f + SwiftSneakEnchantment.getSpeedBoost(this), 0.0f, 1.0f)
			* SneakSpeedPower.getSpeedMultiplier(this).orElse(1F);
	}
}
