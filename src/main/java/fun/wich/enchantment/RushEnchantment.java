package fun.wich.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class RushEnchantment extends Enchantment {
	public RushEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.ARMOR_FEET, slots); }
	@Override
	public int getMaxLevel() { return 4; }
}
