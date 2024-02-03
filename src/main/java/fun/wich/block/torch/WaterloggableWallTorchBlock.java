package fun.wich.block.torch;

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
import net.minecraft.world.WorldView;

import java.util.Random;

public class WaterloggableWallTorchBlock extends LightableWallTorchBlock implements Waterloggable {
	public WaterloggableWallTorchBlock(Settings settings, ParticleEffect particleEffect) {
		super(settings, particleEffect);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(Properties.LIT, true).with(Properties.WATERLOGGED, false));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, Properties.LIT, Properties.WATERLOGGED);
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
		boolean flag = fluidstate.getFluid() == Fluids.WATER || fluidstate.getFluid() == Fluids.FLOWING_WATER;
		return this.getDefaultState().with(Properties.WATERLOGGED, flag).with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(Properties.WATERLOGGED)) world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) return Blocks.AIR.getDefaultState();
		return state;
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		BlockState blockState = world.getBlockState(blockPos);
		return blockState.isSideSolidFullSquare(world, blockPos, direction);
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (state.get(Properties.LIT)) {
			double d = pos.getX() + 0.5D;
			double e = pos.getY() + 0.7D;
			double f = pos.getZ() + 0.5D;
			Direction direction = state.get(FACING).getOpposite();
			world.addParticle(state.get(Properties.WATERLOGGED) ? ParticleTypes.UNDERWATER : ParticleTypes.SMOKE, d + 0.27D * direction.getOffsetX(), e + 0.22D, f + 0.27D * direction.getOffsetZ(), 0.0D, 0.0D, 0.0D);
			world.addParticle(this.particle, d + 0.27D * (double) direction.getOffsetX(), e + 0.22D, f + 0.27D * direction.getOffsetZ(), 0.0D, 0.0D, 0.0D);
		}
	}
}
