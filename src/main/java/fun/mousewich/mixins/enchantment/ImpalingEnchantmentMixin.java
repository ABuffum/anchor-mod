package fun.mousewich.mixins.enchantment;

import fun.mousewich.item.JavelinItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ImpalingEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ImpalingEnchantment.class)
public abstract class ImpalingEnchantmentMixin extends Enchantment {
	protected ImpalingEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) { super(weight, type, slotTypes); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.getItem() instanceof JavelinItem javelin && javelin.acceptsImpaling() || super.isAcceptableItem(stack);
	}
}
