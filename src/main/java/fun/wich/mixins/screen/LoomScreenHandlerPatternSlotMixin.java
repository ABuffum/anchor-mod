package fun.wich.mixins.screen;

import fun.wich.util.banners.ModBannerPattern;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.LoomScreenHandler$5")
public abstract class LoomScreenHandlerPatternSlotMixin extends Slot {
	public LoomScreenHandlerPatternSlotMixin(Inventory inventory, int index, int x, int y) { super(inventory, index, x, y); }

	@Inject(method="canInsert(Lnet/minecraft/item/ItemStack;)Z", at=@At("RETURN"), cancellable=true)
	private void CheckModBannerPatternItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem() instanceof ModBannerPattern.Provider) cir.setReturnValue(true);
	}
}
