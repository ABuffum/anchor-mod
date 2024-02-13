package fun.wich.entity.projectile;

import fun.wich.entity.ModEntityType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
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

public class MelonSeedProjectileEntity extends ThrownItemEntity {
	public MelonSeedProjectileEntity(World world, LivingEntity owner) { super(ModEntityType.MELON_SEED_PROJECTILE_ENTITY, owner, world); }
	public MelonSeedProjectileEntity(World world, double x, double y, double z) { super(ModEntityType.MELON_SEED_PROJECTILE_ENTITY, x, y, z, world); }
	public MelonSeedProjectileEntity(EntityType<MelonSeedProjectileEntity> entityType, World world) { super(entityType, world); }
	@Override
	protected Item getDefaultItem() { return Items.MELON_SEEDS; }

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte id) {
		if (id == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		entity.damage(new ProjectileDamageSource("melon_seeded", this, this.getOwner()).setProjectile(), 1F);
	}

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.sendEntityStatus(this, (byte) 3);
			this.discard();
		}
	}
}
