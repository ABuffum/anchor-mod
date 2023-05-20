package fun.mousewich.block.echo;

import fun.mousewich.ModBase;
import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.basic.ModWallBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class EchoWallBlock extends ModWallBlock {
	public EchoWallBlock(BlockConvertible block) { super(block); }
	public EchoWallBlock(Block block) { super(block); }
	public EchoWallBlock(Settings settings) { super(settings); }
	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		ModBase.ECHO_BLOCK.asBlock().onProjectileHit(world, state, hit, projectile);
	}
}
