package fun.wich.mixins.item;

import fun.wich.event.ModGameEvent;
import fun.wich.origins.power.ChorusImmunePower;
import fun.wich.origins.power.PowersUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusFruitItem.class)
public abstract class ChorusFruitItemMixin extends Item {
	public ChorusFruitItemMixin(Settings settings) { super(settings); }

	@Inject(method="finishUsing", at = @At("HEAD"), cancellable = true)
	public void DontTeleportIfImmune(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (PowersUtil.Active(user, ChorusImmunePower.class)) cir.setReturnValue(super.finishUsing(stack, world, user));
	}

	@Inject(method="finishUsing", at = @At(value="INVOKE", target="Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
	public void TriggerTeleportEvent(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (!world.isClient) world.emitGameEvent(user, ModGameEvent.TELEPORT, user.getBlockPos());
	}
}
