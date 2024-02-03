package fun.wich.mixins.enchantment;

import fun.wich.item.JavelinItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.RiptideEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RiptideEnchantment.class)
public abstract class RiptideEnchantmentMixin extends Enchantment {
	protected RiptideEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) { super(weight, type, slotTypes); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.getItem() instanceof JavelinItem javelin && javelin.acceptsRiptide() || super.isAcceptableItem(stack);
	}
}
