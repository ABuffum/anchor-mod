package fun.mousewich.mixins.enchantment;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import fun.mousewich.item.tool.HammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireAspectEnchantment.class)
public abstract class FireAspectEnchantmentMixin extends Enchantment {
	protected FireAspectEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) { super(weight, type, slotTypes); }
	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		Item item = stack.getItem();
		if (item instanceof AxeItem || item instanceof HammerItem || item instanceof KnifeItem) return true;
		return super.isAcceptableItem(stack);
	}
}
