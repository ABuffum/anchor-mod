package fun.wich.entity.passive.camel;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Dynamic;
import fun.wich.ModBase;
import fun.wich.client.render.entity.animation.AnimationState;
import fun.wich.entity.ModDataHandlers;
import fun.wich.entity.ModEntityPose;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.entity.ai.ModMobNavigation;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class CamelEntity extends HorseBaseEntity implements JumpingMount, Saddleable, EntityWithBloodType {
	public static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.CACTUS);
	public static final TrackedData<Boolean> DASHING = DataTracker.registerData(CamelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public static final TrackedData<Long> LAST_POSE_TICK = DataTracker.registerData(CamelEntity.class, ModDataHandlers.LONG);
	public final AnimationState walkingAnimationState = new AnimationState();
	public final AnimationState sittingTransitionAnimationState = new AnimationState();
	public final AnimationState sittingAnimationState = new AnimationState();
	public final AnimationState standingTransitionAnimationState = new AnimationState();
	public final AnimationState idlingAnimationState = new AnimationState();
	public final AnimationState dashingAnimationState = new AnimationState();
	private static final EntityDimensions SITTING_DIMENSIONS = EntityDimensions.changing(ModEntityType.CAMEL_ENTITY.getWidth(), ModEntityType.CAMEL_ENTITY.getHeight() - 1.43f);
	private int dashCooldown = 0;
	private int idleAnimationCooldown = 0;

	public CamelEntity(EntityType<? extends CamelEntity> entityType, World world) {
		super(entityType, world);
		this.stepHeight = 1.5f;
		ModMobNavigation mobNavigation = (ModMobNavigation)this.getNavigation();
		mobNavigation.setCanSwim(true);
		mobNavigation.setCanWalkOverFences(true);
	}
	@Override
	protected EntityNavigation createNavigation(World world) { return new ModMobNavigation(this, world); }
	protected static final TrackedData<ModEntityPose> NEW_POSE = DataTracker.registerData(CamelEntity.class, ModDataHandlers.NEW_ENTITY_POSE);
	@Override
	public void setPose(EntityPose pose) {
		this.dataTracker.set(NEW_POSE, ModEntityPose.valueOf(pose.name()));
		super.setPose(pose);
	}
	public void SetPose(ModEntityPose pose) {
		this.dataTracker.set(NEW_POSE, pose);
		for(EntityPose p : EntityPose.values()) {
			if (p.name().equals(pose.name())) {
				super.setPose(p);
				return;
			}
		}
		super.setPose(EntityPose.STANDING);
	}
	public ModEntityPose GetPose() { return this.dataTracker.get(NEW_POSE); }
	public boolean IsInPose(ModEntityPose pose) { return GetPose() == pose; }

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("IsSitting", this.GetPose() == ModEntityPose.SITTING);
		nbt.putLong("LastPoseTick", this.dataTracker.get(LAST_POSE_TICK));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.getBoolean("IsSitting")) this.SetPose(ModEntityPose.SITTING);
		this.setLastPoseTick(nbt.getLong("LastPoseTick"));
	}

	public static DefaultAttributeContainer.Builder createCamelAttributes() {
		return CamelEntity.createBaseHorseAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.09f).add(EntityAttributes.HORSE_JUMP_STRENGTH, 0.42f);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DASHING, false);
		this.dataTracker.startTracking(LAST_POSE_TICK, -52L);
		this.dataTracker.startTracking(NEW_POSE, ModEntityPose.STANDING);
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
		this.dataTracker.set(LAST_POSE_TICK, world.toServerWorld().getTime() - 52L);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	protected Brain.Profile<CamelEntity> createBrainProfile() { return CamelBrain.createProfile(); }
	@SuppressWarnings("unchecked")
	public Brain<CamelEntity> getBrain() { return (Brain<CamelEntity>)super.getBrain(); }

	@Override
	protected void initGoals() { }

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return CamelBrain.create(this.createBrainProfile().deserialize(dynamic));
	}
	public EntityDimensions getDimensions(ModEntityPose pose) {
		if (pose == ModEntityPose.SITTING) return SITTING_DIMENSIONS.scaled(this.getScaleFactor());
		if (pose == ModEntityPose.SLEEPING) return SLEEPING_DIMENSIONS;
		return ModEntityType.CAMEL_ENTITY.getDimensions().scaled(this.getScaleFactor());
	}
	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return dimensions.height - 0.1f; }

	@Override
	protected void mobTick() {
		this.world.getProfiler().push("camelBrain");
		Brain<CamelEntity> brain = this.getBrain();
		brain.tick((ServerWorld)this.world, this);
		this.world.getProfiler().pop();
		this.world.getProfiler().push("camelActivityUpdate");
		CamelBrain.updateActivities(this);
		this.world.getProfiler().pop();
		super.mobTick();
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isDashing() && this.dashCooldown < 55 && (this.onGround || this.isTouchingWater())) this.setDashing(false);
		if (this.dashCooldown > 0) {
			--this.dashCooldown;
			if (this.dashCooldown == 0) this.world.playSound(null, this.getBlockPos(), ModSoundEvents.ENTITY_CAMEL_DASH_READY, SoundCategory.PLAYERS, 1, 1);
		}
		if (this.world.isClient()) this.updateAnimations();
	}
	public final boolean hasPrimaryPassenger() { return this.getPrimaryPassenger() != null; }
	private void updateAnimations() {
		if (this.idleAnimationCooldown <= 0) {
			this.idleAnimationCooldown = this.random.nextInt(40) + 80;
			this.idlingAnimationState.start(this.age);
		}
		else --this.idleAnimationCooldown;
		switch (this.GetPose()) {
			case STANDING -> {
				this.sittingTransitionAnimationState.stop();
				this.sittingAnimationState.stop();
				this.dashingAnimationState.setRunning(this.isDashing(), this.age);
				this.standingTransitionAnimationState.setRunning(this.isChangingPose(), this.age);
				this.walkingAnimationState.setRunning((this.onGround || this.hasPrimaryPassenger()) && this.getVelocity().horizontalLengthSquared() > 1.0E-6, this.age);
			}
			case SITTING -> {
				this.walkingAnimationState.stop();
				this.standingTransitionAnimationState.stop();
				this.dashingAnimationState.stop();
				if (this.shouldPlaySittingTransitionAnimation()) {
					this.sittingTransitionAnimationState.startIfNotRunning(this.age);
					this.sittingAnimationState.stop();
					break;
				}
				this.sittingTransitionAnimationState.stop();
				this.sittingAnimationState.startIfNotRunning(this.age);
			}
			default -> {
				this.walkingAnimationState.stop();
				this.sittingTransitionAnimationState.stop();
				this.sittingAnimationState.stop();
				this.standingTransitionAnimationState.stop();
				this.dashingAnimationState.stop();
			}
		}
	}
	@Override
	public void travel(Vec3d movementInput) {
		if (!this.isAlive()) return;
		if (this.isStationary() && this.isOnGround()) {
			this.setVelocity(this.getVelocity().multiply(0.0, 1.0, 0.0));
			movementInput = movementInput.multiply(0.0, 1.0, 0.0);
		}
		super.travel(movementInput);
	}
	public boolean isStationary() { return this.isSitting() || this.isChangingPose(); }
	public float getHorsebackMovementSpeed(LivingEntity passenger) {
		float f = passenger.isSprinting() && this.getJumpCooldown() == 0 ? 0.1f : 0.0f;
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) + f;
	}
	public boolean ignoresMovementInput(LivingEntity passenger) {
		if (this.isSitting() && !this.isChangingPose() && passenger.forwardSpeed > 0.0f) this.startStanding();
		return this.isStationary();
	}
	public boolean canJump(PlayerEntity player) {
		return !this.isStationary() && this.getPrimaryPassenger() == player && this.isSaddled();
	}
	@Override
	public void setJumpStrength(int strength) {
		if (!this.isSaddled() || this.dashCooldown > 0 || !this.isOnGround()) return;
		super.setJumpStrength(strength);
	}
	public void jump(float strength) {
		double d = this.getAttributeValue(EntityAttributes.HORSE_JUMP_STRENGTH) * (double)this.getJumpVelocityMultiplier() + this.getJumpBoostVelocityModifier();
		Vec3d add = this.getRotationVector().multiply(1.0, 0.0, 1.0).normalize().multiply((double)(22.2222f * strength) * this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * (double)this.getVelocityMultiplier()).add(0.0, (double)(1.4285f * strength) * d, 0.0);
		this.addVelocity(add.x, add.y, add.z);
		this.dashCooldown = 55;
		this.setDashing(true);
		this.velocityDirty = true;
	}
	public boolean isDashing() { return this.dataTracker.get(DASHING); }
	public void setDashing(boolean dashing) { this.dataTracker.set(DASHING, dashing); }
	public boolean isPanicking() {
		return this.getBrain().isMemoryInState(ModMemoryModules.IS_PANICKING, MemoryModuleState.VALUE_PRESENT);
	}
	@Override
	public void startJumping(int height) {
		this.playSound(ModSoundEvents.ENTITY_CAMEL_DASH, 1.0f, 1.0f);
		this.setDashing(true);
	}
	@Override
	public void stopJumping() { }
	public int getJumpCooldown() { return this.dashCooldown; }
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_CAMEL_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_CAMEL_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_CAMEL_HURT; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		if (state.getSoundGroup() == BlockSoundGroup.SAND) this.playSound(ModSoundEvents.ENTITY_CAMEL_STEP_SAND, 1.0f, 1.0f);
		else this.playSound(ModSoundEvents.ENTITY_CAMEL_STEP, 1.0f, 1.0f);
	}
	@Override
	public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (player.shouldCancelInteraction()) {
			this.openInventory(player);
			return ActionResult.success(this.world.isClient);
		}
		ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
		if (actionResult.isAccepted()) return actionResult;
		if (this.isBreedingItem(itemStack)) return this.interactHorse(player, itemStack);
		if (this.getPassengerList().size() < 2 && !this.isBaby()) this.putPlayerOnBack(player);
		return ActionResult.success(this.world.isClient);
	}
	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		int i;
		if (fallDistance > 1.0f) this.playSound(ModSoundEvents.ENTITY_CAMEL_LAND, 0.4f, 1.0f);
		if ((i = this.computeFallDamage(fallDistance, damageMultiplier)) <= 0) return false;
		this.damage(damageSource, i);
		if (this.hasPassengers()) {
			for (Entity entity : this.getPassengersDeep()) entity.damage(damageSource, i);
		}
		this.playBlockFallSound();
		return true;
	}

	@Override
	protected void updateForLeashLength(float leashLength) {
		if (leashLength > 6.0f && this.isSitting() && !this.isChangingPose()) this.startStanding();
	}

	@Override
	protected boolean receiveFood(PlayerEntity player, ItemStack item) {
		if (!this.isBreedingItem(item)) return false;
		boolean bl = this.getHealth() < this.getMaxHealth();
		if (bl) this.heal(2.0f);
		boolean bl2 = this.isTame() && this.getBreedingAge() == 0 && this.canEat();
		if (bl2) this.lovePlayer(player);
		boolean bl3 = this.isBaby();
		if (bl3) {
			this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
			if (!this.world.isClient) this.growUp(10);
		}
		if (bl || bl2 || bl3) {
			SoundEvent soundEvent;
			if (!this.isSilent() && (soundEvent = this.getEatSound()) != null) {
				this.world.playSound(null, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundCategory(), 1.0f, 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
			}
			return true;
		}
		return false;
	}

	/*
	 * Enabled force condition propagation
	 * Lifted jumps to return sites
	 */
	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other == this) return false;
		if (!(other instanceof CamelEntity camelEntity)) return false;
		if (!this.canBreed()) return false;
		return camelEntity.canBreed();
	}
	@Override
	public CamelEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) { return ModEntityType.CAMEL_ENTITY.create(serverWorld); }
	@Override
	protected SoundEvent getEatSound() { return ModSoundEvents.ENTITY_CAMEL_EAT; }
	@Override
	protected void applyDamage(DamageSource source, float amount) {
		this.setStanding();
		super.applyDamage(source, amount);
	}
	@Override
	public void updatePassengerPosition(Entity passenger) {
		int i = this.getPassengerList().indexOf(passenger);
		if (i < 0) return;
		boolean bl = i == 0;
		float f = 0.5f;
		float g = (float)(this.isRemoved() ? (double)0.01f : this.method_45346(bl, 0.0f) + passenger.getHeightOffset());
		if (this.getPassengerList().size() > 1) {
			if (!bl) f = -0.7f;
			if (passenger instanceof AnimalEntity) f += 0.2f;
		}
		Vec3d vec3d = new Vec3d(0.0, 0.0, f).rotateY(-this.bodyYaw * ((float)Math.PI / 180));
		passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double)g, this.getZ() + vec3d.z);
		this.clampPassengerYaw(passenger);
	}

	private double method_45346(boolean bl, float f) {
		double d = this.getMountedHeightOffset();
		float g = this.getScaleFactor() * 1.43f;
		float h = g - this.getScaleFactor() * 0.2f;
		float i = g - h;
		boolean bl3 = this.GetPose() == ModEntityPose.SITTING;
		if (this.isChangingPose()) {
			float l;
			int k;
			int j = bl3 ? 40 : 52;
			if (bl3) {
				k = 28;
				l = bl ? 0.5f : 0.1f;
			}
			else {
				k = bl ? 24 : 32;
				l = bl ? 0.6f : 0.35f;
			}
			float m = (float)this.getLastPoseTickDelta() + f;
			boolean bl42 = m < (float)k;
			float n2 = bl42 ? m / (float)k : (m - (float)k) / (float)(j - k);
			float o = g - l * h;
			d += bl3 ? (double)MathHelper.lerp(n2, bl42 ? g : o, bl42 ? o : i) : (double)MathHelper.lerp(n2, bl42 ? i - g : i - o, bl42 ? i - o : 0.0f);
		}
		else if (bl3) d += i;
		return d;
	}

	public Vec3d getLeashOffset(float tickDelta) {
		return new Vec3d(0.0, this.method_45346(true, tickDelta) - (double)(0.2f * this.getScaleFactor()), this.getWidth() * 0.56f);
	}

	@Override
	public double getMountedHeightOffset() {
		return this.getDimensions(this.GetPose()).height - (this.isBaby() ? 0.35f : 0.6f);
	}

	@Override
	public void onPassengerLookAround(Entity passenger) { if (this.getPrimaryPassenger() != passenger) this.clampPassengerYaw(passenger); }

	private void clampPassengerYaw(Entity passenger) {
		passenger.setBodyYaw(this.getYaw());
		float f = passenger.getYaw();
		float g = MathHelper.wrapDegrees(f - this.getYaw());
		float h = MathHelper.clamp(g, -160.0f, 160.0f);
		passenger.prevYaw += h - g;
		float i = f + h - g;
		passenger.setYaw(i);
		passenger.setHeadYaw(i);
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) { return this.getPassengerList().size() <= 2; }

	@Override
	public LivingEntity getPrimaryPassenger() {
		if (!this.getPassengerList().isEmpty() && this.isSaddled() && this.getPassengerList().get(0) instanceof LivingEntity livingEntity) return livingEntity;
		return null;
	}

	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}

	public boolean isSitting() { return this.GetPose() == ModEntityPose.SITTING; }

	public boolean isChangingPose() {
		long l = this.getLastPoseTickDelta();
		return switch (this.GetPose()) {
			case STANDING -> l < 52L;
			case SITTING -> l < 40L;
			default -> false;
		};
	}

	private boolean shouldPlaySittingTransitionAnimation() { return this.GetPose() == ModEntityPose.SITTING && this.getLastPoseTickDelta() < 40L; }

	public void startSitting() {
		if (this.IsInPose(ModEntityPose.SITTING)) return;
		this.playSound(ModSoundEvents.ENTITY_CAMEL_SIT, 1.0f, 1.0f);
		this.SetPose(ModEntityPose.SITTING);
		this.setLastPoseTick(this.world.getTime());
	}

	public void startStanding() {
		if (this.IsInPose(ModEntityPose.STANDING)) return;
		this.playSound(ModSoundEvents.ENTITY_CAMEL_STAND, 1.0f, 1.0f);
		this.SetPose(ModEntityPose.STANDING);
		this.setLastPoseTick(this.world.getTime());
	}

	public void setStanding() {
		this.SetPose(ModEntityPose.STANDING);
		this.setLastPoseTick(this.world.getTime() - 52L);
	}

	@VisibleForTesting
	public void setLastPoseTick(long lastPoseTick) { this.dataTracker.set(LAST_POSE_TICK, lastPoseTick); }

	public long getLastPoseTickDelta() { return this.world.getTime() - this.dataTracker.get(LAST_POSE_TICK); }

	@Override
	public void onInventoryChanged(Inventory sender) {
		boolean bl = this.isSaddled();
		this.updateSaddle();
		if (this.age > 20 && !bl && this.isSaddled()) this.playSound(ModSoundEvents.ENTITY_CAMEL_SADDLE, 0.5f, 1.0f);
	}
	@Override
	public void saddle(SoundCategory sound) {
		this.items.setStack(0, new ItemStack(Items.SADDLE));
		if (sound != null) {
			this.world.playSoundFromEntity(null, this, ModSoundEvents.ENTITY_CAMEL_SADDLE, sound, 0.5f, 1.0f);
		}
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		if (!this.firstUpdate && DASHING.equals(data)) this.dashCooldown = this.dashCooldown == 0 ? 55 : this.dashCooldown;
		super.onTrackedDataSet(data);
	}
	@Override
	protected BodyControl createBodyControl() { return new CamelBodyControl(this); }
	@Override
	public boolean isTame() { return true; }
	@Override
	public void openInventory(PlayerEntity player) { if (!this.world.isClient) player.openHorseInventory(this, this.items); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.CAMEL_BLOOD_TYPE; }

	class CamelBodyControl extends BodyControl {
		public CamelBodyControl(CamelEntity camel) { super(camel); }
		@Override
		public void tick() { if (!CamelEntity.this.isStationary()) super.tick(); }
	}
}