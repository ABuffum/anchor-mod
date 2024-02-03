package fun.wich.mixins.block;

import fun.wich.ModBase;
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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaFluid.class)
public abstract class LavaFluidMixin extends FlowableFluid {
	@Shadow protected abstract void playExtinguishEvent(WorldAccess world, BlockPos pos);

	@Inject(method="flow", at = @At("HEAD"), cancellable = true)
	private void Flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState, CallbackInfo ci) {
		if (direction == Direction.DOWN) {
			FluidState fluidState2 = world.getFluidState(pos);
			if (this.isIn(FluidTags.LAVA)) {
				Block makeBlock = null;
				if (fluidState2.isIn(FluidTags.WATER)) {
					Fluid fluid = fluidState2.getFluid();
					for (Direction dir : FluidBlock.field_34006) {
						BlockState blockState = world.getBlockState(pos.offset(dir));
						if (blockState.isOf(Blocks.BLUE_ICE)) { makeBlock = Blocks.ANDESITE; break; }
						else if (blockState.isOf(Blocks.MAGMA_BLOCK)) { makeBlock = Blocks.TUFF; break; }
						else if (blockState.isOf(ModBase.SCULK.asBlock())) { makeBlock = ModBase.SCULK_STONE.asBlock(); break; }
					}
				}
				if (makeBlock != null) {
					if (state.getBlock() instanceof FluidBlock) world.setBlockState(pos, makeBlock.getDefaultState(), Block.NOTIFY_ALL);
					this.playExtinguishEvent(world, pos);
					ci.cancel();
				}
			}
		}
	}
}
