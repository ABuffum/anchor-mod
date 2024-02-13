package fun.wich.entity.hostile.illager;

import fun.wich.entity.IceChunkEntity;
import fun.wich.entity.ModEntityType;
import fun.wich.entity.projectile.SlowingSnowballEntity;
import fun.wich.origins.power.EnableTradePower;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class IceologerEntity extends CapedSpellcastingIllagerEntity implements RangedAttackMob {

	public IceologerEntity(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 10;
	}
	public IceologerEntity(World world) {
		super(ModEntityType.ICEOLOGER_ENTITY, world);
		this.experiencePoints = 10;
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new SpellcastingIllagerEntity.LookAtTargetGoal());
		this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 6f, 0.6, 1.0, e -> !EnableTradePower.canTrade(e, getType())));
		this.goalSelector.add(4, new SlowingSnowballEntity.SlowingProjectileAttackGoal(this, 1.25, 20, 10.0f) {
			@Override public void start() { super.start(); IceologerEntity.this.setAttacking(true); }
			@Override public void stop() { super.stop(); IceologerEntity.this.setAttacking(false); }
		});
		this.goalSelector.add(5, new SummonIceGoal());
		this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
		this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0f, 1.0f));
		this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0f));
		this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class).setGroupRevenge());
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, MerchantEntity.class, false).setMaxTimeWithoutVisibility(300));
		this.targetSelector.add(3, new FollowTargetGoal<>(this, IronGolemEntity.class, false));
	}

	//Sound
	@Override
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_ICEOLOGER_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_ICEOLOGER_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_ICEOLOGER_HURT; }
	@Override
	protected SoundEvent getCastSpellSound() { return ModSoundEvents.ENTITY_ICEOLOGER_CAST_SPELL; }
	@Override
	public SoundEvent getCelebratingSound() { return ModSoundEvents.ENTITY_ICEOLOGER_CELEBRATE; }

	//Raider
	@Override
	public void addBonusForWave(int wave, boolean unused) { }
	@Override
	public boolean isTeammate(Entity other) {
		if (super.isTeammate(other)) return true;
		if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER) {
			return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
		}
		return false;
	}

	//Spell casting
	@Override
	public State getState() {
		if (this.isSpellcasting()) return State.SPELLCASTING;
		if (this.isCelebrating()) return State.CELEBRATING;
		if (this.isAttacking()) return State.ATTACKING;
		return State.NEUTRAL;
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

	protected class SummonIceGoal extends CastSpellGoal {
		@Override
		protected int getSpellTicks() { return 80; }
		@Override
		protected int startTimeDelay() { return 200; }

		@Override
		public boolean canStart() {
			if (!super.canStart()) return false;
			LivingEntity target = IceologerEntity.this.getTarget();
			if (target == null) return false;
			if (!SlowingSnowballEntity.targetSlowed(target)) return false;
			return IceChunkEntity.isOpen(target.world, target.getBlockPos().add(0, 1.5f * target.getHeight(), 0));
		}
		@Override
		protected void castSpell() {
			ServerWorld serverWorld = (ServerWorld)IceologerEntity.this.world;
			LivingEntity target = IceologerEntity.this.getTarget();
			if (target == null) return;
			IceChunkEntity entity = new IceChunkEntity(world, target.getX(), target.getY() + (1.5f * target.getHeight()), target.getZ());
			entity.setOwner(IceologerEntity.this);
			serverWorld.spawnEntity(entity);
		}
		@Override
		protected SoundEvent getSoundPrepare() { return ModSoundEvents.ENTITY_ICEOLOGER_PREPARE_SUMMON; }
		@Override
		protected SpellcastingIllagerEntity.Spell getSpell() { return SpellcastingIllagerEntity.Spell.SUMMON_VEX; }
	}
}
