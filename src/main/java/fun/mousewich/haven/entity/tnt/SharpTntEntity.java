package fun.mousewich.haven.entity.tnt;

import fun.mousewich.entity.tnt.ModTntEntity;
import fun.mousewich.haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SharpTntEntity extends ModTntEntity {
	public SharpTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.SHARP_TNT.asBlock()); }
	public SharpTntEntity(World world, double x, double y, double z, LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.SHARP_TNT.asBlock());
	}
	public SharpTntEntity(World world, double x, double y, double z, LivingEntity igniter, Block block) {
		super(HavenMod.SHARP_TNT_ENTITY, world, x, y, z, igniter, block);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4F, Explosion.DestructionType.NONE);
		propagateExplosion(3);
	}
	@Override
	public boolean shouldDestroyBlocks() { return false; }
	@Override
	public float damageMultiplier() { return 3; }
	@Override
	public float knockbackMultiplier() { return 0; }
}
