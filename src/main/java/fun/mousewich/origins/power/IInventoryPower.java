package fun.mousewich.origins.power;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface IInventoryPower extends Inventory {
	boolean shouldDropOnDeath(ItemStack stack);
}
