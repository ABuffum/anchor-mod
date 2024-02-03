package fun.wich.mixins.block;

import fun.wich.gen.data.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonHandler.class)
public abstract class PistonHandlerMixin {
	@Inject(method="isBlockSticky", at=@At("HEAD"), cancellable=true)
	private static void IsBlockSticky(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(ModBlockTags.STICKY)) cir.setReturnValue(true);
	}
	@Inject(method="isAdjacentBlockStuck", at=@At("HEAD"), cancellable=true)
	private static void IsAdjacentBlockStuck(BlockState state, BlockState adjacentState, CallbackInfoReturnable<Boolean> cir) {
		if (state.isIn(ModBlockTags.STICKY) && adjacentState.isIn(ModBlockTags.STICKY)
				&& state.getBlock() != adjacentState.getBlock()) cir.setReturnValue(false);
	}
}
