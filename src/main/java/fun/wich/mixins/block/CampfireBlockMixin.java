package fun.wich.mixins.block;

import fun.wich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
	@Inject(method="doesBlockCauseSignalFire", at = @At("HEAD"), cancellable = true)
	private void doesBlockCauseSignalFire(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isOf(ModBase.SUGAR_CANE_BALE.asBlock())) cir.setReturnValue(true);
	}
}
