package fun.mousewich.mixins.enchantment;

import fun.mousewich.item.JavelinItem;
import net.minecraft.enchantment.ChannelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChannelingEnchantment.class)
public abstract class ChannelingEnchantmentMixin extends Enchantment {
	protected ChannelingEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) { super(weight, type, slotTypes); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.getItem() instanceof JavelinItem javelin && javelin.acceptsChanneling() || super.isAcceptableItem(stack);
	}
}
