package fun.mousewich.entity.tnt;

import fun.mousewich.ModBase;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class PowderKegEntity extends ModTntEntity {
	public PowderKegEntity(EntityType<? extends Entity> entityType, World world) { this(entityType, world, ModBase.SPRUCE_POWDER_KEG.asBlock()); }
	public PowderKegEntity(EntityType<? extends Entity> entityType, World world, Block block) { super(entityType, world, block); }
	public PowderKegEntity(World world, double x, double y, double z, LivingEntity igniter, Block block) {
		super(ModBase.POWDER_KEG_ENTITY, world, x, y, z, igniter, block);
	}

	@Override
	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625), this.getZ(), 5F, Explosion.DestructionType.BREAK);
		propagateExplosion(3);
	}
	@Override public boolean shouldDestroyBlocks() { return true; }
}
