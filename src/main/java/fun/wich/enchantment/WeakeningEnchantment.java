package fun.wich.enchantment;

import fun.wich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WeakeningEnchantment extends Enchantment {
	public WeakeningEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		return item instanceof AxeItem || item instanceof HammerItem || isBow(item) || super.isAcceptableItem(stack);
	}
	private boolean isBow(ItemStack stack) { return isBow(stack.getItem()); }
	private boolean isBow(Item item) {
		return EnchantmentTarget.BOW.isAcceptableItem(item) || EnchantmentTarget.CROSSBOW.isAcceptableItem(item);
	}
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity livingEntity && !isBow(user.getMainHandStack())) {
			livingEntity.addStatusEffect(getEffect(level));
		}
	}
	public static StatusEffectInstance getEffect(int level) {
		return new StatusEffectInstance(StatusEffects.WEAKNESS, 40 + Math.max(0, 10 * level), Math.max(0, level - 1));
	}
}