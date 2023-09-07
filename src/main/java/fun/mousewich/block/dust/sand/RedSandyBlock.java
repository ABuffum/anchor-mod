package fun.mousewich.block.dust.sand;

import fun.mousewich.block.BlockConvertible;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RedSandyBlock extends SandyBlock {
	public RedSandyBlock(BlockConvertible block) { super(block); }
	public RedSandyBlock(Block block) { super(block); }
	public RedSandyBlock(Block block, Settings settings) { super(block, settings); }
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new RedSandyBlockEntity(pos, state); }
}
