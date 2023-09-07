package fun.mousewich.entity.tnt;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class PowderKegEntity extends ModTntEntity {
	public PowderKegEntity(EntityType<? extends Entity> entityType, World world) { this(entityType, world, ModBase.SPRUCE_POWDER_KEG.asBlock().getDefaultState()); }
	public PowderKegEntity(EntityType<? extends Entity> entityType, World world, BlockState state) { super(entityType, world, state); }
	public PowderKegEntity(World world, double x, double y, double z, LivingEntity igniter, BlockState state) {
		super(ModBase.POWDER_KEG_ENTITY, world, x, y, z, igniter, state);
	}

	@Override
	protected void explode() {
		this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625), this.getZ(), 5F, Explosion.DestructionType.BREAK);
		propagateExplosion(3);
	}
	@Override public boolean shouldDestroyBlocks() { return true; }
}
