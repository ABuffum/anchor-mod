package fun.mousewich.haven.entity.tnt;

import fun.mousewich.entity.tnt.ModTntEntity;
import fun.mousewich.haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SoftTntEntity extends ModTntEntity {
	public SoftTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.SOFT_TNT.asBlock().getDefaultState()); }
	public SoftTntEntity(World world, double x, double y, double z, LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.SOFT_TNT.asBlock().getDefaultState());
	}
	public SoftTntEntity(World world, double x, double y, double z, LivingEntity igniter, BlockState state) {
		super(HavenMod.SOFT_TNT_ENTITY, world, x, y, z, igniter, state);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), -3F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean shouldDestroyBlocks() { return false; }
	@Override
	public float damageMultiplier() { return 0; }
	@Override
	public float knockbackMultiplier() { return 1.5F; }
}
