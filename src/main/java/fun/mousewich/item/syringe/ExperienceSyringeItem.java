package fun.mousewich.item.syringe;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ExperienceSyringeItem extends BaseSyringeItem {
	public ExperienceSyringeItem() { super(); }
	@Override
	public void ApplyEffect(PlayerEntity user, ItemStack stack, LivingEntity entity) {
		if (entity instanceof PlayerEntity player) {
			int amount = 1 + entity.getRandom().nextInt(3);
			int i = repairPlayerGears(player, amount);
			if (i > 0) player.addExperience(i);
		}
	}
	public static int repairPlayerGears(PlayerEntity player, int amount) {
		Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING, player, ItemStack::isDamaged);
		if (entry != null) {
			ItemStack itemStack = entry.getValue();
			int i = Math.min(amount * 2, itemStack.getDamage());
			itemStack.setDamage(itemStack.getDamage() - i);
			int j = amount - (i / 2);
			return j > 0 ? repairPlayerGears(player, j) : 0;
		}
		else return amount;
	}
}
