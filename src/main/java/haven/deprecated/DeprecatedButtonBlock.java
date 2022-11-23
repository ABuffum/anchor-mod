package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class DeprecatedButtonBlock extends DeprecatedBlock {
	public DeprecatedButtonBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(AbstractButtonBlock.FACING, Direction.NORTH).with(AbstractButtonBlock.POWERED, false).with(AbstractButtonBlock.FACE, WallMountLocation.WALL));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AbstractButtonBlock.FACING, AbstractButtonBlock.POWERED, AbstractButtonBlock.FACE);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(AbstractButtonBlock.FACING, state.get(AbstractButtonBlock.FACING)).with(AbstractButtonBlock.POWERED, state.get(AbstractButtonBlock.POWERED)).with(AbstractButtonBlock.FACE, state.get(AbstractButtonBlock.FACE));
	}
}
