package haven.mixins.blocks;

import haven.ModBase;
import haven.blood.BloodFluid;
import haven.blocks.mud.MudFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin  extends FlowableFluid {
	@Inject(method="flow", at = @At("HEAD"), cancellable = true)
	private void Flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState, CallbackInfo ci) {
		if (direction == Direction.DOWN) {
			FluidState fluidState2 = world.getFluidState(pos);
			if (this.isIn(FluidTags.LAVA)) {
				Fluid fluid = fluidState2.getFluid();
				boolean set = false;
				Block block = null;
				if (fluid instanceof MudFluid) {
					set = true;
					block = Blocks.GRAVEL;
				}
				else if (fluid instanceof BloodFluid) {
					set = true;
					block = ModBase.BLOOD_BLOCK.getBlock();
				}
				if (set) {
					if (state.getBlock() instanceof FluidBlock) {
						world.setBlockState(pos, block.getDefaultState(), Block.NOTIFY_ALL);
					}
					world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
					ci.cancel();
				}
			}
		}
	}
}
