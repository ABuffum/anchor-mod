package fun.mousewich.util;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantmentUtil {
	public static boolean isBow(ItemStack stack) { return isBow(stack.getItem()); }
	public static boolean isBow(Item item) {
		return EnchantmentTarget.BOW.isAcceptableItem(item) || EnchantmentTarget.CROSSBOW.isAcceptableItem(item);
	}
}
