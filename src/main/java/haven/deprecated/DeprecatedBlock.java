package haven.deprecated;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
public class DeprecatedBlock extends Block {
	public final Block replacement;
	public DeprecatedBlock(Block replacement) {
		super(Settings.copy(replacement).ticksRandomly());
		this.replacement = replacement;
	}
	@Override
	public boolean hasRandomTicks(BlockState state) { return true; }
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		world.setBlockState(pos, getReplacementState(world.getBlockState(pos)), NOTIFY_ALL);
	}
	public BlockState getReplacementState(BlockState state) { return replacement.getDefaultState(); }
}
