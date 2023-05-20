package fun.mousewich.enchantment;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public abstract class ShieldBreakingEnchantment extends Enchantment {
	public ShieldBreakingEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	protected boolean canAccept(Enchantment other) {
		return !(other instanceof DamageEnchantment || other instanceof ShieldBreakingEnchantment);
	}
	@Override
	public abstract boolean isAcceptableItem(ItemStack stack);
}
