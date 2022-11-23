package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;

public class DeprecatedFenceBlock extends DeprecatedBlock {
	public DeprecatedFenceBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(FenceBlock.NORTH, false).with(FenceBlock.EAST, false).with(FenceBlock.SOUTH, false).with(FenceBlock.WEST, false).with(FenceBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FenceBlock.NORTH, FenceBlock.EAST, FenceBlock.WEST, FenceBlock.SOUTH, FenceBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(FenceBlock.NORTH, state.get(FenceBlock.NORTH)).with(FenceBlock.EAST, state.get(FenceBlock.EAST)).with(FenceBlock.SOUTH, state.get(FenceBlock.SOUTH)).with(FenceBlock.WEST, state.get(FenceBlock.WEST)).with(FenceBlock.WATERLOGGED, state.get(FenceBlock.WATERLOGGED));
	}
}
