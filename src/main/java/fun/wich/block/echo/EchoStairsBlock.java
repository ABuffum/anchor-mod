package fun.wich.block.echo;

import fun.wich.ModBase;
import fun.wich.block.BlockConvertible;
import fun.wich.block.basic.ModStairsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class EchoStairsBlock extends ModStairsBlock {
	public EchoStairsBlock(BlockConvertible block) { super(block); }
	public EchoStairsBlock(Block block) { super(block); }
	public EchoStairsBlock(BlockState blockState, Settings settings) { super(blockState, settings); }
	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		ModBase.ECHO_BLOCK.asBlock().onProjectileHit(world, state, hit, projectile);
	}
}
