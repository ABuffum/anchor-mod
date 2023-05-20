package fun.mousewich.mixins.block;

import fun.mousewich.ModBase;
import fun.mousewich.origins.power.WalkOnPowderSnowPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
	@Inject(method="canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
	private static void CanWalkOnPowderSnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		for (WalkOnPowderSnowPower power : PowerHolderComponent.getPowers(entity, WalkOnPowderSnowPower.class)) {
			if (power.isActive()) {
				cir.setReturnValue(!power.inverted);
				return;
			}
		}
		if (entity instanceof LivingEntity livingEntity) {
			Item item = livingEntity.getEquippedStack(EquipmentSlot.FEET).getItem();
			if (item == Items.LEATHER_BOOTS || item == ModBase.STUDDED_LEATHER_BOOTS) cir.setReturnValue(true);
		}
	}
}
