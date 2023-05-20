package fun.mousewich.enchantment;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.mousewich.ModBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class SerratedEnchantment extends Enchantment {
	public SerratedEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMinPower(int level) { return 1 + (level - 1) * 8; }
	@Override
	public int getMaxPower(int level) { return this.getMinPower(level) + 20; }
	@Override
	public int getMaxLevel() { return 5; }
	@Override
	public float getAttackDamage(int level, EntityGroup group) { return 0.0f; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		if (stack.getItem() instanceof AxeItem || stack.getItem() instanceof KnifeItem) return true;
		return super.isAcceptableItem(stack);
	}
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity livingEntity) {
			int i = 40 + Math.max(0, 10 * level);
			livingEntity.addStatusEffect(new StatusEffectInstance(ModBase.BLEEDING_EFFECT, i, Math.max(0, level - 1)));
		}
	}
}
