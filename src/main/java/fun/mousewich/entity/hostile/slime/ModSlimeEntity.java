package fun.mousewich.entity.hostile.slime;

import fun.mousewich.entity.ai.goal.slime.FaceTowardTargetGoal;
import fun.mousewich.entity.ai.goal.slime.MoveGoal;
import fun.mousewich.entity.ai.goal.slime.RandomLookGoal;
import fun.mousewich.entity.ai.goal.slime.SwimmingGoal;
import fun.mousewich.origins.power.MobHostilityPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public abstract class ModSlimeEntity extends SlimeEntity {
	protected Goal swimmingGoal;
	public ModSlimeEntity(EntityType<? extends SlimeEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, swimmingGoal = new SwimmingGoal(this));
		this.goalSelector.add(2, new FaceTowardTargetGoal(this));
		this.goalSelector.add(3, new RandomLookGoal(this));
		this.goalSelector.add(5, new MoveGoal(this));
		this.targetSelector.add(1, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, livingEntity -> Math.abs(livingEntity.getY() - this.getY()) <= 4.0));
		this.targetSelector.add(2, MobHostilityPower.makeHostilityGoal(this, getType()));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, true));
	}

	@Override
	public SoundEvent getJumpSound() { return super.getJumpSound(); }
	@Override
	public int getTicksUntilNextJump() { return super.getTicksUntilNextJump(); }
	@Override
	public boolean makesJumpSound() { return super.makesJumpSound(); }
	@Override
	public float getSoundVolume() { return super.getSoundVolume(); }
	@Override
	public boolean canAttack() { return super.canAttack(); }

	public float getJumpSoundPitch() {
		float f = this.isSmall() ? 1.4f : 0.8f;
		return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f) * f;
	}
}
