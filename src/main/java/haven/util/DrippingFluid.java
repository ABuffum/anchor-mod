package haven.util;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;

public class DrippingFluid {
	public final BlockPos pos;
	public final Fluid fluid;
	public final BlockState sourceState;
	public DrippingFluid(BlockPos pos, Fluid fluid, BlockState sourceState) {
		this.pos = pos;
		this.fluid = fluid;
		this.sourceState = sourceState;
	}
}
