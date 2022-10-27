package haven.blocks;

import haven.HavenMod;
import haven.HavenTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ChiseledBookshelfBlockEntity extends BlockEntity implements Clearable {
	public static final int MAX_BOOKS = 6;
	private final Deque<ItemStack> books = new ArrayDeque<ItemStack>(MAX_BOOKS);

	public ChiseledBookshelfBlockEntity(BlockPos pos, BlockState state) {
		super(HavenMod.CHISELED_BOOKSHELF_ENTITY, pos, state);
	}

	public ItemStack getLastBook() {
		return Objects.requireNonNullElse(this.books.poll(), ItemStack.EMPTY);
	}

	public void addBook(ItemStack stack) {
		if (stack.isIn(HavenTags.Items.BOOKSHELF_BOOKS)) this.books.addFirst(stack);
	}

	public void readNbt(NbtCompound nbt) {
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(MAX_BOOKS, ItemStack.EMPTY);
		Inventories.readNbt(nbt, defaultedList);
		this.books.clear();
		for (ItemStack itemStack : defaultedList) {
			if (itemStack.isIn(HavenTags.Items.BOOKSHELF_BOOKS)) {
				this.books.add(itemStack);
			}
		}

	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, getBooksAsList(this.books), true);
		return nbt;
	}

	private static @NotNull DefaultedList<ItemStack> getBooksAsList(Collection<ItemStack> books) {
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(books.size());
		defaultedList.addAll(books);
		return defaultedList;
	}

	//public BlockEntityUpdateS2CPacket method_45465() {
	//	return BlockEntityUpdateS2CPacket.create(this);
	//}

	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbtCompound = new NbtCompound();
		Inventories.writeNbt(nbtCompound, getBooksAsList(this.books), true);
		return nbtCompound;
	}

	public void clear() { this.books.clear(); }
	public int getBookCount() { return this.books.size(); }
	public boolean isFull() { return this.getBookCount() == MAX_BOOKS; }
	public boolean isEmpty() { return this.books.isEmpty(); }
}