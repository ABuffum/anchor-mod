package fun.wich.trim;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public abstract class ModForgingScreenHandler extends ScreenHandler {
	protected final ScreenHandlerContext context;
	protected final PlayerEntity player;
	protected final Inventory input;
	private final List<Integer> inputSlotIndices;
	protected final CraftingResultInventory output = new CraftingResultInventory();
	private final int resultSlotIndex;
	protected abstract boolean canTakeOutput(PlayerEntity var1, boolean var2);
	protected abstract void onTakeOutput(PlayerEntity var1, ItemStack var2);
	protected abstract boolean canUse(BlockState var1);
	public ModForgingScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId);
		this.context = context;
		this.player = playerInventory.player;
		ForgingSlotsManager forgingSlotsManager = this.getForgingSlotsManager();
		this.input = this.createInputInventory(forgingSlotsManager.getInputSlotCount());
		this.inputSlotIndices = forgingSlotsManager.getInputSlotIndices();
		this.resultSlotIndex = forgingSlotsManager.getResultSlotIndex();
		this.addInputSlots(forgingSlotsManager);
		this.addResultSlot(forgingSlotsManager);
		this.addPlayerInventorySlots(playerInventory);
	}
	private void addInputSlots(ForgingSlotsManager forgingSlotsManager) {
		for (final ForgingSlotsManager.ForgingSlot forgingSlot : forgingSlotsManager.getInputSlots()) {
			this.addSlot(new Slot(this.input, forgingSlot.slotId(), forgingSlot.x(), forgingSlot.y()){
				@Override
				public boolean canInsert(ItemStack stack) { return forgingSlot.mayPlace().test(stack); }
			});
		}
	}
	private void addResultSlot(ForgingSlotsManager forgingSlotsManager) {
		this.addSlot(new Slot(this.output, forgingSlotsManager.getResultSlot().slotId(), forgingSlotsManager.getResultSlot().x(), forgingSlotsManager.getResultSlot().y()){
			@Override
			public boolean canInsert(ItemStack stack) { return false; }
			@Override
			public boolean canTakeItems(PlayerEntity playerEntity) { return ModForgingScreenHandler.this.canTakeOutput(playerEntity, this.hasStack()); }
			@Override
			public void onTakeItem(PlayerEntity player, ItemStack stack) { ModForgingScreenHandler.this.onTakeOutput(player, stack); }
		});
	}
	private void addPlayerInventorySlots(PlayerInventory playerInventory) {
		int i;
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
	}
	public abstract void updateResult();
	protected abstract ForgingSlotsManager getForgingSlotsManager();
	private SimpleInventory createInputInventory(int size) {
		return new SimpleInventory(size){
			@Override
			public void markDirty() {
				super.markDirty();
				ModForgingScreenHandler.this.onContentChanged(this);
			}
		};
	}
	@Override
	public void onContentChanged(Inventory inventory) {
		super.onContentChanged(inventory);
		if (inventory == this.input) this.updateResult();
	}
	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.context.run((world, pos) -> this.dropInventory(player, this.input));
	}
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.context.get((world, pos) -> {
			if (!this.canUse(world.getBlockState(pos))) return false;
			return player.squaredDistanceTo((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5) <= 64.0;
		}, true);
	}
	public ItemStack quickMove(PlayerEntity player, int slot) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot2 = this.slots.get(slot);
		if (slot2 != null && slot2.hasStack()) {
			ItemStack itemStack2 = slot2.getStack();
			itemStack = itemStack2.copy();
			int i = this.getPlayerInventoryStartIndex();
			int j = this.getPlayerHotbarEndIndex();
			if (slot == this.getResultSlotIndex()) {
				if (!this.insertItem(itemStack2, i, j, true)) return ItemStack.EMPTY;
				slot2.onQuickTransfer(itemStack2, itemStack);
			}
			else if (this.inputSlotIndices.contains(slot) ? !this.insertItem(itemStack2, i, j, false) : (this.isValidIngredient(itemStack2) && slot >= this.getPlayerInventoryStartIndex() && slot < this.getPlayerHotbarEndIndex() ? !this.insertItem(itemStack2, this.getSlotFor(itemStack), this.getResultSlotIndex(), false) : (slot >= this.getPlayerInventoryStartIndex() && slot < this.getPlayerInventoryEndIndex() ? !this.insertItem(itemStack2, this.getPlayerHotbarStartIndex(), this.getPlayerHotbarEndIndex(), false) : slot >= this.getPlayerHotbarStartIndex() && slot < this.getPlayerHotbarEndIndex() && !this.insertItem(itemStack2, this.getPlayerInventoryStartIndex(), this.getPlayerInventoryEndIndex(), false)))) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) slot2.setStack(ItemStack.EMPTY);
			else slot2.markDirty();
			if (itemStack2.getCount() == itemStack.getCount()) return ItemStack.EMPTY;
			slot2.onTakeItem(player, itemStack2);
		}
		return itemStack;
	}
	protected boolean isValidIngredient(ItemStack stack) { return true; }
	public int getSlotFor(ItemStack stack) { return this.input.isEmpty() ? 0 : this.inputSlotIndices.get(0); }
	public int getResultSlotIndex() { return this.resultSlotIndex; }
	private int getPlayerInventoryStartIndex() { return this.getResultSlotIndex() + 1; }
	private int getPlayerInventoryEndIndex() { return this.getPlayerInventoryStartIndex() + 27; }
	private int getPlayerHotbarStartIndex() { return this.getPlayerInventoryEndIndex(); }
	private int getPlayerHotbarEndIndex() { return this.getPlayerHotbarStartIndex() + 9; }
}
