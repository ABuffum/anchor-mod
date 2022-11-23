package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class DeprecatedStairsBlock extends DeprecatedBlock {
	public DeprecatedStairsBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(StairsBlock.FACING, Direction.NORTH).with(StairsBlock.HALF, BlockHalf.BOTTOM).with(StairsBlock.SHAPE, StairShape.STRAIGHT).with(StairsBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(StairsBlock.FACING, StairsBlock.HALF, StairsBlock.SHAPE, StairsBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(StairsBlock.FACING, state.get(StairsBlock.FACING)).with(StairsBlock.HALF, state.get(StairsBlock.HALF)).with(StairsBlock.SHAPE, state.get(StairsBlock.SHAPE)).with(StairsBlock.WATERLOGGED, state.get(StairsBlock.WATERLOGGED));
	}
}
