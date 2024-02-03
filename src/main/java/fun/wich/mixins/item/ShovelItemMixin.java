package fun.wich.mixins.item;

import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
	@Inject(method="useOnBlock", at = @At(value="INVOKE", shift=At.Shift.AFTER, target="Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
	private void InjectBlockChangeEvent(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		context.getWorld().emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, context.getBlockPos());
	}
}
