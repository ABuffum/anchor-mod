package fun.wich.mixins.enchantment;

import com.google.common.collect.Lists;
import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.wich.item.tool.ExtraKnockbackItem;
import fun.wich.item.tool.HammerItem;
import fun.wich.origins.power.SoulSpeedPower;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	@SuppressWarnings("StatementWithEmptyBody")
	@Redirect(method="generateEnchantments", at=@At(value="INVOKE", target="Lnet/minecraft/enchantment/EnchantmentHelper;getPossibleEntries(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;"))
	private static List<EnchantmentLevelEntry> OverridePossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
		ArrayList<EnchantmentLevelEntry> list = Lists.newArrayList();
		Item item = stack.getItem();
		boolean hammer = item instanceof HammerItem;
		boolean knife = item instanceof KnifeItem;
		boolean bl = stack.isOf(Items.BOOK);
		block0: for (Enchantment enchantment : Registry.ENCHANTMENT) {
			//Skip treasure enchantments when treasure is not allowed
			if (enchantment.isTreasure() && !treasureAllowed) continue;
			//Skip enchantments that cannot be applied randomly
			else if (!enchantment.isAvailableForRandomSelection()) continue;
			//Skip disallowed enchantments for item types with exclusion rules
			else if (hammer && HammerItem.DISALLOWED_ENCHANTMENTS.contains(enchantment)) continue;
			else if (knife && KnifeItem.DENIED_ENCHANTMENTS.contains(enchantment)) continue;
			else if (knife && KnifeItem.ALLOWED_ENCHANTMENTS.contains(enchantment)) { }//bypass the acceptable item check
			//Only apply enchantments to items they accept and books
			else if (!enchantment.isAcceptableItem(stack) && !bl) continue;
			//Populate the list
			for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
				if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i)) continue;
				list.add(new EnchantmentLevelEntry(enchantment, i));
				continue block0;
			}
		}
		return list;
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
