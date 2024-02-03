package fun.wich.enchantment;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.wich.damage.ModEntityDamageSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class LeechingEnchantment extends Enchantment {
	public LeechingEnchantment(Rarity weight, EquipmentSlot... slots) { super(weight, EnchantmentTarget.WEAPON, slots); }
	@Override
	public int getMaxLevel() { return 3; }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		if (stack.getItem() instanceof KnifeItem) return true;
		return super.isAcceptableItem(stack);
	}
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if (target instanceof LivingEntity) {
			Random random = user.getRandom();
			if (level == 3 || random.nextInt(3 - level) == 0) {
				int heal = random.nextInt(level);
				if (heal > 0) {
					user.heal(heal);
					target.damage(ModEntityDamageSource.leeching(user), heal);
				}
			}
		}
	}
}
