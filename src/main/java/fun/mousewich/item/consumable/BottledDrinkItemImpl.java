package fun.mousewich.item.consumable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BottledDrinkItemImpl extends BottledDrinkItem {
	public BottledDrinkItemImpl(Settings settings) { super(settings); }
	@Override public void OnDrink(ItemStack stack, LivingEntity user) { }
}
