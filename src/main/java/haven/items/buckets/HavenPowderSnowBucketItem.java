package haven.items.buckets;

import haven.materials.providers.BucketProvider;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class HavenPowderSnowBucketItem extends Item implements FluidModificationItem, BucketProvided {
	private static final int MAX_USE_TIME = 32;
	private final SoundEvent placeSound;
	private BucketProvider bucketProvider;
	public BucketProvider getBucketProvider() { return bucketProvider; }

	private final Block block;
	public HavenPowderSnowBucketItem(Block block, SoundEvent placeSound, Settings settings, BucketProvider bucketProvider) {
		super(settings);
		this.placeSound = placeSound;
		this.block = block;
		this.bucketProvider = bucketProvider;
	}

	public ActionResult place(ItemPlacementContext context) {
		if (!context.canPlace()) return ActionResult.FAIL;
		else {
			ItemPlacementContext itemPlacementContext = this.getPlacementContext(context);
			if (itemPlacementContext == null) return ActionResult.FAIL;
			else {
				BlockState blockState = this.getPlacementState(itemPlacementContext);
				if (blockState == null) return ActionResult.FAIL;
				else if (!this.place(itemPlacementContext, blockState)) return ActionResult.FAIL;
				else {
					BlockPos blockPos = itemPlacementContext.getBlockPos();
					World world = itemPlacementContext.getWorld();
					PlayerEntity playerEntity = itemPlacementContext.getPlayer();
					ItemStack itemStack = itemPlacementContext.getStack();
					BlockState blockState2 = world.getBlockState(blockPos);
					if (blockState2.isOf(blockState.getBlock())) {
						blockState2 = this.placeFromTag(blockPos, world, itemStack, blockState2);
						this.postPlacement(blockPos, world, playerEntity, itemStack, blockState2);
						blockState2.getBlock().onPlaced(world, blockPos, blockState2, playerEntity, itemStack);
						if (playerEntity instanceof ServerPlayerEntity) {
							Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
						}
					}
					BlockSoundGroup blockSoundGroup = blockState2.getSoundGroup();
					world.playSound(playerEntity, blockPos, this.getPlaceSound(blockState2), SoundCategory.BLOCKS, (blockSoundGroup.getVolume() + 1.0F) / 2.0F, blockSoundGroup.getPitch() * 0.8F);
					world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
					if (playerEntity == null || !playerEntity.getAbilities().creativeMode) itemStack.decrement(1);
					return ActionResult.success(world.isClient);
				}
			}
		}
	}
	@Nullable
	public ItemPlacementContext getPlacementContext(ItemPlacementContext context) { return context; }
	protected boolean postPlacement(BlockPos pos, World world, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
		return writeTagToBlockEntity(world, player, pos, stack);
	}
	@Nullable
	protected BlockState getPlacementState(ItemPlacementContext context) {
		BlockState blockState = this.getBlock().getPlacementState(context);
		return blockState != null && this.canPlace(context, blockState) ? blockState : null;
	}
	public Block getBlock() { return this.block; }
	private BlockState placeFromTag(BlockPos pos, World world, ItemStack stack, BlockState state) {
		BlockState blockState = state;
		NbtCompound nbtCompound = stack.getNbt();
		if (nbtCompound != null) {
			NbtCompound nbtCompound2 = nbtCompound.getCompound("BlockStateTag");
			StateManager<Block, BlockState> stateManager = state.getBlock().getStateManager();
			Iterator var9 = nbtCompound2.getKeys().iterator();
			while(var9.hasNext()) {
				String string = (String)var9.next();
				Property<?> property = stateManager.getProperty(string);
				if (property != null) {
					String string2 = nbtCompound2.get(string).asString();
					blockState = with(blockState, property, string2);
				}
			}
		}
		if (blockState != state) world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
		return blockState;
	}
	private static <T extends Comparable<T>> BlockState with(BlockState state, Property<T> property, String name) {
		return (BlockState)property.parse(name).map((value) -> {
			return (BlockState)state.with(property, value);
		}).orElse(state);
	}

	protected boolean canPlace(ItemPlacementContext context, BlockState state) {
		PlayerEntity playerEntity = context.getPlayer();
		ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
		return (!this.checkStatePlacement() || state.canPlaceAt(context.getWorld(), context.getBlockPos())) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
	}

	protected boolean checkStatePlacement() { return true; }

	protected boolean place(ItemPlacementContext context, BlockState state) {
		return context.getWorld().setBlockState(context.getBlockPos(), state, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
	}

	public static boolean writeTagToBlockEntity(World world, @Nullable PlayerEntity player, BlockPos pos, ItemStack stack) {
		MinecraftServer minecraftServer = world.getServer();
		if (minecraftServer == null) return false;
		else {
			NbtCompound nbtCompound = stack.getSubNbt("BlockEntityTag");
			if (nbtCompound != null) {
				BlockEntity blockEntity = world.getBlockEntity(pos);
				if (blockEntity != null) {
					if (!world.isClient && blockEntity.copyItemDataRequiresOperator() && (player == null || !player.isCreativeLevelTwoOp())) {
						return false;
					}
					NbtCompound nbtCompound2 = blockEntity.writeNbt(new NbtCompound());
					NbtCompound nbtCompound3 = nbtCompound2.copy();
					nbtCompound2.copyFrom(nbtCompound);
					nbtCompound2.putInt("x", pos.getX());
					nbtCompound2.putInt("y", pos.getY());
					nbtCompound2.putInt("z", pos.getZ());
					if (!nbtCompound2.equals(nbtCompound3)) {
						blockEntity.readNbt(nbtCompound2);
						blockEntity.markDirty();
						return true;
					}
				}
			}
			return false;
		}
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult actionResult = this.place(new ItemPlacementContext(context));
		if (!actionResult.isAccepted() && this.isFood()) {
			ActionResult actionResult2 = this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
			actionResult = actionResult2 == ActionResult.CONSUME ? ActionResult.CONSUME_PARTIAL : actionResult2;
		}
		PlayerEntity playerEntity = context.getPlayer();
		if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
			Hand hand = context.getHand();
			playerEntity.setStackInHand(hand, bucketProvider.getBucket().getDefaultStack());
		}
		return actionResult;
	}

	protected SoundEvent getPlaceSound(BlockState state) { return this.placeSound; }

	public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
		if (world.isInBuildLimit(pos) && world.isAir(pos)) {
			if (!world.isClient) world.setBlockState(pos, this.getBlock().getDefaultState(), Block.NOTIFY_ALL);
			world.playSound(player, pos, this.placeSound, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return true;
		}
		else return false;
	}

	public String getTranslationKey() { return this.getOrCreateTranslationKey(); }
	@Override
	public ItemStack getDefaultStack() { return new ItemStack(bucketProvider.getPowderSnowBucket()); }
	@Override
	public int getMaxUseTime(ItemStack stack) { return MAX_USE_TIME; }
	@Override
	public UseAction getUseAction(ItemStack stack) { return UseAction.DRINK; }
}
