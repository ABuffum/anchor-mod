package fun.wich.block;

import fun.wich.block.basic.ModLeavesBlock;
import fun.wich.particle.ModParticleTypes;
import fun.wich.util.ParticleUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class CherryLeavesBlock extends ModLeavesBlock {
	public static final int MAX_DISTANCE = 10;
	public static final IntProperty CHERRY_LEAVES_DISTANCE = IntProperty.of("cherry_leaves_distance", 1, MAX_DISTANCE);
	public CherryLeavesBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(CHERRY_LEAVES_DISTANCE, MAX_DISTANCE).with(PERSISTENT, false).with(Properties.WATERLOGGED, false));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(CHERRY_LEAVES_DISTANCE, PERSISTENT, Properties.WATERLOGGED, DISTANCE); }

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if (random.nextInt(15) != 0) return;
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		if (blockState.isOpaque() && blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) return;
		ParticleUtil.spawnParticle(world, pos, random, ModParticleTypes.CHERRY_LEAVES);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		return updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
	}
	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
	}
	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int i = MAX_DISTANCE;
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (i == 1) break;
		}
		return state.with(CHERRY_LEAVES_DISTANCE, i).with(DISTANCE, Math.min(7, i));
	}
	private static int getDistanceFromLog(BlockState state) {
		return state.isIn(BlockTags.LOGS) ? 0 : state.contains(CHERRY_LEAVES_DISTANCE) ? state.get(CHERRY_LEAVES_DISTANCE) : state.contains(DISTANCE) ? state.get(DISTANCE) : MAX_DISTANCE;
	}
	@Override
	public boolean hasRandomTicks(BlockState state) { return state.get(CHERRY_LEAVES_DISTANCE) >= MAX_DISTANCE && !state.get(PERSISTENT); }
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(CHERRY_LEAVES_DISTANCE) >= MAX_DISTANCE) {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}
	}
}
