package fun.mousewich.mixins.enchantment;

import fun.mousewich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(KnockbackEnchantment.class)
public abstract class KnockbackEnchantmentMixin extends Enchantment {
	protected KnockbackEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) { super(weight, type, slotTypes); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		if (stack.getItem() instanceof HammerItem) return true;
		return super.isAcceptableItem(stack);
	}
}
