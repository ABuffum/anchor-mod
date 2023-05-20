package fun.mousewich.mixins.entity.ai;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LandPathNodeMaker.class)
public class LandPathNodeMakerMixin {
	@Inject(method="inflictsFireDamage", at=@At("HEAD"), cancellable=true)
	private static void InflictsFireDamage(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (state.isOf(ModBase.BLAZE_POWDER_BLOCK.asBlock())) cir.setReturnValue(true);
	}
}
