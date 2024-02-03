package fun.wich.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class WaterShotEnchantment extends Enchantment {
	public WaterShotEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.BOW, slots); }
	@Override
	public int getMaxLevel() { return 1; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return EnchantmentTarget.CROSSBOW.isAcceptableItem(stack.getItem()) || super.isAcceptableItem(stack);
	}
}
