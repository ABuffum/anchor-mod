package fun.wich.item.bucket;

import fun.wich.util.MilkUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ModMilkBucketItem extends Item implements BucketProvided {
	private static final int MAX_USE_TIME = 32;
	private final BucketProvider bucketProvider;
	public BucketProvider getBucketProvider() { return bucketProvider; }

	public ModMilkBucketItem(Settings settings, BucketProvider bucketProvider) {
		super(settings);
		this.bucketProvider = bucketProvider;
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		if (user instanceof PlayerEntity && !((PlayerEntity)user).getAbilities().creativeMode) stack.decrement(1);
		if (!world.isClient) MilkUtil.ApplyMilk(world, user);
		return stack.isEmpty() ? new ItemStack(bucketProvider.getBucket()) : stack;
	}

	public int getMaxUseTime(ItemStack stack) { return MAX_USE_TIME; }

	public UseAction getUseAction(ItemStack stack) { return UseAction.DRINK; }

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}