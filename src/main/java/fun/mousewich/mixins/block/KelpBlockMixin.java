package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KelpBlock.class)
public class KelpBlockMixin {
	@Inject(method="canAttachTo",at=@At("HEAD"),cancellable = true)
	protected void CanAttachTo(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isOf(ModBase.BLAZE_POWDER_BLOCK.asBlock()) || state.isOf(ModBase.COOLED_MAGMA_BLOCK.asBlock())) cir.setReturnValue(false);
	}
}
