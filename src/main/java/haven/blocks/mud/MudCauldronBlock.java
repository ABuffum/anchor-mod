package haven.blocks.mud;

import haven.ModBase;
import haven.materials.base.BaseMaterial;
import haven.materials.providers.BucketProvider;
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

	public static CauldronBehavior FillFromBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
				-> BucketUtils.fillCauldron(world, pos, player, hand, stack,
				ModBase.MUD_CAULDRON.getDefaultState().with(LEVEL, 3),
				SoundEvents.ITEM_BUCKET_EMPTY, bucket);
	}

	public static CauldronBehavior EmptyToBucket(Item bucket) {
		return (state, world, pos, player, hand, stack)
				-> CauldronBehavior.emptyCauldron(state, world, pos, player, hand, stack, new ItemStack(bucket),
				statex -> statex.get(LEVEL) > 2, SoundEvents.ITEM_BUCKET_FILL);
	}

	public MudCauldronBlock(Settings settings) { super(settings, precipitation -> false, getMudCauldronBehaviors()); }

	public static Map<Item, CauldronBehavior> getMudCauldronBehaviors() {
		for(BaseMaterial material : ModBase.MATERIALS) {
			if (material instanceof BucketProvider bucketProvider) {
				Item bucket = bucketProvider.getBucket(), mudBucket = bucketProvider.getMudBucket();
				MUD_CAULDRON_BEHAVIOR.put(mudBucket, FillFromBucket(bucket));
				MUD_CAULDRON_BEHAVIOR.put(bucket, EmptyToBucket(mudBucket));
			}
		}
		BucketProvider bp = BucketProvider.DEFAULT_PROVIDER;
		MUD_CAULDRON_BEHAVIOR.put(bp.getMudBucket(), FillFromBucket(bp.getBucket()));
		MUD_CAULDRON_BEHAVIOR.put(bp.getBucket(), EmptyToBucket(bp.getMudBucket()));
		return MUD_CAULDRON_BEHAVIOR;
	}

	@Override
	protected boolean canBeFilledByDripstone(Fluid fluid) { return fluid instanceof MudFluid; }

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!world.isClient && isEntityTouchingFluid(state, pos, entity)) {
			boolean shouldDrain = false;
			if (entity.isOnFire()) {
				entity.extinguish();
				shouldDrain = true;
			}
			if (shouldDrain && entity.canModifyAt(world, pos)) decrementFluidLevel(state, world, pos);
		}
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) { return Items.CAULDRON.getDefaultStack(); }

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		BucketProvider provider = BucketProvider.getProvider(player.getStackInHand(hand).getItem());
		if (provider != null) {
			ItemStack newStack = new ItemStack(provider.getMudBucket());
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
