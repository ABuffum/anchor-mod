package fun.wich.mixins.entity;

import fun.wich.ModBase;
import fun.wich.block.basic.ModLadderBlock;
import fun.wich.effect.ModStatusEffects;
import fun.wich.enchantment.CommittedEnchantment;
import fun.wich.enchantment.ModEnchantments;
import fun.wich.entity.EntityWithAttackStreak;
import fun.wich.entity.RavagerRideableCompatibilityHook;
import fun.wich.entity.projectile.ModArrowEntity;
import fun.wich.event.ModGameEvent;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.gen.data.tag.ModItemTags;
import fun.wich.item.OxidizableItem;
import fun.wich.origins.power.*;
import fun.wich.sound.IdentifiedSounds;
import fun.wich.sound.ModSoundEvents;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements EntityWithAttackStreak {
	@Shadow
	protected int roll;
	@Shadow protected boolean dead;
	@Shadow private LivingEntity attacking;
	@Shadow protected PlayerEntity attackingPlayer;
	@Shadow public abstract float getHealth();
	@Shadow public abstract float getMaxHealth();
	@Shadow public abstract boolean isUsingItem();
	@Shadow public abstract float getArmorVisibility();
	@Shadow public abstract ItemStack getStackInHand(Hand hand);
	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);
	@Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, Entity source);
	@Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
	@Shadow public abstract boolean damage(DamageSource source, float amount);
	@Shadow protected abstract void damageShield(float amount);

	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="canFreeze", at = @At("HEAD"), cancellable = true)
	private void CannotFreeze(CallbackInfoReturnable<Boolean> cir) {
		if (this.hasStatusEffect(ModStatusEffects.FREEZING_RESISTANCE)) cir.setReturnValue(false);
		if (PowersUtil.Active(this, CannotFreezePower.class)) cir.setReturnValue(false);
	}

	@Inject(method="getPreferredEquipmentSlot", at=@At("HEAD"), cancellable = true)
	private static void GetPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if (stack.isIn(ModItemTags.HEAD_WEARABLE_BLOCKS)) cir.setReturnValue(EquipmentSlot.HEAD);
	}

	@Inject(method="getStackInHand", at = @At("HEAD"), cancellable = true)
	public void GetStackInHand(Hand hand, CallbackInfoReturnable<ItemStack> cir) {
		if (hand == null) cir.setReturnValue(this.getEquippedStack(EquipmentSlot.MAINHAND));
	}

	@Override
	public boolean isSilent() { return this.hasStatusEffect(ModStatusEffects.SILENT) || super.isSilent(); }

	@Override
	public boolean occludeVibrationSignals() {
		return PowersUtil.Active(this, SoftStepsPower.class) || super.occludeVibrationSignals();
	}

	@Inject(method="onDeath", at = @At("HEAD"))
	public void OnDeath(DamageSource source, CallbackInfo ci) {
		if (!this.isRemoved() && !this.dead && this.world instanceof ServerWorld) {
			this.emitGameEvent(ModGameEvent.ENTITY_DIE);
			this.emitGameEvent(GameEvent.ENTITY_KILLED);
		}
	}
	@Inject(method="getDeathSound", at = @At("HEAD"), cancellable = true)
	protected void GetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
		SoundEvent sound = IdentifiedSounds.getDeathSound(this);
		if (sound != null) cir.setReturnValue(sound);
	}

	@Inject(method="applyDamage", at = @At(value = "INVOKE", target="Lnet/minecraft/entity/LivingEntity;setAbsorptionAmount(F)V", ordinal = 1))
	protected void ApplyRushSpeed(DamageSource source, float amount, CallbackInfo ci) {
		int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.RUSH, (LivingEntity)(Object)this);
		if (level > 0 && !this.world.isClient) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20, level - 1, false, false, true), this);
		}
	}


	@Inject(method="tickFallFlying", at = @At("TAIL"))
	private void TickFallFlying(CallbackInfo ci) {
		boolean bl = this.getFlag(Entity.FALL_FLYING_FLAG_INDEX);
		if (bl && !this.onGround && !this.hasVehicle() && !this.hasStatusEffect(StatusEffects.LEVITATION)) {
			ItemStack itemStack = this.getEquippedStack(EquipmentSlot.CHEST);
			if (itemStack.isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack)) {
				if (!this.world.isClient && this.roll + 1 % 10 == 0) this.emitGameEvent(ModGameEvent.ELYTRA_GLIDE);
			}
		}
	}
	@Inject(method="tickMovement", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;getFrozenTicks()I"))
	private void FreezeWhenCold(CallbackInfo ci) {
		if (ModBase.EASY_FREEZE_POWER.isActive(this)) {
			Vec3d vec = this.getPos();
			BlockPos pos = this.getBlockPos();
			double x = vec.getX(), y = vec.getY(), z = vec.getZ(), w = this.getWidth();
			if (HasSnow(world, pos)
					|| this.world.getBlockState(pos).isIn(ModBlockTags.COLD_BLOCKS)
					|| this.getLandingBlockState().isIn(ModBlockTags.COLD_BLOCKS)
					|| this.world.getBlockState(new BlockPos(x + w, y, z)).isIn(ModBlockTags.COLD_BLOCKS)
					|| this.world.getBlockState(new BlockPos(x - w, y, z)).isIn(ModBlockTags.COLD_BLOCKS)
					|| this.world.getBlockState(new BlockPos(x, y, z + w)).isIn(ModBlockTags.COLD_BLOCKS)
					|| this.world.getBlockState(new BlockPos(x, y, z - w)).isIn(ModBlockTags.COLD_BLOCKS)) {
				this.setInPowderSnow(true);
			}
		}
	}
	private static boolean HasSnow(World world, BlockPos pos) {
		if (!world.isRaining()) return false;
		if (!world.isSkyVisible(pos)) return false;
		if (world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > pos.getY()) return false;
		Biome biome = world.getBiome(pos);
		return biome.getPrecipitation() == Biome.Precipitation.SNOW || biome.isCold(pos);
	}
	@Inject(method="clearActiveItem", at = @At("HEAD"))
	public void ClearActiveItem(CallbackInfo ci) {
		if (!this.world.isClient && (this.isUsingItem())) this.emitGameEvent(ModGameEvent.ITEM_INTERACT_FINISH);
	}
	@Inject(method="setCurrentHand", at = @At("HEAD"))
	public void SetCurrentHand(Hand hand, CallbackInfo ci) {
		if (this.getStackInHand(hand).isEmpty() || this.isUsingItem()) return;
		if (!this.world.isClient) this.emitGameEvent(ModGameEvent.ITEM_INTERACT_START);
	}

	@Inject(method="canBreatheInWater", at = @At("HEAD"), cancellable = true)
	public void CanBreatheInWater(CallbackInfoReturnable<Boolean> cir) {
		if (FluidBreatherPower.Applies(this, Fluids.WATER, Fluids.FLOWING_WATER)) cir.setReturnValue(true);
	}

	@Inject(method="getNextAirUnderwater", at = @At("HEAD"), cancellable = true)
	public void GetNextAirUnderwater(int air, CallbackInfoReturnable<Integer> cir) {
		int respiration = BiggerLungsPower.getRespiration(this);
		if (respiration > 0 && this.random.nextInt(respiration + 1) > 0) {
			cir.setReturnValue(air);
		}
	}

	@ModifyArg(method="travel", at = @At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
	private SoundEvent CrashSound(SoundEvent par1) {
		SoundEvent crashSound = IdentifiedSounds.getCrashSound(this);
		if (crashSound != null) return crashSound;
		return ModSoundEvents.ENTITY_GENERIC_ELYTRA_CRASH;
	}

	@Inject(method="canEnterTrapdoor", at = @At("HEAD"), cancellable = true)
	private void CanEnterTrapdoor(BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.get(TrapdoorBlock.OPEN)) {
			BlockState blockState = this.world.getBlockState(pos.down());
			if (blockState.getBlock() instanceof ModLadderBlock) {
				cir.setReturnValue(blockState.get(LadderBlock.FACING) == state.get(TrapdoorBlock.FACING));
			}
		}
	}

	@Inject(method="getAttackDistanceScalingFactor", at = @At("HEAD"), cancellable = true)
	public void GetAttackDistanceScalingFactor(Entity entity, CallbackInfoReturnable<Double> cir) {
		if (entity != null) {
			ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
			if (itemStack.isOf(ModBase.PIGLIN_HEAD.asItem())) {
				double d = 1.0;
				if (this.isSneaky()) d *= 0.8;
				if (this.isInvisible()) d *= 0.7 * Math.max(0.1, this.getArmorVisibility());
				EntityType<?> entityType = entity.getType();
				if (entityType == EntityType.PIGLIN || entityType == EntityType.PIGLIN_BRUTE) cir.setReturnValue(d * 0.5);
			}
		}
	}

	@Inject(method="tickActiveItemStack", at=@At("TAIL"))
	public void UpdateOxidizableItems(CallbackInfo ci) {
		OxidizableItem.tryDegradeItems((LivingEntity)(Object)this, random);
	}

	@Inject(method="tick", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;method_30128()V"))
	public void OxidizeArmorInRain(CallbackInfo ci) {
		if (this.isWet()) {
			OxidizableItem.tryDegradeArmorInWater((LivingEntity)(Object)this, random);
		}
	}

	@Inject(method="tick", at=@At("HEAD"))
	public void FrenzyBelowHalfHealth(CallbackInfo ci) {
		int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.FRENZY, (LivingEntity)(Object)this);
		if (level > 0) {
			if (!this.world.isClient) {
				if (this.getHealth() < this.getMaxHealth() / 2) {
					this.addStatusEffect(new StatusEffectInstance(ModStatusEffects.FRENZIED, 20, level - 1, false, false, true), this);
				}
			}
		}
	}

	@Override
	public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
		super.onStruckByLightning(world, lightning);
		OxidizableItem.tryDeoxidizeItems((LivingEntity)(Object)this);
	}

	protected int attackStreakOnTarget;
	@Override
	public int getAttackStreakOnTarget() { return this.attackStreakOnTarget; }

	@Inject(method="onAttacking", at=@At("HEAD"))
	private void SaveLastAttacking(Entity target, CallbackInfo ci) {
		if (target == null) attackStreakOnTarget = 0;
		else if (this.attacking == null) attackStreakOnTarget = 1;
		else if (this.attacking == target) attackStreakOnTarget++;
		else attackStreakOnTarget = 1;
	}

	@ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
	private float IncreaseDamageFromCommittedAttacker(float originalValue, DamageSource source, float amount) {
		float newValue = originalValue;
		LivingEntity user = null;
		if (source.getAttacker() instanceof LivingEntity temp) user = temp;
		else if (source.getSource() instanceof LivingEntity temp) user = temp;
		if (user != null) {
			float boost = CommittedEnchantment.getBoost(user, this);
			if (boost > 0) newValue *= (1 + boost);
		}
		return newValue;
	}

	@Inject(method="damage", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;damageShield(F)V"))
	private void HandleShieldDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		//Attempt to recycle arrows
		int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.RECYLING, (LivingEntity)(Object)this);
		if (level > 0) {
			ItemStack stack = ItemStack.EMPTY;
			if (source.isProjectile() && random.nextFloat() < (0.2f * level)) {
				Entity projectile = source.getSource();
				if (projectile instanceof ModArrowEntity arrow) stack = arrow.asItemStack();
				else if (projectile instanceof ArrowEntity) stack = new ItemStack(Items.ARROW);
				if (!stack.isEmpty()) {
					//noinspection ConstantConditions
					if ((Object)this instanceof PlayerEntity player) {
						PlayerInventory inventory = player.getInventory();
						if (inventory.getEmptySlot() > 0) inventory.insertStack(stack);
						else player.dropItem(stack, false);
						if (projectile != null) projectile.discard();
					}
				}
			}
		}
		//Damage shield (note, we are explicitly calling damage here and skipping the regular call)
		float damageAmount = amount;
		if (source.getAttacker() instanceof LivingEntity attacker) {
			ItemStack stack = attacker.getMainHandStack();
			if (EnchantmentHelper.getLevel(ModEnchantments.CLEAVING, stack) > 0) damageAmount += level;
			else if (EnchantmentHelper.getLevel(ModEnchantments.CRUSHING, stack) > 0) damageAmount += level;
		}
		this.damageShield(damageAmount);
	}
	@Redirect(method="damage", at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;damageShield(F)V"))
	private void SkipBuiltInDamageCall(LivingEntity instance, float amount) { }

	@Redirect(method="dropXp", at=@At(value="INVOKE", target="Lnet/minecraft/entity/ExperienceOrbEntity;spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;I)V"))
	private void SiphonExperience(ServerWorld world, Vec3d pos, int amount) {
		int newAmount = amount;
		if (this.attackingPlayer != null) {
			int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.EXPERIENCE_SIPHON, this.attackingPlayer);
			if (level > 0) newAmount += level;
			float powerModifier = ExperienceSiphonPower.getModifier(this.attackingPlayer);
			newAmount = (int)(newAmount * powerModifier);
		}
		ExperienceOrbEntity.spawn(world, pos, newAmount);
	}

	@Redirect(method="getVelocityMultiplier", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getEquipmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)I"))
	private int CheckSoulSpeed_getVelocityMultiplier(Enchantment enchantment, LivingEntity entity) { return SoulSpeedPower.getLevel(entity); }
	@Redirect(method="addSoulSpeedBoostIfNeeded", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getEquipmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)I"))
	private int CheckSoulSpeed_addSoulSpeedBoostIfNeeded(Enchantment enchantment, LivingEntity entity) { return SoulSpeedPower.getLevel(entity); }

	@Inject(method="travel", at=@At("HEAD"), cancellable=true)
	private void LetPlayersRiveRavagers(Vec3d movementInput, CallbackInfo ci) {
		if (this instanceof RavagerRideableCompatibilityHook rideable && rideable.RavagerRidableCompatibleTravel(movementInput)) ci.cancel();
	}

	@Inject(method="damage", at=@At("RETURN"))
	public void AngerAlliesOnBehalf(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) return;
		if (!(this.world instanceof ServerWorld)) return;
		if (!(source.getAttacker() instanceof LivingEntity target)) return;
		if (PowersUtil.Active(this, ZombifiedPiglinAllyPower.class)) {
			ZombifiedPiglinAllyPower.AngerNearbyPiglins((LivingEntity)(Object)this, target, 35);
		}
	}

	@Inject(method="getAttackDistanceScalingFactor", at=@At("RETURN"), cancellable=true)
	public void AdjustAttackDistanceScalingFactorForModSkulls(Entity entity, CallbackInfoReturnable<Double> cir) {
		double d = cir.getReturnValue();
		if (entity != null) {
			ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
			EntityType<?> entityType = entity.getType();
			if (entityType == EntityType.PIGLIN && itemStack.isOf(ModBase.PIGLIN_HEAD.asItem())
					|| entityType == EntityType.PIGLIN_BRUTE && itemStack.isOf(ModBase.PIGLIN_HEAD.asItem())
					|| entityType == EntityType.ZOMBIFIED_PIGLIN && itemStack.isOf(ModBase.ZOMBIFIED_PIGLIN_HEAD.asItem())) {
				cir.setReturnValue(d * 0.5);
			}
		}
	}

	@Inject(method="getLootTable", at=@At("HEAD"), cancellable=true)
	public void InjectCustomLootTable(CallbackInfoReturnable<Identifier> cir) {
		List<CustomLootTablePower> customLootTables = PowerHolderComponent.getPowers(this, CustomLootTablePower.class)
				.stream().filter(x -> x.getLootTable() != null).toList();
		if (customLootTables.isEmpty()) return;
		cir.setReturnValue(customLootTables.get(0).getLootTable());
	}
}
