package fun.mousewich.block.container;

import fun.mousewich.gen.data.tag.ModItemTags;
import fun.mousewich.sound.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;
import java.util.Optional;

public class ChiseledBookshelfBlock extends BlockWithEntity {
	public static final int MAX_BOOK_COUNT = 6;
	public static final int BOOK_HEIGHT = 3;
	public static final BooleanProperty SLOT_0_OCCUPIED = BooleanProperty.of("slot_0_occupied");
	public static final BooleanProperty SLOT_1_OCCUPIED = BooleanProperty.of("slot_1_occupied");
	public static final BooleanProperty SLOT_2_OCCUPIED = BooleanProperty.of("slot_2_occupied");
	public static final BooleanProperty SLOT_3_OCCUPIED = BooleanProperty.of("slot_3_occupied");
	public static final BooleanProperty SLOT_4_OCCUPIED = BooleanProperty.of("slot_4_occupied");
	public static final BooleanProperty SLOT_5_OCCUPIED = BooleanProperty.of("slot_5_occupied");
	public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES = List.of(SLOT_0_OCCUPIED, SLOT_1_OCCUPIED, SLOT_2_OCCUPIED, SLOT_3_OCCUPIED, SLOT_4_OCCUPIED, SLOT_5_OCCUPIED);

	public ChiseledBookshelfBlock(AbstractBlock.Settings settings) {
		super(settings);
		BlockState blockState = this.stateManager.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH);
		for (BooleanProperty booleanProperty : SLOT_OCCUPIED_PROPERTIES) {
			blockState = blockState.with(booleanProperty, false);
		}
		this.setDefaultState(blockState);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (!(blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity)) return ActionResult.PASS;
		Optional<Vec2f> optional = ChiseledBookshelfBlock.getHitPos(hit, state.get(HorizontalFacingBlock.FACING));
		if (optional.isEmpty()) return ActionResult.PASS;
		int i = ChiseledBookshelfBlock.getSlotForHitPos(optional.get());
		if (state.get(SLOT_OCCUPIED_PROPERTIES.get(i))) {
			ChiseledBookshelfBlock.tryRemoveBook(world, pos, player, chiseledBookshelfBlockEntity, i);
			return ActionResult.success(world.isClient);
		}
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isIn(ModItemTags.BOOKSHELF_BOOKS)) {
			ChiseledBookshelfBlock.tryAddBook(world, pos, player, chiseledBookshelfBlockEntity, itemStack, i);
			return ActionResult.success(world.isClient);
		}
		return ActionResult.CONSUME;
	}

	private static Optional<Vec2f> getHitPos(BlockHitResult hit, Direction facing) {
		Direction direction = hit.getSide();
		if (facing != direction) return Optional.empty();
		BlockPos blockPos = hit.getBlockPos().offset(direction);
		Vec3d vec3d = hit.getPos().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
		double d = vec3d.getX();
		double e = vec3d.getY();
		double f = vec3d.getZ();
		return switch (direction) {
			case NORTH -> Optional.of(new Vec2f((float)(1.0 - d), (float)e));
			case SOUTH -> Optional.of(new Vec2f((float)d, (float)e));
			case WEST -> Optional.of(new Vec2f((float)f, (float)e));
			case EAST -> Optional.of(new Vec2f((float)(1.0 - f), (float)e));
			case DOWN, UP -> Optional.empty();
		};
	}
	private static int getSlotForHitPos(Vec2f hitPos) { return ChiseledBookshelfBlock.getColumn(hitPos.x) + (hitPos.y >= 0.5f ? 0 : 1) * BOOK_HEIGHT; }
	private static int getColumn(float x) { return x < 0.375f ? 0 : x < 0.6875f ? 1 : 2; }
	private static void tryAddBook(World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, ItemStack stack, int slot) {
		if (world.isClient) return;
		player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
		SoundEvent soundEvent = stack.isOf(Items.ENCHANTED_BOOK) ? ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_INSERT_ENCHANTED : ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_INSERT;
		blockEntity.setStack(slot, stack.split(1));
		world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
		if (player.isCreative()) stack.increment(1);
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}

	private static void tryRemoveBook(World world, BlockPos pos, PlayerEntity player, ChiseledBookshelfBlockEntity blockEntity, int slot) {
		if (world.isClient) return;
		ItemStack itemStack = blockEntity.removeStack(slot, 1);
		SoundEvent soundEvent = itemStack.isOf(Items.ENCHANTED_BOOK) ? ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP_ENCHANTED : ModSoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP;
		world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
		if (!player.getInventory().insertStack(itemStack)) player.dropItem(itemStack, false);
		world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ChiseledBookshelfBlockEntity(pos, state);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(HorizontalFacingBlock.FACING);
		SLOT_OCCUPIED_PROPERTIES.forEach(builder::add);
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity;
		if (state.isOf(newState.getBlock())) return;
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ChiseledBookshelfBlockEntity && !(chiseledBookshelfBlockEntity = (ChiseledBookshelfBlockEntity)blockEntity).isEmpty()) {
			for (int i = 0; i < MAX_BOOK_COUNT; ++i) {
				ItemStack itemStack = chiseledBookshelfBlockEntity.getStack(i);
				if (itemStack.isEmpty()) continue;
				ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
			}
			chiseledBookshelfBlockEntity.clear();
			world.updateComparators(pos, this);
		}
		super.onStateReplaced(state, world, pos, newState, moved);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		PlayerEntity player = ctx.getPlayer();
		Direction horizontalPlayerFacing = player == null ? Direction.NORTH : player.getHorizontalFacing();
		return this.getDefaultState().with(HorizontalFacingBlock.FACING, horizontalPlayerFacing.getOpposite());
	}
	@Override
	public boolean hasComparatorOutput(BlockState state) { return true; }
	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		if (world.isClient()) return 0;
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
			return chiseledBookshelfBlockEntity.getLastInteractedSlot() + 1;
		}
		return 0;
	}
}
