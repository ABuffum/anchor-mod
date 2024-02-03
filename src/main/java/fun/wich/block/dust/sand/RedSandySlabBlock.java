package fun.wich.block.dust.sand;

import fun.wich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RedSandySlabBlock extends SandySlabBlock {
	public RedSandySlabBlock(BlockConvertible block) { super(block); }
	public RedSandySlabBlock(Block block) { super(block); }
	public RedSandySlabBlock(Block block, Settings settings) { super(block, settings); }
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new RedSandyBlockEntity(pos, state); }
}
