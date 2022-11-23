package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.block.enums.WallShape;
import net.minecraft.state.StateManager;

public class DeprecatedWallBlock extends DeprecatedBlock {
	public DeprecatedWallBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(WallBlock.UP, true).with(WallBlock.NORTH_SHAPE, WallShape.NONE).with(WallBlock.EAST_SHAPE, WallShape.NONE).with(WallBlock.SOUTH_SHAPE, WallShape.NONE).with(WallBlock.WEST_SHAPE, WallShape.NONE).with(WallBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WallBlock.UP, WallBlock.NORTH_SHAPE, WallBlock.EAST_SHAPE, WallBlock.WEST_SHAPE, WallBlock.SOUTH_SHAPE, WallBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(WallBlock.UP, state.get(WallBlock.UP)).with(WallBlock.NORTH_SHAPE, state.get(WallBlock.NORTH_SHAPE)).with(WallBlock.EAST_SHAPE, state.get(WallBlock.EAST_SHAPE)).with(WallBlock.SOUTH_SHAPE, state.get(WallBlock.SOUTH_SHAPE)).with(WallBlock.WEST_SHAPE, state.get(WallBlock.WEST_SHAPE)).with(WallBlock.WATERLOGGED, state.get(WallBlock.WATERLOGGED));
	}
}
