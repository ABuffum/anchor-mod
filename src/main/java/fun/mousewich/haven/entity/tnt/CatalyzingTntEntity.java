package fun.mousewich.haven.entity.tnt;

import fun.mousewich.block.tnt.ModTntBlock;
import fun.mousewich.entity.tnt.ModTntEntity;
import fun.mousewich.haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class CatalyzingTntEntity extends ModTntEntity {
	public CatalyzingTntEntity(EntityType<? extends Entity> entityType, World world) { super(entityType, world, HavenMod.CATALYZING_TNT.asBlock().getDefaultState()); }
	public CatalyzingTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter) {
		this(world, x, y, z, igniter, HavenMod.CATALYZING_TNT.asBlock().getDefaultState());
	}
	public CatalyzingTntEntity(World world, double x, double y, double z, @Nullable LivingEntity igniter, BlockState state) {
		super(HavenMod.CATALYZING_TNT_ENTITY, world, x, y, z, igniter, state);
	}

	@Override
	protected void explode() {
		Explosion explosion = this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 0F, Explosion.DestructionType.NONE);
		propagateExplosion(32);
	}
	@Override
	public boolean shouldDestroyBlocks() { return false; }
	@Override
	public boolean shouldDamage() { return false; }
	@Override
	public boolean shouldDoKnockback() { return false; }
	@Override
	public boolean shouldMakeSound() { return false; }

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
