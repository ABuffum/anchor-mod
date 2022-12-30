package haven.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class TintedGlassSlabBlock extends GlassSlabBlock {
	public TintedGlassSlabBlock(Block block) { super(Settings.copy(block)); }
	public TintedGlassSlabBlock(Settings settings) { super(settings); }
	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) { return world.getMaxLightLevel(); }
	@Override
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
	}
}
