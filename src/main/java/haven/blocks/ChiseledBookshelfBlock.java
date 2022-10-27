package haven.blocks;

import haven.HavenTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ChiseledBookshelfBlock extends BlockWithEntity {
	public static final IntProperty BOOKS_STORED = IntProperty.of("books_stored", 0, ChiseledBookshelfBlockEntity.MAX_BOOKS);
	public static final IntProperty LAST_INTERACTION_BOOK_SLOT = IntProperty.of("last_interaction_book_slot", 0, 6);

	public ChiseledBookshelfBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(BOOKS_STORED, 0).with(HorizontalFacingBlock.FACING, Direction.NORTH).with(LAST_INTERACTION_BOOK_SLOT, 0));
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
			if (world.isClient()) return ActionResult.SUCCESS;
			else {
				ItemStack itemStack = player.getStackInHand(hand);
				return itemStack.isIn(HavenTags.Items.BOOKSHELF_BOOKS) ? tryAddBook(state, world, pos, player, chiseledBookshelfBlockEntity, itemStack) : tryRemoveBook(state, world, pos, player, chiseledBookshelfBlockEntity);
			}
		}
		else return ActionResult.PASS;
	}

	private static ActionResult tryRemoveBook(BlockState state, World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity) {
		if (!blockEntity.isEmpty()) {
			ItemStack itemStack = blockEntity.getLastBook();
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			int i = blockEntity.getBookCount();
			world.setBlockState(pos, state.with(BOOKS_STORED, i).with(LAST_INTERACTION_BOOK_SLOT, i + 1), 3);
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
			if (!player.getInventory().insertStack(itemStack)) player.dropItem(itemStack, false);
		}
		return ActionResult.CONSUME;
	}

	private static ActionResult tryAddBook(BlockState state, World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, ItemStack stack) {
		if (!blockEntity.isFull()) {
			blockEntity.addBook(stack.split(1));
			world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (player.isCreative()) stack.increment(1);
			int i = blockEntity.getBookCount();
			world.setBlockState(pos, state.with(BOOKS_STORED, i).with(LAST_INTERACTION_BOOK_SLOT, i), 3);
			world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
		}
		return ActionResult.CONSUME;
	}

	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ChiseledBookshelfBlockEntity(pos, state);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{BOOKS_STORED}).add(new Property[]{LAST_INTERACTION_BOOK_SLOT}).add(HorizontalFacingBlock.FACING);
	}

	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
				for(ItemStack itemStack = chiseledBookshelfBlockEntity.getLastBook(); !itemStack.isEmpty(); itemStack = chiseledBookshelfBlockEntity.getLastBook()) {
					ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				}
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(HorizontalFacingBlock.FACING, ctx.getPlayerFacing().getOpposite());
	}

	public boolean hasComparatorOutput(BlockState state) { return true; }

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(LAST_INTERACTION_BOOK_SLOT);
	}
}