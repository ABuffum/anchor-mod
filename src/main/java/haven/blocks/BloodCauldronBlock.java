package haven.blocks;

import haven.HavenMod;
import haven.blood.BloodFluid;
import haven.blood.BloodType;
import haven.util.BucketUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

public class BloodCauldronBlock extends LeveledCauldronBlock {
	public static final Map<Item, CauldronBehavior> BLOOD_CAULDRON_BEHAVIOR = CauldronBehavior.createMap();
	public static final CauldronBehavior FILL_FROM_BUCKET = (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack, HavenMod.BLOOD_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY, Items.BUCKET);
	public static final CauldronBehavior FILL_FROM_WOOD_BUCKET = (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack, HavenMod.BLOOD_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY, HavenMod.WOOD_BUCKET);
	public static final CauldronBehavior FILL_FROM_COPPER_BUCKET = (state, world, pos, player, hand, stack)
			-> BucketUtils.fillCauldron(world, pos, player, hand, stack, HavenMod.BLOOD_CAULDRON.getDefaultState().with(LEVEL, 3), SoundEvents.ITEM_BUCKET_EMPTY, HavenMod.COPPER_BUCKET);
	public static final CauldronBehavior FILL_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> {
		Block block = state.getBlock();
		if ((block == Blocks.CAULDRON || block == HavenMod.BLOOD_CAULDRON) && (!state.contains(LEVEL) || state.get(LEVEL) != 3) && stack.getItem() == HavenMod.BLOOD_BOTTLE) {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
				if (block == Blocks.CAULDRON) {
					world.setBlockState(pos, HavenMod.BLOOD_CAULDRON.getDefaultState().with(LEVEL, 1));
				} else {
					world.setBlockState(pos, state.with(LEVEL, state.get(LEVEL) + 1));
				}
				world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
			}
			return ActionResult.success(world.isClient);
		}
		return ActionResult.PASS;
	};
	public static final CauldronBehavior EMPTY_TO_BUCKET = (state, world, pos, player, hand, stack)
			-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(HavenMod.BLOOD_BUCKET), statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	public static final CauldronBehavior EMPTY_TO_WOOD_BUCKET = (state, world, pos, player, hand, stack)
			-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(HavenMod.WOOD_BLOOD_BUCKET), statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	public static final CauldronBehavior EMPTY_TO_COPPER_BUCKET = (state, world, pos, player, hand, stack)
			-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(HavenMod.COPPER_BLOOD_BUCKET), statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	public static final CauldronBehavior EMPTY_TO_BOTTLE = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient) {
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(HavenMod.BLOOD_BOTTLE)));
			player.incrementStat(Stats.USE_CAULDRON);
			player.incrementStat(Stats.USED.getOrCreateStat(item));
			LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
			world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
		}
		return ActionResult.success(world.isClient);
	};

	public BloodCauldronBlock(Settings settings) {
		super(settings, precipitation -> false, getBloodCauldronBehaviors());
	}

	public static Map<Item, CauldronBehavior> getBloodCauldronBehaviors() {
		BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.BLOOD_BUCKET, FILL_FROM_BUCKET);
		BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.WOOD_BLOOD_BUCKET, FILL_FROM_WOOD_BUCKET);
		BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.COPPER_BLOOD_BUCKET, FILL_FROM_COPPER_BUCKET);
		BLOOD_CAULDRON_BEHAVIOR.put(Items.BUCKET, EMPTY_TO_BUCKET);
		BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.WOOD_BUCKET, EMPTY_TO_WOOD_BUCKET);
		BLOOD_CAULDRON_BEHAVIOR.put(HavenMod.COPPER_BUCKET, EMPTY_TO_COPPER_BUCKET);

		return BLOOD_CAULDRON_BEHAVIOR;
	}

	@Override
	protected boolean canBeFilledByDripstone(Fluid fluid) {
		return fluid instanceof BloodFluid;
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && isEntityTouchingFluid(state, pos, entity)) {
			boolean shouldDrain = false;
			if (entity.isOnFire()) {
				entity.extinguish();
				shouldDrain = true;
			}
			if (entity instanceof LivingEntity livingEntity) {
				if (BloodType.Get(livingEntity) == BloodType.VAMPIRE) {
					livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10, 1));
				}
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
		if (item == Items.BUCKET) newStack = new ItemStack(HavenMod.BLOOD_BUCKET);
		else if (item == HavenMod.WOOD_BUCKET) newStack = new ItemStack(HavenMod.WOOD_BLOOD_BUCKET);
		else if (item == HavenMod.COPPER_BUCKET) newStack = new ItemStack(HavenMod.COPPER_BLOOD_BUCKET);
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
