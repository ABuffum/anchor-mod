package fun.mousewich.mixins.enchantment;

import fun.mousewich.item.tool.ExtraKnockbackItem;
import fun.mousewich.item.tool.HammerItem;
import fun.mousewich.origins.power.SoulSpeedPower;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	@Inject(method="getPossibleEntries", at=@At("RETURN"))
	private static void CanEnchantModItems(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
		Item item = stack.getItem();
		if (item instanceof HammerItem) {
			cir.getReturnValue().removeIf(entry -> HammerItem.DISALLOWED_ENCHANTMENTS.contains(entry.enchantment));
		}
	}

	@Inject(method="getKnockback", at=@At("TAIL"), cancellable = true)
	private static void GetModKnockback(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
		int level = cir.getReturnValue();
		ItemStack stack = entity.getMainHandStack();
		Item item = stack.getItem();
		if (item instanceof HammerItem) level++;
		if (item instanceof ExtraKnockbackItem e) level += e.getExtraKnockback();
		cir.setReturnValue(level);
	}

	@Redirect(method="hasSoulSpeed", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getEquipmentLevel(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)I"))
	private static int CheckSoulSpeed_hasSoulSpeed(Enchantment enchantment, LivingEntity entity) { return SoulSpeedPower.getLevel(entity); }
}
