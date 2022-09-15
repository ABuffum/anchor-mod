package haven.items;

import haven.HavenMod;
import haven.mixins.items.BucketItemAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class WoodBucketItem extends BucketItem {

	public WoodBucketItem(Fluid fluid, Settings settings) {
		super(fluid, settings);
	}

	public static ItemStack metalToWood(ItemStack itemStack) {
		Item item = itemStack.getItem();
		if (item == Items.WATER_BUCKET) return new ItemStack(HavenMod.WOOD_WATER_BUCKET, itemStack.getCount());
		else if (item == Items.LAVA_BUCKET) return ItemStack.EMPTY;
		else if (item == Items.POWDER_SNOW_BUCKET) return new ItemStack(HavenMod.WOOD_POWDER_SNOW_BUCKET, itemStack.getCount());
		else if (item == HavenMod.MUD_BUCKET) return new ItemStack(HavenMod.WOOD_MUD_BUCKET, itemStack.getCount());
		else if (item == HavenMod.BLOOD_BUCKET) return new ItemStack(HavenMod.WOOD_BLOOD_BUCKET, itemStack.getCount());
		else if (item == Items.MILK_BUCKET) return new ItemStack(HavenMod.WOOD_MILK_BUCKET, itemStack.getCount());
		else if (item == HavenMod.CHOCOLATE_MILK_BUCKET) return new ItemStack(HavenMod.WOOD_CHOCOLATE_MILK_BUCKET, itemStack.getCount());
		else if (item == HavenMod.STRAWBERRY_MILK_BUCKET) return new ItemStack(HavenMod.WOOD_STRAWBERRY_MILK_BUCKET, itemStack.getCount());
		else if (item == HavenMod.COFFEE_MILK_BUCKET) return new ItemStack(HavenMod.WOOD_COFFEE_MILK_BUCKET, itemStack.getCount());
		else return itemStack;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BucketItemAccessor bia = (BucketItemAccessor)this;
		ItemStack itemStack = user.getStackInHand(hand);
		BlockHitResult blockHitResult = raycast(world, user, bia.getFluid() == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
		if (blockHitResult.getType() == HitResult.Type.MISS) {
			return TypedActionResult.pass(itemStack);
		} else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return TypedActionResult.pass(itemStack);
		} else {
			BlockPos blockPos = blockHitResult.getBlockPos();
			Direction direction = blockHitResult.getSide();
			BlockPos blockPos2 = blockPos.offset(direction);
			if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos2, direction, itemStack)) {
				BlockState blockState;
				if (bia.getFluid() == Fluids.EMPTY) {
					blockState = world.getBlockState(blockPos);
					if (blockState.getBlock() instanceof FluidDrainable) {
						FluidDrainable fluidDrainable = (FluidDrainable)blockState.getBlock();
						ItemStack itemStack2 = fluidDrainable.tryDrainFluid(world, blockPos, blockState);
						if (!itemStack2.isEmpty()) {
							user.incrementStat(Stats.USED.getOrCreateStat(this));
							fluidDrainable.getBucketFillSound().ifPresent((sound) -> {
								user.playSound(sound, 1.0F, 1.0F);
							});
							world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
							itemStack2 = metalToWood(itemStack2);
							ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
							if (!world.isClient) {
								Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, itemStack2);
							}

							return TypedActionResult.success(itemStack3, world.isClient());
						}
					}

					return TypedActionResult.fail(itemStack);
				} else {
					blockState = world.getBlockState(blockPos);
					BlockPos blockPos3 = blockState.getBlock() instanceof FluidFillable && bia.getFluid() == Fluids.WATER ? blockPos : blockPos2;
					if (this.placeFluid(user, world, blockPos3, blockHitResult)) {
						this.onEmptied(user, world, itemStack, blockPos3);
						if (user instanceof ServerPlayerEntity) {
							Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, blockPos3, itemStack);
						}

						user.incrementStat(Stats.USED.getOrCreateStat(this));
						return TypedActionResult.success(getEmptiedStack(itemStack, user), world.isClient());
					} else {
						return TypedActionResult.fail(itemStack);
					}
				}
			} else {
				return TypedActionResult.fail(itemStack);
			}
		}
	}

	public static ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
		return !player.getAbilities().creativeMode ? new ItemStack(HavenMod.WOOD_BUCKET) : stack;
	}
}
