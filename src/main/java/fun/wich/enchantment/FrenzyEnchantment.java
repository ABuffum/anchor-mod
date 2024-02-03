package fun.wich.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class FrenzyEnchantment extends Enchantment {
	public FrenzyEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.ARMOR_CHEST, slots); }
	@Override
	public int getMaxLevel() { return 3; }
}
