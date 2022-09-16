package haven.blocks.mud;

import haven.HavenMod;
import haven.util.BucketUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Map;

public class MudCauldronBlock extends LeveledCauldronBlock {
	public static final Map<Item, CauldronBehavior> MUD_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	public static final CauldronBehavior FILL_FROM_BUCKET = (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack, HavenMod.MUD_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY, Items.BUCKET);
	public static final CauldronBehavior FILL_FROM_WOOD_BUCKET = (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack, HavenMod.MUD_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY, HavenMod.WOOD_BUCKET);
	public static final CauldronBehavior FILL_FROM_COPPER_BUCKET = (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack, HavenMod.MUD_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY, HavenMod.COPPER_BUCKET);
	public static final CauldronBehavior EMPTY_TO_BUCKET = (state, world, pos, player, hand, stack) 
			-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(HavenMod.MUD_BUCKET), statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	public static final CauldronBehavior EMPTY_TO_WOOD_BUCKET = (state, world, pos, player, hand, stack) 
			-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(HavenMod.WOOD_MUD_BUCKET), statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	public static final CauldronBehavior EMPTY_TO_COPPER_BUCKET = (state, world, pos, player, hand, stack) 
			-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(HavenMod.COPPER_MUD_BUCKET), statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);

	public MudCauldronBlock(Settings settings) {
		super(settings, precipitation -> false, getMudCauldronBehaviors());
	}

	public static Map<Item, CauldronBehavior> getMudCauldronBehaviors() {
		MUD_CAULDRON_BEHAVIOR.put(HavenMod.MUD_BUCKET, FILL_FROM_BUCKET);
		MUD_CAULDRON_BEHAVIOR.put(HavenMod.WOOD_MUD_BUCKET, FILL_FROM_WOOD_BUCKET);
		MUD_CAULDRON_BEHAVIOR.put(HavenMod.COPPER_MUD_BUCKET, FILL_FROM_COPPER_BUCKET);
		MUD_CAULDRON_BEHAVIOR.put(Items.BUCKET, EMPTY_TO_BUCKET);
		MUD_CAULDRON_BEHAVIOR.put(HavenMod.WOOD_BUCKET, EMPTY_TO_WOOD_BUCKET);
		MUD_CAULDRON_BEHAVIOR.put(HavenMod.COPPER_BUCKET, EMPTY_TO_COPPER_BUCKET);

		return MUD_CAULDRON_BEHAVIOR;
	}

	@Override
	protected boolean canBeFilledByDripstone(Fluid fluid) {
		return fluid instanceof MudFluid;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && isEntityTouchingFluid(state, pos, entity)) {
			boolean shouldDrain = false;
			if (entity.isOnFire()) {
				entity.extinguish();
				shouldDrain = true;
			}

			if (shouldDrain && entity.canModifyAt(world, pos)) {
				decrementFluidLevel(state, world, pos);
			}
		}
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return Items.CAULDRON.getDefaultStack();
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack stack = player.getStackInHand(hand);
		Item item = stack.getItem();
		ItemStack newStack = null;
		if (item == Items.BUCKET) newStack = new ItemStack(HavenMod.MUD_BUCKET);
		else if (item == HavenMod.WOOD_BUCKET) newStack = new ItemStack(HavenMod.WOOD_MUD_BUCKET);
		else if (item == HavenMod.COPPER_BUCKET) newStack = new ItemStack(HavenMod.COPPER_MUD_BUCKET);
		if (newStack != null) {
			if (!player.getAbilities().creativeMode) {
				player.getStackInHand(hand).decrement(1);
				if (player.getStackInHand(hand).isEmpty()) player.setStackInHand(hand, newStack);
				else player.getInventory().insertStack(newStack);
			}
			else player.getInventory().insertStack(newStack);
			world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
			return ActionResult.SUCCESS;
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}
}
