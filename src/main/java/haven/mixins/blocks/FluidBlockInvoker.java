package haven.mixins.blocks;

import net.minecraft.block.FluidBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FluidBlock.class)
public interface FluidBlockInvoker {
	@Invoker("playExtinguishSound")
	public void InvokePlayExtinguishSound(WorldAccess world, BlockPos pos);
}
