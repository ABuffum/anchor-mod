package haven.mixins.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
		super(world, profile);
	}

	@Shadow
	private Hand activeHand;

	@Inject(method="getActiveHand", at = @At("RETURN"), cancellable = true)
	public void getActiveHand(CallbackInfoReturnable<Hand> cir) {
		Hand hand = cir.getReturnValue();
		cir.setReturnValue((hand == null ? activeHand : hand) == Hand.OFF_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);
	}
}
