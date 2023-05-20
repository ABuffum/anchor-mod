package fun.mousewich.client.render.gui;

import com.google.common.collect.Lists;
import fun.mousewich.ModBase;
import fun.mousewich.block.WoodcutterBlock;
import fun.mousewich.recipe.WoodcuttingRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class WoodcutterScreenHandler extends ScreenHandler {
	private final ScreenHandlerContext context;
	private final Property selectedRecipe = Property.create();
	private final World world;
	private List<WoodcuttingRecipe> availableRecipes = Lists.newArrayList();
	private ItemStack inputStack = ItemStack.EMPTY;
	long lastTakeTime;
	final Slot inputSlot;
	final Slot outputSlot;
	Runnable contentsChangedListener = () -> {};
	public final Inventory input = new SimpleInventory(1){
		@Override
		public void markDirty() {
			super.markDirty();
			WoodcutterScreenHandler.this.onContentChanged(this);
			WoodcutterScreenHandler.this.contentsChangedListener.run();
		}
	};
	final CraftingResultInventory output = new CraftingResultInventory();

	public WoodcutterScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}

	public WoodcutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(ModBase.WOODCUTTER_SCREEN_HANDLER, syncId);
		int i;
		this.context = context;
		this.world = playerInventory.player.world;
		this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
		this.outputSlot = this.addSlot(new Slot(this.output, 1, 143, 33){
			@Override
			public boolean canInsert(ItemStack stack) { return false; }
			@Override
			public void onTakeItem(PlayerEntity player, ItemStack stack) {
				stack.onCraft(player.world, player, stack.getCount());
				WoodcutterScreenHandler.this.output.unlockLastRecipe(player);
				ItemStack itemStack = WoodcutterScreenHandler.this.inputSlot.takeStack(1);
				if (!itemStack.isEmpty()) WoodcutterScreenHandler.this.populateResult();
				context.run((world, pos) -> {
					long l = world.getTime();
					if (WoodcutterScreenHandler.this.lastTakeTime != l) {
						world.playSound(null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
						WoodcutterScreenHandler.this.lastTakeTime = l;
					}
				});
				super.onTakeItem(player, stack);
			}
		});
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; ++i) this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		this.addProperty(this.selectedRecipe);
	}

	public int getSelectedRecipe() { return this.selectedRecipe.get(); }

	public List<WoodcuttingRecipe> getAvailableRecipes() { return this.availableRecipes; }

	public int getAvailableRecipeCount() { return this.availableRecipes.size(); }

	public boolean canCraft() { return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty(); }

	@Override
	public boolean canUse(PlayerEntity player) {
		return context.get((world, pos) -> world.getBlockState(pos).getBlock() instanceof WoodcutterBlock && player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64, true);
	}

	public boolean onButtonClick(PlayerEntity player, int id) {
		if (this.isInBounds(id)) {
			this.selectedRecipe.set(id);
			this.populateResult();
		}
		return true;
	}

	private boolean isInBounds(int id) { return id >= 0 && id < this.availableRecipes.size(); }

	public void onContentChanged(Inventory inventory) {
		ItemStack itemStack = this.inputSlot.getStack();
		if (!itemStack.isOf(this.inputStack.getItem())) {
			this.inputStack = itemStack.copy();
			this.updateInput(inventory, itemStack);
		}
	}

	private void updateInput(Inventory input, ItemStack stack) {
		this.availableRecipes.clear();
		this.selectedRecipe.set(-1);
		this.outputSlot.setStack(ItemStack.EMPTY);
		if (!stack.isEmpty()) {
			this.availableRecipes = this.world.getRecipeManager().getAllMatches(ModBase.WOODCUTTING_RECIPE_TYPE, input, this.world);
		}
	}

	public void populateResult() {
		if (!this.availableRecipes.isEmpty() && this.isInBounds(this.selectedRecipe.get())) {
			WoodcuttingRecipe recipe = this.availableRecipes.get(this.selectedRecipe.get());
			this.output.setLastRecipe(recipe);
			this.outputSlot.setStack(recipe.craft(this.input));
		}
		else this.outputSlot.setStack(ItemStack.EMPTY);
		this.sendContentUpdates();
	}

	public ScreenHandlerType<?> getType() { return ModBase.WOODCUTTER_SCREEN_HANDLER; }

	public void setContentsChangedListener(Runnable contentsChangedListener) {
		this.contentsChangedListener = contentsChangedListener;
	}

	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
	}

	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			Item item = itemStack2.getItem();
			itemStack = itemStack2.copy();
			if (index == 1) {
				item.onCraft(itemStack2, player.world, player);
				if (!this.insertItem(itemStack2, 2, 38, true)) return ItemStack.EMPTY;
				slot.onQuickTransfer(itemStack2, itemStack);
			}
			else if (index == 0) {
				if (!this.insertItem(itemStack2, 2, 38, false)) return ItemStack.EMPTY;
			}
			else if (this.world.getRecipeManager().getFirstMatch(ModBase.WOODCUTTING_RECIPE_TYPE, new SimpleInventory(itemStack2), this.world).isPresent()) {
				if (!this.insertItem(itemStack2, 0, 1, false)) return ItemStack.EMPTY;
			}
			else if (index >= 2 && index < 29) {
				if (!this.insertItem(itemStack2, 29, 38, false)) return ItemStack.EMPTY;
			}
			else if (index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false)) {
				return ItemStack.EMPTY;
			}
			if (itemStack2.isEmpty()) slot.setStack(ItemStack.EMPTY);
			slot.markDirty();
			if (itemStack2.getCount() == itemStack.getCount()) return ItemStack.EMPTY;
			slot.onTakeItem(player, itemStack2);
			this.sendContentUpdates();
		}
		return itemStack;
	}

	public void close(PlayerEntity player) {
		super.close(player);
		this.output.removeStack(1);
		this.context.run((world, pos) -> this.dropInventory(player, this.input));
	}
}
