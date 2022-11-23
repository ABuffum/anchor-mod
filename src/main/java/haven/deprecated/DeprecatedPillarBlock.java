package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

public class DeprecatedPillarBlock extends DeprecatedBlock {
	public DeprecatedPillarBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.getDefaultState().with(PillarBlock.AXIS, Direction.Axis.Y));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(PillarBlock.AXIS);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS));
	}
}
