package fun.mousewich.block.amethyst;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.basic.ModStairsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class AmethystStairsBlock extends ModStairsBlock {
	public AmethystStairsBlock(BlockConvertible block) { super(block); }
	public AmethystStairsBlock(Block block) { super(block); }
	public AmethystStairsBlock(BlockState blockState, Settings settings) { super(blockState, settings); }
	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		Blocks.AMETHYST_BLOCK.onProjectileHit(world, state, hit, projectile);
	}
}
