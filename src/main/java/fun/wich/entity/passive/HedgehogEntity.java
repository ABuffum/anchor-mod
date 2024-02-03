package fun.wich.entity.passive;

import fun.wich.ModBase;
import fun.wich.damage.ModEntityDamageSource;
import fun.wich.entity.ModNbtKeys;
import fun.wich.entity.Pouchable;
import fun.wich.entity.ai.goal.ModMeleeAttackGoal;
import fun.wich.entity.ai.goal.MoveToHuntGoal;
import fun.wich.entity.blood.BloodType;
import fun.wich.entity.blood.EntityWithBloodType;
import fun.wich.origins.power.MobHostilityPower;
import fun.wich.origins.power.ScareMobPower;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.List;

public class HedgehogEntity extends AnimalEntity implements Pouchable, EntityWithBloodType {
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SPIDER_EYE);
	private static final TrackedData<Boolean> FROM_POUCH = DataTracker.registerData(HedgehogEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public HedgehogEntity(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }
	private boolean canHedgehogTarget(LivingEntity target) { //attack "small" bugs or any player marked as hedgehog-attackable
		EntityType<?> type = target.getType();
		return target instanceof SilverfishEntity
				|| target instanceof EndermiteEntity
				|| target instanceof BeeEntity
				|| (target instanceof SpiderEntity && canTargetSpider(target, type))
				|| MobHostilityPower.shouldBeHostile(target, ModBase.HEDGEHOG_ENTITY);
	}
	private boolean canTargetSpider(LivingEntity target, EntityType<?> type) {
		if (type == EntityType.CAVE_SPIDER || type == ModBase.JUMPING_SPIDER_ENTITY) return true;
		List<HedgehogEntity> allies = world.getEntitiesByClass(HedgehogEntity.class,
				Box.of(target.getPos(), 10, 10, 10),
				e -> e.isAlive() && !e.isInvisible());
		return allies.size() > 1;
	}
	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new FleeEntityGoal<>(this, FoxEntity.class, 8, 1.6, 1.4));
		this.goalSelector.add(3, new FleeEntityGoal<>(this, WolfEntity.class, 8, 1.6, 1.4, (entity) -> !((WolfEntity)entity).isTamed()));
		this.goalSelector.add(3, ScareMobPower.makeFleeGoal(this, 8, 1.6, 1.4, ModBase.HEDGEHOG_ENTITY));
		this.goalSelector.add(4, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
		this.targetSelector.add(4, new FollowTargetGoal<>(this, LivingEntity.class, 10, false, false, this::canHedgehogTarget));
		this.goalSelector.add(5, new MoveToHuntGoal(this, 12, 1.5D) {
			public boolean canTarget(LivingEntity target) { return canHedgehogTarget(target); }
		});
		this.goalSelector.add(6, new ModMeleeAttackGoal(this, 1.2D, true));
		this.goalSelector.add(7, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(8, new EatBerriesGoal(1.2D, 12, 1));
		this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(11, new LookAroundGoal(this));
	}
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FROM_POUCH, false);
	}
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return this.isBaby() ? dimensions.height * 0.4F : dimensions.height * 0.5F; }
	public static DefaultAttributeContainer.Builder createHedgehogAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0F);
	}
	@Override
	public HedgehogEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) { return ModBase.HEDGEHOG_ENTITY.create(serverWorld); }
	public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }
	public boolean damage(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		else {
			Entity entity = source.getAttacker();
			if (entity != null) {
				if (!source.isProjectile()) { //only melee
					if (!source.isExplosive()) { //ignore explosions
						if (!source.isMagic()) { //hedgehogs are weak to wizardry
							entity.damage(ModEntityDamageSource.quills(this), Math.max(amount * 2, 0));
						}
					}
				}
			}
			return super.damage(source, amount);
		}
	}
	public boolean tryAttack(Entity target) { return target.damage(DamageSource.mob(this), 1); }

	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_HEDGEHOG_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_HEDGEHOG_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_HEDGEHOG_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(ModSoundEvents.ENTITY_HEDGEHOG_STEP, 0.15f, 1.0f); }

	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		return Pouchable.tryPouch(player, hand, this).orElse(super.interactMob(player, hand));
	}
	@Override
	public ItemStack getPouchItem() { return new ItemStack(ModBase.HEDGEHOG_POUCH); }
	@Override
	public boolean isFromPouch() { return this.dataTracker.get(FROM_POUCH); }
	@Override
	public void setFromPouch(boolean fromPouch) { this.dataTracker.set(FROM_POUCH, fromPouch); }
	@Override
	public void copyDataToStack(ItemStack stack) { Pouchable.copyDataToStack(this, stack); }
	@Override
	public void copyDataFromNbt(NbtCompound nbt) { Pouchable.copyDataFromNbt(this, nbt); }
	@Override
	public SoundEvent getPouchedSound() { return ModSoundEvents.ITEM_POUCH_FILL_HEDGEHOG; }
	@Override
	public boolean cannotDespawn() { return super.cannotDespawn() || this.isFromPouch(); }
	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) { return !this.isFromPouch() && !this.hasCustomName(); }
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean(ModNbtKeys.FROM_POUCH, this.isFromPouch());
	}
	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setFromPouch(nbt.getBoolean(ModNbtKeys.FROM_POUCH));
	}

	@Override public BloodType GetDefaultBloodType() { return ModBase.HEDGEHOG_BLOOD_TYPE; }

	public class EatBerriesGoal extends MoveToTargetPosGoal {
		private static final int EATING_TIME = 40;
		protected int timer;
		public EatBerriesGoal(double speed, int range, int maxYDifference) {
			super(HedgehogEntity.this, speed, range, maxYDifference);
		}
		public double getDesiredSquaredDistanceToTarget() { return 2.0D; }
		public boolean shouldResetPath() { return this.tryingTime % 100 == 0; }
		protected boolean isTargetPos(WorldView world, BlockPos pos) {
			BlockState blockState = world.getBlockState(pos);
			return blockState.isOf(Blocks.SWEET_BERRY_BUSH) && blockState.get(SweetBerryBushBlock.AGE) >= 2
					//|| blockState.isOf(ModBase.STRAWBERRY_BUSH) && blockState.get(StrawberryBushBlock.AGE) > 2
					|| CaveVines.hasBerries(blockState);
		}
		public void tick() {
			if (this.hasReached()) {
				if (this.timer >= EATING_TIME) this.eatSweetBerry();
				else ++this.timer;
			}
			else if (!this.hasReached() && HedgehogEntity.this.random.nextFloat() < 0.05F) {
				HedgehogEntity.this.playSound(ModSoundEvents.ENTITY_HEDGEHOG_SNIFF, 1.0F, 1.0F);
			}
			super.tick();
		}
		protected void eatSweetBerry() {
			if (HedgehogEntity.this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				BlockState blockState = HedgehogEntity.this.world.getBlockState(this.targetPos);
				if (blockState.isOf(Blocks.SWEET_BERRY_BUSH)) this.pickSweetBerries(blockState);
				//else if (blockState.isOf(ModBase.STRAWBERRY_BUSH)) this.pickStrawberries(blockState);
				else if (CaveVines.hasBerries(blockState)) this.pickGlowBerries(blockState);
			}
		}
		private void pickGlowBerries(BlockState state) {
			BlockPos pos = this.targetPos;
			if (state.get(CaveVines.BERRIES)) {
				float f = MathHelper.nextBetween(world.random, 0.8F, 1.2F);
				world.playSound(null, pos, SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, f);
				world.setBlockState(pos, state.with(CaveVines.BERRIES, false), Block.NOTIFY_LISTENERS);
			}
		}
		private void pickSweetBerries(BlockState state) {
			HedgehogEntity.this.playSound(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
			HedgehogEntity.this.world.setBlockState(this.targetPos, state.with(SweetBerryBushBlock.AGE, 1), Block.NOTIFY_LISTENERS);
		}
		private void pickStrawberries(BlockState state) {
			HedgehogEntity.this.playSound(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, 1.0F, 1.0F);
			//HedgehogEntity.this.world.setBlockState(this.targetPos, state.with(StrawberryBushBlock.AGE, 2), Block.NOTIFY_LISTENERS);
		}
		public boolean canStart() { return super.canStart(); }
		public void start() { this.timer = 0; super.start(); }
	}
	public class AttackGoal extends MeleeAttackGoal {
		public AttackGoal(double speed, boolean pauseWhenIdle) { super(HedgehogEntity.this, speed, pauseWhenIdle); }
		protected void attack(LivingEntity target, double squaredDistance) {
			double d = 2 + target.getWidth();
			if (squaredDistance <= d && this.isCooledDown()) {
				this.resetCooldown();
				this.mob.tryAttack(target);
			}
		}
	}
}
