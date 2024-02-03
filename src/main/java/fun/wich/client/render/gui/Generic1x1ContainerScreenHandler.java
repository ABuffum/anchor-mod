package fun.wich.client.render.gui;

import fun.wich.ModBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class Generic1x1ContainerScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	public Generic1x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(1));
	}
	public Generic1x1ContainerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(ModBase.GENERIC_1X1_SCREEN_HANDLER, syncId);
		int j;
		int i;
		Generic1x1ContainerScreenHandler.checkSize(inventory, 1);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);
		this.addSlot(new Slot(inventory, 0, 80, 35));
		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
	@Override public boolean canUse(PlayerEntity player) { return this.inventory.canPlayerUse(player); }
	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < 1 ? !this.insertItem(itemStack2, 1, 37, true) : !this.insertItem(itemStack2, 0, 1, false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) slot.setStack(ItemStack.EMPTY);
			else slot.markDirty();
			if (itemStack2.getCount() == itemStack.getCount()) return ItemStack.EMPTY;
			slot.onTakeItem(player, itemStack2);
		}
		return itemStack;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.inventory.onClose(player);
	}
}
