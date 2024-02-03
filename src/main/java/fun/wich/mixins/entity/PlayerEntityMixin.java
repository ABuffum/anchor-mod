package fun.wich.mixins.entity;

import fun.wich.ModBase;
import fun.wich.ModId;
import fun.wich.block.sculk.SculkShriekerWarningManager;
import fun.wich.damage.ModDamageSource;
import fun.wich.effect.ModStatusEffects;
import fun.wich.enchantment.ModEnchantments;
import fun.wich.entity.LastDeathPositionStoring;
import fun.wich.entity.LastJavelinStoring;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.hostile.warden.WardenEntity;
import fun.wich.entity.projectile.JavelinEntity;
import fun.wich.event.ModGameEvent;
import fun.wich.item.tool.HammerItem;
import fun.wich.origins.power.*;
import fun.wich.sound.IdentifiedSounds;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements LastDeathPositionStoring, EntityWithBloodType, LastJavelinStoring {
	@Shadow public abstract void disableShield(boolean sprinting);
	@Shadow public abstract ItemCooldownManager getItemCooldownManager();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) { super(entityType, world); }

	private Optional<GlobalPos> lastDeathPos = Optional.empty();
	@Override public Optional<GlobalPos> getLastDeathPos() { return this.lastDeathPos; }
	@Override public void setLastDeathPos(Optional<GlobalPos> lastDeathPos) { this.lastDeathPos = lastDeathPos; }

	@Override public BloodType GetDefaultBloodType() { return BloodType.PLAYER; }

	@Inject(method="tick", at = @At("HEAD"))
	public void Tick(CallbackInfo ci) {
		if (!this.world.isClient) SculkShriekerWarningManager.getSculkShriekerWarningManager((PlayerEntity)(Object)(this)).tick();

		List<FluidBreatherPower> powers = PowerHolderComponent.getPowers(this, FluidBreatherPower.class)
				.stream().filter(power -> power.isActive() && (power.suffocate || power.dryout)).toList();
		if (powers.size() > 0) {
			if (StatusEffectUtil.hasWaterBreathing(this)
					//If submerged, it counts
					|| powers.stream().anyMatch(power -> this.isSubmergedIn(power.fluid)
					//If it has the power to gain from touching the fluid, it counts
					|| (power.touch && power.touchingFluid()))) {
				if (this.getAir() < this.getMaxAir()) this.setAir(this.getNextAirOnLand(this.getAir()));
				return;
			}
			//If touching the fluid, don't LOSE air, just don't gain any until submerged
			if (powers.stream().anyMatch(FluidBreatherPower::touchingFluid)) {
				int landGain = this.getNextAirOnLand(0);
				this.setAir(this.getAir() - landGain);
				return;
			}
			int landGain = this.getNextAirOnLand(0);
			this.setAir(this.getNextAirUnderwater(this.getAir()) - landGain);
			if (this.getAir() == -20) {
				this.setAir(0);
				for(int i = 0; i < 8; ++i) {
					double f = this.random.nextDouble() - this.random.nextDouble();
					double g = this.random.nextDouble() - this.random.nextDouble();
					double h = this.random.nextDouble() - this.random.nextDouble();
					this.world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(0.5), this.getEyeY() + this.random.nextGaussian() * 0.08D, this.getParticleZ(0.5), f * 0.5F, g * 0.5F + 0.25F, h * 0.5F);
				}
				if (powers.stream().anyMatch(p -> p.suffocate)) this.damage(ModDamageSource.SUFFOCATION, 2.0F);
				if (powers.stream().anyMatch(p -> p.dryout)) this.damage(DamageSource.DRYOUT, 2.0F);
			}
		}
	}

	@Inject(method="onDeath", at = @At("TAIL"))
	public void OnDeath(DamageSource source, CallbackInfo ci) {
		this.setLastDeathPos(Optional.of(GlobalPos.create(this.world.getRegistryKey(), this.getBlockPos())));
	}

	@Inject(method="getFallSound", at = @At("HEAD"), cancellable = true)
	public void GetFallSounds(int distance, CallbackInfoReturnable<SoundEvent> cir) {
		IdentifiedSounds.FallSounds fallSounds = IdentifiedSounds.getFallSounds(this);
		if (fallSounds != null) {
			if (distance > 4) cir.setReturnValue(fallSounds.big());
			else cir.setReturnValue(fallSounds.small());
		}
	}

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
	@Inject(method="getHighSpeedSplashSound", at = @At("HEAD"), cancellable = true)
	protected void GetHighSpeedSplashSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getHighSpeedSplashSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void GetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getDeathSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}
	@Inject(method="takeShieldHit", at = @At("HEAD"), cancellable = true)
	protected void TakeEnchantedShieldHit(LivingEntity attacker, CallbackInfo ci) {
		ItemStack stack = attacker.getMainHandStack();
		int level = 0;
		if (attacker.getMainHandStack().getItem() instanceof AxeItem) {
			level = EnchantmentHelper.getLevel(ModEnchantments.CLEAVING, stack);
		}
		if (attacker.getMainHandStack().getItem() instanceof HammerItem) {
			level = EnchantmentHelper.getLevel(ModEnchantments.CRUSHING, stack);
		}
		if (level > 0) {
			super.takeShieldHit(attacker);
			float f = 1 + (float)EnchantmentHelper.getEfficiency(this) * 0.05f;
			if (this.random.nextFloat() < f) {
				this.getItemCooldownManager().set(Items.SHIELD, 100 + 10 * level);
				this.clearActiveItem();
				this.world.sendEntityStatus(this, (byte)30);
			}
			ci.cancel();
		}
	}
	@Inject(method="takeShieldHit", at = @At("TAIL"))
	protected void TakeWardenShieldHit(LivingEntity attacker, CallbackInfo ci) {
		if (attacker instanceof WardenEntity) this.disableShield(true);
	}

	@Inject(method="updateTurtleHelmet", at = @At("HEAD"))
	private void UpdateGoggles(CallbackInfo ci) {
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
		boolean first, second = false;
		if ((first = itemStack.isOf(ModBase.TINTED_GOGGLES)) || (second = itemStack.isOf(ModBase.RUBY_GOGGLES)) || itemStack.isOf(ModBase.SAPPHIRE_GOGGLES)) {
			if (!this.hasStatusEffect(ModStatusEffects.DARKNESS) && !this.hasStatusEffect(StatusEffects.BLINDNESS)) {
				StatusEffect effect = first ? ModStatusEffects.TINTED_GOGGLES : second ? ModStatusEffects.RUBY_GOGGLES : ModStatusEffects.SAPPHIRE_GOGGLES;
				this.addStatusEffect(new StatusEffectInstance(effect, 20, 0, false, false, true));
			}
			if (!this.world.isClient && this.hasStatusEffect(ModStatusEffects.FLASHBANGED)) this.removeStatusEffect(ModStatusEffects.FLASHBANGED);
		}
		else if (!this.world.isClient) {
			if (this.hasStatusEffect(ModStatusEffects.TINTED_GOGGLES)) this.removeStatusEffect(ModStatusEffects.TINTED_GOGGLES);
			if (this.hasStatusEffect(ModStatusEffects.RUBY_GOGGLES)) this.removeStatusEffect(ModStatusEffects.RUBY_GOGGLES);
			if (this.hasStatusEffect(ModStatusEffects.SAPPHIRE_GOGGLES)) this.removeStatusEffect(ModStatusEffects.SAPPHIRE_GOGGLES);
		}
	}

	@Inject(method="interact", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/item/ItemStack;useOnEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"))
	public void InjectEntityInteractEvent(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		this.world.emitGameEvent(ModGameEvent.ENTITY_INTERACT, entity.getBlockPos());
	}
	private static void DropAll(PlayerEntity player, IInventoryPower inventory) {
		for (int i = 0; i < inventory.size(); ++i) {
			ItemStack itemStack = inventory.getStack(i);
			if (inventory.shouldDropOnDeath(itemStack)) {
				if (!itemStack.isEmpty() && EnchantmentHelper.hasVanishingCurse(itemStack)) inventory.removeStack(i);
				else {
					player.dropItem(itemStack, true, false);
					inventory.setStack(i, ItemStack.EMPTY);
				}
			}
		}
	}
	@Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"))
	private void DropAdditionalInventoryFromSingleSlotInventory(CallbackInfo ci) {
		PlayerEntity player = (PlayerEntity)(Object)this;
		PowerHolderComponent.getPowers(this, SingleSlotInventoryPower.class).forEach(inventory -> DropAll(player, inventory));
	}
	@Inject(method = "dropInventory", at = @At("RETURN"))
	private void DropAdditionalDroppableInventory(CallbackInfo ci) {
		PlayerEntity player = (PlayerEntity)(Object)this;
		PowerHolderComponent.getPowers(this, DroppableInventoryPower.class).forEach(inventory -> DropAll(player, inventory));
	}

	@Inject(method="writeCustomDataToNbt", at=@At("TAIL"))
	private void WriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		this.getLastDeathPos().flatMap(globalPos -> GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, globalPos)
				.resultOrPartial(ModId.LOGGER::error)).ifPresent(nbtElement -> nbt.put(ModNbtKeys.LAST_DEATH_LOCATION, nbtElement));
	}
	@Inject(method="readCustomDataFromNbt", at=@At("TAIL"))
	private void ReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains(ModNbtKeys.LAST_DEATH_LOCATION, NbtElement.COMPOUND_TYPE)) {
			this.setLastDeathPos(GlobalPos.CODEC.parse(NbtOps.INSTANCE, nbt.get("LastDeathLocation")).resultOrPartial(ModId.LOGGER::error));
		}
	}

	private int healingExperience = 0;
	@Inject(method="addExperience", at=@At("TAIL"))
	public void AddHealingExperience(int experience, CallbackInfo ci) {
		int threshold = ExperienceHealingPower.getThreshold(this);
		if (threshold > 0 && !this.isDead()) {
			this.healingExperience += experience;
			if (this.healingExperience > threshold) {
				this.heal((float)(this.healingExperience / threshold));
				this.healingExperience %= threshold;
			}
		}
		else this.healingExperience = 0;
	}

	private JavelinEntity javelin;
	@Override
	public JavelinEntity getLastJavelin() { return this.javelin; }
	@Override
	public void setLastJavelin(JavelinEntity entity) { this.javelin = entity; }
}
