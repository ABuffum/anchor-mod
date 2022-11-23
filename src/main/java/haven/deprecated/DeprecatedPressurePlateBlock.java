package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;

public class DeprecatedPressurePlateBlock extends DeprecatedBlock {
	public DeprecatedPressurePlateBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(PressurePlateBlock.POWERED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(PressurePlateBlock.POWERED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(PressurePlateBlock.POWERED, state.get(PressurePlateBlock.POWERED));
	}
}
