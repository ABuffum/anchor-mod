package fun.mousewich.block;

import fun.mousewich.ModBase;
import fun.mousewich.block.echo.EchoBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class BuddingEchoBlock extends EchoBlock {
	public static final int GROW_CHANCE = 5;
	private static final Direction[] DIRECTIONS = Direction.values();

	public BuddingEchoBlock(AbstractBlock.Settings settings) { super(settings); }

	public PistonBehavior getPistonBehavior(BlockState state) { return PistonBehavior.DESTROY; }

	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(GROW_CHANCE) == 0) {
			Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			Block block = null;
			if (canGrowIn(blockState)) block = ModBase.SMALL_ECHO_BUD.asBlock();
			else if (blockState.isOf(ModBase.SMALL_ECHO_BUD.asBlock()) && blockState.get(Properties.FACING) == direction) {
				block = ModBase.MEDIUM_ECHO_BUD.asBlock();
			}
			else if (blockState.isOf(ModBase.MEDIUM_ECHO_BUD.asBlock()) && blockState.get(Properties.FACING) == direction) {
				block = ModBase.LARGE_ECHO_BUD.asBlock();
			}
			else if (blockState.isOf(ModBase.LARGE_ECHO_BUD.asBlock()) && blockState.get(Properties.FACING) == direction) {
				block = ModBase.ECHO_CLUSTER.asBlock();
			}
			if (block != null) {
				BlockState blockState2 = block.getDefaultState().with(AmethystClusterBlock.FACING, direction).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
				world.setBlockState(blockPos, blockState2);
			}
		}
	}

	public static boolean canGrowIn(BlockState state) {
		return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
	}
}
