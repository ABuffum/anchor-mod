package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class DeprecatedTrapdoorBlock extends DeprecatedBlock {
	public DeprecatedTrapdoorBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(TrapdoorBlock.FACING, Direction.NORTH).with(TrapdoorBlock.OPEN, false).with(TrapdoorBlock.HALF, BlockHalf.BOTTOM).with(TrapdoorBlock.POWERED, false).with(TrapdoorBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(TrapdoorBlock.FACING, TrapdoorBlock.OPEN, TrapdoorBlock.HALF, TrapdoorBlock.POWERED, TrapdoorBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(TrapdoorBlock.FACING, state.get(TrapdoorBlock.FACING)).with(TrapdoorBlock.OPEN, state.get(TrapdoorBlock.OPEN)).with(TrapdoorBlock.HALF, state.get(TrapdoorBlock.HALF)).with(TrapdoorBlock.POWERED, state.get(TrapdoorBlock.POWERED)).with(TrapdoorBlock.WATERLOGGED, state.get(TrapdoorBlock.WATERLOGGED));
	}
}
