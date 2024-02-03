package fun.wich.block.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MudFluidBlock extends FluidBlock {
	public MudFluidBlock(FlowableFluid fluid, Settings settings) { super(fluid, settings); }
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return true; }
}
