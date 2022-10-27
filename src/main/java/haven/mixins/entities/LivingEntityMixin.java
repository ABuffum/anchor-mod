package haven.mixins.entities;

import com.google.common.collect.Maps;
import haven.HavenTags;
import haven.effects.DarknessEffect;
import haven.effects.FlashbangedEffect;
import haven.effects.HavenStatusEffectInstance;
import haven.events.HavenGameEvent;
import haven.origins.powers.UnfreezingPower;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="canFreeze", at = @At("HEAD"), cancellable = true)
	private void CannotFreeze(CallbackInfoReturnable<Boolean> cir) {
		if (UnfreezingPower.HasActivePower(this)) cir.setReturnValue(false);
	}

	@Inject(method="getPreferredEquipmentSlot", at=@At("HEAD"), cancellable = true)
	private static void GetPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if (stack.isIn(HavenTags.Items.HEAD_WEARABLE_BLOCKS)) cir.setReturnValue(EquipmentSlot.HEAD);
	}

	@Inject(method="getStackInHand", at = @At("HEAD"), cancellable = true)
	public void GetStackInHand(Hand hand, CallbackInfoReturnable<ItemStack> cir) {
		if (hand == null) cir.setReturnValue(((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.MAINHAND));
	}

	@Inject(method="onDeath", at = @At("HEAD"))
	public void OnDeath(DamageSource source, CallbackInfo ci) {
		if (!this.isRemoved() && !((LivingEntityAccessor)this).GetDead() && this.world instanceof ServerWorld) {
			this.emitGameEvent(HavenGameEvent.ENTITY_DIE);
			this.emitGameEvent(GameEvent.ENTITY_KILLED);
		}
	}

	@Inject(method="applyDamage", at = @At(value = "INVOKE", target="Lnet/minecraft/entity/LivingEntity;setAbsorptionAmount(F)V", ordinal = 1))
	protected void ApplyDamage(DamageSource source, float amount, CallbackInfo ci) {
		this.emitGameEvent(HavenGameEvent.ENTITY_DAMAGE);
	}

	@Inject(method="tickFallFlying", at = @At("TAIL"))
	private void TickFallFlying(CallbackInfo ci) {
		boolean bl = this.getFlag(Entity.FALL_FLYING_FLAG_INDEX);
		LivingEntity le = (LivingEntity)(Object)this;
		if (bl && !this.onGround && !this.hasVehicle() && !le.hasStatusEffect(StatusEffects.LEVITATION)) {
			ItemStack itemStack = le.getEquippedStack(EquipmentSlot.CHEST);
			if (itemStack.isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack)) {
				int i = ((LivingEntityAccessor)le).GetRoll() + 1;
				if (!this.world.isClient && i % 10 == 0) this.emitGameEvent(HavenGameEvent.ELYTRA_GLIDE);
			}
		}
	}

	@Inject(method="clearActiveItem", at = @At("HEAD"))
	public void ClearActiveItem(CallbackInfo ci) {
		if (!this.world.isClient) {
			if (((LivingEntity)(Object)this).isUsingItem()) this.emitGameEvent(HavenGameEvent.ITEM_INTERACT_FINISH);
		}
	}

	@Inject(method="setCurrentHand", at = @At("HEAD"))
	public void SetCurrentHand(Hand hand, CallbackInfo ci) {
		LivingEntity le = (LivingEntity)(Object)this;
		if (le.getStackInHand(hand).isEmpty() || le.isUsingItem()) return;
		if (!this.world.isClient) this.emitGameEvent(HavenGameEvent.ITEM_INTERACT_START);
	}
}
