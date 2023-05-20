package fun.mousewich.block.plushie;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.data.client.model.Model;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public abstract class PlushieBlock extends HorizontalFacingBlock implements Waterloggable {
	public PlushieBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false));
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) { stateManager.add(FACING, Properties.WATERLOGGED); }
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState()
				.with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER)
				.with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	public abstract Model getModel();
}
