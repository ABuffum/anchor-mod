package fun.wich.entity;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public interface HopperTransferable {
	default boolean canTransferTo(Inventory hopperInventory, int slot, ItemStack stack) { return true; }
}
