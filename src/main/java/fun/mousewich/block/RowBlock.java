package fun.mousewich.block;

import fun.mousewich.gen.data.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class RowBlock extends HorizontalConnectingBlock {
	public RowBlock(Settings settings) {
		super(2.0F, 2.0F, 16.0F, 16.0F, 16.0F, settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(WATERLOGGED, false));
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockView blockView = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
		BlockPos blockPos2 = blockPos.north();
		BlockPos blockPos3 = blockPos.south();
		BlockPos blockPos4 = blockPos.west();
		BlockPos blockPos5 = blockPos.east();
		BlockState blockState = blockView.getBlockState(blockPos2);
		BlockState blockState2 = blockView.getBlockState(blockPos3);
		BlockState blockState3 = blockView.getBlockState(blockPos4);
		BlockState blockState4 = blockView.getBlockState(blockPos5);
		boolean bl1 = this.connectsTo(blockState, blockState.isSideSolidFullSquare(blockView, blockPos2, Direction.SOUTH), Direction.SOUTH);
		boolean bl2 = this.connectsTo(blockState2, blockState2.isSideSolidFullSquare(blockView, blockPos3, Direction.NORTH), Direction.NORTH);
		boolean bl3 = this.connectsTo(blockState3, blockState3.isSideSolidFullSquare(blockView, blockPos4, Direction.EAST), Direction.EAST);
		boolean bl4 = this.connectsTo(blockState4, blockState4.isSideSolidFullSquare(blockView, blockPos5, Direction.WEST), Direction.WEST);
		return this.getDefaultState().with(NORTH, bl1).with(SOUTH, bl2).with(WEST, bl3).with(EAST, bl4).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		Direction opposite = direction.getOpposite();
		return direction.getAxis().isHorizontal() ? state.with(FACING_PROPERTIES.get(direction), this.connectsTo(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, opposite), opposite)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return VoxelShapes.empty(); }
	public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
		if (stateFrom.isOf(this)) {
			if (!direction.getAxis().isHorizontal()) return true;
			if (state.get(FACING_PROPERTIES.get(direction)) && stateFrom.get(FACING_PROPERTIES.get(direction.getOpposite()))) return true;
		}
		return super.isSideInvisible(state, stateFrom, direction);
	}

	public final boolean connectsTo(BlockState state, boolean sideSolidFullSquare, Direction side) {
		Block block = state.getBlock();
		boolean bl = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, side);
		return !cannotConnect(state) && sideSolidFullSquare || state.getBlock() instanceof PaneBlock || state.isIn(BlockTags.WALLS) || state.isIn(ModBlockTags.ROWS) || bl;
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED); }
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
}
