package fun.mousewich.mixins;

import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public class FlowableFluidMixin {
	@Inject(method="canFill", at = @At("HEAD"), cancellable = true)
	private void CannotFillLadders(BlockView world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> cir) {
		if (state.getBlock() instanceof LadderBlock) cir.setReturnValue(false);
	}
}
