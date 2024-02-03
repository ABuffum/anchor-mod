package fun.wich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;

import java.util.Random;

public class GrowingBerryLeavesBlock extends BerryLeavesBlock {
	public GrowingBerryLeavesBlock(ItemConvertible berry, Block block) { super(berry, block); }
	public GrowingBerryLeavesBlock(ItemConvertible berry, Settings settings) { super(berry, settings); }
	@Override
	public boolean hasRandomTicks(BlockState state) { return !state.get(Properties.BERRIES); }
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		boolean berries = state.get(Properties.BERRIES);
		if (!berries && random.nextInt(10) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
			world.setBlockState(pos, state.with(Properties.BERRIES, true), NOTIFY_LISTENERS);
			world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
		}
	}
}
