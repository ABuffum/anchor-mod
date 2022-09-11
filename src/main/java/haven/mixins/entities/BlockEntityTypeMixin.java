package haven.mixins.entities;

import haven.HavenMod;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
	@Inject(method="supports", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (BlockEntityType.SIGN.equals(this)) {
			Block block = state.getBlock();
			if (block instanceof SignBlock sign) {
				if (HavenMod.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
			else if (block instanceof WallSignBlock sign) {
				if (HavenMod.SIGN_TYPES.contains(sign.getSignType())) cir.setReturnValue(true);
			}
		}
		else if (BlockEntityType.BED.equals(this)) {
			Block block = state.getBlock();
			if (block instanceof BedBlock bed) {
				if (bed == HavenMod.RAINBOW_BED.BLOCK) cir.setReturnValue(true);
			}
		}
	}
}
