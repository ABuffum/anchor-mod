package fun.mousewich.mixins.block;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeehiveBlock.class)
public interface BeehiveBlockInvoker {
	@Invoker("hasBees")
	boolean InvokeHasBees(World world, BlockPos pos);
	@Invoker("angerNearbyBees")
	void InvokeAngerNearbyBees(World world, BlockPos pos);
}
