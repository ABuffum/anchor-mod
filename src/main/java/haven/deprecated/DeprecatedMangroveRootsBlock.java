package haven.deprecated;
import haven.blocks.MangroveRootsBlock;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;

public class DeprecatedMangroveRootsBlock extends DeprecatedBlock {
	public DeprecatedMangroveRootsBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(MangroveRootsBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(MangroveRootsBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(MangroveRootsBlock.WATERLOGGED, state.get(MangroveRootsBlock.WATERLOGGED));
	}
}
