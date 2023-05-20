package fun.mousewich.entity.projectile;

import fun.mousewich.ModBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class BoneShardEntity extends ThrownItemEntity {
	public BoneShardEntity(EntityType<? extends BoneShardEntity> entityType, World world) { super(entityType, world); }
	public BoneShardEntity(World world, double x, double y, double z) { super(ModBase.BONE_SHARD_PROJECTILE_ENTITY, x, y, z, world); }
	public BoneShardEntity(World world, LivingEntity owner) { super(ModBase.BONE_SHARD_PROJECTILE_ENTITY, owner, world); }

	@Override
	protected Item getDefaultItem() { return ModBase.BONE_SHARD; }
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		Difficulty difficulty = entity.world.getDifficulty();
		float damage = difficulty == Difficulty.HARD ? 6 : difficulty == Difficulty.NORMAL ? 4 : 2;
		entity.damage(new ProjectileDamageSource("bone_shard", this, this.getOwner()).setProjectile(), damage);
		this.discard();
	}
}
