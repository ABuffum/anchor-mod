package fun.mousewich.mixins.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {
	@Inject(method="setToDirt", at = @At("TAIL"))
	private static void InjectBlockChangeEvent(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos);
	}
}
