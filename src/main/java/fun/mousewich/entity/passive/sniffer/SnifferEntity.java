package fun.mousewich.entity.passive.sniffer;

import com.mojang.serialization.Dynamic;
import fun.mousewich.ModBase;
import fun.mousewich.client.render.entity.animation.AnimationState;
import fun.mousewich.entity.ModDataHandlers;
import fun.mousewich.entity.ai.ModMemoryModules;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.gen.data.tag.ModBlockTags;
import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SnifferEntity extends AnimalEntity implements EntityWithBloodType {
	private static final TrackedData<State> STATE = DataTracker.registerData(SnifferEntity.class, ModDataHandlers.SNIFFER_STATE);
	private static final TrackedData<Integer> FINISH_DIG_TIME = DataTracker.registerData(SnifferEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public final AnimationState walkingAnimationState = new AnimationState();
	public final AnimationState panickingAnimationState = new AnimationState();
	public final AnimationState feelingHappyAnimationState = new AnimationState();
	public final AnimationState scentingAnimationState = new AnimationState();
	public final AnimationState sniffingAnimationState = new AnimationState();
	public final AnimationState seachingAnimationState = new AnimationState();
	public final AnimationState diggingAnimationState = new AnimationState();
	public final AnimationState risingAnimationState = new AnimationState();

	public static DefaultAttributeContainer.Builder createSnifferAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f).add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0);
	}

	public SnifferEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
		this.dataTracker.startTracking(STATE, State.IDLING);
		this.dataTracker.startTracking(FINISH_DIG_TIME, 0);
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER, -2.0f);
	}

	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.getDimensions(pose).height * 0.6f;
	}

	private boolean isMoving() {
		boolean bl = this.onGround || this.isInsideWaterOrBubbleColumn();
		return bl && this.getVelocity().horizontalLengthSquared() > 1.0E-6;
	}

	@Override
	public void breed(ServerWorld serverWorld, AnimalEntity other) {
		BlockPos blockPos = this.getSteppingPos();
		ItemStack itemStack = new ItemStack(ModBase.SNIFFER_EGG.asItem());
		ItemEntity itemEntity = new ItemEntity(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
		itemEntity.setToDefaultPickupDelay();
		serverWorld.spawnEntity(itemEntity);
		Optional.ofNullable(this.getLovingPlayer()).or(() -> Optional.ofNullable(other.getLovingPlayer())).ifPresent(serverPlayerEntity -> {
			serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
			Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, other, null);
		});
		this.setBreedingAge(6000);
		other.setBreedingAge(6000);
		this.resetLoveTicks();
		other.resetLoveTicks();
		serverWorld.sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
		if (serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
			serverWorld.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
		}
	}

	public boolean isPanicking() { return this.brain.getOptionalMemory(ModMemoryModules.IS_PANICKING).isPresent(); }

	public boolean isDiggingOrSearching() { return this.getState() == State.DIGGING || this.getState() == State.SEARCHING; }

	private BlockPos getDigPos() {
		Vec3d vec3d = this.getPos().add(this.getRotationVecClient().multiply(2.25));
		return new BlockPos(vec3d.getX(), this.getY(), vec3d.getZ());
	}

	private State getState() { return this.dataTracker.get(STATE); }

	private SnifferEntity setState(State state) {
		this.dataTracker.set(STATE, state);
		return this;
	}

	@Override
	public void onTrackedDataSet(TrackedData<?> data) {
		if (STATE.equals(data)) {
			State state = this.getState();
			this.stopAnimations();
			switch (state) {
				case SCENTING -> this.scentingAnimationState.startIfNotRunning(this.age);
				case SNIFFING -> this.sniffingAnimationState.startIfNotRunning(this.age);
				case SEARCHING -> this.seachingAnimationState.startIfNotRunning(this.age);
				case DIGGING -> this.diggingAnimationState.startIfNotRunning(this.age);
				case RISING -> this.risingAnimationState.startIfNotRunning(this.age);
				case FEELING_HAPPY -> this.feelingHappyAnimationState.startIfNotRunning(this.age);
			}
		}
		super.onTrackedDataSet(data);
	}

	private void stopAnimations() {
		this.seachingAnimationState.stop();
		this.diggingAnimationState.stop();
		this.sniffingAnimationState.stop();
		this.risingAnimationState.stop();
		this.feelingHappyAnimationState.stop();
		this.scentingAnimationState.stop();
	}

	public SnifferEntity startState(State state) {
		switch (state) {
			case IDLING -> this.setState(State.IDLING);
			case SCENTING -> {
				this.playSound(ModSoundEvents.ENTITY_SNIFFER_SCENTING, 1.0f, 1.0f);
				this.setState(State.SCENTING);
			}
			case SNIFFING -> {
				this.playSound(ModSoundEvents.ENTITY_SNIFFER_SNIFFING, 1.0f, 1.0f);
				this.setState(State.SNIFFING);
			}
			case SEARCHING -> this.setState(State.SEARCHING);
			case DIGGING -> this.setState(State.DIGGING).setDigging();
			case RISING -> {
				this.playSound(ModSoundEvents.ENTITY_SNIFFER_DIGGING_STOP, 1.0f, 1.0f);
				this.setState(State.RISING);
			}
			case FEELING_HAPPY -> {
				this.playSound(ModSoundEvents.ENTITY_SNIFFER_HAPPY, 1.0f, 1.0f);
				this.setState(State.FEELING_HAPPY);
			}
		}
		return this;
	}

	private void setDigging() {
		this.dataTracker.set(FINISH_DIG_TIME, this.age + 120);
		this.world.sendEntityStatus(this, (byte)63);
	}
	private BlockPos getSteppingPos() {
		BlockPos blockPos2;
		BlockState blockState;
		Vec3d pos = this.getPos();
		int i = MathHelper.floor(pos.x);
		BlockPos blockPos = new BlockPos(i, MathHelper.floor(pos.y - (float) 1.0E-5), MathHelper.floor(pos.z));
		if (this.world.getBlockState(blockPos).isAir() && ((blockState = this.world.getBlockState(blockPos2 = blockPos.down())).isIn(BlockTags.FENCES) || blockState.isIn(BlockTags.WALLS) || blockState.getBlock() instanceof FenceGateBlock)) {
			return blockPos2;
		}
		return blockPos;
	}

	public void finishDigging(boolean explored) {
		if (explored) this.addExploredPosition(this.getSteppingPos());
	}

	Optional<BlockPos> findSniffingTargetPos() {
		return IntStream.range(0, 5).mapToObj(i -> FuzzyTargeting.find(this, 10 + 2 * i, 3)).filter(Objects::nonNull).map(BlockPos::new).map(BlockPos::down).filter(this::isDiggable).findFirst();
	}

	@Override
	protected boolean canStartRiding(Entity entity) { return false; }

	boolean canDig() {
		return !this.isPanicking() && !this.isBaby() && !this.isTouchingWater() && this.isDiggable(this.getDigPos().down());
	}

	/*
	 * Enabled force condition propagation
	 * Lifted jumps to return sites
	 */
	private boolean isDiggable(BlockPos pos) {
		if (!this.world.getBlockState(pos).isIn(ModBlockTags.SNIFFER_DIGGABLE_BLOCK)) return false;
		if (!this.world.getBlockState(pos.up()).isAir()) return false;
		if (this.getExploredPositions().anyMatch(pos::equals)) return false;
		return true;
	}

	private void dropSeeds() {
		if (this.world.isClient() || this.dataTracker.get(FINISH_DIG_TIME) != this.age) return;
		ItemStack itemStack = new ItemStack(this.world.random.nextBoolean() ? ModBase.PITCHER_CROP.asItem() : ModBase.TORCHFLOWER_CROP.asItem());
		BlockPos blockPos = this.getDigPos();
		ItemEntity itemEntity = new ItemEntity(this.world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
		itemEntity.setToDefaultPickupDelay();
		this.world.spawnEntity(itemEntity);
		this.playSound(ModSoundEvents.ENTITY_SNIFFER_DROP_SEED, 1.0f, 1.0f);
	}

	public BlockState getSteppingBlockState() { return this.world.getBlockState(this.getSteppingPos()); }

	private SnifferEntity spawnDiggingParticles(AnimationState diggingAnimationState) {
		boolean bl = diggingAnimationState.getTimeRunning() > 1700L && diggingAnimationState.getTimeRunning() < 6000L;
		if (bl) {
			BlockState blockState = this.getSteppingBlockState();
			BlockPos blockPos = this.getDigPos();
			if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
				for (int i = 0; i < 30; ++i) {
					Vec3d vec3d = Vec3d.ofCenter(blockPos);
					this.world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
				}
				if (this.age % 10 == 0) {
					this.world.playSound(this.getX(), this.getY(), this.getZ(), blockState.getSoundGroup().getHitSound(), this.getSoundCategory(), 0.5f, 0.5f, false);
				}
			}
		}
		return this;
	}

	private void addExploredPosition(BlockPos pos) {
		List<BlockPos> list = this.getExploredPositions().limit(20L).collect(Collectors.toList());
		list.add(0, pos);
		this.getBrain().remember(ModMemoryModules.SNIFFER_EXPLORED_POSITIONS, list);
	}

	private Stream<BlockPos> getExploredPositions() {
		return this.getBrain().getOptionalMemory(ModMemoryModules.SNIFFER_EXPLORED_POSITIONS).stream().flatMap(Collection::stream);
	}

	@Override
	public void tick() {
		EntityAttributeInstance inst = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (inst != null) inst.setBaseValue(this.isTouchingWater() ? (double)0.2f : (double)0.1f);
		if (this.isMoving() || this.isTouchingWater()) {
			if (this.isPanicking()) {
				this.walkingAnimationState.stop();
				this.panickingAnimationState.startIfNotRunning(this.age);
			}
			else {
				this.panickingAnimationState.stop();
				this.walkingAnimationState.startIfNotRunning(this.age);
			}
		}
		else {
			this.panickingAnimationState.stop();
			this.walkingAnimationState.stop();
		}
		switch (this.getState()) {
			case DIGGING -> this.spawnDiggingParticles(this.diggingAnimationState).dropSeeds();
			case SEARCHING -> this.playSearchingSound();
		}
		super.tick();
	}
	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		ActionResult actionResult = super.interactMob(player, hand);
		if (actionResult.isAccepted() && this.isBreedingItem(itemStack)) {
			this.world.playSoundFromEntity(null, this, this.getEatSound(itemStack), SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
		}
		return actionResult;
	}
	private void playSearchingSound() {
		if (this.world.isClient() && this.age % 20 == 0) {
			this.world.playSound(this.getX(), this.getY(), this.getZ(), ModSoundEvents.ENTITY_SNIFFER_SEARCHING, this.getSoundCategory(), 1.0f, 1.0f, false);
		}
	}
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(ModSoundEvents.ENTITY_SNIFFER_STEP, 0.15f, 1.0f);
	}
	@Override
	public SoundEvent getEatSound(ItemStack stack) { return ModSoundEvents.ENTITY_SNIFFER_EAT; }
	@Override
	protected SoundEvent getAmbientSound() {
		return Set.of(State.DIGGING, State.SEARCHING).contains(this.getState()) ? null : ModSoundEvents.ENTITY_SNIFFER_IDLE;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_SNIFFER_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_SNIFFER_DEATH; }
	@Override
	public void setBaby(boolean baby) { this.setBreedingAge(baby ? -48000 : 0); }
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) { return ModBase.SNIFFER_ENTITY.create(world); }

	@Override
	public boolean canBreedWith(AnimalEntity other) {
		if (other instanceof SnifferEntity snifferEntity) {
			Set<State> set = Set.of(State.IDLING, State.SCENTING, State.FEELING_HAPPY);
			return set.contains(this.getState()) && set.contains(snifferEntity.getState()) && super.canBreedWith(other);
		}
		return false;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) { return stack.isIn(ModItemTags.SNIFFER_FOOD); }

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return SnifferBrain.create(this.createBrainProfile().deserialize(dynamic));
	}

	@SuppressWarnings("unchecked")
	public Brain<SnifferEntity> getBrain() { return (Brain<SnifferEntity>)super.getBrain(); }

	protected Brain.Profile<SnifferEntity> createBrainProfile() {
		return Brain.createProfile(SnifferBrain.MEMORY_MODULES, SnifferBrain.SENSORS);
	}

	@Override
	protected void mobTick() {
		this.world.getProfiler().push("snifferBrain");
		this.getBrain().tick((ServerWorld)this.world, this);
		this.world.getProfiler().swap("snifferActivityUpdate");
		SnifferBrain.updateActivities(this);
		this.world.getProfiler().pop();
		super.mobTick();
	}

	@Override
	protected void sendAiDebugData() {
		super.sendAiDebugData();
		DebugInfoSender.sendBrainDebugData(this);
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.SNIFFER_BLOOD_TYPE; }

	public enum State {
		IDLING,
		FEELING_HAPPY,
		SCENTING,
		SNIFFING,
		SEARCHING,
		DIGGING,
		RISING
	}
}
