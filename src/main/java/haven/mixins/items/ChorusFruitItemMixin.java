package haven.mixins.items;

import haven.blood.BloodType;
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
	public void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if (!BloodType.Get(user).IsChorusVulnerable()) {
			cir.setReturnValue(super.finishUsing(stack, world, user));
		}
	}
}
