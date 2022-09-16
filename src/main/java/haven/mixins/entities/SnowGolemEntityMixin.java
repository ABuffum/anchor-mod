package haven.mixins.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin {
	@Inject(method="interactMob", at = @At("HEAD"), cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack itemStack = player.getStackInHand(hand);
		SnowGolemEntity entity = (SnowGolemEntity)(Object)this;
		if (itemStack.getItem() instanceof ShearsItem && entity.isShearable()) {
			entity.sheared(SoundCategory.PLAYERS);
			entity.emitGameEvent(GameEvent.SHEAR, player);
			if (!entity.world.isClient) {
				itemStack.damage(1, (LivingEntity)player, (Consumer)((playerx) -> {
					((LivingEntity)playerx).sendToolBreakStatus(hand);
				}));
			}

			cir.setReturnValue(ActionResult.success(entity.world.isClient));
		}
	}
}
