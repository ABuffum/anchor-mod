package fun.wich.block.oxidizable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class WaterloggableOxidizableTorchBlock extends OxidizableTorchBlock implements Waterloggable {
	public WaterloggableOxidizableTorchBlock(OxidizationLevel oxidizationLevel, Settings settings, ParticleEffect particle) {
		super(oxidizationLevel, settings, particle);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.LIT, true).with(Properties.WATERLOGGED, false));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.LIT, Properties.WATERLOGGED);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean flag = fluidstate.getFluid() == Fluids.WATER || fluidstate.getFluid() == Fluids.FLOWING_WATER;
		return this.getDefaultState().with(Properties.WATERLOGGED, flag);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return state;
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(Properties.LIT)) {
			double d = pos.getX() + 0.5;
			double e = pos.getY() + 0.7;
			double f = pos.getZ() + 0.5;
			world.addParticle(state.get(Properties.WATERLOGGED) ? ParticleTypes.UNDERWATER : ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
			world.addParticle(this.particle, d, e, f, 0.0, 0.0, 0.0);
		}
	}
}
