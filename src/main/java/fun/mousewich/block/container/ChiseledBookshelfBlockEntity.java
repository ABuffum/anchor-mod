package fun.mousewich.block.container;

import fun.mousewich.ModBase;
import fun.mousewich.gen.data.tag.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.function.Predicate;

public class ChiseledBookshelfBlockEntity extends BlockEntity implements Inventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(ChiseledBookshelfBlock.MAX_BOOK_COUNT, ItemStack.EMPTY);
	private int lastInteractedSlot = -1;

	public ChiseledBookshelfBlockEntity(BlockPos pos, BlockState state) { super(ModBase.CHISELED_BOOKSHELF_ENTITY, pos, state); }

	private void updateState(int interactedSlot) {
		if (interactedSlot < 0 || interactedSlot >= ChiseledBookshelfBlock.MAX_BOOK_COUNT) {
			ModBase.LOGGER.error("Expected slot 0-5, got {}", interactedSlot);
			return;
		}
		this.lastInteractedSlot = interactedSlot;
		BlockState blockState = this.getCachedState();
		for (int i = 0; i < ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); ++i) {
			boolean bl = !this.getStack(i).isEmpty();
			BooleanProperty booleanProperty = ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
			blockState = blockState.with(booleanProperty, bl);
		}
		Objects.requireNonNull(this.world).setBlockState(this.pos, blockState, Block.NOTIFY_ALL);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.inventory.clear();
		Inventories.readNbt(nbt, this.inventory);
		this.lastInteractedSlot = nbt.getInt("last_interacted_slot");
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, this.inventory, true);
		nbt.putInt("last_interacted_slot", this.lastInteractedSlot);
		return nbt;
	}

	public int getOpenSlotCount() { return (int)this.inventory.stream().filter(Predicate.not(ItemStack::isEmpty)).count(); }

	@Override
	public void clear() { this.inventory.clear(); }

	@Override
	public int size() { return ChiseledBookshelfBlock.MAX_BOOK_COUNT; }

	@Override
	public boolean isEmpty() { return this.inventory.stream().allMatch(ItemStack::isEmpty); }

	@Override
	public ItemStack getStack(int slot) { return this.inventory.get(slot); }

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Objects.requireNonNullElse(this.inventory.get(slot), ItemStack.EMPTY);
		this.inventory.set(slot, ItemStack.EMPTY);
		if (!itemStack.isEmpty()) this.updateState(slot);
		return itemStack;
	}

	@Override
	public ItemStack removeStack(int slot) { return this.removeStack(slot, 1); }

	@Override
	public void setStack(int slot, ItemStack stack) {
		if (stack.isIn(ModItemTags.BOOKSHELF_BOOKS)) {
			this.inventory.set(slot, stack);
			this.updateState(slot);
		}
	}

	@Override
	public int getMaxCountPerStack() { return 1; }

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (this.world == null || this.world.getBlockEntity(this.pos) != this) return false;
		return player.squaredDistanceTo(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 0;
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) { return stack.isIn(ModItemTags.BOOKSHELF_BOOKS) && this.getStack(slot).isEmpty(); }
	public int getLastInteractedSlot() { return this.lastInteractedSlot; }
}