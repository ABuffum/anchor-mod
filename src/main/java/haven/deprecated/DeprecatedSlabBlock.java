package haven.deprecated;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.state.StateManager;

public class DeprecatedSlabBlock extends DeprecatedBlock {
	public DeprecatedSlabBlock(Block replacement) {
		super(replacement);
		this.setDefaultState(this.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM).with(SlabBlock.WATERLOGGED, false));
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SlabBlock.TYPE, SlabBlock.WATERLOGGED);
	}
	public BlockState getReplacementState(BlockState state) {
		return replacement.getDefaultState().with(SlabBlock.TYPE, state.get(SlabBlock.TYPE)).with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED));
	}
}
