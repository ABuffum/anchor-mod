package fun.wich.entity.passive.frog;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import fun.wich.ModBase;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.entity.ai.YawAdjustingLookControl;
import fun.wich.entity.ai.sensor.ModSensorTypes;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.registry.ModEntityRegistry;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TadpoleEntity extends FishEntity implements EntityWithBloodType {
	@VisibleForTesting
	public static int MAX_TADPOLE_AGE = Math.abs(-24000);
	public static float WIDTH = 0.4f;
	public static float HEIGHT = 0.3f;
	private int tadpoleAge;
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.BREED_TARGET, ModMemoryModules.IS_PANICKING);
	public TadpoleEntity(EntityType<? extends FishEntity> entityType, World world) {
		super(entityType, world);
		this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, true);
		this.lookControl = new YawAdjustingLookControl(this, 10);
	}
	@Override
	protected EntityNavigation createNavigation(World world) { return new SwimNavigation(this, world); }
	protected Brain.Profile<TadpoleEntity> createBrainProfile() {
		ImmutableList<SensorType<? extends Sensor<? super TadpoleEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, ModSensorTypes.FROG_TEMPTATIONS_SENSOR.get());
		return Brain.createProfile(MEMORY_MODULES, SENSORS);
	}
	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return TadpoleBrain.create(this.createBrainProfile().deserialize(dynamic));
	}
	public Brain<TadpoleEntity> getBrain() { return (Brain<TadpoleEntity>)super.getBrain(); }
	@Override
	protected SoundEvent getFlopSound() { return ModSoundEvents.ENTITY_TADPOLE_FLOP; }
	@Override
	protected void mobTick() {
		this.world.getProfiler().push("tadpoleBrain");
		this.getBrain().tick((ServerWorld)this.world, this);
		this.world.getProfiler().pop();
		this.world.getProfiler().push("tadpoleActivityUpdate");
		TadpoleBrain.updateActivities(this);
		this.world.getProfiler().pop();
		super.mobTick();
	}
	public static DefaultAttributeContainer.Builder createTadpoleAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0).add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0);
	}
	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!this.world.isClient) this.setTadpoleAge(this.tadpoleAge + 1);
	}
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Age", this.tadpoleAge);
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setTadpoleAge(nbt.getInt("Age"));
	}
	@Override
	protected SoundEvent getAmbientSound() { return null; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_TADPOLE_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_TADPOLE_DEATH; }
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (this.isSlimeBall(itemStack)) {
			this.eatSlimeBall(player, itemStack);
			return ActionResult.success(this.world.isClient);
		}
		return Bucketable.tryBucket(player, hand, this).orElse(super.interactMob(player, hand));
	}
	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}
	@Override
	public boolean isFromBucket() { return true; }
	@Override
	public void setFromBucket(boolean fromBucket) { }
	@Override
	public void copyDataToStack(ItemStack stack) {
		Bucketable.copyDataToStack(this, stack);
		NbtCompound nbtCompound = stack.getOrCreateNbt();
		nbtCompound.putInt("Age", this.getTadpoleAge());
	}
	@Override
	public void copyDataFromNbt(NbtCompound nbt) {
		Bucketable.copyDataFromNbt(this, nbt);
		if (nbt.contains("Age")) this.setTadpoleAge(nbt.getInt("Age"));
	}
	@Override
	public ItemStack getBucketItem() { return new ItemStack(ModEntityRegistry.TADPOLE_BUCKET); }
	@Override
	public SoundEvent getBucketedSound() { return ModSoundEvents.ITEM_BUCKET_FILL_TADPOLE; }
	private boolean isSlimeBall(ItemStack stack) { return FrogEntity.SLIME_BALL.test(stack); }
	private void eatSlimeBall(PlayerEntity player, ItemStack stack) {
		this.decrementItem(player, stack);
		this.increaseAge((int)(this.getTicksUntilGrowth() / 200f));
		this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), 0.0, 0.0, 0.0);
	}
	private void decrementItem(PlayerEntity player, ItemStack stack) {
		if (!player.getAbilities().creativeMode) stack.decrement(1);
	}
	private int getTadpoleAge() { return this.tadpoleAge; }
	private void increaseAge(int seconds) { this.setTadpoleAge(this.tadpoleAge + seconds * 20); }
	private void setTadpoleAge(int tadpoleAge) {
		this.tadpoleAge = tadpoleAge;
		if (this.tadpoleAge >= MAX_TADPOLE_AGE) this.growUp();
	}
	private void growUp() {
		World world = this.world;
		if (world instanceof ServerWorld serverWorld) {
			FrogEntity frogEntity = ModEntityType.FROG_ENTITY.create(this.world);
			frogEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
			frogEntity.initialize(serverWorld, this.world.getLocalDifficulty(frogEntity.getBlockPos()), SpawnReason.CONVERSION, null, null);
			frogEntity.setAiDisabled(this.isAiDisabled());
			if (this.hasCustomName()) {
				frogEntity.setCustomName(this.getCustomName());
				frogEntity.setCustomNameVisible(this.isCustomNameVisible());
			}
			frogEntity.setPersistent();
			this.playSound(ModSoundEvents.ENTITY_TADPOLE_GROW_UP, 0.15f, 1.0f);
			serverWorld.spawnEntityAndPassengers(frogEntity);
			this.discard();
		}
	}
	private int getTicksUntilGrowth() { return Math.max(0, MAX_TADPOLE_AGE - this.tadpoleAge); }
	@Override
	public boolean shouldDropXp() { return false; }

	@Override public BloodType GetDefaultBloodType() { return ModBase.FROG_BLOOD_TYPE; }
}
