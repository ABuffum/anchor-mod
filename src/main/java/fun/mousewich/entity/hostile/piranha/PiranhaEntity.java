package fun.mousewich.entity.hostile.piranha;

import fun.mousewich.ModBase;
import fun.mousewich.entity.ai.goal.ModMeleeAttackGoal;
import fun.mousewich.entity.blood.BloodType;
import fun.mousewich.entity.blood.EntityWithBloodType;
import fun.mousewich.entity.hostile.skeleton.SunkenSkeletonEntity;
import fun.mousewich.entity.hostile.slime.TropicalSlimeEntity;
import fun.mousewich.entity.passive.frog.TadpoleEntity;
import fun.mousewich.origins.power.MobHostilityPower;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class PiranhaEntity extends SchoolingFishEntity implements EntityWithBloodType {
	public PiranhaEntity(EntityType<? extends PiranhaEntity> entityType, World world) { super(entityType, world); }
	private boolean canTarget() {
		LivingEntity target = this.getTarget();
		return target != null && canPiranhaTarget(target);
	}
	private static boolean canPiranhaTarget(LivingEntity target) { //you are not welcome in the water
		//Target has to be in the water
		if (!target.isWet()) return false;
		//If the mob should always be hostile, we don't need to dig any deeper here
		if (!MobHostilityPower.shouldBeHostile(target, ModBase.PIRANHA_ENTITY)) {
			//BITE PLAYER BITE PLAYER BITE PLAYER
			if (target instanceof PlayerEntity) {
				if (target.world.getDifficulty() == Difficulty.PEACEFUL) return false;
				else if (target.getGroup() == EntityGroup.AQUATIC) return false;
			}
			//Don't attack aquatic creatures
			else if (target instanceof WaterCreatureEntity) {
				//Nevermind, it would be funny to nuke tadpoles actually
				if (!(target instanceof TadpoleEntity)) return false;
			}
			else if (target instanceof AxolotlEntity) return false;
			else if (target instanceof TurtleEntity) return false;
			else if (target instanceof DrownedEntity) return false;
			else if (target instanceof SunkenSkeletonEntity) return false;
			else if (target instanceof TropicalSlimeEntity) return false;
			else if (target.getGroup() == EntityGroup.AQUATIC) return false;
		}
		//There is safety in boats
		if (target.getRootVehicle() instanceof BoatEntity) return false;
		return true;
	}
	@Override
	protected void initGoals() {
		super.initGoals();
		this.targetSelector.add(-2, new FollowTargetGoal<LivingEntity>(this, LivingEntity.class, 10, false, false, PiranhaEntity::canPiranhaTarget) {
			@Override
			public boolean shouldContinue() {
				LivingEntity target = this.mob.getTarget();
				return target != null && canPiranhaTarget(target) && super.shouldContinue();
			}
		});
		/*this.goalSelector.add(1, new MoveToHuntGoal(this, 12, 1.5) {
			public boolean canTarget(LivingEntity target) { return canPiranhaTarget(target); }
			public void tick() { super.onTick(); if (!canTarget(entity.getTarget())) stop(); }
		});*/
		this.goalSelector.add(1, new ModMeleeAttackGoal(this, 1.2, true) {
			@Override
			public boolean shouldContinue() {
				LivingEntity target = this.mob.getTarget();
				return target != null && canPiranhaTarget(target) && super.shouldContinue();
			}
		});
		this.goalSelector.add(-1, new TemptGoal(this, 1.25, Ingredient.ofItems(ModBase.CHUM), false));
	}
	public static DefaultAttributeContainer.Builder createPiranhaAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0F);
	}
	@Override
	public ItemStack getBucketItem() { return new ItemStack(ModBase.PIRANHA_BUCKET); }
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_PIRANHA_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_PIRANHA_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_PIRANHA_HURT; }
	@Override
	protected SoundEvent getFlopSound() { return ModSoundEvents.ENTITY_PIRANHA_FLOP; }
	@Override
	public boolean canBeLeashedBy(PlayerEntity player) { return !this.isLeashed() && !(this instanceof Monster); }

	@Override public BloodType GetDefaultBloodType() { return ModBase.PIRANHA_BLOOD_TYPE; }
}