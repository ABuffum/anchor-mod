package fun.mousewich.block.decoration;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DecorativeVineBlock extends Block {
	public static final BooleanProperty UP = ConnectingBlock.UP;
	public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
	public static final BooleanProperty EAST = ConnectingBlock.EAST;
	public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
	public static final BooleanProperty WEST = ConnectingBlock.WEST;
	public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> entry.getKey() != Direction.DOWN).collect(Util.toMap());
	private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
	private final Map<BlockState, VoxelShape> shapesByState;

	public DecorativeVineBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(UP, false).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false));
		this.shapesByState = ImmutableMap.copyOf(this.stateManager.getStates().stream().collect(Collectors.toMap(Function.identity(), DecorativeVineBlock::getShapeForState)));
	}

	private static VoxelShape getShapeForState(BlockState state) {
		VoxelShape voxelShape = VoxelShapes.empty();
		if (state.get(UP)) voxelShape = UP_SHAPE;
		if (state.get(NORTH)) voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
		if (state.get(SOUTH)) voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
		if (state.get(EAST)) voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
		if (state.get(WEST)) voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
		return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return this.shapesByState.get(state); }

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) { return true; }

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return this.hasAdjacentBlocks(this.getPlacementShape(state, world, pos));
	}

	private boolean hasAdjacentBlocks(BlockState state) { return this.getAdjacentBlockCount(state) > 0; }

	private int getAdjacentBlockCount(BlockState state) {
		int i = 0;
		for (BooleanProperty booleanProperty : FACING_PROPERTIES.values()) {
			if (!state.get(booleanProperty)) continue;
			++i;
		}
		return i;
	}

	private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
		if (side == Direction.DOWN) return false;
		BlockPos blockPos = pos.offset(side);
		if (VineBlock.shouldConnectTo(world, blockPos, side)) return true;
		if (side.getAxis() != Direction.Axis.Y) {
			BooleanProperty booleanProperty = FACING_PROPERTIES.get(side);
			BlockState blockState = world.getBlockState(pos.up());
			return blockState.isOf(this) && blockState.get(booleanProperty);
		}
		return false;
	}

	private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		if (state.get(UP)) state = state.with(UP, VineBlock.shouldConnectTo(world, blockPos, Direction.DOWN));
		AbstractBlockState blockState = null;
		for (Direction direction : Direction.Type.HORIZONTAL) {
			BooleanProperty booleanProperty = VineBlock.getFacingProperty(direction);
			if (!state.get(booleanProperty)) continue;
			boolean bl = this.shouldHaveSide(world, pos, direction);
			if (!bl) {
				if (blockState == null) blockState = world.getBlockState(blockPos);
				bl = blockState.isOf(this) && blockState.get(booleanProperty);
			}
			state = state.with(booleanProperty, bl);
		}
		return state;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == Direction.DOWN) return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		BlockState blockState = this.getPlacementShape(state, world, pos);
		if (!this.hasAdjacentBlocks(blockState)) return Blocks.AIR.getDefaultState();
		return blockState;
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
		if (blockState.isOf(this) || blockState.isOf(Blocks.VINE)) {
			return this.getAdjacentBlockCount(blockState) < FACING_PROPERTIES.size();
		}
		return super.canReplace(state, context);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
		boolean bl = blockState.isOf(this) || blockState.isOf(Blocks.VINE);
		BlockState blockState2 = bl ? blockState : this.getDefaultState();
		for (Direction direction : ctx.getPlacementDirections()) {
			if (direction == Direction.DOWN) continue;
			BooleanProperty booleanProperty = VineBlock.getFacingProperty(direction);
			boolean bl2 = bl && blockState.get(booleanProperty);
			if (bl2 || !this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), direction)) continue;
			return blockState2.with(booleanProperty, true);
		}
		return bl ? blockState2 : null;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(UP, NORTH, EAST, SOUTH, WEST); }

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		switch (rotation) {
			case CLOCKWISE_180: {
				return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
			}
			case COUNTERCLOCKWISE_90: {
				return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
			}
			case CLOCKWISE_90: {
				return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
			}
		}
		return state;
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		if (mirror == BlockMirror.LEFT_RIGHT) return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
		else if (mirror == BlockMirror.FRONT_BACK) return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
		return super.mirror(state, mirror);
	}
}