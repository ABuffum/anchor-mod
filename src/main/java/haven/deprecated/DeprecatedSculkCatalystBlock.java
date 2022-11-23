package haven.deprecated;
import haven.blocks.sculk.SculkCatalystBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

public class DeprecatedSculkCatalystBlock extends DeprecatedBlock {
	public DeprecatedSculkCatalystBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(SculkCatalystBlock.BLOOM, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SculkCatalystBlock.BLOOM);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(SculkCatalystBlock.BLOOM, state.get(SculkCatalystBlock.BLOOM));
	}
}
