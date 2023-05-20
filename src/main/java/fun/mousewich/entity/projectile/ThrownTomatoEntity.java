package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import fun.mousewich.origins.power.ClownPacifistPower;
import fun.mousewich.origins.power.PowersUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownTomatoEntity extends ThrownItemEntity {
	public ThrownTomatoEntity(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }
	public ThrownTomatoEntity(World world, LivingEntity owner) { super(ModBase.THROWABLE_TOMATO_ENTITY, owner, world); }
	public ThrownTomatoEntity(World world, double x, double y, double z) { super(ModBase.THROWABLE_TOMATO_ENTITY, x, y, z, world); }

	@Override
	protected Item getDefaultItem() { return ModBase.THROWABLE_TOMATO_ITEM; }

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return itemStack.isEmpty() ? ModBase.TOMATO_PARTICLE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
			for(int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		Entity owner = this.getOwner();
		entity.damage(DamageSource.thrownProjectile(this, owner), 0F);
		if (entity instanceof LivingEntity livingEntity) {
			livingEntity.addStatusEffect((new StatusEffectInstance(ModBase.BOO_EFFECT, 20 * 3, 0)));
			if (PowersUtil.Active(livingEntity, ClownPacifistPower.class)) {
				if (owner instanceof LivingEntity livingOwner) {
					if (!livingOwner.world.isClient) {
						livingOwner.addStatusEffect(new StatusEffectInstance(ModBase.KILLJOY_EFFECT, 20 * 3, 0));
					}
				}
			}
			if (livingEntity.getEntityWorld().isClient) {
				if (livingEntity instanceof PlayerEntity player) {
					if (player.hasStatusEffect(ModBase.BOO_EFFECT)) player.sendMessage(Text.of("Boo!"), true);
					else player.sendMessage(Text.of("Boo!!!"), true);
				}
			}
			//TODO: Tomato Squelch sound on hit
		}
		if (PowersUtil.Active(owner, ClownPacifistPower.class) && entity instanceof PlayerEntity) {
			entity.addVelocity(1, 0, 0);
			entity.velocityModified = true;
		}
	}

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte)3);
			this.kill();
		}
	}
}
