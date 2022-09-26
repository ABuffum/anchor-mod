package haven.mixins;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method="isOf", at = @At("HEAD"), cancellable = true)
	public void isOf(Item item, CallbackInfoReturnable<Boolean> cir) {
		if (item == Items.FISHING_ROD) {
			if (((ItemStack)(Object)this).getItem() instanceof FishingRodItem) cir.setReturnValue(true);
		}
		else if (item == Items.SHEARS) {
			if (((ItemStack)(Object)this).getItem() instanceof ShearsItem) cir.setReturnValue(true);
		}
	}
}
