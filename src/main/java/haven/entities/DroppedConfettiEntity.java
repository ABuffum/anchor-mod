package haven.entities;

import haven.HavenMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

public class DroppedConfettiEntity extends ThrownItemEntity {
	public DroppedConfettiEntity(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }

	public DroppedConfettiEntity(World world, LivingEntity owner) {
		super(HavenMod.DROPPED_CONFETTI_ENTITY, owner, world);
	}

	public DroppedConfettiEntity(World world, double x, double y, double z) {
		super(HavenMod.DROPPED_CONFETTI_ENTITY, x, y, z, world);
	}

	@Override
	protected Item getDefaultItem() { return Items.AIR; }

	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
		super.onEntityHit(entityHitResult);
	}

	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
			Vec3d pos = hitResult.getPos();
			this.world.spawnEntity(new ConfettiCloudEntity(this.world, this.getX(), this.getY(), this.getZ()));
			this.kill(); // kills the projectile
		}
	}
}
