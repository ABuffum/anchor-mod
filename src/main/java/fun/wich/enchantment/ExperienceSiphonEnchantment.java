package fun.wich.enchantment;

import fun.wich.util.EnchantmentUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ExperienceSiphonEnchantment extends Enchantment {
	public ExperienceSiphonEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof AxeItem || EnchantmentUtil.isBow(item) || super.isAcceptableItem(stack);
	}
}
