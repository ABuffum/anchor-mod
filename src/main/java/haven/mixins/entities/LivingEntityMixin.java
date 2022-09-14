package haven.mixins.entities;

import haven.HavenMod;
import haven.origins.powers.UnfreezingPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method="canFreeze", at = @At("HEAD"), cancellable = true)
	private void CannotFreeze(CallbackInfoReturnable<Boolean> cir) {
		List<UnfreezingPower> powers = PowerHolderComponent.KEY.get(this).getPowers(UnfreezingPower.class);
		if (!powers.isEmpty()) {
			for (UnfreezingPower power : powers) {
				if (power.isActive()) {
					cir.setReturnValue(false);
				}
			}
		}
	}

	@Inject(method="getPreferredEquipmentSlot", at=@At("HEAD"), cancellable = true)
	private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if (stack.isOf(HavenMod.CARVED_MELON.ITEM)) cir.setReturnValue(EquipmentSlot.HEAD);
	}
}
