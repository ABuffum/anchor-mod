package haven.blocks;

import haven.ModBase;
import haven.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ChiseledBookshelfBlockEntity extends BlockEntity implements Clearable {
	// Assigning an arbitrary value unique to chiseled bookshelf block entities:
	// Hopefully nobody else is using this one since they only go up to 14 in vanilla Minecraft?
	// If they are we might be in trouble. I'm not 100% sure how the packet system in Minecraft works.
	public static final int BLOCK_ENTITY_UPDATE_S2C_PACKET_TYPE = 8122514;
	public static final int MAX_BOOKS = 6;
	private final Deque<ItemStack> books = new ArrayDeque<ItemStack>(MAX_BOOKS);

	public ChiseledBookshelfBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.CHISELED_BOOKSHELF_ENTITY, pos, state);
	}

	public ItemStack getLastBook() {
		return Objects.requireNonNullElse(this.books.poll(), ItemStack.EMPTY);
	}

	public void addBook(ItemStack stack) {
		if (stack.isIn(ModTags.Items.BOOKSHELF_BOOKS)) {
			this.books.addFirst(stack);
		}
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		this.books.clear();
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(MAX_BOOKS, ItemStack.EMPTY);
		Inventories.readNbt(nbt, defaultedList);
		for (ItemStack itemStack : defaultedList) {
			if (!itemStack.isIn(ModTags.Items.BOOKSHELF_BOOKS)) continue;
			this.books.add(itemStack);
		}
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		this.saveInitialChunkData(nbt);
		return nbt;
	}

	private NbtCompound saveInitialChunkData(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, ChiseledBookshelfBlockEntity.getBooksAsList(this.books), true);
		return nbt;
	}

	@Nullable
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return new BlockEntityUpdateS2CPacket(this.pos, BLOCK_ENTITY_UPDATE_S2C_PACKET_TYPE, this.toInitialChunkDataNbt());
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() { return this.saveInitialChunkData(new NbtCompound()); }

	@NotNull
	private static DefaultedList<ItemStack> getBooksAsList(Collection<ItemStack> books) {
		DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(books.size());
		defaultedList.addAll(books);
		return defaultedList;
	}

	@Override
	public void clear() { this.books.clear(); }

	public int getBookCount() { return this.books.size(); }

	public boolean isFull() { return this.getBookCount() == MAX_BOOKS; }

	public boolean isEmpty() { return this.books.isEmpty(); }
}