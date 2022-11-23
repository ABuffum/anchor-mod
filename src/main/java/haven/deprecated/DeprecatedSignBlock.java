package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;

public class DeprecatedSignBlock extends DeprecatedBlock {
	public DeprecatedSignBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(SignBlock.ROTATION, 0).with(SignBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SignBlock.ROTATION, SignBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(SignBlock.ROTATION, state.get(SignBlock.ROTATION)).with(SignBlock.WATERLOGGED, state.get(SignBlock.WATERLOGGED));
	}
}