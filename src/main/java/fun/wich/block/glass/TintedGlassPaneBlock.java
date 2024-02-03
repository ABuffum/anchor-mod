package fun.wich.block.glass;

import fun.wich.block.basic.ModPaneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class TintedGlassPaneBlock extends ModPaneBlock {
	public TintedGlassPaneBlock(Settings settings) { super(settings); }

	public int getOpacity(BlockState state, BlockView world, BlockPos pos) { return world.getMaxLightLevel(); }

	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
	}
}
