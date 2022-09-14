package haven.items;

import haven.HavenMod;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CottageCheeseBucketItem extends BlockItem {
	private boolean canDrink;

	public CottageCheeseBucketItem(Block block, Settings settings) {
		super(block, settings);
	}

	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult actionResult = super.useOnBlock(context);
		PlayerEntity playerEntity = context.getPlayer();
		if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
			Hand hand = context.getHand();
			playerEntity.setStackInHand(hand, Items.BUCKET.getDefaultStack());
		}

		return actionResult;
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

	public String getTranslationKey() {
		return this.getOrCreateTranslationKey();
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
		return new ItemStack(HavenMod.COTTAGE_CHEESE_BUCKET);
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