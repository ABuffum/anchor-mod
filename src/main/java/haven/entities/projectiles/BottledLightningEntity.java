package haven.entities.projectiles;

import haven.ModBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BottledLightningEntity extends ThrownItemEntity {
	public BottledLightningEntity(EntityType<? extends ThrownItemEntity> entityType, World world) { super(entityType, world); }

	public BottledLightningEntity(World world, LivingEntity owner) {
		super(ModBase.BOTTLED_LIGHTNING_ENTITY, owner, world);
	}

	public BottledLightningEntity(World world, double x, double y, double z) {
		super(ModBase.BOTTLED_LIGHTNING_ENTITY, x, y, z, world);
	}

	@Override
	protected Item getDefaultItem() { return ModBase.BOTTLED_LIGHTNING_ITEM; }

	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			summonLightning(entityHitResult.getPos(), this.getOwner() instanceof ServerPlayerEntity player ? player : null);
		}
	}

	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			summonLightning(hitResult.getPos(), this.getOwner() instanceof ServerPlayerEntity player ? player : null);
		}
	}

	protected void summonLightning(Vec3d pos, ServerPlayerEntity channeler) {
		LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
		lightningEntity.refreshPositionAfterTeleport(pos);
		lightningEntity.setChanneler(channeler);
		world.spawnEntity(lightningEntity);
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0f, 1.0f);
	}
}
