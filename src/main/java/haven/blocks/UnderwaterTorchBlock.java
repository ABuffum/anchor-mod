package haven.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

public class UnderwaterTorchBlock extends TorchBlock implements Waterloggable {

	public UnderwaterTorchBlock(AbstractBlock.Settings settings, ParticleEffect particle) {
		super(settings, particle);
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.WATERLOGGED, false));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(Properties.WATERLOGGED);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean flag = fluidstate.getFluid() == Fluids.WATER || fluidstate.getFluid() == Fluids.FLOWING_WATER;
		return this.getDefaultState().with(Properties.WATERLOGGED, flag);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) {
			world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
			return Blocks.AIR.getDefaultState();
		} else {
			return state;
		}
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		double d = (double)pos.getX() + 0.5D;
		double e = (double)pos.getY() + 0.7D;
		double f = (double)pos.getZ() + 0.5D;
		world.addParticle(state.get(Properties.WATERLOGGED) ? ParticleTypes.UNDERWATER : ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
		world.addParticle(this.particle, d, e, f, 0.0D, 0.0D, 0.0D);
	}
}
