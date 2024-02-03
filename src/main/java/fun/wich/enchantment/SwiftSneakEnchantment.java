package fun.wich.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class SwiftSneakEnchantment extends Enchantment {
	public SwiftSneakEnchantment(Rarity rarity, EquipmentSlot... slots) { super(rarity, EnchantmentTarget.ARMOR_LEGS, slots); }
	@Override
	public int getMinPower(int level) { return level * 25; }
	@Override
	public int getMaxPower(int level) { return this.getMinPower(level) + 50; }
	@Override
	public boolean isTreasure() { return true; }
	@Override
	public boolean isAvailableForEnchantedBookOffer() { return false; }
	@Override
	public boolean isAvailableForRandomSelection() { return false; }
	@Override
	public int getMaxLevel() { return 3; }
	public static float getSpeedBoost(LivingEntity livingEntity) {
		return (float) EnchantmentHelper.getEquipmentLevel(ModEnchantments.SWIFT_SNEAK, livingEntity) * 0.15f;
	}
}
