package haven.mixins.blocks;

import haven.HavenMod;
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
		if (entity instanceof LivingEntity livingEntity) {
			Item item = livingEntity.getEquippedStack(EquipmentSlot.FEET).getItem();
			if (item == Items.LEATHER_BOOTS || item == HavenMod.STUDDED_LEATHER_MATERIAL.getBoots()){
				cir.setReturnValue(true);
			}
		}
	}
}
