package fun.wich.mixins.block;

import com.google.common.collect.ImmutableList;
import fun.wich.ModBase;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin extends Block implements FluidDrainable {
	@Shadow @Final protected FlowableFluid fluid;
	@Shadow @Final public static ImmutableList<Direction> field_34006;
	@Shadow protected abstract void playExtinguishSound(WorldAccess world, BlockPos pos);

	public FluidBlockMixin(Settings settings) { super(settings); }

	@Inject(method="receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
	private void CreateNewLavaBlockGenerators(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (this.fluid.isIn(FluidTags.LAVA)) {
			BlockState up = world.getBlockState(pos.up());
			BlockState down = world.getBlockState(pos.down());
			Block makeBlock = null;
			for (Direction direction : field_34006) {
				BlockPos blockPos = pos.offset(direction.getOpposite());
				FluidState fluidState = world.getFluidState(blockPos);
				BlockState blockState = world.getBlockState(blockPos);
				if (fluidState.isIn(FluidTags.WATER)) {
					Fluid fluid = fluidState.getFluid();
					boolean still = world.getFluidState(pos).isStill();
					if (still) continue;
					else if (down.isOf(Blocks.QUARTZ_BLOCK)) makeBlock = up.isOf(Blocks.DIORITE) ? Blocks.GRANITE : Blocks.DIORITE;
					else if (up.isOf(ModBase.SCULK.asBlock())) makeBlock = ModBase.COBBLED_SCULK_STONE.asBlock();
					else continue;
				}
				else if (down.isOf(Blocks.SMOOTH_BASALT) && blockState.isOf(Blocks.AMETHYST_BLOCK)) makeBlock = Blocks.CALCITE;
				else if (blockState.isOf(Blocks.BLUE_ICE)) {
					if (down.isOf(Blocks.OBSIDIAN)) makeBlock = Blocks.DEEPSLATE;
					else if (down.isOf(Blocks.DEEPSLATE)) makeBlock = ModBase.SHALE.asBlock();
					else if (down.isOf(Blocks.SOUL_SAND)) makeBlock = Blocks.BLACKSTONE;
					else if (down.isOf(Blocks.MAGMA_BLOCK)) makeBlock = Blocks.NETHERRACK;
					else if (down.isOf(Blocks.PURPUR_PILLAR)) makeBlock = ModBase.END_SHALE.asBlock();
					else if (down.isOf(ModBase.END_SHALE.asBlock())) makeBlock = Blocks.END_STONE;
				}
				//Generate Block
				if (makeBlock != null) {
					world.setBlockState(pos, makeBlock.getDefaultState());
					this.playExtinguishSound(world, pos);
					cir.setReturnValue(false);
				}
			}
		}
	}
}
