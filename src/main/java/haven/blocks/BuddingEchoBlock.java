package haven.blocks;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
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
			if (canGrowIn(blockState)) block = HavenMod.SMALL_ECHO_BUD.BLOCK;
			else if (blockState.isOf(HavenMod.SMALL_ECHO_BUD.BLOCK) && blockState.get(AmethystClusterBlock.FACING) == direction) {
				block = HavenMod.MEDIUM_ECHO_BUD.BLOCK;
			}
			else if (blockState.isOf(HavenMod.MEDIUM_ECHO_BUD.BLOCK) && blockState.get(AmethystClusterBlock.FACING) == direction) {
				block = HavenMod.LARGE_ECHO_BUD.BLOCK;
			}
			else if (blockState.isOf(HavenMod.LARGE_ECHO_BUD.BLOCK) && blockState.get(AmethystClusterBlock.FACING) == direction) {
				block = HavenMod.ECHO_CLUSTER.BLOCK;
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

