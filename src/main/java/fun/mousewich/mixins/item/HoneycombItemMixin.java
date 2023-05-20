package fun.mousewich.mixins.item;

import fun.mousewich.util.OxidationScale;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoneycombItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {
	@Inject(method="getWaxedState", at = @At("HEAD"), cancellable = true)
	private static void GetWaxedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
		cir.setReturnValue(OxidationScale.getWaxedState(state));
	}
}
