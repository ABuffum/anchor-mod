package haven.mixins.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import haven.HavenMod;
import haven.blocks.BloodFluid;
import haven.blocks.MudFluid;
import net.minecraft.block.*;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin extends Block implements FluidDrainable {
	public FluidBlockMixin(Settings settings) { super(settings); }

	@Inject(method="receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
	private void ReceiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		FluidBlock fb = (FluidBlock)(Object)this;
		FluidBlockAccessor fba = (FluidBlockAccessor)fb;
		FluidBlockInvoker fbi = (FluidBlockInvoker)fb;
		if (fba.GetFluid().isIn(FluidTags.LAVA)) {
			UnmodifiableIterator var5 = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST).iterator();
			while(var5.hasNext()) {
				Direction direction = (Direction)var5.next();
				BlockPos blockPos = pos.offset(direction.getOpposite());
				if (world.getFluidState(blockPos).getFluid() instanceof BloodFluid) {
					Block block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : HavenMod.DRIED_BLOOD_BLOCK.BLOCK;
					world.setBlockState(pos, block.getDefaultState());
					fbi.InvokePlayExtinguishSound(world, pos);
					cir.setReturnValue(false);
				}
				else if (world.getFluidState(blockPos).getFluid() instanceof MudFluid) {
					Block block = world.getFluidState(pos).isStill() ? Blocks.COARSE_DIRT : Blocks.DIRT;
					world.setBlockState(pos, block.getDefaultState());
					fbi.InvokePlayExtinguishSound(world, pos);
					cir.setReturnValue(false);
				}
			}
		}
	}
}
