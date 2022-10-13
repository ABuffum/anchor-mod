package haven.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class SugarBlock extends FallingBlock {
	public SugarBlock(Settings settings) { super(settings); }
	public int getColor(BlockState state, BlockView world, BlockPos pos) { return 0xFFFFFF; }
}
