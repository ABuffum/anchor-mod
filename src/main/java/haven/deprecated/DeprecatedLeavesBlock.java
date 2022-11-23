package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;

public class DeprecatedLeavesBlock extends DeprecatedBlock {
	public DeprecatedLeavesBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(LeavesBlock.DISTANCE, 7).with(LeavesBlock.PERSISTENT, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LeavesBlock.DISTANCE, LeavesBlock.PERSISTENT);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(LeavesBlock.DISTANCE, state.get(LeavesBlock.DISTANCE)).with(LeavesBlock.PERSISTENT, state.get(LeavesBlock.PERSISTENT));
	}
}
