package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class DeprecatedDoorBlock extends DeprecatedBlock {
	public DeprecatedDoorBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(DoorBlock.FACING, Direction.NORTH).with(DoorBlock.OPEN, false).with(DoorBlock.HINGE, DoorHinge.LEFT).with(DoorBlock.POWERED, false).with(DoorBlock.HALF, DoubleBlockHalf.LOWER));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(DoorBlock.HALF, DoorBlock.FACING, DoorBlock.OPEN, DoorBlock.HINGE, DoorBlock.POWERED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(DoorBlock.FACING, state.get(DoorBlock.FACING)).with(DoorBlock.HINGE, state.get(DoorBlock.HINGE)).with(DoorBlock.POWERED, state.get(DoorBlock.POWERED)).with(DoorBlock.HALF, state.get(DoorBlock.HALF));
	}
}
