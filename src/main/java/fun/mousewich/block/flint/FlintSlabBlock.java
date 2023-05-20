package fun.mousewich.block.flint;

import fun.mousewich.ModBase;
import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.basic.ModSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintSlabBlock extends ModSlabBlock {
	public FlintSlabBlock(BlockConvertible block) { super(block); }
	public FlintSlabBlock(Block block) { super(block); }
	public FlintSlabBlock(Settings settings) { super(settings); }
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return ModBase.FLINT_BLOCK.asBlock().onUse(state, world, pos, player, hand, hit);
	}
}
