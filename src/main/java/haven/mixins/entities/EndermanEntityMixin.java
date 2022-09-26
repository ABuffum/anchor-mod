package haven.mixins.entities;

import haven.HavenTags;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {
	@Inject(method="isPlayerStaring", at = @At("HEAD"), cancellable = true)
	void IsPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		ItemStack itemStack = player.getInventory().armor.get(3);
		if (itemStack.isIn(HavenTags.Items.CARVED_PUMPKINS)) cir.setReturnValue(false);
	}
}
