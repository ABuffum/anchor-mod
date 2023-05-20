package fun.mousewich.mixins.enchantment;

import fun.mousewich.enchantment.ShieldBreakingEnchantment;
import fun.mousewich.item.tool.HammerItem;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {
	@Shadow @Final public int typeIndex;
	@Inject(method="canAccept", at=@At("HEAD"), cancellable=true)
	public void IncompatibleWithShieldBreakingEnchantments(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
		if (other instanceof ShieldBreakingEnchantment) cir.setReturnValue(false);
	}
	@Inject(method="isAcceptableItem", at=@At("HEAD"), cancellable=true)
	public void HammerIsAcceptable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (this.typeIndex > 0 && stack.getItem() instanceof HammerItem) cir.setReturnValue(true);
	}
}
