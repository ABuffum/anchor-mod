package fun.mousewich.mixins.enchantment;

import com.nhoryzon.mc.farmersdelight.item.KnifeItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LuckEnchantment.class)
public class LuckEnchantmentMixin extends Enchantment {
	protected LuckEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
		super(weight, type, slotTypes);
	}

	public boolean isAcceptableItem(ItemStack stack) {
		return super.isAcceptableItem(stack) || (stack.getItem() instanceof KnifeItem && this != Enchantments.FORTUNE);
	}
}
