package fun.wich.enchantment;

import fun.wich.entity.EntityWithAttackStreak;
import fun.wich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommittedEnchantment extends Enchantment {
	public CommittedEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof AxeItem || item instanceof HammerItem;
	}

	public static float getBoost(LivingEntity user, Entity target) {
		return getBoost(user, target, EnchantmentHelper.getLevel(ModEnchantments.COMMITTED, user.getMainHandStack()));
	}
	public static float getBoost(LivingEntity user, Entity target, int level) {
		if (user instanceof EntityWithAttackStreak lastAttacking) {
			if (target instanceof LivingEntity livingEntity) {
				if (user.getAttacking() == livingEntity) {
					return Math.min(10, lastAttacking.getAttackStreakOnTarget()) / 10f * (0.25f + level * 0.25f);
				}
			}
		}
		return 0;
	}
}
