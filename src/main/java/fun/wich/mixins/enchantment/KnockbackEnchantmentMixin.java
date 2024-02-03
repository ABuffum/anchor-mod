package fun.wich.mixins.enchantment;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.wich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(KnockbackEnchantment.class)
public abstract class KnockbackEnchantmentMixin extends Enchantment {
	protected KnockbackEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) { super(weight, type, slotTypes); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof HammerItem || item instanceof KnifeItem) return true;
		return super.isAcceptableItem(stack);
	}
}
