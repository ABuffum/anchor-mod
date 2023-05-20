package fun.mousewich.haven.entity.tnt;

import fun.mousewich.entity.tnt.ModTntEntity;
import fun.mousewich.haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ViolentTntEntity extends ModTntEntity {
	public ViolentTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.VIOLENT_TNT.asBlock()); }
	public ViolentTntEntity(World world, double x, double y, double z, LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.VIOLENT_TNT.asBlock());
	}
	public ViolentTntEntity(World world, double x, double y, double z, LivingEntity igniter, Block block) {
		super(HavenMod.VIOLENT_TNT_ENTITY, world, x, y, z, igniter, block);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 6F, Explosion.DestructionType.BREAK);
		propagateExplosion(3);
	}
	@Override
	public boolean shouldDestroyBlocks() { return true; }
	@Override
	public float knockbackMultiplier() { return 0; }
}
