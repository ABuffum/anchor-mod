package fun.mousewich.block.echo;

import fun.mousewich.ModBase;
import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class EchoSlabBlock extends ModSlabBlock {
	public EchoSlabBlock(BlockConvertible block) { super(block); }
	public EchoSlabBlock(Block block) { super(block); }
	public EchoSlabBlock(Settings settings) { super(settings); }
	@Override
	public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
		ModBase.ECHO_BLOCK.asBlock().onProjectileHit(world, state, hit, projectile);
	}
}
