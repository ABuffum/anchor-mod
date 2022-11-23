package haven.blocks;

import haven.ModRegistry;
import haven.sounds.ModSoundEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class JuicerBlock extends Block {
	public static final DirectionProperty FACING;
	public static final BooleanProperty HAS_BOTTLE;

	public JuicerBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(HAS_BOTTLE, false));
	}

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		super.afterBreak(world, player, pos, state, blockEntity, stack);
		boolean loaded = state.get(HAS_BOTTLE);
		if (loaded) dropStack(world, pos, new ItemStack(Items.GLASS_BOTTLE));
	}

	public Item getJuice(ItemStack stack) {
		for(Map.Entry<Supplier<Item>,Item> entry : ModRegistry.JUICE_MAP.entrySet()) {
			Item juice = entry.getKey().get();
			if (juice != Items.AIR && stack.isOf(juice)) return entry.getValue();
		}
		return null;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		boolean loaded = state.get(HAS_BOTTLE);
		ItemStack itemStack = player.getStackInHand(hand);
		if (loaded) {
			Item juice;
			if (!itemStack.isEmpty() && (juice = getJuice(itemStack)) != null) {
				ItemStack newStack = new ItemStack(juice);
				world.setBlockState(pos, state.with(HAS_BOTTLE, false));
				PlayerInventory inventory = player.getInventory();
				if (!player.getAbilities().creativeMode) {
					player.getStackInHand(hand).decrement(1);
					if (player.getStackInHand(hand).isEmpty()) player.setStackInHand(hand, newStack);
					else if (inventory.getEmptySlot() > 0) inventory.insertStack(newStack);
					else dropStack(world, pos, newStack);
				}
				else if (inventory.getEmptySlot() > 0) inventory.insertStack(newStack);
				else dropStack(world, pos, newStack);
				world.playSound(player, pos, ModSoundEvents.BLOCK_JUICER_JUICED, SoundCategory.BLOCKS, 1F, 1F);
			}
			else {
				world.setBlockState(pos, state.with(HAS_BOTTLE, false));
				ItemStack newStack = new ItemStack(Items.GLASS_BOTTLE);
				PlayerInventory inventory = player.getInventory();
				if (player.getStackInHand(hand).isEmpty()) player.setStackInHand(hand, newStack);
				else if (inventory.getEmptySlot() > 0) inventory.insertStack(newStack);
				else dropStack(world, pos, newStack);
			}
			return ActionResult.SUCCESS;
		}
		else if (itemStack.getItem() == Items.GLASS_BOTTLE) {
			world.setBlockState(pos, state.with(HAS_BOTTLE, true));
			if (!player.getAbilities().creativeMode) player.getStackInHand(hand).decrement(1);
			world.playSound(player, pos, ModSoundEvents.BLOCK_JUICER_LOADED, SoundCategory.BLOCKS, 1F, 1F);
			return ActionResult.SUCCESS;
		}
		else return ActionResult.PASS;
	}

	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, HAS_BOTTLE);
	}

	static {
		FACING = HorizontalFacingBlock.FACING;
		HAS_BOTTLE = Properties.HAS_BOTTLE_0;
	}
}
