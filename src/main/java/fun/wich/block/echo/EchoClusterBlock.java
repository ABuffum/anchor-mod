package fun.wich.block.echo;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class EchoClusterBlock extends EchoBlock implements Waterloggable {
	protected final VoxelShape NORTH_SHAPE, SOUTH_SHAPE, EAST_SHAPE, WEST_SHAPE, UP_SHAPE, DOWN_SHAPE;

	public EchoClusterBlock(int height, int xzOffset, Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(Properties.WATERLOGGED, false).with(Properties.FACING, Direction.UP));
		this.UP_SHAPE = Block.createCuboidShape(xzOffset, 0.0D, xzOffset, 16 - xzOffset, height, 16 - xzOffset);
		this.DOWN_SHAPE = Block.createCuboidShape(xzOffset, 16 - height, xzOffset, 16 - xzOffset, 16.0D, 16 - xzOffset);
		this.NORTH_SHAPE = Block.createCuboidShape(xzOffset, xzOffset, 16 - height, 16 - xzOffset, 16 - xzOffset, 16.0D);
		this.SOUTH_SHAPE = Block.createCuboidShape(xzOffset, xzOffset, 0.0D, 16 - xzOffset, 16 - xzOffset, height);
		this.EAST_SHAPE = Block.createCuboidShape(0.0D, xzOffset, xzOffset, height, 16 - xzOffset, 16 - xzOffset);
		this.WEST_SHAPE = Block.createCuboidShape(16 - height, xzOffset, xzOffset, 16.0D, 16 - xzOffset, 16 - xzOffset);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = state.get(Properties.FACING);
		if (direction == Direction.NORTH) return this.NORTH_SHAPE;
		else if (direction == Direction.SOUTH) return this.SOUTH_SHAPE;
		else if (direction == Direction.EAST) return this.EAST_SHAPE;
		else if (direction == Direction.WEST) return this.WEST_SHAPE;
		else if (direction == Direction.DOWN) return this.DOWN_SHAPE;
		/*else if (direction == Direction.UP)*/ return this.UP_SHAPE;
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(Properties.FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return direction == state.get(Properties.FACING).getOpposite() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess worldAccess = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		return this.getDefaultState().with(Properties.WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER).with(Properties.FACING, ctx.getSide());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(Properties.FACING, rotation.rotate(state.get(Properties.FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(Properties.FACING)));
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.WATERLOGGED, Properties.FACING); }

	public PistonBehavior getPistonBehavior(BlockState state) { return PistonBehavior.DESTROY; }
}
