package fun.wich.mixins.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TurtleEggBlock.class)
public interface TurtleEggBlockInvoker {
	@Invoker("breakEgg")
	void InvokeBreakEgg(World world, BlockPos pos, BlockState state);
	@Invoker("tryBreakEgg")
	void InvokeTryBreakEgg(World world, BlockState state, BlockPos pos, Entity entity, int inverseChance);
}
