package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PinkSlimeBallEntity extends ThrownItemEntity {
	public PinkSlimeBallEntity(EntityType<? extends PinkSlimeBallEntity> entityType, World world) { super(entityType, world); }
	public PinkSlimeBallEntity(World world, LivingEntity owner) { super(ModBase.PINK_SLIME_BALL_ENTITY, owner, world); }
	public PinkSlimeBallEntity(World world, double x, double y, double z) { super(ModBase.PINK_SLIME_BALL_ENTITY, x, y, z, world); }
	@Override
	protected Item getDefaultItem() { return ModBase.PINK_SLIME_BALL; }
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return itemStack.isEmpty() ? ModBase.ITEM_PINK_SLIME_PARTICLE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
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
		if (entity instanceof SlimeEntity) {
			if (entity instanceof MagmaCubeEntity) return;
			((SlimeEntity) entity).heal(1);
		}
		else {
			if (entity instanceof LivingEntity livingEntity) {
				livingEntity.addStatusEffect(new StatusEffectInstance(ModBase.STICKY_EFFECT, 100), this.getOwner());
			}
			entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), this.getOwner() instanceof SlimeEntity slime ? (1.5F + 0.5F * slime.getSize()) : 0F);
		}
	}
	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte)3);
			this.discard();
		}
	}
}
