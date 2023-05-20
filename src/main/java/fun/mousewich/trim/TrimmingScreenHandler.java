package fun.mousewich.trim;

import fun.mousewich.ModBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.List;
import java.util.Optional;

public class TrimmingScreenHandler extends ModForgingScreenHandler {
	private final World world;
	private TrimmingRecipe currentRecipe;
	private final List<TrimmingRecipe> recipes;

	public TrimmingScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
	}
	public TrimmingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(ModBase.TRIMMING_SCREEN_HANDLER, syncId, playerInventory, context);
		this.world = playerInventory.player.world;
		this.recipes = this.world.getRecipeManager().listAllOfType(ModBase.TRIMMING_RECIPE_TYPE);
	}
	@Override
	protected ForgingSlotsManager getForgingSlotsManager() {
		return ForgingSlotsManager.create().input(0, 8, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.testTemplate(stack))).input(1, 26, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.testBase(stack) && recipe.testTemplate(this.slots.get(0).getStack()))).input(2, 44, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.testAddition(stack) && recipe.testTemplate(this.slots.get(0).getStack()))).output(3, 98, 48).build();
	}

	public ScreenHandlerType<?> getType() { return ModBase.TRIMMING_SCREEN_HANDLER; }
	@Override
	protected boolean canUse(BlockState state) { return state.isOf(ModBase.TRIMMING_TABLE.asBlock()); }
	@Override
	protected boolean canTakeOutput(PlayerEntity player, boolean present) {
		return this.currentRecipe != null && this.currentRecipe.matches(this.input, this.world);
	}
	@Override
	protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
		stack.onCraft(player.world, player, stack.getCount());
		this.output.unlockLastRecipe(player);
		this.decrementStack(0);
		this.decrementStack(1);
		this.decrementStack(2);
		this.context.run((world, pos) -> world.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, pos, 0));
	}
	private void decrementStack(int slot) {
		ItemStack itemStack = this.input.getStack(slot);
		itemStack.decrement(1);
		this.input.setStack(slot, itemStack);
	}
	@Override
	public void updateResult() {
		List<TrimmingRecipe> list = this.world.getRecipeManager().getAllMatches(ModBase.TRIMMING_RECIPE_TYPE, this.input, this.world);
		if (list.isEmpty()) this.output.setStack(0, ItemStack.EMPTY);
		else {
			TrimmingRecipe recipe = list.get(0);
			ItemStack itemStack = recipe.craft(this.input);
			this.currentRecipe = recipe;
			this.output.setLastRecipe(recipe);
			this.output.setStack(0, itemStack);
		}
	}
	@Override
	public int getSlotFor(ItemStack stack) {
		return this.recipes.stream().map(recipe -> getQuickMoveSlot(recipe, stack)).filter(Optional::isPresent).findFirst().orElse(Optional.of(0)).get();
	}
	private static Optional<Integer> getQuickMoveSlot(TrimmingRecipe recipe, ItemStack stack) {
		if (recipe.testTemplate(stack)) return Optional.of(0);
		if (recipe.testBase(stack)) return Optional.of(1);
		if (recipe.testAddition(stack)) return Optional.of(2);
		return Optional.empty();
	}
	@Override
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
	}
	@Override
	public boolean isValidIngredient(ItemStack stack) {
		return this.recipes.stream().map(recipe -> getQuickMoveSlot(recipe, stack)).anyMatch(Optional::isPresent);
	}
}
