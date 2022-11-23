package haven.blood;

import haven.ModBase;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;

public class BloodBucketItem extends BucketItem {
	private boolean canDrink;

	public BloodBucketItem(Fluid fluid, Settings settings) {
		super(fluid, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		BlockHitResult hit = raycast(world, user, RaycastContext.FluidHandling.NONE);
		if (hit != null && hit.getType() == HitResult.Type.BLOCK && !user.isSneaking()) {
			canDrink = false;

			if (world.getBlockState(hit.getBlockPos()).getBlock() instanceof CauldronBlock) {
				user.setStackInHand(hand, new ItemStack(Items.BUCKET));
				return TypedActionResult.success(this.getDefaultStack(), true);
			}

			return super.use(world, user, hand);
		}

		canDrink = true;
		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
		if (playerEntity != null) {
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
			if (!playerEntity.getAbilities().creativeMode) {
				stack.decrement(1);
			}
		}
		if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.BUCKET);
			}
			if (playerEntity != null) {
				playerEntity.getInventory().insertStack(new ItemStack(Items.BUCKET));
			}
		}
		if (playerEntity == null || canDrink) {
			world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
		}
		return stack;
	}

	@Override
	public ItemStack getDefaultStack() {
		return new ItemStack(ModBase.BLOOD_BUCKET);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
}
