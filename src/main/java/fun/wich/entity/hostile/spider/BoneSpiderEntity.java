package fun.wich.entity.hostile.spider;

import fun.wich.ModBase;
import fun.wich.entity.ModDataHandlers;
import fun.wich.entity.projectile.BoneShardEntity;
import fun.wich.origins.power.MobHostilityPower;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public class BoneSpiderEntity extends SpiderEntity implements RangedAttackMob {
	public static final int MAX_FIRE_TIME = 15;
	private static final TrackedData<Integer> FIRE_TIME = DataTracker.registerData(BoneSpiderEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<OptionalInt> TARGET = DataTracker.registerData(BoneSpiderEntity.class, ModDataHandlers.OPTIONAL_INT);
	public BoneSpiderEntity(EntityType<? extends BoneSpiderEntity> entityType, World world) { super(entityType, world); }
	public static DefaultAttributeContainer.Builder createBoneSpiderAttributes() {
		return SpiderEntity.createSpiderAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
	}
	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FIRE_TIME, 0);
		this.dataTracker.startTracking(TARGET, OptionalInt.empty());
	}
	public int getFireTime() { return this.dataTracker.get(FIRE_TIME); }

	@Override
	public void tick() {
		super.tick();
		int fireTime = getFireTime();
		if (fireTime > 0) {
			if (fireTime == 1) {
				Optional<Entity> dataTarget = this.dataTracker.get(TARGET).stream().mapToObj(this.world::getEntityById).filter(Objects::nonNull).findFirst();
				if (dataTarget.orElse(null) instanceof LivingEntity target) shoot(target);
				this.dataTracker.set(TARGET, OptionalInt.empty());
			}
			this.dataTracker.set(FIRE_TIME, fireTime - 1);
		}
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new AvoidSunlightGoal(this));
		this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
		//this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4f));
		//this.goalSelector.add(4, new AttackGoal(this));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false, e -> MobHostilityPower.shouldBeHostile(e, ModBase.BONE_SPIDER_ENTITY)));
		this.goalSelector.add(4, new BoneSpiderShootGoal(this, 1, MAX_FIRE_TIME + 20, 15));
	}

	@Override
	public int getMinAmbientSoundDelay() { return 120; }
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_BONE_SPIDER_AMBIENT; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_BONE_SPIDER_HURT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_BONE_SPIDER_DEATH; }
	@Override
	protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(ModSoundEvents.ENTITY_BONE_SPIDER_STEP, 0.15f, 1.0f); }

	@Override
	public boolean tryAttack(Entity target) {
		boolean bl = target.damage(DamageSource.mob(this), (int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
		if (bl) {
			this.applyDamageEffects(this, target);
			this.playSound(ModSoundEvents.ENTITY_BONE_SPIDER_ATTACK, 1.0f, 1.0f);
		}
		return bl;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) { attack(target); }
	public void attack(LivingEntity target) {
		this.dataTracker.set(FIRE_TIME, MAX_FIRE_TIME);
		this.dataTracker.set(TARGET, OptionalInt.of(target.getId()));
		this.playSound(ModSoundEvents.ENTITY_BONE_SPIDER_SPIT, 1.0F, 1F / (this.getRandom().nextFloat() * 0.4F + 1F));
	}

	private void shoot(LivingEntity target) {
		if (target == null || !target.isAlive()) return;
		BoneShardEntity projectile = new BoneShardEntity(this.world, this);
		double targetX = target.getX(), targetZ = target.getZ();
		double x = this.getX(), z = this.getZ();
		double e = targetX - x;
		double f = target.getBodyY(0.3333333333333333) - projectile.getY();
		double g = targetZ - z;
		double h = Math.sqrt(e * e + g * g) * 0.2;
		projectile.setVelocity(e, f + h, g, 2F, 14 - this.world.getDifficulty().getId() * 4);
		this.world.spawnEntity(projectile);
		Vec3d fire = new Vec3d(-e, 0, -g).normalize();
		this.setVelocity(fire.getX() * 0.2, 0.1, fire.getZ() * 0.2);
		this.dataTracker.set(TARGET, OptionalInt.empty());
	}
}
