package haven.mixins.items;

import haven.HavenTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin {
	@Inject(method="getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
	public void GetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if (state.isIn(HavenTags.Blocks.FLEECE)) cir.setReturnValue(5.0F);
	}
}
