package fun.wich.mixins.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin extends Block {
	public AbstractCauldronBlockMixin(Settings settings) { super(settings); }

	@Inject(method="scheduledTick", at = @At("HEAD"), cancellable = true)
	public void ScheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
		BlockPos blockPos = PointedDripstoneBlock.getDripPos(world, pos);
		if (!(blockPos == null)) {
			Fluid fluid = PointedDripstoneBlock.getDripFluid(world, blockPos);
			AbstractCauldronBlock acb = (AbstractCauldronBlock)(Object)this;
			AbstractCauldronBlockInvoker acbi = (AbstractCauldronBlockInvoker)acb;
			if (fluid != Fluids.EMPTY && acbi.canBeFilledByDripstone(fluid)) {
				acbi.fillFromDripstone(state, world, pos, fluid);
			}
		}
		ci.cancel();
	}
}
