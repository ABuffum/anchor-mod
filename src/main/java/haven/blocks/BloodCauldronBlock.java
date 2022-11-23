package haven.blocks;

import haven.ModBase;
import haven.blood.BloodFluid;
import haven.blood.BloodType;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.BucketProvider;
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

	public static CauldronBehavior FillFromBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
				-> BucketUtils.fillCauldron(world, pos, player, hand, stack,
				ModBase.BLOOD_CAULDRON.getDefaultState().with(LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY, bucket);
	}

	public static final CauldronBehavior FILL_FROM_BOTTLE = (state, world, pos, player, hand, stack) -> {
		Block block = state.getBlock();
		if ((block == Blocks.CAULDRON || block == ModBase.BLOOD_CAULDRON) && (!state.contains(LEVEL) || state.get(LEVEL) != 3) && stack.getItem() == ModBase.BLOOD_BOTTLE) {
			if (!world.isClient) {
				player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.incrementStat(Stats.USE_CAULDRON);
				player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
				if (block == Blocks.CAULDRON) {
					world.setBlockState(pos, ModBase.BLOOD_CAULDRON.getDefaultState().with(LEVEL, 1));
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

	public static CauldronBehavior EmptyToBucket(Item bloodBucket) {
		return (state, world, pos, player, hand, stack)
				-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(bloodBucket),
				statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	}
	public static final CauldronBehavior EMPTY_TO_BOTTLE = (state, world, pos, player, hand, stack) -> {
		if (!world.isClient) {
			Item item = stack.getItem();
			player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ModBase.BLOOD_BOTTLE)));
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
		for(BaseMaterial material : ModBase.MATERIALS) {
			if (material instanceof BucketProvider bucketProvider) {
				Item bucket = bucketProvider.getBucket(), bloodBucket = bucketProvider.getBloodBucket();
				BLOOD_CAULDRON_BEHAVIOR.put(bloodBucket, FillFromBucket(bucket));
				BLOOD_CAULDRON_BEHAVIOR.put(bucket, EmptyToBucket(bloodBucket));
			}
		}
		BucketProvider bp = BucketProvider.DEFAULT_PROVIDER;
		BLOOD_CAULDRON_BEHAVIOR.put(bp.getBloodBucket(), FillFromBucket(bp.getBucket()));
		BLOOD_CAULDRON_BEHAVIOR.put(bp.getBucket(), EmptyToBucket(bp.getBloodBucket()));
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
		BucketProvider provider = BucketProvider.getProvider(player.getStackInHand(hand).getItem());
		if (provider != null) {
			ItemStack newStack = new ItemStack(provider.getBloodBucket());
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
