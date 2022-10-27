package haven.entities.passive;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import haven.HavenMod;
import haven.damage.HavenEntityDamageSource;
import haven.entities.ai.MoveToHuntGoal;
import haven.origins.powers.AttackedByHedgehogsPower;
import haven.origins.powers.ScareHedgehogsPower;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class HedgehogEntity extends AnimalEntity {
	private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(
			Items.APPLE, Items.MELON_SLICE, Items.SPIDER_EYE,
			Items.GLOW_BERRIES, Items.SWEET_BERRIES,
			HavenMod.CHERRY_ITEM, HavenMod.STRAWBERRY,
			ItemsRegistry.CABBAGE.get(), ItemsRegistry.TOMATO.get()
	);

	public HedgehogEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	//attack bugs or any player marked as hedgehog-attackable
	private boolean canHedgehogTarget(LivingEntity target) { //attack "small" bugs or any player marked as hedgehog-attackable
		return (target instanceof CaveSpiderEntity
				|| target instanceof SilverfishEntity
				|| target instanceof EndermiteEntity
				|| target instanceof BeeEntity
				|| AttackedByHedgehogsPower.HasActivePower(target));
	}

	protected void initGoals() {
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4D));
		this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
		this.goalSelector.add(3, new FleeEntityGoal<>(this, FoxEntity.class, 8.0F, 1.6D, 1.4D));
		this.goalSelector.add(3, new FleeEntityGoal<>(this, WolfEntity.class, 8.0F, 1.6D, 1.4D, (entity) -> !((WolfEntity)entity).isTamed()));
		this.goalSelector.add(3, new FleeEntityGoal<>(this, PlayerEntity.class, ScareHedgehogsPower::HasActivePower, 8.0F, 1.6D, 1.4D, EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR::test));
		this.goalSelector.add(4, new TemptGoal(this, 1.0D, BREEDING_INGREDIENT, false));
		this.targetSelector.add(4, new FollowTargetGoal<LivingEntity>(this, LivingEntity.class, 10, false, false, this::canHedgehogTarget));
		this.goalSelector.add(5, new MoveToHuntGoal(this, 12, 1.5D) {
			public boolean canTarget(LivingEntity target) { return canHedgehogTarget(target); }
		});
		this.goalSelector.add(6, new HedgehogEntity.AttackGoal(1.2D, true));
		this.goalSelector.add(7, new FollowParentGoal(this, 1.1D));
		this.goalSelector.add(8, new HedgehogEntity.EatSweetBerriesGoal(1.2D, 12, 1));
		this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.add(11, new LookAroundGoal(this));
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return this.isBaby() ? dimensions.height * 0.4F : dimensions.height * 0.5F;
	}

	public static DefaultAttributeContainer.Builder createHedgehogAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
	}

	@Nullable
	@Override
	public HedgehogEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
		return HavenMod.HEDGEHOG_ENTITY.create(serverWorld);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return BREEDING_INGREDIENT.test(stack);
	}

	public boolean damage(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) return false;
		else {
			Entity entity = source.getAttacker();
			if (entity != null) {
				if (!source.isProjectile()) { //only melee
					if (!source.isExplosive()) { //ignore explosions
						if (!source.isMagic()) { //hedgehogs are weak to wizardry
							entity.damage(HavenEntityDamageSource.quills(this), Math.max(amount * 0.75F, 1.0F));
						}
					}
				}
			}
			return super.damage(source, amount);
		}
	}

	public boolean tryAttack(Entity target) {
		return target.damage(DamageSource.mob(this), 1);
	}

	public class EatSweetBerriesGoal extends MoveToTargetPosGoal {
		private static final int EATING_TIME = 40;
		protected int timer;

		public EatSweetBerriesGoal(double speed, int range, int maxYDifference) {
			super(HedgehogEntity.this, speed, range, maxYDifference);
		}

		public double getDesiredSquaredDistanceToTarget() { return 2.0D; }

		public boolean shouldResetPath() { return this.tryingTime % 100 == 0; }

		protected boolean isTargetPos(WorldView world, BlockPos pos) {
			BlockState blockState = world.getBlockState(pos);
			return blockState.isOf(Blocks.SWEET_BERRY_BUSH) && blockState.get(SweetBerryBushBlock.AGE) >= 2 || CaveVines.hasBerries(blockState);
		}

		public void tick() {
			if (this.hasReached()) {
				if (this.timer >= EATING_TIME) this.eatSweetBerry();
				else ++this.timer;
			}
			else if (!this.hasReached() && HedgehogEntity.this.random.nextFloat() < 0.05F) {
				HedgehogEntity.this.playSound(SoundEvents.ENTITY_FOX_SNIFF, 1.0F, 1.0F);
			}
			super.tick();
		}

		protected void eatSweetBerry() {
			if (HedgehogEntity.this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
				BlockState blockState = HedgehogEntity.this.world.getBlockState(this.targetPos);
				if (blockState.isOf(Blocks.SWEET_BERRY_BUSH)) this.pickSweetBerries(blockState);
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

		public boolean canStart() { return super.canStart(); }

		public void start() {
			this.timer = 0;
			super.start();
		}
	}

	public class AttackGoal extends MeleeAttackGoal {
		public AttackGoal(double speed, boolean pauseWhenIdle) {
			super(HedgehogEntity.this, speed, pauseWhenIdle);
		}

		protected void attack(LivingEntity target, double squaredDistance) {
			double d = 2 + target.getWidth();
			if (squaredDistance <= d && this.isCooledDown()) {
				this.resetCooldown();
				this.mob.tryAttack(target);
			}
		}
	}
}
