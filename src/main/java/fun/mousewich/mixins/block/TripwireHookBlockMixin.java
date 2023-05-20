package fun.mousewich.mixins.block;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TripwireBlock;
import net.minecraft.block.TripwireHookBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TripwireHookBlock.class)
public abstract class TripwireHookBlockMixin {
	@Inject(method="playSound", at = @At("HEAD"))
	private void PlaySound(World world, BlockPos pos, boolean attached, boolean on, boolean detached, boolean off, CallbackInfo ci) {
		if (on && !off) world.emitGameEvent(null, ModGameEvent.BLOCK_ACTIVATE, pos);
		else if (!on && off) world.emitGameEvent(null, ModGameEvent.BLOCK_DEACTIVATE, pos);
	}
}
