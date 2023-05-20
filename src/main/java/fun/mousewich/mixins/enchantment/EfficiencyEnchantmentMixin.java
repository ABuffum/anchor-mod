package fun.mousewich.mixins.enchantment;

import net.minecraft.enchantment.EfficiencyEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EfficiencyEnchantment.class)
public class EfficiencyEnchantmentMixin {
	@Inject(method="isAcceptableItem", at = @At("HEAD"), cancellable = true)
	private void IsAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem() instanceof ShearsItem) cir.setReturnValue(true);
	}
}
