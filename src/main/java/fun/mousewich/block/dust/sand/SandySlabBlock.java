package fun.mousewich.block.dust.sand;

import fun.mousewich.block.BlockConvertible;
import fun.mousewich.block.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class SandySlabBlock extends SandyBlock implements Waterloggable {
	public static final EnumProperty<SlabType> TYPE = Properties.SLAB_TYPE;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
	protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

	public SandySlabBlock(BlockConvertible block) {
		super(block);
		this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
	}
	public SandySlabBlock(Block block) {
		super(block);
		this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
	}
	public SandySlabBlock(Block block, Settings settings) {
		super(block, settings);
		this.setDefaultState(this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, false));
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) { return state.get(TYPE) != SlabType.DOUBLE; }

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ModProperties.DUSTED, TYPE, WATERLOGGED);
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(TYPE)) {
			case DOUBLE -> VoxelShapes.fullCube();
			case TOP -> TOP_SHAPE;
			default -> BOTTOM_SHAPE;
		};
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		BlockState blockState = ctx.getWorld().getBlockState(blockPos);
		if (blockState.isOf(this)) return blockState.with(TYPE, SlabType.DOUBLE).with(WATERLOGGED, false);
		FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
		BlockState blockState2 = this.getDefaultState().with(TYPE, SlabType.BOTTOM).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
		Direction direction = ctx.getSide();
		if (direction == Direction.DOWN || direction != Direction.UP && ctx.getHitPos().y - (double)blockPos.getY() > 0.5) {
			return blockState2.with(TYPE, SlabType.TOP);
		}
		return blockState2;
	}
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		ItemStack itemStack = context.getStack();
		SlabType slabType = state.get(TYPE);
		if (slabType == SlabType.DOUBLE || !itemStack.isOf(this.asItem())) return false;
		if (context.canReplaceExisting()) {
			boolean bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5;
			Direction direction = context.getSide();
			if (slabType == SlabType.BOTTOM) return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
			return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
		}
		return true;
	}
	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) return Fluids.WATER.getStill(false);
		return super.getFluidState(state);
	}
	@Override
	public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
		if (state.get(TYPE) != SlabType.DOUBLE) return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
		return false;
	}
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		if (type == NavigationType.WATER) return world.getFluidState(pos).isIn(FluidTags.WATER);
		return false;
	}
	@Override
	public BlockState getBrushedState(BlockState state) { return super.getBrushedState(state).with(TYPE, state.get(TYPE)).with(WATERLOGGED, state.get(WATERLOGGED)); }
}
