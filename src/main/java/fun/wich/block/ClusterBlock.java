package fun.wich.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ClusterBlock extends Block implements Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final DirectionProperty FACING = Properties.FACING;
	protected final VoxelShape northShape;
	protected final VoxelShape southShape;
	protected final VoxelShape eastShape;
	protected final VoxelShape westShape;
	protected final VoxelShape upShape;
	protected final VoxelShape downShape;

	public ClusterBlock(int height, int xzOffset, Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
		this.upShape = Block.createCuboidShape(xzOffset, 0.0, xzOffset, 16 - xzOffset, height, 16 - xzOffset);
		this.downShape = Block.createCuboidShape(xzOffset, 16 - height, xzOffset, 16 - xzOffset, 16.0, 16 - xzOffset);
		this.northShape = Block.createCuboidShape(xzOffset, xzOffset, 16 - height, 16 - xzOffset, 16 - xzOffset, 16.0);
		this.southShape = Block.createCuboidShape(xzOffset, xzOffset, 0.0, 16 - xzOffset, 16 - xzOffset, height);
		this.eastShape = Block.createCuboidShape(0.0, xzOffset, xzOffset, height, 16 - xzOffset, 16 - xzOffset);
		this.westShape = Block.createCuboidShape(16 - height, xzOffset, xzOffset, 16.0, 16 - xzOffset, 16 - xzOffset);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = state.get(FACING);
		return switch (direction) {
			case NORTH -> this.northShape;
			case SOUTH -> this.southShape;
			case EAST -> this.eastShape;
			case WEST -> this.westShape;
			case DOWN -> this.downShape;
			case UP -> this.upShape;
		};
	}
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		if (direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		World worldAccess = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		return (this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER)).with(FACING, ctx.getSide());
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(WATERLOGGED, FACING); }
	@Override
	public PistonBehavior getPistonBehavior(BlockState state) { return PistonBehavior.DESTROY; }
}
