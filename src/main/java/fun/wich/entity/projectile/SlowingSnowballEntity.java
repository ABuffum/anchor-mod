package fun.wich.entity.projectile;

import fun.wich.ModBase;
import fun.wich.entity.FreezeConversionEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SlowingSnowballEntity extends ThrownItemEntity {
	public SlowingSnowballEntity(EntityType<? extends SlowingSnowballEntity> entityType, World world) { super(entityType, world); }
	public SlowingSnowballEntity(World world, LivingEntity owner) { super(ModBase.SLOWING_SNOWBALL_ENTITY, owner, world); }
	public SlowingSnowballEntity(World world, double x, double y, double z) { super(ModBase.SLOWING_SNOWBALL_ENTITY, x, y, z, world); }
	@Override
	protected Item getDefaultItem() { return Items.SNOWBALL; }
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
	}

	@Override
	public void handleStatus(byte status) {
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		int i = entity instanceof BlazeEntity ? 3 : 0;
		Entity owner = this.getOwner();
		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600), owner == null ? this : owner);
		}
		entity.damage(DamageSource.thrownProjectile(this, owner), i);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte)3);
			this.discard();
		}
	}

	public static boolean targetSlowed(LivingEntity target) {
		return target != null && (target.hasStatusEffect(StatusEffects.SLOWNESS) || FreezeConversionEntity.InPowderSnow(target));
	}

	public static class SlowingProjectileAttackGoal extends ProjectileAttackGoal {
		protected final MobEntity mob;
		public SlowingProjectileAttackGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
			super(mob, mobSpeed, intervalTicks, maxShootRange);
			this.mob = (MobEntity)mob;
		}
		@Override
		public boolean canStart() { return super.canStart() && !SlowingSnowballEntity.targetSlowed(this.mob.getTarget()); }
		@Override
		public boolean shouldContinue() { return super.shouldContinue() && !SlowingSnowballEntity.targetSlowed(this.mob.getTarget()); }
	}
}
