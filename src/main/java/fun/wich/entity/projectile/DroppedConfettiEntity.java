package fun.wich.entity.projectile;

import fun.wich.entity.ModEntityType;
import fun.wich.entity.cloud.DragonBreathCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DroppedConfettiEntity extends ThrownItemEntity {
	public DroppedConfettiEntity(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }
	public DroppedConfettiEntity(World world, LivingEntity owner) { super(ModEntityType.DROPPED_CONFETTI_ENTITY, owner, world); }
	public DroppedConfettiEntity(World world, double x, double y, double z) { super(ModEntityType.DROPPED_CONFETTI_ENTITY, x, y, z, world); }

	@Override
	protected Item getDefaultItem() { return Items.AIR; }

	protected void onEntityHit(EntityHitResult entityHitResult) { super.onEntityHit(entityHitResult); }

	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			Vec3d pos = hitResult.getPos();
			this.world.spawnEntity(new DragonBreathCloudEntity(this.world, pos.getX(), pos.getY(), pos.getZ()));
			this.kill();
		}
	}
}
