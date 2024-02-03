package fun.wich.entity.passive.frog;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import fun.wich.ModBase;
import fun.wich.client.render.entity.animation.AnimationState;
import fun.wich.entity.ModDataHandlers;
import fun.wich.entity.ModEntityPose;
import fun.wich.entity.ai.AxolotlSwimNavigation;
import fun.wich.entity.ai.ModMemoryModules;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.entity.hostile.slime.TropicalSlimeEntity;
import fun.wich.entity.variants.FrogVariant;
import fun.wich.gen.data.tag.ModBiomeTags;
import fun.wich.gen.data.tag.ModBlockTags;
import fun.wich.gen.data.tag.ModEntityTypeTags;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

public class FrogEntity extends AnimalEntity implements EntityWithBloodType {
	public static final Ingredient SLIME_BALL = Ingredient.ofItems(Items.SLIME_BALL);
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.BREED_TARGET, MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, ModMemoryModules.IS_IN_WATER, ModMemoryModules.IS_PREGNANT, ModMemoryModules.IS_PANICKING, ModMemoryModules.UNREACHABLE_TONGUE_TARGETS);
	private static final TrackedData<FrogVariant> VARIANT = DataTracker.registerData(FrogEntity.class, ModDataHandlers.FROG_VARIANT);
	private static final TrackedData<OptionalInt> TARGET = DataTracker.registerData(FrogEntity.class, ModDataHandlers.OPTIONAL_INT);
	public static final String VARIANT_KEY = "variant";
	public final AnimationState longJumpingAnimationState = new AnimationState();
	public final AnimationState croakingAnimationState = new AnimationState();
	public final AnimationState usingTongueAnimationState = new AnimationState();
	public final AnimationState walkingAnimationState = new AnimationState();
	public final AnimationState swimmingAnimationState = new AnimationState();
	public final AnimationState idlingInWaterAnimationState = new AnimationState();

	public FrogEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		this.lookControl = new FrogLookControl(this);
		this.setPathfindingPenalty(PathNodeType.WATER, 4.0f);
		this.setPathfindingPenalty(PathNodeType.TRAPDOOR, -1.0f);
		this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, true);
		this.stepHeight = 1.0f;
	}
	protected Brain.Profile<FrogEntity> createBrainProfile() {
		ImmutableList<SensorType<? extends Sensor<? super FrogEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, ModBase.FROG_ATTACKABLES_SENSOR.get(), ModBase.FROG_TEMPTATIONS_SENSOR.get(), ModBase.IS_IN_WATER_SENSOR.get());
		return Brain.createProfile(MEMORY_MODULES, SENSORS);
	}
	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) { return FrogBrain.create(this.createBrainProfile().deserialize(dynamic)); }
	@SuppressWarnings("unchecked")
	public Brain<FrogEntity> getBrain() { return (Brain<FrogEntity>)super.getBrain(); }
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(VARIANT, FrogVariant.TEMPERATE);
		this.dataTracker.startTracking(TARGET, OptionalInt.empty());
		this.dataTracker.startTracking(NEW_POSE, ModEntityPose.STANDING);
	}
	public void clearFrogTarget() { this.dataTracker.set(TARGET, OptionalInt.empty()); }
	public Optional<Entity> getFrogTarget() {
		return this.dataTracker.get(TARGET).stream().mapToObj(this.world::getEntityById).filter(Objects::nonNull).findFirst();
	}
	public void setFrogTarget(Entity entity) { this.dataTracker.set(TARGET, OptionalInt.of(entity.getId())); }
	public int getMaxLookYawChange() { return 35; }
	public int getMaxHeadRotation() { return 5; }
	public FrogVariant getVariant() { return this.dataTracker.get(VARIANT); }
	public void setVariant(FrogVariant variant) { this.dataTracker.set(VARIANT, variant); }
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putString(VARIANT_KEY, this.getVariant().toString());
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		try {
			FrogVariant frogVariant = Enum.valueOf(FrogVariant.class, nbt.getString(VARIANT_KEY));
			this.setVariant(frogVariant);
		}
		catch (IllegalArgumentException ignored) { }
	}
	@Override
	public boolean canBreatheInWater() { return true; }
	private boolean shouldWalk() {
		return this.onGround && this.getVelocity().horizontalLengthSquared() > 1.0E-6 && !this.isInsideWaterOrBubbleColumn();
	}
	private boolean shouldSwim() {
		return this.getVelocity().horizontalLengthSquared() > 1.0E-6 && this.isInsideWaterOrBubbleColumn();
	}
	@Override
	protected void mobTick() {
		this.world.getProfiler().push("frogBrain");
		this.getBrain().tick((ServerWorld)this.world, this);
		this.world.getProfiler().pop();
		this.world.getProfiler().push("frogActivityUpdate");
		FrogBrain.updateActivities(this);
		this.world.getProfiler().pop();
		super.mobTick();
	}

	@Override
	public void tick() {
		if (this.world.isClient()) {
			if (this.shouldWalk()) this.walkingAnimationState.startIfNotRunning(this.age);
			else this.walkingAnimationState.stop();
			if (this.shouldSwim()) {
				this.idlingInWaterAnimationState.stop();
				this.swimmingAnimationState.startIfNotRunning(this.age);
			}
			else if (this.isInsideWaterOrBubbleColumn()) {
				this.swimmingAnimationState.stop();
				this.idlingInWaterAnimationState.startIfNotRunning(this.age);
			}
			else {
				this.swimmingAnimationState.stop();
				this.idlingInWaterAnimationState.stop();
			}
		}
		super.tick();
	}
	protected static final TrackedData<ModEntityPose> NEW_POSE = DataTracker.registerData(FrogEntity.class, ModDataHandlers.NEW_ENTITY_POSE);
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

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		if (POSE.equals(data)) {
			ModEntityPose pose = this.GetPose();
			if (pose == ModEntityPose.LONG_JUMPING) this.longJumpingAnimationState.start(this.age);
			else this.longJumpingAnimationState.stop();
			if (pose == ModEntityPose.CROAKING) this.croakingAnimationState.start(this.age);
			else this.croakingAnimationState.stop();
			if (pose == ModEntityPose.USING_TONGUE) this.usingTongueAnimationState.start(this.age);
			else this.usingTongueAnimationState.stop();
		}
		super.onTrackedDataSet(data);
	}
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		FrogEntity frogEntity = ModBase.FROG_ENTITY.create(world);
		if (frogEntity != null) FrogBrain.coolDownLongJump(frogEntity, world.getRandom());
		return frogEntity;
	}
	@Override
	public boolean isBaby() { return false; }
	@Override
	public void setBaby(boolean baby) { }
	@Override
	public void breed(ServerWorld world, AnimalEntity other) {
		ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
		if (serverPlayerEntity == null) serverPlayerEntity = other.getLovingPlayer();
		if (serverPlayerEntity != null) {
			serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
			Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, null);
		}
		this.setBreedingAge(6000);
		other.setBreedingAge(6000);
		this.resetLoveTicks();
		other.resetLoveTicks();
		this.getBrain().remember(ModMemoryModules.IS_PREGNANT, Unit.INSTANCE);
		world.sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
		if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
		}
	}

	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt) {
		Biome biome = world.getBiome(this.getBlockPos());
		if (ModBiomeTags.SPAWNS_COLD_VARIANT_FROGS.contains(biome)) this.setVariant(FrogVariant.COLD);
		else if (ModBiomeTags.SPAWNS_WARM_VARIANT_FROGS.contains(biome)) this.setVariant(FrogVariant.WARM);
		else this.setVariant(FrogVariant.TEMPERATE);

		FrogBrain.coolDownLongJump(this, world.getRandom());
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}
	public static DefaultAttributeContainer.Builder createFrogAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0).add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0);
	}
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_FROG_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_FROG_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_FROG_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_FROG_STEP, 0.15f, 1.0f);
	}
	@Override
	public boolean isPushedByFluids() { return false; }
	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}
	@Override
	protected int computeFallDamage(float fallDistance, float damageMultiplier) {
		return super.computeFallDamage(fallDistance, damageMultiplier) - 5;
	}
	@Override
	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() && this.isTouchingWater()) {
			this.updateVelocity(this.getMovementSpeed(), movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.9));
		}
		else super.travel(movementInput);
	}
	@Override
	public boolean canJumpToNextPathNode(PathNodeType type) {
		return super.canJumpToNextPathNode(type) && type != PathNodeType.WATER_BORDER;
	}
	public static boolean isValidFrogFood(LivingEntity entity) {
		if (entity instanceof TropicalSlimeEntity tropical && tropical.getSize() != 1) return false;
		if (entity instanceof SlimeEntity slimeEntity && slimeEntity.getSize() != 1) return false;
		return entity.getType().isIn(ModEntityTypeTags.FROG_FOOD);
	}
	@Override
	protected EntityNavigation createNavigation(World world) { return new FrogSwimNavigation(this, world); }
	@Override
	public boolean isBreedingItem(ItemStack stack) { return SLIME_BALL.test(stack); }
	public static boolean canSpawn(EntityType<? extends AnimalEntity> ignoredType, WorldAccess world, SpawnReason ignoredReason, BlockPos pos, Random ignoredRandom) {
		return world.getBlockState(pos.down()).isIn(ModBlockTags.FROGS_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos);
	}
	protected static boolean isLightLevelValidForNaturalSpawn(BlockRenderView world, BlockPos pos) {
		return world.getBaseLightLevel(pos, 0) > 8;
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.FROG_BLOOD_TYPE; }

	class FrogLookControl extends LookControl {
		FrogLookControl(MobEntity entity) { super(entity); }
		@Override
		protected boolean shouldStayHorizontal() { return FrogEntity.this.getFrogTarget().isEmpty(); }
	}
	static class FrogSwimNavigation extends AxolotlSwimNavigation {
		FrogSwimNavigation(FrogEntity frog, World world) { super(frog, world); }
		@Override
		protected PathNodeNavigator createPathNodeNavigator(int range) {
			this.nodeMaker = new FrogSwimPathNodeMaker(true);
			this.nodeMaker.setCanEnterOpenDoors(true);
			return new PathNodeNavigator(this.nodeMaker, range);
		}
	}
	static class FrogSwimPathNodeMaker extends AmphibiousPathNodeMaker {
		private final BlockPos.Mutable pos = new BlockPos.Mutable();
		public FrogSwimPathNodeMaker(boolean bl) { super(bl); }
		@Override
		public PathNode getStart() {
			return this.getStart(new BlockPos(MathHelper.floor(this.entity.getBoundingBox().minX), MathHelper.floor(this.entity.getBoundingBox().minY), MathHelper.floor(this.entity.getBoundingBox().minZ)));
		}
		protected PathNode getStart(BlockPos pos) {
			PathNode pathNode = this.getNode(pos);
			if (pathNode != null) {
				pathNode.type = this.getNodeType(this.entity, pathNode.getBlockPos());
				pathNode.penalty = this.entity.getPathfindingPenalty(pathNode.type);
			}
			return pathNode;
		}
		private PathNodeType getNodeType(MobEntity entity, BlockPos pos) {
			return this.getNodeType(entity, pos.getX(), pos.getY(), pos.getZ());
		}
		@Override
		public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
			this.pos.set(x, y - 1, z);
			BlockState blockState = world.getBlockState(this.pos);
			if (blockState.isIn(ModBlockTags.FROG_PREFER_JUMP_TO)) return PathNodeType.OPEN;
			return super.getDefaultNodeType(world, x, y, z);
		}
	}
}