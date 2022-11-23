package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class DeprecatedWallSignBlock extends DeprecatedBlock {
	public DeprecatedWallSignBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(WallSignBlock.FACING, Direction.NORTH).with(WallSignBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WallSignBlock.FACING, WallSignBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(WallSignBlock.FACING, state.get(WallSignBlock.FACING)).with(WallSignBlock.WATERLOGGED, state.get(WallSignBlock.WATERLOGGED));
	}
}