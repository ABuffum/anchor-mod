package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;

public class DeprecatedFenceGateBlock extends DeprecatedBlock {
	public DeprecatedFenceGateBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(FenceGateBlock.OPEN, false).with(FenceGateBlock.POWERED, false).with(FenceGateBlock.IN_WALL, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FenceGateBlock.FACING, FenceGateBlock.OPEN, FenceGateBlock.POWERED, FenceGateBlock.IN_WALL);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(FenceGateBlock.OPEN, state.get(FenceGateBlock.OPEN)).with(FenceGateBlock.POWERED, state.get(FenceGateBlock.POWERED)).with(FenceGateBlock.IN_WALL, state.get(FenceGateBlock.IN_WALL));
	}
}
