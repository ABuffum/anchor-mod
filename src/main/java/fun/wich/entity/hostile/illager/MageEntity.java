package fun.wich.entity.hostile.illager;

import fun.wich.enchantment.GravityEnchantment;
import fun.wich.entity.ModEntityType;
import fun.wich.sound.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MageEntity extends CapedSpellcastingIllagerEntity implements RangedAttackMob {

	public MageEntity(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 10;
	}
	public MageEntity(World world) {
		super(ModEntityType.MAGE_ENTITY, world);
		this.experiencePoints = 10;
	}

	@Override
	protected void initGoals() {
		super.initGoals();
		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(1, new LookAtTargetGoal());
		this.goalSelector.add(4, new PushTargetsGoal(this));
		this.goalSelector.add(6, new ProjectileAttackGoal(this, 1.25, 60, 10.0f) {
			@Override public boolean canStart() { return super.canStart() && MageEntity.this.squaredDistanceTo(MageEntity.this.getTarget()) > 25; }
			@Override public void start() { super.start(); MageEntity.this.setAttacking(true); }
			@Override public void stop() { super.stop(); MageEntity.this.setAttacking(false); }
		});
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
	protected SoundEvent getAmbientSound() { return ModSoundEvents.ENTITY_MAGE_AMBIENT; }
	@Override
	protected SoundEvent getDeathSound() { return ModSoundEvents.ENTITY_MAGE_DEATH; }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) { return ModSoundEvents.ENTITY_MAGE_HURT; }
	@Override
	protected SoundEvent getCastSpellSound() { return ModSoundEvents.ENTITY_MAGE_CAST_SPELL; }
	@Override
	public SoundEvent getCelebratingSound() { return ModSoundEvents.ENTITY_MAGE_CELEBRATE; }

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

	//Spellcasting
	@Override
	public State getState() {
		if (this.isSpellcasting()) return State.SPELLCASTING;
		if (this.isCelebrating()) return State.CELEBRATING;
		return State.NEUTRAL;
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
		Vec3d vec3d = target.getVelocity();
		double d = target.getX() + vec3d.x - MageEntity.this.getX();
		double e = target.getEyeY() - (double)1.1f - MageEntity.this.getY();
		double f = target.getZ() + vec3d.z - MageEntity.this.getZ();
		double g = Math.sqrt(d * d + f * f);

		Potion potion = Potions.HARMING;
		PotionEntity potionEntity = new PotionEntity(MageEntity.this.world, MageEntity.this);
		potionEntity.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
		potionEntity.setPitch(potionEntity.getPitch() - -20.0f);
		potionEntity.setVelocity(d, e + g * 0.2, f, 0.75f, 8.0f);
		MageEntity.this.world.spawnEntity(potionEntity);
	}

	protected class PushTargetsGoal extends CastSpellGoal {
		protected MageEntity mob;
		public PushTargetsGoal(MageEntity mob) { this.mob = mob; }
		@Override
		protected int getSpellTicks() { return 20; }
		@Override
		protected int startTimeDelay() { return 40; }
		@Override
		public boolean canStart() { return super.canStart() && MageEntity.this.squaredDistanceTo(MageEntity.this.getTarget()) <= 25; }

		@Override
		protected void castSpell() {
			LivingEntity target = MageEntity.this.getTarget();
			if (target == null) return;
			double force = -8;
			if (GravityEnchantment.wearingShulkerArmor(target) || target instanceof ShulkerEntity) return;
			if (target.isSpectator()) return;
			if (target instanceof PlayerEntity player && player.isCreative()) return;
			Vec3d delta = new Vec3d(MageEntity.this.getX() - target.getX(), MageEntity.this.getY() - target.getY(), MageEntity.this.getZ() - target.getZ());
			Vec3d normal = delta.normalize();
			double X = MathHelper.clamp(normal.x * force, -12, 12);
			double Y = MathHelper.clamp(normal.y * force, -3, -3);
			double Z = MathHelper.clamp(normal.z * force, -12, 12);
			target.addVelocity(X, Y, Z);
			target.velocityModified = true;
		}

		@Override
		protected SoundEvent getSoundPrepare() { return ModSoundEvents.ENTITY_MAGE_PREPARE_SPELL; }
		@Override
		protected Spell getSpell() { return Spell.SUMMON_VEX; }
	}
}
