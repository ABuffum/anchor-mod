package fun.wich.item.bucket;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ModBucketItem extends BucketItem implements BucketProvided {
	private final BucketProvider bucketProvider;
	public BucketProvider getBucketProvider() { return bucketProvider; }

	protected final Fluid localFluid;

	public ModBucketItem(Fluid fluid, Settings settings, BucketProvider bucketProvider) {
		super(fluid, settings);
		this.localFluid = fluid;
		this.bucketProvider = bucketProvider;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult blockHitResult = raycast(world, user, localFluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
		if (blockHitResult.getType() == HitResult.Type.MISS) return TypedActionResult.pass(itemStack);
		else if (blockHitResult.getType() != HitResult.Type.BLOCK) return TypedActionResult.pass(itemStack);
		else {
			BlockPos blockPos = blockHitResult.getBlockPos();
			Direction direction = blockHitResult.getSide();
			BlockPos blockPos2 = blockPos.offset(direction);
			if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
				BlockState blockState;
				if (localFluid == Fluids.EMPTY) {
					blockState = world.getBlockState(blockPos);
					if (blockState.getBlock() instanceof FluidDrainable fluidDrainable) {
						ItemStack itemStack2 = fluidDrainable.tryDrainFluid(world, blockPos, blockState);
						if (!itemStack2.isEmpty()) {
							user.incrementStat(Stats.USED.getOrCreateStat(this));
							fluidDrainable.getBucketFillSound().ifPresent(sound -> user.playSound(sound, 1.0F, 1.0F));
							world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
							itemStack2 = bucketProvider.bucketInMaterial(itemStack2);
							if (itemStack2 != null && !itemStack2.isEmpty()) {
								ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
								if (!world.isClient) Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
								return TypedActionResult.success(itemStack3, world.isClient());
							}
						}
					}
					return TypedActionResult.fail(itemStack);
				}
				else {
					blockState = world.getBlockState(blockPos);
					BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && localFluid == Fluids.WATER ? blockPos : blockPos2;
					if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
						this.onEmptied(user, world, itemStack, blockPos3);
						if (user instanceof ServerPlayerEntity serverPlayer) Criteria.PLACED_BLOCK.trigger(serverPlayer, blockPos3, itemStack);
						user.incrementStat(Stats.USED.getOrCreateStat(this));
						return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
					}
					return TypedActionResult.fail(itemStack);
				}
			}
			else return TypedActionResult.fail(itemStack);
		}
	}

	public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
		if (stack != null && !stack.isEmpty() && stack.getItem() instanceof BucketProvided bp) {
			return !player.getAbilities().creativeMode ? new ItemStack(bp.getBucketProvider().getBucket()) : stack;
		}
		return stack;
	}
}
