package haven.deprecated;
import haven.blocks.sculk.SculkShriekerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;

public class DeprecatedSculkShriekerBlock extends DeprecatedBlock {
	public DeprecatedSculkShriekerBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.stateManager.getDefaultState().with(SculkShriekerBlock.SHRIEKING, false).with(SculkShriekerBlock.WATERLOGGED, false).with(SculkShriekerBlock.CAN_SUMMON, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SculkShriekerBlock.SHRIEKING, SculkShriekerBlock.WATERLOGGED, SculkShriekerBlock.CAN_SUMMON);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(SculkShriekerBlock.SHRIEKING, state.get(SculkShriekerBlock.SHRIEKING)).with(SculkShriekerBlock.WATERLOGGED, state.get(SculkShriekerBlock.WATERLOGGED)).with(SculkShriekerBlock.CAN_SUMMON, state.get(SculkShriekerBlock.CAN_SUMMON));
	}
}
