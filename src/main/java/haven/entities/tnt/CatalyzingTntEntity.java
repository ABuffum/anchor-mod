package haven.entities.tnt;

import haven.ModBase;
import haven.blocks.tnt.ModTntBlock;
import net.minecraft.block.Block;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class CatalyzingTntEntity extends ModTntEntity {
	public CatalyzingTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world); }
	public CatalyzingTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		super(ModBase.CATALYZING_TNT_ENTITY, world, x, y, z, igniter);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 0F, Explosion.DestructionType.NONE);
		propagateExplosion(32);
	}
	@Override
	public boolean destroyBlocks() { return false; }
	@Override
	public boolean doDamage() { return false; }
	@Override
	public boolean doKnockback() { return false; }
	@Override
	public boolean makeSound() { return false; }

	@Override
	protected void propagate(BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof ModTntBlock modTntBlock) {
			modTntBlock.primeTnt(world, pos);
			world.removeBlock(pos, false);
		}
		else if (block instanceof TntBlock) {
			TntBlock.primeTnt(world, pos);
			world.removeBlock(pos, false);
		}
	}
}
