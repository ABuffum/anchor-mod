package haven.mixins.blocks;

import haven.events.HavenGameEvent;
import net.minecraft.block.TripwireHookBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TripwireHookBlock.class)
public abstract class TripwireHookBlockMixin {
	@Inject(method="playSound", at = @At("HEAD"))
	private void playSound(World world, BlockPos pos, boolean attached, boolean on, boolean detached, boolean off, CallbackInfo ci) {
		if (on && !off) world.emitGameEvent(null, HavenGameEvent.BLOCK_ACTIVATE, pos);
		else if (!on && off) world.emitGameEvent(null, HavenGameEvent.BLOCK_DEACTIVATE, pos);
	}
}
