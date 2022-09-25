package haven.blocks;

import haven.blocks.basic.HavenPaneBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class TintedGlassPaneBlock extends HavenPaneBlock {
	public TintedGlassPaneBlock(Settings settings) {
		super(settings);
	}

	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return world.getMaxLightLevel();
	}

	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
	}
}
