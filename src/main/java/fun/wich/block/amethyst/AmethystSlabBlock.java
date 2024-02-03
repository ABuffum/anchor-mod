package fun.wich.block.amethyst;

import fun.wich.block.BlockConvertible;
import fun.wich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class AmethystSlabBlock extends ModSlabBlock {
	public AmethystSlabBlock(BlockConvertible block) { super(block); }
	public AmethystSlabBlock(Block block) { super(block); }
	public AmethystSlabBlock(Settings settings) { super(settings); }
	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		Blocks.AMETHYST_BLOCK.onProjectileHit(world, state, hit, projectile);
	}
}
