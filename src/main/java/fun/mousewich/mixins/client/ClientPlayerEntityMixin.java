package fun.mousewich.mixins.client;

import com.mojang.authlib.GameProfile;
import fun.mousewich.ModBase;
import fun.mousewich.effect.ModStatusEffects;
import fun.mousewich.enchantment.SwiftSneakEnchantment;
import fun.mousewich.entity.passive.camel.CamelEntity;
import fun.mousewich.origins.power.SneakSpeedPower;
import fun.mousewich.util.ClientMixinStore;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.JumpingMount;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) { super(world, profile); }
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

	@Inject(method="hasJumpingMount", at = @At("HEAD"), cancellable = true)
	public void HasJumpingMount(CallbackInfoReturnable<Boolean> cir) {
		Entity entity = this.getVehicle();
		if (this.hasVehicle() && entity instanceof JumpingMount mount && mount.canJump()) {
			if (mount instanceof CamelEntity camel) cir.setReturnValue(camel.getJumpCooldown() == 0);
		}
	}

	@Inject(at = @At("HEAD"), method = "getUnderwaterVisibility", cancellable = true)
	private void GetGoggledUnderwaterVisibility(CallbackInfoReturnable<Float> cir) {
		if (this.hasStatusEffect(ModStatusEffects.SAPPHIRE_GOGGLES) || this.getEquippedStack(EquipmentSlot.HEAD).isOf(ModBase.SAPPHIRE_GOGGLES)) {
			cir.setReturnValue(1.0F);
		}
	}
}
