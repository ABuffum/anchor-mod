package fun.mousewich.mixins.item;

import fun.mousewich.event.ModGameEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
	@Inject(method="use", at = @At("HEAD"))
	public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if (user.fishHook != null) user.emitGameEvent(ModGameEvent.ITEM_INTERACT_FINISH);
		else user.emitGameEvent(ModGameEvent.ITEM_INTERACT_START);
	}
}
