package haven.deprecated;

import haven.blocks.sculk.SculkVeinBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.state.StateManager;

public class DeprecatedSculkVeinBlock extends DeprecatedBlock {
	public DeprecatedSculkVeinBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(ConnectingBlock.NORTH, false).with(ConnectingBlock.EAST, false).with(ConnectingBlock.SOUTH, false).with(ConnectingBlock.WEST, false).with(ConnectingBlock.UP, false).with(ConnectingBlock.DOWN, false).with(SculkVeinBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ConnectingBlock.NORTH, ConnectingBlock.EAST, ConnectingBlock.SOUTH, ConnectingBlock.WEST, ConnectingBlock.UP, ConnectingBlock.DOWN, SculkVeinBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(ConnectingBlock.NORTH, state.get(ConnectingBlock.NORTH)).with(ConnectingBlock.EAST, state.get(ConnectingBlock.EAST)).with(ConnectingBlock.SOUTH, state.get(ConnectingBlock.SOUTH)).with(ConnectingBlock.WEST, state.get(ConnectingBlock.WEST)).with(ConnectingBlock.UP, state.get(ConnectingBlock.UP)).with(ConnectingBlock.DOWN, state.get(ConnectingBlock.DOWN)).with(SculkVeinBlock.WATERLOGGED, state.get(SculkVeinBlock.WATERLOGGED));
	}
}
