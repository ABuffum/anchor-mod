package fun.mousewich.mixins.block;

import net.minecraft.block.AbstractCauldronBlock;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractCauldronBlock.class)
public interface AbstractCauldronBlockInvoker {
	@Invoker("canBeFilledByDripstone")
	boolean canBeFilledByDripstone(Fluid fluid);
	@Invoker("fillFromDripstone")
	void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid);
}
