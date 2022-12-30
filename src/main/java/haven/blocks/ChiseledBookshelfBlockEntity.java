package haven.blocks;

import haven.ModBase;
import haven.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class ChiseledBookshelfBlockEntity extends BlockEntity implements Inventory {
	// Assigning an arbitrary value unique to chiseled bookshelf block entities:
	// Hopefully nobody else is using this one since they only go up to 14 in vanilla Minecraft?
	// If they are we might be in trouble. I'm not 100% sure how the packet system in Minecraft works.
	public static final int BLOCK_ENTITY_UPDATE_S2C_PACKET_TYPE = 8122514;
	public static final int MAX_BOOKS = 6;
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

	public ChiseledBookshelfBlockEntity(BlockPos pos, BlockState state) {
		super(ModBase.CHISELED_BOOKSHELF_ENTITY, pos, state);
	}

	private void updateState(int interactedSlot) {
		if (interactedSlot < 0 || interactedSlot >= 6) {
			ModBase.LOGGER.error("Expected slot 0-5, got {}", interactedSlot);
			return;
		}
		BlockState blockState = this.getCachedState().with(ChiseledBookshelfBlock.LAST_INTERACTION_BOOK_SLOT, interactedSlot + 1);
		for (int i = 0; i < ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); ++i) {
			boolean bl = !this.getStack(i).isEmpty();
			BooleanProperty booleanProperty = ChiseledBookshelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
			blockState = blockState.with(booleanProperty, bl);
		}
		Objects.requireNonNull(this.world).setBlockState(this.pos, blockState, Block.NOTIFY_ALL);
	}

	@Override
	public void readNbt(NbtCompound nbt) { Inventories.readNbt(nbt, this.inventory); }

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, this.inventory, true);
		return nbt;
	}

	@Nullable
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return new BlockEntityUpdateS2CPacket(this.pos, BLOCK_ENTITY_UPDATE_S2C_PACKET_TYPE, this.toInitialChunkDataNbt());
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		NbtCompound nbtCompound = new NbtCompound();
		Inventories.writeNbt(nbtCompound, this.inventory, true);
		return nbtCompound;
	}

	public int getOpenSlotCount() {
		return (int)this.inventory.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
	}

	@Override
	public void clear() { this.inventory.clear(); }

	@Override
	public int size() { return 6; }

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
	public ItemStack removeStack(int slot) {
		return this.removeStack(slot, 1);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		if (stack.isIn(ModTags.Items.BOOKSHELF_BOOKS)) {
			this.inventory.set(slot, stack);
			this.updateState(slot);
		}
	}

	@Override
	public int getMaxCountPerStack() { return 1; }

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (this.world == null) return false;
		if (this.world.getBlockEntity(this.pos) != this) return false;
		return !(player.squaredDistanceTo(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) > 64.0);
	}

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return stack.isIn(ModTags.Items.BOOKSHELF_BOOKS) && this.getStack(slot).isEmpty();
	}
}