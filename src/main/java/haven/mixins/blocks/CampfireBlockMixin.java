package haven.mixins.blocks;

import haven.HavenMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
	@Inject(method="doesBlockCauseSignalFire", at = @At("HEAD"), cancellable = true)
	private void doesBlockCauseSignalFire(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isOf(HavenMod.SUGAR_CANE_MATERIAL.getBale().BLOCK)) cir.setReturnValue(true);
	}
}
