package fun.wich.block.glass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class TintedGlassTrapdoorBlock extends AbstractGlassTrapdoorBlock {
	public TintedGlassTrapdoorBlock(Block block, Block slab) { super(block, slab); }
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return false; }
	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) { return world.getMaxLightLevel(); }
}
