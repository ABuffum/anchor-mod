package fun.mousewich.entity.hostile.zombie;

import fun.mousewich.ModBase;
import fun.mousewich.entity.FreezeConversionEntity;
import fun.mousewich.entity.projectile.SlowingSnowballEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

import java.util.Random;

public class FrozenZombieEntity extends ZombieEntity implements RangedAttackMob, FreezeConversionEntity {
	public FrozenZombieEntity(EntityType<? extends FrozenZombieEntity> entityType, World world) {
		super(entityType, world);
	}
	public FrozenZombieEntity(World world) { super(ModBase.FROZEN_ZOMBIE_ENTITY, world); }

	@Override
	protected void initCustomGoals() {
		this.goalSelector.add(2, new FrozenZombieAttackGoal(this, 1.0, false));
		this.goalSelector.add(2, new FrozenZombieProjectileAttackGoal(this, 1.25, 20, 10.0f));
		this.goalSelector.add(6, new MoveThroughVillageGoal(this, 1.0, true, 4, this::canBreakDoors));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge(ZombifiedPiglinEntity.class));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
		this.targetSelector.add(5, new FollowTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
	}

	@Override
	public void applyDamageEffects(LivingEntity attacker, Entity target) {
		super.applyDamageEffects(attacker, target);
		if (target instanceof LivingEntity livingEntity) {
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600), attacker);
		}
	}

	private static boolean targetSlowed(LivingEntity target) {
		return target != null && (target.hasStatusEffect(StatusEffects.SLOWNESS) || FreezeConversionEntity.InPowderSnow(target));
	}

	public static class FrozenZombieAttackGoal extends ZombieAttackGoal {
		public FrozenZombieAttackGoal(ZombieEntity zombie, double speed, boolean pauseWhenMobIdle) { super(zombie, speed, pauseWhenMobIdle); }
		@Override
		public boolean canStart() { return super.canStart() && targetSlowed(this.mob.getTarget()); }
		@Override
		public boolean shouldContinue() { return super.shouldContinue() && targetSlowed(this.mob.getTarget()); }
	}
	public static class FrozenZombieProjectileAttackGoal extends ProjectileAttackGoal {
		private final MobEntity mob;
		public FrozenZombieProjectileAttackGoal(FrozenZombieEntity mob, double mobSpeed, int intervalTicks, float maxShootRange) {
			super(mob, mobSpeed, intervalTicks, maxShootRange);
			this.mob = mob;
		}
		@Override
		public boolean canStart() { return super.canStart() && !targetSlowed(this.mob.getTarget()); }
		@Override
		public boolean shouldContinue() { return super.shouldContinue() && !targetSlowed(this.mob.getTarget()); }
	}

	public static boolean canSpawn(EntityType<FrozenZombieEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		BlockPos blockPos = pos;
		while (world.getBlockState(blockPos = blockPos.up()).isOf(Blocks.POWDER_SNOW)) { }
		return canSpawnInDark(type, world, spawnReason, pos, random) && (spawnReason == SpawnReason.SPAWNER || world.isSkyVisible(blockPos.down()));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
		SlowingSnowballEntity projectile = new SlowingSnowballEntity(this.world, this);
		double d = target.getEyeY() - (double)1.1f;
		double e = target.getX() - this.getX();
		double f = d - projectile.getY();
		double g = target.getZ() - this.getZ();
		double h = Math.sqrt(e * e + g * g) * (double)0.2f;
		projectile.setVelocity(e, f + h, g, 1.6f, 14 - this.world.getDifficulty().getId() * 4);
		this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0f, 0.4f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		this.world.spawnEntity(projectile);
	}

	@Override
	protected ItemStack getSkull() { return ItemStack.EMPTY; }

	@Override
	protected void initEquipment(LocalDifficulty difficulty) { }

	@Override
	public boolean isConvertingInSnow() { return this.getDataTracker().get(ZOMBIE_CONVERTING_IN_SNOW); }
	@Override
	public boolean canConvertInSnow() { return false; }
	@Override
	public boolean isShaking() { return this.isFrozen(); }
	@Override
	public boolean canFreeze() { return false; }

	public boolean isFrozen() {
		return this.getFrozenTicks() >= this.getMinFreezeDamageTicks();
	}

	@Override
	protected void convertInWater() {
		this.convertTo(EntityType.ZOMBIE);
		if (!this.isSilent()) {
			//this.world.syncWorldEvent(null, WorldEvents.HUSK_CONVERTS_TO_ZOMBIE, this.getBlockPos(), 0);
			this.world.playSound(null, this.getBlockPos(), SoundEvents.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, SoundCategory.HOSTILE, 2.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f);
		}
	}
}
