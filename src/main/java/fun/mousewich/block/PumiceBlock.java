package fun.mousewich.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class PumiceBlock extends Block implements Waterloggable {
	public PumiceBlock(BlockConvertible block) { this(block.asBlock()); }
	public PumiceBlock(Block block) { this(Settings.copy(block)); }
	public PumiceBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(Properties.WATERLOGGED, false));
	}
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess worldAccess = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		return this.getDefaultState().with(Properties.WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
	}
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(Properties.WATERLOGGED); }
}
