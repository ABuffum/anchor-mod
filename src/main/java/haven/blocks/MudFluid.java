package haven.blocks;

import haven.HavenMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class MudFluid extends FlowableFluid {

	@Override
	public boolean matchesType(Fluid fluid) { return fluid == getStill() || fluid == getFlowing(); }
	@Override
	public Fluid getFlowing() { return HavenMod.FLOWING_MUD_FLUID; }
	@Override
	public Fluid getStill() { return HavenMod.STILL_MUD_FLUID; }
	@Override
	public Item getBucketItem() { return HavenMod.MUD_BUCKET; }
	@Override
	protected boolean isInfinite() { return false; }
	@Override
	protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
		Block.dropStacks(state, world, pos, blockEntity);
	}
	@Override
	protected int getFlowSpeed(WorldView worldIn) { return 4; }
	@Override
	protected int getLevelDecreasePerBlock(WorldView worldIn) { return 1; }

	@Override
	public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
		return direction == Direction.DOWN && !(fluid instanceof MudFluid);
	}

	@Override
	public int getTickRate(WorldView p_205569_1_) {
		return 20;
	}

	@Override
	protected float getBlastResistance() {
		return 100.0F;
	}

	@Override
	protected BlockState toBlockState(FluidState state) {
		return HavenMod.MUD_FLUID_BLOCK.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
	}

	@Override
	public boolean isStill(FluidState state) { return false; }

	@Override
	public int getLevel(FluidState state) { return state.get(LEVEL); }

	private void playExtinguishEvent(WorldAccess world, BlockPos pos) {
		world.syncWorldEvent(1501, pos, 0);
	}

	@Override
	protected void flow(WorldAccess worldIn, BlockPos pos, BlockState blockStateIn, Direction direction, FluidState fluidStateIn) {
		if (this.getFlowing() instanceof MudFluid) {
			boolean flag = false;
			for (Direction dir : Direction.values()) {
				if (worldIn.getFluidState(pos.offset(dir)).isIn(FluidTags.LAVA)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState(), 3);
				this.playExtinguishEvent(worldIn, pos);
				return;
			}
		}
		super.flow(worldIn, pos, blockStateIn, direction, fluidStateIn);
	}


	public static class Flowing extends MudFluid {
		@Override
		protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) { super.appendProperties(builder); builder.add(LEVEL); }
		public int getLevel(FluidState state) { return state.get(LEVEL); }
		public boolean isStill(FluidState state) { return false; }
	}

	public static class Still extends MudFluid {
		public int getLevel(FluidState state) { return 8; }
		public boolean isStill(FluidState state) { return true; }
	}

}
