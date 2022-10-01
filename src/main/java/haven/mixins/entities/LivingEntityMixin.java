package haven.mixins.entities;

import haven.HavenTags;
import haven.origins.powers.UnfreezingPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) { super(type, world); }

	@Inject(method="canFreeze", at = @At("HEAD"), cancellable = true)
	private void CannotFreeze(CallbackInfoReturnable<Boolean> cir) {
		if (UnfreezingPower.HasActivePower(this)) cir.setReturnValue(false);
	}

	@Inject(method="getPreferredEquipmentSlot", at=@At("HEAD"), cancellable = true)
	private static void getPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
		if (stack.isIn(HavenTags.Items.HEAD_WEARABLE_BLOCKS)) cir.setReturnValue(EquipmentSlot.HEAD);
	}

	@Inject(method="getStackInHand", at = @At("HEAD"), cancellable = true)
	public void GetStackInHand(Hand hand, CallbackInfoReturnable<ItemStack> cir) {
		if (hand == null) cir.setReturnValue(((LivingEntity)(Object)this).getEquippedStack(EquipmentSlot.MAINHAND));
	}
}
