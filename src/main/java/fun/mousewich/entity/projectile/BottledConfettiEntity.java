package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import fun.mousewich.entity.cloud.ConfettiCloudEntity;
import fun.mousewich.util.ColorUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BottledConfettiEntity extends ThrownItemEntity {
	public BottledConfettiEntity(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }
	public BottledConfettiEntity(World world, LivingEntity owner) { super(ModBase.BOTTLED_CONFETTI_ENTITY, owner, world); }
	public BottledConfettiEntity(World world, double x, double y, double z) { super(ModBase.BOTTLED_CONFETTI_ENTITY, x, y, z, world); }

	@Override
	protected Item getDefaultItem() { return ModBase.BOTTLED_CONFETTI_ITEM; }

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == 3) {
			for (DyeColor color : DyeColor.values()) {
				ParticleEffect effect = new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(ColorUtil.GetWoolItem(color)));
				double speed = 0.5f;
				for (int i = 0; i < 40; i++) {
					double angle = 9.0 * i + (Math.random() * 10 - 5);
					double x = Math.cos(angle), z = Math.sin(angle);
					double y = Math.random() * 0.5F;
					this.world.addParticle(effect, this.getX(), this.getY(), this.getZ(), x * speed, y, z * speed);
				}
			}
		}
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0F);
	}

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			Vec3d pos = hitResult.getPos();
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent
			this.world.spawnEntity(new ConfettiCloudEntity(this.world, pos.getX(), pos.getY(), pos.getZ()));
			this.world.sendEntityStatus(this, (byte)3);
			this.kill();
		}
	}
}
